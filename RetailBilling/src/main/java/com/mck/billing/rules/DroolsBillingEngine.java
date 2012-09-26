package com.mck.billing.rules;

import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mck.billing.BillingInvoice;

/**
 * JBOSS Drools based billing engine
 * 
 */
public class DroolsBillingEngine implements BillingEngine {

    private static Logger log = LoggerFactory.getLogger(DroolsBillingEngine.class);

    private KnowledgeRuntimeLogger droolsLogger;
    private final KnowledgeBase kbase;

    /**
     * Initialise the drools engine
     * 
     * @param droolsFileName
     */
    public DroolsBillingEngine(String droolsFileName) {
        this();
        initialise(droolsFileName);
    }

    private void initialise(String droolsFileName) {
        Resource droolsFile = ResourceFactory.newClassPathResource(droolsFileName);

        final KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        try {
            knowledgeBuilder.add(droolsFile, ResourceType.DRL);
        } catch (Exception e) {
            log.error("Unable to load the Drools file: " + droolsFileName);
            throw new RuntimeException("Unable to load the Drools file: " + droolsFileName);
        }

        // Check the builder for errors
        if (knowledgeBuilder.hasErrors()) {
            log.error(knowledgeBuilder.getErrors().toString());
            throw new RuntimeException("Unable to compile:" + droolsFileName);
        }

        // get the compiled packages (which are serializable)
        final Collection<KnowledgePackage> knowledgePackages = knowledgeBuilder.getKnowledgePackages();

        // add the packages to a knowledge base (deploy the knowledge packages).
        kbase.addKnowledgePackages(knowledgePackages);
    }

    private void startDroolsLogging(final StatefulKnowledgeSession ksession) {
        // setup the audit logging
        droolsLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "log/drools");
    }

    private void closeDroolsLoggging() {
        if (droolsLogger != null)
            droolsLogger.close();
    }

    private DroolsBillingEngine() {
        kbase = KnowledgeBaseFactory.newKnowledgeBase();
    }

    @Override
    public BillingInvoice generateCustomerBill(BillingInvoice billingInvoice) {
        final StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        startDroolsLogging(ksession);
        try {
            ksession.insert(billingInvoice);
            ksession.fireAllRules();
        } finally {
            closeDroolsLoggging();
            ksession.dispose();
        }
        return billingInvoice;

    }

}

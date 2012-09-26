package com.mck.billing.rules;

/**
 * The Billing Engine Factory that provides the billing engine for generating
 * bills based on billing rules,schemes,offers,discounts etc etc etc....
 * 
 */
public enum BillingEngineFactory {

    INSTANCE;

    private BillingEngine billingEngine;

    private BillingEngineFactory() {

        /**
         * NOTE: Currently we are using a simple billing engine. For ease of
         * business configurations, we could also use DroolsBillingEngine.
         * 
         * Currently DroolsBillingEngine cannot be injected since I did not
         * manage time to create a DROOLS .drl file that could fire all the
         * billing calculation rules
         */
        billingEngine = new SimpleBillingEngine();

        // billingEngine = new DroolsBillingEngine(Drools .drl file name comes
        // here) - create your own drools file;
    }

    /**
     * Returns a billing engine
     * 
     * @return
     */
    public BillingEngine getBillingEngine() {
        return billingEngine;
    }
}

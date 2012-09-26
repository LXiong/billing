package com.mck.billing;

import com.mck.billing.rules.BillingEngine;
import com.mck.billing.rules.BillingEngineFactory;

/**
 * External window that exposes billing related api. All logical work related to
 * billing is done underneath this facade
 * 
 */
public class BillingFacadeImpl implements BillingFacade {

    private final BillingEngine billingEngine;

    /**
     * Initialise
     * 
     */
    public BillingFacadeImpl() {
        billingEngine = BillingEngineFactory.INSTANCE.getBillingEngine();
    }

    @Override
    public BillingInvoice generateCustomerBill(BillingInvoice billingInvoice) {
        return billingEngine.generateCustomerBill(billingInvoice);
    }


}

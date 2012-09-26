package com.mck.billing.rules;

import com.mck.billing.BillingInvoice;

/**
 * Exposes all Billing related operations
 * 
 */
public interface BillingEngine {

    /**
     * Generates the bill for a provided invoice
     * 
     * 
     * @param billingInvoice
     * @return billingInvoice - Holds all billing information
     */
    public BillingInvoice generateCustomerBill(BillingInvoice billingInvoice);

}

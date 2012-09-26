package com.mck.billing;

/**
 * That billing facade that supports all billing related operations
 * 
 */
public interface BillingFacade {

    /**
     * Generates the bill for a provided customer
     * 
     * @param billingInvoice
     * @return
     */
    public BillingInvoice generateCustomerBill(BillingInvoice billingInvoice);

}

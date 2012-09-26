package com.mck.billing;

import java.util.List;

/**
 * All bill generation interfaces like Console, WEB etc should implement this
 * interface
 * 
 * @param <T>
 */
public interface BillGenerator<T> {

    /**
     * Returns the billing products - the products that the customer wants to
     * buy
     * 
     * @param userInputInterface
     * @return
     * 
     */
    public List<BillingProduct> getBillingProducts(T userInputInterface);

    /**
     * Returns the customer relation with the billing company
     * 
     * @param userInputInterface
     * @return
     */
    public String getCustomerRelation(T userInputInterface);

    /**
     * Returns the period since the customer is buying from the billing company
     * (The association time of the customer with the billing company)
     * 
     * @param userInputInterface
     * @return
     */
    public int getCustLongevity(T userInputInterface);

    /**
     * Generates and returns the customer bill
     * 
     * @param billedProducts
     * @param custRelation
     * @param custLongevity
     * @return
     */
    public BillingInvoice generateCustomerBill(List<BillingProduct> billedProducts, String custRelation, int custLongevity);

}

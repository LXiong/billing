package com.mck.billing;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all billing related information Note: No more billed products should be
 * added after BillingInvoice is initialised
 * 
 */
public class BillingInvoice {

    private List<BillingProduct> billingProducts;

    /**
     * This is the gross price of all the products
     */
    private long grossPrice = 0L;

    /**
     * The net final price to be payed by the customer after all discounts
     */
    private long netFinalPrice = 0L;

    /**
     * The discount amount
     */
    private long amountDiscounted = 0L;

    /**
     * The customer for whom this Billing invoice is generated
     */
    private Customer customer;

    /**
     * Initialise
     * 
     * @param billingProducts
     * 
     */
    public BillingInvoice(Customer customer, List<BillingProduct> billedProducts) {
        this.customer = customer;
        this.billingProducts = new ArrayList<BillingProduct>();
        this.billingProducts.addAll(billedProducts);
    }

    private BillingInvoice() {
        // SO that nobody uses this constructor
    }

    /**
     * Returns the gross price of all the products
     * 
     * @return
     */
    public long getGrossPrice() {
        return grossPrice;
    }

    /**
     * Sets the Gross price
     * 
     * @param price
     */
    public void setGrossPrice(long netPrice) {
        this.grossPrice = netPrice;
    }

    /**
     * Returns the net final price to be payed by the customer after all
     * discounts
     * 
     * @return
     */
    public long getNetFinalPrice() {
        return netFinalPrice;
    }

    /**
     * Sets the net final price to be payed by the customer
     * 
     * @param finalPayableAmount
     */
    public void setNetFinalPrice(long finalPayableAmount) {
        this.netFinalPrice = finalPayableAmount;
    }

    /**
     * Returns the customer
     * 
     * @return
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Returns the billed products
     * 
     * @return
     */
    public List<BillingProduct> getBillingProducts() {
        return billingProducts;
    }

    /**
     * Returns the amount discounted
     * 
     * @return
     */
    public long getAmountDiscounted() {
        return amountDiscounted;
    }

    /**
     * Sets the amount discounted
     * 
     * @param amountDiscounted
     */
    public void setAmountDiscounted(long amountDiscounted) {
        this.amountDiscounted = amountDiscounted;
    }

}

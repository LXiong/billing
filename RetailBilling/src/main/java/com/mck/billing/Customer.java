package com.mck.billing;

/**
 * Contains customer information
 * 
 */
public class Customer {

    private int longevity;
    private String relation;

    /**
     * Initialises a Customer
     * 
     * @param longevity
     * @param relation
     */
    public Customer(int longevity, String relation) {
        this.longevity = longevity;
        this.relation = relation;
    }

    private Customer() {
        // DO Nothing : Just barred the invocation of the default constructor
    }

    /**
     * Returns the longevity in months
     * 
     * @return
     */
    public int getLongevity() {
        return longevity;
    }

    /**
     * Return the relation
     * 
     * @return
     */
    public String getRelation() {
        return relation;
    }

}

package com.mck.billing.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * Singleton that reads customer relations from the configuration.properties
 * file
 * 
 */
public enum CustomerRelation {

    INSTANCE;

    private final List<String> customerRelations;

    private CustomerRelation() {
        String customerRelationsValue = ConfigurationPropertyReader.getInstance().getProperty(ConfigurationConstants.CUSTOMER_RELATION);
        String[] custRelationArray = StringUtils.split(customerRelationsValue, ";");
        customerRelations = Arrays.asList(custRelationArray);
    }

    /**
     * Returns true if the relation exists, else returns false
     * 
     * @param relation
     * @return
     */
    public boolean containsRelation(String relation) {
        return getCustomerRelations().contains(relation) ? true : false;
    }

    /**
     * Returns the customer relations
     * 
     * @return
     */
    public List<String> getCustomerRelations() {
        return customerRelations;
    }

}

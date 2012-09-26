package com.mck.billing.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class CustomerRelationTest {

    @Test
    public void shouldReturnFalseIfRelationNull() {
        boolean containsRelation = CustomerRelation.INSTANCE.containsRelation(null);
        assertFalse(containsRelation);
    }

    @Test
    public void shouldReturnFalseIfRelationNotFound() {
        boolean containsRelation = CustomerRelation.INSTANCE.containsRelation("doesNotExist");
        assertFalse(containsRelation);
    }

}

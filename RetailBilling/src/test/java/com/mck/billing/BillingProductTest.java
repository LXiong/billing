package com.mck.billing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mck.billing.generated.product.Product;
import com.mck.billing.utils.ProductUtils;

public class BillingProductTest {
    BillingProduct billingProduct;
    Product product;

    @Before
    public void init() {
        product = ProductUtils.INSTANCE.getProductByName("milk");
    }

    @Test
    public void testChangeInQntityShouldChangeGrossPrice() {
        billingProduct = new BillingProduct(1, product);
        
        long initGrossProductPrice = billingProduct.getGrossProductPrice();
        billingProduct.addProductQuantity(2);
        long updatedGrossProductPrice = billingProduct.getGrossProductPrice();
        Assert.assertTrue(updatedGrossProductPrice > initGrossProductPrice);
    }

}

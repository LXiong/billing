package com.mck.billing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mck.billing.generated.product.Product;
import com.mck.billing.utils.ProductUtils;

public class IntegrationTest {

    List<BillingProduct> billingProducts;
    BillGenerator<Scanner> billGenerator;

    @Before
    public void init() {
        billGenerator = new ConsoleBillGenerator();
        billingProducts = new ArrayList<BillingProduct>();

        Product milk = ProductUtils.INSTANCE.getProductByName("milk");
        Product jeans = ProductUtils.INSTANCE.getProductByName("jeans");
        BillingProduct milkBilling = new BillingProduct(3, milk);
        BillingProduct jeansBilling = new BillingProduct(1, jeans);

        billingProducts.add(jeansBilling);
        billingProducts.add(milkBilling);

    }

    @Test
    public void testAffiliateOrder() {
        String custInputRelation = "affiliate";
        int userLongevity = 20;

        BillingInvoice finalBill = billGenerator.generateCustomerBill(billingProducts, custInputRelation, userLongevity);

        long netFinalPrice = finalBill.getNetFinalPrice();
        Assert.assertEquals(1055, netFinalPrice);
    }

    @Test
    public void testEmployeeOrder() {
        String custInputRelation = "employee";
        int userLongevity = 20;

        BillingInvoice finalBill = billGenerator.generateCustomerBill(billingProducts, custInputRelation, userLongevity);

        long netFinalPrice = finalBill.getNetFinalPrice();
        Assert.assertEquals(885, netFinalPrice);
    }

    @Test
    public void tesNoRelationOrder() {
        String custInputRelation = "none";
        int userLongevity = 20;

        BillingInvoice finalBill = billGenerator.generateCustomerBill(billingProducts, custInputRelation, userLongevity);

        long netFinalPrice = finalBill.getNetFinalPrice();
        Assert.assertEquals(1140, netFinalPrice);
    }

    @Test
    public void testCustomerLongevityRelationOrder() {
        String custInputRelation = "none";
        int userLongevity = 25;

        BillingInvoice finalBill = billGenerator.generateCustomerBill(billingProducts, custInputRelation, userLongevity);

        long netFinalPrice = finalBill.getNetFinalPrice();
        Assert.assertEquals(1100, netFinalPrice);
    }

}

package com.mck.billing;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * The starting point of the project if you have to generate a bill using
 * console input
 * 
 */
public class Start {

    /**
     * The starting point for the console user
     */
    public static void main(String[] args) {
        PrintStream output = System.out;

        output.println("Welcome to Retail Billing System");

        Scanner userInput = new Scanner(System.in);

        BillGenerator<Scanner> billGenerator = new ConsoleBillGenerator();

        List<BillingProduct> billingProducts = billGenerator.getBillingProducts(userInput);

        String custInputRelation = billGenerator.getCustomerRelation(userInput);

        int userLongevity = billGenerator.getCustLongevity(userInput);

        BillingInvoice finalBill = billGenerator.generateCustomerBill(billingProducts, custInputRelation, userLongevity);

        output.println("**************BILL DETAILS*****************************");
        output.println("Gross price of all products = " + finalBill.getGrossPrice());
        output.println("Total discounts = " + finalBill.getAmountDiscounted());
        output.println("*******************************************");
        output.println("Final NET price that you have to pay : " + finalBill.getNetFinalPrice());

    }

}

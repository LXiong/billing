package com.mck.billing.rules;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mck.billing.BillingProduct;
import com.mck.billing.BillingInvoice;
import com.mck.billing.utils.ConfigurationConstants;
import com.mck.billing.utils.ConfigurationPropertyReader;

/**
 * A simple in-line billing engine for handling billing calculations
 * 
 */
public class SimpleBillingEngine implements BillingEngine {

    private static Logger log = LoggerFactory.getLogger(SimpleBillingEngine.class);

    @Override
    public BillingInvoice generateCustomerBill(BillingInvoice billingInvoice) {

        return deductProductLevelDiscounts(billingInvoice);
    }

    /**
     * This method deducts the product level percentage based discounts and also
     * updates the billing invoice level net invoice price
     * 
     * @param billingInvoice
     * @return
     */
    private BillingInvoice deductProductLevelDiscounts(BillingInvoice billingInvoice) {

        /**
         * This is total price of all the products after product level discounts
         * have been deducted
         */
        long netAllProductsPrice = 0L;

        /**
         * This is the price of all products without any discounts
         */
        long grossInvoicePrice = 0L;

        /**
         * This is the total of product level discounts
         */
        long productLevelDiscounts = 0L;

        int customerRelationDiscount = getCustomerRelationDiscount(billingInvoice);
        int customerLongevityDiscount = getCustomerLongevityDiscount(billingInvoice);
        int percentageDiscount = getPercentageDiscount(customerRelationDiscount, customerLongevityDiscount);

        log.info("Percentage discount selected = " + percentageDiscount);

        List<BillingProduct> billedProducts = billingInvoice.getBillingProducts();

        for (BillingProduct aBilledProduct : billedProducts) {

            // If product category is not blocked on percentage based discounts:
            if (!BooleanUtils.isTrue(aBilledProduct.getProductCategory().isBlockpercentagediscount())) {
                long grossProductPrice = aBilledProduct.getGrossProductPrice();
                long netProductPrice = aBilledProduct.getGrossProductPrice();

                // Calculate product discount
                long productDiscount = (percentageDiscount * grossProductPrice) / 100;

                log.info("Discount on product : " + aBilledProduct.getProductDetails().getName() + " = " + productDiscount);
                netProductPrice = grossProductPrice - productDiscount;
                productLevelDiscounts += productDiscount;

                aBilledProduct.setNetProductPrice(netProductPrice);
            } else {
                log.info("By passing percentage based product level discounts on product:" + aBilledProduct.getProductDetails().getName()
                        + "\t | Category : " + aBilledProduct.getProductCategory().getName());
            }

            netAllProductsPrice += aBilledProduct.getNetProductPrice();
            grossInvoicePrice += aBilledProduct.getGrossProductPrice();
        }

        log.info("Gross invoice price = " + grossInvoicePrice);
        billingInvoice.setGrossPrice(grossInvoicePrice);

        log.info("Sum of product level discounts = " + productLevelDiscounts);
        billingInvoice.setAmountDiscounted(productLevelDiscounts);

        log.info("Net price of all products after product level percentage discounts = " + netAllProductsPrice);

        return deductInvoiceLevelDiscounts(netAllProductsPrice, billingInvoice);
    }

    /**
     * Deducts all the invoice level discounts that are applied after all
     * product level discounts have been deducted
     * 
     * @param netAllProductsPrice
     * @param billingInvoice
     * @return
     */
    private BillingInvoice deductInvoiceLevelDiscounts(long netAllProductsPrice, BillingInvoice billingInvoice) {

        String repetitiveDiscBandString = ConfigurationPropertyReader.getInstance().getProperty(ConfigurationConstants.REPETITIVE_DISCOUNT_BAND);
        String repetitiveDiscValueString = ConfigurationPropertyReader.getInstance().getProperty(ConfigurationConstants.REPETITIVE_DISCOUNT_VAL);

        if (NumberUtils.isDigits(repetitiveDiscValueString) && NumberUtils.isDigits(repetitiveDiscBandString)) {
            log.info("Applying product level repetive discounts");

            log.info("repetitive_discount_band = " + repetitiveDiscBandString + " and repetitive_discount_val = "
                    + repetitiveDiscValueString);

            long repetitiveDiscountBand = Long.parseLong(repetitiveDiscBandString);
            long repetitiveDiscountValue = Long.parseLong(repetitiveDiscValueString);
            long numberOfDiscRepitions = netAllProductsPrice / repetitiveDiscountBand;
            long totalRepetitiveDisc = numberOfDiscRepitions * repetitiveDiscountValue;

            log.info("Total Repitive discount = " + totalRepetitiveDisc);
            billingInvoice.setNetFinalPrice(netAllProductsPrice - totalRepetitiveDisc);

            log.info("Final Invoice price : " + billingInvoice.getNetFinalPrice());
            billingInvoice.setAmountDiscounted(billingInvoice.getAmountDiscounted() + totalRepetitiveDisc);
        }
        return billingInvoice;
    }

    private int getPercentageDiscount(int customerRelationDiscount, int customerLongevityDiscount) {
        int pecentageDiscount = 0;

        String discountCalculationStrategy = ConfigurationPropertyReader.getInstance().getProperty(
                ConfigurationConstants.PERCENTAGE_DISCOUNT_CALCULATION_STRATEGY);
        discountCalculationStrategy = StringUtils.trim(discountCalculationStrategy);

        if (!StringUtils.isEmpty(discountCalculationStrategy)) {
            if (discountCalculationStrategy.equalsIgnoreCase("MAX")) {
                log.info("Percent discount calculation strategy used = MAX");
                pecentageDiscount = customerRelationDiscount >= customerLongevityDiscount ? customerRelationDiscount : customerLongevityDiscount;
            } else {
                log.info("Percent discount calculation strategy used = MIN");
                pecentageDiscount = customerRelationDiscount <= customerLongevityDiscount ? customerRelationDiscount : customerLongevityDiscount;
            }
        }

        return pecentageDiscount;
    }

    private int getCustomerLongevityDiscount(BillingInvoice billingInvoice) {
        int longevityDiscountPercent = 0;
        int customerLongevity = billingInvoice.getCustomer().getLongevity();
        String minLongevityTime = ConfigurationPropertyReader.getInstance().getProperty(ConfigurationConstants.MIN_LONGEVITY_TIME);
        if (NumberUtils.isDigits(minLongevityTime)) {
            log.info("Minimum longevity time = " + minLongevityTime);
            if (customerLongevity >= Integer.parseInt(minLongevityTime)) {
                String longevityDiscountValue = ConfigurationPropertyReader.getInstance().getProperty(ConfigurationConstants.LONGEVITY_DISCOUNT);
                if (NumberUtils.isDigits(longevityDiscountValue)) {
                    longevityDiscountPercent = Integer.parseInt(longevityDiscountValue);
                } else {
                    log.info("Could not read longevity discount");
                }
            }
        } else {
            log.info("Minimum longevity time not defined properly");
        }

        log.info("Longevity discount percent =  " + longevityDiscountPercent);
        return longevityDiscountPercent;
    }

    private int getCustomerRelationDiscount(BillingInvoice billingInvoice) {
        int customerRelationDiscount = 0;
        String customerRelation = billingInvoice.getCustomer().getRelation();

        String relationshipPercentDiscount = ConfigurationPropertyReader.getInstance().getProperty(
                customerRelation + ConfigurationConstants.RELATION_DISCOUNT);

        if (NumberUtils.isDigits(relationshipPercentDiscount)) {
            log.info("Percent customer relation discount = " + relationshipPercentDiscount);
            customerRelationDiscount = Integer.parseInt(relationshipPercentDiscount);
        } else {
            log.info("No Customer Relation Discounts applied");
        }

        return customerRelationDiscount;
    }

}

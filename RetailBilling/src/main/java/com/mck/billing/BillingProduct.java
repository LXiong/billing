package com.mck.billing;

import com.mck.billing.generated.category.Category;
import com.mck.billing.generated.product.Product;
import com.mck.billing.utils.ProductUtils;

/**
 * A product to be billed for the customer
 * 
 */
public class BillingProduct {

    private int productQuantity;

    /**
     * Price of a product * productQuantity is grossProductPrice. This is the
     * price before any discounts on the product
     */
    private long grossProductPrice;

    /**
     * Final price of the product quantity after all product level discounts
     */
    private long netProductPrice;

    private final Product productDetails;
    private final Category productCategory;

    /**
     * Initialises a billed product by setting the product type and the quantity
     * of the product
     * 
     * @param productQuantity
     * @param userSelectedProduct
     */
    public BillingProduct(int productQuantity, Product userSelectedProduct) {
        this.productQuantity = productQuantity;
        this.productDetails = userSelectedProduct;
        productCategory = ProductUtils.INSTANCE.getProductCategory(userSelectedProduct);
        updateGrossProductPrice();
    }

    /**
     * Returns the billed product't quantity
     * 
     * @return
     */
    public int getProductQuantity() {
        return productQuantity;
    }

    /**
     * Adds the supplied quantity to the existing product quantity
     * 
     * @param additionalQuantity
     */
    public void addProductQuantity(int additionalQuantity) {
        this.productQuantity = this.productQuantity + additionalQuantity;
        updateGrossProductPrice();
    }

    /**
     * Returns the product's details
     * 
     * @return
     */
    public Product getProductDetails() {
        return productDetails;
    }

    /**
     * Returns the product category
     * 
     * @return
     */
    public Category getProductCategory() {
        return productCategory;
    }

    /**
     * Returns the net product price for all quantities before any discounts
     * 
     * @return
     */
    public long getGrossProductPrice() {
        return grossProductPrice;
    }

    public long getNetProductPrice() {
        return netProductPrice;
    }

    public void setNetProductPrice(long finalProductPrice) {
        this.netProductPrice = finalProductPrice;
    }

    private void updateGrossProductPrice() {
        grossProductPrice = productQuantity * productDetails.getPrice();

        /**
         * Since grossProductPrice can only be updated before billing
         * calculations, hence netProductPrice can be set to grossProductPrice
         * until final billing calculations when the netProductPrice may
         * decrease due to accrued discounts
         */
        netProductPrice = grossProductPrice;
    }
}

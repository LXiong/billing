package com.mck.billing.utils;

import java.util.List;

import com.mck.billing.catalogue.CategoryFactory;
import com.mck.billing.catalogue.ProductFactory;
import com.mck.billing.generated.category.Category;
import com.mck.billing.generated.category.CategoryList;
import com.mck.billing.generated.product.Product;
import com.mck.billing.generated.product.ProductList;

/**
 * Contains utility methods related to the product catalog
 * 
 */
public enum ProductUtils {

    INSTANCE;

    private ProductList productList;
    private CategoryList categoryList;

    private ProductUtils() {
        productList = ProductFactory.INSTANCE.getProductList();
        categoryList = CategoryFactory.INSTANCE.getCategoryList();
    }

    /**
     * Returns the product with the matching name from the product catalogue.
     * Returns null if no matching product is found
     * 
     * @param productName
     * @return
     */
    public Product getProductByName(String productName) {
        productName = productName.trim();

        List<Product> allProducts = productList.getProduct();
        for (Product product : allProducts) {

            String aProductName = product.getName().trim();
            if (aProductName.equalsIgnoreCase(productName)) {
                return product;
            }
        }

        return null;
    }

    /**
     * Returns the list of available products
     * 
     * @return
     */
    public List<Product> getAllAvailableProducts() {
        return productList.getProduct();
    }

    /**
     * Returns the Category with the matching name as the product's category
     * name Returns null if no matching category found
     * 
     * @param product
     * @return
     */
    public Category getProductCategory(Product product) {
        String productCategoryName = product.getCategory().trim();
        List<Category> allCategories = categoryList.getCategory();
        for (Category aCategory : allCategories) {
            String aCategoryName = aCategory.getName().trim();
            if (aCategoryName.equalsIgnoreCase(productCategoryName)) {
                return aCategory;
            }
        }
        return null;
    }

}

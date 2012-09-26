package com.mck.billing.catalogue;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.mck.billing.generated.category.Category;
import com.mck.billing.generated.product.Product;

public class ProductFactoryTest {

    @Test
    public void testProductsAreNeverEmpty() {
        List<Product> products = ProductFactory.INSTANCE.getProductList().getProduct();
        assertTrue(products.size() > 0);
    }

    @Test(expected = RuntimeException.class)
    public void testShouldThrowExceptionIfProductFileNotFound() throws Exception {
        ProductFactory.INSTANCE.getObject("someFile", ProductFactory.INSTANCE.getUnmarshalledPackageName());
    }
    
    @Test
    public void testCategoryEntryFoundForEachProductCategory() {
        List<Product> products = ProductFactory.INSTANCE.getProductList().getProduct();
        List<Category> categories = CategoryFactory.INSTANCE.getCategoryList().getCategory();
        
        
        for (Product eachProduct : products) {
            boolean categoryExistForProductCategory = false;
            
            String productCategory = eachProduct.getCategory();
            for (Category aCategory : categories) {
                if(productCategory.equalsIgnoreCase(aCategory.getName())){
                    categoryExistForProductCategory = true;
                    break;
                }
            }
            assertTrue(categoryExistForProductCategory);
        }
        
    }

}

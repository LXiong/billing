package com.mck.billing.catalogue;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mck.billing.generated.category.CategoryList;

public class CategoryFactoryTest {

    @Test
    public void testCategoryNeverEmpty() throws Exception {
        CategoryList categoryList = CategoryFactory.INSTANCE.getCategoryList();
        assertTrue(categoryList.getCategory().size() > 0);
    }

    @Test(expected = RuntimeException.class)
    public void testShouldThrowExceptionIfCategoryFileNotFound() throws Exception {
        CategoryFactory.INSTANCE.getObject("someFile", CategoryFactory.INSTANCE.getUnmarshalledPackageName());
    }

}

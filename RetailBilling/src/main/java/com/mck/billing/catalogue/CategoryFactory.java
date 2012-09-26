package com.mck.billing.catalogue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.mck.billing.generated.category.CategoryList;
import com.mck.billing.utils.ConfigurationConstants;
import com.mck.billing.utils.ConfigurationPropertyReader;

/**
 * The singleton category factory that accesses the JAXB unmarshalled category
 * schema
 * 
 */
public enum CategoryFactory implements CatalogueFactory<CategoryList> {

    /**
     * The singleton instance that holds the un-marshalled Category Package name
     * field
     */
    INSTANCE("com.mck.billing.generated.category");

    private ConfigurationPropertyReader configurationPropertyReader;
    private CategoryList categoryList;
    private final String unmarshalledPackageName;

    private CategoryFactory(String unmarshalledPackageName) {
        this.unmarshalledPackageName = unmarshalledPackageName;

        try {
            configurationPropertyReader = ConfigurationPropertyReader.getInstance();
            categoryList = getObject(configurationPropertyReader.getProperty(ConfigurationConstants.CATEGORY_CATALOGUE_FILE), unmarshalledPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occured while reading Product Catalogue");
        }
    }

    /**
     * Returns the List of all product categories
     * 
     * @return
     */
    public CategoryList getCategoryList() {
        return categoryList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.mck.billing.catalogue.CatalogueFactory#getObject(java.lang.String,
     * java.lang.String)
     * 
     * NOTE: Public access to this method is open just to allow dynamic category
     * list picking. Avoid public usage of this method in common case scenarios
     * 
     * Helps in testing
     */
    @Override
    public CategoryList getObject(String fileName, String unmarshalledPackageName) throws Exception {
        try {
            JAXBContext context = JAXBContext.newInstance(unmarshalledPackageName);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (CategoryList) unmarshaller.unmarshal(getClass().getResourceAsStream("/schema/" + fileName));
        } catch (Exception e) {
            throw new RuntimeException("Could not load the category list file");
        }
    }

    /**
     * Returns the unmarshalled category package name
     * 
     * @return
     */
    public String getUnmarshalledPackageName() {
        return unmarshalledPackageName;
    }

}

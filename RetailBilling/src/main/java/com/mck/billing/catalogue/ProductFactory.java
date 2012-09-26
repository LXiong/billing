package com.mck.billing.catalogue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.mck.billing.generated.product.ProductList;
import com.mck.billing.utils.ConfigurationConstants;
import com.mck.billing.utils.ConfigurationPropertyReader;

/**
 * The singleton product factory that accesses the JAXB unmarshalled product
 * schema
 * 
 */
public enum ProductFactory implements CatalogueFactory<ProductList> {

    /**
     * The singleton instance that holds the unmarshalled Product Package name
     * field
     */
    INSTANCE("com.mck.billing.generated.product");

    private ConfigurationPropertyReader configurationPropertyReader;
    private ProductList productList;
    private final String unmarshalledPackageName;

    private ProductFactory(String unmarshalledPackageName) {
        this.unmarshalledPackageName = unmarshalledPackageName;

        try {
            configurationPropertyReader = ConfigurationPropertyReader.getInstance();
            productList = getObject(configurationPropertyReader.getProperty(ConfigurationConstants.PRODUCT_CATALOGUE_FILE), unmarshalledPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occured while reading Product Catalogue");
        }
    }

    /**
     * Returns the List of available products
     * 
     * @return
     */
    public ProductList getProductList() {
        return productList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.mck.billing.catalogue.CatalogueFactory#getObject(java.lang.String,
     * java.lang.String)
     * 
     * NOTE: Public access to this method is open just to allow dynamic product
     * list picking. Avoid public usage of this method in common case scenarios
     * 
     * Helps in testing
     */
    @Override
    public ProductList getObject(String fileName, String unmarshalledPackageName) throws Exception {
        try {
            JAXBContext context = JAXBContext.newInstance(unmarshalledPackageName);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (ProductList) unmarshaller.unmarshal(getClass().getResourceAsStream("/schema/" + fileName));
        } catch (Exception e) {
            throw new RuntimeException("Could not load the product list file");
        }
    }

    /**
     * Returns the unmarshalled package name
     * 
     * @return
     */
    public String getUnmarshalledPackageName() {
        return unmarshalledPackageName;
    }

}

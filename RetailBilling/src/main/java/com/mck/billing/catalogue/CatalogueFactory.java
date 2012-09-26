package com.mck.billing.catalogue;

/**
 * Base factory for reading all XML catalogues
 * 
 */
interface CatalogueFactory<T> {

    T getObject(String fileName, String unmarshalledPackageName) throws Exception;

}

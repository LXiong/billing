package com.mck.billing;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang.math.NumberUtils;

import com.mck.billing.generated.product.Product;
import com.mck.billing.utils.CustomerRelation;
import com.mck.billing.utils.ProductUtils;

/**
 * Bill generator that uses console scanner for user inputs
 */
public class ConsoleBillGenerator implements BillGenerator<Scanner>
{

    private static final PrintStream OUTPUT = System.out;

    @Override
    public List<BillingProduct> getBillingProducts(Scanner userInputInterface) {
        List<BillingProduct> billedProducts = new ArrayList<BillingProduct>();

        displayProductCatalogue();

        addProductsToCart(billedProducts, userInputInterface);

        displayUserProductCart(billedProducts);

        return billedProducts;
    }

    @Override
    public String getCustomerRelation(Scanner userInputInterface) {

        OUTPUT.println("Type your relation with our company :");
        List<String> customerRelations = CustomerRelation.INSTANCE.getCustomerRelations();
        for (String customerRelation : customerRelations) {
            OUTPUT.println(customerRelation);
        }

        String userInputRelation = userInputInterface.next();
        while (!CustomerRelation.INSTANCE.containsRelation(userInputRelation.trim())) {
            OUTPUT.println("No matching relation found. Please specify the correct relation");
            userInputRelation = userInputInterface.next();
        }
        return userInputRelation;

    }

    @Override
    public BillingInvoice generateCustomerBill(List<BillingProduct> billedProducts, String custRelation, int custLongevity) {
        Customer customer = new Customer(custLongevity, custRelation);
        BillingInvoice billingInvoice = new BillingInvoice(customer, billedProducts);
        BillingFacade billingFacade = new BillingFacadeImpl();
        return billingFacade.generateCustomerBill(billingInvoice);
    }

    @Override
    public int getCustLongevity(Scanner userInputInterface) {

        OUTPUT.println("Please specify the number of months since you have been our customer (in digits): ");

        String userInputLongevity = userInputInterface.next();
        while (!NumberUtils.isDigits(userInputLongevity)) {
            OUTPUT
                    .println("Wrong input. Please specify the number of months (in digits) since you have been our customer (type 0 for new customer) : ");
            userInputLongevity = userInputInterface.next();
        }
        return Integer.parseInt(userInputLongevity);
    }

    private void addProductsToCart(List<BillingProduct> billedProducts, Scanner userInput) {
        // Add the first product
        addProductToUserBilling(billedProducts, userInput);
        OUTPUT.println("DO you want to add more products? Y : N");

        // Continue adding products :
        while (userInput.next().equalsIgnoreCase("Y")) {
            addProductToUserBilling(billedProducts, userInput);
            OUTPUT.println("DO you want to add more products? Y : N");
        }
    }

    private void displayUserProductCart(List<BillingProduct> billedProducts) {
        OUTPUT.println("Following products are added by the user");
        for (BillingProduct billedProduct : billedProducts) {
            OUTPUT.println("Product Name : " + billedProduct.getProductDetails().getName() + " \t| Per Unit Price : "
                    + billedProduct.getProductDetails().getPrice() + " \t| Quantity : " + billedProduct.getProductQuantity()
                    + " \t| Gross Total Product Price: " + billedProduct.getGrossProductPrice() + " \t| Product Category : "
                    + billedProduct.getProductCategory().getName());
        }
    }

    private void addProductToUserBilling(List<BillingProduct> billedProducts, Scanner userInput) {
        Product userSelectedProduct = selectProductByName(userInput);

        int productQuantity = getQuantityForProduct(userInput, userSelectedProduct);

        // If user has again selected a product that he/she added earlier, then
        // update the quantity of the already added product
        boolean existingProductUpdated = updateExistingProductQuantity(billedProducts, userSelectedProduct, productQuantity);

        if (!existingProductUpdated) {
            // Create a billed product
            BillingProduct aBilledProduct = new BillingProduct(productQuantity, userSelectedProduct);
            // Add this product to the list of products bought by the user
            billedProducts.add(aBilledProduct);
            OUTPUT
                    .println("Added Product : " + aBilledProduct.getProductDetails().getName() + " | Quantity : "
                            + aBilledProduct.getProductQuantity());
        }
    }

    private boolean updateExistingProductQuantity(List<BillingProduct> billedProducts, Product userSelectedProduct, int productQuantity) {

        for (BillingProduct aBilledProduct : billedProducts) {
            if (aBilledProduct.getProductDetails().getName().equalsIgnoreCase(userSelectedProduct.getName())) {

                OUTPUT.println("Product : " + userSelectedProduct.getName()
                        + " has already been selected by the user, so we are updating the quanity of the existing product to "
                        + aBilledProduct.getProductQuantity() + " + " + productQuantity + " = "
                        + (aBilledProduct.getProductQuantity() + productQuantity));

                aBilledProduct.addProductQuantity(productQuantity);

                return true;
            }
        }

        return false;
    }

    private void displayProductCatalogue() {
        OUTPUT.println("List of products available :");

        List<Product> allAvailableProducts = ProductUtils.INSTANCE.getAllAvailableProducts();

        for (Product aProduct : allAvailableProducts) {
            OUTPUT.println("Product Name : " + aProduct.getName() + " \t| Price : " + aProduct.getPrice() + " \t| Category : "
                    + aProduct.getCategory());
        }
    }

    private int getQuantityForProduct(Scanner userInput, Product userSelectedProduct) {
        OUTPUT.println("How many " + userSelectedProduct.getName() + " do you want to buy (Quantity) : ");
        String aUserProductQuantity = userInput.next();

        // Verify that quantity is a whole number only!!
        boolean isWholeNumber = NumberUtils.isDigits(aUserProductQuantity);
        while (!isWholeNumber) {
            OUTPUT.println("Please provide a valid quantity (whole number only)");
            aUserProductQuantity = userInput.next();
            isWholeNumber = NumberUtils.isDigits(aUserProductQuantity);
        }

        int productQuantity = Integer.parseInt(aUserProductQuantity);
        return productQuantity;
    }

    private Product selectProductByName(Scanner userInput) {
        OUTPUT.println("Enter the name of the product that you want to buy : ");
        String aUserProductName = userInput.next();

        Product userSelectedProduct = ProductUtils.INSTANCE.getProductByName(aUserProductName);
        while (null == userSelectedProduct) {
            OUTPUT.println("No such product found. Please provide a valid product name : ");
            aUserProductName = userInput.next();
            userSelectedProduct = ProductUtils.INSTANCE.getProductByName(aUserProductName);
        }
        return userSelectedProduct;
    }

}

This is a Billing System.

_________________
How to run :
Open command prompt. 
Go to the project's target folder.
Enter the command "java -jar RetailBilling-1.0-SNAPSHOT.jar" - And your billing system should be up and running.
OR
The main class to run the project is "Start.java" located at src/main/java com.mck.billing package.
_________________

Salient Features and Cautions: 

List of products is maintained in "productlist.xml" found at src/main/resources/schema (Corresponding schema to adhere to: product.xsd)

List of categories is maintained in "categorylist.xml" found at src/main/resources/schema (Corresponding schema to adhere to: category.xsd)

While adding a new product in productlist.xml, make sure the product's category exists in categorylist.xml (Unit test will fail if you dont maintain that anyhow!!)

All configurable properties of the Billing System are in "configuration.properties" located at /src/main/resources (Read on each property in this file and you shall understand what it is doing)



The project is a maintained by maven so no external dependencies required.

All code is checkstyle aware, so you can't really mess up with the java coding style (Build will fail if you do so).

In case, you want to provide billing rules through a DROOLS rule engine .drl file: Create your desired .drl file and place it under scr/main/resources. Open the java class BillingEngineFactory and comment "billingEngine = new SimpleBillingEngine();" and uncomment the line below it i.e. "billingEngine = new DroolsBillingEngine(Drools .drl file name comes here)". 





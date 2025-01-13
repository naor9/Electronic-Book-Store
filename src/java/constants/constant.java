/*Here the defines / constant will be placed.*/
package constants;

public class constant {

    //constants in utils:
    //in ConnectionTODatabase:
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/BookParadise";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "1234";

    //in beans:
    //in BookBean and CategoryBean:
    public static final int NUMBER_OF_CATEGORIES = 10;   //the number of categories in the 'categories' table in the DB.

    //in CustomerBean:
    /*'MAX_EXPIRATION_DATEFOR_CREDIT_CARD' represent the maximum years a credit
    card can have for expiration date since it was created:*/
    public static final int MAX_EXPIRATION_DATE_FOR_CREDIT_CARD = 5;

    //errors:
    public static final int ERROR_IN_GETTING_QUANTITY_IN_THE_STORE_FROM_THE_DATABASE = -1;
    
    //initialize values:
    public static final int INITIALIZE_YEAR = 0;
    public static final int INITIALIZE_PAGE_NUMBER = 0;
    public static final double INITIALIZE_PRICE = 0.0;
    public static final int INITIALIZE_QUANTITY_IN_THE_STORE = 0;
    public static final int INITIALIZE_QUANTITY_FOR_CUSTOMER_TO_BUY = 0;
    public static final int INITIALIZE_ORDER_NUMBER = -1;
    public static final int INITIALIZE_TOTAL_PAYMENT = -1;
    public static final int INITIALIZE_INDEX_IN_ARRAY = -1;
    public static final String INITIALIZE_MANAGER_USER_NAME = "";
    public static final String INITIALIZE_MANAGER_PASSWORD = "";

    //others:
    public static final int MAX_PAGE_NUMBER = 24000;
}

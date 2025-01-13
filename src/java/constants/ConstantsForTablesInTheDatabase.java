/*
 Here are all the constants for the tables in the Database
 */
package constants;

public class ConstantsForTablesInTheDatabase {

    //manager table:
    public static final String MANAGER_TABLE_NAME = "manager";
    public static final String MANAGER_USER_NAME_COLUMN_NAME = "managerusername";
    public static final String MANAGER_PASSWORD_COLUMN_NAME = "managerpassword";

    //Customer table:
    public static final String CUSTOMER_TABLE_NAME = "customer";
    public static final String CUSTOMER_USER_NAME_COLUMN_NAME = "username";
    public static final String CUSTOMER_PASSWORD_COLUMN_NAME = "Password";
    public static final int IN_CUSTOMER_TABLE_USER_NAME_COULMN_NUMBER = 1;
    public static final int IN_CUSTOMER_TABLE_PASSWORD_COULMN_NUMBER = 2;
    public static final int IN_CUSTOMER_TABLE_CUSTOMER_FIRST_NAME_COULMN_NUMBER = 3;
    public static final int IN_CUSTOMER_TABLE_CUSTOMER_LAST_NAME_COULMN_NUMBER = 4;
    public static final int IN_CUSTOMER_TABLE_ADDRESS_COULMN_NUMBER = 5;
    public static final int IN_CUSTOMER_TABLE_PHONE_NUMBER_COULMN_NUMBER = 6;
    public static final int IN_CUSTOMER_TABLE_CREDIT_CARD_NUMBER_COULMN_NUMBER = 7;
    public static final int IN_CUSTOMER_TABLE_CREDIT_CARD_EXPIRATION_DATE_COULMN_NUMBER = 8;

    //books table:
    public static final String BOOKS_TABLE_NAME = "books";
    public static final String BOOK_TITLE = "booktitle";
    public static final String BOOK_PUBLISH_YEAR = "publishyear";
    public static final String BOOK_WRITER_FIRST_NAME = "writerfirstname";
    public static final String BOOK_WRITER_LAST_NAME = "writerlastname";
    public static final String BOOK_CATEGORY = "category";
    public static final String BOOK_PAGE_NUMBER = "pagenumber";
    public static final String BOOK_IMAGE_URL = "bookimageurl";
    public static final String BOOK_PRICE = "price";
    public static final String QUANTITY_IN_THE_STORE = "quantityinthestore";
    public static final int BOOK_TITLE_COULMN_NUMBER = 1;
    public static final int PUBLISH_YEAR_COULMN_NUMBER = 2;
    public static final int IN_BOOKS_TABLE_WRITER_FIRST_NAME_COULMN_NUMBER = 3;
    public static final int IN_BOOKS_TABLE_WRITER_LAST_NAME_COULMN_NUMBER = 4;
    public static final int PAGE_NUMBER_COULMN_NUMBER = 5;
    public static final int BOOK_IMG_URL_COULMN_NUMBER = 6;
    public static final int PRICE_COULMN_NUMBER = 7;
    public static final int QUANTITY_IN_THE_STORE_COULMN_NUMBER = 8;

    //bookCategory table:
    public static final String BOOKCATEGORY_TABLE_NAME = "bookcategory";
    public static final String IN_BOOKCATEGORY_TABLE_BOOK_TITLE = "booktitle";
    public static final String IN_BOOKCATEGORY_TABLE_CATEGORY = "category";
    public static final int IN_BOOKCATEGORY_TABLE_BOOK_TITLE_COULMN_NUMBER = 1;
    public static final int IN_BOOKCATEGORY_TABLE_CATEGORY_TITLE_COULMN_NUMBER = 2;

    //books netural join with bookCategory:
    public static final int IN_BOOKS_NETURAL_JOIN_WITH_BOOKCATEGORY_CATEGORY_COULMN_NUMBER = 9;

    //orders table:
    public static final String ORDER_TABLE_NAME = "orders";
    public static final String IN_ORDERS_TABLE_USER_NAME_COULMN_NAME = "username";
    public static final int IN_ORDERS_TABLE_ORDER_NUMBER_COULMN_NUMBER = 1;
    public static final int IN_ORDERS_TABLE_USER_NAME_COULMN_NUMBER = 2;
    public static final int IN_ORDERS_TABLE_BOOK_TITLE_COULMN_NUMBER = 3;
    public static final int IN_ORDERS_TABLE_QUANTITY_COULMN_NUMBER = 4;
    public static final int IN_ORDERS_TABLE_TOTAL_PAYMENT_COULMN_NUMBER = 5;
    public static final int IN_ORDERS_TABLE_ORDER_DATE_COULMN_NUMBER = 6;

    //writer table:
    public static final String WRITER_TABLE_NAME = "writers";
    public static final String IN_WRITERS_TABLE_WRITER_FIRST_NAME = "writerfirstname";
    public static final String IN_WRITERS_TABLE_WRITER_LAST_NAME = "writerlastname";
    public static final int IN_WRITERS_TABLE_WRITER_FIRST_NAME_COULMN_NUMBER = 1;
    public static final int IN_WRITERS_TABLE_WRITER_LAST_NAME_COULMN_NUMBER = 2;

}

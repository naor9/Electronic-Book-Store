/*
 This class will have the constants for all the pages for the manager.
 */
package beans.constantsBean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;


@ManagedBean(name = "managerPagesBean")
@RequestScoped
public class ManagerPagesBean implements Serializable {

     public static final String MANAGER_FILE = "/manager/";
    public static final String MANAGER_LOG_IN = "managerLogIn";
    public static final String MANAGER_LOG_IN_FROM_OUTSIDE_MANAGER_FILE = MANAGER_FILE + MANAGER_LOG_IN;
    public static final String MANAGER_WELCOME_PAGE = "managerWelcomePage";
    public static final String ADD_NEW_BOOK = "addNewBook";
    public static final String BOOK_WAS_ADDED_SUCCESSFULLY = "bookWasAddedSuccessfully";
    public static final String ADD_NEW_WRITER = "addNewWriter";
    public static final String WRITER_WAS_ADDED_SUCCESSFULLY = "writerWasAddedSuccessfully";
    public static final String SEARCH_BOOK_FOR_EDIT_BOOK = "searchBookForEditBook";
    public static final String EDIT_BOOK = "editBook";
    public static final String BOOK_WAS_EDIT_SUCCESSFULLY = "bookWasEditSuccessfully";
    public static final String ORDERS_INFO = "ordersInfo";
    public static final String CUSTOMER_INFO = "customerInfo";
    public static final String EDIT_WRITER = "editWriter";
    public static final String SEARCH_WRITER_FOR_EDIT_WRITER = "searchWriterForEditWriter";
    public static final String WRITER_WAS_EDIT_SUCCESSFULLY = "writerWasEditSuccessfully";
    public static final String MANAGER_LOG_IN_SUCCESSFUL = "managerLogInSuccessful";
    public static final String MANAGER_LOG_IN_SUCCESSFUL_FROM_OUTSIDE_MANAGER_FILE = MANAGER_FILE +MANAGER_LOG_IN_SUCCESSFUL; 
    public static final String MANAGER_LOG_OUT_SUCCESSFULLY = "managerLogOutSuccessfully";
    public static final String EDIT_PASSWORD_FOR_MANAGER = "editPasswordForManager";
    public static final String MANAGER_PASSWORD_WAS_EDIT_SUCCESSFULLY = "managerPasswordWasEditSuccessfully";
    public static final String ADD_NEW_MANAGER = "addNewManager";
    public static final String MANAGER_WAS_ADDED_SUCCESSFULLY = "managerWasAddedSuccessfully";
    public static final String SEARCH_CUSTOMER_FOR_CUSTOMER_ORDERS = "searchCustomerForCustomerOrders";
    public static final String ALL_THE_ORDERS_AND_INFO_OF_A_CUSTOMER = "allTheOrdersAndInfoOfACustomer";

    public ManagerPagesBean() {
    }

    //getters:
    public static String getALL_THE_ORDERS_AND_INFO_OF_A_CUSTOMER() {
        return ALL_THE_ORDERS_AND_INFO_OF_A_CUSTOMER;
    }
    
    public static String getMANAGER_WAS_ADDED_SUCCESSFULLY() {
        return MANAGER_WAS_ADDED_SUCCESSFULLY;
    }

    public static String getSEARCH_CUSTOMER_FOR_CUSTOMER_ORDERS() {
        return SEARCH_CUSTOMER_FOR_CUSTOMER_ORDERS;
    }

    public static String getADD_NEW_MANAGER() {
        return ADD_NEW_MANAGER;
    }

    public static String getEDIT_PASSWORD_FOR_MANAGER() {
        return EDIT_PASSWORD_FOR_MANAGER;
    }

    public static String getMANAGER_PASSWORD_WAS_EDIT_SUCCESSFULLY() {
        return MANAGER_PASSWORD_WAS_EDIT_SUCCESSFULLY;
    }

    public static String getMANAGER_FILE() {
        return MANAGER_FILE;
    }

    public static String getMANAGER_LOG_IN_SUCCESSFUL_FROM_OUTSIDE_MANAGER_FILE() {
        return MANAGER_LOG_IN_SUCCESSFUL_FROM_OUTSIDE_MANAGER_FILE;
    }

    public static String getMANAGER_LOG_OUT_SUCCESSFULLY() {
        return MANAGER_LOG_OUT_SUCCESSFULLY;
    }

    public static String getMANAGER_LOG_IN() {
        return MANAGER_LOG_IN;
    }

    public static String getMANAGER_LOG_IN_FROM_OUTSIDE_MANAGER_FILE() {
        return MANAGER_LOG_IN_FROM_OUTSIDE_MANAGER_FILE;
    }

    public static String getMANAGER_WELCOME_PAGE() {
        return MANAGER_WELCOME_PAGE;
    }

    public static String getADD_NEW_BOOK() {
        return ADD_NEW_BOOK;
    }

    public static String getBOOK_WAS_ADDED_SUCCESSFULLY() {
        return BOOK_WAS_ADDED_SUCCESSFULLY;
    }

    public static String getADD_NEW_WRITER() {
        return ADD_NEW_WRITER;
    }

    public static String getWRITER_WAS_ADDED_SUCCESSFULLY() {
        return WRITER_WAS_ADDED_SUCCESSFULLY;
    }

    public static String getSEARCH_BOOK_FOR_EDIT_BOOK() {
        return SEARCH_BOOK_FOR_EDIT_BOOK;
    }

    public static String getEDIT_BOOK() {
        return EDIT_BOOK;
    }

    public static String getBOOK_WAS_EDIT_SUCCESSFULLY() {
        return BOOK_WAS_EDIT_SUCCESSFULLY;
    }

    public static String getORDERS_INFO() {
        return ORDERS_INFO;
    }

    public static String getCUSTOMER_INFO() {
        return CUSTOMER_INFO;
    }

    public static String getEDIT_WRITER() {
        return EDIT_WRITER;
    }

    public static String getSEARCH_WRITER_FOR_EDIT_WRITER() {
        return SEARCH_WRITER_FOR_EDIT_WRITER;
    }

    public static String getWRITER_WAS_EDIT_SUCCESSFULLY() {
        return WRITER_WAS_EDIT_SUCCESSFULLY;
    }

    public static String getMANAGER_LOG_IN_SUCCESSFUL() {
        return MANAGER_LOG_IN_SUCCESSFUL;
    }
    
    
}

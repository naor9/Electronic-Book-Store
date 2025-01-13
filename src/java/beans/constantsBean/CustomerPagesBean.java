/*
 This class will have the constants for all the pages for the customer.
 */
package beans.constantsBean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "customerPagesBean")
@RequestScoped
public class CustomerPagesBean implements Serializable {

    public static final String CUSTOMER_FILE = "/customer/";
    public static final String BOOK_INFO = CUSTOMER_FILE + "bookInfo";
    public static final String BOOK_SEARCH_RESULT = CUSTOMER_FILE + "bookSearchResult";
    public static final String CART = CUSTOMER_FILE + "cart";
    public static final String CUSTOMER_HOME_PAGE = "/index";
    public static final String CUSTOMER_LOG_IN = CUSTOMER_FILE + "customerLogIn";
    public static final String CUSTOMER_REGISTER = CUSTOMER_FILE + "customerRegister";
    public static final String NO_BOOK_WAS_FOUND = CUSTOMER_FILE + "noBookWasFound";
    public static final String SUCCESSFUL_TRANSACTION = CUSTOMER_FILE + "successfulTransaction";
    public static final String CUSTOMER_LOG_IN_SUCCESSFUL = CUSTOMER_FILE + "customerLogInSuccessful";
    public static final String CUSTOMER_REGISTER_SUCCESSFUL = CUSTOMER_FILE + "customerRegisterSuccessful";
    public static final String EDIT_CUSTOMER_INFO = CUSTOMER_FILE + "editCustomerInfo";
    public static final String CUSTOMER_WAS_EDIT_SUCCESSFULLY = CUSTOMER_FILE + "customerWasEditSuccessfully";
    public static final String ERROR_EDIT_CUSTOMER = CUSTOMER_FILE + "errorEditCustomer";
    public static final String LOG_OUT__SUCCESSFULLY = CUSTOMER_FILE + "CustomerLogOutSuccessfully";
    public static final String LOG_OUT_ERROR = CUSTOMER_FILE + "logOutError";

    public CustomerPagesBean() {
    }

    //getters:
    public static String getCUSTOMER_FILE() {
        return CUSTOMER_FILE;
    }

    public static String getBOOK_INFO() {
        return BOOK_INFO;
    }

    public static String getBOOK_SEARCH_RESULT() {
        return BOOK_SEARCH_RESULT;
    }

    public static String getCART() {
        return CART;
    }

    public static String getCUSTOMER_HOME_PAGE() {
        return CUSTOMER_HOME_PAGE;
    }

    public static String getCUSTOMER_LOG_IN() {
        return CUSTOMER_LOG_IN;
    }

    public static String getCUSTOMER_REGISTER() {
        return CUSTOMER_REGISTER;
    }

    public static String getNO_BOOK_WAS_FOUND() {
        return NO_BOOK_WAS_FOUND;
    }

    public static String getSUCCESSFUL_TRANSACTION() {
        return SUCCESSFUL_TRANSACTION;
    }

    public static String getCUSTOMER_LOG_IN_SUCCESSFUL() {
        return CUSTOMER_LOG_IN_SUCCESSFUL;
    }

    public static String getCUSTOMER_REGISTER_SUCCESSFUL() {
        return CUSTOMER_REGISTER_SUCCESSFUL;
    }

    public static String getEDIT_CUSTOMER_INFO() {
        return EDIT_CUSTOMER_INFO;
    }

    public static String getCUSTOMER_WAS_EDIT_SUCCESSFULLY() {
        return CUSTOMER_WAS_EDIT_SUCCESSFULLY;
    }

    public static String getERROR_EDIT_CUSTOMER() {
        return ERROR_EDIT_CUSTOMER;
    }

    public static String getLOG_OUT__SUCCESSFULLY() {
        return LOG_OUT__SUCCESSFULLY;
    }

    public static String getLOG_OUT_ERROR() {
        return LOG_OUT_ERROR;
    }

}

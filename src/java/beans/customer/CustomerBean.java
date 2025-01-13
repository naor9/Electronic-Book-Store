/*
 Managed Bean for the customer info, for log in and register.
 */
package beans.customer;

import beans.book.BookBean;
import DAO.customer.GetCustomersInfoFromDatabaseDAO;
import DateHelper.DateChecker;
import beans.book.SearchBookBean;
import static beans.constantsBean.CustomerPagesBean.LOG_OUT_ERROR;
import static beans.constantsBean.CustomerPagesBean.LOG_OUT__SUCCESSFULLY;
import static beans.constantsBean.ManagerPagesBean.CUSTOMER_INFO;
import static beans.constantsBean.ManagerPagesBean.SEARCH_CUSTOMER_FOR_CUSTOMER_ORDERS;
import beans.order.customer.CustomerOrderLineBean;
import beans.order.customer.OrderForCustomerBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "customerBean")
@SessionScoped
public class CustomerBean implements Serializable {

    /*'isCustomerLoggedIn' will indicate if the customer logged in or not.*/
    private boolean isCustomerLoggedIn = false;
    private String customerUserName = null;
    private String customerPassword = null;
    private String customerFirstName = null;
    private String customerLastName = null;
    private String address = null;
    private String phoneNumber = null;
    private String creditCardNumber = null;
    Date creditCardExpirationDate = null;

    //builder;
    public CustomerBean() {
    }

    //getters and setters:
    public void setIsCustomerLoggedIn(boolean isCustomerLoggedIn) {
        this.isCustomerLoggedIn = isCustomerLoggedIn;
    }

    public boolean getIsCustomerLoggedIn() {
        return isCustomerLoggedIn;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public void setCustomerFirstName(String CustomerFirstName) {
        this.customerFirstName = CustomerFirstName;
    }

    public void setCustomerLastName(String CustomerLastName) {
        this.customerLastName = CustomerLastName;
    }

    public void setAddress(String Address) {
        this.address = Address;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.phoneNumber = PhoneNumber;
    }

    public void setCreditCardNumber(String CreditCardNumber) {
        this.creditCardNumber = CreditCardNumber;
    }

    /*This method gets a LocalDate object in the format 01/MM/YY, and checks 
    if this date is a valid credit card expiration date. 
    If the date is valid, it saves it, and if not it intialize the value
    and give appropriate response to the user, to change the expiration date to another.*/
    public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
        if (DateChecker.isTheGivenDateIsMoreThanTheMaximumPossibleExpirationDate(creditCardExpirationDate)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "The credit card expiration date should not be more than 5 years from today.", null));
            creditCardExpirationDate = null;
        } else {
            /*If the next 'if' is true then the credit card is exipred:*/
            if (DateChecker.isTheGivenDateObjectDateHadPassed(creditCardExpirationDate)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "The credit card expiration date had alredy passed! Please choose a credit card with a future expiration date", null));
                creditCardExpirationDate = null;
            } else {
                this.creditCardExpirationDate = creditCardExpirationDate;
            }
        }
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public Date getCreditCardExpirationDate() {
        return creditCardExpirationDate;
    }

    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //override:
    /*This methods return true if the values of this customer and the given customer 
     are the same (exept "isCustomerLoggedIn" that can be the same or diffrent),
     and return false otherwise.*/
    public boolean equals(CustomerBean secondCustomer) {
        return (this.customerUserName.equals(secondCustomer.getCustomerUserName()))
                && (this.customerPassword.equals(secondCustomer.getCustomerPassword()))
                && (this.customerFirstName.equals(secondCustomer.getCustomerFirstName()))
                && (this.customerLastName.equals(secondCustomer.getCustomerLastName()))
                && (this.address.equals(secondCustomer.getAddress()))
                && (this.phoneNumber.equals(secondCustomer.getPhoneNumber()))
                && (this.creditCardNumber.equals(secondCustomer.getCreditCardNumber()))
                && (isThisCreditCardExpirationDateIsTheSame(secondCustomer.getCreditCardExpirationDate()));
    }
    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //other methods:

    /*This method reset all the values of this customer.*/
    public void initializeCustomerBean() {
        isCustomerLoggedIn = false;
        customerUserName = null;
        customerPassword = null;
        customerFirstName = null;
        customerLastName = null;
        address = null;
        phoneNumber = null;
        creditCardNumber = null;
        creditCardExpirationDate = null;
    }

    /*return getCreditCardExpirationDate as a String representing only 
     the month and the year.*/
    public String getCreditCardExpirationDateYearMonthAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        // Format the Date object:
        String formattedDate = dateFormat.format(creditCardExpirationDate);
        return formattedDate;
    }

    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //methods for log out:    
    /*This function loged out the user. It clear his data like search, cart and customer info.*/
    public String logOut(OrderForCustomerBean order, CustomerOrderLineBean lineInOrder, BookBean book,
            SearchBookBean bookSerachResult, UpdateCostomerBean customerInfoForUpdate) {
        if (!isCustomerLoggedIn) {
            return LOG_OUT_ERROR;
        }
        order.initializeOrder();
        lineInOrder.initializeLineInTheOrder();
        book.initializeBookBean();
        bookSerachResult.initializeSearchBookBean();
        customerInfoForUpdate.initializeUpdateCostomerBean();
        initializeCustomerBean();
        return LOG_OUT__SUCCESSFULLY;
    }

    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //****************************************************************************************************************************

    /*This method checks if at least one of the data in this customerBean is null, and return true
     if so, or false otherwise.*/
    public boolean checkIfAtLeastOneOfTheDataIsNull() {
        return customerUserName == null
                || customerPassword == null
                || customerFirstName == null
                || customerLastName == null
                || address == null
                || phoneNumber == null
                || creditCardNumber == null
                || creditCardExpirationDate == null;
    }

    /*This method gets username and make this CustomerBean with the info 
     of the customer with that user name from the database.
     It also returns a direction for the manager into the 'customerInfo' page.*/
    public String setCustomerInfoFromUserName(String customerUserName) {
        //The next 'if' checks if the customer exist in the database:
        if (!GetCustomersInfoFromDatabaseDAO.checkIfUserNameExistInTheDatabase(customerUserName)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "The user name doesn't exist in the database.", null));
            return SEARCH_CUSTOMER_FOR_CUSTOMER_ORDERS;
        }
        GetCustomersInfoFromDatabaseDAO.setTheGivenCustomerBeanWithGivenUserName(customerUserName, this);
        return CUSTOMER_INFO;
    }

    /*return true if the given credit card expiration date and this credit card
     expiration date are the same, and false otherwise.*/
    private boolean isThisCreditCardExpirationDateIsTheSame(Date secondCreditCardExpirationDate) {
        LocalDate LocalDateCreditCardExpirationDate = creditCardExpirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        YearMonth creditCardExpirationDateYearMonth = YearMonth.from(LocalDateCreditCardExpirationDate);
        LocalDate secondLocalDateCreditCardExpirationDate = secondCreditCardExpirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        YearMonth secondCreditCardExpirationDateYearMonth = YearMonth.from(secondLocalDateCreditCardExpirationDate);
        return creditCardExpirationDateYearMonth.equals(secondCreditCardExpirationDateYearMonth);
    }

    /*This method gets a customerBean and copy all the given CustomerBean values 
     into this CustomerBean, except 'isCustomerLoggedIn'.
     This method assume the given CustomerBean data is legit.*/
    void setAllTheValuesIntoTheGivenCustomerBeanValues(CustomerBean newCustomer) {
        customerUserName = newCustomer.getCustomerUserName();
        customerPassword = newCustomer.getCustomerPassword();
        customerFirstName = newCustomer.getCustomerFirstName();
        customerLastName = newCustomer.getCustomerLastName();
        address = newCustomer.getAddress();
        phoneNumber = newCustomer.getPhoneNumber();
        creditCardNumber = newCustomer.getCreditCardNumber();
        creditCardExpirationDate = newCustomer.getCreditCardExpirationDate();
    }
}

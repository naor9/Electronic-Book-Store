/*
 Managed Bean for the manager and customer log in.
 */
package beans.logIn;

import DAO.logIn.LogInDAO;
import static beans.constantsBean.CustomerPagesBean.CUSTOMER_LOG_IN;
import static beans.constantsBean.CustomerPagesBean.CUSTOMER_LOG_IN_SUCCESSFUL;
import static beans.constantsBean.ManagerPagesBean.MANAGER_LOG_IN_SUCCESSFUL_FROM_OUTSIDE_MANAGER_FILE;
import beans.customer.CustomerBean;
import beans.manager.ManagerBean;
import static constants.ConstantsForTablesInTheDatabase.CUSTOMER_PASSWORD_COLUMN_NAME;
import static constants.ConstantsForTablesInTheDatabase.CUSTOMER_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.CUSTOMER_USER_NAME_COLUMN_NAME;
import static constants.ConstantsForTablesInTheDatabase.MANAGER_PASSWORD_COLUMN_NAME;
import static constants.ConstantsForTablesInTheDatabase.MANAGER_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.MANAGER_USER_NAME_COLUMN_NAME;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "logInBean")
@RequestScoped
public class LogInBean implements Serializable {

    private String userName;
    private String password;

    public LogInBean() {
    }

    //getters and setters: 
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    //other methods:

    /*This function check if the data is valid (username and password), and if it is valid
      it redirects the user (customer or manager) into their welcome page.*/
    public String logIn(CustomerBean customer, ManagerBean manager) {
        if (checkIfAUserIsAlreadyLoggedIn(customer.getIsCustomerLoggedIn(), manager.getIsManagerLoggedIn())) {
            return CUSTOMER_LOG_IN;  //to stay in the same page.
        }
        if (checkIfCustomerInfoIsValid(customer)) {
            //The next line is to fill the info of the customer:
            customer.setIsCustomerLoggedIn(true);
            customer.setCustomerInfoFromUserName(userName);
            return CUSTOMER_LOG_IN_SUCCESSFUL;
        }
        if (checkIfManagerInfoIsValid(manager)) {
            return MANAGER_LOG_IN_SUCCESSFUL_FROM_OUTSIDE_MANAGER_FILE;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password", null));
        return CUSTOMER_LOG_IN;    //to stay in the same page.
    }

    /*return true if the user is already log in, and false otherwise with appropriate message and initillize
     this LogInBean veriables.*/
    private boolean checkIfAUserIsAlreadyLoggedIn(boolean isCustomerLoggedIn, boolean isManagerLoggedIn) {
        if (isCustomerLoggedIn || isManagerLoggedIn) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "A user is already log in! Please log out first", null));
            return true;
        }
        return false;
    }

    /*This method checks if the info of this LogInBean is a valid info for a customer log in,
     and this method return true if it does and saves the customer info and intialize this LogInBean
     variables. Otherwise This method return false.*/
    private boolean checkIfCustomerInfoIsValid(CustomerBean customer) {
        /*if the next 'if' is true then the customer loged in successfully:*/
        if (LogInDAO.isTheUserNameAndPasswordExistInTheDatabase(userName, password,
                CUSTOMER_TABLE_NAME, CUSTOMER_USER_NAME_COLUMN_NAME, CUSTOMER_PASSWORD_COLUMN_NAME)) {
            return true;
        }
        return false;
    }

    /*This method checks if the info of this LogInBean is a valid info for a manager log in,
     and this method return true if it does and saves the manager info and intialize this LogInBean
     variables. Otherwise This method return false.*/
    private boolean checkIfManagerInfoIsValid(ManagerBean manager) {
        /*if the next 'if' is true then the manager loged in successfully:*/
        if (LogInDAO.isTheUserNameAndPasswordExistInTheDatabase(userName, password,
                MANAGER_TABLE_NAME, MANAGER_USER_NAME_COLUMN_NAME, MANAGER_PASSWORD_COLUMN_NAME)) {
            manager.setIsManagerLoggedIn(true);
            manager.setManagerUserName(userName);
            manager.setManagerPassword(password);
            return true;
        }
        return false;
    }
}

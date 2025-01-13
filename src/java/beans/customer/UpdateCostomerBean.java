/*
 This bean will be used for update(edit) the customer info. 
 It will keep the original cutomer info for later use for the edit of the new customer data.
 */
package beans.customer;

import DAO.customer.GetCustomersInfoFromDatabaseDAO;
import DAO.customer.UpdateCustomerInfoDAO;
import static beans.constantsBean.CustomerPagesBean.CUSTOMER_WAS_EDIT_SUCCESSFULLY;
import static beans.constantsBean.CustomerPagesBean.EDIT_CUSTOMER_INFO;
import static beans.constantsBean.CustomerPagesBean.ERROR_EDIT_CUSTOMER;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "updateCostomerBean")
@SessionScoped
public class UpdateCostomerBean implements Serializable {

    //'originalCustomer' the the data before the changes:
    private CustomerBean originalCustomer = new CustomerBean();
    //'newCustomer' will represent the new data of the customer (is used as a 'temp' customer with the new data)
    private CustomerBean newCustomer = new CustomerBean();

    //constructor:
    public UpdateCostomerBean() {
    }

    public void setNewCustomer(CustomerBean newCustomer) {
        this.newCustomer = newCustomer;
    }

    public CustomerBean getNewCustomer() {
        return newCustomer;
    }

    //getters and setters:
    public void setOriginalCustomer(CustomerBean OriginalCustomer) {
        this.originalCustomer = OriginalCustomer;
    }

    public CustomerBean getOriginalCustomer() {
        return originalCustomer;
    }

    //other methods:
    /*This method checks if the customer is logged in, in if he does logged in
     this method saves the customer in this 'OriginalCustomer' for latter use and sends 
     the user into update customer info page. 
     if the customer is not logged in the customer is sent into an appropriate page.*/
    public String canCustomerUpdateHisInfo(CustomerBean originalCustomer) {
        if (!originalCustomer.getIsCustomerLoggedIn()) {
            return ERROR_EDIT_CUSTOMER;
        }
        //else:
        this.originalCustomer = originalCustomer;
        //the next line is to 'fill' the data of the customer from the database:
        GetCustomersInfoFromDatabaseDAO.setTheGivenCustomerBeanWithGivenUserName(
                originalCustomer.getCustomerUserName(), this.originalCustomer);
        newCustomer = new CustomerBean();
        return EDIT_CUSTOMER_INFO;
    }

    /*This method initialize all the values of this object.*/
    public void initializeUpdateCostomerBean() {
        originalCustomer.initializeCustomerBean();
    }

    /*this method gets nothing, and change the info in the database
     from the 'originalCustomer' info (the old customer info) into the 'newCustomerInfo' 
    (the new customer info).
     This method sands the user into an appropricate page depend if it success
     to update the data or not, and give appropriate notice in cases of an error.*/
    public synchronized String editCustomerInTheDatabase() {
        /*The next if checks if at least one of the data (like credit card expiration date)
         wasn't legit (and hence its valuse is null)*/
        if (newCustomer.checkIfAtLeastOneOfTheDataIsNull()) {
            return EDIT_CUSTOMER_INFO;
        }
        /*To check if there was any change in the customer info:*/
        if (isTheNewEditCustomerIsTheSameAsBeforeTheEdit()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The data is the same as before, please change at least 1 category in order to update.", null));
            return EDIT_CUSTOMER_INFO;
        }
        /*if the next 'if' is true then the user name is already exist in the database
        and the customer will need to choose another user name*/
        if (!newCustomer.getCustomerUserName().equals(originalCustomer.getCustomerUserName())
                && GetCustomersInfoFromDatabaseDAO.checkIfUserNameExistInTheDatabase(newCustomer.getCustomerUserName())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The username " + newCustomer.getCustomerUserName() + " is already exist, please choose another"
                            + " user name", null));
            newCustomer.setCustomerUserName(null); //initialize the user name.
            return EDIT_CUSTOMER_INFO;
        }
        /*Add the new customer to the database and checks if the customer was added successfully:*/
        if (!UpdateCustomerInfoDAO.updateCustomer(originalCustomer, newCustomer)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Oops, something heppened! Your info wasn't updated, please try again", null));
            return EDIT_CUSTOMER_INFO;
        }
        originalCustomer.setAllTheValuesIntoTheGivenCustomerBeanValues(newCustomer);
        newCustomer.initializeCustomerBean();
        return CUSTOMER_WAS_EDIT_SUCCESSFULLY;
    }

    /*This method is part of the handle the 'edit' customer info.
      It checks if there was any changes in the 'edit' command, and if so return true,
      otherwise give an appropriate response and return false.*/
    private boolean isTheNewEditCustomerIsTheSameAsBeforeTheEdit() {
        /*To check if there was a change in the customer info:*/
        if (newCustomer.equals(originalCustomer)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No data has changed in your info, hence no update occurred, please try again", null));
            return true;
        }
        return false;
    }

}

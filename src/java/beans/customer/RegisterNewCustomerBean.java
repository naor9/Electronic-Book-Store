/*
  ManagedBean for register a new customer
 */
package beans.customer;

import DAO.customer.GetCustomersInfoFromDatabaseDAO;
import DAO.customer.RegisterNewCustomerDAO;
import static beans.constantsBean.CustomerPagesBean.CUSTOMER_REGISTER;
import static beans.constantsBean.CustomerPagesBean.CUSTOMER_REGISTER_SUCCESSFUL;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


@ManagedBean(name = "registerNewCustomerBean")
@RequestScoped
public class RegisterNewCustomerBean implements Serializable {

    
    public RegisterNewCustomerBean() {
    }
    
    /*This method handle the register of a new customer. 
    It adds it to the database and send the customer into an appropriate page
    if the register was successful, or send a notice if the register was not successful.*/
    public String registerNewCustomer(CustomerBean customerBean) {
        /*To check if the customer is already log in:*/
        if (customerBean.getIsCustomerLoggedIn()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "A user is already log in! Please log out first", null));
            return CUSTOMER_REGISTER;
        }
        /*if the next 'if' is true then the user name is already exist in the database
        and the customer will need to choose another user name*/
        if (GetCustomersInfoFromDatabaseDAO.checkIfUserNameExistInTheDatabase(customerBean.getCustomerUserName())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The username " + customerBean.getCustomerUserName() + 
                                    " is already exist, please choose other user name", null));
            customerBean.setCustomerUserName(null); //initialize the user name.
            return CUSTOMER_REGISTER;
        }
        /*The next if checks if at least one of the data (like credit card expiration date)
         wasn't legit (and hence its valuse is null)*/
        if (customerBean.checkIfAtLeastOneOfTheDataIsNull()) {
            return CUSTOMER_REGISTER;
        }
        /*Add the new customer to the database and checks if the customer was added successfully:*/
        if (!RegisterNewCustomerDAO.addNewCustomerToTheDatabase(customerBean.getCustomerUserName(),
                customerBean.getCustomerPassword(), customerBean.getCustomerFirstName(),
                customerBean.getCustomerLastName(), customerBean.getAddress(), customerBean.getPhoneNumber(),
                customerBean.getCreditCardNumber(), customerBean.getCreditCardExpirationDate())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "OOPS! Something went wrong! couldn't register. please try to register again", null));
            return CUSTOMER_REGISTER;
        }
        customerBean.setIsCustomerLoggedIn(true);
        return CUSTOMER_REGISTER_SUCCESSFUL;
    }
}

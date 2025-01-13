/*
 'OrderForCustomerBean' represent a full customer order from the store, 
 calculating all the books (represented as 'CustomerOrderLineBean') the customer bought.
 */
package beans.order.customer;

import DAO.orders.InsertOrderIntoTheDatabaseDAO;
import DateHelper.DateChecker;
import static beans.constantsBean.CustomerPagesBean.CART;
import static beans.constantsBean.CustomerPagesBean.SUCCESSFUL_TRANSACTION;
import beans.customer.CustomerBean;
import static constants.constant.INITIALIZE_QUANTITY_FOR_CUSTOMER_TO_BUY;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "orderForCustomerBean")
@SessionScoped
public class OrderForCustomerBean implements Serializable {

    //'order' represent an order of a customer, with all the books and their quantiteis:
    private ArrayList<CustomerOrderLineBean> order = new ArrayList<CustomerOrderLineBean>();
    //'totalOrderPayment' is the sum of the payment the customer made with all the books he bought:
    private double totalOrderPayment = 0.0;
    //the quantity of a book that is being added to this order(temp veriable for creating a new CustomerOrderLineBean):
    private int quantity;

    //constructor:
    public OrderForCustomerBean() {
    }

    //getters and setters:
    public ArrayList<CustomerOrderLineBean> getOrder() {
        return order;
    }

    public double getTotalOrderPayment() {
        return totalOrderPayment;
    }

    public void setOrder(ArrayList<CustomerOrderLineBean> order) {
        this.order = order;
    }

    public void setTotalOrderPayment(double totalOrderPayment) {
        this.totalOrderPayment = totalOrderPayment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
    }

    //*************************************************************************************************************************
    //*************************************************************************************************************************
    //*************************************************************************************************************************
    //other methods:
    /*This method initialize all the variables of this object.*/
    public void initializeOrder() {
        this.order.clear();
        totalOrderPayment = 0.0;
        quantity = INITIALIZE_QUANTITY_FOR_CUSTOMER_TO_BUY;
    }

    //*************************************************************************************************************************
    //*************************************************************************************************************************
    //*************************************************************************************************************************
    /*This method responsible to when the customer click "add to cart" in 'bookInfo' page to a book.
    This method finish to update the new 'orderLineBean' info, and add that new 
    'OrderLineBean' into this Order, and send the user to see his cart in the 'cart' page.*/
    public String updateTheNewOrderLineAndAddItToOrder(String bookTitle, double bookPrice, int quantityInTheStore) {
        //creating a new 'CustomerOrderLineBean':
        CustomerOrderLineBean orderLine = new CustomerOrderLineBean(bookTitle,
                bookPrice, quantity, quantityInTheStore, quantity * bookPrice);
        order.add(orderLine);
        totalOrderPayment += quantity * bookPrice;
        quantity = 0;  //to initialize quantity for the next info on the next book
        return CART;
    }

    /*Deleted the given orderLineBean from this order(from the cart)*/
    public String delteOrderLineFromOrder(CustomerOrderLineBean orderLine) {
        totalOrderPayment -= orderLine.getTotalPayment();
        order.remove(orderLine);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "The book order was deleted.", null));
        return CART;
    }

    /*This method deals when the customer cklick "check out".
     It checks if the order is legit (customer loged in and cart isn't empty),
     and if so insert the orders lines beans into the 'order' database, 
     update the quantity in the store for the books that were bought 
     and send the user to a successful transaction page.
     If not, it gives an appropriate notice to the customer.*/
    public synchronized String pay(CustomerBean customer) {
        if (!checkIfOrderIsLegal(customer)) {
            return CART;  //stay in the same page with the notifications
        }//else:
        //if the next 'if' is true then the transaction was successful:
        if (InsertOrderIntoTheDatabaseDAO.addOrderToTheDatabaseIfLegit(customer.getCustomerUserName(), this)) {
            this.initializeOrder();
            return SUCCESSFUL_TRANSACTION;
        }//else:
        //In case the quantity in the order had changed
        calculateNewTotalPayment();
        return CART;
    }

    /*This methods is responsible to check if the customer can transmit the new order.*/
    private boolean checkIfOrderIsLegal(CustomerBean customer) {
        //if true then the customer didn't loged in yet:
        if (!customer.getIsCustomerLoggedIn()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "You must log in before making an order", null));
            return false;
        }
        //if true then the order(cart) is empty:
        if (order.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Your cart is empty, please fill it to make an order", null));
            return false;
        }
        //if true then the credit card was expired:
        if (DateChecker.isTheGivenDateObjectDateHadPassed(customer.getCreditCardExpirationDate())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Your credit card was expired, please update your "
                            + "details with a valid credit card", null));
            return false;
        }
        return true;
    }

    /*calculate the totalOrderPayment 
    Needed when a quantity in an orderLine was changed, for example.*/
    public String calculateNewTotalPayment() {
        this.totalOrderPayment = 0;
        for (CustomerOrderLineBean orderLine : order) {
            totalOrderPayment += orderLine.getPrice() * orderLine.getQuantity();
        }
        return CART;
    }
}

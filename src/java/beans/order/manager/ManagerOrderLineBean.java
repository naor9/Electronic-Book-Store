/*
'ManagerOrderLineBean' represent a line in the 'Orders' table in the databse.  
 The 'managerOrderLineBean' is for the use of the manager, to get data from the 'Orders'
 table in the database.
 */
package beans.order.manager;

import beans.order.OrderLineBean;
import static constants.constant.INITIALIZE_ORDER_NUMBER;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "managerOrderLineBean")
@SessionScoped
public class ManagerOrderLineBean extends OrderLineBean implements Serializable {

    /*'orderNumber' is a uniqe number that is uniqe to each order*/
    private int orderNumber;
    private String userName;
    private Date dateOfTheOrder;

    public ManagerOrderLineBean() {
    }

    /*constructor with part of others values that are being given, for getting order line from DB.*/
    public ManagerOrderLineBean(int orderNumber, String userName, String bookTitle, int quantity,
            double totalPayment, Date dateOfTheOrder) {
        super(bookTitle, quantity, totalPayment);
        this.orderNumber = orderNumber;
        this.userName = userName;
        this.dateOfTheOrder = dateOfTheOrder;
    }

    // Getter and Setter:
    public void setDateOfTheOrder(Timestamp dateOfTheOrder) {
        this.dateOfTheOrder = dateOfTheOrder;
    }

    public Date getDateOfTheOrder() {
        return dateOfTheOrder;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (userName.length() >= 3 && userName.length() <= 16) {
            this.userName = userName;
        } else {
            throw new IllegalArgumentException("Username length must be between 3 and 16 characters.");
        }
    }

    //***************************************************************************************************************************
    //***************************************************************************************************************************
    //***************************************************************************************************************************  
    //override methods:

    /*This method initialize all the values of this object.*/
    @Override
    public void initializeLineInTheOrder() {
        super.initializeLineInTheOrder();
        orderNumber = INITIALIZE_ORDER_NUMBER;
        userName = null;
        dateOfTheOrder = null;
    }

}

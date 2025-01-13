/*
'customerOrderLineBean' represent a line in the customer bill (in the customer order). 
 The 'customerOrderLineBean' is for the use of the customer, to create a new order for the customer
 and to save it in the database.
 */
package beans.order.customer;

import beans.order.OrderLineBean;
import static constants.constant.INITIALIZE_PRICE;
import static constants.constant.INITIALIZE_QUANTITY_IN_THE_STORE;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "customerOrderLineBean")
@SessionScoped
public class CustomerOrderLineBean extends OrderLineBean implements Serializable {

    private int quantityInTheStore; //the quantity of the book that was bought that is avalibale in the store
    //'price' represent the price of 1 book
    private double price;

    public CustomerOrderLineBean() {
    }

    /*constructor with the values being given, for the use of inserting a new order:*/
    public CustomerOrderLineBean(String bookTitle, double bookPrice, int quantity,
            int quantityInTheStore, double totalPayment) {
        super(bookTitle, quantity, totalPayment);
        this.price = bookPrice;
        this.quantityInTheStore = quantityInTheStore;
    }

    public void setQuantityInTheStore(int quantityInTheStore) {
        this.quantityInTheStore = quantityInTheStore;
    }

    public int getQuantityInTheStore() {
        return quantityInTheStore;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    //***************************************************************************************************************************
    //***************************************************************************************************************************
    //***************************************************************************************************************************
    //override methods:

    /*This method initialize all the values of this object.*/
    @Override
    public void initializeLineInTheOrder() {
        super.initializeLineInTheOrder();
        quantityInTheStore = INITIALIZE_QUANTITY_IN_THE_STORE;
        price = INITIALIZE_PRICE;
    }
}

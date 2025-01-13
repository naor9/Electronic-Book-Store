/*
This Managed Bean represent a line in the order, meaning 1 book item in the 
order with the quantity of that book the customer bought.
 */
package beans.order;

import static constants.constant.INITIALIZE_QUANTITY_FOR_CUSTOMER_TO_BUY;
import static constants.constant.INITIALIZE_TOTAL_PAYMENT;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "orderLineBean")
@SessionScoped
public class OrderLineBean implements Serializable {

    private String bookTitle;
    //'quantity' is the quantity of the book that was bought:
    private int quantity;
    /*totalPayment refers to the sum the customer pay, only for that 1 book 
    (calculate with the quantity of that book the customer bought):*/
    private double totalPayment;  //price*quantity

    //constructors:
    public OrderLineBean() {
    }

    /*constructor with given veriables:*/
    public OrderLineBean(String bookTitle, int quantity, double totalPayment) {
        this.bookTitle = bookTitle;
        this.quantity = quantity;
        this.totalPayment = totalPayment;
    }

    // Getter and Setter for bookTitle
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    // Getter and Setter for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

    // Getter and Setter for totalPayment
    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        if (totalPayment >= 0) {
            this.totalPayment = totalPayment;
        } else {
            throw new IllegalArgumentException("Total payment must be non-negative.");
        }
    }

    /*This method initialize all the values of this object.*/
    public void initializeLineInTheOrder() {
        bookTitle = null;
        quantity = INITIALIZE_QUANTITY_FOR_CUSTOMER_TO_BUY;
        totalPayment = INITIALIZE_TOTAL_PAYMENT;
    }
}

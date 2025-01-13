/*
  'OrderForManagerBean' represent a view of orders from the 'Orders' table in the database,
  for the manager.
 */
package beans.order.manager;

import static beans.constantsBean.ManagerPagesBean.ALL_THE_ORDERS_AND_INFO_OF_A_CUSTOMER;
import static beans.constantsBean.ManagerPagesBean.ORDERS_INFO;
import static beans.constantsBean.ManagerPagesBean.SEARCH_CUSTOMER_FOR_CUSTOMER_ORDERS;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "orderForManagerBean")
@SessionScoped
public class OrderForManagerBean implements Serializable {

    //'order' represent the lines from the 'orders' table in the database:
    private ArrayList<ManagerOrderLineBean> order;

    public OrderForManagerBean() {
    }

    public void setOrder(ArrayList<ManagerOrderLineBean> order) {
        this.order = order;
    }

    public ArrayList<ManagerOrderLineBean> getOrder() {
        return order;
    }

    /*This method initialize all the variables of this object (there is just a veriable: 'order')*/
    public void initializeOrder() {
        order = new ArrayList<ManagerOrderLineBean>();
    }

    /*This method make this 'order' to include all the orders lines in the database
     (from 'orders' table).
     It sends the manager into the orders page.*/
    public String setOrderToBeAllOrdersLines() {
        order = DAO.orders.GetOrdersInfoFromTheDatabase.getAllTheOrders();
        if (order.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No order was found", null));
            return ORDERS_INFO;
        }
        return ORDERS_INFO;
    }
    
    /*This method make this 'order' to include all the orders of the given user name.
     It sends the manager into the page to see all those orders.*/
    public String setOrderToBeAllTheGivenUserNameOrders(String customerUserName) {
        order = DAO.orders.GetOrdersInfoFromTheDatabase.getAllTheOrdersOfTheGivenUserName(customerUserName);
        if (order.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No order was found for the user name " + customerUserName, null));
            return SEARCH_CUSTOMER_FOR_CUSTOMER_ORDERS;
        }
        return ALL_THE_ORDERS_AND_INFO_OF_A_CUSTOMER;
    }
}

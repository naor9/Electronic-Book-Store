/*
 Managed Bean representing a manager (for the manager info).
 */
package beans.manager;


import beans.book.BookBean;
import beans.book.SearchBookBean;
import static beans.constantsBean.ManagerPagesBean.MANAGER_LOG_OUT_SUCCESSFULLY;
import beans.customer.CustomerBean;
import beans.order.manager.ManagerOrderLineBean;
import beans.order.manager.OrderForManagerBean;
import beans.writer.SearchWriterBean;
import beans.writer.WriterBean;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "managerBean")
@SessionScoped
public class ManagerBean implements Serializable {

    private String managerUserName;
    private String managerPassword;
    /*'isManagerLoggedIn' will indicate if the manager logged in or not.*/
    private boolean isManagerLoggedIn = false;

    public ManagerBean() {
    }
    
    //getter and setter methods:
    public boolean getIsManagerLoggedIn() {
        return isManagerLoggedIn;
    }

    public void setIsManagerLoggedIn(boolean isManagerLoggedIn) {
        this.isManagerLoggedIn = isManagerLoggedIn;
    }
    
    public String getManagerUserName() {
        return managerUserName;
    }

    public void setManagerUserName(String managerUserName) {
        this.managerUserName = managerUserName;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }
    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //methods for log out:    
    /*This function loged out the user. It clear his data like search and manager info.*/
    public String logOut(OrderForManagerBean order, ManagerOrderLineBean lineInOrder, BookBean book,
            SearchBookBean bookSerachResult, CustomerBean customerBean, WriterBean writer, SearchWriterBean searchWriter) {
        order.initializeOrder();
        lineInOrder.initializeLineInTheOrder();
        book.initializeBookBean();
        bookSerachResult.initializeSearchBookBean();
        customerBean.initializeCustomerBean();
        writer.initializeWriterBean();
        searchWriter.initializeSearchWriterBean();
        initializeManagerBean();
        return MANAGER_LOG_OUT_SUCCESSFULLY;
    }

    //****************************************************************************************************************************
    //****************************************************************************************************************************
    //****************************************************************************************************************************

    /*This method initialize all the variables of this managerBean object.*/
    private void initializeManagerBean() {
    managerUserName = null;
    managerPassword = null;
    isManagerLoggedIn = false;
    }

}

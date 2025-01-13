/*
 This bean is responsible to add a new manager into the database.
 It saves the new manager info in 'newManager'.
 */
package beans.manager;

import DAO.Manager.GetManagerInfoFromTheDatabase;
import DAO.Manager.InsertManagerIntoTheDatabaseDAO;
import static beans.constantsBean.ManagerPagesBean.ADD_NEW_MANAGER;
import static beans.constantsBean.ManagerPagesBean.MANAGER_WAS_ADDED_SUCCESSFULLY;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "addNewManager")
@RequestScoped
public class AddNewManager implements Serializable {

    /*'newManager' represent the new manager data.*/
    private ManagerBean newManager = new ManagerBean();

    public AddNewManager() {
    }

    public ManagerBean getNewManager() {
        return newManager;
    }

    public void setNewManager(ManagerBean newManager) {
        this.newManager = newManager;
    }

    /*This method handle all the adding a new manager into the database. 
     It checks if the manager username isn't already exist.
     This method sends the user-manager (that call this method) into an appropriate
     page according to the success/fail of adding the new manager into the database.*/
    public String addNewManagerIntoTheDatabase() {
        /*The next 'if' checks if the new manager user name is already exist in the database:*/
        if (GetManagerInfoFromTheDatabase.checkIfTheManagerUserNameExistInTheDatabase(newManager.getManagerUserName())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The new manager user name is already exist, please choose "
                            + "a diffrent user name", null));
            newManager.setManagerUserName("");
            return ADD_NEW_MANAGER;
        }
        /*The next 'if' checks if the new manager was insert successfully into the database:*/
        if (InsertManagerIntoTheDatabaseDAO.addNewManagerIntoTheDatabase(newManager)) {
            return MANAGER_WAS_ADDED_SUCCESSFULLY;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Oops! something heppened! The new manager was NOT added into the database, "
                        + "please try again", null));
        return ADD_NEW_MANAGER;
    }
}

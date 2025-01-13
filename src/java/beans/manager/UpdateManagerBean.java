/*
 This bean is used as back-up when the manager wants to edit his info. 
 It is responsible to update the manager info in the database or keep the password 
 as before in case of an error.
 */
package beans.manager;

import DAO.Manager.UpdateManagerInfoDAO;
import static beans.constantsBean.ManagerPagesBean.EDIT_PASSWORD_FOR_MANAGER;
import static beans.constantsBean.ManagerPagesBean.MANAGER_PASSWORD_WAS_EDIT_SUCCESSFULLY;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "updateManagerBean")
@RequestScoped
public class UpdateManagerBean implements Serializable {
    
    /*represent the new password the manager wants to change to.*/
    private String managerNewPassword;
            

    public UpdateManagerBean() {
    }


    public void setManagerNewPassword(String managerNewPassword) {
        this.managerNewPassword = managerNewPassword;
    }

    public String getManagerNewPassword() {
        return managerNewPassword;
    }

    /*This method checks if the new manager password (represent in the variable 
     'managerNewPassword').
     It assume the new password is legit.*/
    public String changeManagerPassword(ManagerBean oldManagerInfo) {
        /*To check if there was any change in the manager password:*/
        if (managerNewPassword.equals(oldManagerInfo.getManagerPassword())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The new password is the same as before, please change at least 1 "
                            + "character in the password in order to update.", null));
            return EDIT_PASSWORD_FOR_MANAGER;
        }
        /*Add the new manager password into the database and checks if the 
         new password was added successfully:*/
        if (!UpdateManagerInfoDAO.updateTheManagerPassworInTheDatabase(
                oldManagerInfo.getManagerUserName(), managerNewPassword)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Oops, something heppened! Your password wasn't updated, please try again", null));
            return EDIT_PASSWORD_FOR_MANAGER;
        }
        oldManagerInfo.setManagerPassword(managerNewPassword);
        managerNewPassword = null;
        return MANAGER_PASSWORD_WAS_EDIT_SUCCESSFULLY;
    }
}

/*
This object represent the current date at the moment it was created
 */
package beans;

import java.time.LocalDate;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "currentDateBean")
@RequestScoped
public class CurrentDateBean {

    /*'currentYear' represent the year at the moment it was requested.*/
    private int currentYear;

    public CurrentDateBean() {
    }

    /*This method return the current Year at the moment it is asked for it.
     This method, before givin the currentYear, update the current year to be 
     the current year to make sure it is updated.*/
    public int getCurrentYear() {
        LocalDate currentDate = LocalDate.now();
        currentYear = currentDate.getYear();
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

}

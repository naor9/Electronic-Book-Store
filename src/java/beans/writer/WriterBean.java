/*
This bean represent a writer (with the values of a writer in the 'writers' table).
 */
package beans.writer;

import DAO.writers.GetWritersInfoFromDatabase;
import DAO.writers.InsertWriterIntoTheDatabaseDAO;
import DAO.writers.UpdateWriterInfoDAO;
import static beans.constantsBean.ManagerPagesBean.ADD_NEW_WRITER;
import static beans.constantsBean.ManagerPagesBean.EDIT_WRITER;
import static beans.constantsBean.ManagerPagesBean.WRITER_WAS_ADDED_SUCCESSFULLY;
import static beans.constantsBean.ManagerPagesBean.WRITER_WAS_EDIT_SUCCESSFULLY;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "writerBean")
@SessionScoped
public class WriterBean implements Serializable {

    private String writerFirstName;
    private String writerLastName;

    //constructors:
    public WriterBean() {
    }

    public WriterBean(String writerFirstName, String writerLastName) {
        this.writerFirstName = writerFirstName;
        this.writerLastName = writerLastName;
    }

    //getters and setters:
    public String getWriterFirstName() {
        return writerFirstName;
    }

    public String getWriterLastName() {
        return writerLastName;
    }

    public void setWriterFirstName(String writerFirstName) {
        this.writerFirstName = writerFirstName;
    }

    public void setWriterLastName(String writerLastName) {
        this.writerLastName = writerLastName;
    }

    /*return the writer first and last name*/
    @Override
    public String toString() {
        return writerFirstName + ", " + writerLastName;
    }
    
    /*This methods return true if the values of this writer and the given writer 
     are the same, and return false otherwise.*/
    public boolean equals(WriterBean secondWriter) {
        return (this.writerFirstName.equals(secondWriter.getWriterFirstName()))
                && (this.writerLastName.equals(secondWriter.getWriterLastName()));
    }
    
    /*This method initialize all the variables of this WriterBean object.*/
    public void initializeWriterBean() {
    writerFirstName = null;
    writerLastName = null;
    }
    
    /*This method adds this new writer into the database if the writer isn't
     already in the database.*/
    public String addNewWriterToTheDatabase() {
        /*to check if the writer data is already exist in the database:*/
        if (GetWritersInfoFromDatabase.checkIfWriterExistInTheDatabase(writerFirstName, writerLastName)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The writer: " + writerFirstName + " " + writerLastName
                            + " is already exist in the database, the writer was NOT added into the database, please try again", null));
            return ADD_NEW_WRITER; //stay in the same page
        }
        //else:
        /*The next 'if' checks if the insert was successful:*/
        if (InsertWriterIntoTheDatabaseDAO.insertANewWriterIntoTheDatabase(this)) {
            return WRITER_WAS_ADDED_SUCCESSFULLY;
        }
        //else if there was a problem in insert the new writer:
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Oops, something heppened! The writer: " + writerFirstName + " " + writerLastName
                        + " wasn't added to the database, please try again", null));
        return ADD_NEW_WRITER; //stay in the same page
    }
    
    /*This method edit the writer data in the databse*/
    public String editWriterInTheDatabase(WriterBean originalwriter) {
        /*To check if there was any change in the writer:*/
        if (this.equals(originalwriter)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No data on the writer has changed, hence no update occurred, please try again", null));
            return EDIT_WRITER;
        }
        /*to check if the writer data is already exist in the database:*/
        if (GetWritersInfoFromDatabase.checkIfWriterExistInTheDatabase(writerFirstName, writerLastName)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The writer: " + writerFirstName + " " + writerLastName
                            + " is already exist in the database, the writer was NOT edit in the the database, please try again", null));
            return EDIT_WRITER; //stay in the same page
        }
        if (!UpdateWriterInfoDAO.updateWriter(originalwriter, this)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Oops, something heppened! The writer: " + originalwriter.getWriterFirstName()
                            +" " + originalwriter.getWriterLastName()
                    + " wasn't updated, please try again", null));
            return EDIT_WRITER;
        }
        return WRITER_WAS_EDIT_SUCCESSFULLY;
    }

}

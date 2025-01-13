/*
  'SearchWriterBean' is a Managed Bean for writer search.
  This method keeps the writer data that represent the writer that
  are fit for the search result, for later use.
 */
package beans.writer;

import DAO.writers.GetWritersInfoFromDatabase;
import static beans.constantsBean.ManagerPagesBean.EDIT_WRITER;
import static beans.constantsBean.ManagerPagesBean.SEARCH_WRITER_FOR_EDIT_WRITER;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "searchWriterBean")
@SessionScoped
public class SearchWriterBean implements Serializable {

    private WriterBean writer = new WriterBean();

    public SearchWriterBean() {
    }

    public void setWriter(WriterBean writer) {
        this.writer = writer;
    }

    public WriterBean getWriter() {
        return writer;
    }

    /*This method initialize all the variables of this SearchWriterBean object.*/
    public void initializeSearchWriterBean() {
        writer = null;
    }

    /*This method search if the given writer exist in the database.
     If it does it saves that writer for later use and direct the manager into 
    "editWriter" page, and if it doesn't it gives the manager a notice and keep 
    the manager in the same page.*/
    public String searchWriterInTheDatabase(WriterBean writer) {
        if (GetWritersInfoFromDatabase.checkIfWriterExistInTheDatabase(writer.getWriterFirstName(), writer.getWriterLastName())) {
            this.writer.setWriterFirstName(writer.getWriterFirstName());
            this.writer.setWriterLastName(writer.getWriterLastName());
            return EDIT_WRITER;
        }
        //else:
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Oops, something heppened! The writer: "
                        + writer.getWriterFirstName() + " " + writer.getWriterLastName()
                        + " isn't exist in the database, please try again", null));
        return SEARCH_WRITER_FOR_EDIT_WRITER; //stay in the same page
    }

}

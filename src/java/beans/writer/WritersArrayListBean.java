/*
This class represent an array list of writers..
 */
package beans.writer;

import DAO.writers.GetWritersInfoFromDatabase;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "writersArrayListBean")
@SessionScoped
public class WritersArrayListBean implements Serializable {

    /*the next 'writers' represent the result of a query that got writers*/
    private ArrayList<WriterBean> writers = new ArrayList<WriterBean>();

    //constructor:
    public WritersArrayListBean() {
    }

    //getters and setters:
    public ArrayList<WriterBean> getWriters() {
        return writers;
    }

    public void setWriters(ArrayList<WriterBean> writers) {
        this.writers = writers;
    }

    //other methids:
    /*The next methd set the 'writers' to be with all the data of all the writers
    in the database:*/
    public ArrayList<WriterBean> setWritersToAllTheWriterInTheDatabase() {
        this.writers = GetWritersInfoFromDatabase.getAllTheWritersInTheDatabase();
        return writers;
    }

}

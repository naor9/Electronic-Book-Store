/*
    This bean is responsible for adding a new book into the database.
 */
package beans.book;

import DAO.books.GetBookInfoFromDatabaseDAO;
import DAO.books.InsertBookIntoTheDatabaseDAO;
import DAO.writers.GetWritersInfoFromDatabase;
import static beans.constantsBean.ManagerPagesBean.ADD_NEW_BOOK;
import static beans.constantsBean.ManagerPagesBean.BOOK_WAS_ADDED_SUCCESSFULLY;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


@ManagedBean(name = "addNewBookBean")
@RequestScoped
public class AddNewBookBean implements Serializable {

    
    
    public AddNewBookBean() {
    }
    
    /*This method is used when the manager wants to add a new book into the database.
     It handle all the adding of the new book, and send the manager into a page
    that declares the new book was added, or keep the manager in the same page 
    and give him a note that the book wasn't added (if there was a problem)*/ 
    public String addNewBookToTheDatabase(BookBean newBook) {
        /*to check if the writer data is in the database:*/
        if (!GetWritersInfoFromDatabase.checkIfWriterExistInTheDatabase(
                newBook.getWriterFirstName(),newBook.getWriterLastName())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The writer: " + newBook.getWriterFirstName() + ", " + newBook.getWriterLastName()
                            + " does not exist in the database, please make sure there is no typo or add the new writer into the database",
                            null));
            return ADD_NEW_BOOK;
        }
        /*The next if checks if the book is already in the store*/
        if (GetBookInfoFromDatabaseDAO.isTheBookExistInTheStore(newBook.getBookTitle())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The book: " + newBook.getBookTitle() + " is already exist in the store! The book was NOT added",
                            null));
            return ADD_NEW_BOOK;
        }
        /*The next 'if' checks if the insert was successful:*/
        if (InsertBookIntoTheDatabaseDAO.insertANewBookIntoTheDatabase(newBook)) {
            return BOOK_WAS_ADDED_SUCCESSFULLY;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Oops, something heppened! The book: " + newBook.getBookTitle()
                        + " wasn't added to the database, please try again", null));
        return ADD_NEW_BOOK;
    }
   
}

/*
   The next methods are for update the book data in the database
 */
package beans.book;

import DAO.books.GetBookInfoFromDatabaseDAO;
import DAO.books.UpdateBookInfoDAO;
import DAO.writers.GetWritersInfoFromDatabase;
import static beans.constantsBean.ManagerPagesBean.BOOK_WAS_EDIT_SUCCESSFULLY;
import static beans.constantsBean.ManagerPagesBean.EDIT_BOOK;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "editBookBean")
@RequestScoped
public class EditBookBean implements Serializable {

    public EditBookBean() {
    }

    /*This method handle the edit of the book. It gets the old info and the new info and checks if the edit is legit,
     and if it does it edit the book, and if not it gives the user an appropriate response.*/
    public String editBook(BookBean originalBookData, BookBean newBookData) {
        /*To check if there was any change in the book:*/
        if (isTheNewEditBookIsTheSameAsBeforeTheEdit(originalBookData, newBookData)) {
            return EDIT_BOOK;
        }
        /*To check if a changed title or writer is legit:*/
        if (!checksIfTheChangedDataIsLegit(originalBookData.getBookTitle(), originalBookData.getWriterFirstName(),
                originalBookData.getWriterLastName(), newBookData)) {
            return EDIT_BOOK;
        }
        if (!UpdateBookInfoDAO.updateBook(originalBookData, newBookData)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Oops, something heppened! The book: " + originalBookData.getBookTitle()
                    + " wasn't updated, please try again", null));
            return EDIT_BOOK;
        }
        return BOOK_WAS_EDIT_SUCCESSFULLY;
    }

    /*This method is part of the handle the 'edit' book.
      It checks if there was any changes in the edit command, and if so return true,
      otherwise give an appropriate response and return false.*/
    private boolean isTheNewEditBookIsTheSameAsBeforeTheEdit(BookBean originalBookData, BookBean newBookData) {
        /*To check if there was a change in the book:*/
        if (newBookData.equals(originalBookData)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No data on the book has changed, hence no update occurred, please try again", null));
            return true;
        }
        return false;
    }

    /*This method checks if the new given book data to edit is legit, and return
     true if the data can be changed or false otherwise.
     This method also give notice according to the problem that occurs (if occurs).*/
    private boolean checksIfTheChangedDataIsLegit(String oldBookTitle,
            String oldWiterFirstName, String oldWriterLastName, BookBean newBookData) {
        /*The next if checks if the title had changed, and if so make sure that
          there is no other book with that new book title:*/
        if (!newBookData.getBookTitle().equals(oldBookTitle)
                && GetBookInfoFromDatabaseDAO.isTheBookExistInTheStore(newBookData.getBookTitle())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The book title: " + newBookData.getBookTitle() + " is already exist in the store! The book was NOT"
                            + " updated, please choose another book title.", null));
            return false;
        }
        /*The next 'if' checks if the writer data changed, and if so make sure that the 
        new writer exist in the database.*/
        if (((!newBookData.getWriterFirstName().equals(oldWiterFirstName))
                || (!newBookData.getWriterLastName().equals(oldWriterLastName)))
                && (!GetWritersInfoFromDatabase.checkIfWriterExistInTheDatabase(
                        newBookData.getWriterFirstName(), newBookData.getWriterLastName()))) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The new writer: " + newBookData.getWriterFirstName() + ", " + newBookData.getWriterLastName()
                            + " is NOT exist in the database. The book was NOT updated into the database, please try again",
                            null));
            return false;
        }
        return true;
    }

}

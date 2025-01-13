/*
 The next methods are used for the book search by title (for both customer and manager)
 It is responsible to set a new "searchBookBean" with the result of the search of the book by the title.
 */
package beans.book;

import DAO.booksSearch.GetBookSearchResultByTitleDAO;
import static beans.constantsBean.CustomerPagesBean.BOOK_SEARCH_RESULT;
import static beans.constantsBean.CustomerPagesBean.NO_BOOK_WAS_FOUND;
import static beans.constantsBean.ManagerPagesBean.EDIT_BOOK;
import static beans.constantsBean.ManagerPagesBean.SEARCH_BOOK_FOR_EDIT_BOOK;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "searchABookByTitleBean")
@RequestScoped
public class SearchABookByTitleBean implements Serializable {

    //represent the book title that the user want to serach
    private String bookTitleToSearch;

    public SearchABookByTitleBean() {
    }

    //The gets and sets:
    public String getBookTitleToSearch() {
        return bookTitleToSearch;
    }

    public void setBookTitleToSearch(String bookTitleToSearch) {
        this.bookTitleToSearch = bookTitleToSearch;
    }

    //other methods:
    /*This method is for the use of a customer. 
     It search for the book title in the databse, and send the customer
     into the book info if the book was found or send the customer into a
     "no book was found" page otherwise.*/
    public String searchABookByTitleForCustomer(SearchBookBean searchBookBean) {
        //the next line is to save the result serach:
        searchBookBean.setBooksSearchResults(
                GetBookSearchResultByTitleDAO.getBooksSearchResultsByTitle(bookTitleToSearch));
        //if the next line is true then no books were found in the store:
        if (searchBookBean.getBooksSearchResults().isEmpty()) {
            return NO_BOOK_WAS_FOUND;
        }
        //else:
        //the next line is to send the user into the next page (the page with the results of the book search by title):
        return BOOK_SEARCH_RESULT;
    }

    /*This method is for the use of a manager. 
     It search for the book title in the databse, and send the manager
     into edit book page if the book was found or notice the manager that
     the book was not found and keep him in the same page otherwise.*/
    public String searchABookByTitleForBookEditForManager(SearchBookBean searchBookBean) {
        //the next line is to save the result of the book serch:
        searchBookBean.setBooksSearchResults(
                GetBookSearchResultByTitleDAO.getBooksSearchResultsByTitle(bookTitleToSearch));
        //if the next line is true then no books were found in the store:
        if (searchBookBean.getBooksSearchResults().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "The book: " + bookTitleToSearch
                            + " isn't exist in the database, please try again", null));
            return SEARCH_BOOK_FOR_EDIT_BOOK;
        }
        //else:
        //the next line is to send the manager into the next page to edit the book:
        return EDIT_BOOK;
    }
}

/*
  Managed Bean for book/s search, for both a search with title or a search with 
  category/ies. This method keeps the "ArrayList<BookBean> booksSearchResults" that represent all the books that
  are fit for the search result, for later use.
 */
package beans.book;

import DAO.books.GetBookInfoFromDatabaseDAO;
import static constants.constant.INITIALIZE_INDEX_IN_ARRAY;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "searchBookBean")
@SessionScoped
public class SearchBookBean implements Serializable {

    /*the next 'booksSearchResults' represent all the books that are the result 
      of the search (for either title or category):*/
    private ArrayList<BookBean> booksSearchResults = new ArrayList<BookBean>();

    /*'indexForBooksSearchResults' will be used after a book from the search result
    was chosen. It indicates the index of the book that was chosen (for example for 
    more details about the book).*/
    private int indexForBooksSearchResults;

    public SearchBookBean() {
    }

    public ArrayList<BookBean> getBooksSearchResults() {
        return booksSearchResults;
    }

    public void setBooksSearchResults(ArrayList<BookBean> booksSearchResults) {
        this.booksSearchResults = booksSearchResults;
    }

    public int getIndexForBooksSearchResults() {
        return indexForBooksSearchResults;
    }

    public void setIndexForBooksSearchResults(int indexForBooksSearchResults) {
        this.indexForBooksSearchResults = indexForBooksSearchResults;
    }

    //*****************************************************************************************************************************
    //*****************************************************************************************************************************
    //*****************************************************************************************************************************
    //other methods:
    /*This method initialize all the values of this object.*/
    public void initializeSearchBookBean() {
        booksSearchResults = new ArrayList<BookBean>();
        indexForBooksSearchResults = INITIALIZE_INDEX_IN_ARRAY;
    }

    /*This method is used to navigate into the book info page.
    It gets a BookBean object and find that book in the books search results
    (the arraylist).*/
    public String goToBookInfo(BookBean book) {
        /*The next 'for' find the bookBean in the arraylist:*/
        for (int i = 0; i < booksSearchResults.size(); i++) {
            /*the next 'if' checks if the bookBean in the arraylist and the given bookBean
            are equals:*/
            if (booksSearchResults.get(i).getBookTitle().equals(book.getBookTitle())) {
                this.indexForBooksSearchResults = i;
                return beans.constantsBean.CustomerPagesBean.BOOK_INFO;
            }
        }
        /*In case of an error, should not reach here.*/
        return null;
    }

    /*initialize 'booksSearchResults' to include all the books in the store.*/
    public void setBooksSearchResultsToAllTheBooks() {
        booksSearchResults = GetBookInfoFromDatabaseDAO.getAllTheBooksInTheDB();
    }
}

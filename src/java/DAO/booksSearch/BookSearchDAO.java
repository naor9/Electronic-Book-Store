/*
This is the DAO for all the methods that is being used for both search types of 
books (title and category/ies).
 */
package DAO.booksSearch;

import DAO.books.CreateBookBeanDAO;
import beans.book.BookBean;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class BookSearchDAO {

//the next method is for all the books search types (title and category):

    /*This method gets the result of the search of the books as CachedRowSet, 
    and returns the result of the search of all the books that were found 
    as an ArrayList of Books, so each bookBean is beaing shown only once and with all its
    categories.*/
    public static ArrayList<BookBean> getTheResultOfTheBooksSearch(CachedRowSet searchResultCachedRowSet) {
        ArrayList<BookBean> booksSearchResults = new ArrayList<BookBean>();
        try {
            //if the next 'if' is true then the search didn't find any books:
            if (!searchResultCachedRowSet.isBeforeFirst()) {
                return booksSearchResults;
            }
            //else:
            while (searchResultCachedRowSet.next()) {
                BookBean nextBook = CreateBookBeanDAO.getANewBookBeanFromCachedRowSet(searchResultCachedRowSet);
                booksSearchResults.add(nextBook);
            }
            return booksSearchResults;
        } catch (SQLException exception) {
            handleCatchWithSQLException(exception);
            return null;  //in case of an error.
        }
    }
}

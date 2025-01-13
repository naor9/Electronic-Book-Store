/*
 Here are all the methods for the search of a book in the database with a title search.
 */
package DAO.booksSearch;

import static StringHelper.StringForSQLQuery.makeEachApostropheIntoTwoApostrophe;
import beans.book.BookBean;
import static constants.ConstantsForTablesInTheDatabase.BOOKCATEGORY_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.BOOKS_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.BOOK_TITLE;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import utils.ConnectionToDatabase;

public class GetBookSearchResultByTitleDAO {

    /*This method return the result of the search of the user
    according to the title it gets.*/
    public static ArrayList<BookBean> getBooksSearchResultsByTitle(String bookTitleToSearch) {
        CachedRowSet searchResultCachedRowSet = getCachedRowSetForBookTitleSearch(bookTitleToSearch);
        ArrayList<BookBean> booksSearchResultsArrayList = BookSearchDAO.getTheResultOfTheBooksSearch(searchResultCachedRowSet);
        return booksSearchResultsArrayList;
    }

    /*This method returns a CachedRowSet object that is the result of the search in the 
    database for the book with the given title (from the user).*/
    private static CachedRowSet getCachedRowSetForBookTitleSearch(String bookTitleToSearch) {
        /*In the next line, the "order by" is to organize the books by there names for 
        later use in the creation of the books categories for the  ArrayList book result.*/
        bookTitleToSearch = makeEachApostropheIntoTwoApostrophe(bookTitleToSearch);
        String query = "SELECT * FROM " + BOOKS_TABLE_NAME + " NATURAL JOIN "
                + BOOKCATEGORY_TABLE_NAME + " WHERE "
                + BOOK_TITLE + " = " + "'" + bookTitleToSearch + "'" + " ORDER BY " + BOOK_TITLE;
        CachedRowSet searchResultCachedRowSet = ConnectionToDatabase.getCachedRowSetFromQuery(query);
        return searchResultCachedRowSet;
    }
}

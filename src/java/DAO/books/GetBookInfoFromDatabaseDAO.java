/*
Here are the methods that handle getting info on book from the database.
 */
package DAO.books;

import DAO.booksSearch.GetBooksSearchResultByCategoriesDAO;
import static StringHelper.StringForSQLQuery.makeEachApostropheIntoTwoApostrophe;
import beans.book.BookBean;
import beans.constantsBean.CategoryBean;
import static constants.ConstantsForTablesInTheDatabase.BOOKS_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.BOOK_TITLE;
import static constants.ConstantsForTablesInTheDatabase.QUANTITY_IN_THE_STORE;
import static constants.constant.ERROR_IN_GETTING_QUANTITY_IN_THE_STORE_FROM_THE_DATABASE;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import utils.ConnectionToDatabase;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class GetBookInfoFromDatabaseDAO {

    /*gets book title and return the book quantity in the store*/
    public static synchronized int getQuantityInTheStore(String bookTitle) {
        bookTitle = makeEachApostropheIntoTwoApostrophe(bookTitle);
        String queryToGetQauntityInTheStore = "SELECT " + QUANTITY_IN_THE_STORE + " FROM "
                + BOOKS_TABLE_NAME + " WHERE " + BOOK_TITLE + " = " + "'" + bookTitle + "'";
        CachedRowSet resultQuantityInTheStore = ConnectionToDatabase.getCachedRowSetFromQuery(queryToGetQauntityInTheStore);
        try {
            resultQuantityInTheStore.next();
            return resultQuantityInTheStore.getInt(1);
        } catch (SQLException exception) {
            handleCatchWithSQLException(exception);
            return ERROR_IN_GETTING_QUANTITY_IN_THE_STORE_FROM_THE_DATABASE;
        }
    }

    /*return true if the given book exit in the store, and false otherwise.*/
    public static boolean isTheBookExistInTheStore(String bookTitle) {
        bookTitle = makeEachApostropheIntoTwoApostrophe(bookTitle);
        String querySearchBookTitle = "SELECT * FROM " + BOOKS_TABLE_NAME + " WHERE "
                + BOOK_TITLE + " = " + "'" + bookTitle + "'";
        CachedRowSet resultSearchBookTitle = ConnectionToDatabase.getCachedRowSetFromQuery(querySearchBookTitle);
        try {
            if (resultSearchBookTitle.next()) {
                return true;
            }
        } catch (SQLException exception) {
            handleCatchWithSQLException(exception);
        }
        return false;
    }

    /*return ArrayList<BookBean> that represent all the books in the database. */
    public static ArrayList<BookBean> getAllTheBooksInTheDB() {
        return GetBooksSearchResultByCategoriesDAO.getBooksSearchResultsByCategories(CategoryBean.getCategories());
    }
}

/*
Here are the methods that handle getting info on book category from the database,
from the "BookCategory" table
 */
package DAO.bookCategory;

import static StringHelper.StringForSQLQuery.makeEachApostropheIntoTwoApostrophe;
import static constants.ConstantsForTablesInTheDatabase.BOOKCATEGORY_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.IN_BOOKCATEGORY_TABLE_BOOK_TITLE;
import static constants.ConstantsForTablesInTheDatabase.IN_BOOKCATEGORY_TABLE_CATEGORY;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import utils.ConnectionToDatabase;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class GetBookCategoryInfoFromTheDatabase {

    /*This method gets a book title and return all the book categories in the 
     'bookCategory' table in the database.*/
    public static ArrayList<String> getAllTheBookCategoriesInArrayList(String bookTitle) {
        String allCategoriesQuery;
        allCategoriesQuery = getQueryForAllCategoriesFromBookTitle(bookTitle);
        CachedRowSet allCategoriesCachedRowSet =  ConnectionToDatabase.getCachedRowSetFromQuery(allCategoriesQuery);
        return getAllCategoriesFromCachedRowSet(allCategoriesCachedRowSet);
    }

    /*This method gets a CachedRowSet with the result of all the categories of a 
     book title, and return all those categories as an ArrayList<String> of categories.*/
    private static ArrayList<String> getAllCategoriesFromCachedRowSet(CachedRowSet allCategoriesCachedRowSet) {
        ArrayList<String> categories = new ArrayList<String>();
        try {
            while (allCategoriesCachedRowSet.next()) {
                categories.add(allCategoriesCachedRowSet.getString(1));
            }
        } catch (SQLException exception) {
            handleCatchWithSQLException(exception);
        }
        return categories;
    }

    /*return a query for the search of all the categories of a book (by book title)
     in the "BookCategory" table.*/
    private static String getQueryForAllCategoriesFromBookTitle(String bookTitle) {
        bookTitle = makeEachApostropheIntoTwoApostrophe(bookTitle);
        return "SELECT " + IN_BOOKCATEGORY_TABLE_CATEGORY + " FROM " + BOOKCATEGORY_TABLE_NAME
                + " WHERE " + IN_BOOKCATEGORY_TABLE_BOOK_TITLE + " = '" + bookTitle + "'";
    }
}
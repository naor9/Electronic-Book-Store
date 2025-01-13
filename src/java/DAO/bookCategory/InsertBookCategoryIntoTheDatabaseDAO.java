/*
 Here are all the methods that handle insert a new book category into the 
 "BookCategory" table in the database
 */
package DAO.bookCategory;

import static StringHelper.StringForSQLQuery.makeEachApostropheIntoTwoApostrophe;
import static constants.ConstantsForTablesInTheDatabase.BOOKCATEGORY_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.IN_BOOKCATEGORY_TABLE_BOOK_TITLE_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_BOOKCATEGORY_TABLE_CATEGORY_TITLE_COULMN_NUMBER;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsertBookCategoryIntoTheDatabaseDAO {

    /*This method return a query for the use of a statement to insert a new book 
     category line.
     It adds only 1 book category.*/
    public static String getStatmentQueryForInsertNewBookCategory(String bookTitle, String category) {
        bookTitle = makeEachApostropheIntoTwoApostrophe(bookTitle);
        category = makeEachApostropheIntoTwoApostrophe(category);
        return "INSERT INTO " + BOOKCATEGORY_TABLE_NAME
                + " (booktitle, category)"
                + " VALUES (" + "'" + bookTitle + "'" + ", " + "'" + category + "')";
    }

    /*This method execute a query command for adding 
     a new book categories into the 'BookCategories' table.*/
    public static synchronized void executeNewBookCategoriesInBookCategoryTable(String bookTitle,
            ArrayList<String> categories, Connection connection) throws SQLException {
        String insertBookCategoryQuery = getPreparedStatementQueryForInsertNewBookCategory();
        PreparedStatement preparedStatementForInsertBookCategory = connection.prepareStatement(insertBookCategoryQuery);
        setAndExecutePreparedStatementForBookCategoryInsertCommand(preparedStatementForInsertBookCategory, bookTitle,
                categories);
    }

    /*This method return a query for the use of PreparedStatement to insert a 
     new book category.
     It adds only 1 book category.*/
    private static String getPreparedStatementQueryForInsertNewBookCategory() {
        return "INSERT INTO " + BOOKCATEGORY_TABLE_NAME
                + " (booktitle, category)"
                + " VALUES (?, ?)";
    }

    /*This method gets a PreparedStatement for adding a new book and category 
      line into the 'bookCategory' table, and set and execute it with the book 
      title and the categories this method also gets.*/
    private static void setAndExecutePreparedStatementForBookCategoryInsertCommand(
            PreparedStatement preparedStatementForInsertBookCategory, String bookTitle, ArrayList<String> categories)
            throws SQLException {
        for (String category : categories) {
            preparedStatementForInsertBookCategory.setString(IN_BOOKCATEGORY_TABLE_BOOK_TITLE_COULMN_NUMBER, bookTitle);
            preparedStatementForInsertBookCategory.setString(IN_BOOKCATEGORY_TABLE_CATEGORY_TITLE_COULMN_NUMBER, category);
            preparedStatementForInsertBookCategory.execute();
        }
    }

}
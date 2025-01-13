/*
 Here are all the methods for the search of a book in the database with a category/ies
 search.
 */
package DAO.booksSearch;

import static DAO.booksSearch.BookSearchDAO.getTheResultOfTheBooksSearch;
import beans.book.BookBean;
import static constants.ConstantsForTablesInTheDatabase.BOOKCATEGORY_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.BOOKS_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.BOOK_CATEGORY;
import static constants.ConstantsForTablesInTheDatabase.BOOK_TITLE;
import constants.constant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import utils.ConnectionToDatabase;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;


public class GetBooksSearchResultByCategoriesDAO {

    /*This method return the result of the search of the user according to the 
    categoies that the user chose before. 'numberOfCategoriesThatWereChosen' supposed to be at least 1.*/
    public static ArrayList<BookBean> getBooksSearchResultsByCategories(String[] booksCategoriesToSearch) {
        String query = getQueryForBooksSearchByCategory(booksCategoriesToSearch.length);
        try {
            Connection connection = DriverManager.getConnection(constant.DB_URL, constant.DB_USER, constant.DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query);
            setStatementForSearchABookByACategory(statement, booksCategoriesToSearch, booksCategoriesToSearch.length);
            ResultSet resultSet = statement.executeQuery();
            CachedRowSet searchResultsCachedRowSet = ConnectionToDatabase.getCachedRowSetFromResultSet(resultSet);
            //In the next line, 'booksCategoriesToSearch' represent the search result:
            ArrayList<BookBean> booksSearchResultsArrayList = getTheResultOfTheBooksSearch(searchResultsCachedRowSet);
            return booksSearchResultsArrayList;
        } catch (SQLException exception) {
            handleCatchWithSQLException(exception);
        }
        return null;  //in case of an error.
    }

    /*This method returns the query that will be used with the database to get the books search
      result by categories. 
      numberOfCategoriesThatWereChosen is the number of categories the user chose for the search 
      and it suppose to be at least 1 (this method assume it).*/
    private static String getQueryForBooksSearchByCategory(int numberOfCategoriesThatWereChosen) {
        /*the next line,I added to the string category='?' for the first category (that is supposed to be 
        in 'booksCategoriesToSearch').*/
        String query = "SELECT * FROM " + BOOKS_TABLE_NAME + " NATURAL JOIN "
                + BOOKCATEGORY_TABLE_NAME + " WHERE " + BOOK_CATEGORY + " = ?";
        String tempString;
        for (int i = 1; i < numberOfCategoriesThatWereChosen; i++) { //starts from 1 becasue I added the first category in the first line code of this method.
            tempString = " OR " + BOOK_CATEGORY + " = ?";
            query += tempString;
        }
        /*In the next line, the "order by" is to organize the books by there names for 
        later use in the creation of the books categories for the  ArrayList book result.*/
        query += " ORDER BY " + BOOK_TITLE;
        return query;
    }

    /*This method set the statement that will be used with the database to get the books search
        result by categories. 
      It gets 'numberOfCategoriesThatWereChosen' as input, and it is the number 
      of categories the user chose for the search and it suppose to be at least 
      1 (this method assume it).*/
    private static void setStatementForSearchABookByACategory(PreparedStatement statement,
            String[] booksCategoriesToSearch, int numberOfCategoriesThatWereChosen) throws SQLException {
        for (int i = 0; i < numberOfCategoriesThatWereChosen; i++) {
            statement.setString(i + 1, booksCategoriesToSearch[i]);
        }
    }
}

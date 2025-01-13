/*
 Here are all the methods that handle insert a new book into the database
 */
package DAO.books;

import DAO.bookCategory.InsertBookCategoryIntoTheDatabaseDAO;
import beans.book.BookBean;
import static constants.ConstantsForTablesInTheDatabase.BOOKS_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.BOOK_IMG_URL_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.BOOK_TITLE_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_BOOKS_TABLE_WRITER_FIRST_NAME_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_BOOKS_TABLE_WRITER_LAST_NAME_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.PAGE_NUMBER_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.PRICE_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.PUBLISH_YEAR_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.QUANTITY_IN_THE_STORE_COULMN_NUMBER;
import static constants.constant.DB_PASSWORD;
import static constants.constant.DB_URL;
import static constants.constant.DB_USER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class InsertBookIntoTheDatabaseDAO {

    /*This method insert a new given BookBean into the database.
     It assumes the data of the book is correct.
     It return true if it succeeded and false otherwise.*/
    public static synchronized boolean insertANewBookIntoTheDatabase(BookBean newBook) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            try {
                /*The next line is to make sure that all the books categories are inserted:*/
                connection.setAutoCommit(false);
                addToBatchNewBookInBooksTable(connection, newBook);
                /*adding the categories into 'BookCategory' table:*/
                InsertBookCategoryIntoTheDatabaseDAO.executeNewBookCategoriesInBookCategoryTable(
                        newBook.getBookTitle(), newBook.getCategories(), connection);
                connection.commit();
                return true;
            } catch (SQLException exeption) {
                handleCatchWithSQLException(exeption);
                connection.rollback();
                return false;  // if could't update the database.
            }
        } catch (SQLException exeption) {
            handleCatchWithSQLException(exeption);
            return false;  // if could't make connection to the database.
        }
    }

    /*This method add to the batch the insert command for the given book.
     This method assumes that BookBean isn't null.*/
    public static void setAndAddToBatchPreparedStatementForBooksInsertCommand(
            PreparedStatement preparedStatementForInsertBook, BookBean newBook) throws SQLException {
        preparedStatementForInsertBook.setString(BOOK_TITLE_COULMN_NUMBER, newBook.getBookTitle());
        preparedStatementForInsertBook.setInt(PUBLISH_YEAR_COULMN_NUMBER, newBook.getPublishYear());
        preparedStatementForInsertBook.setString(IN_BOOKS_TABLE_WRITER_FIRST_NAME_COULMN_NUMBER, newBook.getWriterFirstName());
        preparedStatementForInsertBook.setString(IN_BOOKS_TABLE_WRITER_LAST_NAME_COULMN_NUMBER, newBook.getWriterLastName());
        preparedStatementForInsertBook.setInt(PAGE_NUMBER_COULMN_NUMBER, newBook.getPageNumber());
        preparedStatementForInsertBook.setString(BOOK_IMG_URL_COULMN_NUMBER, newBook.getBookImageUrl());
        preparedStatementForInsertBook.setDouble(PRICE_COULMN_NUMBER, newBook.getPrice());
        preparedStatementForInsertBook.setInt(QUANTITY_IN_THE_STORE_COULMN_NUMBER, newBook.getQuantityInTheStore());
        preparedStatementForInsertBook.addBatch();
    }

    /*return a string that is an insert command for prepered statement for new book*/
    public static String getQueryForInsertNewBook() {
        return "INSERT INTO " + BOOKS_TABLE_NAME
                + " (booktitle, publishyear, writerfirstname, writerlastname,"
                + "pagenumber, bookimageurl, price, quantityinthestore)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }


    /*This method handle the adding to the batch the query command for adding 
     a new book into the 'books' table.*/
    private static void addToBatchNewBookInBooksTable(Connection connection, BookBean newBook) throws SQLException {
        String insertBookQuery = getQueryForInsertNewBook();
        PreparedStatement preparedStatementForInsertBook = connection.prepareStatement(insertBookQuery);
        setAndAddToBatchPreparedStatementForBooksInsertCommand(preparedStatementForInsertBook, newBook);
        preparedStatementForInsertBook.execute();
    }
}

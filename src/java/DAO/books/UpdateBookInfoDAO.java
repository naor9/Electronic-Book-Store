/*
 Here are all the method that handle the updating of a book in the database
 */
package DAO.books;

import DAO.bookCategory.InsertBookCategoryIntoTheDatabaseDAO;
import static StringHelper.StringForSQLQuery.makeEachApostropheIntoTwoApostrophe;
import beans.book.BookBean;
import beans.constantsBean.CategoryBean;
import static constants.ConstantsForTablesInTheDatabase.BOOKS_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.BOOK_IMAGE_URL;
import static constants.ConstantsForTablesInTheDatabase.BOOK_PAGE_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.BOOK_PRICE;
import static constants.ConstantsForTablesInTheDatabase.BOOK_PUBLISH_YEAR;
import static constants.ConstantsForTablesInTheDatabase.BOOK_TITLE;
import static constants.ConstantsForTablesInTheDatabase.BOOK_WRITER_FIRST_NAME;
import static constants.ConstantsForTablesInTheDatabase.BOOK_WRITER_LAST_NAME;
import static constants.ConstantsForTablesInTheDatabase.QUANTITY_IN_THE_STORE;
import constants.constant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class UpdateBookInfoDAO {

    /*This method gets a new book info and an old book info and update the new 
     book info instead of the old one. 
     This method assume the new book writer exist in 'writers' table and the new book title
     is either the same as the old book title or a new book title that doesn't
     apear in the 'books' table in the DB.*/
    public static synchronized boolean updateBook(BookBean oldBook, BookBean newBook) {
        try {
            Connection connection = DriverManager.getConnection(constant.DB_URL, constant.DB_USER, constant.DB_PASSWORD);
            try {
                /*The next line is to make sure that all the books categories are updated:*/
                connection.setAutoCommit(false);
                Statement statementForUpdateBook = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                /*first add to the batch the new book title for the later use
                 of the rest of the update queries:*/
                updateNewTitle(statementForUpdateBook, oldBook.getBookTitle(), newBook.getBookTitle());
                udtadeStringValuesOfTheBook(statementForUpdateBook, oldBook, newBook);
                udtadeIntValuesOfTheBook(statementForUpdateBook, oldBook, newBook);
                udtadeDoubleValuesOfTheBook(statementForUpdateBook, oldBook, newBook);
                //updating the categories:
                udtadeTheCategoriesOfTheBook(statementForUpdateBook, oldBook.getCategories(), newBook.getCategories(),
                        newBook);
                statementForUpdateBook.executeBatch();
                connection.commit();
                return true;
            } catch (SQLException exeption) {
                connection.rollback();
                handleCatchWithSQLException(exeption);
                return false;  // if could't update the database.
            }
        } catch (SQLException exeption) {
            handleCatchWithSQLException(exeption);
            return false;  // if could't make connection to the database.
        }
    }

    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //methods for handle edit the book title:
    /*This method handle the update of the book title and adds it to the batch*/
    private static void updateNewTitle(Statement statementForUpdateBook,
            String oldBooktitle, String newBookTitle) throws SQLException {
        if (!oldBooktitle.equals(newBookTitle)) {
            updateBookWithGivenStringValue(statementForUpdateBook, BOOK_TITLE,
                    newBookTitle, oldBooktitle);
        }
    }

    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //methods for handle update a new writer for a book:
    /*This method handle the update of a new writer (first+last names) and adds it to the batch*/
    private static void updateNewWriter(Statement statementForUpdateBook,
            String newWriterFirstName, String newWriterLastName, String bookTitle) throws SQLException {
        String updateNewWriterQuery = getQueryForUdpateWriter(newWriterFirstName, newWriterLastName, bookTitle);
        statementForUpdateBook.addBatch(updateNewWriterQuery);
    }

    /*return a String that is the query for update a new writer in the database.*/
    private static String getQueryForUdpateWriter(String writerFirstName,
            String writerLastName, String bookTitle) {
        writerFirstName = makeEachApostropheIntoTwoApostrophe(writerFirstName);
        writerLastName = makeEachApostropheIntoTwoApostrophe(writerLastName);
        return "UPDATE " + BOOKS_TABLE_NAME + " SET " + BOOK_WRITER_FIRST_NAME
                + " = '" + writerFirstName + "', " + BOOK_WRITER_LAST_NAME + " = '" + writerLastName + "' WHERE "
                + BOOK_TITLE + " = " + "'" + bookTitle + "'";
    }

    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //methods for handeling the update of String types of 'book':
    /*This method return the query for the update in the 'books' table
      for the given coulmn name from String type exept bookTitle, WriterFisrtName and WriterLastName.*/
    private static String getQueryForUdpateGivenStringTypeCoulmn(String columnName,
            String bookTitle, String newBookInfo) {
        bookTitle = makeEachApostropheIntoTwoApostrophe(bookTitle);
        newBookInfo = makeEachApostropheIntoTwoApostrophe(newBookInfo);
        return "UPDATE " + BOOKS_TABLE_NAME + " SET " + columnName + " = "
                + "'" + newBookInfo + "'" + " WHERE " + BOOK_TITLE + " = " + "'"
                + bookTitle + "'";
    }

    /*This method excecute the update of all the values from type string in BookBean.
     It assumes the given old book title exist in the database.
     It return true if it succeeded and false otherwise.*/
    private static void udtadeStringValuesOfTheBook(Statement statementForUpdateBook,
            BookBean oldBook, BookBean newBook) throws SQLException {
        if (!oldBook.getBookImageUrl().equals(newBook.getBookImageUrl())) {
            updateBookWithGivenStringValue(statementForUpdateBook, BOOK_IMAGE_URL,
                    newBook.getBookImageUrl(), newBook.getBookTitle());
        }
        if (!oldBook.getWriterFirstName().equals(newBook.getWriterFirstName())
                || !oldBook.getWriterLastName().equals(newBook.getWriterLastName())) {
            updateNewWriter(statementForUpdateBook, newBook.getWriterFirstName(),
                    newBook.getWriterLastName(), newBook.getBookTitle());
        }
    }

    /*This method gets a column from String type in 'books' table, and the new
     value of the book, and uptade the book into that value. */
    public static void updateBookWithGivenStringValue(Statement statementForUpdateBook, String columnName,
            String newBookInfo, String BookTitle) throws SQLException {
        String updateQuery = getQueryForUdpateGivenStringTypeCoulmn(columnName, BookTitle, newBookInfo);
        statementForUpdateBook.addBatch(updateQuery);
    }

    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //methods for handeling the update of double types of 'book':
    /*This method return the query for the update in the 'books' table
      for the given coulmn name for DOUBLE type.*/
    private static String getQueryForUdpateGivenDoubleTypeCoulmn(String columnName,
            String newBookTitle, double newBookInfo) {
        newBookTitle = makeEachApostropheIntoTwoApostrophe(newBookTitle);
        return "UPDATE " + BOOKS_TABLE_NAME + " SET " + columnName + " = "
                + newBookInfo + " WHERE " + BOOK_TITLE + " = " + "'"
                + newBookTitle + "'";
    }

    /*This method excecute the update of all the values from type double in BookBean.*/
    private static void udtadeDoubleValuesOfTheBook(Statement statementForUpdateBook,
            BookBean oldBook, BookBean newBook) throws SQLException {
        if (oldBook.getPrice() != (newBook.getPrice())) {
            updateBookWithGivenDoubleValue(statementForUpdateBook, BOOK_PRICE,
                    newBook.getPrice(), newBook.getBookTitle());
        }
    }

    /*This method gets a column from double type in 'books' table, and the new
     value of the book, and uptade the book into that value. */
    private static void updateBookWithGivenDoubleValue(Statement statementForUpdateBook, String columnName,
            double newBookInfo, String newBookTitle) throws SQLException {
        String updateQuery = getQueryForUdpateGivenDoubleTypeCoulmn(columnName, newBookTitle, newBookInfo);
        statementForUpdateBook.addBatch(updateQuery);
    }
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //methods for handeling the update of int types of 'book':

    /*This method return the query for the update in the 'books' table
      for the given coulmn name for int type.*/
    private static String getQueryForUdpateGivenIntTypeCoulmn(String columnName,
            String newBookTitle, int newBookInfo) {
        newBookTitle = makeEachApostropheIntoTwoApostrophe(newBookTitle);
        return "UPDATE " + BOOKS_TABLE_NAME + " SET " + columnName + " = "
                + newBookInfo + " WHERE " + BOOK_TITLE + " = " + "'"
                + newBookTitle + "'";
    }

    /*This method excecute the update of all the values from type int in BookBean.*/
    private static void udtadeIntValuesOfTheBook(Statement statementForUpdateBook, BookBean oldBook,
            BookBean newBook) throws SQLException {
        if (oldBook.getPublishYear() != (newBook.getPublishYear())) {
            updateBookWithGivenIntValue(statementForUpdateBook, BOOK_PUBLISH_YEAR,
                    newBook.getPublishYear(), newBook.getBookTitle());
        }
        if (oldBook.getPageNumber() != (newBook.getPageNumber())) {
            updateBookWithGivenIntValue(statementForUpdateBook, BOOK_PAGE_NUMBER,
                    newBook.getPageNumber(), newBook.getBookTitle());
        }
        if (oldBook.getQuantityInTheStore() != (newBook.getQuantityInTheStore())) {
            updateBookWithGivenIntValue(statementForUpdateBook, QUANTITY_IN_THE_STORE,
                    newBook.getQuantityInTheStore(), newBook.getBookTitle());
        }
    }

    /*This method gets a column from int type in 'books' table, and the new
     value of the book, and uptade the book into that value. */
    private static void updateBookWithGivenIntValue(Statement statementForUpdateBook, String columnName,
            int newBookInfo, String newBookTitle) throws SQLException {
        String updateQuery = getQueryForUdpateGivenIntTypeCoulmn(columnName, newBookTitle, newBookInfo);
        statementForUpdateBook.addBatch(updateQuery);
    }
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //methods for handeling the update of 'categories' of 'book':

    /*This method excecute the update for the categories of the book.*/
    private static void udtadeTheCategoriesOfTheBook(Statement StatementForInsertBook,
            ArrayList<String> oldBookCategories, ArrayList<String> newBookCategories, BookBean newBook) throws SQLException {
        String insertQuery, deleteQuery;
        for (String category : CategoryBean.getCategories()) {
            /*the next 'if' checks if a category was deleted:*/
            if (oldBookCategories.contains(category) && !newBookCategories.contains(category)) {
                deleteQuery = DeleteBookCategoryDAO.getQueryForDeletingBookCategory(newBook.getBookTitle(), category);
                StatementForInsertBook.addBatch(deleteQuery);
            } else {
                /*the next 'if' checks if a new category was added:*/
                if (!oldBookCategories.contains(category) && newBookCategories.contains(category)) {
                    insertQuery = InsertBookCategoryIntoTheDatabaseDAO.getStatmentQueryForInsertNewBookCategory(
                            newBook.getBookTitle(), category);
                    StatementForInsertBook.addBatch(insertQuery);
                }
            }
        }
    }
}

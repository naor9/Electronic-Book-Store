/*
 This class is responsible for creating a new BookBean (for example, from CachedRowSet object).
 */
package DAO.books;

import DAO.booksSearch.BookSearchDAO;
import beans.book.BookBean;
import static constants.ConstantsForTablesInTheDatabase.BOOK_IMG_URL_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.BOOK_TITLE_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_BOOKS_NETURAL_JOIN_WITH_BOOKCATEGORY_CATEGORY_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_BOOKS_TABLE_WRITER_FIRST_NAME_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_BOOKS_TABLE_WRITER_LAST_NAME_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.PAGE_NUMBER_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.PRICE_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.PUBLISH_YEAR_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.QUANTITY_IN_THE_STORE_COULMN_NUMBER;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;

public class CreateBookBeanDAO {

    /*This method returns a new book with all the categories for that 1 book combined 
    to 1 ArrayList<String> (with all the categories of that book).
    The result this method is given is supposed to be in the order of the book title.
    This method assume there is a book in the result !*/
    public static BookBean getANewBookBeanFromCachedRowSet(CachedRowSet searchResultCachedRowSet) {
        ArrayList<String> categories = new ArrayList<String>();
        try {
            //getting the values for the new BookBEan from the query:
            String firstBookTitle = searchResultCachedRowSet.getString(BOOK_TITLE_COULMN_NUMBER);
            int publishYear = searchResultCachedRowSet.getInt(PUBLISH_YEAR_COULMN_NUMBER);
            String writerFirstName = searchResultCachedRowSet.getString(IN_BOOKS_TABLE_WRITER_FIRST_NAME_COULMN_NUMBER);
            String writerLastName = searchResultCachedRowSet.getString(IN_BOOKS_TABLE_WRITER_LAST_NAME_COULMN_NUMBER);
            String nextCategory = searchResultCachedRowSet.getString(IN_BOOKS_NETURAL_JOIN_WITH_BOOKCATEGORY_CATEGORY_COULMN_NUMBER);
            categories.add(nextCategory);
            int pageNumber = searchResultCachedRowSet.getInt(PAGE_NUMBER_COULMN_NUMBER);
            String bookImageUrl = searchResultCachedRowSet.getString(BOOK_IMG_URL_COULMN_NUMBER);
            double price = searchResultCachedRowSet.getDouble(PRICE_COULMN_NUMBER);
            int quantityInTheStore = searchResultCachedRowSet.getInt(QUANTITY_IN_THE_STORE_COULMN_NUMBER);
            categories = getCategoriesForTheNewBookBean(categories, firstBookTitle, searchResultCachedRowSet);
            searchResultCachedRowSet.previous(); //to come beck to the bookBean that wasn't added yet.
            return new BookBean(firstBookTitle, publishYear, writerFirstName, writerLastName, categories,
                    pageNumber, bookImageUrl, price, quantityInTheStore);
        } catch (SQLException ex) {
            Logger.getLogger(BookSearchDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*This method returns the categories for the new bookBean.*/
    private static ArrayList<String> getCategoriesForTheNewBookBean(ArrayList<String> categories, 
            String firstBookTitle, CachedRowSet searchResultCachedRowSet) throws SQLException {
        /*In the next line, 'secondBookTitle' will indicate that it reached the next 
        book and I finished to updated all the last book categories:*/
        String secondBookTitle, nextCategory;
        while (searchResultCachedRowSet.next()) {
            secondBookTitle = searchResultCachedRowSet.getString(BOOK_TITLE_COULMN_NUMBER);
            /*If the next line is true then I finished to update all the book categories,
                and reached to the next book:*/
            if (!secondBookTitle.equals(firstBookTitle)) {
                return categories;
            }//else:
            nextCategory = searchResultCachedRowSet.getString(IN_BOOKS_NETURAL_JOIN_WITH_BOOKCATEGORY_CATEGORY_COULMN_NUMBER);
            categories.add(nextCategory);
        }
        return categories;
    }
}

/*
 This method handle deleting info from the "BookCategory" table in the database.
 */
package DAO.books;

import static StringHelper.StringForSQLQuery.makeEachApostropheIntoTwoApostrophe;
import static constants.ConstantsForTablesInTheDatabase.BOOKCATEGORY_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.IN_BOOKCATEGORY_TABLE_BOOK_TITLE;
import static constants.ConstantsForTablesInTheDatabase.IN_BOOKCATEGORY_TABLE_CATEGORY;

public class DeleteBookCategoryDAO {

    /*returns a query for deleting a book category in "BookCategory" table.
     It assumes it gets only 1 category*/
    public static String getQueryForDeletingBookCategory(String bookTitle, String categoryToDelete) {
        categoryToDelete = makeEachApostropheIntoTwoApostrophe(categoryToDelete);
        bookTitle = makeEachApostropheIntoTwoApostrophe(bookTitle);
        return "DELETE FROM " + BOOKCATEGORY_TABLE_NAME + " WHERE " + IN_BOOKCATEGORY_TABLE_BOOK_TITLE
                + " = " + "'" + bookTitle + "'" + " AND " + IN_BOOKCATEGORY_TABLE_CATEGORY
                + " = " + "'" + categoryToDelete + "'";
    }
}

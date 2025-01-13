/*
 This Managed Bean is representing a book in the store, with all the values 
 that a book have in the "Books" table in the database.
 */
package beans.book;

import ArrayListHelper.Compare;
import static StringHelper.StringForSQLQuery.makeEachApostropheIntoTwoApostrophe;
import static constants.constant.INITIALIZE_PAGE_NUMBER;
import static constants.constant.INITIALIZE_PRICE;
import static constants.constant.INITIALIZE_QUANTITY_IN_THE_STORE;
import static constants.constant.INITIALIZE_YEAR;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "bookBean")
@SessionScoped
public class BookBean implements Serializable {

    private String bookTitle;
    private int publishYear;
    private String writerFirstName;
    private String writerLastName;
    private int pageNumber;
    private String bookImageUrl;
    private double price;
    private int quantityInTheStore;
    /*the next 'categories' represent the categories of a book in the 
      'bookCategory' table in the database:*/
    private ArrayList<String> categories;

    public BookBean() {
    }

    public BookBean(String bookTitle, int publishYear, String writerFirstName, String writerLastName, ArrayList<String> categories,
            int pageNumber, String bookImageUrl, double price, int quantityInTheStore) {
        this.bookTitle = bookTitle;
        this.publishYear = publishYear;
        this.writerFirstName = writerFirstName;
        this.writerLastName = writerLastName;
        this.categories = categories;
        this.pageNumber = pageNumber;
        this.bookImageUrl = bookImageUrl;
        this.price = price;
        this.quantityInTheStore = quantityInTheStore;
    }

// Getter and Setter for bookTitle:
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    // Getter and Setter for publishYear
    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    // Getter and Setter for writerFirstName
    public String getWriterFirstName() {
        return writerFirstName;
    }

    public void setWriterFirstName(String writerFirstName) {
        this.writerFirstName = writerFirstName;
    }

    // Getter and Setter for writerLastName
    public String getWriterLastName() {
        return writerLastName;
    }

    public void setWriterLastName(String writerLastName) {
        this.writerLastName = writerLastName;
    }

    // Getter and Setter for category
    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    // Getter and Setter for pageNumber
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    // Getter and Setter for bookImageUrl
    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    // Getter and Setter for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getter and Setter for quantityInTheStore
    public int getQuantityInTheStore() {
        return quantityInTheStore;
    }

    public void setQuantityInTheStore(int quantityInTheStore) {
        this.quantityInTheStore = quantityInTheStore;
    }

    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //other methods:
    /*prints the given BookBean*/
    private void printBookBean() {
        System.out.println("Book Title: " + bookTitle);
        System.out.println("Publish Year: " + publishYear);
        System.out.println("Writer First Name: " + writerFirstName);
        System.out.println("Writer Last Name: " + writerLastName);
        System.out.println("Categories: " + categories);
        System.out.println("Page Number: " + pageNumber);
        System.out.println("Book Image URL: " + bookImageUrl);
        System.out.println("Price: " + price);
        System.out.println("Quantity in the Store: " + quantityInTheStore);
    }

    /*This method make for every String value in this book, 2 Apostrophe 
     instead of every 1 apostrophe it have.
     It is used for Statment queries in case of a ' in a book value.*/
    public void makeForEachBookValueTwoApostropheFromOneApostrophe() {
        String category;
        this.bookTitle = makeEachApostropheIntoTwoApostrophe(bookTitle);
        this.writerFirstName = makeEachApostropheIntoTwoApostrophe(writerFirstName);
        this.writerLastName = makeEachApostropheIntoTwoApostrophe(writerLastName);
        this.bookImageUrl = makeEachApostropheIntoTwoApostrophe(bookImageUrl);
        for (int i = 0; i < categories.size(); i++) {
            category = makeEachApostropheIntoTwoApostrophe(categories.get(i));
            categories.set(i, category);
        }
    }

    /*This method initialize all the values of this object.*/
    public void initializeBookBean() {
        bookTitle = null;
        publishYear = INITIALIZE_YEAR;
        writerFirstName = null;
        writerLastName = null;
        categories = null;
        pageNumber = INITIALIZE_PAGE_NUMBER;
        bookImageUrl = null;
        price = INITIALIZE_PRICE;
        quantityInTheStore = INITIALIZE_QUANTITY_IN_THE_STORE;
    }

    /*returns true if the only diffrence between the given book and this book
      is the book title, and return false otherwise.*/
    public boolean isOnlyBookTitleIsDiffrent(BookBean book) {
        return !(this.bookTitle.equals(book.bookTitle))
                && this.publishYear == book.publishYear
                && this.writerFirstName.equals(book.writerFirstName)
                && this.writerLastName.equals(book.writerLastName)
                && this.pageNumber == book.pageNumber
                && this.bookImageUrl.equals(book.bookImageUrl)
                && this.price == book.price
                && this.quantityInTheStore == book.quantityInTheStore
                && this.categories.equals(book.categories);
    }

    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    /*This methods return true if the values of this book and the given book 
     are the same, and return false otherwise.*/
    public boolean equals(BookBean book) {
        return (this.bookTitle.equals(book.bookTitle))
                && (this.publishYear == book.publishYear)
                && (this.writerFirstName.equals(book.writerFirstName))
                && (this.writerLastName.equals(book.writerLastName))
                && (this.pageNumber == book.pageNumber)
                && (this.bookImageUrl.equals(book.bookImageUrl))
                && (this.price == book.price)
                && (this.quantityInTheStore == book.quantityInTheStore)
                && (Compare.equalsWithoutTheMeaningToTheIndex(this.categories, book.categories));
    }
}

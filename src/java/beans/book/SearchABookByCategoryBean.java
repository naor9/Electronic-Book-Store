/*
   this ManagedBean is for a search of book/s by category/ies
 */
package beans.book;

import DAO.booksSearch.GetBooksSearchResultByCategoriesDAO;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "searchABookByCategoryBean")
@RequestScoped
public class SearchABookByCategoryBean implements Serializable {

    //the next array represent the categories the user chose in the "selectManyCheckbox":
    private String[] booksCategoriesToSearch;

    public SearchABookByCategoryBean() {
    }

    public String[] getBooksCategoriesToSearch() {
        return booksCategoriesToSearch;
    }

    /*This method set the categories the user chose for the book search.*/
    public void setBooksCategoriesToSearch(String[] booksCategoriesToSearch) {
        this.booksCategoriesToSearch = booksCategoriesToSearch;
    }

    //initialize 'booksCategoriesToSearch' to null
    public void initializeBooksCategoriesToSearch() {
        booksCategoriesToSearch = null;
    }

    /*This method set all the books that fit the categories that the user chose,
      and save it to the 'booksSearchResults' (of 'SearchBookBean') veriable for later use.
      If the user didn't chose any category then he is not supposed to get 
      to this method, he will get an appropriate notice and he will stay in the same page.
      Otherwise (if the user did chose at least 1 category) the user will get here and
      this method will send him into the page that will show him the books (the page the user will 
      be at is determine by the String this method will return).*/
    public String searchABookByCategory(SearchBookBean searchBookBean) {
        //the next line is to save the result serach:
        searchBookBean.setBooksSearchResults(GetBooksSearchResultByCategoriesDAO.getBooksSearchResultsByCategories(
                booksCategoriesToSearch));
        //to intialize the customer categories choices:
        initializeBooksCategoriesToSearch();
        //if the next line is true then no books were found in the store:
        if (searchBookBean.getBooksSearchResults().isEmpty()) {
            return beans.constantsBean.CustomerPagesBean.NO_BOOK_WAS_FOUND;
        }
        //else:
        //the next line is to send the user into the next page (the page with the results of the book search by categories):
        return beans.constantsBean.CustomerPagesBean.BOOK_SEARCH_RESULT;
    }
}

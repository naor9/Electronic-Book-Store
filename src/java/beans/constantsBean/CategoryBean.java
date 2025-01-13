/*
This managed bean will represent all the Categories that are avaliable 
for the books (all the categories in "Categories" table in the database.
 */
package beans.constantsBean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "categoryBean")
@RequestScoped
public class CategoryBean {

    /*The next array represent all the categories in the databe. */
    private static final String[] categories
            = {"Fiction", "Science Fiction", "Fantasy", "Mystery", "Thriller",
                "Romance", "Biography", "History", "Classic Literature", "Children's Fiction"};

    public CategoryBean() {
    }

    public static String[] getCategories() {
        return categories;
    }

    /*gets an index that represent the number of the line in the table 'Categories' (start from 0)
    and returns the value for that category in that line (as it is in the database, which is in this case
    represented by the 'categories' array. */
    public String getCategoryByIndex(int i) {
        return categories[i];
    }
}

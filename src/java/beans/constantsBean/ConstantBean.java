/*
This bean is used for constant for view
 */
package beans.constantsBean;


import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "constantBean")
@RequestScoped
public class ConstantBean {

    /*The maximum number of pages a book can have (more then the longest book with 21,450 pages)*/
    private static final int MAX_PAGE_NUMBER = 23000;

    public ConstantBean() {
    }

    public static int getMAX_PAGE_NUMBER() {
        return MAX_PAGE_NUMBER;
    }

}

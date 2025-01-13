/*
This bean represent a writer (with the values of a writer in the 'writers' table),
but as a request scoped.
 */
package beans.writer;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;


@ManagedBean(name = "writerAsRequestScopedBean")
@RequestScoped
public class WriterAsRequestScopedBean implements Serializable {

    private WriterBean writerBean = new WriterBean();

    //constructors:
    public WriterAsRequestScopedBean() {
    }

    public WriterBean getWriterBean() {
        return writerBean;
    }

    public void setWriterBean(WriterBean writerBean) {
        this.writerBean = writerBean;
    }
    
}

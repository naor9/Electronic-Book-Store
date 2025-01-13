/*
 This Managed Bean is represent a CustomerBean, but it is used when we need a 
 customer bean as a 'RequestScoped'.
 It is like a 'temp' CustomerBean.
 */
package beans.customer;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;



@ManagedBean(name = "customerBeanForRequestScoped")
@RequestScoped
public class CustomerBeanForRequestScoped implements Serializable {

    private CustomerBean customerForRequestScoped = new CustomerBean();
    
    public CustomerBeanForRequestScoped() {
    }

    public void setCustomerForRequestScoped(CustomerBean customerForRequestScoped) {
        this.customerForRequestScoped = customerForRequestScoped;
    }

    public CustomerBean getCustomerForRequestScoped() {
        return customerForRequestScoped;
    }

    
}

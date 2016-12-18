package app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "ITEM_ORDER")
public class Order extends AbstractPersistable <Long> {
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductOrder> productOrders;

    @ManyToOne(fetch = FetchType.EAGER)
    //@NotNull
    private Customer customer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    public List<ProductOrder> getProductOrders() {
        if (productOrders == null) {
            productOrders = new ArrayList<>();
        }
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    
}

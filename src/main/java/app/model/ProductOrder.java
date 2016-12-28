package app.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class ProductOrder extends AbstractPersistable<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Order order;

    @Min(1)
    private long count;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}

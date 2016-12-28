package app.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProductOrderTest {
    @Test
    public void testItem() {
        ProductOrder productOrder = new ProductOrder();
        Item item = new Item();
        productOrder.setItem(item);
        assertEquals(item, productOrder.getItem());
    }

    @Test
    public void testCount() {
        ProductOrder productOrder = new ProductOrder();
        long count = 15;
        productOrder.setCount(count);
        assertEquals(count, productOrder.getCount());
    }

    @Test
    public void testOrder() {
        ProductOrder productOrder = new ProductOrder();
        Order order = new Order();
        productOrder.setOrder(order);
        assertEquals(order, productOrder.getOrder());
    }
    
}

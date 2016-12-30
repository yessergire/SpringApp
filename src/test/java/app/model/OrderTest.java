package app.model;

import static app.model.ItemTest.getRandomItem;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

public class OrderTest {

    @Test
    public void testSetProductOrders() {
        ProductOrder productOrder = new ProductOrder();
        ArrayList<ProductOrder> list = new ArrayList<>();
        list.add(productOrder);
        Order order = new Order();
        order.setProductOrders(list);
        assertEquals(list, order.getProductOrders());
    }

    @Test
    public void testGetProductOrders() {
        Order order = new Order();
        assertNotNull(order.getProductOrders());
    }

    @Test
    public void testCustomer() {
        Customer customer = new Customer();
        Order order = new Order();
        order.setCustomer(customer);
        assertEquals(customer, order.getCustomer());
    }

    @Test
    public void testGetOrderDate() {
        Date date = new Date();
        Order order = new Order();
        order.setOrderDate(date);
        assertEquals(date, order.getOrderDate());
    }

    @Test
    public void testGetTotal() {
        ArrayList<ProductOrder> list = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            ProductOrder productOrder = new ProductOrder();
            Item item = getRandomItem();
            item.setPrice(1.5);
            productOrder.setItem(item);
            productOrder.setCount(10);
            list.add(productOrder);
        }
        Order order = new Order();
        order.setProductOrders(list);

        assertEquals(150, order.getTotal(), 1e-9);
    }
}

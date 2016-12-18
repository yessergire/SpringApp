package app.model;

import java.util.ArrayList;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderTest {

    @Test
    public void testProductOrders() {
        ProductOrder productOrder = new ProductOrder();
        ArrayList<ProductOrder> list = new ArrayList<>();
        list.add(productOrder);
        Order order = new Order();
        order.setProductOrders(list);
        assertEquals(list, order.getProductOrders());
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
    
}

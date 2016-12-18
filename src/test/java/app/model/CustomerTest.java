package app.model;

import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerTest {

    @Test
    public void testName() {
        String name = UUID.randomUUID().toString();
        Customer customer = new Customer();
        customer.setName(name);
        assertEquals(name, customer.getName());
    }

    @Test
    public void testUsername() {
        String username = UUID.randomUUID().toString();
        Customer customer = new Customer();
        customer.setUsername(username);
        assertEquals(username, customer.getUsername());
    }

    @Test
    public void testPassword() {
        String password = UUID.randomUUID().toString();
        Customer customer = new Customer();
        customer.setPassword(password);
        assertEquals(password, customer.getPassword());
    }

    @Test
    public void testAdminAccess() {
        Customer customer = new Customer();

        customer.setAdminAccess(true);
        assertEquals(true, customer.isAdminAccess());

        customer.setAdminAccess(false);
        assertEquals(false, customer.isAdminAccess());
    }

}

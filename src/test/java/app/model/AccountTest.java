package app.model;

import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void testName() {
        String name = UUID.randomUUID().toString();
        Account account = new Account();
        account.setName("");
        assertEquals("", account.getName());
    }

    @Test
    public void testUsername() {
        String username = UUID.randomUUID().toString();
        Account account = new Account();
        account.setUsername(username);
        assertEquals(username, account.getUsername());
    }

    @Test
    public void testPassword() {
        String password = UUID.randomUUID().toString();
        Account account = new Account();
        account.setPassword(password);
        assertEquals(password, account.getPassword());
    }


    @Test
    public void testAdminAccess() {
        Account account = new Account();

        account.setAdminAccess(true);
        assertEquals(true, account.isAdminAccess());

        account.setAdminAccess(false);
        assertEquals(false, account.isAdminAccess());
    }

}

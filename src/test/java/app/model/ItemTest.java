package app.model;

import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void testName() {
        Item item = new Item();
        String name = UUID.randomUUID().toString();
        item.setName(name);
        assertEquals(name, item.getName());
    }

    @Test
    public void testUsername() {
        Item item = new Item();
        String url = UUID.randomUUID().toString();
        item.setImageUrl(url);
        assertEquals(url, item.getImageUrl());
    }

}

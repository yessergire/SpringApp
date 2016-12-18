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
    public void testImage() {
        Item item = new Item();
        String url = UUID.randomUUID().toString();
        item.setImageUrl(url);
        assertEquals(url, item.getImageUrl());
    }

    @Test
    public void testPrice() {
        Item item = new Item();
        double price = 99.95;
        item.setPrice(price);
        assertEquals(price, item.getPrice(), 1e-6);
    }

    @Test
    public void testCount() {
        Item item = new Item();
        long count = 99;
        item.setCount(count);
        assertEquals(count, item.getCount(), 1e-6);
    }

}

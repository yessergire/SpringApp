package app.model;

import static app.TestHelper.*;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.*;

public class CartTest {

    @Test
    public void testGetItems() {
        Item item = new Item();
        item.setName(randomString());
        Map<Item, Long> map = new TreeMap<>();
        map.put(item, 1L);
        Cart cart = new Cart();
        cart.setItems(map);
        assertEquals(map, cart.getItems());
    }

    @Test
    public void testSetItems() {
        Cart cart = new Cart();
        assertNotNull(cart.getItems());
    }

    @Test
    public void testAdd() {
        Item item = new Item();
        item.setName(randomString());

        Cart cart = new Cart();
        cart.add(item);
        Map<Item, Long> map = cart.getItems();
        assertEquals(1, map.size());
    }

    @Test
    public void testRemove() {
        Item item = new Item();
        item.setName(randomString());

        Cart cart = new Cart();
        Map<Item, Long> map = cart.getItems();
        map.put(item, 2L);
        assertEquals(2L, map.get(item).longValue());
        
        cart.remove(item);
        assertEquals(1L, map.get(item).longValue());

        cart.remove(item);
        assertFalse(map.containsKey(item));
    }
}

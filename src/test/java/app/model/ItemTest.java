package app.model;

import java.util.UUID;
import org.junit.Test;

import static app.TestHelper.randomString;
import static org.junit.Assert.*;

public class ItemTest {

    public static Item getRandomItem() {
    	Item item = new Item();
        item.setName(randomString(10));
        item.setImageUrl(randomString(10));
        item.setPrice(Math.random());
        item.setCount((int) (Math.random() * 100));
    	return item;
    }

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

    @Test
    public void testNameNotEquals() {
	Item item1 = getRandomItem();
	Item item2 = getRandomItem();
        assertNotEquals(item1, item2);
    }

    @Test
    public void testImageNotEquals() {
	Item item1 = getRandomItem();
	Item item2 = getRandomItem();
	item2.setName(item1.getName());
        assertNotEquals(item1, item2);
    }

    @Test
    public void testCountNotEquals() {
	Item item1 = getRandomItem();
	Item item2 = getRandomItem();
	item2.setName(item1.getName());
	item2.setImageUrl(item1.getImageUrl());
        assertNotEquals(item1, item2);
    }

    @Test
    public void testPriceNotEquals() {
	Item item1 = getRandomItem();
	Item item2 = getRandomItem();
	item2.setName(item1.getName());
	item2.setImageUrl(item1.getImageUrl());
	item2.setCount(item1.getCount());
        assertNotEquals(item1, item2);
    }

    @Test
    public void testEquals() {
        Item item = getRandomItem();
        assertEquals(item, item);
    }

}

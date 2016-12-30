package app.model;

import static app.TestHelper.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class CategoryTest {

    @Test
    public void testName() {
    	Category c = new Category();
    	String name = randomString(10);
    	c.setName(name);
        assertEquals(name, c.getName());
    }

    @Test
    public void testDescription() {
    	Category c = new Category();
    	String description = randomString(10);
    	c.setDescription(description);
        assertEquals(description, c.getDescription());
    }

    public static Category getRandomCategory() {
    	Category c = new Category();
    	c.setName(randomString(10));
    	c.setDescription(randomString() + randomString() + randomString());
	return c;
    }

}

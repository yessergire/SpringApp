package app.controller;

import static app.TestHelper.*;
import static app.model.ItemTest.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import app.model.Item;
import app.repository.ItemRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemControllerTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private final String url = "/items";

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        itemRepository.deleteAll();
    }

    @Test
    public void GetItemsShowsItemsPage() throws Exception {
        testThatMvcReturnsPage("The view should be created from items/items.html.",
                mockMvc.perform(get(url)), "items/items");
    }

    @Test
    public void GetItemShowsItemPage() throws Exception {
    	Item item = getRandomItem();
    	item = itemRepository.save(item);

        MvcResult res = testThatMvcReturnsPage("The view should be created from items/item.html.",
                mockMvc.perform(get(url + "/" + item.getId())), "items/item");

        assertTrue(res.getModelAndView().getModelMap().containsKey("item"));
    }

    @Test
    public void GetAddItemShowsAddItemPage() throws Exception {
        testThatMvcReturnsPage("The view should be created from items/add_item_form.html.",
                mockMvc.perform(get(url + "/new")), "items/add_item_form");
    }


    @Test
    public void GetEditItemShowsEditItemPage() throws Exception {
    	Item item = getRandomItem();
    	item = itemRepository.save(item);

        MvcResult res = testThatMvcReturnsPage("The view should be created from items/edit_item_form.html.",
                mockMvc.perform(get(url + "/" + item.getId() + "/edit")), "items/edit_item_form");

        assertTrue(res.getModelAndView().getModelMap().containsKey("item"));
    }

    @Test
    public void SuccessfulPostStoresItemToTheDatabase() throws Exception {
    	Item item = getRandomItem();

        mockMvc.perform(post(url)
                .param("name", item.getName())
                .param("imageUrl", item.getImageUrl())
                .param("price", Double.toString(item.getPrice()))
                .param("count", Long.toString(item.getCount())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url))
                .andReturn();

        assertEquals("The created item should be stored to the database", itemRepository.count(), 1);
        assertEquals("The created item should be stored to the database", item,
        		itemRepository.findAll().get(0));
    }


    @Test
    public void UnsuccessfulPostDoesNotStoreItemToTheDatabase() throws Exception {
        MvcResult res = testThatMvcReturnsPage("The view should be created from items/add_item_form.html.",
                mockMvc.perform(post(url)), "items/add_item_form");

        assertTrue(res.getModelAndView().getModelMap().containsKey("item"));
        assertEquals("The item should not be stored to the database", itemRepository.count(), 0);
    }

    @Test
    public void SuccessfulPostUpdatesItemInTheDatabase() throws Exception {
        Item item = getRandomItem();
        item = itemRepository.save(item);

        Item updatedItem = getRandomItem();

        mockMvc.perform(post(url + "/" + item.getId())
                .param("name", updatedItem.getName())
                .param("imageUrl", updatedItem.getImageUrl())
                .param("price", Double.toString(updatedItem.getPrice()))
                .param("count", Long.toString(updatedItem.getCount())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url + "/" + item.getId()))
                .andReturn();

        item = itemRepository.findOne(item.getId());
        assertEquals("The post should've updated the item", updatedItem, item);
    }

    @Test
    public void UnsuccessfulPostDoesNotUpdateItemInTheDatabase() throws Exception {
        Item item = getRandomItem();
        item = itemRepository.save(item);

        String name = "";
        String imageUrl = "";
        double price = -1D;
        long count = -1L;

        MvcResult res = testThatMvcReturnsPage("The view should be created from items/edit_item_form.html.",
                mockMvc.perform(post(url + "/" + item.getId())
                        .param("name", name)
                        .param("imageUrl", imageUrl)
                        .param("price", Double.toString(price))
                        .param("count", Long.toString(count))), "items/edit_item_form");

        assertTrue(res.getModelAndView().getModelMap().containsKey("item"));

        item = itemRepository.findOne(item.getId());
        assertNotEquals("The post should not update the item", name, item.getName());
        assertNotEquals("The post should not update the item", imageUrl, item.getImageUrl());
        assertNotEquals("The post should not update the item", price, item.getPrice(), 1e-5);
        assertNotEquals("The post should not update the item", count, item.getCount());
    }

    @Test
    public void SuccessfulDeleteRemovesItemFromTheDatabase() throws Exception {
        Item item = getRandomItem();
        item = itemRepository.save(item);

        mockMvc.perform(delete(url + "/" + item.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url))
                .andReturn();

        item = itemRepository.findOne(item.getId());
        assertNull("The delete should've removed the item from the database", item);
    }

}

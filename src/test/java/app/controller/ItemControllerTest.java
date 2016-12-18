package app.controller;

import static app.TestHelper.randomString;
import app.model.Item;
import app.repository.ItemRepository;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
        mockMvc.perform(post("/login")
                .param("username", "user")
                .param("password", "password"));
        itemRepository.deleteAll();
    }

    private void testThatMvcReturnsPage(String message, ResultActions actions, String page) throws Exception {
        MvcResult res = actions.andExpect(status().isOk()).andReturn();
        assertEquals("The view should be created from the item.html page.",
                "items", res.getModelAndView().getViewName());
    }

    @Test
    public void GetShowsItemsPage() throws Exception {
        testThatMvcReturnsPage("The view should be created from items.html.",
                mockMvc.perform(get("/items")), "items");
    }

    @Test
    public void SuccessfulPostStoresItemToDatabase() throws Exception {
        String name = randomString(10);
        String imageUrl = randomString(10);

        mockMvc.perform(post(url)
                .param("name", name)
                .param("imageUrl", imageUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url))
                .andReturn();

        assertEquals("The created item should be stored to the database", itemRepository.count(), 1);
    }


    @Test
    public void SuccessfulPutUpdatesItemInDatabase() throws Exception {
        Item item = new Item();
        item.setName(randomString(10));
        item.setImageUrl(randomString(10));
        item = itemRepository.save(item);

        String name = randomString(10);
        String imageUrl = randomString(10);
        mockMvc.perform(put(url + "/" + item.getId())
                .param("name", name)
                .param("imageUrl", imageUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url))
                .andReturn();

        item = itemRepository.findOne(item.getId());
        assertEquals("The put should've updated the item", name, item.getName());
        assertEquals("The put should've updated the item", imageUrl, item.getImageUrl());
    }

}

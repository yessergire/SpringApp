package app.controller;

import static app.TestHelper.*;
import static app.model.ItemTest.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import app.model.Cart;
import app.model.Item;
import app.repository.ItemRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartControllerTest {

    @Autowired
    private Cart cart;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockHttpSession mockSession;

    private MockMvc mockMvc;

    private final String url = "/cart";

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void GetCaArtShowsCartPage() throws Exception {
	MvcResult res = testThatMvcReturnsPage("The view should be created from cart.html.",
                mockMvc.perform(get(url)), "cart");
        assertTrue(res.getModelAndView().getModelMap().containsKey("cart"));
    }

    @Test
    public void SuccessfulPostAddsAnItemToTheCart() throws Exception {
	Item item = getRandomItem();
	item = itemRepository.save(item);

	System.out.println("POST " + url + "/items/" + item.getId());
        mockMvc.perform(post(url + "/items/" + item.getId())
        	.session(mockSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url))
                .andReturn();
        assertTrue("The post should've added the item to the cart", cart.getItems().keySet().contains(item));
    }

    @Test
    public void SuccessfulDeleteRemovesItemFromTheCart() throws Exception {
	Item item = getRandomItem();
	item = itemRepository.save(item);
	cart.add(item);

        mockMvc.perform(delete(url + "/items/" + item.getId())
        	.session(mockSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url))
                .andReturn();
        assertNull("The delete should've removed the item from the cart", cart.getItems().get(item));
    }

}

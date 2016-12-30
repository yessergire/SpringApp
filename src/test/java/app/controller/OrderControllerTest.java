package app.controller;

import static app.TestHelper.testThatMvcReturnsPage;
import static app.model.CustomerTest.getRandomCustomer;
import static app.model.ItemTest.getRandomItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import app.model.Cart;
import app.model.Customer;
import app.model.Item;
import app.model.Order;
import app.repository.CustomerRepository;
import app.repository.ItemRepository;
import app.repository.OrderRepository;
import app.service.CurrentUserService;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class OrderControllerTest {

    @Autowired
    private Cart cart;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockHttpSession mockSession;

    private MockMvc mockMvc;

    private final String url = "/orders";

    @Before
    public void init() throws Exception {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	orderRepository.deleteAll();
	customerRepository.deleteAll();
	Authentication authentication = Mockito.mock(Authentication.class);
	SecurityContext securityContext = Mockito.mock(SecurityContext.class);
	Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

	Customer customer = customerRepository.save(getRandomCustomer());
	System.out.println(customer.getUsername());
	Mockito.when(authentication.getName()).thenReturn(customer.getUsername());
	SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void GetOrdersShowsOrdersPage() throws Exception {
	MvcResult res = testThatMvcReturnsPage("The view should be created from orders.html.",
		mockMvc.perform(get(url)), "orders");
	assertTrue(res.getModelAndView().getModelMap().containsKey("orders"));
    }

    @Test
    public void GetItemShowsItemPage() throws Exception {
	Order order = new Order();
	order.setCustomer(currentUserService.getCustomer());
	order = orderRepository.save(order);

	MvcResult res = testThatMvcReturnsPage("The view should be created from order.html.",
		mockMvc.perform(get(url + "/" + order.getId())), "order");

	assertTrue(res.getModelAndView().getModelMap().containsKey("order"));
    }

    @Test
    public void SuccessfulPostStoresOrderToTheDatabase() throws Exception {
	mockMvc.perform(post(url)).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl(url)).andReturn();

	assertEquals("The created order should be stored to the database", orderRepository.count(), 1);
    }

    @Test
    public void SuccessfulPostStoresOrderWithItemsToTheDatabase() throws Exception {
	Item item1 = getRandomItem();
	item1 = itemRepository.save(item1);
	cart.add(item1);

	Item item2 = getRandomItem();
	item1 = itemRepository.save(item2);
	cart.add(item2);

	mockMvc.perform(post(url).session(mockSession)).andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl(url)).andReturn();

	assertEquals("The created order should be stored to the database", orderRepository.count(), 1);

	Order order = orderRepository.findAll().get(0);
	assertEquals("The created items should be stored to the cart", 2, order.getProductOrders().size());
    }

    @Test
    public void SuccessfulDeleteRemovesOrderFromTheDatabase() throws Exception {
	Order order = new Order();
	order.setCustomer(currentUserService.getCustomer());
	order = orderRepository.save(order);

	mockMvc.perform(delete(url + "/" + order.getId())).andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl(url)).andReturn();

	order = orderRepository.findOne(order.getId());
	assertNull("The delete should've removed the order from the database", order);
    }

}

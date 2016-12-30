package app.controller;

import app.model.Customer;
import app.model.Item;

import java.util.UUID;
import static org.junit.Assert.*;
import static app.TestHelper.*;
import static app.model.CustomerTest.getRandomCustomer;
import static app.model.ItemTest.getRandomItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import app.repository.CustomerRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    @Autowired
    private CustomerRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private final String url = "/customers";

    private String form;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private void testThatMvcReturnsForm(String message, ResultActions actions) throws Exception {
        testThatMvcReturnsPage(message, actions, form);
    }

    @Test
    public void GetShowsForm() throws Exception {
	form = "customers/new";
        testThatMvcReturnsForm("The view should be created from the customers/new.html-page.",
                mockMvc.perform(get("/signup")));
    }

    @Test
    public void SuccessfulPostStoresUserDetailsToDatabase() throws Exception {
        String name = randomString(10);
        String username = randomString(10);
        String password = randomString(15);

        mockMvc.perform(post(url)
                .param("name", name)
                .param("username", username)
                .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andReturn();

        Customer user = userRepository.findByUsername(username);
        assertNotNull("The created account should be added to the database", user);
    }

    @Test
    public void InvalidCreateReturnsForm() throws Exception {
	String url = this.url;
	form = "customers/new";

	testThatBlankNameReturnsForm(url);
	testThatShortNameReturnsForm(url);
	testThatLongNameReturnsForm(url);

	testThatBlankUsernameReturnsForm(url);
	testThatShortUsernameReturnsForm(url);
	testThatLongUsernameReturnsForm(url);

	testThatBlankPasswordReturnsForm(url);
    }

    @Test
    public void InvalidUpdateReturnsForm() throws Exception {
	Customer customer = getRandomCustomer();
	customer = userRepository.save(customer);
	String url = this.url + "/" + customer.getId();
	form = "customers/edit";

	testThatBlankNameReturnsForm(url);
	testThatShortNameReturnsForm(url);
	testThatLongNameReturnsForm(url);

	testThatBlankUsernameReturnsForm(url);
	testThatShortUsernameReturnsForm(url);
	testThatLongUsernameReturnsForm(url);

	testThatBlankPasswordReturnsForm(url);
    }

    private void InvalidPostReturnsForm(String msg, Customer customer, String url) throws Exception {
        ResultActions actions = mockMvc.perform(post(url)
                .param("name", customer.getName())
                .param("username", customer.getUsername())
                .param("password", customer.getPassword()));
        testThatMvcReturnsForm(msg, actions);
    }

    private void testThatBlankNameReturnsForm(String url) throws Exception {
	Customer customer = getRandomCustomer();
	customer.setName("");
        InvalidPostReturnsForm("The name should not be blank.", customer, url);
    }

    private void testThatShortNameReturnsForm(String url) throws Exception {
	Customer customer = getRandomCustomer();
	customer.setName("a");
        InvalidPostReturnsForm("The name should be longer that 3 characters.", customer, url);
    }

    private void testThatLongNameReturnsForm(String url) throws Exception {
	Customer customer = getRandomCustomer();
	customer.setName(randomString() + randomString() + randomString());
        InvalidPostReturnsForm("The name should be shorter that 40 characters.", customer, url);
    }

    private void testThatBlankUsernameReturnsForm(String url) throws Exception {
	Customer customer = getRandomCustomer();
	customer.setUsername("");
        InvalidPostReturnsForm("The username shouldn't be blank.", customer, url);
    }

    private void testThatShortUsernameReturnsForm(String url) throws Exception {
	Customer customer = getRandomCustomer();
	customer.setUsername("a");
        InvalidPostReturnsForm("The username shouldn't be shorter than 5 characters.", customer, url);
    }

    private void testThatLongUsernameReturnsForm(String url) throws Exception {
	Customer customer = getRandomCustomer();
	customer.setUsername("randomString() + randomString() + randomString()");
        InvalidPostReturnsForm("The username shouldn't be longer than 40 characters.", customer, url);
    }

    private void testThatBlankPasswordReturnsForm(String url) throws Exception {
	Customer customer = getRandomCustomer();
	customer.setPassword("");
        InvalidPostReturnsForm("The password shouldn't be blank.", customer, url);
    }

    @Test
    public void SuccessfulDeleteRemovesCustomerFromTheDatabase() throws Exception {
	Customer customer = getRandomCustomer();
	customer = userRepository.save(customer);

        mockMvc.perform(delete(url + "/" + customer.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andReturn();
        assertNull("The delete should've removed the user from the database", userRepository.findOne(customer.getId()));
    }

    @Test
    public void GetCustomersShowsCustomersPage() throws Exception {
	MvcResult res = testThatMvcReturnsPage("The view should be created from customers/customers.html.",
                mockMvc.perform(get(url)), "customers/list");
        assertTrue(res.getModelAndView().getModelMap().containsKey("customers"));
    }

    @Test
    public void GetCustomerShowsCustomerPage() throws Exception {
	Customer customer = getRandomCustomer();
	customer = userRepository.save(customer);


	MvcResult res = testThatMvcReturnsPage("The view should be created from customers/customers.html.",
                mockMvc.perform(get(url + "/" + customer.getId())), "customers/show");
        assertTrue(res.getModelAndView().getModelMap().containsKey("customer"));
    }


    @Test
    public void GetEditCustomerShowsEditCustomerForm() throws Exception {
	Customer customer = getRandomCustomer();
	customer = userRepository.save(customer);

	MvcResult res = testThatMvcReturnsPage("The view should be created from customers/customers.html.",
                mockMvc.perform(get(url + "/" + customer.getId() + "/edit")), "customers/edit");
        assertTrue(res.getModelAndView().getModelMap().containsKey("customer"));
    }

    @Test
    public void SuccessfulUpdateStoresUserDetailsToDatabase() throws Exception {
	Customer customer = getRandomCustomer();
	customer = userRepository.save(customer);

        String name = randomString(10);
        String username = randomString(10);
        String password = randomString(15);

        mockMvc.perform(post(url + "/" + customer.getId())
                .param("name", name)
                .param("username", username)
                .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url + "/" + customer.getId()))
                .andReturn();

        String msg = "The created account should be added to the database";
        Customer user = userRepository.findByUsername(username);
        assertNotNull(msg, user);
        assertEquals(msg, name, user.getName());
        assertEquals(msg, username, user.getUsername());
        assertEquals(msg, password, user.getPassword());
    }

}

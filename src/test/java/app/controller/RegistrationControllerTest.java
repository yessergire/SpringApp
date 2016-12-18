package app.controller;

import app.model.Customer;
import java.util.UUID;
import static org.junit.Assert.*;
import static app.TestHelper.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import app.repository.CustomerRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegistrationControllerTest {

    @Autowired
    private CustomerRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private final String url = "/signup";

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private void testThatMvcReturnsForm(String message, ResultActions actions) throws Exception {
        testThatMvcReturnsPage("The view should be created from the form.html page.", actions, "form");
    }

    @Test
    public void GetShowsForm() throws Exception {
        testThatMvcReturnsForm("The view should be created from the form.html-page.",
                mockMvc.perform(get(url)));
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

    private void InvalidPostReturnsForm(String message, String name,
            String username, String password) throws Exception {
        ResultActions actions = mockMvc.perform(post(url)
                .param("name", name)
                .param("username", username)
                .param("password", password));
        testThatMvcReturnsForm("The account name shouldn't be blank.", actions);
    }

    @Test
    public void BlankNameReturnsForm() throws Exception {
        String name = "";
        String username = UUID.randomUUID().toString().substring(0, 15);
        String password = UUID.randomUUID().toString().substring(0, 15);
        InvalidPostReturnsForm("The name should not be blank.", name, username, password);
    }

    @Test
    public void ShortNameReturnsForm() throws Exception {
        String name = "a";
        String username = randomString(10);
        String password = randomString(10);
        InvalidPostReturnsForm("The name should be longer that 3 characters.", name, username, password);
    }

    @Test
    public void LongNameReturnsForm() throws Exception {
        String name = randomString() + randomString() + randomString();
        String username = randomString(10);
        String password = randomString(10);
        InvalidPostReturnsForm("The name should be shorter that 40 characters.", name, username, password);
    }

    @Test
    public void BlankUsernameReturnsForm() throws Exception {
        String username = "";
        String name = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        InvalidPostReturnsForm("The username shouldn't be blank.", name, username, password);
    }

    @Test
    public void ShortUsernameReturnsForm() throws Exception {
        String username = "a";
        String name = randomString(10);
        String password = randomString(10);
        InvalidPostReturnsForm("The username shouldn't be shorter than 5 characters.", name, username, password);
    }

    @Test
    public void LongUsernameReturnsForm() throws Exception {
        String username = randomString() + randomString() + randomString();
        String name = randomString(10);
        String password = randomString(10);
        InvalidPostReturnsForm("The username shouldn't be longer than 40 characters.", name, username, password);
    }

    @Test
    public void BlankPasswordReturnsForm() throws Exception {
        String password = "";
        String username = randomString(10);
        String name = randomString(10);
        InvalidPostReturnsForm("The password shouldn't be blank.", name, username, password);
    }
}

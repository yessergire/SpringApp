package app.selenium;

import org.fluentlenium.adapter.FluentTest;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterationTest extends FluentTest {
    public WebDriver driver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return driver;
    }

    @LocalServerPort
    private Integer port;

    @Ignore
    public void canRegister() {
        goTo("http://localhost:" + port + "/signup");

        String username = "the_username";
        String password = "users_password";

        fill($("#name")).with("User's Full Name");
        fill(find("#username")).with(username);
        fill(find("#password")).with(password);
        submit(find("form").first());

        fill(find("input", withName("username"))).with(username);
        fill(find("input", withName("password"))).with(password);
        submit(find("form").first());
        assertTrue("Successful login should redirect to home page",
                pageSource().contains("My Web"));
    }
}

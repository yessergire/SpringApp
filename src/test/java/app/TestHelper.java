package app;

import java.util.UUID;
import static org.junit.Assert.assertEquals;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestHelper {

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static String randomString(int n) {
        return randomString().substring(0, n);
    }

    public static MvcResult testThatMvcReturnsPage(String message, ResultActions actions, String view) throws Exception {
        MvcResult res = actions.andExpect(status().isOk()).andReturn();
        assertEquals(message, view, res.getModelAndView().getViewName());
        return res;
    }

}

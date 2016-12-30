package app.controller;

import static app.TestHelper.*;
import static app.model.CategoryTest.getRandomCategory;
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

import app.model.Category;
import app.repository.CategoryRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryControllerTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private final String url = "/categories";

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        categoryRepository.deleteAll();
    }

    @Test
    public void GetCategorysShowsCategorysPage() throws Exception {
	MvcResult res = testThatMvcReturnsPage("The view should be created from categories/list.html.",
                mockMvc.perform(get(url)), "categories/list");

        assertTrue(res.getModelAndView().getModelMap().containsKey("category"));
    }

    @Test
    public void GetCategoryShowsCategoryPage() throws Exception {
    	Category category = getRandomCategory();
    	category = categoryRepository.save(category);

        MvcResult res = testThatMvcReturnsPage("The view should be created from categories/show.html.",
                mockMvc.perform(get(url + "/" + category.getId())), "categories/show");

        assertTrue(res.getModelAndView().getModelMap().containsKey("category"));
    }

    @Test
    public void GetAddCategoryShowsAddCategoryPage() throws Exception {
        testThatMvcReturnsPage("The view should be created from categories/new.html.",
                mockMvc.perform(get(url + "/new")), "categories/new");
    }


    @Test
    public void GetEditCategoryShowsEditCategoryPage() throws Exception {
    	Category category = getRandomCategory();
    	category = categoryRepository.save(category);

        MvcResult res = testThatMvcReturnsPage("The view should be created from categories/edit_Category_form.html.",
                mockMvc.perform(get(url + "/" + category.getId() + "/edit")), "categories/edit");

        assertTrue(res.getModelAndView().getModelMap().containsKey("category"));
    }

    @Test
    public void SuccessfulPostStoresCategoryToTheDatabase() throws Exception {
    	Category category = getRandomCategory();

        mockMvc.perform(post(url)
                .param("name", category.getName())
                .param("description", category.getDescription()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url))
                .andReturn();

        assertEquals("The created Category should be stored to the database", categoryRepository.count(), 1);
    }


    @Test
    public void UnsuccessfulPostDoesNotStoreCategoryToTheDatabase() throws Exception {
        MvcResult res = testThatMvcReturnsPage("The view should be created from categories/new.html.",
                mockMvc.perform(post(url)), "categories/new");

        assertTrue(res.getModelAndView().getModelMap().containsKey("category"));
        assertEquals("The Category should not be stored to the database", categoryRepository.count(), 0);
    }

    @Test
    public void SuccessfulPostUpdatesCategoryInTheDatabase() throws Exception {
        Category category = getRandomCategory();
        category = categoryRepository.save(category);

        Category updatedCategory = getRandomCategory();

        mockMvc.perform(post(url + "/" + category.getId())
                .param("name", updatedCategory.getName())
                .param("description", updatedCategory.getDescription()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url + "/" + category.getId()))
                .andReturn();

        category = categoryRepository.findOne(category.getId());
        assertEquals("The post should have update the Category", updatedCategory.getName(), category.getName());
        assertEquals("The post should have update the Category", updatedCategory.getDescription(), category.getDescription());
    }

    @Test
    public void UnsuccessfulPostDoesNotUpdateCategoryInTheDatabase() throws Exception {
        Category category = getRandomCategory();
        category = categoryRepository.save(category);

        String name = "";
        String imageUrl = "";
        double price = -1D;
        long count = -1L;

        MvcResult res = testThatMvcReturnsPage("The view should be created from categories/edit.html.",
                mockMvc.perform(post(url + "/" + category.getId())
                        .param("name", name)
                        .param("imageUrl", imageUrl)
                        .param("price", Double.toString(price))
                        .param("count", Long.toString(count))), "categories/edit");

        assertTrue(res.getModelAndView().getModelMap().containsKey("category"));

        category = categoryRepository.findOne(category.getId());
        assertNotEquals("The post should not update the Category", name, category.getName());
        assertNotEquals("The post should not update the Category", imageUrl, category.getDescription());
    }

    @Test
    public void SuccessfulDeleteRemovesCategoryFromTheDatabase() throws Exception {
        Category category = getRandomCategory();
        category = categoryRepository.save(category);

        mockMvc.perform(delete(url + "/" + category.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url))
                .andReturn();

        category = categoryRepository.findOne(category.getId());
        assertNull("The delete should've removed the Category from the database", category);
    }

}

package app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.model.Category;
import app.repository.CategoryRepository;
import app.repository.ItemRepository;
import app.service.PaginationService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PaginationService paginationService;

    @ModelAttribute
    public Category getCategory() {
	return new Category();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, @RequestParam(defaultValue = "0") String p) {
	PageRequest pageable = new PageRequest(Integer.parseInt(p), 10);
	Page<Category> page = categoryRepository.findAll(pageable);
	paginationService.addPagination(model, page, "categories");
	return "categories/list";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String add() {
	return "categories/new";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
	Category category = categoryRepository.findOne(id);
	model.addAttribute("category", category);
	return "categories/edit";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model, @RequestParam(defaultValue = "0") String p) {
	Category category = categoryRepository.findOne(id);
	PageRequest pageable = new PageRequest(Integer.parseInt(p), 10);
	paginationService.addPagination(model, pageable, "items", category.getItems());
	model.addAttribute("category", category);
	return "categories/show";
    }

    @Secured("ADMIN")
    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Category category, BindingResult bindingResult) {
	if (bindingResult.hasErrors()) {
	    return "categories/new";
	}
	categoryRepository.save(category);
	return "redirect:/categories";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute Category updatedCategory, BindingResult bindingResult,
	    @PathVariable Long id) {
	if (bindingResult.hasErrors()) {
	    return "categories/edit";
	}

	Category category = categoryRepository.findOne(id);
	category.setName(updatedCategory.getName());
	category.setDescription(updatedCategory.getDescription());
	category.setItems(updatedCategory.getItems());

	categoryRepository.save(category);
	return "redirect:/categories/" + id;
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
	categoryRepository.delete(id);
	return "redirect:/categories";
    }
}

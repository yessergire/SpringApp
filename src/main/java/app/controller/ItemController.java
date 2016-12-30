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

import app.model.Item;
import app.repository.ItemRepository;
import app.service.PaginationService;

@Controller
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private PaginationService paginationService;

	@ModelAttribute
	public Item getItem() {
		return new Item();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, @RequestParam(defaultValue = "0") String p) {
		PageRequest pageable = new PageRequest(Integer.parseInt(p), 10);
		Page<Item> page = itemRepository.findAll(pageable);
		paginationService.addPagination(model, page, "products");
		return "items/list";
	}

	@Secured("ADMIN")
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add() {
		return "items/new";
	}

	@Secured("ADMIN")
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("item", itemRepository.findOne(id));
		return "items/edit";
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String view(@PathVariable Long id, Model model) {
		model.addAttribute("item", itemRepository.findOne(id));
		return "items/show";
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute Item item,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "items/new";
		}
		itemRepository.save(item);
		return "redirect:/items";
	}

	@Secured("ADMIN")
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute Item updatedItem,
			BindingResult bindingResult,
			@PathVariable Long id) {
		if (bindingResult.hasErrors()) {
			return "items/edit";
		}

		Item item = itemRepository.findOne(id);
		item.setName(updatedItem.getName());
		item.setImageUrl(updatedItem.getImageUrl());
		item.setCount(updatedItem.getCount());
		item.setPrice(updatedItem.getPrice());

		item = itemRepository.save(item);
		return "redirect:/items/" + item.getId();
	}

	@Secured("ADMIN")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id) {
		itemRepository.delete(id);
		return "redirect:/items";
	}
}

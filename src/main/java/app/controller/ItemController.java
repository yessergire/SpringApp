package app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.model.Cart;
import app.model.Item;
import app.repository.ItemRepository;

@Controller
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private Cart cart;

	@ModelAttribute
	public Item getItem() {
		return new Item();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("products", itemRepository.findAll());
		model.addAttribute("cart", cart);
		return "items/items";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("cart", cart);
		return "items/add_item_form";
	}


	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("item", itemRepository.findOne(id));
		model.addAttribute("cart", cart);
		return "items/edit_item_form";
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String view(@PathVariable Long id, Model model) {
		model.addAttribute("item", itemRepository.findOne(id));
		model.addAttribute("cart", cart);
		return "items/item";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute Item item, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "items";
		}
		itemRepository.save(item);
		return "redirect:/items";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute Item updatedItem,
			@PathVariable Long id,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "items";
		}

		Item item = itemRepository.findOne(id);
		item.setName(updatedItem.getName());
		item.setImageUrl(updatedItem.getImageUrl());
		item.setCount(updatedItem.getCount());
		item.setPrice(updatedItem.getPrice());

		item = itemRepository.save(item);
		return "redirect:/items/" + item.getId();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id) {
		itemRepository.delete(id);
		return "redirect:/items";
	}
}

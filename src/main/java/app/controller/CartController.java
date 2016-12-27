package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.model.Cart;
import app.repository.ItemRepository;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private Cart cart;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("items", cart.getItems());
        model.addAttribute("cart", cart);
        return "cart";
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.POST)
    public String add(@PathVariable Long id) {
        cart.add(itemRepository.findOne(id));
        return "redirect:/cart";
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.DELETE)
    public String remove(@PathVariable Long id) {
        cart.remove(itemRepository.findOne(id));
        return "redirect:/cart";
    }
}

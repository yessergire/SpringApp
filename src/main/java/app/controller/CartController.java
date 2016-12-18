package app.controller;

import app.model.Cart;
import app.model.Item;
import app.model.Order;
import app.model.ProductOrder;
import app.repository.CustomerRepository;
import app.repository.ItemRepository;
import app.repository.OrderRepository;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        return "cart";
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.POST)
    public String order(@PathVariable Long id) {
        cart.add(itemRepository.findOne(id));
        return "redirect:/cart";
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.DELETE)
    public String remove(@PathVariable Long id) {
        cart.remove(itemRepository.findOne(id));
        return "redirect:/cart";
    }
}

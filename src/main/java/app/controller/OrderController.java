package app.controller;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.model.Cart;
import app.model.Item;
import app.model.Order;
import app.model.ProductOrder;
import app.repository.CustomerRepository;
import app.repository.OrderRepository;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Cart shoppingCart;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "orders";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String fetchOrder(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderRepository.findOne(id));
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create() {
        Order order = new Order();
        order.setOrderDate(Calendar.getInstance().getTime());
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        order.setCustomer(customerRepository.findAll().get(0));

        Map<Item, Long> items = shoppingCart.getItems();
        for (Item item: items.keySet()) {
            ProductOrder orderItem = new ProductOrder();
            orderItem.setItem(item);
            orderItem.setCount(items.get(item));
            order.getProductOrders().add(orderItem);
        }

        shoppingCart.setItems(new TreeMap<>());
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
        orderRepository.delete(id);
        return "redirect:/orders";
    }
}

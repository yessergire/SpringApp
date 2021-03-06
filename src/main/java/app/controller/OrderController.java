package app.controller;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.model.Cart;
import app.model.Customer;
import app.model.Item;
import app.model.Order;
import app.model.ProductOrder;
import app.repository.CustomerRepository;
import app.repository.OrderRepository;
import app.repository.ProductOrderRepository;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Cart shoppingCart;

    @Secured("ADMIN")
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

    @Secured({"USER"})
    @RequestMapping(method = RequestMethod.POST)
    public String create() {
        Order order = new Order();
        order.setOrderDate(Calendar.getInstance().getTime());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findByUsername(auth.getName().toString());
        order.setCustomer(customer);

        Map<Item, Long> items = shoppingCart.getItems();
        orderRepository.save(order);
        for (Item item: items.keySet()) {
            ProductOrder orderItem = new ProductOrder();
            orderItem.setItem(item);
            orderItem.setCount(items.get(item));
            orderItem.setOrder(order);
            orderItem = productOrderRepository.save(orderItem);
            order.getProductOrders().add(orderItem);
        }

        shoppingCart.setItems(new TreeMap<>());
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
        orderRepository.delete(id);
        return "redirect:/orders";
    }
}

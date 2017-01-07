package app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.model.Customer;
import app.model.Order;
import app.repository.CustomerRepository;
import app.repository.OrderRepository;
import app.service.CurrentUserService;

@Controller
public class AccountController {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CurrentUserService currentUserService;

    @RequestMapping(value = "account", method = RequestMethod.GET)
    public String viewAccount(Model model) {
	Customer customer = currentUserService.getCustomer();
	model.addAttribute("customer", customer);
	model.addAttribute("orders", orderRepository.findByCustomer(customer));
	return "customers/show";
    }

}

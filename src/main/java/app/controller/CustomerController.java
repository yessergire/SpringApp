package app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.model.Customer;
import app.repository.CustomerRepository;
import app.service.CurrentUserService;
import app.service.PaginationService;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaginationService paginationService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @ModelAttribute
    public Customer getCustomer() {
	return new Customer();
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String list(Model model, @RequestParam(defaultValue = "0") String p) {
	PageRequest pageable = new PageRequest(Integer.parseInt(p), 10);
	Page<Customer> page = customerRepository.findAll(pageable);
	paginationService.addPagination(model, page, "customers");
	return "customers/list";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String add() {
        return "customers/new";
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public String register(
            @Valid @ModelAttribute Customer customer,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customers/new";
        }

        customer.setAdminAccess(false);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
        return "redirect:/login";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model) {
	model.addAttribute("customer", customerRepository.findOne(id));
	return "customers/show";
    }

    @Secured("USER")
    @RequestMapping(value = "/customers/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
	model.addAttribute("customer", customerRepository.findOne(id));
	return "customers/edit";
    }

    @Secured("USER")
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute Customer updatedCustomer, BindingResult bindingResult,
	    @PathVariable Long id) {
	if (bindingResult.hasErrors()) {
	    return "customers/edit";
	}

	Customer customer = customerRepository.findOne(id);
	customer.setName(updatedCustomer.getName());
	customer.setUsername(updatedCustomer.getUsername());
	customer.setPassword(updatedCustomer.getPassword());

	customer = customerRepository.save(customer);
	return "redirect:/customers/" + customer.getId();
    }

    @Secured("USER")
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
	customerRepository.delete(id);
	return "redirect:/login";
    }
}

package app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.model.Customer;
import app.repository.CustomerRepository;

@Controller
@RequestMapping("/signup")
public class RegistrationController {
    @Autowired
    private CustomerRepository userRepository;

    //private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @ModelAttribute
    public Customer getCustomer(){
        return new Customer();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String view() {
        return "form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String register(
            @Valid @ModelAttribute Customer customer,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        //customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        userRepository.save(customer);

        return "redirect:/login";
    }
}

package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import app.model.Customer;
import app.repository.CustomerRepository;

@Service
public class CurrentUserService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findByUsername(auth.getName().toString());
        return customer;
    }

}

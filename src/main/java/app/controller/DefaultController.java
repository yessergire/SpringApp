package app.controller;

import app.model.Account;
import app.repository.AccountRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    public void init() {
        if (accountRepository.findByUsername("test_user") != null) {
            return;
        }

        Account user = new Account();
        user.setUsername("test_user");
        user.setPassword(passwordEncoder.encode("password12"));
        user = accountRepository.save(user);
    }

    @RequestMapping("*")
    public String handleDefault() {
        return "home";
    }

}

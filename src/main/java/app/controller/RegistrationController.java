package app.controller;

import app.model.Account;
import app.repository.AccountRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/signup")
public class RegistrationController {
    @Autowired
    private AccountRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @ModelAttribute
    public Account getAccount(){
        return new Account();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String view() {
        return "form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String register(
            @Valid @ModelAttribute Account account,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        userRepository.save(account);

        return "redirect:/login";
    }
}

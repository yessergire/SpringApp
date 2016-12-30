package app.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.model.Customer;
import app.repository.CustomerRepository;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private CustomerRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        ArrayList<GrantedAuthority> auths = new ArrayList<>();
        if (account.isAdminAccess()) {
            auths.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            auths.add(new SimpleGrantedAuthority("USER"));
        }

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                auths);
    }
}

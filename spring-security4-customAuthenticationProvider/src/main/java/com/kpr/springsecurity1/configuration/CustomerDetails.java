package com.kpr.springsecurity1.configuration;

import com.kpr.springsecurity1.entity.Customer;
import com.kpr.springsecurity1.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerDetails implements UserDetailsService {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Customer> customer = customerRepository.findByEmail(username);
        if (customer.size() ==0){
            throw new UsernameNotFoundException("No user found with the email:- "+ username);
        }
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(customer.get(0).getRole()));
        return new User(customer.get(0).getEmail(),customer.get(0).getPwd(),authorities);
    }
}

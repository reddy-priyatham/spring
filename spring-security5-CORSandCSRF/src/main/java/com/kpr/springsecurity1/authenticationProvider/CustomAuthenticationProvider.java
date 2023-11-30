package com.kpr.springsecurity1.authenticationProvider;

import com.kpr.springsecurity1.entity.Customer;
import com.kpr.springsecurity1.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<Customer> byEmail = customerRepository.findByEmail(username);
        if(byEmail.size() > 0){
            if(this.passwordEncoder.matches(password,byEmail.get(0).getPwd())){
                List<GrantedAuthority> roles = new ArrayList<>();
                roles.add(new SimpleGrantedAuthority(byEmail.get(0).getRole()));
                return new UsernamePasswordAuthenticationToken(username,password,roles);
            } else {
                throw  new BadCredentialsException("Invalid password");
            }
        } else {
            throw new UsernameNotFoundException("NO user found with email:- "+authentication.getDetails());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

package com.kpr.springsecurity1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((request) -> {
            request.requestMatchers("/open").permitAll();
            request.requestMatchers("/user","/admin").authenticated();
            request.requestMatchers("/none").denyAll();
        });
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        /*UserDetails u1 = User.withDefaultPasswordEncoder().username("user").password("user").authorities("user").build();
//        UserDetails u2 = User.withDefaultPasswordEncoder().username("admin").password("admin").authorities("admin").build();*/
//
//        UserDetails user1 = User.withUsername("user").password("user").authorities("user").build();
//        UserDetails user2 = User.withUsername("admin").password("admin").authorities("admin").build();
//        return new InMemoryUserDetailsManager(user2,user1);
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}

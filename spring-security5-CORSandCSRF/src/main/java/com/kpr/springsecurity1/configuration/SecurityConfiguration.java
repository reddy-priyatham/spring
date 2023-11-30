package com.kpr.springsecurity1.configuration;


import com.kpr.springsecurity1.filters.CSRFFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((request) -> {
            request.requestMatchers("/").permitAll();
            request.requestMatchers("/open","/register","/getCustomer").permitAll();
            request.requestMatchers("/user","/admin").authenticated();
            request.requestMatchers("/none").denyAll();
        });
        /**
         * Cors related changes
         * */
        httpSecurity.cors(cors -> {
            cors.configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhosy:4200"));//Allows cors request from this URL
                    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfiguration.setMaxAge(3600L);
                    return null;
                }
            });
        });
        /**
         * ENDOF Cors related changes
         * */

        /**
         * CSRF Changes
         * */
        httpSecurity.securityContext(securityContext -> {
            securityContext.requireExplicitSave(false);//Giving responsibility of generating jSessionID to application
        }).sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        });

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequestAttributeHandler.setCsrfRequestAttributeName("_csrf");

        httpSecurity.csrf(csrf -> {
           csrf.ignoringRequestMatchers("/open","/none");
           csrf.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler);
           csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());// To make sure js in frontend can be able to read the cookie value
        });
        httpSecurity.addFilterAfter(new CSRFFilter(), BasicAuthenticationFilter.class);
        /**
         * ENDOF CSRF Changes
         * */
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

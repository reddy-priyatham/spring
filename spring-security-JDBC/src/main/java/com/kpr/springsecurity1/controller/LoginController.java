package com.kpr.springsecurity1.controller;

import com.kpr.springsecurity1.entity.Customer;
import com.kpr.springsecurity1.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    CustomerRepository customerRepository;
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody Customer customer){

        Logger logger = LoggerFactory.getLogger(LoginController.class);
        Customer cus = null;
        ResponseEntity res = null;
        try {
            Customer save = customerRepository.save(customer);
            if(save.getId() > 0){
                logger.info("User created successfully for email:- ",customer.getEmail());
                res = ResponseEntity.status(201).body("Registration Successful");
            }
        } catch (Exception e) {
            logger.error("Error occured while creating the account for the user:- ",customer.getEmail());
            res = ResponseEntity.status(500).body("Exception occurred:- "+ e.getMessage());
        }
        return res;
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<List<Customer>> getCustomer() {
        ResponseEntity res = null;
        try {
            List<Customer> all = (List<Customer>) customerRepository.findAll();
            if(all.size()>0){
                res = ResponseEntity.status(200).body(all);
            }
        } catch (Exception e){
            res = ResponseEntity.status(404).body(new LinkedList<Customer>());
        }
        return res;
    }
}

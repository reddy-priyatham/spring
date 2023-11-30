package com.kpr.springsecurity1.repository;

import com.kpr.springsecurity1.entity.Customer;;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    //JPARepository has all implementation of CrudRepository and extra scopes
    List<Customer> findByEmail(String email);
}

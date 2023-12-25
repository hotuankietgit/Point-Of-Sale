package com.example.PointOfSale.dao;

import com.example.PointOfSale.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {
    List<Customer> findAll();

    Optional<Customer> findByPhone(String phone);
}

package com.example.PointOfSale.service;

import com.example.PointOfSale.dao.CustomerPaging;
import com.example.PointOfSale.dao.CustomerRepository;
import com.example.PointOfSale.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerPaging customerPaging;

    public Page<Customer> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);

        return customerPaging.findAll(pageable);
    }

    public List<Customer> listAll() {
        return customerRepository.findAll();
    }

    public Customer add(Customer customer) {
        return customerRepository.save(customer);
    }


    public Optional<Customer> findById(int id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> findByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }


}

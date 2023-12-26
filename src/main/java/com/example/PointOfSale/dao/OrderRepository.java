package com.example.PointOfSale.dao;

import com.example.PointOfSale.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {

    List<Orders> findByCustomer_CustomerID(int id);
    
    
}

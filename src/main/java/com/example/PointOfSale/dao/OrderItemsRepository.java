package com.example.PointOfSale.dao;

import com.example.PointOfSale.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
    List<OrderItems> findByOrders_OrderId(String orderId);
}

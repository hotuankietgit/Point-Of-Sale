package com.example.PointOfSale.service;

import com.example.PointOfSale.dao.OrderItemsRepository;
import com.example.PointOfSale.model.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderItemService {
    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    public OrderItemService(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }
    public List<OrderItems> findOrderItemsByOrderId(String orderId){
        return orderItemsRepository.findByOrders_OrderId(orderId);
    }
}

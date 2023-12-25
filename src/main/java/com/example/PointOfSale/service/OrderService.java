package com.example.PointOfSale.service;

import com.example.PointOfSale.dao.OrderItemsRepository;
import com.example.PointOfSale.dao.OrderRepository;
import com.example.PointOfSale.model.OrderItems;
import com.example.PointOfSale.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;


    public List<Orders> getAllOrders() {
       return orderRepository.findAll();
      }
        @Autowired
        private OrderItemsRepository orderItemsRepository;

      // create new order
        public boolean createOrder(Orders orders) {
            orderRepository.save(orders);
            return true;
        }

      // add one item to cart
         public boolean addNewOrderItems(OrderItems orderItems) {
            orderItemsRepository.save(orderItems);
            return true;
         }

         public List<Orders> findOrderByCustomer(int customerID){
            return orderRepository.findByCustomer_CustomerID(customerID);
         }

}

package com.example.PointOfSale.controller;

import com.example.PointOfSale.model.Orders;
import com.example.PointOfSale.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/api/v1/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @GetMapping
  public List<Orders> getAllOrders() {
    return orderService.getAllOrders();
  }
  @PostMapping("")
  public ResponseEntity<Orders> createOrder(@RequestBody Orders orders) {
    return null;
  }
}

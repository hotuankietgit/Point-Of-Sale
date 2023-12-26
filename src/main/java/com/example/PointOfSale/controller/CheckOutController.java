package com.example.PointOfSale.controller;

import com.example.PointOfSale.model.*;
import com.example.PointOfSale.service.AccountService;
import com.example.PointOfSale.service.CustomerService;
import com.example.PointOfSale.service.OrderService;
import com.example.PointOfSale.service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@RestController
public class CheckOutController {

  @Autowired
  private CustomerService customerService;
  @Autowired
  private productService productService1;
  @Autowired
  private OrderService orderService;
  @Autowired AccountService accountService;
  

  @PostMapping("/checkout")
  public View handleCheckout(@RequestBody CheckoutData data, @AuthenticationPrincipal UserDetails userDetails) {
    System.out.println("Data get from client: " + data.toString());
    System.out.println(userDetails.getUsername());

    String phoneNumber = data.getPhoneNumber();

    Double balance = Double.parseDouble(data.getBalance().replace("$", ""));
//    System.out.println("balance"+balance);
    List<ProductData> productData = data.getProductData();
//    System.out.println(productData);
    double totalAmount = 0;
    for(ProductData p : productData){
      Product product = productService1.findByBarcodeOrProductName(p.getProductId());
      totalAmount += (Integer.parseInt(p.getQuantity()) * product.getRetailPrice());
    }

    double paidAmount = Double.parseDouble(data.getPaidAmount());

//    System.out.println(phoneNumber + " paid: " + paidAmount);

    // Tạo đối tượng đơn hàng
    Orders orders = new Orders();

//        orders.setCustomer(customerService.getCustomerById(phoneNumber));

    Optional<Customer> customerOptional =  customerService.findByPhone(phoneNumber);
    if(customerOptional.isPresent()){
      Customer customer = customerOptional.get();
      orders.setCustomer(customer);
    }

    orders.setTotalAmount(totalAmount);
    orders.setBalance(balance);
    orders.setPaidAmount(paidAmount);
  

    List<OrderItems> itemsList = new ArrayList<>();
    for (ProductData i : productData) {
      System.out.println("id "+ i.getProductId());
      int quantity = Integer.parseInt(i.getQuantity());
//      System.out.println("quan: "+quantity);
      Product product = productService1.findByBarcodeOrProductName(i.getProductId());

      System.out.println("Test add ProductID to OrderItem: " + product);

      if(product != null){
        int stockQuantity = product.getQuantity();
        int updateQuantity = stockQuantity - quantity;
        product.setQuantity(updateQuantity);
      }

      OrderItems orderItems = new OrderItems();
      orderItems.setQuantity(quantity);
      orderItems.setProduct(product);
      orderItems.setOrders(orders);
      Account account = accountService.getAccountByUsername(data.getEmployeeId());
     

      System.out.println("Test OrderItem has a right Product: " + orderItems.getProduct());
      itemsList.add(orderItems);
    }

    orders.setOrderItems(itemsList);
    orders.setAccount(accountService.getAccountByUsername(data.getEmployeeId()));
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    try {
      currentDate = dateFormat.parse(dateFormat.format(currentDate));
    } catch (Exception e) {
      e.printStackTrace();
    }

    orders.setDatePurchase(currentDate);
    orderService.createOrder(orders);

    return new RedirectView("transaction");
  }

}

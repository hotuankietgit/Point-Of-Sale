package com.example.PointOfSale.controller;

import com.example.PointOfSale.model.Customer;
import com.example.PointOfSale.model.OrderItems;
import com.example.PointOfSale.model.Orders;
import com.example.PointOfSale.model.Product;
import com.example.PointOfSale.service.CustomerService;
import com.example.PointOfSale.service.OrderItemService;
import com.example.PointOfSale.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.PointOfSale.service.productService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired private CustomerService customerService;

    @Autowired private OrderService orderService;

    @Autowired private OrderItemService orderItemService;

    @Autowired private productService productService;
//    , @Autowired OrderItemService orderItemService
    public CustomerController(@Autowired CustomerService customerService, @Autowired OrderService orderService, @Autowired OrderItemService orderItemService){
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }


    @GetMapping("")
    public String showCustomerList(Model model){
        return listByPage(model, 1);

    }


    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage){
        Page<Customer> page = customerService.getAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Customer> customers = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("customers", customers);
        model.addAttribute("present", customers.size());

        return "Customer/customer";
    }



    @GetMapping("/add")
    public String addCustomerForm(Model model){
        Customer customer = new Customer();

        model.addAttribute("pageTitle", "Add New Customer");
        model.addAttribute("customer", customer);
        return "Customer/addCustomer";
    }


    @PostMapping("/add")
    public String addCustomer(Customer customer , Model model){
        customerService.add(customer);
        return "redirect:customers/add";
    }


    @PostMapping("/add-trans")
    public String addCustomerFromTransaction(Customer customer , Model model){
        customerService.add(customer);
        return "redirect:/transaction";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model, RedirectAttributes ra){
        try {
            Optional<Customer> result = customerService.findById(id);

            if(result.isPresent()){
                Customer customer = result.get();
                model.addAttribute("pageTitle", "Edit Customer");
                model.addAttribute("customer", customer);
            }
            else{
                System.out.println("Don't see customer");
                ra.addFlashAttribute("message", "Don't see Customer");
            }
            return "Customer/addCustomer";
        }catch (Exception e){
            e.printStackTrace();
            ra.addFlashAttribute("message", "Don't see customer");
            return "redirect:/customers";
        }

    }

    @PostMapping("/remove")
    public String removePost(@RequestParam(value="options[]", required = false) String[] options){
        if (options == null){
            return "redirect:/customers";
        }
        for (String option : options) {

            int customerId = Integer.valueOf(option);
            customerService.deleteById(customerId);
        }
        return "redirect:/customers";
    }

    @GetMapping("order/{customerId}")
    public String viewHistoryOrder(@PathVariable("customerId") int customerId, Model model){
        List<Orders> orders = orderService.findOrderByCustomer(customerId);
        System.out.println(orders.size());
//        List<OrderItems> orderItems = null;
        model.addAttribute("customer",customerId);
        model.addAttribute("orderHistory", orders);
        for(Orders o : orders){
//            orderItems = orderItemService.findOrderItemsByOrderId(o.getOrderId());
//            System.out.println(orderItems);
            System.out.println(o.getDatePurchase());
        }
//        model.addAttribute("orderItems", orderItems);




        return "Customer/viewHistoryOrder";
    }

    @GetMapping("viewDetail/{orderId}")
    public String viewDetail(@PathVariable("orderId") String orderId, Model model){

        System.out.println(orderId);
        List<OrderItems> orderItems = orderItemService.findOrderItemsByOrderId(orderId);
        model.addAttribute("detail",orderItems);


        List<Product> products = new ArrayList<>();

        for (OrderItems o : orderItems){
            if(o.getProduct() != null){
                Product p = o.getProduct();
//                System.out.println(p);
                System.out.println("Product: "+o.getProduct().getProductName());
//                productService.getProductById(o.getProduct());
                products.add(p);
            }
        }

        model.addAttribute("products",products);
        return "Customer/viewDetail";
    }
}

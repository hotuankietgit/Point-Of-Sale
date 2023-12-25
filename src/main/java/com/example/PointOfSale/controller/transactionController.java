package com.example.PointOfSale.controller;

import com.example.PointOfSale.model.Customer;
import com.example.PointOfSale.model.Product;
import com.example.PointOfSale.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.PointOfSale.service.productService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/transaction")
public class transactionController {
    @Autowired
    private productService productService;
    @Autowired
    private CustomerService customerService;


    //    private List<Product> cart = new ArrayList<>();
    private List<Product> cart;

    public transactionController(@Autowired productService productService, @Autowired CustomerService customerService){
        this.cart = new ArrayList<>();
        this.productService = productService;
        this.customerService = customerService;
    }



    @GetMapping("")
    public String viewCart(Model model){


        model.addAttribute("cart", cart);
        int total = 0;
        if(!cart.isEmpty()){
            for(Product p : cart){

                total += p.getQuantity();
            }
        }

        model.addAttribute("cartSize", total);

        return "pos";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cart.clear(); // Clear the cart
        return "redirect:/transaction";
    }



    @PostMapping("")
    public String findProduct(@RequestParam("keyword") String keyword, Model model) {
//        if (barcode == null || barcode.isEmpty()) {
//            // Barcode is empty or not provided, redirect or handle as needed
//            // For example:
//            return "redirect:/transaction";
//        }
//        Optional<Product> optionalProduct = productService.findByBarcode(barcode);
        Product optionalProduct = productService.findByBarcodeOrProductName(keyword);

        if (optionalProduct != null) {
            Product product = optionalProduct;

            // Check if the product is already in the cart
            boolean found = false;
            for (Product p : cart) {
                if (p.getBarcode().equals(product.getBarcode()) ) {
                    // Update the quantity and set found to true
                    if(product.getQuantity() > 0){
//                        model.addAttribute("zeroQuantityAlert", true);
//                        return "pos";
                        p.setQuantity(p.getQuantity() + 1);
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                if(product.getQuantity() > 0){
                    // If the product is not in the cart, add it with quantity 1
                    product.setQuantity(1);
                    cart.add(product);

                }
                else {
                    model.addAttribute("zeroQuantityAlert", true);
                    System.out.println("Zero quantity alert set!");
//                    return "pos";

                }
            }


        } else {
            System.out.println("Don't see product");
        }
        return "redirect:/transaction";
    }



    @PostMapping("/updateQuantity")
    public String updateQuantity(@RequestParam("barcode") String barcode, @RequestParam("quantity") int quantity) {

        for (Product p : cart) {
            if (p.getBarcode().equals(barcode)) {

                p.setQuantity(quantity);
                break;
            }
        }
        return "redirect:/transaction";
    }

    @PostMapping("/remove")
    public String removePost(@RequestParam(value="options[]", required = false) String[] options){
        if (options == null){
            return "redirect:/transaction";
        }
        for (String option : options) {
//            System.out.println("barcode" + option);
            for(Product p : cart){
//                System.out.println("p: " + p);
                if(p.getBarcode().equals(option)){
                    cart.remove(p);
                    break;
                }
                else{
                    System.out.println("Can't find product");
                }
            }

        }
        return "redirect:/transaction";
    }

    @GetMapping("/check-customer")
    public ResponseEntity<String> checkCustomer(@RequestParam("phoneNumber") String phoneNumber) {
        Optional<Customer> optionalCustomer = customerService.findByPhone(phoneNumber);
        String response;

        if (optionalCustomer.isPresent()) {
            response = "{\"exists\": true, \"message\": \"This phone number already exists in the database.\"}";
        } else {
            response = "{\"exists\": false, \"message\": \"This phone number is for a new customer.\"}";
        }

        return ResponseEntity.ok().body(response);
    }


}


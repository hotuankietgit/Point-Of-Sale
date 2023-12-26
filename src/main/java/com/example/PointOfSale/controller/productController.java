package com.example.PointOfSale.controller;

import com.example.PointOfSale.model.Category;
import com.example.PointOfSale.model.Product;
import com.example.PointOfSale.model.ProductProfit;
import com.example.PointOfSale.model.ProductProfitByDate;
import com.example.PointOfSale.service.OrderItemService;
import com.example.PointOfSale.service.categoryService;
import com.example.PointOfSale.service.productService;
import com.example.PointOfSale.utils.uploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Printable;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class productController {

    @Autowired
    private   categoryService categoryService;
    @Autowired
    private productService productService;
    @Autowired OrderItemService orderItemService;

    @GetMapping("")
    public String showProducts(Model model){

        return listByPage(model, 1);
    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage){
        Page<Product> page = productService.getAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Product> productList = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("products", productList);
        model.addAttribute("present", productList.size());

        return "index";
    }


    @GetMapping("/add")
    public String addProductForm(Model model){
        Product product = new Product();
        List<Category> categories = categoryService.getCategory();

        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Add New Product");
        model.addAttribute("product", product);
        return "add";
    }


    //    @PostMapping("/add")
//    public String addProduct(Product product, @RequestParam("fileImage") MultipartFile multipartFile, Model model) throws IOException {
//        if(!multipartFile.isEmpty()){
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            product.setImage(fileName);
//
////            Optional<Product> optionalProduct = productService.findByBarcode(product.getBarcode());
////            if(optionalProduct.isPresent()){
////                model.addAttribute("Error", "Please enter another barcode");
////                return "add";
//////                return "redirect:/products/add";
////            }
//            Product addProduct = productService.add(product);
//
//
//            String upload = "images";
//            uploadImage.saveFile(upload, fileName, multipartFile);
//        }
//        else {
//            if(product.getImage().isEmpty()){
//                product.setImage(null);
//                productService.add(product);
//
//            }
//
//        }
//
//        productService.add(product);
//        return "redirect:/products";
//    }
//
//
//    @GetMapping("/edit/{id}")
//    public String edit(@PathVariable("id") int id, Model model){
//        try {
//            Optional<Product> result = productService.findById(id);
//
//            if(result.isPresent()){
//                Product product = result.get();
//                String previousImage = product.getImage();
//
//
//                model.addAttribute("pageTitle", "Edit Product");
//                model.addAttribute("previousImage", previousImage);
//
//                List<Category> categories = categoryService.getCategory();
//                model.addAttribute("categories", categories);
//                model.addAttribute("product", product);
//            }
//            else{
//                System.out.println("Don't see product");
//            }
//            return "add";
//        }catch (Exception e){
//            e.printStackTrace();
//
//            return "redirect:/products";
//        }
//
//    }
    @PostMapping("/add")
    public String addProduct(Product product, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            product.setImage(fileName);

            Product addProduct = productService.add(product);
            String upload = "images";
            uploadImage.saveFile(upload, fileName, multipartFile);
        }
        else {
            if(product.getImage().isEmpty()){
                product.setImage(null);
                productService.add(product);

            }

        }

        productService.add(product);
        return "redirect:/products";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model, RedirectAttributes ra){
        try {
            Optional<Product> result = productService.findById(id);

            if(result.isPresent()){
                Product product = result.get();
                String previousImage = product.getImage();


                model.addAttribute("pageTitle", "Edit Product");
                model.addAttribute("previousImage", previousImage);

                List<Category> categories = categoryService.getCategory();
                model.addAttribute("categories", categories);
                model.addAttribute("product", product);
            }
            else{
                System.out.println("Don't see product");
                ra.addFlashAttribute("message", "Don't see Product");
            }
            return "add";
        }catch (Exception e){
            e.printStackTrace();
            ra.addFlashAttribute("message", "Don't see product");
            return "redirect:/products";
        }

    }

    @PostMapping("/remove")
    public String removePost(@RequestParam(value="options[]", required = false) String[] options){
        if (options == null){
            return "redirect:/products";
        }


        for (String option : options) {

            int productId = Integer.valueOf(option);
            if(orderItemService.checkProductExist(productId)){
//
                return "redirect:/products?orderItemError=true";
            }
            productService.deleteById(productId);
        }
        return "redirect:/products";
    }

    @GetMapping("/profits")
    public ResponseEntity<List<ProductProfit>> getProfitData(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate) {

        // Convert String to java.sql.Date
        Date startDateSql = Date.valueOf(startDate);
        Date endDateSql = Date.valueOf(endDate);

        List<ProductProfit> productProfits = productService.getProductProfits(startDateSql, endDateSql);
        
        return new ResponseEntity<>(productProfits, HttpStatus.OK);
    }
    @GetMapping("/profits-by-date")
    public ResponseEntity<List<ProductProfitByDate>> getProfitDataByDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate) {

        // Convert String to java.sql.Date
        Date startDateSql = Date.valueOf(startDate);
        Date endDateSql = Date.valueOf(endDate);

        List<ProductProfitByDate> productProfits = productService.getTotalProfitsByDate(startDateSql, endDateSql);

        return new ResponseEntity<>(productProfits, HttpStatus.OK);
    }

}

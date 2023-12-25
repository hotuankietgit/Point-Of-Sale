package com.example.PointOfSale.controller;

import com.example.PointOfSale.model.Category;
import com.example.PointOfSale.service.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class categoryController {

    @Autowired
    private categoryService categoryService;

    public categoryController(@Autowired categoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String showCategory(Model model){
        List<Category> categories = new ArrayList<>();
        categories = categoryService.getCategory();
        model.addAttribute("categories", categories);
        return "category";
    }

    @PostMapping("/add")
    public String addCategory(Category category){
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    @PostMapping("/remove")
    public String removePost(@RequestParam(value="options[]", required = false) String[] options){
        if (options == null){
            return "redirect:/categories";
        }
        for (String option : options) {

            int productId = Integer.valueOf(option);
            categoryService.deleteById(productId);
        }
        return "redirect:/categories";
    }


}

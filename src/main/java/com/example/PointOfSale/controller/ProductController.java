package com.example.PointOfSale.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/temp")
public class ProductController {

    @GetMapping("/home")
    public ModelAndView getProductPage(@AuthenticationPrincipal UserDetails userDetails){
        ModelAndView mv = new ModelAndView("/temp/temp");
        mv.addObject("user", userDetails);
        return mv;
    }

    @GetMapping("/product")
    public ModelAndView getProductAll(Principal principal){
        ModelAndView mv = new ModelAndView("/temp/temp");
        mv.addObject("user", principal.getName());
        return mv;
    }
}

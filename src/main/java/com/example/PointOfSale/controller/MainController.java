package com.example.PointOfSale.controller;

import com.example.PointOfSale.model.Account;
import com.example.PointOfSale.service.AccountDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
@RequestMapping("")
public class MainController {
    @Autowired
    AccountDetailsService accountDetailsService;
    @GetMapping("/test")
    public String getTest(){
        Account account = accountDetailsService.getEmployeeByUsername("admin");
        account.getRoles().forEach(System.out::println);
        return "denied";
    }

    @PostMapping("/test")
    public String postTest(){
        Account account = accountDetailsService.getEmployeeByUsername("admin");
        account.getRoles().forEach(System.out::println);
        return "denied";
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(@AuthenticationPrincipal UserDetails userDetails){
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for (GrantedAuthority authority : authorities){
            if (authority.getAuthority().equals("Admin")){
                return new ModelAndView("redirect:/admin/index");
            }
        }
        return new ModelAndView("redirect:/transaction");
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(HttpServletRequest request){
        String error = (String) request.getSession().getAttribute("error");
        request.getSession().removeAttribute("error");

        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @GetMapping("/denied")
    public String getErrorPage(){
        return "denied";
    }
}

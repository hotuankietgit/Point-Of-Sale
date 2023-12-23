package com.example.PointOfSale.controller;

import com.example.PointOfSale.model.Account;
import com.example.PointOfSale.service.AccountDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    AccountDetailsService accountDetailsService;

    @GetMapping("/login")
    public ModelAndView getStaffLoginPage(@RequestParam String token, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/staff/login");
        modelAndView.addObject("token", token);
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView postStaffLogin(@RequestParam("token") String token, Account account, String newPassword){
        String error = accountDetailsService.activateNewStaff(account, token, newPassword);

        //if success return login page
        if (error == null){
            return new ModelAndView("/login");
        }

        //if failed return staff login with error message
        ModelAndView modelAndView = new ModelAndView("/staff/login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }
}

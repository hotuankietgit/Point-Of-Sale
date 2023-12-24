package com.example.PointOfSale.controller;

import com.example.PointOfSale.model.Account;
import com.example.PointOfSale.service.AccountDetailsService;
import com.example.PointOfSale.service.EmailServiceImpl;
import com.example.PointOfSale.utils.FileStorageService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AccountDetailsService accountDetailsService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/home")
    public ModelAndView adminHomePage(Principal principal){
        ModelAndView modelAndView = new ModelAndView("admin/index");
        modelAndView.addObject("employees", accountDetailsService.getEmployees());
        modelAndView.addObject("admin", principal.getName());
        return modelAndView;
    }

    @RequestMapping("/index")
    public String index(@RequestParam(value="page",required = false) String page, Model m){
        int total = accountDetailsService.numberOfEmployees();
        if (page == null || Integer.parseInt(page) <= 0){
            if (page != null && Integer.parseInt(page) <= 0){
                return "error-404";
            }
            List<Account> list = accountDetailsService.pagination(0);
            m.addAttribute("mainPage", 1);
            m.addAttribute("offset", 0);
            m.addAttribute("employees", list);
            m.addAttribute("total", total);
            m.addAttribute("present", list.size());
            return "admin/index";
        }else{
            int UIPage = Integer.parseInt(page);
            int realPage =  UIPage - 1;
            int offset = realPage / 5;

            List<Account> list = accountDetailsService.pagination(realPage);
            int present = 0;
            if (list.isEmpty()){
                present = total;
            }else{
                present = list.size() + realPage*5;
            }
            m.addAttribute("employees", list);
            m.addAttribute("mainPage", UIPage);
            m.addAttribute("offset", offset);
            m.addAttribute("total", total);
            m.addAttribute("present", present);
            return "admin/index";
        }
    }

    @GetMapping("/register")
    public ModelAndView adminRecruitPage(Principal principal){
        ModelAndView modelAndView = new ModelAndView("admin/register");
        modelAndView.addObject("admin", principal.getName());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView adminRecruit(Account account) throws MessagingException {
        ModelAndView modelAndView = new ModelAndView("admin/register");
        Account serviceAccount = accountDetailsService.recruitEmployee(account);

        if (serviceAccount == null){
            modelAndView.addObject("error", "Failed adding new employee");
            return modelAndView;
        }

        String url = "http://localhost:8080/staff/login?token=" + serviceAccount.getTokenLogin();
        emailService.sendMimeEmail(serviceAccount, "Account activation", url);
        modelAndView.addObject("message", "Successfully adding new employee. A login link has been sent to new staff");
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView getEmployeeDetailPage(@RequestParam String username){
        Account account = accountDetailsService.getEmployeeByUserame(username);
        if (account == null){
            return new ModelAndView("error-404");
        }

        ModelAndView modelAndView = new ModelAndView("admin/detail");
        modelAndView.addObject("account", account);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateEmployee(@RequestParam MultipartFile file, Account account, ModelMap model){

        if (file != null && file.getSize() != 0){
            fileStorageService.storeImageToPublicFolder(file, account.getUsername());
        }
        accountDetailsService.updateEmployee(account);

        model.addAttribute("username", account.getUsername());
        return new ModelAndView("redirect:/admin/edit", model);
    }
}

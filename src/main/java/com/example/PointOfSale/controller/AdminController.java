package com.example.PointOfSale.controller;

import com.example.PointOfSale.model.Account;
import com.example.PointOfSale.service.AccountDetailsService;
import com.example.PointOfSale.service.EmailServiceImpl;
import com.example.PointOfSale.utils.FileStorageService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

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
        ModelAndView modelAndView = new ModelAndView("admin/home");
        modelAndView.addObject("employees", accountDetailsService.getEmployees());
        modelAndView.addObject("admin", principal.getName());
        return modelAndView;
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

    @GetMapping("/employeeDetail")
    public ModelAndView getEmployeeDetailPage(@RequestParam String email){
        Account account = accountDetailsService.getEmployee(email);
        if (account == null){
            return new ModelAndView("error-404");
        }

        ModelAndView modelAndView = new ModelAndView("admin/detail");
        modelAndView.addObject("account", account);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateEmployee(@RequestParam MultipartFile file, Account account){

        if (file != null){
            fileStorageService.storeImageToPublicFolder(file, account.getUsername());
        }
        accountDetailsService.updateEmployee(account);

        ModelAndView modelAndView = new ModelAndView("admin/detail");
        modelAndView.addObject("message", "Successfully updated Employee");
        return modelAndView;
    }
}

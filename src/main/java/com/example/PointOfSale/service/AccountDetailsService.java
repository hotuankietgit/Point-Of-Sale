package com.example.PointOfSale.service;

import com.example.PointOfSale.dao.AccountRepository;
import com.example.PointOfSale.model.Account;
import com.example.PointOfSale.model.AccountDetails;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AccountDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByUsername(username);
        if (account == null){
            throw new UsernameNotFoundException("Account does not exist!");
        }

        if (account.isLocked()){
            throw new UsernameNotFoundException("Account is locked. Please consult admin@gmail.com");
        }

        if (!account.isStatus()){
            throw new UsernameNotFoundException("New staff should login from the login link");
        }

        return new AccountDetails(account);
    }

    public Account getEmployee(String email){
        return accountRepository.findByEmail(email);
    }

    public void updateEmployee(Account account){
        Account accountService = accountRepository.getAccountByEmail(account.getEmail());
        accountService.setLocked(account.isLocked());
        accountService.setProfilePicture(account.getUsername() + ".jpg");
        accountRepository.save(accountService);
    }

    public List<Account> getEmployees(){
        return accountRepository.findAll();
    }

    public Account recruitEmployee(Account account){
        if (accountRepository.findByEmail(account.getEmail()) == null){
            account.setPassword(passwordEncoder.encode(account.getUsername()));
            account.setStatus(false);
            account.setTokenExpiredDate(getLocalDateTimeWithSeconds(15));
            account.setTokenLogin(RandomStringUtils.randomAlphanumeric(10));
            accountRepository.save(account);
            return account;
        }
        return null;
    }

    public String activateNewStaff(Account account, String token, String newPassword){
        Account accountService = accountRepository.getAccountByUsername(account.getUsername());

        if (accountService == null)
            return "Account does not exist!";

        if (accountService.isLocked())
            return "Account is locked. Please consult admin@gmail.com";

        if (accountService.isStatus())
            return "Account is already activated";

        if (accountService.getTokenExpiredDate().isBefore(LocalDateTime.now()))
            return "Token is expired. Please consult admin@gmail.com to re-activate token";

        if (!accountService.getTokenLogin().equals(token))
            return "Token is invalid";

        accountService.setStatus(true);
        accountService.setPassword(newPassword);
        accountRepository.save(accountService);
        return null;
    }
    public LocalDateTime getLocalDateTimeWithSeconds(int seconds){
        LocalDateTime date = LocalDateTime.now();
        date = date.plusSeconds(seconds);
        return date;
    }
}
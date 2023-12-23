package com.example.PointOfSale.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AccountDetails implements UserDetails {

    private Account account;

    public AccountDetails(Account account){
        this.account = account;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = account.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role: roles){
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    public String getEmail(){
        return account.getEmail();
    }

    public String tokenLogin() {
        return account.getTokenLogin();
    }

    private LocalDateTime tokenExpiredDate(){
        return account.getTokenExpiredDate();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !account.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.isStatus();
    }
}

package com.example.PointOfSale.config;

import com.example.PointOfSale.service.AccountDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(){
        return new AccountDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new SecurityAuthenticationFailureHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/*").hasAuthority("Admin")
                        .requestMatchers("/agent/*").hasAuthority("Agent")
                        .requestMatchers("/temp/*").hasAuthority("Agent")

                        .requestMatchers("/products").hasAnyAuthority("Agent", "Admin")
                        .requestMatchers("/products/*").hasAnyAuthority("Admin")
                        .requestMatchers(HttpMethod.POST,"/products/add").hasAuthority("Admin")

                        .requestMatchers("/categories").hasAuthority("Admin")
                        .requestMatchers(HttpMethod.POST,"/categories/*").hasAuthority("Admin")

                        // .requestMatchers("/viewSales/*").hasAnyAuthority("Agent", "Admin")
                        // all totalAmount

                        .requestMatchers("/customers").hasAnyAuthority("Admin", "Agent")
                        .requestMatchers("/customers/*").hasAnyAuthority("Admin", "Agent")
                        .requestMatchers("/transaction").hasAnyAuthority("Admin", "Agent")
                        .requestMatchers("/transaction/*").hasAnyAuthority("Admin", "Agent")

                        .requestMatchers("/home").authenticated()
                        .requestMatchers(HttpMethod.POST,"/staff/login").permitAll()
                        .anyRequest().permitAll()).
                formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                        .failureHandler(authenticationFailureHandler())
                        ).
                exceptionHandling(ex -> ex
                        .accessDeniedPage("/denied")
                        ).
                build();
    }
}

package com.example.PointOfSale.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    @Id
    private String username;
    private String email;
    private String fullName;
    private String password;
    private String profilePicture;
    private String tokenLogin;

    private LocalDateTime tokenExpiredDate;
    private boolean status;
    private boolean locked;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles = new HashSet<>();

//    @OneToMany(mappedBy = "customer")
//    private List<Orders> orders;
}

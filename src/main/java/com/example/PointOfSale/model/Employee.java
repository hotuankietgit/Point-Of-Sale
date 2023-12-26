package com.example.PointOfSale.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Employee {
  @Id
  private String email;
  private String username;
  private String fullName;
  private String password;
  private String profilePicture;
  private String tokenLogin; // random
  private String ExpiredDate; // format dmy
  private boolean active;
  private boolean block;
//  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
//  @JsonBackReference
//  private List<Orders> orders;
}

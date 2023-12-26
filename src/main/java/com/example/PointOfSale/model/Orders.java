package com.example.PointOfSale.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "orders")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId")
public class Orders {

  @Id
  private String orderId;

  private double totalAmount;
  private double paidAmount;
  private double balance;
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date datePurchase;
//  @ManyToOne
//  @JoinColumn(name = "employee_id")
//  @JsonIdentityReference(alwaysAsId = true)
//  private Employee employee;// email
  @ManyToOne
  @JoinColumn(name = "employee_id")
  @JsonIdentityReference(alwaysAsId = true)
  private Account account;

  @ManyToOne
  @JoinColumn(name = "customerID")
  @JsonIdentityReference(alwaysAsId = true)
  private Customer customer;

  @OneToMany(mappedBy="orders",targetEntity = OrderItems.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
 @JsonManagedReference
  private List<OrderItems> orderItems;


//    @ManyToOne
//    @JoinColumn(name = "customerID")
//    @JsonIdentityReference(alwaysAsId = true)
//    private Account account;

  public void setOrderItems(
      List<OrderItems> orderItems) {
    this.orderItems = orderItems;
  }

  public Orders() {
    this.orderId = generateOrderId();
  }

  private String generateOrderId() {
    return UUID.randomUUID().toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Orders orders = (Orders) o;
    return Objects.equals(orderId, orders.orderId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId);
  }

}

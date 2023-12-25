package com.example.PointOfSale.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItems {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private int quantity;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  @JsonBackReference
//  @JsonProperty("product")
  private Product product;

  @ManyToOne(fetch = FetchType.EAGER)
  @JsonBackReference
  @JoinColumn(name = "order_id")
  private Orders orders;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderItems that = (OrderItems) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }


}

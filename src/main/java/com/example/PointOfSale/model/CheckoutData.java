package com.example.PointOfSale.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutData {
  private String phoneNumber;
  private String paidAmount;
  private String balance;

  @JsonAlias("productData")
  private String productDataJson;

  // Không serialize trường này
  @Getter
  private transient List<ProductData> productData;

  // getters and setters

  public void setProductDataJson(String productDataJson) {
    this.productDataJson = productDataJson;

    // Khi đặt productDataJson, parse chuỗi JSON thành danh sách ProductData
    ObjectMapper mapper = new ObjectMapper();
    try {
      this.productData = mapper.readValue(productDataJson, new TypeReference<List<ProductData>>() {});
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
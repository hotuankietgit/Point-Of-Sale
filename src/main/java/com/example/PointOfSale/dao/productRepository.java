package com.example.PointOfSale.dao;

import com.example.PointOfSale.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface productRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();

    Product getProductById(int id);

    Optional<Product> findByBarcode(String barcode);

    Optional<Product> findByBarcodeOrProductName(String barcode, String productName);
}

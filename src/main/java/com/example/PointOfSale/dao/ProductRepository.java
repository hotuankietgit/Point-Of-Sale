package com.example.PointOfSale.dao;

import com.example.PointOfSale.model.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();

    Product getProductById(int id);

    Optional<Product> findByBarcode(String barcode);

    Optional<Product> findByBarcodeOrProductName(String barcode, String productName);
    
    @Query(value = "SELECT p.product_name, SUM((p.retail_price - p.import_price) * oi.quantity) AS total_profit " +
            "FROM product p " +
            "JOIN order_items oi ON p.id = oi.product_id " +
            "JOIN orders o ON oi.order_id = o.order_id " +
            "WHERE o.date_purchase BETWEEN :startDate AND :endDate " +
            "GROUP BY p.product_name", nativeQuery = true)
     List<Object[]> findProductProfits(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
     
     // profit by date
     @Query(value = "SELECT DATE(o.date_purchase) AS date, " +
    	        "SUM((p.retail_price - p.import_price) * oi.quantity) AS total_profit " +
    	        "FROM product p " +
    	        "JOIN order_items oi ON p.id = oi.product_id " +
    	        "JOIN orders o ON oi.order_id = o.order_id " +
    	        "WHERE o.date_purchase BETWEEN :startDate AND :endDate " +
    	        "GROUP BY DATE(o.date_purchase)", nativeQuery = true)
    	     List<Object[]> findTotalProfitsByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

package com.example.PointOfSale.service;

import com.example.PointOfSale.model.Product;
import com.example.PointOfSale.model.ProductProfit;
import com.example.PointOfSale.model.ProductProfitByDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.PointOfSale.dao.productRepository;
import com.example.PointOfSale.dao.OrderItemsRepository;
import com.example.PointOfSale.dao.OrderRepository;
import com.example.PointOfSale.dao.productPaging;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class productService {
    @Autowired
    private productRepository productRepository;

    @Autowired
    private productPaging productPaging;
    @Autowired
    private OrderItemsRepository orderItemsRepository;


    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    public Page<Product> getAll(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);

        return productPaging.findAll(pageable);
    }

    public List<Product> listAll(){
        return productRepository.findAll();
    }

    public Product add(Product product){
        return productRepository.save(product);
    }

    public Optional<Product> findById(int id){
        return productRepository.findById(id);
    }


    public void deleteById(int id){
        productRepository.deleteById(id);
    }

    public Optional<Product> findByBarcode(String barcode){
        return productRepository.findByBarcode(barcode);
    }

    public Product findByBarcodeOrProductName(String keyword) {
        return productRepository.findByBarcodeOrProductName(keyword, keyword).orElse(null);
    }
    
    public List<ProductProfit> getProductProfits(Date startDate, Date endDate) {
    	   List<Object[]> resultsList = productRepository.findProductProfits(startDate, endDate);
    	   
    	   List<ProductProfit> productProfits = new ArrayList<>();
    	   for (Object[] result : resultsList) {
    	       String productName = (String) result[0];
    	       double profit = ((Number) result[1]).doubleValue();
    	       ProductProfit productProfit = new ProductProfit(productName, profit);
    	       productProfits.add(productProfit);
    	       
    	   }
    	   return productProfits;
    	}
    
    public List<ProductProfitByDate> getTotalProfitsByDate(Date startDate, Date endDate) {
        List<Object[]> resultsList = productRepository.findTotalProfitsByDate(startDate, endDate);

        List<ProductProfitByDate> productProfits = new ArrayList<>();
        for (Object[] result : resultsList) {
            String date = result[0].toString();
            double totalProfit = ((Number) result[1]).doubleValue();
            ProductProfitByDate productProfit = new ProductProfitByDate(date, totalProfit);
            productProfits.add(productProfit);
            System.out.println(productProfit);
        }
        return productProfits;
    }


}

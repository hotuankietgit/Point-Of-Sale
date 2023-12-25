package com.example.PointOfSale.service;

import com.example.PointOfSale.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.PointOfSale.dao.productRepository;
import com.example.PointOfSale.dao.productPaging;
import java.util.List;
import java.util.Optional;

@Service
public class productService {
    @Autowired
    private productRepository productRepository;

    @Autowired
    private productPaging productPaging;


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

}

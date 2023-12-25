package com.example.PointOfSale.dao;

import com.example.PointOfSale.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface productPaging extends PagingAndSortingRepository<Product, Integer> {
//    Page<Product> getAll(int currentPage);
}

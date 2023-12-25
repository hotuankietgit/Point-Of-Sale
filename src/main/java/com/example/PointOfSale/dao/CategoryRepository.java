package com.example.PointOfSale.dao;

import com.example.PointOfSale.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAll();
}

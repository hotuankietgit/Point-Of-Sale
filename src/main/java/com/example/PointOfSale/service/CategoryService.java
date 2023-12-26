package com.example.PointOfSale.service;

import com.example.PointOfSale.dao.CategoryRepository;
import com.example.PointOfSale.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired private CategoryRepository repo;


    public List<Category> getCategory(){
        return repo.findAll();
    }
    public Category addCategory(Category category){
        return repo.save(category);
    }

    public void deleteById(int id){
        repo.deleteById(id);
    }
}

package com.example.PointOfSale.dao;

import com.example.PointOfSale.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
    public Set<Role> findAll();
}

package com.example.PointOfSale.service;

import com.example.PointOfSale.dao.RoleRepository;
import com.example.PointOfSale.model.Role;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Set<Role> getRoles(){
        return roleRepository.findAll();
    }
}

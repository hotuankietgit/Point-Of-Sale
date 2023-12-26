package com.example.PointOfSale.service;

import com.example.PointOfSale.dao.RoleRepository;
import com.example.PointOfSale.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Set<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Role getRoleByName(String name){
        return roleRepository.getRoleByRoleName(name);
    }
}

package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserRoleRepository {
    @Autowired
    private EntityManager entityManager;


    public void addNewUser(UserRole userRole){
        entityManager.persist(userRole);
    }
}

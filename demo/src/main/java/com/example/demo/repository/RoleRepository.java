package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.entity.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class RoleRepository {
    @Autowired
    private EntityManager entityManager;
    public List<String> getRoleNames(String Username){
//        String sql="Select ur.Role.RoleName from " + UserRole.class.getName()+" ur " //
//        +" where ur.TaiKhoan.UserName=:Username";
        String sql = "SELECT ur.role.RoleName FROM " + UserRole.class.getName() + " ur " +
                "WHERE ur.taiKhoan.Username = :Username";
        Query query=this.entityManager.createQuery(sql, String.class);
        query.setParameter("Username",Username);
        return query.getResultList();
    }
    public Role findByRoleName(String roleName) {
        String jpql = "SELECT r FROM Role r WHERE r.RoleName = :roleName";
        TypedQuery<Role> query = entityManager.createQuery(jpql, Role.class);
        query.setParameter("roleName", roleName);
        return query.getSingleResult();
    }
    public void save(Role role){
        entityManager.persist(role);
    }
}

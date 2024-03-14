package com.example.demo.repository;

import com.example.demo.entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class TaiKhoanRepository {
    @Autowired
    private EntityManager entityManager;
    public TaiKhoan findUserAccount(String Username){
        try {
            String sql = "SELECT e.Username, e.EncrytedPassword, e.khachHang.CCCD, e.Enabled FROM " + TaiKhoan.class.getName() + " e WHERE e.Username = :Username";

            Query query=entityManager.createQuery(sql, TaiKhoan.class);
            query.setParameter("Username",Username);
            return (TaiKhoan) query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
    public boolean existsByUsername(String username) {
        Query query = entityManager.createQuery("SELECT 1 FROM TaiKhoan t WHERE t.Username = :username");
        query.setParameter("username", username);
        return query.getResultList().size() > 0;
    }
    public void save(TaiKhoan taiKhoan){
        entityManager.persist(taiKhoan);
    }
}

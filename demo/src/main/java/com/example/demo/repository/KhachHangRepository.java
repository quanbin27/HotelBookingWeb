package com.example.demo.repository;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Role;
import com.example.demo.entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class KhachHangRepository  {
    @Autowired
    private EntityManager entityManager;
    public void save(KhachHang khachHang){
        entityManager.persist(khachHang);
    }
    public void merge(KhachHang khachHang ){ entityManager.merge(khachHang);}
    public KhachHang findByCCCD(String CCCD) {
        try {
            String sql = "SELECT k FROM "+KhachHang.class.getName() +" k WHERE k.CCCD = :CCCD";

            Query query=entityManager.createQuery(sql, KhachHang.class);
            query.setParameter("CCCD",CCCD);
            return (KhachHang) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

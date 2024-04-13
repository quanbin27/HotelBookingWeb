package com.example.demo.repository;

import com.example.demo.entity.PhieuDat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuDatRepository extends JpaRepository<PhieuDat,String> {
    List<PhieuDat> findAll();
    PhieuDat save(PhieuDat phieuDat);
}

package com.example.demo.repository;

import com.example.demo.entity.KieuPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KieuPhongRepository extends JpaRepository<KieuPhong,String> {
    List<KieuPhong> findAll();
}

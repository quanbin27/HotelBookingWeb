package com.example.demo.repository;

import com.example.demo.entity.HangPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HangPhongRepository extends JpaRepository<HangPhong,String> {
    List<HangPhong> findAll();
}

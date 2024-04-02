package com.example.demo.repository;

import com.example.demo.entity.LoaiPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiPhongRepository extends JpaRepository<LoaiPhong,String> {

}

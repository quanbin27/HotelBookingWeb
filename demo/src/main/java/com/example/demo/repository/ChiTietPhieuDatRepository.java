package com.example.demo.repository;

import com.example.demo.entity.ChiTietPhieuDat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhieuDatRepository extends JpaRepository<ChiTietPhieuDat,Long> {

}

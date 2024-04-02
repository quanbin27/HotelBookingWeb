package com.example.demo.repository;

import com.example.demo.entity.HangPhong;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HangPhongRepository extends JpaRepository<HangPhong, String> {
    // Phương thức tùy chỉnh để tìm các hạng phòng theo mã loại phòng
    @Query("SELECT hp FROM HangPhong hp WHERE hp.loaiphong.MaLP = :maLP")
    List<HangPhong> findByLoaiPhongMaLP(@Param("maLP") String maLP);
    @Query("SELECT hp FROM HangPhong hp WHERE hp.MaHP = :maHP")
    HangPhong findByMaHP(@Param("maHP") String maHP);
    @Query(value = "EXEC GET_AVAILABLE_ROOM :maHP, :tungay, :denngay", nativeQuery = true)
    int getAvailableRoom(@Param("maHP") String maHP,
                         @Param("tungay") String tungay,
                         @Param("denngay") String denngay);
}

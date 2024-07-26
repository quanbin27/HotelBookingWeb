package com.example.demo.service;

import com.example.demo.entity.LoaiPhong;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ILoaiPhongService {

    LoaiPhong createLoaiPhong(LoaiPhong loaiPhong);

    List<LoaiPhong> findAll();

    LoaiPhong findById(String s);

    void deleteById(String s);

    LoaiPhong updateLoaiPhong(String s, LoaiPhong loaiPhongDetails);
}

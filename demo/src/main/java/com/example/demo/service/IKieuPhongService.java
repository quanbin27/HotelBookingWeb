package com.example.demo.service;

import com.example.demo.entity.KieuPhong;

import java.util.List;
import java.util.Optional;

public interface IKieuPhongService {

    KieuPhong createKieuPhong(KieuPhong kieuPhong);

    List<KieuPhong> findAll();

    KieuPhong findById(String s);

    void deleteById(String s);

    KieuPhong updateKieuPhong(String s, KieuPhong kieuPhongDetails);
}

package com.example.demo.service.impl;

import com.example.demo.entity.KieuPhong;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.KieuPhongRepository;
import com.example.demo.service.IKieuPhongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KieuPhongServiceImpl implements IKieuPhongService {
    KieuPhongRepository kieuPhongRepository;
    @Autowired
    public KieuPhongServiceImpl(KieuPhongRepository kieuPhongRepository) {
        this.kieuPhongRepository = kieuPhongRepository;
    }
    @Override
    public KieuPhong createKieuPhong(KieuPhong kieuPhong){
        return kieuPhongRepository.save(kieuPhong);
    }
    @Override
    public List<KieuPhong> findAll() {
        return kieuPhongRepository.findAll();
    }



    @Override
    public KieuPhong findById(String s) {
        return kieuPhongRepository.findById(s).orElseThrow(()->new CustomException("Kieu phong khong tim thay voi id "+s,"KIEUPHONG_NOT_FOUND"));
    }


    @Override
    public void deleteById(String s) {
        KieuPhong kieuPhong=kieuPhongRepository.findById(s).orElseThrow(()->new CustomException("Kieu phong khong tim thay voi id "+s,"KIEUPHONG_NOT_FOUND"));
        kieuPhongRepository.delete(kieuPhong);
    }
    @Override
    public KieuPhong updateKieuPhong(String s, KieuPhong kieuPhongDetails){
        KieuPhong kieuPhong=kieuPhongRepository.findById(s).orElseThrow(()->new CustomException("Kieu phong khong tim thay voi id "+s,"KIEUPHONG_NOT_FOUND"));
        kieuPhong.setTenKP(kieuPhongDetails.getTenKP());
        kieuPhong.setWifi(kieuPhongDetails.isWifi());
        kieuPhong.setDienTich(kieuPhongDetails.getDienTich());
        kieuPhong.setViewPhong(kieuPhongDetails.getViewPhong());
        kieuPhong.setMoTa(kieuPhongDetails.getMoTa());
        kieuPhong.setImage(kieuPhongDetails.getImage());
        return kieuPhongRepository.save(kieuPhong);
    }
}

package com.example.demo.service.impl;

import com.example.demo.entity.LoaiPhong;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.LoaiPhongRepository;
import com.example.demo.service.ILoaiPhongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiPhongServiceImpl implements ILoaiPhongService {
    LoaiPhongRepository loaiPhongRepository;
    @Autowired
    public LoaiPhongServiceImpl(LoaiPhongRepository loaiPhongRepository) {
        this.loaiPhongRepository = loaiPhongRepository;
    }
    @Override
    public LoaiPhong createLoaiPhong(LoaiPhong loaiPhong){
        return loaiPhongRepository.save(loaiPhong);
    }
    @Override
    public List<LoaiPhong> findAll() {
        return loaiPhongRepository.findAll();
    }


    @Override
    public LoaiPhong findById(String s) {
        return loaiPhongRepository.findById(s).orElseThrow(()->new CustomException("Loai phong khong tim thay voi id "+s,"LOAIPHONG_NOT_FOUND"));
    }


    @Override
    public void deleteById(String s) {
        LoaiPhong loaiPhong=loaiPhongRepository.findById(s).orElseThrow(()->new CustomException("Loai phong khong tim thay voi id "+s,"LOAIPHONG_NOT_FOUND"));
        loaiPhongRepository.delete(loaiPhong);
    }
    @Override
    public LoaiPhong updateLoaiPhong(String s, LoaiPhong loaiPhongDetails){
        LoaiPhong loaiPhong=loaiPhongRepository.findById(s).orElseThrow(()->new CustomException("Loai phong khong tim thay voi id "+s,"LOAIPHONG_NOT_FOUND"));
        loaiPhong.setMaLP(loaiPhongDetails.getMaLP());
        loaiPhong.setMoTa(loaiPhongDetails.getMoTa());
        loaiPhong.setTenLP(loaiPhongDetails.getTenLP());
        return loaiPhongRepository.save(loaiPhong);
    }



}

package com.example.demo.service.impl;

import com.example.demo.entity.LoaiPhong;
import com.example.demo.repository.LoaiPhongRepository;
import com.example.demo.service.ILoaiPhongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiPhongServiceImpl implements ILoaiPhongService {
    @Autowired
    LoaiPhongRepository loaiPhongRepository;

    public LoaiPhongServiceImpl(LoaiPhongRepository loaiPhongRepository) {
        this.loaiPhongRepository = loaiPhongRepository;
    }

    @Override
    public List<LoaiPhong> findAll() {
        return loaiPhongRepository.findAll();
    }

    @Override
    public List<LoaiPhong> findAllById(Iterable<String> strings) {
        return loaiPhongRepository.findAllById(strings);
    }

    @Override
    public <S extends LoaiPhong> S save(S entity) {
        return loaiPhongRepository.save(entity);
    }

    @Override
    public Optional<LoaiPhong> findById(String s) {
        return loaiPhongRepository.findById(s);
    }

    @Override
    public long count() {
        return loaiPhongRepository.count();
    }

    @Override
    public void deleteById(String s) {
        loaiPhongRepository.deleteById(s);
    }

    @Override
    public void delete(LoaiPhong entity) {
        loaiPhongRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        loaiPhongRepository.deleteAll();
    }

    @Override
    public List<LoaiPhong> findAll(Sort sort) {
        return loaiPhongRepository.findAll(sort);
    }
}

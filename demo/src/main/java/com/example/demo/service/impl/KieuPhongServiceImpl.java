package com.example.demo.service.impl;

import com.example.demo.entity.KieuPhong;
import com.example.demo.repository.KieuPhongRepository;
import com.example.demo.service.IKieuPhongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KieuPhongServiceImpl implements IKieuPhongService {
    @Autowired
    KieuPhongRepository kieuPhongRepository;

    public KieuPhongServiceImpl(KieuPhongRepository kieuPhongRepository) {
        this.kieuPhongRepository = kieuPhongRepository;
    }

    @Override
    public List<KieuPhong> findAll() {
        return kieuPhongRepository.findAll();
    }

    @Override
    public List<KieuPhong> findAllById(Iterable<String> strings) {
        return kieuPhongRepository.findAllById(strings);
    }

    @Override
    public <S extends KieuPhong> S save(S entity) {
        return kieuPhongRepository.save(entity);
    }

    @Override
    public Optional<KieuPhong> findById(String s) {
        return kieuPhongRepository.findById(s);
    }

    @Override
    public long count() {
        return kieuPhongRepository.count();
    }

    @Override
    public void deleteById(String s) {
        kieuPhongRepository.deleteById(s);
    }

    @Override
    public void delete(KieuPhong entity) {
        kieuPhongRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        kieuPhongRepository.deleteAll();
    }
}

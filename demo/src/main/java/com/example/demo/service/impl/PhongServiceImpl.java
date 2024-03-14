package com.example.demo.service.impl;

import com.example.demo.entity.Phong;
import com.example.demo.repository.PhongRepository;
import com.example.demo.service.IPhongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhongServiceImpl implements IPhongService {
    @Autowired
    PhongRepository phongRepository;

    public PhongServiceImpl(PhongRepository phongRepository) {
        this.phongRepository = phongRepository;
    }

    @Override
    public List<Phong> findAll() {
        return phongRepository.findAll();
    }

    @Override
    public <S extends Phong> S save(S entity) {
        return phongRepository.save(entity);
    }

    @Override
    public Optional<Phong> findById(String s) {
        return phongRepository.findById(s);
    }

    @Override
    public long count() {
        return phongRepository.count();
    }

    @Override
    public void deleteById(String s) {
        phongRepository.deleteById(s);
    }

    @Override
    public void delete(Phong entity) {
        phongRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends Phong> entities) {
        phongRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        phongRepository.deleteAll();
    }

    @Override
    public List<Phong> findAll(Sort sort) {
        return phongRepository.findAll(sort);
    }
}

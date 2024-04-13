package com.example.demo.service.impl;

import com.example.demo.entity.PhieuDat;
import com.example.demo.repository.PhieuDatRepository;
import com.example.demo.service.IPhietDatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhieuDatServiceImpl implements IPhietDatService {
    @Autowired
    PhieuDatRepository phieuDatRepository;

    public PhieuDatServiceImpl(PhieuDatRepository phieuDatRepository) {
        this.phieuDatRepository = phieuDatRepository;
    }

    @Override
    public List<PhieuDat> findAll() {
        return phieuDatRepository.findAll();
    }

    @Override
    public List<PhieuDat> findAllById(Iterable<String> strings) {
        return phieuDatRepository.findAllById(strings);
    }



    @Override
    public Optional<PhieuDat> findById(String s) {
        return phieuDatRepository.findById(s);
    }

    @Override
    public long count() {
        return phieuDatRepository.count();
    }

    @Override
    public void deleteById(String s) {
        phieuDatRepository.deleteById(s);
    }

    @Override
    public void delete(PhieuDat entity) {
        phieuDatRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        phieuDatRepository.deleteAllById(strings);
    }

    @Override
    public void deleteAll() {
        phieuDatRepository.deleteAll();
    }
}

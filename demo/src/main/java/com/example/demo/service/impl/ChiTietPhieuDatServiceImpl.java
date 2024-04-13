package com.example.demo.service.impl;

import com.example.demo.entity.ChiTietPhieuDat;
import com.example.demo.repository.ChiTietPhieuDatRepository;
import com.example.demo.service.IChiTietPhieuDatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChiTietPhieuDatServiceImpl implements IChiTietPhieuDatService {
    @Autowired
    ChiTietPhieuDatRepository chiTietPhieuDatRepository;

    public ChiTietPhieuDatServiceImpl(ChiTietPhieuDatRepository chiTietPhieuDatRepository) {
        this.chiTietPhieuDatRepository = chiTietPhieuDatRepository;
    }

    @Override
    public List<ChiTietPhieuDat> findAll() {
        return chiTietPhieuDatRepository.findAll();
    }

    @Override
    public List<ChiTietPhieuDat> findAllById(Iterable<Long> longs) {
        return chiTietPhieuDatRepository.findAllById(longs);
    }



    @Override
    public Optional<ChiTietPhieuDat> findById(Long aLong) {
        return chiTietPhieuDatRepository.findById(aLong);
    }

    @Override
    public long count() {
        return chiTietPhieuDatRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        chiTietPhieuDatRepository.deleteById(aLong);
    }

    @Override
    public void delete(ChiTietPhieuDat entity) {
        chiTietPhieuDatRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        chiTietPhieuDatRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll() {
        chiTietPhieuDatRepository.deleteAll();
    }

    @Override
    public List<ChiTietPhieuDat> findAll(Sort sort) {
        return chiTietPhieuDatRepository.findAll(sort);
    }
}

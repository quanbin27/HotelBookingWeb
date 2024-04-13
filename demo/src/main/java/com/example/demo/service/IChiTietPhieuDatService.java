package com.example.demo.service;

import com.example.demo.entity.ChiTietPhieuDat;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IChiTietPhieuDatService {
    List<ChiTietPhieuDat> findAll();

    List<ChiTietPhieuDat> findAllById(Iterable<Long> longs);

    Optional<ChiTietPhieuDat> findById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(ChiTietPhieuDat entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll();

    List<ChiTietPhieuDat> findAll(Sort sort);
}

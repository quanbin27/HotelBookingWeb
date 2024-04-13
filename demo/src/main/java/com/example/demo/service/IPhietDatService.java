package com.example.demo.service;

import com.example.demo.entity.PhieuDat;

import java.util.List;
import java.util.Optional;

public interface IPhietDatService {
    List<PhieuDat> findAll();

    List<PhieuDat> findAllById(Iterable<String> strings);


    Optional<PhieuDat> findById(String s);

    long count();

    void deleteById(String s);

    void delete(PhieuDat entity);

    void deleteAllById(Iterable<? extends String> strings);

    void deleteAll();
}

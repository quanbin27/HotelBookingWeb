package com.example.demo.service;

import com.example.demo.entity.KieuPhong;

import java.util.List;
import java.util.Optional;

public interface IKieuPhongService {
    List<KieuPhong> findAll();

    List<KieuPhong> findAllById(Iterable<String> strings);

    <S extends KieuPhong> S save(S entity);

    Optional<KieuPhong> findById(String s);

    long count();

    void deleteById(String s);

    void delete(KieuPhong entity);

    void deleteAll();
}

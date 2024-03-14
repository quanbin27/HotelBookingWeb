package com.example.demo.service;

import com.example.demo.entity.LoaiPhong;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ILoaiPhongService {
    List<LoaiPhong> findAll();

    List<LoaiPhong> findAllById(Iterable<String> strings);

    <S extends LoaiPhong> S save(S entity);

    Optional<LoaiPhong> findById(String s);

    long count();

    void deleteById(String s);

    void delete(LoaiPhong entity);

    void deleteAll();

    List<LoaiPhong> findAll(Sort sort);
}

package com.example.demo.service;

import com.example.demo.entity.HangPhong;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IHangPhongService {
    List<HangPhong> findAll();

    List<HangPhong> findAllById(Iterable<String> strings);

    <S extends HangPhong> S save(S entity);

    Optional<HangPhong> findById(String s);

    long count();

    void deleteById(String s);

    void delete(HangPhong entity);

    void deleteAll();

    List<HangPhong> findAll(Sort sort);
}

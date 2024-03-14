package com.example.demo.service;

import com.example.demo.entity.Phong;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IPhongService {
    List<Phong> findAll();

    <S extends Phong> S save(S entity);

    Optional<Phong> findById(String s);

    long count();

    void deleteById(String s);

    void delete(Phong entity);

    void deleteAll(Iterable<? extends Phong> entities);

    void deleteAll();

    List<Phong> findAll(Sort sort);
}

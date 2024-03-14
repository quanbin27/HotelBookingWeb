package com.example.demo.service.impl;

import com.example.demo.entity.HangPhong;
import com.example.demo.repository.HangPhongRepository;
import com.example.demo.service.IHangPhongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HangPhongServiceImpl implements IHangPhongService {
    @Autowired
    HangPhongRepository hangPhongRepository;

    public HangPhongServiceImpl(HangPhongRepository hangPhongRepository) {
        this.hangPhongRepository = hangPhongRepository;
    }

    @Override
    public List<HangPhong> findAll() {
        return hangPhongRepository.findAll();
    }

    @Override
    public List<HangPhong> findAllById(Iterable<String> strings) {
        return hangPhongRepository.findAllById(strings);
    }

    @Override
    public <S extends HangPhong> S save(S entity) {
        return hangPhongRepository.save(entity);
    }

    @Override
    public Optional<HangPhong> findById(String s) {
        return hangPhongRepository.findById(s);
    }

    @Override
    public long count() {
        return hangPhongRepository.count();
    }

    @Override
    public void deleteById(String s) {
        hangPhongRepository.deleteById(s);
    }

    @Override
    public void delete(HangPhong entity) {
        hangPhongRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        hangPhongRepository.deleteAll();
    }

    @Override
    public List<HangPhong> findAll(Sort sort) {
        return hangPhongRepository.findAll(sort);
    }

}

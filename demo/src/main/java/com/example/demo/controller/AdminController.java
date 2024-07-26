package com.example.demo.controller;

import com.example.demo.entity.KieuPhong;
import com.example.demo.entity.LoaiPhong;
import com.example.demo.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class AdminController {
    private final PhieuDatServiceImpl phieuDatService;
    private final HangPhongServiceImpl hangPhongService;
    private final UserDetailsServiceImpl userDetailsService;
    private final LoaiPhongServiceImpl loaiPhongService;
    private final KieuPhongServiceImpl kieuPhongService;
    @Autowired
    public AdminController(HangPhongServiceImpl hangPhongService, PhieuDatServiceImpl phieuDatService, UserDetailsServiceImpl userDetailsService, LoaiPhongServiceImpl loaiPhongService, KieuPhongServiceImpl kieuPhongService) {
        this.hangPhongService = hangPhongService;
        this.phieuDatService = phieuDatService;
        this.userDetailsService = userDetailsService;
        this.loaiPhongService = loaiPhongService;
        this.kieuPhongService = kieuPhongService;
    }



    // Quan ly Loai Phong

    @GetMapping("loaiphong")
    public String showListLoaiPhong(Model model){
        model.addAttribute("listloaiphong",loaiPhongService.findAll());
        return "admin/loaiphong/list-loaiphong";
    }
    @GetMapping("loaiphong/edit/{id}")
    public String showEditFormLoaiPhong(@PathVariable("id") String id, Model model) {
        LoaiPhong loaiPhong=loaiPhongService.findById(id);
        model.addAttribute("loaiphong", loaiPhong);
        return "admin/loaiphong/edit-loaiphong";
    }
    @PostMapping("loaiphong/edit/{id}")
    public String updateLoaiPhong(@PathVariable("id") String id, @ModelAttribute LoaiPhong loaiPhong) {
        loaiPhongService.updateLoaiPhong(id, loaiPhong);
        System.out.println("UPDATE XONG");
        return "redirect:/loaiphong";
    }
    @PostMapping("loaiphong/delete/{id}")
    public String deleteLoaiPhong(@PathVariable("id") String id){
        loaiPhongService.deleteById(id);
        return "redirect:/loaiphong";
    }
    @GetMapping("loaiphong/add")
    public String addLoaiPhong(){
        return "admin/loaiphong/add-loaiphong";
    }
    @PostMapping("loaiphong/add")
    public String addLoaiPhong(@ModelAttribute LoaiPhong loaiPhong){
        loaiPhongService.createLoaiPhong(loaiPhong);
        return "redirect:/loaiphong";
    }

    // Quan ly Kieu Phong

    @GetMapping("kieuphong")
    public String showListKieuPhong(Model model){
        model.addAttribute("listkieuphong",kieuPhongService.findAll());
        return "admin/kieuphong/list-kieuphong";
    }
    @GetMapping("kieuphong/edit/{id}")
    public String showEditFormKieuPhong(@PathVariable("id") String id, Model model) {
        KieuPhong kieuPhong=kieuPhongService.findById(id);
        model.addAttribute("kieuphong", kieuPhong);
        return "admin/kieuphong/edit-kieuphong";
    }
    @PostMapping("kieuphong/edit/{id}")
    public String updateLoaiPhong(@PathVariable("id") String id, @ModelAttribute KieuPhong kieuPhong,@RequestParam("imageimport") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                kieuPhong.setImage(imageFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            kieuPhong.setImage(kieuPhongService.findById(id).getImage());
        }
        kieuPhongService.updateKieuPhong(id, kieuPhong);
        return "redirect:/kieuphong";
    }
    @PostMapping("kieuphong/delete/{id}")
    public String deleteKieuPhong(@PathVariable("id") String id){
        kieuPhongService.deleteById(id);
        return "redirect:/kieuphong";
    }
    @GetMapping("kieuphong/add")
    public String addKieuPhong(){
        return "admin/kieuphong/add-kieuphong";
    }
    @PostMapping("kieuphong/add")
    public String addKieuPhong(@ModelAttribute KieuPhong kieuPhong, @RequestParam("imageimport") MultipartFile imageFile){
        if (!imageFile.isEmpty()) {
            try {
                kieuPhong.setImage(imageFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        kieuPhongService.createKieuPhong(kieuPhong);
        return "redirect:/kieuphong";
    }






}

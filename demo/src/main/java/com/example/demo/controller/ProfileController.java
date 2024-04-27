package com.example.demo.controller;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.PhieuDat;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.service.impl.PhieuDatServiceImpl;
import com.example.demo.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProfileController {
    private final UserDetailsServiceImpl userDetailsService;
    private final PhieuDatServiceImpl phieuDatServiceImpl;
    @Autowired
    public ProfileController(UserDetailsServiceImpl userDetailsService,PhieuDatServiceImpl phieuDatServiceImpl){
        this.userDetailsService=userDetailsService;
        this.phieuDatServiceImpl=phieuDatServiceImpl;
    }
    @RequestMapping("profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TaiKhoan taiKhoan =userDetailsService.findUserAccount(authentication.getName());
        KhachHang khachHang = taiKhoan.getKhachHang();
        khachHang=userDetailsService.findByCCCD(khachHang.getCCCD());
        model.addAttribute("khachhang",khachHang);
        return "profile";
    }
    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("khachhang") KhachHang khachHang) {
        userDetailsService.merge(khachHang);
        return "redirect:/profile";
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("old-password") String currentPassword, @RequestParam("new-password") String newPassword, @RequestParam("confirm-password") String confirmPassword, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TaiKhoan taiKhoan = userDetailsService.findUserAccount(authentication.getName());
        PasswordEncoder passwordEncoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "New password and confirm password do not match.");
            return "redirect:/profile";
        }
        if (!passwordEncoder.matches(currentPassword,taiKhoan.getEncrytedPassword())){
            redirectAttributes.addFlashAttribute("error", "Incorrect old password.");
            return "redirect:/profile";
        }
        userDetailsService.updatePassword(taiKhoan,newPassword);
        redirectAttributes.addFlashAttribute("success","Success");
        return "redirect:/profile";
    }
    @GetMapping("/bookinghistory")
    public String bookingHistory(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TaiKhoan taiKhoan = userDetailsService.findUserAccount(authentication.getName());
        List<PhieuDat> phieuDatList=phieuDatServiceImpl.findByKhachHangCCCD(taiKhoan.getKhachHang().getCCCD());
        model.addAttribute("listphieudat",phieuDatList);
        return "bookinghistory";
    }
}

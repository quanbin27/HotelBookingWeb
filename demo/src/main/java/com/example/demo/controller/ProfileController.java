package com.example.demo.controller;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.TaiKhoanRepository;
import com.example.demo.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {
    @Autowired
    private KhachHangRepository khachhangRepository;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    private final UserDetailsServiceImpl userDetailsService;
    @Autowired
    public ProfileController(UserDetailsServiceImpl userDetailsService){
        this.userDetailsService=userDetailsService;
    }
    @RequestMapping("profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TaiKhoan taiKhoan = taiKhoanRepository.findUserAccount(authentication.getName());
        KhachHang khachHang = taiKhoan.getKhachHang();
        khachHang=khachhangRepository.findByCCCD(khachHang.getCCCD());
        model.addAttribute("khachhang",khachHang);
        return "profile";
    }
    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("khachhang") KhachHang khachHang) {
        System.out.println(khachHang.getHo());
        khachhangRepository.merge(khachHang);
        return "redirect:/profile";
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("old-password") String currentPassword, @RequestParam("new-password") String newPassword, @RequestParam("confirm-password") String confirmPassword, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TaiKhoan taiKhoan = taiKhoanRepository.findUserAccount(authentication.getName());
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
}

package com.example.demo.controller;

import com.example.demo.DTO.TaiKhoanKhachHangDTO;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.KieuPhong;
import com.example.demo.DTO.KieuPhongDTO;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.TaiKhoanRepository;
import com.example.demo.service.IKieuPhongService;
import com.example.demo.service.impl.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.utils.EncrytedPasswordUtils.encrytePassword;

@Controller
public class PhieuDatController {
    @Autowired
    IKieuPhongService kieuPhongService;
    @Autowired
    private KhachHangRepository khachhangRepository;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    private final UserDetailsServiceImpl userDetailsService;
    @Autowired
    public PhieuDatController(UserDetailsServiceImpl userDetailsService){
        this.userDetailsService=userDetailsService;
    }


    @Autowired
    private AuthenticationManager authenticationManager;
    @ModelAttribute("isLoggedIn")
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.isAuthenticated());
        return authentication != null  && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    @RequestMapping("rooms")
    public String rooms(Model model){
        List<KieuPhong> kieuphongs=kieuPhongService.findAll();
        List<KieuPhongDTO> kieuphongDTOs = new ArrayList<>();
        for (KieuPhong kieuPhong:kieuphongs){
            KieuPhongDTO kieuPhongDTO=new KieuPhongDTO();
            kieuPhongDTO.loadFromEntity(kieuPhong);
            kieuphongDTOs.add(kieuPhongDTO);
        }
        model.addAttribute("listkieuphong",kieuphongDTOs);
        return "rooms";
    }
    @RequestMapping(value = { "index" ,"home"})
    public String home(Model model) {
        return "index";
    }
    @RequestMapping("admin")
    public String admin(Model model){
        return "admin";
    }
    @RequestMapping("signin")
    public String login(Model model){
        return "sign_in";
    }
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        StringTrimmerEditor  stringTrimmerEditor=new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);

    }
    @GetMapping("signup")
    public  String signup(@ModelAttribute TaiKhoanKhachHangDTO taiKhoanKhachHangDTO, Model model){
        model.addAttribute("taiKhoanKhachHangDTO",taiKhoanKhachHangDTO);
        System.out.println("vao signup");
        return "sign_up";
    }
    private static final Logger log =LoggerFactory.getLogger(PhieuDatController.class);
    @PostMapping("signup")
    public String returnHome(@Valid TaiKhoanKhachHangDTO taiKhoanKhachHangDTO, BindingResult bindingResult){
        taiKhoanKhachHangDTO.setEncrytedPassword(encrytePassword(taiKhoanKhachHangDTO.getEncrytedPassword()));
        System.out.println(taiKhoanKhachHangDTO.toString());
        if (userDetailsService.userExists(taiKhoanKhachHangDTO.getUsername())) {
            bindingResult.addError(new FieldError("taiKhoanKhachHangDTO","Username","Username already in use"));
        }
        if (bindingResult.hasErrors()){
            System.out.println("co loi"+bindingResult.toString());
            return "sign_up";
        }
        log.info("----UserDTO:",taiKhoanKhachHangDTO.toString());
        userDetailsService.register(taiKhoanKhachHangDTO);
        return "redirect:signin";

    }
    @RequestMapping("contact")
    public String contact(){
        return "contact";
    }
    @RequestMapping("about")
    public String about(){
        return "about";
    }
    @RequestMapping("booking")
    public  String booking(){return "booking";}
    @RequestMapping("profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            TaiKhoan taiKhoan = taiKhoanRepository.findUserAccount(authentication.getName());
            KhachHang khachHang = taiKhoan.getKhachHang();
            khachHang=khachhangRepository.findByCCCD(khachHang.getCCCD());
            model.addAttribute("khachhang",khachHang);
        }
        return "profile";
    }
    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("khachhang") KhachHang khachHang) {
        System.out.println(khachHang.getHo());
        khachhangRepository.merge(khachHang);
        return "redirect:/profile";
    }
}

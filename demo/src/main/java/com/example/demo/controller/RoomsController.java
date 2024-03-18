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
import com.example.demo.utils.RandomStringUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.utils.EncrytedPasswordUtils.encrytePassword;






@Controller
public class RoomsController {
    @Autowired
    IKieuPhongService kieuPhongService;
    private final UserDetailsServiceImpl userDetailsService;
    @Autowired
    public RoomsController(UserDetailsServiceImpl userDetailsService){
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



}

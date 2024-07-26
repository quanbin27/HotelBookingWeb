package com.example.demo.controller;

import com.example.demo.DTO.KieuPhongDTO;
import com.example.demo.entity.KieuPhong;
import com.example.demo.service.IKieuPhongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    IKieuPhongService kieuPhongService;
    @ModelAttribute("isLoggedIn")
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.isAuthenticated());
        return authentication != null  && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
    @RequestMapping(value = { "index" ,"home","/"})
    public String home(Model model) {
        return "index";
    }


    @RequestMapping("contact")
    public String contact(){
        return "contact";
    }
    @RequestMapping("about")
    public String about(){
        return "about";
    }
    @RequestMapping("rooms")
    public String rooms(Model model){
        List<KieuPhong> kieuphongs=kieuPhongService.findAll();
        List<KieuPhongDTO> kieuphongDTOs = new ArrayList<>();
        model.addAttribute("listkieuphong",kieuphongs);
        return "rooms";
    }


}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {
    @Autowired
    private AuthenticationManager authenticationManager;
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
    @RequestMapping("admin")
    public String admin(Model model){
        return "admin";
    }

    @RequestMapping("contact")
    public String contact(){
        return "contact";
    }
    @RequestMapping("about")
    public String about(){
        return "about";
    }

}

package com.example.demo.controller;

import com.example.demo.DTO.TaiKhoanKhachHangDTO;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.TaiKhoanRepository;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.demo.utils.EncrytedPasswordUtils.encrytePassword;

@Controller
public class AccountController {
    @Autowired
    private KhachHangRepository khachhangRepository;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    private final UserDetailsServiceImpl userDetailsService;
    @Autowired
    public AccountController(UserDetailsServiceImpl userDetailsService){
        this.userDetailsService=userDetailsService;
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
        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);

    }
    @GetMapping("signup")
    public  String signup(@ModelAttribute TaiKhoanKhachHangDTO taiKhoanKhachHangDTO, Model model){
        model.addAttribute("taiKhoanKhachHangDTO",taiKhoanKhachHangDTO);
        System.out.println("vao signup");
        return "sign_up";
    }
    private static final Logger log = LoggerFactory.getLogger(RoomsController.class);
    @PostMapping("signup")
    public String returnHome(@Valid TaiKhoanKhachHangDTO taiKhoanKhachHangDTO, BindingResult bindingResult){
        System.out.println(taiKhoanKhachHangDTO.toString());
        if (userDetailsService.userExists(taiKhoanKhachHangDTO.getUsername())) {
            bindingResult.addError(new FieldError("taiKhoanKhachHangDTO","Username","Username already in use"));
        }
        if (bindingResult.hasErrors()){
            System.out.println("co loi");
            System.out.println("co loi"+bindingResult.toString());
            return "sign_up";
        }
        System.out.println("ko loi");
        log.info("----UserDTO:",taiKhoanKhachHangDTO.toString());
        taiKhoanKhachHangDTO.setEncrytedPassword(encrytePassword(taiKhoanKhachHangDTO.getEncrytedPassword()));
        userDetailsService.register(taiKhoanKhachHangDTO);
        return "redirect:signin";

    }
    @GetMapping("/forgotpass")
    public String forgotpass(){
        return "forgotpass";
    }
    @Autowired
    private JavaMailSender mailSender;
    public class Utility {
        public static String getSiteURL(HttpServletRequest request) {
            String siteURL = request.getRequestURL().toString();
            return siteURL.replace(request.getServletPath(), "");
        }
    }
    @PostMapping("/forgotpass")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String token = RandomStringUtil.generateRandomString(30);
        try {
            System.out.println("vao update");
            userDetailsService.updateResetPasswordToken(token, username);
            TaiKhoan taiKhoan=taiKhoanRepository.findUserAccount(username);
            System.out.println(taiKhoan.getKhachHang().getCCCD());
            KhachHang khachHang=khachhangRepository.findByCCCD(taiKhoan.getKhachHang().getCCCD());
            System.out.println(khachHang.getEmail());
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(khachHang.getEmail(),resetPasswordLink);
            System.out.println("da gui");
            model.addAttribute("success", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        }catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return "forgotpass";
    }
    @GetMapping("/reset_password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        TaiKhoan taiKhoan = userDetailsService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
        if (taiKhoan == null) {
            model.addAttribute("error", "Invalid Token");
            return "message";
        }
        return "resetpasswordform";
    }
    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String confirmpassword=request.getParameter("confirmpassword");
        System.out.println(confirmpassword+" "+password);
        if (!confirmpassword.equals(password)){
            model.addAttribute("error", "New password and confirm password do not match.");
            return "message";
        }
        TaiKhoan taiKhoan = userDetailsService.getByResetPasswordToken(token);
        if (taiKhoan == null) {
            model.addAttribute("error", "Invalid Token");
            return "message";
        } else {
            userDetailsService.updatePassword(taiKhoan,password);
            model.addAttribute("success", "You have successfully changed your password.");
        }
        return "message";
    }
    public void sendEmail(String recipientEmail, String context)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("n21dccn068@student.ptithcm.edu.vn", "Monata Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + context + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}

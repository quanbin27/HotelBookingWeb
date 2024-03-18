package com.example.demo.service.impl;

import com.example.demo.DTO.TaiKhoanKhachHangDTO;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Role;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.TaiKhoanRepository;
import com.example.demo.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserDetails loadUserByUsername(String UserName) throws UsernameNotFoundException{
        TaiKhoan taiKhoan=this.taiKhoanRepository.findUserAccount(UserName);
        if (taiKhoan==null){
            System.out.println("User not found! " + UserName);
            throw new UsernameNotFoundException("User " + UserName + " was not found in the database");
        }
        System.out.println("Found User: " + taiKhoan);
        List<String> roleNames = this.roleRepository.getRoleNames(taiKhoan.getUsername());
        System.out.println(roleNames);
        List<GrantedAuthority> grantedAuthorityList=new ArrayList<GrantedAuthority>();
        if (roleNames!=null){
            for (String role:roleNames){
                GrantedAuthority authority=new SimpleGrantedAuthority(role);
                grantedAuthorityList.add(authority);
            }
        }
        System.out.println(grantedAuthorityList);
        System.out.println(taiKhoan.getEncrytedPassword());
        UserDetails userDetails=(UserDetails) new User(taiKhoan.getUsername(),taiKhoan.getEncrytedPassword(),grantedAuthorityList);
        return userDetails;
    }

    public boolean userExists(String Username ){
        return taiKhoanRepository.existsByUsername(Username);
    }
    @Transactional
    public void save(TaiKhoan taiKhoan, KhachHang khachHang){
        khachHangRepository.save(khachHang);
        taiKhoanRepository.save(taiKhoan);

    }
    @Transactional
    public void addNewUser(UserRole userRole){
        userRole.setRole(roleRepository.findByRoleName("USER"));
        userRoleRepository.addNewUser(userRole);
    }
    public void register(TaiKhoanKhachHangDTO taiKhoanKhachHangDTO){
        TaiKhoan tk=new TaiKhoan();
        KhachHang kh=new KhachHang();
        kh.setCCCD(taiKhoanKhachHangDTO.getCCCD());
        kh.setHo(taiKhoanKhachHangDTO.getHo());
        kh.setTen(taiKhoanKhachHangDTO.getTen());
        kh.setEmail(taiKhoanKhachHangDTO.getEmail());
        kh.setPhai(taiKhoanKhachHangDTO.isPhai());
        kh.setSDT("sdttest");
        kh.setDiaChi(taiKhoanKhachHangDTO.getDiaChi());
        kh.setNgaySinh(taiKhoanKhachHangDTO.getNgaySinh());
        kh.setMaSoThue(taiKhoanKhachHangDTO.getMaSoThue());

        tk.setUsername(taiKhoanKhachHangDTO.getUsername());
        tk.setEncrytedPassword(taiKhoanKhachHangDTO.getEncrytedPassword());
        tk.setEnabled(false);
        System.out.println("here2" );
        tk.setKhachHang(kh);
        save(tk,kh);
        UserRole userRole=new UserRole();
        userRole.setTaiKhoan(tk);
        addNewUser(userRole);
        System.out.println("here4" );
        System.out.println("toiday");
        System.out.println("here3" );

        System.out.println("da save");
    }
    public void updateResetPasswordToken(String token, String username) throws UsernameNotFoundException{
        TaiKhoan taiKhoan = taiKhoanRepository.findUserAccount(username);
        System.out.println(taiKhoan.getKhachHang().getEmail());
        if (taiKhoan != null) {
            taiKhoan.setResetPasswordToken(token);
            taiKhoanRepository.merge(taiKhoan);
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the username " + username);
        }
    }
    public TaiKhoan getByResetPasswordToken(String token) {
        return taiKhoanRepository.findByResetPasswordToken(token);
    }
    public void updatePassword(TaiKhoan taiKhoan,String newPassword){
        PasswordEncoder passwordEncoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
        taiKhoan.setEncrytedPassword(passwordEncoder.encode(newPassword));
        taiKhoan.setResetPasswordToken(null);
        taiKhoanRepository.merge(taiKhoan);
    }
}

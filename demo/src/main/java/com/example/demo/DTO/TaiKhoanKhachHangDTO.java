package com.example.demo.DTO;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.TaiKhoan;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanKhachHangDTO implements Serializable {
    @NotBlank(message = "Enter your Username")
    private String Username;
    @Length(min = 6,message = "Password must be at least 6 characters")
    @NotBlank(message = "Enter your Password")
    private String EncrytedPassword;
    @NotBlank(message = "Enter your CCCD")
    private String CCCD;
    @NotBlank(message = "Enter your First Name")
    private String Ho;
    @NotBlank(message = "Enter your Last Name")
    private String Ten;
    @NotNull
    private boolean Phai;
    //@NotBlank(message = "Enter your Phone Number")
    private String SDT;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Enter your Birthday")
    private Date NgaySinh;
    private String DiaChi;
    @NotBlank(message = "Enter your Email")
    @jakarta.validation.constraints.Email(message = "Enter a valid Email address")
    private String Email;
    private String MaSoThue;
    private boolean Enabled;
    public void loadFromEntity(KhachHang khachHang, TaiKhoan taiKhoan){
        this.Username=taiKhoan.getUsername();
        this.EncrytedPassword=taiKhoan.getEncrytedPassword();
        this.CCCD=khachHang.getCCCD();
        this.Ho= khachHang.getHo();
        this.Ten= khachHang.getTen();
        this.Phai= khachHang.isPhai();
        this.SDT= khachHang.getSDT();
        this.NgaySinh=khachHang.getNgaySinh();
        this.DiaChi= khachHang.getDiaChi();
        this.MaSoThue=khachHang.getMaSoThue();
        this.Email=khachHang.getEmail();
        this.Enabled= taiKhoan.isEnabled();
    }
}

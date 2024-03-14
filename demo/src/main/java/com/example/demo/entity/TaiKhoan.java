package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TaiKhoan", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "CCCD_UK", columnNames = "CCCD") })
public class TaiKhoan implements Serializable {
    @Id
    @Column(name = "Username",nullable = false,length = 36)
    private String Username;
    @Column(name = "Encryted_Password",length = 128,nullable = false)
    private String EncrytedPassword;
    @OneToOne
    @JoinColumn(name = "CCCD")
    private KhachHang khachHang;
    @Column(name = "Enabled",length = 1,nullable = false)
    private boolean Enabled;

    public TaiKhoan(String username, String encrytedPassword, String cccd, Boolean enabled) {
        this.Username = username;
        this.EncrytedPassword = encrytedPassword;
        this.khachHang = new KhachHang(); // Khởi tạo một đối tượng KhachHang trống nếu cần thiết
        this.khachHang.setCCCD(cccd);
        this.Enabled = enabled;
    }


}

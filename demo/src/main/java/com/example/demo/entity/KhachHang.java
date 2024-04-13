package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="KhachHang")
public class KhachHang implements Serializable {
    @Id
    @Column(name = "CCCD")
    private String CCCD;
    private String Ho;
    private String Ten;
    private boolean Phai;
    private String SDT;
    @Temporal(TemporalType.DATE)
    private Date NgaySinh;
    private String DiaChi;
    private String MaSoThue;
    private String Email;
    @OneToOne(mappedBy = "khachHang")
    private TaiKhoan taiKhoan;
    @OneToMany(mappedBy = "khachHang",cascade = CascadeType.ALL)
    private Set<PhieuDat> phieudats;
}

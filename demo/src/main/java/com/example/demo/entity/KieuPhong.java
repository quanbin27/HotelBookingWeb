package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KieuPhong")
public class KieuPhong implements Serializable {
    @Id
    private String MaKP;
    private String TenKP;
    private boolean Wifi;
    private float DienTich;
    private String ViewPhong;
    private String MoTa;
    @Lob
    private byte[] image;
    @OneToMany(mappedBy = "kieuphong", cascade = CascadeType.ALL)
    private Set<HangPhong> hangphongs;

}

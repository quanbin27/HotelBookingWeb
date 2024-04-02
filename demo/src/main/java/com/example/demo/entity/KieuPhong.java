package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Base64;
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
    @Transient
    private String base64Image;
    @OneToMany(mappedBy = "kieuphong", cascade = CascadeType.ALL)
    private Set<HangPhong> hangphongs;
    @PostLoad
    public void generateBase64Image() {
        if (this.image != null) {
            this.base64Image = Base64.getEncoder().encodeToString(this.image);
        }
    }
}

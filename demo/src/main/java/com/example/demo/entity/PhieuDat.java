package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "PhieuDat")
public class PhieuDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String MaPD;
    private String CCCD;
    @Temporal(TemporalType.DATE)
    private Date NgayDat;
    @Temporal(TemporalType.DATE)
    private Date NgayBD;
    @Temporal(TemporalType.DATE)
    private Date NgayTra;
    private String TrangThai;
    private double TongTien;
    @OneToMany(mappedBy = "phieudat",cascade = CascadeType.ALL)
    @Builder.Default
    private Set<ChiTietPhieuDat> chitietphieudats= new HashSet<>();

    public void addChiTietPhieuDat(ChiTietPhieuDat chiTietPhieuDat) {
        this.chitietphieudats.add(chiTietPhieuDat);
    }

}

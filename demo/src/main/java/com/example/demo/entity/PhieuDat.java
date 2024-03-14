package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PhieuDat")
public class PhieuDat implements Serializable {
    @Id
    private String MaPD;
    @Temporal(TemporalType.DATE)
    private Date NgayDat;
    @Temporal(TemporalType.DATE)
    private Date NgayBD;
    private int SoNgay;
    private String TrangThai;
    @OneToMany(mappedBy = "phieudat",cascade = CascadeType.ALL)
    private Set<ChiTietPhieuDat> chitietphieudats;

    public int getSoNgay1() {
        return SoNgay;
    }


}

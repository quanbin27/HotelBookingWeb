package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    @ManyToOne
    @JoinColumn(name = "CCCD")
    private KhachHang khachHang;
    @Temporal(TemporalType.DATE)
    private Date NgayDat;
    @Temporal(TemporalType.DATE)
    private Date NgayBD;
    @Temporal(TemporalType.DATE)
    private Date NgayTra;
    private String TrangThai;
    private double TongTien;
    @OneToMany(mappedBy = "phieudat", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @Builder.Default
    private Set<ChiTietPhieuDat> chitietphieudats= new HashSet<>();
    public long getSoNgay() {
        long diffInMillies = Math.abs(NgayTra.getTime() - NgayBD.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
    public double calculateTongTien() {
        double total = 0;
        long soNgay = getSoNgay();
        for (ChiTietPhieuDat chiTiet : chitietphieudats) {
            total += chiTiet.getSoLuong() * chiTiet.getHangphong().getDonGia() * soNgay;
        }
        this.TongTien = total;
        return total;
    }
}

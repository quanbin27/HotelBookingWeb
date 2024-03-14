package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiTietPhietDat",uniqueConstraints = {@UniqueConstraint(columnNames = {"MAPD","MAHP"})})
public class ChiTietPhieuDat implements Serializable {
    @Id
    private Long IdPD;
    private int SoLuong;
    @ManyToOne
    @JoinColumn(name = "MaPD")
    private PhieuDat phieudat;
    @ManyToOne
    @JoinColumn(name = "MaHP")
    private HangPhong hangphong;


}

package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiTietPhietDat",uniqueConstraints = {@UniqueConstraint(columnNames = {"MAPD","MAHP"})})
public class ChiTietPhieuDat implements Serializable {
    @Id
    @GeneratedValue
    private Long IdPD;
    private int SoLuong;
    @ManyToOne
    @JoinColumn(name = "MaPD")
    private PhieuDat phieudat;
    @ManyToOne
    @JoinColumn(name = "MaHP")
    private HangPhong hangphong;


}

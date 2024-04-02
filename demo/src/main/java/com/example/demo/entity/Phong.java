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
@Table(name = "Phong")
public class Phong implements Serializable {
    @Id
    private String Maphong;
    private int SoTang;
    private  String TrangThai;
    @ManyToOne
    @JoinColumn(name = "MaHP")
    private HangPhong hangphong;

}

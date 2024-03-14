package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LoaiPhong")
public class LoaiPhong implements Serializable {
    @Id
    private String MaLP;
    private String TenLP;
    private String MoTa;
    @OneToMany(mappedBy = "loaiphong", cascade = CascadeType.ALL)
    private Set<HangPhong> hangphongs;

}

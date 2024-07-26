package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @OneToMany(mappedBy = "loaiphong", cascade = CascadeType.MERGE)
    private Set<HangPhong> hangphongs;

}

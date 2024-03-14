package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User_Role", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "USER_ROLE_UK", columnNames = { "Username", "RoleId" }) })
public class UserRole implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "Id",nullable = false)
    private long Id;
    @ManyToOne
    @JoinColumn(name = "Username",nullable = false)
    private TaiKhoan taiKhoan;
    @ManyToOne
    @JoinColumn(name = "RoleId", nullable = false)
    private Role role;
}

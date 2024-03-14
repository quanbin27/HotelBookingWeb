package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HangPhongDTO {
    private String MaHP;
    private String TenHP;
    private Long DonGia;
    private String MaKP;
    private String MaLP;
    private int SoNguoi;
}

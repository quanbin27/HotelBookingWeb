package com.example.demo.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhieuDatDTO {
    private String IdPD;
    private String MaPD;
    private String MaHP;
    private int SoLuong;
}

package com.example.demo.DTO;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuDatDTO {
    private String MaPD;
    @Temporal(TemporalType.DATE)
    private Date NgayDat;
    @Temporal(TemporalType.DATE)
    private Date NgayBD;
    private int SoNgay;
    private String TrangThai;

}

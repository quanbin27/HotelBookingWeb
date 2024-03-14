package com.example.demo.DTO;
import com.example.demo.entity.KieuPhong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KieuPhongDTO {
    private String MaKP;
    private String TenKP;
    private boolean Wifi;
    private float DienTich;
    private String ViewPhong;
    private String Mota;
    private String base64Image;
    public void loadFromEntity(KieuPhong entity){
        this.MaKP=entity.getMaKP();
        this.DienTich=entity.getDienTich();
        this.Mota=entity.getMoTa();
        this.base64Image= Base64.getEncoder().encodeToString(entity.getImage());
        this.Wifi=entity.isWifi();
        this.TenKP=entity.getTenKP();
        this.ViewPhong=entity.getViewPhong();

    }
}

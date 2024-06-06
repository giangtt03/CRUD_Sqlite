package com.example.ph27127_damli_sp24.Models;

public class ThanhVien {
    int IDTV;
    String HoTenTV;
    String NamsinhTV;

    public ThanhVien() {
    }

    public ThanhVien(int IDTV, String hoTenTV, String namsinhTV) {
        this.IDTV = IDTV;
        HoTenTV = hoTenTV;
        NamsinhTV = namsinhTV;
    }

    public int getIDTV() {
        return IDTV;
    }

    public void setIDTV(int IDTV) {
        this.IDTV = IDTV;
    }

    public String getHoTenTV() {
        return HoTenTV;
    }

    public void setHoTenTV(String hoTenTV) {
        HoTenTV = hoTenTV;
    }

    public String getNamsinhTV() {
        return NamsinhTV;
    }

    public void setNamsinhTV(String namsinhTV) {
        NamsinhTV = namsinhTV;
    }

    public static final String TB_NAME = "ThanhVien";
    public static final String COL_NAME_ID = "maTV";
    public static final String COL_NAME_HOTEN = "hoTen";
    public static final String COL_NAME_NAMSINH = "namSinh";
}

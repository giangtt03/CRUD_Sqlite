package com.example.ph27127_damli_sp24.Models;

public class NhanVien {

    private String maNV;

    public NhanVien() {
    }

    public NhanVien(String maNV, String hoTen, String maKhau) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.maKhau = maKhau;
    }

    private String hoTen;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMaKhau() {
        return maKhau;
    }

    public void setMaKhau(String maKhau) {
        this.maKhau = maKhau;
    }

    private String maKhau;


    public static final String TB_NAME = "Nhanvien";
    public static final String COL_MANV = "maNV";
    public static final String COL_TENNV = "hoTen";
    public static final String COL_MK = "matKhau";

}

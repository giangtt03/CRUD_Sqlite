package com.example.ph27127_damli_sp24.Models;

public class PhieuMuon {
    int maPM;
    String maNVpm;
    int maTVpm;
    int maSpm;
    int tienthue;
    String ngaymuon;
    int trasach;
    public static final String TB_NAME_PM = "PhieuMuon";
    public static final String COL_NAME_MAPM = "maPM";
    public static final String COL_NAME_MANVPM = "maNV";
    public static final String COL_NAME_MATVPM = "maTV";
    public static final String COL_NAME_MASPM = "maSach";
    public static final String COL_NAME_TIENTHUE = "tienThue";
    public static final String COL_NAME_NGAYMUON = "ngay";
    public static final String COL_NAME_TRASACH = "traSach";

    public PhieuMuon() {
    }

    public PhieuMuon(int maPM, String maNVpm, int maTVpm, int maSpm, int tienthue, String ngaymuon, int trasach) {
        this.maPM = maPM;
        this.maNVpm = maNVpm;
        this.maTVpm = maTVpm;
        this.maSpm = maSpm;
        this.tienthue = tienthue;
        this.ngaymuon = ngaymuon;
        this.trasach = trasach;
    }

    public int getTrasach() {
        return trasach;
    }

    public void setTrasach(int trasach) {
        this.trasach = trasach;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getMaNVpm() {
        return maNVpm;
    }

    public void setMaNVpm(String maNVpm) {
        this.maNVpm = maNVpm;
    }

    public int getMaTVpm() {
        return maTVpm;
    }

    public void setMaTVpm(int maTVpm) {
        this.maTVpm = maTVpm;
    }

    public int getMaSpm() {
        return maSpm;
    }

    public void setMaSpm(int maSpm) {
        this.maSpm = maSpm;
    }

    public int getTienthue() {
        return tienthue;
    }

    public void setTienthue(int tienthue) {
        this.tienthue = tienthue;
    }

    public String getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(String ngaymuon) {
        this.ngaymuon = ngaymuon;
    }


}

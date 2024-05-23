package com.example.ph27127_damli_sp24.Models;

public class LoaiSach {
    private int maLS;
    private String tenLS;
    private String nhacc;

    public String getNhacc() {
        return nhacc;
    }

    public void setNhacc(String nhacc) {
        this.nhacc = nhacc;
    }

    public static final String TB_NAME = "LoaiSach";
    public static final String COL_NAME_MALS = "maLoai";
    public static final String COL_NAME_TENLS = "tenLoai";
    public static final String COL_NAME_NCC = "nhacc";

    public LoaiSach() {
    }

    public LoaiSach(int maLS, String tenLS) {
        this.maLS = maLS;
        this.tenLS = tenLS;
    }

    public int getMaLS() {
        return maLS;
    }

    public void setMaLS(int maLS) {
        this.maLS = maLS;
    }

    public String getTenLS() {
        return tenLS;
    }

    public void setTenLS(String tenLS) {
        this.tenLS = tenLS;
    }
}

package com.example.ph27127_damli_sp24.Models;

public class Sach {
    int mas;
    String tens;
    int gias;
    int mals;
    String tacgia;

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public int getMas() {
        return mas;
    }

    public void setMas(int mas) {
        this.mas = mas;
    }

    public String getTens() {
        return tens;
    }

    public void setTens(String tens) {
        this.tens = tens;
    }

    public int getGias() {
        return gias;
    }

    public void setGias(int gias) {
        this.gias = gias;
    }

    public int getMals() {
        return mals;
    }

    public void setMals(int mals) {
        this.mals = mals;
    }

    public Sach() {
    }

    public Sach(int mas, String tens, int gias, int mals) {
        this.mas = mas;
        this.tens = tens;
        this.gias = gias;
        this.mals = mals;
    }

    public static final String TB_NAME = "Sach";
    public static final String COL_NAME_MAS = "maSach";
    public static final String COL_NAME_tacgia = "tacgia";
    public static final String COL_NAME_TENS = "tenSach";
    public static final String COL_NAME_GIAS = "giaThue";
    public static final String COL_NAME_MALS = "maLoai";

}

package com.example.ph27127_damli_sp24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.ph27127_damli_sp24.DB.DbHelper;
import com.example.ph27127_damli_sp24.Models.PhieuMuon;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDao {
    DbHelper createData;
    SQLiteDatabase liteDatabase;

    public PhieuMuonDao(Context context) {
        createData = new DbHelper(context);
        liteDatabase = createData.getWritableDatabase();
    }

    public long ADDPM(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put(PhieuMuon.COL_NAME_MATVPM, phieuMuon.getMaTVpm());
        values.put(PhieuMuon.COL_NAME_MANVPM, phieuMuon.getMaNVpm());
        values.put(PhieuMuon.COL_NAME_MASPM, phieuMuon.getMaSpm());
        values.put(PhieuMuon.COL_NAME_NGAYMUON, phieuMuon.getNgaymuon());
        values.put(PhieuMuon.COL_NAME_TIENTHUE, phieuMuon.getTienthue());
        values.put(PhieuMuon.COL_NAME_TRASACH, phieuMuon.getTrasach());
        return liteDatabase.insert("PhieuMuon", null, values);
    }

    public int DELETEPM(PhieuMuon phieuMuon) {
        return liteDatabase.delete(PhieuMuon.TB_NAME_PM, "maPM=?", new String[]{String.valueOf(phieuMuon.getMaPM())});
    }

    public int UPDATEPM(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put(PhieuMuon.COL_NAME_MAPM, phieuMuon.getMaPM());
        values.put(PhieuMuon.COL_NAME_MATVPM, phieuMuon.getMaTVpm());
        values.put(PhieuMuon.COL_NAME_MANVPM, phieuMuon.getMaNVpm());
        values.put(PhieuMuon.COL_NAME_MASPM, phieuMuon.getMaSpm());
        values.put(PhieuMuon.COL_NAME_NGAYMUON, phieuMuon.getNgaymuon());
        values.put(PhieuMuon.COL_NAME_TIENTHUE, phieuMuon.getTienthue());
        values.put(PhieuMuon.COL_NAME_TRASACH, phieuMuon.getTrasach());
        return liteDatabase.update(PhieuMuon.TB_NAME_PM, values, "maPM=?", new String[]{String.valueOf(phieuMuon.getMaPM())});
    }

    //Lấy Phần tử Phiếu Mượn từ id cua PM trog Csdl
    public PhieuMuon getID(String id) {
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon> list = getData(sql, id);
        return list.get(0);
    }

    public List<PhieuMuon> GETPM() {
        String sql = "SELECT * FROM PhieuMuon";
        List<PhieuMuon> list = getData(sql);
        return list;
    }

    // getData viet kieu List để sd nhiều lần
    private List<PhieuMuon> getData(String sql, String... Arays) {
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = liteDatabase.rawQuery(sql, Arays);
        while (cursor.moveToNext()) {
            PhieuMuon phieuMuon = new PhieuMuon();
            phieuMuon.setMaPM(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_NAME_MAPM))));
            phieuMuon.setMaNVpm(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_NAME_MANVPM)));
            phieuMuon.setMaTVpm(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_NAME_MATVPM))));
            phieuMuon.setMaSpm(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_NAME_MASPM))));
            phieuMuon.setNgaymuon(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_NAME_NGAYMUON)));
            phieuMuon.setTienthue(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_NAME_TIENTHUE))));
            phieuMuon.setTrasach(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_NAME_TRASACH))));
            list.add(phieuMuon);
        }
        return list;

    }

    public int laygiatritheongay(String tungay, String dengay) {
        Cursor cursor = liteDatabase.rawQuery("SELECT SUM(tienThue) FROM PhieuMuon WHERE ngay >='" + tungay + "'AND ngay <='" + dengay + "'", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }




}

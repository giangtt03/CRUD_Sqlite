package com.example.ph27127_damli_sp24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.ph27127_damli_sp24.DB.CreateData;
import com.example.ph27127_damli_sp24.Models.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class NVDao {
    SQLiteDatabase sqlite;
    CreateData createData;

    public NVDao(Context context) {
        createData = new CreateData(context);
        sqlite = createData.getWritableDatabase();
    }

    public void OPEN() {
        sqlite = createData.getWritableDatabase();
    }

    public void Close() {
        createData.close();
    }

    public long ADDNV(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put(NhanVien.COL_MANV, nhanVien.getMaNV());
        values.put(NhanVien.COL_TENNV, nhanVien.getHoTen());
        values.put(NhanVien.COL_MK, nhanVien.getMaKhau());
        return sqlite.insert(NhanVien.TB_NAME, null, values);
    }

    public int UPDATE(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put(NhanVien.COL_MANV, nhanVien.getMaNV());
        values.put(NhanVien.COL_TENNV, nhanVien.getHoTen());
        values.put(NhanVien.COL_MK, nhanVien.getMaKhau());
        return sqlite.update(NhanVien.TB_NAME, values, "maNV=?", new String[]{nhanVien.getMaNV()});
    }

    public int Thaypass(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put(NhanVien.COL_MK, nhanVien.getMaKhau());
        return sqlite.update(NhanVien.TB_NAME, values, "maNV=?", new String[]{nhanVien.getMaNV()});
    }

    public int DELETE(String mNV) {
        return sqlite.delete(NhanVien.TB_NAME, "maNV=?", new String[]{mNV});
    }

    public NhanVien getId(String maNV) {
        String selectId = "SELECT * FROM NhanVien WHERE maNV=?";
        List<NhanVien> list = getdata(selectId, maNV);
        return list.get(0);
    }

    public NhanVien getUser(String user) {
        String getuser = "SELECT * FROM NhanVien WHERE maNV=?";
        List<NhanVien> list = getdata(getuser, user);
        return list.get(0);
    }

    //check taikhoan
    public int getUserName(String user) {
        String dl = "SELECT * FROM NhanVien WHERE maNV=? ";
        List<NhanVien> list = getdata(dl, user);
        if (list.size() == 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public List<NhanVien> GETNV() {
        String select = "SELECT* FROM NhanVien";
        return getdata(select);
    }

    private List<NhanVien> getdata(String sql, String... selection) {
        List<NhanVien> list = new ArrayList<>();
        Cursor cursor = sqlite.rawQuery(sql, selection);
        while (cursor.moveToNext()) {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNV(cursor.getString(cursor.getColumnIndex(NhanVien.COL_MANV)));
            nhanVien.setHoTen(cursor.getString(cursor.getColumnIndex(NhanVien.COL_TENNV)));
            nhanVien.setMaKhau(cursor.getString(cursor.getColumnIndex(NhanVien.COL_MK)));
            list.add(nhanVien);
        }
        return list;
    }

    public int getlogin(String user, String pass) {
        String dl = "SELECT * FROM NhanVien WHERE maNV=? AND matKhau=?";
        List<NhanVien> list = getdata(dl, user, pass);
        if (list.size() == 0) {
            return -1;
        } else {
            return 1;
        }
    }
}

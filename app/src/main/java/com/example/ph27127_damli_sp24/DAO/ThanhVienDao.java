package com.example.ph27127_damli_sp24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.ph27127_damli_sp24.DB.DbHelper;
import com.example.ph27127_damli_sp24.Models.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDao {
    SQLiteDatabase sqLitetv;
    DbHelper createData;

    public ThanhVienDao(Context context) {
        createData = new DbHelper(context);
        sqLitetv = createData.getWritableDatabase();
    }

    public long ADDTV(ThanhVien thanhVien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ThanhVien.COL_NAME_HOTEN, thanhVien.getHoTenTV());
        contentValues.put(ThanhVien.COL_NAME_NAMSINH, thanhVien.getNamsinhTV());
        return sqLitetv.insert(ThanhVien.TB_NAME, null, contentValues);
    }

    public int UPDATETV(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put(ThanhVien.COL_NAME_HOTEN, thanhVien.getHoTenTV());
        values.put(ThanhVien.COL_NAME_NAMSINH, thanhVien.getNamsinhTV());
        return sqLitetv.update(ThanhVien.TB_NAME, values, "maTV=?", new String[]{String.valueOf(thanhVien.getIDTV())});
    }

    public int DELETETV(ThanhVien thanhVien) {
        return sqLitetv.delete(ThanhVien.TB_NAME, "maTV=?", new String[]{String.valueOf(thanhVien.getIDTV())});
    }

    // get tất cả dữ liệu
    public List<ThanhVien> GETTV() {
        String dl = "SELECT * FROM ThanhVien";
        List<ThanhVien> list = getdata(dl);
        return list;
    }

    // get dữ liệu theo id
    public ThanhVien getId(String id) {
        String dl = "SELECT * FROM ThanhVien WHERE maTV==? ";
        List<ThanhVien> list = getdata(dl, id);
        return list.get(0);
    }
// get dữ liệu nhiều tham số
    private List<ThanhVien> getdata(String dl, String... Arays) {
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = sqLitetv.rawQuery(dl, Arays);
        while (cursor.moveToNext()) {
            ThanhVien thanhVien = new ThanhVien();
            thanhVien.setIDTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ThanhVien.COL_NAME_ID))));
            thanhVien.setHoTenTV(cursor.getString(cursor.getColumnIndex(ThanhVien.COL_NAME_HOTEN)));
            thanhVien.setNamsinhTV(cursor.getString(cursor.getColumnIndex(ThanhVien.COL_NAME_NAMSINH)));
            list.add(thanhVien);
        }
        return list;
    }

}

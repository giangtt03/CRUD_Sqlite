package com.example.ph27127_damli_sp24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ph27127_damli_sp24.DB.DbHelper;
import com.example.ph27127_damli_sp24.Models.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDao {
    SQLiteDatabase sqLiteDatabase;
    DbHelper createData;

    public SachDao(Context context) {
        createData = new DbHelper(context);
        sqLiteDatabase = createData.getWritableDatabase();
    }

    public long ADDS(Sach sach) {
        ContentValues values = new ContentValues();
        values.put(Sach.COL_NAME_MALS, sach.getMals());
        values.put(Sach.COL_NAME_TENS, sach.getTens());
        values.put(Sach.COL_NAME_GIAS, sach.getGias());
        values.put(Sach.COL_NAME_tacgia, sach.getTacgia());
        return sqLiteDatabase.insert(Sach.TB_NAME, null, values);
    }

    public int DELETES(Sach sach) {
        return sqLiteDatabase.delete(Sach.TB_NAME, "maSach=?", new String[]{String.valueOf(sach.getMas())});
    }

    public int UPDATES(Sach sach) {
        ContentValues values = new ContentValues();
        values.put(Sach.COL_NAME_MALS, sach.getMals());
        values.put(Sach.COL_NAME_TENS, sach.getTens());
        values.put(Sach.COL_NAME_GIAS, sach.getGias());
        values.put(Sach.COL_NAME_tacgia, sach.getTacgia());
        return sqLiteDatabase.update(Sach.TB_NAME, values, "maSach=?", new String[]{String.valueOf(sach.getMas())});
    }

    public List<Sach> GETS() {
        String dl = "SELECT * FROM Sach";
        List<Sach> list = getdata(dl);
        return list;
    }

    public Sach getId(String id) {
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach> list = getdata(sql, id);
        return list.get(0);
    }

    private List<Sach> getdata(String dl, String... Arays /* có hoặc không nhiều phần tử*/) {
        List<Sach> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(dl, Arays);
        while (cursor.moveToNext()) {
            Sach sach = new Sach();
            sach.setMas(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Sach.COL_NAME_MAS))));
            sach.setMals(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Sach.COL_NAME_MALS))));
            sach.setTens(cursor.getString(cursor.getColumnIndex(Sach.COL_NAME_TENS)));
            sach.setTacgia(cursor.getString(cursor.getColumnIndex(Sach.COL_NAME_tacgia)));
            sach.setGias(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Sach.COL_NAME_GIAS))));
            list.add(sach);
        }
        return list;
    }
}

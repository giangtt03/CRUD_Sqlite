package com.example.ph27127_damli_sp24.Taonv;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;


import com.example.ph27127_damli_sp24.Adapter.Nv_Adapter;
import com.example.ph27127_damli_sp24.DAO.NVDao;
import com.example.ph27127_damli_sp24.Models.NhanVien;
import com.example.ph27127_damli_sp24.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Singup extends Fragment {

    NVDao dao;
    EditText edUser, edPass, edEnPass, edhoten;
    ListView lv_nv;
    FloatingActionButton flb_nv;
    Nv_Adapter adapter;
    List<NhanVien> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.singup_activity, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_nv = view.findViewById(R.id.lisv_taoidnv);
        dao = new NVDao(getActivity());
        dao.OPEN();
        list = new ArrayList<>();
        list = dao.GETNV();
        adapter = new Nv_Adapter(list, getContext());
        lv_nv.setAdapter(adapter);

        flb_nv = view.findViewById(R.id.flonv);
        flb_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.custom_taonv, null);
                builder.setView(view);
                dao = new NVDao(getActivity());
                edUser = view.findViewById(R.id.edUser);
                edhoten = view.findViewById(R.id.edHoTen);
                edPass = view.findViewById(R.id.edPass);
                edEnPass = view.findViewById(R.id.edRePass);
                builder.setTitle("                Tạo Tài Khoản");
                builder.setPositiveButton("Tạo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NhanVien nhanVien = new NhanVien();
                        nhanVien.setMaNV(edUser.getText().toString());
                        nhanVien.setHoTen(edhoten.getText().toString());
                        nhanVien.setMaKhau(edPass.getText().toString());
                        if (checkrong() > 0) {
                            long kq = dao.ADDNV(nhanVien);
                            if (kq > 0) {
                                Toast.makeText(getActivity(), "Tạo Tài khoản thành công", Toast.LENGTH_SHORT).show();
                                edUser.setText("");
                                edhoten.setText("");
                                edPass.setText("");
                                edEnPass.setText("");
                                list.clear();
                                list.addAll(dao.GETNV());
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });

    }


    public int checkrong() {
        int check = 1;
        if (edUser.getText().length() == 0 || edhoten.getText().length() == 0 ||
                edPass.getText().length() == 0 || edEnPass.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String pass = edPass.getText().toString();
            String rePass = edEnPass.getText().toString();
            if (!pass.equals(rePass)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }

}

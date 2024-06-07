package com.example.ph27127_damli_sp24.QuanlyvsThongke.Quanlytheloaisach;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph27127_damli_sp24.Adapter.Ls_Adapter;
import com.example.ph27127_damli_sp24.DAO.LoaiSachDao;
import com.example.ph27127_damli_sp24.Models.LoaiSach;
import com.example.ph27127_damli_sp24.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuanlytheloaiFragment extends Fragment {
    RecyclerView rcl_ls;
    EditText ed_tenls;
    LoaiSachDao lsdao;
    Ls_Adapter adapter;
    FloatingActionButton flb_addls;
    LoaiSachViewModel model;
    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_loaisach, null);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcl_ls = view.findViewById(R.id.rcl_loaisach);
        flb_addls = view.findViewById(R.id.flb_ls);
        lsdao = new LoaiSachDao(getActivity());
        RecyclerView.LayoutManager lymanage = new LinearLayoutManager(getActivity());
        rcl_ls.setLayoutManager(lymanage);
        model = new ViewModelProvider(this).get(LoaiSachViewModel.class);
        model.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<LoaiSach>>() {
            @Override
            public void onChanged(List<LoaiSach> loaiSaches) {
                adapter = new Ls_Adapter(getActivity(), loaiSaches, lsdao);
                rcl_ls.setAdapter(adapter);
            }
        });
        flb_addls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.custom_add_loaisach, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view1);
                EditText ed_ls = (EditText) view1.findViewById(R.id.ed_editls);
                EditText ed_nhcc = (EditText) view1.findViewById(R.id.ed_nhcc);
                builder.setTitle("                Thêm Loại Sách");
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoaiSach loaiSach = new LoaiSach();
                        loaiSach.setTenLS(ed_ls.getText().toString());
                        loaiSach.setNhacc(ed_nhcc.getText().toString());
                        long kq = lsdao.ADDLS(loaiSach);
                        if (kq > 0) {
                            ed_ls.setText("");
                            ed_nhcc.setText("");
                            Toast.makeText(getActivity(), "Thêm Loại Sách Thành Công", Toast.LENGTH_SHORT).show();
                            model.getLiveData();
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Tên Loại Sách Trùng Lặp \n Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
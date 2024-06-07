package com.example.ph27127_damli_sp24.QuanlyvsThongke.Quanlithanhvien;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph27127_damli_sp24.Adapter.TV_Adapter;
import com.example.ph27127_damli_sp24.DAO.ThanhVienDao;
import com.example.ph27127_damli_sp24.Models.ThanhVien;
import com.example.ph27127_damli_sp24.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuanlithanhvienFragment extends Fragment {
    RecyclerView rcl_tv;
    ThanhvienViewModel model;
    ThanhVienDao vienDao;
    FloatingActionButton flb_addtv;
    TV_Adapter adapter;
    SearchView searchView;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flb_addtv = view.findViewById(R.id.flb_addtv);
        rcl_tv = view.findViewById(R.id.recy_thanhvien);
        vienDao = new ThanhVienDao(getActivity());
        searchView =(SearchView) view.findViewById(R.id.id_seach);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcl_tv.setLayoutManager(layoutManager);
        model = new ViewModelProvider(this).get(ThanhvienViewModel.class);
        model.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<ThanhVien>>() {
            @Override
            public void onChanged(List<ThanhVien> thanhViens) {
                adapter = new TV_Adapter(getContext(), thanhViens);
                rcl_tv.setAdapter(adapter);
            }
        });
        flb_addtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view1 = layoutInflater.inflate(R.layout.custom_addthanhvien, null);
                AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                builder.setView(view1);
                builder.setTitle("                Thêm Thành Viên");
                EditText ed_hoten = (EditText) view1.findViewById(R.id.ed_hotentvedit);
                EditText ed_namsinh = (EditText) view1.findViewById(R.id.ed_namstvedit);
                AppCompatButton btn_them = (AppCompatButton) view1.findViewById(R.id.btn_themtvsua);
                AppCompatButton btn_cle = (AppCompatButton) view1.findViewById(R.id.btn_clentvedit);
                btn_them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ed_hoten.getText().length() == 0 || ed_namsinh.getText().length() == 0) {
                            Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
                        } else if (ed_hoten.getText().toString().length() < 5 || ed_hoten.getText().toString().length() > 15) {
                            Toast.makeText(getActivity(), "Độ dài kí tự từ 5-15", Toast.LENGTH_SHORT).show();
                        } else if (!ed_hoten.getText().toString().substring(0, 1).toUpperCase().equals(ed_hoten.getText().toString().substring(0, 1))) {
                            Toast.makeText(getActivity(), "Chữ cái đầu viết hoa", Toast.LENGTH_SHORT).show();
                        } else {
                            ThanhVien thanhVien = new ThanhVien();
                            thanhVien.setHoTenTV(ed_hoten.getText().toString());
                            thanhVien.setNamsinhTV(ed_namsinh.getText().toString());
                            long kq = vienDao.ADDTV(thanhVien);
                            if (kq > 0) {
                                Toast.makeText(getActivity(), "Đã Thêm Thành viên", Toast.LENGTH_SHORT).show();
                                ed_hoten.setText("");
                                ed_namsinh.setText("");
                                model.getLiveData();
                                adapter.notifyDataSetChanged();
                                builder.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Thêm Thành viên Thất Bại", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
                btn_cle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quanlythanhvien, null, false);
    }

}
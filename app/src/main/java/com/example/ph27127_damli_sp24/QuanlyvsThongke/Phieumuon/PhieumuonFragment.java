package com.example.ph27127_damli_sp24.QuanlyvsThongke.Phieumuon;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph27127_damli_sp24.Adapter.PM_Adapter;
import com.example.ph27127_damli_sp24.DAO.PhieuMuonDao;
import com.example.ph27127_damli_sp24.DAO.SachDao;
import com.example.ph27127_damli_sp24.DAO.ThanhVienDao;
import com.example.ph27127_damli_sp24.Models.PhieuMuon;
import com.example.ph27127_damli_sp24.Models.Sach;
import com.example.ph27127_damli_sp24.Models.ThanhVien;
import com.example.ph27127_damli_sp24.R;
import com.example.ph27127_damli_sp24.SpinnerAdapter.SachSpiner;
import com.example.ph27127_damli_sp24.SpinnerAdapter.ThanhVienSpiner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * create an instance of this fragment.
 */
public class PhieumuonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhieumuonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhieumuonFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static PhieumuonFragment newInstance(String param1, String param2) {
        PhieumuonFragment fragment = new PhieumuonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phieumuon, container, false);
    }

    List<PhieuMuon> list;
    PhieuMuonViewModel model;
    RecyclerView rcl_pm;
    FloatingActionButton fbl_add_pm;
    PM_Adapter pm_adapter;
    SachSpiner sachSpiner;
    ThanhVienSpiner thanhVienSpiner;
    int maTV, maSach, Trasach;
    String maNV;
    ThanhVienDao vienDao;
    ArrayList<ThanhVien> vienArrayList;
    SachDao sachDao;
    ArrayList<Sach> sachArrayList;
    PhieuMuonDao muonDao;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcl_pm = view.findViewById(R.id.recyle_pm);
        fbl_add_pm = view.findViewById(R.id.fbl_phieumuon);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcl_pm.setLayoutManager(layoutManager);
        model = new ViewModelProvider(this).get(PhieuMuonViewModel.class);
        muonDao = new PhieuMuonDao(getActivity());
        model.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<PhieuMuon>>() {
            @Override
            public void onChanged(List<PhieuMuon> phieuMuons) {
                pm_adapter = new PM_Adapter(getActivity(), phieuMuons);
                rcl_pm.setAdapter(pm_adapter);
            }
        });

        Intent intent = getActivity().getIntent();
        maNV = intent.getStringExtra("admintion");

        fbl_add_pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view1 = inflater.inflate(R.layout.custom_add_phieumuon, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view1);

                EditText ed_ngaymuon = (EditText) view1.findViewById(R.id.ed_ngaymuon);
                EditText ed_tienthue = (EditText) view1.findViewById(R.id.ed_giamuon);
                Spinner spn_tv = (Spinner) view1.findViewById(R.id.spinnertv);
                Spinner spn_sach = (Spinner) view1.findViewById(R.id.spinnersach);
                ImageView img_date = (ImageView) view1.findViewById(R.id.img_lich);
                CheckBox chk_trasach = (CheckBox) view1.findViewById(R.id.chk_sachtra);

                builder.setTitle("                Thêm Phiếu Mượn");
                // Spinner tv
                vienDao = new ThanhVienDao(getContext());
                vienArrayList = new ArrayList<>();
                vienArrayList = (ArrayList<ThanhVien>) vienDao.GETTV();
                thanhVienSpiner = new ThanhVienSpiner(getContext(), vienArrayList);
                spn_tv.setAdapter(thanhVienSpiner);
                spn_tv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maTV = vienArrayList.get(position).getIDTV();
//                        Log.d("rrrrrrrr",vienArrayList.get(position).getIDTV()+"");
                        Toast.makeText(getActivity(), "Thành Viên: " + vienArrayList.get(position).getHoTenTV(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                // Spiner sach
                sachDao = new SachDao(getContext());
                sachArrayList = new ArrayList<>();
                sachArrayList = (ArrayList<Sach>) sachDao.GETS();
                sachSpiner = new SachSpiner(getContext(), sachArrayList);
                spn_sach.setAdapter(sachSpiner);
                spn_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maSach = sachArrayList.get(position).getMas();
                        Toast.makeText(getActivity(), "Chọn sách: " + sachArrayList.get(position).getTens(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                // date pikerdialog
                img_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                calendar.set(year, month, dayOfMonth);
                                ed_ngaymuon.setText(sdf.format(calendar.getTime()));
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }
                });
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (chk_trasach.isChecked()) {
                            Trasach = 1;
                        } else {
                            Trasach = 0;
                        }
                        if (ed_ngaymuon.getText().length() == 0 || ed_tienthue.getText().length() == 0) {
                            Toast.makeText(getContext(), "Bạn cần phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            if (checkNgay(ed_ngaymuon) && checkgiatien(ed_tienthue)) {

                                PhieuMuon phieuMuon = new PhieuMuon();
                                phieuMuon.setMaNVpm(maNV);
                                phieuMuon.setMaTVpm(maTV);
                                phieuMuon.setMaSpm(maSach);
                                phieuMuon.setNgaymuon(ed_ngaymuon.getText().toString());
                                phieuMuon.setTienthue(Integer.parseInt(ed_tienthue.getText().toString()));
                                phieuMuon.setTrasach(Trasach);
                                long kq = muonDao.ADDPM(phieuMuon);
                                if (kq > 0) {

                                    Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                                    model.getLiveData();
                                    pm_adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }

    public boolean checkNgay(EditText ed) {
        Date date = null;
        String value = ed.getText().toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
                Toast.makeText(getActivity(), "Sai dịnh dạng ngày!", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    public boolean checkgiatien(EditText ed) {
        boolean check = true;
        try {
            Integer.parseInt(ed.getText().toString());
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Tiền thuê phải là số", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }

}
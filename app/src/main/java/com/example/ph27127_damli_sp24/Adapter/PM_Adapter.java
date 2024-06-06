package com.example.ph27127_damli_sp24.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ph27127_damli_sp24.DAO.PhieuMuonDao;
import com.example.ph27127_damli_sp24.DAO.SachDao;
import com.example.ph27127_damli_sp24.DAO.ThanhVienDao;
import com.example.ph27127_damli_sp24.Models.PhieuMuon;
import com.example.ph27127_damli_sp24.Models.Sach;
import com.example.ph27127_damli_sp24.Models.ThanhVien;
import com.example.ph27127_damli_sp24.R;
import com.example.ph27127_damli_sp24.SpinnerAdapter.SachSpiner;
import com.example.ph27127_damli_sp24.SpinnerAdapter.ThanhVienSpiner;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PM_Adapter extends RecyclerView.Adapter<PM_Adapter.PhieuMuonhoder> {
    Context context;
    List<PhieuMuon> list;
    PhieuMuonDao phieuMuonDao;
    SachSpiner sachSpiner;
    ThanhVienSpiner thanhVienSpiner;
    int maTV, maSach, Trasach, mst, mtvt;
    String maNV;
    ThanhVienDao vienDao;
    ArrayList<ThanhVien> vienArrayList;
    SachDao sachDaoe;
    ArrayList<Sach> sachArrayList;


    public PM_Adapter(Context context, List<PhieuMuon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public PhieuMuonhoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_phieumuon, parent, false);
        return new PhieuMuonhoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PhieuMuonhoder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        if (phieuMuon == null) {
            return;
        } else {
            String hoTenTV = "";
            String tenSach = "";
            holder.tv_mapm.setText("Mã Phiếu: " + phieuMuon.getMaPM() + "");
            try {
                ThanhVienDao tv_dao = new ThanhVienDao(context);
                SachDao sachDao = new SachDao(context);
                ThanhVien tv = tv_dao.getId(phieuMuon.getMaTVpm() + "");
                Sach sach = sachDao.getId(phieuMuon.getMaSpm() + "");
                hoTenTV = tv.getHoTenTV();
                tenSach = sach.getTens();
            } catch (Exception e) {
                hoTenTV = "Đã xóa TV";
                tenSach = "Đã xóa Sách";
            }

            // lấy họ tên từ id
//            ThanhVienDao tv_dao = new ThanhVienDao(context);
//            ThanhVien tv = tv_dao.getId(phieuMuon.getMaTVpm() + "");
            holder.tv_matvpm.setText("Tên Thành Viên: " + hoTenTV + "");
            // lấy tên sách
//            SachDao sachDao = new SachDao(context);
//            Sach sach = sachDao.getId(phieuMuon.getMaSpm() + "");
            holder.tv_maspm.setText("Tên Sách: " + tenSach + "");
            // fomat tiền
            Locale locale = new Locale("nv", "VN");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
            String tien = numberFormat.format(phieuMuon.getTienthue());
            holder.tv_giamuon.setText("Tiền Thuê: " + tien);
            holder.tv_ngaymuon.setText("Ngày Mượn: " + phieuMuon.getNgaymuon());
            if (phieuMuon.getTrasach() == 1) {
                holder.tv_trasach.setText("Đã Trả Sách");
                holder.tv_trasach.setTextColor(Color.BLUE);
            } else {
                holder.tv_trasach.setText("Chưa Trả Sách");
                holder.tv_trasach.setTextColor(Color.RED);
            }
        }
        holder.img_dellpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete");
                builder.setIcon(R.drawable.ic_dele);
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        phieuMuonDao = new PhieuMuonDao(context);
                        int kq = phieuMuonDao.DELETEPM(phieuMuon);
                        if (kq > 0) {
                            list.clear();
                            list.addAll(phieuMuonDao.GETPM());
                            // load lại dữ liệu
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context.getApplicationContext(), "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        holder.img_ditpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.custom_edit_phieumuon, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(view);
                builder.setTitle("Sửa Phiếu Mượn");
                EditText ed_ngaymuoned = (EditText) view.findViewById(R.id.ed_ngaymuoned);
                EditText ed_tienthueed = (EditText) view.findViewById(R.id.ed_giamuoned);
                Spinner spn_tved = (Spinner) view.findViewById(R.id.spinnertved);
                Spinner spn_sached = (Spinner) view.findViewById(R.id.spinnersached);
                ImageView img_dateed = (ImageView) view.findViewById(R.id.img_liched);
                CheckBox chk_trasached = (CheckBox) view.findViewById(R.id.chk_sachtraed);
                ed_ngaymuoned.setText(phieuMuon.getNgaymuon());
                ed_tienthueed.setText(Integer.toString(phieuMuon.getTienthue()));
                // hiển thị
                if (phieuMuon.getTrasach() == 1) {
                    chk_trasached.setChecked(true);
                } else {
                    chk_trasached.setChecked(false);

                }
                // spiner thanh vien
                vienDao = new ThanhVienDao(view.getContext());
                vienArrayList = new ArrayList<>();
                vienArrayList = (ArrayList<ThanhVien>) vienDao.GETTV();
                thanhVienSpiner = new ThanhVienSpiner(view.getContext(), vienArrayList);
                spn_tved.setAdapter(thanhVienSpiner);
                spn_tved.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maTV = vienArrayList.get(position).getIDTV();
//                        Log.d("rrrrrrrr",vienArrayList.get(position).getIDTV()+"");
                        Toast.makeText(view.getContext(), "Thành Viên: " + vienArrayList.get(position).getHoTenTV(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                // Spiner sach
                sachDaoe = new SachDao(view.getContext());
                sachArrayList = new ArrayList<>();
                sachArrayList = (ArrayList<Sach>) sachDaoe.GETS();
                sachSpiner = new SachSpiner(view.getContext(), sachArrayList);
                spn_sached.setAdapter(sachSpiner);
                spn_sached.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maSach = sachArrayList.get(position).getMas();
                        Toast.makeText(view.getContext(), "Chọn sách: " + sachArrayList.get(position).getTens(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                mst = 0;
                for (int i = 0; i < sachArrayList.size(); i++) {
                    if (phieuMuon.getMaSpm() == sachArrayList.get(i).getMas()) {
                        mst = i;
                    }
                }
                spn_sached.setSelection(mst);
                mtvt = 0;
                for (int i = 0; i < vienArrayList.size(); i++) {
                    if (phieuMuon.getMaTVpm() == vienArrayList.get(i).getIDTV()) {
                        mtvt = i;
                    }
                }
                spn_tved.setSelection(mtvt);
                // date pikerdialog
                img_dateed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                calendar.set(year, month, dayOfMonth);
                                ed_ngaymuoned.setText(sdf.format(calendar.getTime()));
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }
                });
//                Intent intent = Contextm.getIntent();
//                maNV = intent.getStringExtra("admintion");
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (chk_trasached.isChecked()) {
                            Trasach = 1;
                        } else {
                            Trasach = 0;
                        }
                        if (phieuMuon.getNgaymuon().equals(ed_ngaymuoned.getText().toString())
                                && phieuMuon.getTienthue() == Integer.parseInt(ed_tienthueed.getText().toString())
                                && phieuMuon.getMaTVpm() == mtvt
                                && phieuMuon.getMaSpm() == mst
                                && phieuMuon.getTrasach() == phieuMuon.getTrasach()) {

                            Toast.makeText(view.getContext(), "Không có thay đổi \n Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                        } else {
                            if (checkNgay(ed_ngaymuoned) && checkgiatien(ed_tienthueed)) {

                                //  PhieuMuon phieuMuon = new PhieuMuon();
//                                phieuMuon.setMaNVpm(maNV);
                                phieuMuon.setMaTVpm(maTV);
                                phieuMuon.setMaSpm(maSach);
                                phieuMuon.setNgaymuon(ed_ngaymuoned.getText().toString());
                                phieuMuon.setTienthue(Integer.parseInt(ed_tienthueed.getText().toString()));
                                phieuMuon.setTrasach(Trasach);
                                long kq = phieuMuonDao.UPDATEPM(phieuMuon);
                                if (kq > 0) {
                                    list.clear();
                                    list.addAll(phieuMuonDao.GETPM());
                                    Toast.makeText(view.getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(view.getContext(), "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public boolean checkNgay(EditText ed) {
        Date date = null;
        String value = ed.getText().toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
                Toast.makeText(context.getApplicationContext(), "Sai dịnh dạng ngày!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context.getApplicationContext(), "Tiền thuê phải là số", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }

    public class PhieuMuonhoder extends RecyclerView.ViewHolder {
        TextView tv_mapm, tv_matvpm, tv_maspm, tv_ngaymuon, tv_giamuon, tv_trasach;
        ImageView img_dellpm, img_ditpm;
        ConstraintLayout cnt_pm;

        public PhieuMuonhoder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_mapm = itemView.findViewById(R.id.tv_maphieumuon);
            tv_matvpm = itemView.findViewById(R.id.tv_maTVmuon);
            tv_maspm = itemView.findViewById(R.id.tv_masachphieumuon);
            tv_ngaymuon = itemView.findViewById(R.id.tv_ngaymuon);
            tv_giamuon = itemView.findViewById(R.id.tv_giathuesach);
            tv_trasach = itemView.findViewById(R.id.tv_checktrasach);
            img_dellpm = itemView.findViewById(R.id.img_delete_pm);
            img_ditpm = itemView.findViewById(R.id.img_edit_pm);
            cnt_pm = itemView.findViewById(R.id.cns_pm);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition);
            cnt_pm.setAnimation(animation);

            phieuMuonDao = new PhieuMuonDao(context);
        }
    }
}

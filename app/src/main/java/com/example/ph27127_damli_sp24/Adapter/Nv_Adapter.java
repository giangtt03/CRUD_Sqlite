package com.example.ph27127_damli_sp24.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph27127_damli_sp24.DAO.NVDao;
import com.example.ph27127_damli_sp24.Models.NhanVien;
import com.example.ph27127_damli_sp24.R;

import java.util.List;

public class Nv_Adapter extends BaseAdapter {
    private List<NhanVien> list;
    private Context context;
    private NVDao nvdao;

    public Nv_Adapter(List<NhanVien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // cái nay hơi sai m tìm xem lại
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(context, R.layout.custom_list_tk, null);

        } else {
            view = convertView;
        }
        NhanVien nhanVien = list.get(position);
        TextView tv_user = (TextView) view.findViewById(R.id.tv_usernv);
        TextView tv_pss = (TextView) view.findViewById(R.id.tv_passnv);
        TextView tv_acout = (TextView) view.findViewById(R.id.tv_id);
        tv_acout.setText("Họ và Tên: " + nhanVien.getHoTen());
        tv_user.setText("USER: " + nhanVien.getMaNV());
        tv_pss.setText("PASS: ******** " );
        ImageView img_dele = view.findViewById(R.id.img_delete);
        img_dele.setOnClickListener(new View.OnClickListener() {
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
                        nvdao = new NVDao(context);
                        nvdao.OPEN();
                        int kq = nvdao.DELETE(nhanVien.getMaNV());
                        if (kq > 0) {
                            list.clear();
                            list.addAll(nvdao.GETNV());
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Xóa Nhân Viên Thành Công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa Nhân Viên Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                        nvdao.Close();
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

        return view;
    }
}

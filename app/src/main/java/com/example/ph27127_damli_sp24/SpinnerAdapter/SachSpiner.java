package com.example.ph27127_damli_sp24.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.ph27127_damli_sp24.Models.Sach;
import com.example.ph27127_damli_sp24.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SachSpiner extends ArrayAdapter<Sach> {
    Context context;
    ArrayList<Sach> list;
    TextView tvTenSach;

    public SachSpiner(@NonNull @NotNull Context context, ArrayList<Sach> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spiner_sach, null);
        }
        Sach sach = list.get(position);
        if (sach != null) {
            tvTenSach = view.findViewById(R.id.tv_spiner_sach);
            tvTenSach.setText(sach.getTens());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spiner_sach, null);
        }
        Sach sach = list.get(position);
        if (sach != null) {
            tvTenSach = view.findViewById(R.id.tv_spiner_sach);
            tvTenSach.setText(sach.getTens());
        }
        return view;
    }
}

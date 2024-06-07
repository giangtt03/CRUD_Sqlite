package com.example.ph27127_damli_sp24.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ph27127_damli_sp24.DAO.NVDao;
import com.example.ph27127_damli_sp24.Models.NhanVien;
import com.example.ph27127_damli_sp24.R;

import org.jetbrains.annotations.NotNull;

public class DoiPass extends Fragment {
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.doipass_fragment, null, false);

    }
    NVDao nvdao;
    EditText ed_passnew, ed_passold, ed_passen;
    Button btn_save, btn_clear;
    NhanVien nhanVien;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_passold = view.findViewById(R.id.edpassold);
        ed_passnew = view.findViewById(R.id.edPassnew);
        ed_passen = view.findViewById(R.id.edEnterpass);
        btn_clear = view.findViewById(R.id.btn_clear);
        btn_save = view.findViewById(R.id.btn_save);
        nvdao = new NVDao(getActivity());
        String user = getActivity().getIntent().getStringExtra("admintion");
        nhanVien = nvdao.getId(user);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_passold.setText("");
                ed_passnew.setText("");
                ed_passen.setText("");
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkn() > 0) {
                    nhanVien.setMaKhau(ed_passnew.getText().toString());
                    int kq = nvdao.Thaypass(nhanVien);
                    if (kq > 0) {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        ed_passold.setText("");
                        ed_passnew.setText("");
                        ed_passen.setText("");
                    }else {
                        Toast.makeText(getActivity(),"Thay đổi mật khẩu thất bại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public int checkn() {
        int check = 1;
        if (ed_passold.getText().length() == 0 || ed_passnew.getText().length() == 0 || ed_passen.getText().length() == 0) {
            Toast.makeText(getActivity(), "Hãy điền đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String passold = nhanVien.getMaKhau();
            String passnew = ed_passnew.getText().toString();
            String passent = ed_passen.getText().toString();
            if (!passold.equals(ed_passold.getText().toString())) {
                Toast.makeText(getActivity(), "Sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                return check = -1;
            }
            if (!passnew.equals(passent)) {
                Toast.makeText(getActivity(), "Nhập lại mật khẩu không giống", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}
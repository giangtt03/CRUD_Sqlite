package com.example.ph27127_damli_sp24.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.ph27127_damli_sp24.DAO.NVDao;
import com.example.ph27127_damli_sp24.MainActivity;
import com.example.ph27127_damli_sp24.Models.NhanVien;
import com.example.ph27127_damli_sp24.R;


public class Login extends AppCompatActivity {
    private AppCompatButton btn_login, btn_clear;
    private EditText ed_user, ed_pass;
    Intent intent;
    NVDao nvdao;
    CheckBox chk_remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        btn_login = findViewById(R.id.login_btn);
        btn_clear = findViewById(R.id.btn_clear);
        ed_user = findViewById(R.id.ed_user);
        ed_pass = findViewById(R.id.ed_pass);

        chk_remember = findViewById(R.id.chk_remember);
        nvdao = new NVDao(this);
//        NhanVien nhanVien = nvdao.getUser("admin");
        nvdao.OPEN();
        if (nvdao.getUserName("admin") < 0) {
            nvdao.ADDNV(new NhanVien("admin", "admin", "admin"));
        }
        // Đọc Sharepreferences
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        ed_user.setText(preferences.getString("USERNAME", "admin"));
        ed_pass.setText(preferences.getString("PASSWORD", "admin"));
        chk_remember.setChecked(preferences.getBoolean("REMEMBER", false));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checklogin();
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }

    public void checklogin() {
        String usered = ed_user.getText().toString();
        String passed = ed_pass.getText().toString();
        if (usered.isEmpty() || passed.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Tên đăng nhập không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (nvdao.getlogin(usered, passed) > 0 || (usered.equalsIgnoreCase("admin") && passed.equalsIgnoreCase("admin"))
                    || (usered.equalsIgnoreCase("user") && passed.equalsIgnoreCase("user"))) {
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(usered, passed, chk_remember.isChecked());
                startActivity(intent = new Intent(Login.this, MainActivity.class).putExtra("admintion", usered));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Đăng nhập thất bại ! " +
                        "\nSai tài khoản,mật khẩu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String user, String pass, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status) {
            // Xóa lưu trữ trước đó
            editor.clear();
        } else {
            // Lưu dữ liệu
            editor.putString("USERNAME", user);
            editor.putString("PASSWORD", pass);
            editor.putBoolean("REMEMBER", status);
        }
        // LƯu lại toàn bộ dữ liệu
        editor.commit();
    }

}
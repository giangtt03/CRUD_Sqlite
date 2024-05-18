package com.example.ph27127_damli_sp24;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ph27127_damli_sp24.Login.Login;
import com.example.ph27127_damli_sp24.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    View mHeaderview;
    TextView tv_user, tv_email;
    ImageView img_user;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_Quanlyphieu, R.id.nav_top10,
                R.id.nav_tong, R.id.nav_admin,R.id.nav_doimk)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        mHeaderview = navigationView.getHeaderView(0);
        tv_user = mHeaderview.findViewById(R.id.tv_user);
        img_user = mHeaderview.findViewById(R.id.img_user);
        Intent intent = getIntent();
        String user = intent.getStringExtra("admintion");

        if (user != null) {
            tv_user.setText("Wellcome " + user + "!");
            if (user.equals("admin")) {
                img_user.setImageResource(R.drawable.ic_administrator);
                navigationView.getMenu().findItem(R.id.nav_admin).setVisible(true);
            } else {
                navigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
            }
        } else {
            tv_user.setText("Wellcome Guest!");
            navigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void Exit(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_baseline_cancel_24);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
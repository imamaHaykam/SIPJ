package com.example.aplikasisipj.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.aplikasisipj.AdminActivity;
import com.example.aplikasisipj.ListTim;
import com.example.aplikasisipj.ListUser;
import com.example.aplikasisipj.R;
import com.example.aplikasisipj.StatistikTim;
import com.example.aplikasisipj.Statistika;
import com.example.aplikasisipj.Surat;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_admin:
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_listTim:
                Intent intent1 = new Intent(MainActivity.this, ListTim.class);
                startActivity(intent1);
                break;
            case R.id.nav_listUser:
                Intent intent2 = new Intent(MainActivity.this, ListUser.class);
                startActivity(intent2);
                break;
            case R.id.nav_surat:
                Intent intent3 = new Intent(MainActivity.this, Surat.class);
                startActivity(intent3);
                break;
            case R.id.nav_statistika:
                Intent intent4 = new Intent(MainActivity.this, Statistika.class);
                startActivity(intent4);
                break;
            case R.id.nav_statistikTim:
                Intent intent5 = new Intent(MainActivity.this, StatistikTim.class);
                startActivity(intent5);
                break;
//            case R.id.nav_logout:
//                Intent intent6 = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent6);
//                break;
            case R.id.nav_logout:
                break;


        }

        return true;
    }
}
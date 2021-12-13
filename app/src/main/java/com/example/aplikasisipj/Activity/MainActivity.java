package com.example.aplikasisipj.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.aplikasisipj.AdminActivity;
import com.example.aplikasisipj.ListTim;
import com.example.aplikasisipj.ListUser;
import com.example.aplikasisipj.LoginRegister.Login;
import com.example.aplikasisipj.R;
import com.example.aplikasisipj.SessionManager;
import com.example.aplikasisipj.StatistikTim;
import com.example.aplikasisipj.Statistika;
import com.example.aplikasisipj.Surat;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    SessionManager sessionManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(MainActivity.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        CardView cardadmin = findViewById(R.id.cardAdmin);
        cardadmin.setOnClickListener(this);
        CardView cardlstim = findViewById(R.id.cardTim);
        cardlstim.setOnClickListener(this);
        CardView carduser = findViewById(R.id.cardUser);
        carduser.setOnClickListener(this);
        CardView cardsurat = findViewById(R.id.cardSurat);
        cardsurat.setOnClickListener(this);
        CardView cardsts = findViewById(R.id.cardStatistik);
        cardsts.setOnClickListener(this);
        CardView cardststim = findViewById(R.id.cardStsTim);
        cardststim.setOnClickListener(this);
        CardView cardLogout = findViewById(R.id.cardLogout);
        cardLogout.setOnClickListener(this);
    }

    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
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

        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_home) {
        } else if (itemId == R.id.nav_admin) {
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_listTim) {
            Intent intent1 = new Intent(MainActivity.this, ListTim.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_listUser) {
            Intent intent2 = new Intent(MainActivity.this, ListUser.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_surat) {
            Intent intent3 = new Intent(MainActivity.this, Surat.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_statistika) {
            Intent intent4 = new Intent(MainActivity.this, Statistika.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_statistikTim) {
            Intent intent5 = new Intent(MainActivity.this, StatistikTim.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_logout) {
            sessionManager.logoutSession();
            moveToLogin();
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cardAdmin) {
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
        } else if (id == R.id.cardTim) {
            Intent intent1 = new Intent(MainActivity.this, ListTim.class);
            startActivity(intent1);
        } else if (id == R.id.cardUser) {
            Intent intent2 = new Intent(MainActivity.this, ListUser.class);
            startActivity(intent2);
        } else if (id == R.id.cardSurat) {
            Intent intent3 = new Intent(MainActivity.this, Surat.class);
            startActivity(intent3);
        } else if (id == R.id.cardStatistik) {
            Intent intent4 = new Intent(MainActivity.this, Statistika.class);
            startActivity(intent4);
            Intent intent5 = new Intent(MainActivity.this, StatistikTim.class);
            startActivity(intent5);
        } else if (id == R.id.cardStsTim) {
            Intent intent5 = new Intent(MainActivity.this, StatistikTim.class);
            startActivity(intent5);
        } else if (id == R.id.cardLogout) {
            sessionManager.logoutSession();
            moveToLogin();
        }
    }
}
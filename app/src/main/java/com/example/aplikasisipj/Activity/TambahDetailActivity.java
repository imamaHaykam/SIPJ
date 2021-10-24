package com.example.aplikasisipj.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.aplikasisipj.Model.DataModel;
import com.example.aplikasisipj.R;
import com.example.aplikasisipj.Tools;


public class TambahDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img;
    private DataModel tambah = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_detail);
        tambah = getIntent().getParcelableExtra("TAMBAH");

        img = findViewById(R.id.img_tambah);
        findViewById(R.id.fab_edit).setOnClickListener(this);

        String imageUrl = (new Tools()).BASE_URL + tambah.getImage();
        Glide.with(this)
                .load(imageUrl)
                .into(img);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_edit) {
            Intent intent = new Intent(this, UpdateActivity.class);
            intent.putExtra("TAMBAH", tambah);
            startActivity(intent);
            finish();
        }
    }
}
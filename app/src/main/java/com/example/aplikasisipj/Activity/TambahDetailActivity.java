package com.example.aplikasisipj.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.aplikasisipj.Model.DataModel;
import com.example.aplikasisipj.R;
import com.example.aplikasisipj.Tools;

public class TambahDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img;
    private TextView title;
    private TextView tangal;
    private TextView alamat;
    private TextView fasilitas;
    private TextView status;
    private DataModel tambah = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_detail);
        tambah = getIntent().getParcelableExtra("TAMBAH");

        img = findViewById(R.id.img_tambah);
        title = findViewById(R.id.nama);
        tangal = findViewById(R.id.tangal);
        alamat = findViewById(R.id.alamat);
        fasilitas = findViewById(R.id.fasilitas);
        status = findViewById(R.id.status);
        findViewById(R.id.fab_edit).setOnClickListener(this);

        //set values
        String imageUrl = (new Tools()).BASE_URL + tambah.getImage();
        title.setText(tambah.getNama());
        tangal.setText(tambah.getTanggal());
        alamat.setText(tambah.getAlamat());
        fasilitas.setText(tambah.getFasilitas());
        status.setText(tambah.getStatus());
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
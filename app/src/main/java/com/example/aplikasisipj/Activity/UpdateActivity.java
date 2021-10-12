package com.example.aplikasisipj.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasisipj.API.APIRequestData;
import com.example.aplikasisipj.API.RetroServer;
import com.example.aplikasisipj.Model.ResponseModel;
import com.example.aplikasisipj.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    private int xId;
    private String xNama, xTanggal, xAlamat, xFasilitas, xStatus;
    private EditText etNama, etTanggal, etAlamat, etFasilitas, etStatus;
    private Button btnUpdate;
    private String yNama, yTanggal, yAlamat, yFasilitas, yStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent receive = getIntent();
        xId = receive.getIntExtra("xId", -1);
        xNama = receive.getStringExtra("xNama");
        xTanggal = receive.getStringExtra("xTanggal");
        xAlamat = receive.getStringExtra("xAlamat");
        xFasilitas = receive.getStringExtra("xFasilitas");
        xStatus = receive.getStringExtra("xStatus");

        etNama = findViewById(R.id.et_nama);
        etTanggal = findViewById(R.id.et_tanggal);
        etAlamat = findViewById(R.id.et_alamat);
        etFasilitas = findViewById(R.id.et_fasilitas);
        etStatus = findViewById(R.id.et_status);
        btnUpdate = findViewById(R.id.btn_update);

        etNama.setText(xNama);
        etTanggal.setText(xTanggal);
        etAlamat.setText(xAlamat);
        etFasilitas.setText(xFasilitas);
        etStatus.setText(xStatus);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yNama = etNama.getText().toString();
                yTanggal = etTanggal.getText().toString();
                yAlamat = etAlamat.getText().toString();
                yFasilitas = etFasilitas.getText().toString();
                yStatus = etStatus.getText().toString();

                updateData();
            }
        });
    }

    private void updateData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> updateData = ardData.ardUpdateData(xId, yNama, yTanggal, yAlamat, yFasilitas ,yStatus);

        updateData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UpdateActivity.this, "kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
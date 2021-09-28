package com.example.aplikasisipj.Activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etTanggal, etAlamat, etFasilitas, etStatus;
    private Button btnSimpan;
    private String nama, tanggal, alamat, fasilitas, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etTanggal = findViewById(R.id.et_tanggal);
        etAlamat = findViewById(R.id.et_alamat);
        etFasilitas = findViewById(R.id.et_fasilitas);
        etStatus = findViewById(R.id.et_status);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = etNama.getText().toString();
                tanggal = etTanggal.getText().toString();
                alamat = etAlamat.getText().toString();
                fasilitas = etFasilitas.getText().toString();
                status = etStatus.getText().toString();

                if(nama.trim().equals("")){
                    etNama.setError("Harus Diisi");
                }
                else if(tanggal.trim().equals("")){
                    etTanggal.setError("Harus Diisi");
                }
                else if(alamat.trim().equals("")){
                    etAlamat.setError("Harus Diisi");
                }
                else if(fasilitas.trim().equals("")){
                    etFasilitas.setError("Harus Diisi");
                }
                else if(status.trim().equals("")){
                    etStatus.setError("Harus Diisi");
                }
                else {
                    createData();
                }
            }
        });
    }

    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardcreateData(nama, tanggal, alamat, fasilitas, status);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
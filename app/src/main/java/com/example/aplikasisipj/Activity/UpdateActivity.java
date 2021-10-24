package com.example.aplikasisipj.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.aplikasisipj.API.APIRequestData;
import com.example.aplikasisipj.API.RetroServer;
import com.example.aplikasisipj.Model.DataModel;
import com.example.aplikasisipj.Model.ResponseModel;
import com.example.aplikasisipj.R;
import com.example.aplikasisipj.Tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private int xId;
    private EditText etNama, etTanggal, etAlamat, etFasilitas, etStatus;
    private Button btnUpdate;
    private String xNama, xTanggal, xAlamat, xFasilitas, xStatus;
    private String yNama, yTanggal, yAlamat, yFasilitas, yStatus;
    private DataModel tambah = null;
    private ImageView img;
    private static final int IMAGE_UPLOAD_REQUEST = 1;
    private static final int PERMISSIONS_REQUEST = 2;
    private Bitmap selectedImage = null;
    private MultipartBody.Part imagePart = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        etNama = findViewById(R.id.et_nama);
        etTanggal = findViewById(R.id.et_tanggal);
        etAlamat = findViewById(R.id.et_alamat);
        etFasilitas = findViewById(R.id.et_fasilitas);
        etStatus = findViewById(R.id.et_status);
        btnUpdate = findViewById(R.id.btn_update);
        img = findViewById(R.id.img_upload);

        img.setOnClickListener(this);

        tambah = getIntent().getParcelableExtra("TAMBAH");
        if (tambah != null) {
            xId = tambah.getId();
            etNama.setText(tambah.getNama());
            etTanggal.setText(tambah.getTanggal());
            etAlamat.setText(tambah.getAlamat());
            etFasilitas.setText(tambah.getFasilitas());
            etStatus.setText(tambah.getStatus());
            String imageUrl = (new Tools()).BASE_URL + tambah.getImage();
            Glide.with(this)
                    .load(imageUrl)
                    .into(img);
        }

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

    private void updateData() {
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> updateData = ardData.ardUpdateData(
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(xId)),
                RequestBody.create(MediaType.parse("multipart/form-data"), yNama),
                RequestBody.create(MediaType.parse("multipart/form-data"), yTanggal),
                RequestBody.create(MediaType.parse("multipart/form-data"), yAlamat),
                RequestBody.create(MediaType.parse("multipart/form-data"), yFasilitas),
                RequestBody.create(MediaType.parse("multipart/form-data"), yStatus),
                imagePart
        );

        updateData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

//                Toast.makeText(UpdateActivity.this, "kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Gagal Menghubungi Server | " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startImageUploadProcess() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST
            );
        } else {
            launchImageUploadIntent();
        }
    }

    private void launchImageUploadIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Open Gallery"), IMAGE_UPLOAD_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_UPLOAD_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri selectedImageUrl = data.getData();
                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUrl);

                    File file = new File(this.getCacheDir(), System.currentTimeMillis() + ".png");
                    file.createNewFile();

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 0, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                    imagePart = MultipartBody.Part.createFormData("Image", file.getName(), requestFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                img.setImageBitmap(selectedImage);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_upload){
            startImageUploadProcess();
        }
    }
}
package com.example.aplikasisipj.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.aplikasisipj.API.APIRequestData;
import com.example.aplikasisipj.API.RetroServer;
import com.example.aplikasisipj.Model.ResponseModel;
import com.example.aplikasisipj.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {
    private EditText etNama, etTanggal, etAlamat, etFasilitas, etStatus;
    private Button btnSimpan, btnLokasi;
    private ImageView imgUpload;
    private String nama, tanggal, alamat, fasilitas, status;
    private static final int IMAGE_UPLOAD_REQUEST = 1;
    private static final int PERMISSIONS_REQUEST = 2;
    private Bitmap selectedImage = null;
    private MultipartBody.Part imagePart = null;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        Spinner spinnerFasilitas = findViewById(R.id.sp_fasilitas);
        Spinner spinnerStatus = findViewById(R.id.sp_status);
        etNama = findViewById(R.id.et_nama);
        etTanggal = findViewById(R.id.et_tanggal);
        etAlamat = findViewById(R.id.et_alamat);
        etFasilitas = findViewById(R.id.et_fasilitas);
        etStatus = findViewById(R.id.et_status);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnLokasi = findViewById(R.id.btn_lokasi);
        imgUpload = findViewById(R.id.img_upload);

        if (ContextCompat.checkSelfPermission(TambahActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TambahActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        final String strFasilitas[]={"Rambu","Zebra Cross","Traffic Light","Barrier","Marka","Warning Light","RPPJ","Papan Nama Jalan","Speed Bump","Cermin Tikungan"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(TambahActivity.this, android.R.layout.simple_dropdown_item_1line,strFasilitas);
        spinnerFasilitas.setAdapter(arrayAdapter);
        spinnerFasilitas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(strFasilitas[0].equals(spinnerFasilitas.getItemAtPosition(i).toString())){
                    etFasilitas.setText(R.string.Rambu);
                } else if(strFasilitas[1].equals(spinnerFasilitas.getItemAtPosition(i).toString())){
                    etFasilitas.setText(R.string.ZebraCross);
                } else if(strFasilitas[2].equals(spinnerFasilitas.getItemAtPosition(i).toString())){
                    etFasilitas.setText(R.string.TrafficLight);
                } else if(strFasilitas[3].equals(spinnerFasilitas.getItemAtPosition(i).toString())){
                    etFasilitas.setText(R.string.Barrier);
                } else if(strFasilitas[4].equals(spinnerFasilitas.getItemAtPosition(i).toString())){
                    etFasilitas.setText(R.string.Marka);
                } else if(strFasilitas[5].equals(spinnerFasilitas.getItemAtPosition(i).toString())){
                    etFasilitas.setText(R.string.WarningLight);
                } else if(strFasilitas[6].equals(spinnerFasilitas.getItemAtPosition(i).toString())){
                    etFasilitas.setText(R.string.RPPJ);
                } else if(strFasilitas[7].equals(spinnerFasilitas.getItemAtPosition(i).toString())){
                    etFasilitas.setText(R.string.PapanNamaJalan);
                } else if(strFasilitas[8].equals(spinnerFasilitas.getItemAtPosition(i).toString())){
                    etFasilitas.setText(R.string.SpeedBump);
                } else if(strFasilitas[9].equals(spinnerFasilitas.getItemAtPosition(i).toString())){
                    etFasilitas.setText(R.string.CerminTikungan);
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final String strStatus[]={"Rencana","Baru","Normal","Rusak Ringan","Rusak Sedang","Rusak Berat"};
        ArrayAdapter arrayAdapterS = new ArrayAdapter(TambahActivity.this, android.R.layout.simple_dropdown_item_1line,strStatus);
        spinnerStatus.setAdapter(arrayAdapterS);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(strStatus[0].equals(spinnerStatus.getItemAtPosition(i).toString())){
                    etStatus.setText(R.string.Rencana);
                } else if(strStatus[1].equals(spinnerStatus.getItemAtPosition(i).toString())){
                    etStatus.setText(R.string.Baru);
                } else if(strStatus[2].equals(spinnerStatus.getItemAtPosition(i).toString())){
                    etStatus.setText(R.string.Normal);
                } else if(strStatus[3].equals(spinnerStatus.getItemAtPosition(i).toString())){
                    etStatus.setText(R.string.RusakRingan);
                } else if(strStatus[4].equals(spinnerStatus.getItemAtPosition(i).toString())){
                    etStatus.setText(R.string.RusakSedang);
                } else if(strStatus[5].equals(spinnerStatus.getItemAtPosition(i).toString())){
                    etStatus.setText(R.string.RusakBerat);
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imgUpload.setOnClickListener(this);

        btnSimpan.setOnClickListener(this);

//        btnLokasi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openActivitytracklokasi();
//            }
//        });

//        String textLocation = getIntent().getStringExtra("keyname");
//        etAlamat.setText(textLocation);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, TambahActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();

        try {
            Geocoder geocoder = new Geocoder(TambahActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);

            etAlamat.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

//    public void openActivitytracklokasi() {
//        Intent intent = new Intent(TambahActivity.this, Activitytracklokasi.class);
//        startActivity(intent);
//    }

    private void createData() {
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardcreateData(
                RequestBody.create(MediaType.parse("multipart/form-data"), nama),
                RequestBody.create(MediaType.parse("multipart/form-data"), tanggal),
                RequestBody.create(MediaType.parse("multipart/form-data"), alamat),
                RequestBody.create(MediaType.parse("multipart/form-data"), fasilitas),
                RequestBody.create(MediaType.parse("multipart/form-data"), status),
                imagePart);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String pesan = response.body().getPesan();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server | " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.img_upload) {
            startImageUploadProcess();
        } else if (id == R.id.btn_simpan) {
            nama = etNama.getText().toString();
            tanggal = etTanggal.getText().toString();
            alamat = etAlamat.getText().toString();
            fasilitas = etFasilitas.getText().toString();
            status = etStatus.getText().toString();

            if (selectedImage == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            } else if (nama.trim().equals("")) {
                etNama.setError("Harus Diisi");
            } else if (tanggal.trim().equals("")) {
                etTanggal.setError("Harus Diisi");
            } else if (alamat.trim().equals("")) {
                etAlamat.setError("Harus Diisi");
            } else if (fasilitas.trim().equals("")) {
                etFasilitas.setError("Harus Diisi");
            } else if (status.trim().equals("")) {
                etStatus.setError("Harus Diisi");
            } else {
                createData();
            }
        }
    }

    private void startImageUploadProcess() {
            launchImageUploadIntent();
    }

    private void launchImageUploadIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_UPLOAD_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_UPLOAD_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                selectedImage = (Bitmap) extras.get("data");
                try {
                    File file = new File(this.getCacheDir(), System.currentTimeMillis() +".png");
                    file.createNewFile();

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 0 , bos);
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
                imgUpload.setImageBitmap(selectedImage);
            }
        }
    }
}

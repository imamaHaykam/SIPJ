package com.example.aplikasisipj.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasisipj.API.APIRequestData;
import com.example.aplikasisipj.API.RetroServer;
import com.example.aplikasisipj.Activity.MainActivity;
import com.example.aplikasisipj.Activity.TambahActivity;
import com.example.aplikasisipj.Model.SignUp.Signup;
import com.example.aplikasisipj.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText etFullName, etUsername, etPassword;
    Button buttonSignUp;
    TextView textViewLogin;
    String Nama, Username, Password;
    APIRequestData APIRequestData;
//    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFullName = findViewById(R.id.et_registerFullName);
        etUsername = findViewById(R.id.et_registerUsername);
        etPassword = findViewById(R.id.et_registerPassword);

        buttonSignUp = findViewById(R.id.btn_Signup);
        buttonSignUp.setOnClickListener(this);

        textViewLogin = findViewById(R.id.tv_createAccount);
        textViewLogin.setOnClickListener(this);
//        progressBar = findViewById(R.id.progress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Signup:
                Nama = etFullName.getText().toString();
                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();
                Register(Nama, Username, Password);
                break;
            case R.id.tv_createAccount:
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                break;
        }
    }

    private void Register(String nama, String username, String password) {

        APIRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<Signup> call = APIRequestData.ardSignup(nama, username, password);
        call.enqueue(new Callback<Signup>() {
            @Override
            public void onResponse(Call<Signup> call, Response<Signup> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(SignUp.this, response.body().getData().getNama(), Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(SignUp.this, Login.class);
                    startActivity(intent1);
                    finish();
                } else {
                    Toast.makeText(SignUp.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Signup> call, Throwable t) {
                Toast.makeText(SignUp.this, "Gagal Menghubungi Server | " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
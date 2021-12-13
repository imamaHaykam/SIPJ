package com.example.aplikasisipj.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.example.aplikasisipj.Model.Login.LoginData;
import com.example.aplikasisipj.R;
import com.example.aplikasisipj.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    String Username, Password;
    APIRequestData APIRequestData;
    SessionManager sessionManager;
//    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_loginUsername);
        etPassword = findViewById(R.id.et_loginPassword);

        textViewSignUp = findViewById(R.id.tv_createAccount);
        textViewSignUp.setOnClickListener(this);

        buttonLogin = findViewById(R.id.btn_Login);
        buttonLogin.setOnClickListener(this);
//        progressBar = findViewById(R.id.progress);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Login:
                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();
                login(Username, Password);
                break;

            case R.id.tv_createAccount:
                Intent intent1 = new Intent(this, SignUp.class);
                startActivity(intent1);
                break;

        }
    }

    private void login(String Username, String Password) {

        APIRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<com.example.aplikasisipj.Model.Login.Login> loginCall = APIRequestData.ardLogin(Username, Password);
        loginCall.enqueue(new Callback<com.example.aplikasisipj.Model.Login.Login>() {
            @Override
            public void onResponse(Call<com.example.aplikasisipj.Model.Login.Login> call, Response<com.example.aplikasisipj.Model.Login.Login> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){

                    sessionManager = new SessionManager(getApplicationContext());
                    LoginData loginData = response.body().getData();
                    sessionManager.createLoginSession(loginData);

                    Toast.makeText(Login.this, response.body().getData().getNama(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<com.example.aplikasisipj.Model.Login.Login> call, Throwable t) {

            }
        });

    }
}
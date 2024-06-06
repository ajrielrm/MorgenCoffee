package com.morgen.morgencoffee.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.morgen.morgencoffee.Component.ErrorDialog;
import com.morgen.morgencoffee.Component.LoadingDialog;
import com.morgen.morgencoffee.Component.SuccessDialog;
import com.morgen.morgencoffee.Helper.API;
import com.morgen.morgencoffee.Helper.SPHelper;
import com.morgen.morgencoffee.MainActivity;
import com.morgen.morgencoffee.Model.LoginModel;
import com.morgen.morgencoffee.Response.LoginResponse;
import com.uas.nb_official.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    ActivityLoginBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
    }

    public void login(View view){
        if (validasi()){

        } else {
            LoginModel loginModel = new LoginModel(bind.username.getText().toString(), bind.password.getText().toString());
            prosesLogin(loginModel);
//            startActivity(new Intent(Login.this, MainActivity.class));
//            finish();
        }
    }

    public void prosesLogin(LoginModel loginModel){
        SPHelper sp = new SPHelper(Login.this);
        LoadingDialog.load(Login.this);
        Call<LoginResponse> loginResponseCall = API.getRetrofit(Login.this).login(loginModel);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoadingDialog.close();
                if (response.isSuccessful()){
                    //simpan token dan username
                    sp.setToken(response.body().getToken());
                    sp.setEmail(response.body().getData().getEmail());
                    sp.setUsername(response.body().getData().getName());
                    sp.setIdPengguna(response.body().getData().getId());
                    SuccessDialog.message(Login.this, "Login berhasil", bind.getRoot());

                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();

                } else {
                    ErrorDialog.message(Login.this, "Akun tidak ditemukan, periksa kembali password anda", bind.getRoot());
                }
//                Toast.makeText(Login.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                LoadingDialog.close();
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void register(View view){
        startActivity(new Intent(Login.this, Register.class));
        finish();
    }

    public boolean validasi(){
        EditText Username = bind.username;
        EditText Password = bind.password;
        if(Username.getText().toString().isEmpty() || Password.getText().toString().isEmpty()) {
            Username.requestFocus();
            Username.setError("Harap diisi");
            Password.requestFocus();
            Password.setError("Harap diisi");
            return true;
        } else {
            SuccessDialog.message(Login.this, "Login berhasil", bind.getRoot());
        }
        return false;
    }
}
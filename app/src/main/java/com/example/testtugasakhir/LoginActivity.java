package com.example.testtugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testtugasakhir.Pojo.Pengangkutan;
import com.example.testtugasakhir.Response.LoginResponse;
import com.example.testtugasakhir.Utils.API;
import com.example.testtugasakhir.Utils.BaseUrl;
import com.example.testtugasakhir.Utils.Session;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    TextView inputUsername;
    TextView inputPassword;
    Button loginButton;
    BaseUrl url;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        inputPassword = findViewById(R.id.inputPassword);
        inputUsername = findViewById(R.id.inputUsernameNew);
        url = new BaseUrl();
        session = new Session(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputUsername.getText().toString();
                String passsword = inputPassword.getText().toString();
                Log.i("INPUIT", email + " 0 " + passsword);
                doLogin(email, passsword);
            }
        });

    }


    private void doLogin(String email,String passsword){
        Log.i("INPUT", email + " 0 " + passsword);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url.getUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api =retrofit.create(API.class);
        Call<LoginResponse> call = api.login(email,passsword);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.i("SUCC", response.body().getMessage());
                if(response.body().getSuccess()){
                    session.setUser(response.body().getData());
                    Toast.makeText(getApplicationContext(),"Login Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), RuteActivity.class);
                    intent.putExtra("username",response.body().getData().getName());
                    finishAffinity();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Login Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("FAILED", t.getMessage());
            }
        });
    }

//    private void doLogin(){
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String password = inputPassword.getText().toString();
//                String username = inputUsername.getText().toString();
//                Toast.makeText(getApplicationContext(),password + " - " + username , Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(getApplicationContext(),RuteActivity.class);
//                intent.putExtra("username",username);
//                startActivity(intent);
//
//            }
//        });
//    }
}

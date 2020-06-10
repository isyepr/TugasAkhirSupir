package com.example.testtugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.testtugasakhir.Pojo.Pengangkutan;
import com.example.testtugasakhir.Utils.Session;

public class SplashScreenActivity extends AppCompatActivity {


    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        session = new Session(getApplicationContext());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                checkLogin();
            }
        }, 5000);



    }


    private void checkLogin(){
        if(session.getUser() != null){
            Intent intent = new Intent(getApplicationContext(), RuteActivity.class);
            finishAffinity();
            startActivity(intent);

        }else{
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            finishAffinity();
            startActivity(intent);
        }
    }
}

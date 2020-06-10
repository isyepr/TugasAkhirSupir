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
import com.example.testtugasakhir.Response.PengangkutanResponse;
import com.example.testtugasakhir.Utils.API;
import com.example.testtugasakhir.Utils.BaseUrl;
import com.example.testtugasakhir.Utils.Session;
import com.ncorti.slidetoact.SlideToActView;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PengangkutanActivity extends AppCompatActivity {
    Button kosongButton, terisiButton, tidakAngkutButton, angkutButton;
    BaseUrl url;
    String tps_nama;
    String tps_id;
    String idSupir;
    TextView namaTps;
    String title;
    Session session;
    String idTps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengangkutan);
        url = new BaseUrl();





        session = new Session(getApplicationContext());

        final Intent fromTpsList = getIntent();
        namaTps = findViewById(R.id.textViewNamaTps);
        namaTps.setText(fromTpsList.getStringExtra("tps_nama"));
        tps_id = fromTpsList.getStringExtra("tps_id");
        idSupir = Integer.toString(session.getUser().getId());
//        idSupir = getIntent().getStringExtra("idSupir");


        terisiButton= findViewById(R.id.terisiButton);
        terisiButton.setVisibility(View.VISIBLE);
        terisiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer is_penuh = 1;
                pengangkutan(idSupir, tps_id,"is_penuh",is_penuh);
                terisiButton.setVisibility(View.GONE);
                kosongButton.setVisibility(View.GONE);

            }
        });

        kosongButton = findViewById(R.id.kosongButton);
        kosongButton.setVisibility(View.VISIBLE);
        kosongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer is_penuh = 0;
                pengangkutan(idSupir, tps_id,"is_penuh",is_penuh);
                finish();
            }
        });

        angkutButton = findViewById(R.id.angkutButton);
        angkutButton.setVisibility(View.VISIBLE);
        angkutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer is_ambil = 1;
                pengangkutan(idSupir, tps_id,"is_ambil",is_ambil);
                finish();
            }

        });
        tidakAngkutButton = findViewById(R.id.tidakAngkutButton);
        tidakAngkutButton.setVisibility(View.VISIBLE);
        tidakAngkutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer is_ambil = 0;
                pengangkutan(idSupir, tps_id,"is_ambil",is_ambil);
                finish();
            }
        });

    }


    private void pengangkutan( String idSupir,String tps_id, String attribute, Integer status) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url.getUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api =retrofit.create(API.class);
        Call<PengangkutanResponse> call = api.mulai(Integer.parseInt(idSupir),Integer.parseInt(tps_id),attribute,status);
        call.enqueue(new Callback<PengangkutanResponse>() {
            @Override
            public void onResponse(Call<PengangkutanResponse> call, Response<PengangkutanResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("SERRR", response.body().getMessage());
                } else {
                    Toast.makeText(PengangkutanActivity.this, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
//                Log.i("SERRR", response.message());
//                Log.i("SERRR", String.valueOf(response.body()));
//                Log.i("SERRR", String.valueOf(response.code()));
            }
            @Override
            public void onFailure(Call<PengangkutanResponse> call, Throwable t) {
                Log.i("SERRR", t.getMessage());
            }
        });


    }
}

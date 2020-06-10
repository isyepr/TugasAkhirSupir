package com.example.testtugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testtugasakhir.Response.EditResponse;
import com.example.testtugasakhir.Utils.API;
import com.example.testtugasakhir.Utils.BaseUrl;
import com.example.testtugasakhir.Utils.RetrofitClient;
import com.example.testtugasakhir.Utils.Session;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfilActivity extends AppCompatActivity {
    Button simpanButton;
    TextView textHp;
    TextView textUsername;
    TextView textAlamat;
    ImageView foto;
    BaseUrl url;
    Session session;
//    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        simpanButton = findViewById(R.id.simpanButton);
        session = new Session(getApplicationContext());
        String nohp = session.getUser().getUser_hp();
        String alamat = session.getUser().getUser_alamat();
        String nama = session.getUser().getName();
        final Integer inputId = session.getUser().getId();
        String fotourl = session.getUser().getUser_photo_url();


        textHp = findViewById(R.id.textHp);
        textAlamat = findViewById(R.id.textAlamat);
        textUsername =  findViewById(R.id.textUsername);
        foto = findViewById(R.id.fotoProfil);
        url = new BaseUrl();
        textHp.setText(nohp);
        textUsername.setText(nama);
        textAlamat.setText(alamat);
        Picasso.get().load(fotourl).into(foto);

        simpanButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
          String nohp2 = textHp.getText().toString();
          String alamat2 = textAlamat.getText().toString();
            isyemandi(nohp2, alamat2);
//
            }
        });

    }

    private void isyemandi(String nohp2, String alamat2){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url.getUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api =retrofit.create(API.class);
        Call<EditResponse> update = api.editData(nohp2, alamat2, 11);
        update.enqueue(new Callback<EditResponse>() {
            @Override
            public void onResponse(Call<EditResponse> call, Response<EditResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"yes", Toast.LENGTH_SHORT).show();
                    EditProfilActivity.this.finish();

                }else{
                    Toast.makeText(getApplicationContext(),"nope", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<EditResponse> call, Throwable t) {
                Toast.makeText(EditProfilActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
                Log.i("GAGAL", t.getMessage().toString());

            }
        });
    }


}

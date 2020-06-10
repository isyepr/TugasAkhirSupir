package com.example.testtugasakhir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testtugasakhir.Utils.BaseUrl;
import com.example.testtugasakhir.Utils.Session;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    Button ubahButton;
    FloatingActionButton logoutFab;
    TextView textHp;
    TextView textUsername;
    TextView textAlamat;
    ImageView foto;
    BaseUrl url;
    Session session;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ubahButton = findViewById(R.id.ubahButton);
        session = new Session(getApplicationContext());
        String nohp = session.getUser().getUser_hp();
        String nama = session.getUser().getName();
        String alamat = session.getUser().getUser_alamat();
        String fotourl = session.getUser().getUser_photo_url();

        textHp = findViewById(R.id.textHp);
        textUsername = findViewById(R.id.textUsername);
        textAlamat = findViewById(R.id.textAlamat);
        foto = findViewById(R.id.fotoProfil);
        textHp.setText(nohp);
        textUsername.setText(nama);
        textAlamat.setText(alamat);
        Picasso.get().load(fotourl).into(foto);
        logoutFab = (FloatingActionButton) findViewById(R.id.logoutFab);
        logoutFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Logout")
                        .setMessage("Apakah anda yakin ingin logout?")
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                                session.logout();
//                                finish();
                                Intent intent  = new Intent(MainActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                session.logout();
                                finish();
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // dismiss dialog here because user doesn't want to logout
                            }
                        })
                        .show();


            }
        });
        


        openEdit();
    }

    private void openEdit() {
        ubahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditProfilActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            session.logout();
            this.finish();
        }
        return true;
    }




}

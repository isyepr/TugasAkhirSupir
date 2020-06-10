package com.example.testtugasakhir;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.testtugasakhir.Pojo.Tps;
import com.example.testtugasakhir.Response.PengangkutanResponse;
import com.example.testtugasakhir.Response.TpsResponse;
import com.example.testtugasakhir.Utils.API;
import com.example.testtugasakhir.Utils.BaseUrl;
import com.example.testtugasakhir.Utils.Session;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ncorti.slidetoact.SlideToActView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RuteActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
     static String tps_nama;
    FloatingActionButton ubahFab;
    SlideToActView slide;
    Session session;
    String is_kesana;
    String tps_id;
//    String idSupir = session.Integer.toString(getUser().getId());
    String idSupir;
    BaseUrl url;
    GoogleMap mMap;
    ProgressDialog loading;
    private static final int PERMISSIONS_REQUEST = 100;

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private static final int TAG_CODE_PERMISSION_LOCATION = 1;
    private Marker marker;
    private float zoom = 15f;
    private LocationManager locationManager;
    private Location currentLocation;
    private Marker currentMarker;
    API api;
    private ArrayList<Tps> tpsList = new ArrayList<>();
    Map<String, String> mMarkerMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rute);
        url = new BaseUrl();
        checkLocationPermission();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

//If the location permission has been granted, then start the TrackerService//

        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {

//If the app doesn’t currently have access to the user’s location, then request access//

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        session = new Session(getApplicationContext());

        ubahFab = (FloatingActionButton) findViewById(R.id.ubahFab);
        ubahFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfil();
            }
        });
        idSupir = Integer.toString(session.getUser().getId());
        final SlideToActView slide = (SlideToActView) findViewById(R.id.slide1);
        final SlideToActView slide2 = (SlideToActView) findViewById(R.id.slide2);

        slide.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                int i = 0;
                slide.setVisibility(View.GONE);
                slide2.setVisibility(View.VISIBLE);
//                final Intent fromTpsList = getIntent();
                tps_id = tpsList.get(i).getId();
                Integer is_kesana = 1;
                pengangkutan(idSupir, tps_id,"is_kesana", is_kesana);
            }


        });

        slide2.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                int i = 0;
                slide2.setVisibility(View.VISIBLE);
                tps_id = tpsList.get(i).getId();
                Integer is_selesai = 1;
                pengangkutan(idSupir, tps_id,"is_selesai", is_selesai);
            }
        });

        toPengangkutan();


    }

    private void pengangkutan(String idSupir, String tps_id, String attribute, Integer status) {
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
                    Toast.makeText(RuteActivity.this, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<PengangkutanResponse> call, Throwable t) {
                Log.i("SERRR", t.getMessage());
            }
        });

    }


    private void toPengangkutan() {
        Intent intent = new Intent(getApplicationContext(), PengangkutanActivity.class);
        intent.putExtra("idSupir", idSupir);
        intent.putExtra("is_kesana", is_kesana);
    }




    private void openProfil() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION },

                        TAG_CODE_PERMISSION_LOCATION);
            }


        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = map;
        mMap = googleMap;

        getAllDataLocationLatLng();



        currentMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                .title("Posisi saya")
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_person_pin_circle_black_24dp))));
        double lat = currentLocation.getLatitude();
        double lng = currentLocation.getLongitude();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), zoom));




    }


    private void getAllDataLocationLatLng() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Menampilkan data marker ..");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url.getUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
//        API api= RetrofitClient.getClient().create(API.class);
        API api = retrofit.create(API.class);
        Call<TpsResponse> call = api.getListTps();
        call.enqueue(new Callback<TpsResponse>() {
            @Override
            public void onResponse(Call<TpsResponse> call, Response<TpsResponse> response) {
                dialog.dismiss();
                tpsList = response.body().getValue();
                initMarker(tpsList);
            }

            @Override
            public void onFailure(Call<TpsResponse> call, Throwable t) {
//                dialog.dismiss();
                Toast.makeText(RuteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void initMarker(ArrayList<Tps> tpsList) {
        for (int i=0; i<tpsList.size(); i++){
            //set latlng nya
            LatLng location = new LatLng((tpsList.get(i).getTps_lattitude()),
                    (tpsList.get(i).getTps_longitude()));

            if (tpsList.get(i).getTps_jenis().equals("TPS Resmi")){
                marker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(tpsList.get(i).getTps_nama())
                        .snippet(tpsList.get(i).getTps_jenis())
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.marker_tps))));
            }

            else if (tpsList.get(i).getTps_jenis().equals("TPS Liar")) {
                marker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(tpsList.get(i).getTps_nama())
                        .snippet(tpsList.get(i).getTps_jenis())
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.marker_tps3))));
            }
            else{
                marker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(tpsList.get(i).getTps_nama())
                        .snippet(tpsList.get(i).getTps_jenis())
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.marker_tps2))));
            }
            //set latlng index ke 0
            LatLng latLng = new LatLng((tpsList.get(0).getTps_lattitude()),
                    (tpsList.get(0).getTps_longitude()));
            //lalu arahkan zooming ke marker index ke 0
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude,latLng.longitude), 17.0f));
            mMarkerMap.put(marker.getId(), tpsList.get(i).getId());

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String tps_id = mMarkerMap.get(marker.getId());
                    String tps_nama = marker.getTitle();
                    Intent intent = new Intent(RuteActivity.this, PengangkutanActivity.class);
                    intent.putExtra("tps_nama", tps_nama);
                    intent.putExtra("tps_id", tps_id);
                    startActivity(intent);

                    return false;
                }
            });
        }

    }
    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }



    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();



    @Override
    public void onLocationChanged(Location location) {
        double lat1 = location.getLongitude();
        double lng1= location.getLatitude();
//            try {
//                getAlamat(lat1,lng1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {

//If the permission has been granted...//

        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//...then start the GPS tracking service//

            startTrackerService();
        } else {

//If the user denies the permission request, then display a toast with some more information//

            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
        }
    }

//Start the TrackerService//

    private void startTrackerService() {
        startService(new Intent(this, TrackingService.class));

//Notify the user that tracking has been enabled//

        Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show();

//Close MainActivity//

    }
}




package com.example.testtugasakhir.Utils;

import com.example.testtugasakhir.Response.EditResponse;
import com.example.testtugasakhir.Response.LoginResponse;
import com.example.testtugasakhir.Response.PengangkutanResponse;
import com.example.testtugasakhir.Response.TpsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("profil")
    Call<LoginResponse> profil(
            @Field("name") String name,
            @Field("user_hp") String user_hp,
            @Field("user_alamat") String user_alamat
    );
    @FormUrlEncoded
    @POST("edit")
    Call<EditResponse> editData(
            @Field("hp") String hp,
            @Field("alamat") String alamat,
            @Field("inputId") Integer inputId
    );

    @FormUrlEncoded
    @POST("pengangkutan/store")
    Call<PengangkutanResponse> mulai(
            @Field("user_id") Integer user_id,
            @Field("tps_id") Integer tps_id,
            @Field("attribute") String attribute,
            @Field("status") Integer status

    );




    @GET("detail")
    Call <TpsResponse> getListTps();
}

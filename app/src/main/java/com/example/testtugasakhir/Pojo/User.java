package com.example.testtugasakhir.Pojo;

public class User {

    private Integer id;
    private Integer mobil_id;
    private String name;
    private String email;
    private String rememberToken;
    private String user_fcm;
    private Integer user_role;
    private String user_hp;

    public String getUser_photo_url() {
        return user_photo_url;
    }

    private String user_photo_url;
    private String user_alamat;

    public Integer getId() {
        return id;
    }

    public Integer getMobil_id() {
        return mobil_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public String getUser_fcm() {
        return user_fcm;
    }

    public Integer getUser_role() {
        return user_role;
    }

    public String getUser_hp() {
        return user_hp;
    }

    public String getUser_alamat() {
        return user_alamat;
    }

}

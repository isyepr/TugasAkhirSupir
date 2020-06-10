package com.example.testtugasakhir.Pojo;

import java.sql.Timestamp;

public class Pengangkutan {
    private Integer user_id;
    private Integer is_kesana;
    private Integer is_penuh;
    private Integer is_ambil;
    private Integer is_selesai;
    private Integer id_rute;
    private String waktu_angkut;
    private Integer id_detil_rute;


    public Integer getUser_id() {
        return user_id;
    }

    public Integer getIs_kesana() {
        return is_kesana;
    }

    public Integer getIs_penuh() {
        return is_penuh;
    }

    public Integer getIs_ambil() {
        return is_ambil;
    }

    public Integer getIs_selesai() {
        return is_selesai;
    }

    public Integer getId_rute() {
        return id_rute;
    }

    public String getWaktu_angkut() {
        return waktu_angkut;
    }

    public Integer getId_detil_rute() {
        return id_detil_rute;
    }




}

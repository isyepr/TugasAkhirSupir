package com.example.testtugasakhir.Response;

import com.example.testtugasakhir.Pojo.Pengangkutan;

public class PengangkutanResponse {
    private String message;
    private Boolean success;
    private Pengangkutan data;

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Pengangkutan getData() {
        return data;
    }
}

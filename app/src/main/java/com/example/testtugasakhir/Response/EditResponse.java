package com.example.testtugasakhir.Response;

import com.example.testtugasakhir.Pojo.User;

public class EditResponse {
    private String message;
    private Integer code;
    private Boolean success;
    private String token;
    private String data;

    public String getMessage() { return message; }

    public Integer getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    public String getData() {
        return data;
    }

    public Boolean getSuccess() {
        return success;
    }
}

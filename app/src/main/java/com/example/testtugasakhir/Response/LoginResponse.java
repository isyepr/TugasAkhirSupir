package com.example.testtugasakhir.Response;

import com.example.testtugasakhir.Pojo.User;

public class LoginResponse {

    private String message;
    private Boolean success;
    private String token;
    private User data;

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public User getData() {
        return data;
    }

}

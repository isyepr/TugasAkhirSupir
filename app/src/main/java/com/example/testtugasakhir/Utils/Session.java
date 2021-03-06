package com.example.testtugasakhir.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.testtugasakhir.Pojo.User;
import com.google.gson.Gson;

public class Session {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private User user;
    Context _context;


    private String Session;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "session";

    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setUser(User user) {
        this.user = user;
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.commit();
    }

    public User getUser() {
        Gson gson = new Gson();
        String json = pref.getString("user",null);
        user = gson.fromJson(json, User.class);
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}

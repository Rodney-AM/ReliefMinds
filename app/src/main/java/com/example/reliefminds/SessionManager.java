package com.example.reliefminds;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String ID = "ID";
    public static final String NAMES = "NAMES";
    public static final String CONTACT = "CONTACT";
    public static final String EMAIL= "EMAIL";
    public static final String USERNAME = "USERNAME";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id,String names,String email,String contact,String username){
        editor.putBoolean(LOGIN, true);
        editor.putString(ID,id);
        editor.putString(NAMES,names);
        editor.putString(CONTACT,contact);
        editor.putString(EMAIL,email);
        editor.putString(USERNAME,username);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){
        if (!this.isLogin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();

        }
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(NAMES, sharedPreferences.getString(NAMES, null));
        user.put(CONTACT, sharedPreferences.getString(CONTACT, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        return user;

    }

    public void logout(){

      editor.clear();
      editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();

    }

}

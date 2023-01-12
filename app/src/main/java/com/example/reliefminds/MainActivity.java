package com.example.reliefminds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView names;
    String id, contact;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        names = findViewById(R.id.names);

        HashMap<String, String> user = sessionManager.getUserDetail();
        id = user.get(SessionManager.ID);
        names.setText("Welcome!"+user.get(SessionManager.NAMES));

    }

    public void Logout(View view){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);
        alertDialog2.setMessage("Are you sure you want to LogOut? Note that you will have to login again the next time you visit the App");
        alertDialog2.setIcon(R.drawable.ic_baseline_warning_24);
        alertDialog2.setPositiveButton("Yes", (dialog, which) ->{
            sessionManager.logout();
        });

        alertDialog2.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        alertDialog2.show();
    }
}
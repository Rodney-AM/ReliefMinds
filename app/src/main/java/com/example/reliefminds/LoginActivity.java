package com.example.reliefminds;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button loginbtn;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un = username.getText().toString();
                String pass = password.getText().toString();

                if (un.isEmpty() || pass.isEmpty()){
//                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_LONG).show();
                username.setError("Username is required");
                password.setError("Password is required");
                }
                else {
                    Login(un,pass);
                }
            }
        });
    }

    private void Login(String un, String pass) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in please wait....");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.LOG_URL,
                response -> {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("login");
                        if (success.equals("1")){
//                            Toast.makeText(getApplicationContext(), "Logged In successfully",
//                                    Toast.LENGTH_LONG).show();
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String username = object.getString("uname");
                                String contact = object.getString("contact");
                                String email = object.getString("email");
                                String names = object.getString("names");
                                sessionManager.createSession(id,names,email,contact,username);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Something went wrong try again",
                                    Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error"+e.getMessage(),
                                Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }, error -> {
            Toast.makeText(getApplicationContext(), "Error"+error.getMessage(),
                    Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }){
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username",un);
                params.put("password",pass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void goToregister(View view) {
        Intent r = new Intent(this,RegisterActivity.class);
        startActivity(r);
    }
    public void goTomain(View view) {
        Intent m = new Intent(this,MainActivity.class);
        startActivity(m);

    }
}
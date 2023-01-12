package com.example.reliefminds;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
EditText fname,lname,email,contact,dob,username,password;
Spinner gender;
Button registerbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        gender = findViewById(R.id.gender);
        registerbtn = findViewById(R.id.registerbtn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn = fname.getText().toString();
                String ln = lname.getText().toString();
                String em = email.getText().toString();
                String phone = contact.getText().toString();
                String date = dob.getText().toString();
                String un = username.getText().toString();
                String pass = password.getText().toString();
                String sex = gender.getSelectedItem().toString();

                if (fn.isEmpty() || ln.isEmpty() || em.isEmpty() || phone.isEmpty() || date.isEmpty()
                || un.isEmpty() || pass.isEmpty() || sex.equals("Select gender")){

                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_LONG).show();

                }
                else {
                    CreateAccount(fn,ln,em,phone,date,un,pass,sex);
                }
            }
        });
    }

    private void CreateAccount(String fn, String ln, String em, String phone,
                               String date, String un, String pass, String sex) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Creating account please wait....");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.REG_URL,
                response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                if (success.equals("1")){
                    Toast.makeText(getApplicationContext(), "Account created successfully",
                            Toast.LENGTH_LONG).show();
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
        }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fname",fn);
                params.put("lname",ln);
                params.put("email",em);
                params.put("contact",phone);
                params.put("dob",date);
                params.put("gender",sex);
                params.put("username",un);
                params.put("password",pass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void goTologin(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
package com.example.praca_inz_02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class login extends AppCompatActivity {

    private static final String connection="http://192.168.1.164/login.php";
    private Button login_button;
    private String result;
    private Context context;
    private AlertDialog dialog;
    private EditText em, has;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        em=(EditText)findViewById(R.id.email);
        has=(EditText)findViewById(R.id.haslo);


        login_button=(Button)findViewById(R.id.login);
        AlertDialog dialog= new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");

    }
    public void login(View view) {
        String user = em.getText().toString();
        String pass = has.getText().toString();

        background bg = new background(this);
        bg.execute(user,pass);
    }




}

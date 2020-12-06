package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    EditText pas,usr;
    String IP="192.168.1.164";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usr = (EditText) findViewById( R.id.email);
        pas = (EditText) findViewById(R.id.haslo);
    }

    public void login(View view) {
        String user = usr.getText().toString();
        String pass = pas.getText().toString();

        bgLogowanie bg = new bgLogowanie(this);

        String res=bg.execute(user,pass).toString();
        Log.d("MW","ccc "+res);

    }

    public void rejestracja(View view) {
        Intent intencjaRejestracja = new Intent(this,rejestracja.class);
        startActivity(intencjaRejestracja);
    }
    //public String getIP{
   //     return IP;
   // }
}

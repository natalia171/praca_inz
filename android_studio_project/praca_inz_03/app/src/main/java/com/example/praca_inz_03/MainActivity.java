package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {
    EditText pas,usr;
    String IP="192.168.1.164";
    Spinner listaSpecjalizacji;
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

        String idWizyty = "1";
        String idPacjenta = "1";

        //????? tworzenie klasy bglogowanie  i tworzenie stringa z nazwa usera i haslem
        //bgLogowanie bg = new bgLogowanie(this);
        //String res=bg.execute(user,pass).toString();
        //Log.d("MainACC","Odpowiedz logowanie "+res);
        bgPotwierdzWizyte bgPW = new bgPotwierdzWizyte(null);
        String res1=bgPW.execute("1", "1").toString();


    }

    public void rejestracja(View view) {
        //tworzenie nowej aktywnosci  i jej uruchomienie
        Intent intencjaRejestracja = new Intent(this,rejestracja.class);
        startActivity(intencjaRejestracja);

    }
    //public String getIP{
   //     return IP;
   // }
}

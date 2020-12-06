package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DodajWizyte extends AppCompatActivity {
Spinner listaSpecjalizacji;
    String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      listaSpecjalizacji = (Spinner) findViewById( R.id.listaSpecjalizacji);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wizyte);

//        bgPobierzSpecjalizacje bg = new bgPobierzSpecjalizacje(this);
//        String res=bg.execute().toString();
//        Log.d("MW",res);

      String connstr = "http://192.168.1.164/rejestracja.php";
      try {
          URL url = new URL(connstr);
          HttpURLConnection http = (HttpURLConnection) url.openConnection();
          http.setRequestMethod("POST");
          http.setDoInput(true);
          http.setDoOutput(true);
          OutputStream ops = http.getOutputStream();
          ops.close();
          InputStream ips = http.getInputStream();

         BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
          String line = "";
          while ((line = reader.readLine()) != null) {
              result += line;
             Log.d("MW", line);
         }
         reader.close();
         ips.close();
         http.disconnect();
      }
        catch (Exception ex){
//        Log.d("MW", ex.getMessage());
    }
    }

}

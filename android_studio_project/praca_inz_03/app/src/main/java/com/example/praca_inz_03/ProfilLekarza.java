package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

public class ProfilLekarza extends AppCompatActivity {

    TextView daneLekarza;
    EditText wpisanaOpinia;
    Button dodajOpinie;
    String idLekarza;
    String IP;
    String dL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_lekarza);

        daneLekarza = findViewById(R.id.daneLekarza);
        wpisanaOpinia = findViewById(R.id.wpisanaOpinia);
        dodajOpinie = findViewById(R.id.dodajOpinie);

        idLekarza = getIntent().getStringExtra("idLekarza");
        IP = getIntent().getStringExtra("IP");

        bgPobierzDaneLekarza bgPDL = new bgPobierzDaneLekarza(this);
        try {
            dL = (String) bgPDL.execute(idLekarza,IP).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        daneLekarza.setText(dL);

    }

    public void dodajOpinie(View view) {
    }
}
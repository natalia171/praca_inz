package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PanelPacjenta extends AppCompatActivity {
String ajdi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_pacjenta);
        ajdi = getIntent().getStringExtra("ajdi");


    }

    public void dodajWizte(View view) {
        String idPacjenta = ajdi;
        Intent intencjaDodajWizyte = new Intent(this,DodajWizyte.class);
       startActivity(intencjaDodajWizyte);
        intencjaDodajWizyte.putExtra("ajdi",idPacjenta);
        Log.d("dupa", idPacjenta);


    }

}

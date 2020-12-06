package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PanelPacjenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_pacjenta);
    }

    public void dodajWizte(View view) {
        Intent intencjaDodajWizyte = new Intent(this,DodajWizyte.class);
        startActivity(intencjaDodajWizyte);

    }
}

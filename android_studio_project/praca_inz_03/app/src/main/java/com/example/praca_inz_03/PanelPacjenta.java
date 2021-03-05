package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PanelPacjenta extends AppCompatActivity {
String ajdi;
String IP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_pacjenta);
        ajdi = getIntent().getStringExtra("idPacjenta");
        IP = getIntent().getStringExtra("IP");
        Log.d("id", "onCreate: ID w panelu pacjenta:  "+ajdi);


//        Intent intent_name = new Intent();
//        intent_name.setClass(context.getApplicationContext(),PanelPacjenta.class);////////
//        intent_name.putExtra("ajdi",ajdi);
    }

    public void dodajWizte(View view) {
        String idPacjenta = ajdi;
        //idPacjenta=getIntent().getStringExtra("s");
        Intent intencjaDodajWizyte = new Intent(this,DodajWizyte.class);
        intencjaDodajWizyte.putExtra("idPacjenta",idPacjenta);
        intencjaDodajWizyte.putExtra("IP",IP);
        startActivity(intencjaDodajWizyte);
        Log.d("id", "dodajWizyte funkcja: ID w panelu pacjenta:  "+idPacjenta);



    }

}

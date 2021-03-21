package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class OpiniePanelLekarza extends AppCompatActivity {

    String idLekarza;
    String IP;
    ListView listaOpinii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinie_panel_lekarza);

        listaOpinii = findViewById(R.id.listaOpinii);
        idLekarza = getIntent().getStringExtra("idLekarza");

        IP = getIntent().getStringExtra("IP");


        bgWyswietlOpiniePanelLekarza bgWOPA = new bgWyswietlOpiniePanelLekarza(this);

        List<String> opinie= new ArrayList<>();
        try {
            opinie = bgWOPA.execute(idLekarza,IP).get();
        }catch (Exception e){}
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, opinie);
        listaOpinii.setAdapter(adp1);

    }
}
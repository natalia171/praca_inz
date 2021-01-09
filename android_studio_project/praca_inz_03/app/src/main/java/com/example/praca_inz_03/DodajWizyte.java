package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class DodajWizyte extends AppCompatActivity {
    String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wizyte);
        Spinner listaSpecjalizacjiSpinner;
        listaSpecjalizacjiSpinner = (Spinner) findViewById( R.id.listaSpecjalizacji);
        bgPobierzSpecjalizacje bg = new bgPobierzSpecjalizacje(this);
        List<String> listaSpecjalizacji= new ArrayList<>();
        try {
            listaSpecjalizacji = bg.execute().get();
            Log.d("DoWi","ressponse "+listaSpecjalizacji.get(0));
        }catch (Exception e){}
       ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, listaSpecjalizacji);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.d("DoWi","ressponse x  "+adp1.getItem(1));
       listaSpecjalizacjiSpinner.setAdapter(adp1);

        Log.d("DoWi","ressponse "+listaSpecjalizacji);


    }

}

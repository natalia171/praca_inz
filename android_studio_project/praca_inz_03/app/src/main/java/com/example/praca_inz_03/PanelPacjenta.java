package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PanelPacjenta extends AppCompatActivity {
    String ajdi;
    String IP;
    ListView historiaWizyt;
    ListView nadchodzaceWizyty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_pacjenta);

        historiaWizyt = findViewById(R.id.historiaWizyt);
        nadchodzaceWizyty = findViewById(R.id.nadchodząceWizyty);

        ajdi = getIntent().getStringExtra("idPacjenta");
        IP = getIntent().getStringExtra("IP");

        //pobieranie historii wizyt
        bgPobierzHistorieWizyt bgPHW = new bgPobierzHistorieWizyt(this); //tworzy nowa klase

        List<String> historia_wizyt= new ArrayList<>(); //tworzy liste
        try {
            historia_wizyt = bgPHW.execute(ajdi,IP).get(); //przypisuje do listy wynik z klasy
            Log.d("historia","historia "+historia_wizyt.get(0));
        }catch (Exception e){}
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, historia_wizyt);//tworzy arrayadapter ktory bedzie posiadal wartosci listaspecjalizacji
        //adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        historiaWizyt.setAdapter(adp1);

        //pobieranie nadchodzacych wizyt
        bgPobierzNadchodzaceWizyty bgPNW = new bgPobierzNadchodzaceWizyty(this); //tworzy nowa klase

        List<String> nadchodzace_wizyty= new ArrayList<>(); //tworzy liste
        try {
            nadchodzace_wizyty = bgPNW.execute(ajdi,IP).get(); //przypisuje do listy wynik z klasy
        }catch (Exception e){}
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, nadchodzace_wizyty);//tworzy arrayadapter ktory bedzie posiadal wartosci listaspecjalizacji
        //adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        nadchodzaceWizyty.setAdapter(adp2);

    }



    public void dodajWizte(View view) {
        String idPacjenta = ajdi;
        //idPacjenta=getIntent().getStringExtra("s");
        Intent intencjaDodajWizyte = new Intent(this, DodajWizyte.class);
        intencjaDodajWizyte.putExtra("idPacjenta", idPacjenta);
        intencjaDodajWizyte.putExtra("IP", IP);
        startActivity(intencjaDodajWizyte);
        Log.d("id", "dodajWizyte funkcja: ID w panelu pacjenta:  " + idPacjenta);


    }


}

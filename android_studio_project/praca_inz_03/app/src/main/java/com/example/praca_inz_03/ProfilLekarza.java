package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfilLekarza extends AppCompatActivity {

    TextView daneLekarza;
    ListView listaOpinii;
    EditText wpisanaOpinia;
    Button dodajOpinie;
    String idLekarza;
    String idPacjenta;
    String IP;
    String dL;
    String trescOpinii;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_lekarza);

        listaOpinii = findViewById(R.id.listaOpinii);
        daneLekarza = findViewById(R.id.daneLekarza);
        wpisanaOpinia = findViewById(R.id.wpisanaOpinia);
        dodajOpinie = findViewById(R.id.dodajOpinie);

        idPacjenta = getIntent().getStringExtra("idPacjenta");
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


        //wyswietlenie opinii
        bgWyswietlOpinie bgWO = new bgWyswietlOpinie(this);

        List<String> opinie= new ArrayList<>();
        try {
            opinie = bgWO.execute(idLekarza,IP).get();
        }catch (Exception e){}
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, opinie);
        listaOpinii.setAdapter(adp1);
    }

    public void dodajOpinie(View view) {
        trescOpinii=wpisanaOpinia.getText().toString();

        bgDodajOpinie bgDO = new bgDodajOpinie(this);
        bgDO.execute(idLekarza,idPacjenta,IP, trescOpinii);
        wpisanaOpinia.setText("");
        finish();
        startActivity(getIntent());
    }


}
package com.example.praca_inz_03;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;




public class aktywneKontaAdmin extends AppCompatActivity {
    ListView daneLekarza;
    String IP;
    String idLekarza;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktywne_konta_admin);

        IP = getIntent().getStringExtra("IP");
        idLekarza = getIntent().getStringExtra("idLekarza");


        daneLekarza = findViewById(R.id.daneLekarz);



        //pobieranie danych lekarza do zatwierdzenia

        bgPobierzDaneLekarzaDoZatwierdzenia bgPDLDZ = new bgPobierzDaneLekarzaDoZatwierdzenia(this);

        LinkedHashMap<String, String> LinkedhashListaLekarzy1=new LinkedHashMap<String,String>();
        try {
            LinkedhashListaLekarzy1 = (LinkedHashMap<String,String>) bgPDLDZ.execute(IP,idLekarza).get();

        }catch (Exception e){}



        Set<String> IdWizyt = LinkedhashListaLekarzy1.keySet();
        final ArrayList<String> ListaKluczyLekarzy = new ArrayList<String>(IdWizyt);

        Collection<String> ListaDanychLekarzy1 = LinkedhashListaLekarzy1.values();
        final ArrayList<String> arrayListLekarzy1 = new ArrayList<String>(ListaDanychLekarzy1);
        final Intent aktywneKonta1 = new Intent(this, aktywneKontaAdmin.class);


        final ArrayAdapter adp = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayListLekarzy1);

        daneLekarza.setAdapter(adp);

    }

    public void zatwierdz(View view) {
        bgDezaktywujProfil bgDP = new bgDezaktywujProfil(aktywneKontaAdmin.this);
        String ress=bgDP.execute(idLekarza,IP).toString();
    }
}

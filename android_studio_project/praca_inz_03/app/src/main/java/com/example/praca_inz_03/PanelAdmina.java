package com.example.praca_inz_03;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;




public class PanelAdmina extends AppCompatActivity {
    ListView lekarzeDoZatwierdzenia;
    ListView aktywneKonta;
    String IP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_admina);

        IP = getIntent().getStringExtra("IP");

        lekarzeDoZatwierdzenia = findViewById(R.id.lekarzeDoZatwierdzenia);
        aktywneKonta = findViewById(R.id.aktywneKonta);


        //pobieranie listy lekarzy do zatwierdzenia

        bgPobierzLekarzyDoZatwierdzenia bgPLDZ = new bgPobierzLekarzyDoZatwierdzenia(this);

        LinkedHashMap<String, String> LinkedhashListaLekarzy=new LinkedHashMap<String,String>();
        try {
            LinkedhashListaLekarzy = (LinkedHashMap<String,String>) bgPLDZ.execute(IP).get();

        }catch (Exception e){}



        Set<String> IdWizyt = LinkedhashListaLekarzy.keySet();
        final ArrayList<String> ListaKluczyLekarzy = new ArrayList<String>(IdWizyt);

        Collection<String> ListaDanychLekarzy = LinkedhashListaLekarzy.values();
        final ArrayList<String> arrayListLekarzy = new ArrayList<String>(ListaDanychLekarzy);
        final Intent profilAdmina = new Intent(this, profilAdmina.class);


        final ArrayAdapter adp2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayListLekarzy);

        lekarzeDoZatwierdzenia.setAdapter(adp2);
        lekarzeDoZatwierdzenia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object listItem = lekarzeDoZatwierdzenia.getItemAtPosition(position);
                final String idLekarza = ListaKluczyLekarzy.get(position);

               profilAdmina.putExtra("IP", IP);
               profilAdmina.putExtra("idLekarza", idLekarza);

               startActivity(profilAdmina);
            }
        });

        //pobieranie listy aktywnych kont lekarzy

        bgPobierzListeAktywnychKont bgPLAK = new bgPobierzListeAktywnychKont(this);

        LinkedHashMap<String, String> LinkedhashListaAktywnychKont=new LinkedHashMap<String,String>();
        try {
            LinkedhashListaAktywnychKont = (LinkedHashMap<String,String>) bgPLAK.execute(IP).get();

        }catch (Exception e){}



        Set<String> IdLek = LinkedhashListaAktywnychKont.keySet();
        final ArrayList<String> ListaKluczy = new ArrayList<String>(IdLek);

        Collection<String> ListaDanych = LinkedhashListaAktywnychKont.values();
        final ArrayList<String> arrayListLekarz = new ArrayList<String>(ListaDanych);
        final Intent aktywneKontaAdmin = new Intent(this, aktywneKontaAdmin.class);


        final ArrayAdapter adp = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayListLekarz);

        aktywneKonta.setAdapter(adp);
        aktywneKonta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object listItem = aktywneKonta.getItemAtPosition(position);
                final String idLekarza = ListaKluczy.get(position);

                aktywneKontaAdmin.putExtra("IP", IP);
                aktywneKontaAdmin.putExtra("idLekarza", idLekarza);

                startActivity(aktywneKontaAdmin);



            }
        });
    }


    public void wyloguj(View view) {
        Intent wylogowanie = new Intent(this,MainActivity.class);
        startActivity(wylogowanie);
    }
}

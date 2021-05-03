package com.example.praca_inz_03;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;




public class DodajWizyte extends AppCompatActivity {
    String pSpecjalizacja;

    Context context;

    ListView listaWizyt;
    String IP;
    String ajdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wizyte);

        Bundle bundle=getIntent().getExtras();
        ajdi = getIntent().getStringExtra("idPacjenta");
        IP = getIntent().getStringExtra("IP");


        final Spinner listaSpecjalizacjiSpinner;
        listaSpecjalizacjiSpinner = (Spinner) findViewById( R.id.listaSpecjalizacji);
        bgPobierzSpecjalizacje bg = new bgPobierzSpecjalizacje(this);

        List<String> listaSpecjalizacji= new ArrayList<>();
        try {
            listaSpecjalizacji = bg.execute(IP).get();
        }catch (Exception e){}
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,  R.layout.spinner_close, listaSpecjalizacji);
        adp1.setDropDownViewResource(R.layout.spinner_open);

        listaSpecjalizacjiSpinner.setAdapter(adp1);


        listaSpecjalizacjiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                pSpecjalizacja= (String) listaSpecjalizacjiSpinner.getSelectedItem();
                Wyswietl(pSpecjalizacja);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }


    public void Wyswietl (String pSpecjalizacja){

        listaWizyt = (ListView)findViewById(R.id.listaWizyt);
        bgPobierzDostepneWizyty bgPDW = new bgPobierzDostepneWizyty(this);

        LinkedHashMap<String, String> LinkedhashMapaWizyt = new LinkedHashMap<String,String>();
        try {
            LinkedhashMapaWizyt = (LinkedHashMap<String,String>) bgPDW.execute(pSpecjalizacja,IP).get();
        }catch (Exception e){}


        Set<String> IdWizyt = LinkedhashMapaWizyt.keySet();
        final ArrayList<String> ListaKluczyWizyt = new ArrayList<String>(IdWizyt);

        Collection<String> ListaDanychWizyt = LinkedhashMapaWizyt.values();
        final ArrayList<String> arrayListWizyt = new ArrayList<String>(ListaDanychWizyt);

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayListWizyt);
        listaWizyt.setAdapter(arrayAdapter);
        listaWizyt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object listItem = listaWizyt.getItemAtPosition(position);
                final String idWizyty = ListaKluczyWizyt.get(position);


                AlertDialog.Builder builder = new AlertDialog.Builder(DodajWizyte.this);
                builder.setTitle("Potwierdzenie rezerwacji");
                builder.setMessage("Czy chcesz potwierdzić rezerwację wizyty?");

                builder.setPositiveButton("Tak",

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bgPotwierdzWizyte bgPW = new bgPotwierdzWizyte(DodajWizyte.this);
                                String ress=bgPW.execute(idWizyty,ajdi,IP).toString();
                            }
                        }
                );
                builder.setNegativeButton("Nie", null);
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }



}

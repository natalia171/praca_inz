package com.example.praca_inz_03;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.List;
import java.util.Set;


public class DodajWizyte extends AppCompatActivity {
    String pSpecjalizacja;//="internista"; //Zmien zeby pokazywalo lekarza wybranego ze spinnera

    Context context;

    ListView listaWizyt;
    String iDpacjenta="";
    //String ajdi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        iDpacjenta = getIntent().getStringExtra("ajdi");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wizyte);

        final Spinner listaSpecjalizacjiSpinner;
        listaSpecjalizacjiSpinner = (Spinner) findViewById( R.id.listaSpecjalizacji); //podpina spinner do obiektu w widoku
        bgPobierzSpecjalizacje bg = new bgPobierzSpecjalizacje(this); //tworzy nowa klase

        List<String> listaSpecjalizacji= new ArrayList<>(); //tworzy liste
        try {
            listaSpecjalizacji = bg.execute().get(); //przypisuje do listy wynik z klasy
            Log.d("DoWi","ressponse "+listaSpecjalizacji.get(0));
        }catch (Exception e){}
       ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, listaSpecjalizacji);//tworzy arrayadapter ktory bedzie posiadal wartosci listaspecjalizacji
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

       listaSpecjalizacjiSpinner.setAdapter(adp1);


        listaSpecjalizacjiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                pSpecjalizacja= (String) listaSpecjalizacjiSpinner.getSelectedItem();//pobieranie stringa z konkretna specjalizacja
                Wyswietl(pSpecjalizacja);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }


    public void Wyswietl (String pSpecjalizacja){

        listaWizyt = (ListView)findViewById(R.id.listaWizyt);
        bgPobierzDostepneWizyty bgPDW = new bgPobierzDostepneWizyty(this);


       // final bgPotwierdzWizyte bgPW = new bgPotwierdzWizyte(this);


//        String res=bgPW.execute("1","1").toString();
//        Log.d("ASD", "Wyswietl: "+res.toString());
        HashMap<String, String> hashMapaWizyt = new HashMap<String,String>();
        try {
            hashMapaWizyt = (HashMap<String,String>) bgPDW.execute(pSpecjalizacja).get();
        }catch (Exception e){}


        //Klucze z hashmapy
        Set<String> IdWizyt = hashMapaWizyt.keySet();
        final ArrayList<String> ListaKluczyWizyt = new ArrayList<String>(IdWizyt);

        //Opisy wizyt
        Collection<String> ListaDanychWizyt = hashMapaWizyt.values();
        final ArrayList<String> arrayListWizyt = new ArrayList<String>(ListaDanychWizyt);
        //Info do loga
        Log.i("XXXX","XXXX"+ hashMapaWizyt.values()+" "+arrayListWizyt.get(1)+" "+ListaKluczyWizyt.get(1));

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayListWizyt);
        listaWizyt.setAdapter(arrayAdapter);
        listaWizyt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object listItem = listaWizyt.getItemAtPosition(position);
                Log.d("DoWi","ressponse "+listItem.toString());
                final String idWizyty = ListaKluczyWizyt.get(position);

                final AlertDialog.Builder builder = new AlertDialog.Builder(DodajWizyte.this);
                builder.setTitle("Potwierdzenie rezerwacji");
                builder.setMessage("Czy chcesz potwierdzić rezerwację wizyty?");


                // add the buttons
               builder.setPositiveButton("Tak",null);





                builder.setNegativeButton("Nie", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }
}

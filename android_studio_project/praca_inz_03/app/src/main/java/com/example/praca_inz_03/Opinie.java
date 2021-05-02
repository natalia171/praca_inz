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




public class Opinie extends AppCompatActivity {

    String pSpecjalizacja;//="internista"; //Zmien zeby pokazywalo lekarza wybranego ze spinnera

    ListView listaNazwisk;
    String IP;
    String ajdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinie);

        ajdi = getIntent().getStringExtra("idPacjenta");
        IP = getIntent().getStringExtra("IP");
        Log.d("id", "onCreate dodaj wizyte: ID w dodaj wizyte:  "+ajdi);

//         bgPotwierdzWizyte bgPW = new bgPotwierdzWizyte(this);
//         String ress=bgPW.execute("1",ajdi).toString();
//         Log.d("Main","ressponse "+ress);



        final Spinner listaSpecjalizacjiSpinner;
        listaSpecjalizacjiSpinner = (Spinner) findViewById( R.id.listaSpecjalizacji2); //podpina spinner do obiektu w widoku
        bgPobierzSpecjalizacje bg = new bgPobierzSpecjalizacje(this); //tworzy nowa klase

        List<String> listaSpecjalizacji= new ArrayList<>(); //tworzy liste
        try {
            listaSpecjalizacji = bg.execute(IP).get(); //przypisuje do listy wynik z klasy
            Log.d("DoWi","ressponse "+listaSpecjalizacji.get(0));
        }catch (Exception e){}
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,  R.layout.spinner_close, listaSpecjalizacji);//tworzy arrayadapter ktory bedzie posiadal wartosci listaspecjalizacji
        adp1.setDropDownViewResource(R.layout.spinner_open);

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

        listaNazwisk = (ListView)findViewById(R.id.listaNazwisk);
        bgPobierzNazwiskaSpecjalistow bgPNS = new bgPobierzNazwiskaSpecjalistow(this);
//        bgPotwierdzWizyte bgPW = new bgPotwierdzWizyte(this);
//        String ress=bgPW.execute("1","1").toString();
//        Log.d("Main","ressponse "+ress);

        LinkedHashMap<String, String> LinkedhashMapaNazwisk = new LinkedHashMap<String,String>();
        try {
            LinkedhashMapaNazwisk = (LinkedHashMap<String,String>) bgPNS.execute(pSpecjalizacja,IP).get();
        }catch (Exception e){}


        //Klucze z hashmapy
        Set<String> IdWizyt = LinkedhashMapaNazwisk.keySet();
        final ArrayList<String> ListaKluczyNazwisk = new ArrayList<String>(IdWizyt);

        //Opisy wizyt
        Collection<String> ListaDanychNazwisk = LinkedhashMapaNazwisk.values();
        final ArrayList<String> arrayListNazwisk = new ArrayList<String>(ListaDanychNazwisk);

        //utworzenie intencji profilu lekarza
        final Intent profilLekarza = new Intent(this, ProfilLekarza.class);

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayListNazwisk);
        listaNazwisk.setAdapter(arrayAdapter);
        listaNazwisk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object listItem = listaNazwisk.getItemAtPosition(position);
                Log.d("DoWi","ressponse "+listItem.toString());
                final String idNazwisk = ListaKluczyNazwisk.get(position);

                profilLekarza.putExtra("idLekarza", idNazwisk);
                profilLekarza.putExtra("idPacjenta",ajdi);
                profilLekarza.putExtra("IP", IP);
                startActivity(profilLekarza);



            }
        });
    }



}

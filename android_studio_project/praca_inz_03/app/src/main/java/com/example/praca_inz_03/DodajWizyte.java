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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;




public class DodajWizyte extends AppCompatActivity {
    String pSpecjalizacja;//="internista"; //Zmien zeby pokazywalo lekarza wybranego ze spinnera

    Context context;

    ListView listaWizyt;
    //String idPacjenta;
    String IP;
    String ajdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wizyte);

        Bundle bundle=getIntent().getExtras();
        ajdi = getIntent().getStringExtra("idPacjenta");
        IP = getIntent().getStringExtra("IP");
        //ajdi=bundle.getString("idPacjenta");
        Log.d("id", "onCreate dodaj wizyte: ID w dodaj wizyte:  "+ajdi);

//         bgPotwierdzWizyte bgPW = new bgPotwierdzWizyte(this);
//         String ress=bgPW.execute("1",ajdi).toString();
//         Log.d("Main","ressponse "+ress);



        final Spinner listaSpecjalizacjiSpinner;
        listaSpecjalizacjiSpinner = (Spinner) findViewById( R.id.listaSpecjalizacji); //podpina spinner do obiektu w widoku
        bgPobierzSpecjalizacje bg = new bgPobierzSpecjalizacje(this); //tworzy nowa klase

        List<String> listaSpecjalizacji= new ArrayList<>(); //tworzy liste
        try {
            listaSpecjalizacji = bg.execute(IP).get(); //przypisuje do listy wynik z klasy
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
//        bgPotwierdzWizyte bgPW = new bgPotwierdzWizyte(this);
//        String ress=bgPW.execute("1","1").toString();
//        Log.d("Main","ressponse "+ress);

        LinkedHashMap<String, String> LinkedhashMapaWizyt = new LinkedHashMap<String,String>();
        try {
            LinkedhashMapaWizyt = (LinkedHashMap<String,String>) bgPDW.execute(pSpecjalizacja,IP).get();
        }catch (Exception e){}


        //Klucze z hashmapy
        Set<String> IdWizyt = LinkedhashMapaWizyt.keySet();
        final ArrayList<String> ListaKluczyWizyt = new ArrayList<String>(IdWizyt);

        //Opisy wizyt
        Collection<String> ListaDanychWizyt = LinkedhashMapaWizyt.values();
        final ArrayList<String> arrayListWizyt = new ArrayList<String>(ListaDanychWizyt);
        //Info do loga
        Log.i("XXXX","XXXX"+ LinkedhashMapaWizyt.values()+" "+arrayListWizyt.get(1)+" "+ListaKluczyWizyt.get(1));

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayListWizyt);
        listaWizyt.setAdapter(arrayAdapter);
        listaWizyt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object listItem = listaWizyt.getItemAtPosition(position);
                Log.d("DoWi","ressponse "+listItem.toString());
                final String idWizyty = ListaKluczyWizyt.get(position);


                AlertDialog.Builder builder = new AlertDialog.Builder(DodajWizyte.this);
                builder.setTitle("Potwierdzenie rezerwacji");
                builder.setMessage("Czy chcesz potwierdzić rezerwację wizyty?");
                // add the buttons

                builder.setPositiveButton("Tak",

                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("id wizytowe", "id wizyty "+idWizyty);
                        bgPotwierdzWizyte bgPW = new bgPotwierdzWizyte(DodajWizyte.this);
                        String ress=bgPW.execute(idWizyty,ajdi,IP).toString();
                        Log.d("Main","ressponse "+ress);
                    }
                }
                );
                builder.setNegativeButton("Nie", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }
}

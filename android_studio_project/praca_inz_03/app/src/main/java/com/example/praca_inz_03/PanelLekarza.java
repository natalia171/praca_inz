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




public class PanelLekarza extends AppCompatActivity {
    Context context;
    ListView wolneTerminy;
    ListView nadchodzaceWizyty;
    String IP;
    String ajdi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_lekarza);


        ajdi = getIntent().getStringExtra("idLekarza");
        IP = getIntent().getStringExtra("IP");


        wolneTerminy = findViewById(R.id.wolneTerminy);
        nadchodzaceWizyty = findViewById(R.id.nadchodząceWizyty);


        //pobieranie nadchodzacych wizyt
        bgPobierzNadchodzaceWizytyLekarz bgPNWL = new bgPobierzNadchodzaceWizytyLekarz(this); //tworzy nowa klase

        List<String> nadchodzace_wizyty_lekarz= new ArrayList<>(); //tworzy liste
        try {
            nadchodzace_wizyty_lekarz = bgPNWL.execute(ajdi,IP).get(); //przypisuje do listy wynik z klasy
            Log.d("nadcho","wizyty "+nadchodzace_wizyty_lekarz.get(0));
        }catch (Exception e){}
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, nadchodzace_wizyty_lekarz);//tworzy arrayadapter ktory bedzie posiadal wartosci listaspecjalizacji
        //adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        nadchodzaceWizyty.setAdapter(adp1);


      //  pobieranie wolnych terminow

        bgPobierzWolneTerminy bgPWT = new bgPobierzWolneTerminy(this);


        LinkedHashMap<String, String> LinkedhashMapaTerminow=new LinkedHashMap<String,String>();
        try {
            LinkedhashMapaTerminow = (LinkedHashMap<String,String>) bgPWT.execute(ajdi,IP).get();

        }catch (Exception e){}








        //Klucze z hashmapy
        Set<String> IdWizyt = LinkedhashMapaTerminow.keySet();
        final ArrayList<String> ListaKluczy = new ArrayList<String>(IdWizyt);

        //Opisy wizyt
        Collection<String> ListaDanych = LinkedhashMapaTerminow.values();
        final ArrayList<String> arrayListTerminow = new ArrayList<String>(ListaDanych);
        //Info do loga
//        Log.i("XXXX","XXXX"+ LinkedhashMapaTerminow.values()+" "+arrayListTerminow.get(1)+" "+ListaKluczy.get(1));

        final ArrayAdapter adp2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayListTerminow);
        wolneTerminy.setAdapter(adp2);

        wolneTerminy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object listItem = wolneTerminy.getItemAtPosition(position);
                Log.d("DoWi","ressponse "+listItem.toString());
                final String idTerminu = ListaKluczy.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(PanelLekarza.this);
                builder.setTitle("Usuwanie terminu");
                builder.setMessage("Czy chcesz usunąć termin?");
                // add the buttons

                builder.setPositiveButton("Tak",

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("id terminu", "id terminu "+idTerminu);
                                bgAnulujTermin bgAT = new bgAnulujTermin(PanelLekarza.this);
                                String ress=bgAT.execute(idTerminu,ajdi,IP).toString();
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



    public void dodajWizte(View view) {
        String idLekarza = ajdi;
        //idPacjenta=getIntent().getStringExtra("s");
        Intent intencjaDodajWizyte = new Intent(this, DodajNowyTerminLekarz.class);
        intencjaDodajWizyte.putExtra("idLekarza", idLekarza);
        intencjaDodajWizyte.putExtra("IP", IP);
        startActivity(intencjaDodajWizyte);
        Log.d("id", "panel lekarza:  " + idLekarza);


    }


}

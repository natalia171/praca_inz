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




public class PanelPacjenta extends AppCompatActivity {
    Context context;
    ListView historiaWizyt;
    ListView nadchodzaceWizyty;
    String IP;
    String ajdi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_pacjenta);


        ajdi = getIntent().getStringExtra("idPacjenta");
        IP = getIntent().getStringExtra("IP");


        historiaWizyt = findViewById(R.id.historiaWizyt);
        nadchodzaceWizyty = findViewById(R.id.nadchodząceWizyty);


        //pobieranie historii wizyt
        bgPobierzHistorieWizyt bgPHW = new bgPobierzHistorieWizyt(this);

        List<String> historia_wizyt= new ArrayList<>();
        try {
            historia_wizyt = bgPHW.execute(ajdi,IP).get();
        }catch (Exception e){}
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, historia_wizyt);
        historiaWizyt.setAdapter(adp1);



        //pobieranie nadchodzacych wizyt

        bgPobierzNadchodzaceWizyty bgPNW = new bgPobierzNadchodzaceWizyty(this);

        LinkedHashMap<String, String> LinkedhashMapaWizyt=new LinkedHashMap<String,String>();
        try {
            LinkedhashMapaWizyt = (LinkedHashMap<String,String>) bgPNW.execute(ajdi,IP).get();

        }catch (Exception e){}



        Set<String> IdWizyt = LinkedhashMapaWizyt.keySet();
        final ArrayList<String> ListaKluczyWizyt = new ArrayList<String>(IdWizyt);

        Collection<String> ListaDanychWizyt = LinkedhashMapaWizyt.values();
        final ArrayList<String> arrayListWizyt = new ArrayList<String>(ListaDanychWizyt);

        final ArrayAdapter adp2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayListWizyt);
        nadchodzaceWizyty.setAdapter(adp2);

        nadchodzaceWizyty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object listItem = nadchodzaceWizyty.getItemAtPosition(position);
                final String idWizyty = ListaKluczyWizyt.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(PanelPacjenta.this);
                builder.setTitle("Anulowanie rezerwacji");
                builder.setMessage("Czy chcesz anulować wizytę?");
                builder.setPositiveButton("Tak",

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                bgAnulujWizyte bgAW = new bgAnulujWizyte(PanelPacjenta.this);
                                String ress=bgAW.execute(idWizyty,ajdi,IP).toString();
                            }
                        }
                );
                builder.setNegativeButton("Nie", null);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }



    public void dodajWizte(View view) {
        String idPacjenta = ajdi;
        Intent intencjaDodajWizyte = new Intent(this, DodajWizyte.class);
        intencjaDodajWizyte.putExtra("idPacjenta", idPacjenta);
        intencjaDodajWizyte.putExtra("IP", IP);
        startActivity(intencjaDodajWizyte);


    }


    public void opinie(View view) {
        String idPacjenta = ajdi;
        Intent intencjaOpinie = new Intent(this, Opinie.class);
        intencjaOpinie.putExtra("idPacjenta", idPacjenta);
        intencjaOpinie.putExtra("IP", IP);
        startActivity(intencjaOpinie);
    }

    public void wyloguj(View view) {
        Intent wylogowanie = new Intent(this,MainActivity.class);
        startActivity(wylogowanie);
    }
    public void onBackPressed(){}

}

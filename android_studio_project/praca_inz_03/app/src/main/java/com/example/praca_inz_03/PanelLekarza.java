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
import android.widget.TextView;
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
        bgPobierzNadchodzaceWizytyLekarz bgPNWL = new bgPobierzNadchodzaceWizytyLekarz(this);

        List<String> nadchodzace_wizyty_lekarz= new ArrayList<>();
        try {
            nadchodzace_wizyty_lekarz = bgPNWL.execute(ajdi,IP).get();
        }catch (Exception e){}
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, nadchodzace_wizyty_lekarz);

        nadchodzaceWizyty.setAdapter(adp1);



      //  pobieranie wolnych terminow

        bgPobierzWolneTerminy bgPWT = new bgPobierzWolneTerminy(this);

        LinkedHashMap<String, String> LinkedhashMapaTerminow=new LinkedHashMap<String,String>();
        try {
            LinkedhashMapaTerminow = (LinkedHashMap<String,String>) bgPWT.execute(ajdi,IP).get();

        }catch (Exception e){}

        Set<String> IdWizyt = LinkedhashMapaTerminow.keySet();
        final ArrayList<String> ListaKluczy = new ArrayList<String>(IdWizyt);

        Collection<String> ListaDanych = LinkedhashMapaTerminow.values();
        final ArrayList<String> arrayListTerminow = new ArrayList<String>(ListaDanych);

        final ArrayAdapter adp2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayListTerminow);
        wolneTerminy.setAdapter(adp2);

        wolneTerminy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object listItem = wolneTerminy.getItemAtPosition(position);
                final String idTerminu = ListaKluczy.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(PanelLekarza.this);
                builder.setTitle("Usuwanie terminu");
                builder.setMessage("Czy chcesz usunąć termin?");
                builder.setPositiveButton("Tak",

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bgAnulujTermin bgAT = new bgAnulujTermin(PanelLekarza.this);
                                String ress=bgAT.execute(idTerminu,ajdi,IP).toString();
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
        String idLekarza = ajdi;
        Intent intencjaDodajWizyte = new Intent(this, DodajNowyTerminLekarz.class);
        intencjaDodajWizyte.putExtra("idLekarza", idLekarza);
        intencjaDodajWizyte.putExtra("IP", IP);
        startActivity(intencjaDodajWizyte);


    }


    public void podgladOpinii(View view) {
        String idLekarza = ajdi;
        Intent intencjaOpinie = new Intent(this, OpiniePanelLekarza.class);
        intencjaOpinie.putExtra("idLekarza", idLekarza);
        intencjaOpinie.putExtra("IP", IP);
        startActivity(intencjaOpinie);
    }

    public void wyloguj(View view) {
        Intent wylogowanie = new Intent(this,MainActivity.class);
        startActivity(wylogowanie);
    }
}

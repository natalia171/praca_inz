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




public class profilAdmina extends AppCompatActivity {
    ListView daneLekarza;
    String IP;
    String idLekarza;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_admina);

        IP = getIntent().getStringExtra("IP");
        idLekarza = getIntent().getStringExtra("idLekarza");


        daneLekarza = findViewById(R.id.daneLekarza);



        //pobieranie danych lekarza do zatwierdzenia

        bgPobierzDaneLekarzaDoZatwierdzenia bgPDLDZ = new bgPobierzDaneLekarzaDoZatwierdzenia(this);

        LinkedHashMap<String, String> LinkedhashListaLekarzy=new LinkedHashMap<String,String>();
        try {
            LinkedhashListaLekarzy = (LinkedHashMap<String,String>) bgPDLDZ.execute(IP,idLekarza).get();

        }catch (Exception e){}



        Set<String> IdWizyt = LinkedhashListaLekarzy.keySet();
        final ArrayList<String> ListaKluczyLekarzy = new ArrayList<String>(IdWizyt);

        Collection<String> ListaDanychLekarzy = LinkedhashListaLekarzy.values();
        final ArrayList<String> arrayListLekarzy = new ArrayList<String>(ListaDanychLekarzy);
        final Intent profilAdmina = new Intent(this, profilAdmina.class);


        final ArrayAdapter adp2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayListLekarzy);

        daneLekarza.setAdapter(adp2);

    }

    public void zatwierdz(View view) {
        bgZatwierdzProfil bgZP = new bgZatwierdzProfil(profilAdmina.this);
        String ress=bgZP.execute(idLekarza,IP).toString();
    }
}

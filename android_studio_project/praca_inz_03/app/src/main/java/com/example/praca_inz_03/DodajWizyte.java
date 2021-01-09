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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import static com.example.praca_inz_03.R.id.listaWizyt;

public class DodajWizyte extends AppCompatActivity {
    String result = "";
    ListView listaWizyt;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wizyte);
        context=this.getApplicationContext();
        listaWizyt = (ListView)findViewById(R.id.listaWizyt);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("rrrrrr");
        arrayList.add("aaa");
        arrayList.add("bbbbbbb");
        arrayList.add("ccccccc");
        arrayList.add("ddddddd");
        arrayList.add("eeeeee");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listaWizyt.setAdapter(arrayAdapter);
        listaWizyt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = listaWizyt.getItemAtPosition(position);
                Log.d("DoWi","ressponse "+listItem.toString());

                AlertDialog.Builder builder = new AlertDialog.Builder(DodajWizyte.this);
                builder.setTitle("Potwierdzenie rezerwacji");
                builder.setMessage("Czy chcesz potwierdzić rezerwację wizyty?");

                // add the buttons
                builder.setPositiveButton("Tak", null);
                builder.setNegativeButton("Nie", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        Spinner listaSpecjalizacjiSpinner;
        listaSpecjalizacjiSpinner = (Spinner) findViewById( R.id.listaSpecjalizacji);
        bgPobierzSpecjalizacje bg = new bgPobierzSpecjalizacje(this);
        List<String> listaSpecjalizacji= new ArrayList<>();
        try {
            listaSpecjalizacji = bg.execute().get();
            Log.d("DoWi","ressponse "+listaSpecjalizacji.get(0));
        }catch (Exception e){}
       ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, listaSpecjalizacji);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.d("DoWi","ressponse x  "+adp1.getItem(1));
       listaSpecjalizacjiSpinner.setAdapter(adp1);

        Log.d("DoWi","ressponse "+listaSpecjalizacji);


    }

    
}

package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText pas,usr;
   String IP="192.168.1.164";
   // String IP="192.168.0.18";
    Spinner listaSpecjalizacji;
    RadioButton RadioPacjent, RadioLekarz;
    RadioGroup radiogroup1;
    String kto="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radiogroup1 = findViewById(R.id.radiogroup1);
        usr = (EditText) findViewById( R.id.email);
        pas = (EditText) findViewById(R.id.haslo);
       // RadioPacjent = findViewById(R.id.RadioPacjent);
        //RadioLekarz = findViewById(R.id.RadioLekarz);
        radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        Log.d("nic", "Choices cleared!");
                        break;
                    case R.id.RadioPacjent:
                        kto="Pacjent";

                        break;
                    case R.id.RadioLekarz:
                        kto="Lekarz";
                        break;
                }
            }
        });
    }

    public void login(View view) {
       final String user = usr.getText().toString();
        final String pass = pas.getText().toString();

        if(kto=="Pacjent") {
                bgLogowanie bg = new bgLogowanie(this);
                String res=bg.execute(user,pass,IP).toString();
                Log.d("MainACC","Odpowiedz logowanie "+res);
        }
        if(kto=="Lekarz"){
            bgLogowanieLekarz bgLL = new bgLogowanieLekarz(this);
            String res=bgLL.execute(user,pass,IP).toString();
            Log.d("MainACC","Odpowiedz logowanie lekarz"+res);
        }
        else if (kto==""){
            Toast toast= Toast.makeText(MainActivity.this,"Wybierz pacjent lub lekarz",Toast.LENGTH_LONG);
            toast.show();        }
    }

    public void rejestracja(View view) {
        if(kto=="Pacjent") {
            //tworzenie nowej aktywnosci  i jej uruchomienie
            Intent intencjaRejestracja = new Intent(this,rejestracja.class);
            intencjaRejestracja.putExtra("IP",IP);
            startActivity(intencjaRejestracja);
        }
        if(kto=="Lekarz"){
            Intent intencjaRejestracjaLekarza = new Intent(this,rejestracjaLekarza.class);
            intencjaRejestracjaLekarza.putExtra("IP",IP);
            startActivity(intencjaRejestracjaLekarza);
        }
        else if (kto==""){
            Toast toast= Toast.makeText(MainActivity.this,"Wybierz pacjent lub lekarz",Toast.LENGTH_LONG);
            toast.show();        }



    }

}

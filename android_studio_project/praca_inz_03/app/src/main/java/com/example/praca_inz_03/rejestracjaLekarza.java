package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class rejestracjaLekarza extends AppCompatActivity {
    //stworzenie edittextow
    EditText em, im, nazw, pes, tel, spec, has, has1;
    String IP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja_lekarza);

        IP = getIntent().getStringExtra("IP");


        //znalezienie i przypisanie edittextow
        em=(EditText)findViewById(R.id.email);
        im=(EditText)findViewById(R.id.imie);
        nazw=(EditText)findViewById(R.id.nazwisko);
        pes =(EditText)findViewById(R.id.pesel);
        tel=(EditText)findViewById(R.id.telefon);
        spec=(EditText)findViewById(R.id.specjalizacja);
        has=(EditText)findViewById(R.id.haslo);
        has1=(EditText)findViewById(R.id.haslo1);
    }

    public void DodajBtn(View view) {
        // wyciagniecie wpisanych w edittexty danych do stringow, po wcisnieciu buttona
        String email = em.getText().toString();
        String imie = im.getText().toString();
        String nazwisko = nazw.getText().toString();
        String pesel = pes.getText().toString();
        String telefon = tel.getText().toString();
        String specjalizacja = spec.getText().toString();
        String haslo = has.getText().toString();
        String haslo1 = has1.getText().toString();

        //  ??????? stworzenie klasy rejestracja i wywolanie jej z danymi z edittextow
        bgRejestracjaLekarza bgRL = new bgRejestracjaLekarza(this);
        bgRL.execute(email, imie, nazwisko, pesel, telefon, specjalizacja, haslo, haslo1,IP);
    }
}

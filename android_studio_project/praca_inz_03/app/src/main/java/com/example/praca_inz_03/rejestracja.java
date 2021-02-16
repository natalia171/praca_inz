package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class rejestracja extends AppCompatActivity {
    //stworzenie edittextow
    EditText em, im, nazw, pes, tel, dat, has, has1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);

        //znalezienie i przypisanie edittextow
        em=(EditText)findViewById(R.id.email);
        im=(EditText)findViewById(R.id.imie);
        nazw=(EditText)findViewById(R.id.nazwisko);
        pes =(EditText)findViewById(R.id.pesel);
        tel=(EditText)findViewById(R.id.telefon);
        dat=(EditText)findViewById(R.id.data_urodzenia);
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
        String data_urodzenia = dat.getText().toString();
        String haslo = has.getText().toString();
        String haslo1 = has1.getText().toString();

        //  ??????? stworzenie klasy rejestracja i wywolanie jej z danymi z edittextow
        bgRejestracja bg = new bgRejestracja(this);
        bg.execute(email, imie, nazwisko, pesel, telefon, data_urodzenia, haslo, haslo1);
    }
}

package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class rejestracja extends AppCompatActivity {
    EditText em, im, nazw, pes, tel, dat, has, has1;
    String IP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);

        IP = getIntent().getStringExtra("IP");
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
        String email = em.getText().toString();
        String regexemail = "[a-zA-Z0-9-_.]+@[a-zA-Z0-9-_.]+\\.[a-zA-Z0-9]+";
        String imie = im.getText().toString();
        String regeximie = "[a-zA-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]+";
        String nazwisko = nazw.getText().toString();
        String pesel = pes.getText().toString();
        String regexpesel = "[0-9]{11}";
        String telefon = tel.getText().toString();
        String regextelefon = "[0-9]{9}";
        String data_urodzenia = dat.getText().toString();
        String regexdata = "\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])*";
        String haslo = has.getText().toString();
        String haslo1 = has1.getText().toString();

        if (email.matches(regexemail)==false)
        { Toast toast1= Toast.makeText(rejestracja.this,"Niepoprawny email!",Toast.LENGTH_SHORT);
            toast1.show();
        }
        if (imie.matches(regeximie)==false)
        { Toast toast2= Toast.makeText(rejestracja.this,"Wpisz poprawne imię!",Toast.LENGTH_SHORT);
            toast2.show();
        }
        if (nazwisko.matches(regeximie)==false)
        { Toast toast3= Toast.makeText(rejestracja.this,"Wpisz poprawne nazwisko!",Toast.LENGTH_SHORT);
            toast3.show();
        }
        if (pesel.matches(regexpesel)==false)
        { Toast toast4= Toast.makeText(rejestracja.this,"Wpisz poprawny PESEL!",Toast.LENGTH_SHORT);
            toast4.show();
        }
        if (telefon.matches(regextelefon)==false)
        { Toast toast5= Toast.makeText(rejestracja.this,"Wpisz poprawny numer telefonu!",Toast.LENGTH_SHORT);
            toast5.show();
        }
        if (data_urodzenia.matches(regexdata)==false)
        { Toast toast6= Toast.makeText(rejestracja.this,"Wpisz poprawną datę urodzenia!",Toast.LENGTH_SHORT);
            toast6.show();
        }
        if (haslo.isEmpty()==true || haslo1.isEmpty()==true)
        { Toast toast7= Toast.makeText(rejestracja.this,"Wprowadź hasło!",Toast.LENGTH_SHORT);
            toast7.show();
        }
        if ((haslo.equals(haslo1))==false)
        { Toast toast8= Toast.makeText(rejestracja.this,"Hasła nie są identyczne!",Toast.LENGTH_SHORT);
            toast8.show();
        }


        if (email.matches(regexemail)
                &&imie.matches(regeximie)
                &&nazwisko.matches(regeximie)
                &&pesel.matches(regexpesel)
                &&telefon.matches(regextelefon)
                &&data_urodzenia.matches(regexdata)
                &&haslo.equals(haslo1)&&haslo.isEmpty()==false
        ) {
            bgRejestracja bg = new bgRejestracja(this);
            bg.execute(email, imie, nazwisko, pesel, telefon, data_urodzenia, haslo, haslo1,IP);
        }

    }
}

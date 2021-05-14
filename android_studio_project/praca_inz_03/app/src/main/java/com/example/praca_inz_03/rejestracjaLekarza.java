package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class rejestracjaLekarza extends AppCompatActivity {
    EditText em, im, nazw, pes, tel, spec, has, has1;
    String IP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja_lekarza);

        IP = getIntent().getStringExtra("IP");

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
        String email = em.getText().toString();
        String regexemail = "[a-zA-Z0-9-_.]+@[a-zA-Z0-9-_.]+\\.[a-zA-Z0-9]+";
        String imie = im.getText().toString();
        String regeximie = "[a-zA-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]+";
        String nazwisko = nazw.getText().toString();
        String pesel = pes.getText().toString();
        String regexpesel = "[0-9]{11}";
        String telefon = tel.getText().toString();
        String regextelefon = "[0-9]{9}";
        String specjalizacja = spec.getText().toString();
        String haslo = has.getText().toString();
        String haslo1 = has1.getText().toString();
        if (email.matches(regexemail) == false) {
            Toast toast = Toast.makeText(rejestracjaLekarza.this, "Niepoprawny email!", Toast.LENGTH_LONG);
            toast.show();
        }
        if (imie.matches(regeximie) == false) {
            Toast toast = Toast.makeText(rejestracjaLekarza.this, "Wpisz poprawne imię!", Toast.LENGTH_LONG);
            toast.show();
        }
        if (nazwisko.matches(regeximie) == false) {
            Toast toast = Toast.makeText(rejestracjaLekarza.this, "Wpisz poprawne nazwisko!", Toast.LENGTH_LONG);
            toast.show();
        }
        if (pesel.matches(regexpesel) == false) {
            Toast toast = Toast.makeText(rejestracjaLekarza.this, "Wpisz poprawny PESEL!", Toast.LENGTH_LONG);
            toast.show();
        }
        if (telefon.matches(regextelefon) == false) {
            Toast toast = Toast.makeText(rejestracjaLekarza.this, "Wpisz poprawny numer telefonu!", Toast.LENGTH_LONG);
            toast.show();
        }
        if (specjalizacja.matches(regeximie) == false) {
            Toast toast = Toast.makeText(rejestracjaLekarza.this, "Wpisz poprawną specjalizację!", Toast.LENGTH_LONG);
            toast.show();
        }
        if (haslo.isEmpty() == true || haslo1.isEmpty() == true) {
            Toast toast = Toast.makeText(rejestracjaLekarza.this, "Wprowadź hasło!", Toast.LENGTH_LONG);
            toast.show();
        }
        if ((haslo.equals(haslo1)) == false) {
            Toast toast = Toast.makeText(rejestracjaLekarza.this, "Hasła nie są identyczne!", Toast.LENGTH_LONG);
            toast.show();
        }


        if (email.matches(regexemail)
                && imie.matches(regeximie)
                && nazwisko.matches(regeximie)
                && pesel.matches(regexpesel)
                && telefon.matches(regextelefon)
                && specjalizacja.matches(regeximie)
                && haslo.equals(haslo1) && haslo.isEmpty() == false
        ) {
            bgRejestracjaLekarza bgRL = new bgRejestracjaLekarza(this);
            bgRL.execute(email, imie, nazwisko, pesel, telefon, specjalizacja, haslo, haslo1, IP);
        }
    }
}

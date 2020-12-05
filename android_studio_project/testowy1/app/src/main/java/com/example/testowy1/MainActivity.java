package com.example.testowy1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText has,em;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        has=(EditText) findViewById(R.id.haslo);
        em=(EditText) findViewById(R.id.email);
    }

    public void login(View view) {
        String email= em.getText().toString();
        String haslo= has.getText().toString();
    }

    public void rejestr(View view) {
        Intent intencja = new Intent(this, rejestracja.class);
        startActivity(intencja);
    }


}

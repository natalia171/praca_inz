package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DodajNowyTerminLekarz extends AppCompatActivity {

    EditText wizytaStart;
    EditText wizytaKoniec;
    String IP;
    String idLekarza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                String languageToLoad  = "pl";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_dodaj_nowy_termin_lekarz);
        wizytaStart=findViewById(R.id.date_time_inputStart);
        wizytaKoniec=findViewById(R.id.date_time_inputEnd);

        wizytaStart.setInputType(InputType.TYPE_NULL);
        wizytaKoniec.setInputType(InputType.TYPE_NULL);

        idLekarza = getIntent().getStringExtra("idLekarza");
        IP = getIntent().getStringExtra("IP");


        wizytaStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazWyborWizytyStart(wizytaStart);
            }
        });

        wizytaKoniec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazWyborWizytyStop(wizytaKoniec);
            }
        });
    }

    private void pokazWyborWizytyStart(final EditText date_time_in) {
        final Calendar kalendarz=Calendar.getInstance();


                DatePickerDialog.OnDateSetListener wyborDaty=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                kalendarz.set(Calendar.YEAR,year);
                kalendarz.set(Calendar.MONTH,month);
                kalendarz.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener wyborGodziny=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        kalendarz.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        kalendarz.set(Calendar.MINUTE,minute);

                        SimpleDateFormat formatDaty=new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

                        date_time_in.setText(formatDaty.format(kalendarz.getTime()));
                    }
                };

                new TimePickerDialog(DodajNowyTerminLekarz.this,wyborGodziny,kalendarz.get(Calendar.HOUR_OF_DAY),kalendarz.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(DodajNowyTerminLekarz.this,wyborDaty,kalendarz.get(Calendar.YEAR),kalendarz.get(Calendar.MONTH),kalendarz.get(Calendar.DAY_OF_MONTH)).show();

    }
    private void pokazWyborWizytyStop(final EditText date_time_in) {
        final Calendar kalendarz=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener wyborDaty=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                kalendarz.set(Calendar.YEAR,year);
                kalendarz.set(Calendar.MONTH,month);
                kalendarz.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener wyborGodziny=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        kalendarz.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        kalendarz.set(Calendar.MINUTE,minute);

                        SimpleDateFormat formatDaty=new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

                        date_time_in.setText(formatDaty.format(kalendarz.getTime()));
                    }
                };

                new TimePickerDialog(DodajNowyTerminLekarz.this,wyborGodziny,kalendarz.get(Calendar.HOUR_OF_DAY),kalendarz.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(DodajNowyTerminLekarz.this,wyborDaty,kalendarz.get(Calendar.YEAR),kalendarz.get(Calendar.MONTH),kalendarz.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void DodajTermin(View view) {
        String poczatekWizyty = wizytaStart.getText().toString();
        String koniecWizyty = wizytaKoniec.getText().toString();
        Date aktualnyCzas = Calendar.getInstance().getTime();


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
            Date poczatek = sdf.parse(poczatekWizyty);
            Date koniec = sdf.parse(koniecWizyty);
            if(koniec.getTime() > poczatek.getTime()&& poczatek.getTime()>aktualnyCzas.getTime()) {
                bgDodajNowyTermin bgDNT = new bgDodajNowyTermin(DodajNowyTerminLekarz.this);
                String ress = bgDNT.execute(idLekarza, IP, poczatekWizyty, koniecWizyty).toString();
            }if(koniec.getTime() <= poczatek.getTime())
            {
                Toast toast= Toast.makeText(DodajNowyTerminLekarz.this,
                        "Czas końcowy musi być większy od początkowego!",Toast.LENGTH_LONG);
                toast.show();
            }if(poczatek.getTime()<= aktualnyCzas.getTime())
            {
                Toast toast= Toast.makeText(DodajNowyTerminLekarz.this,
                        "Wpisz prawidłową datę!",Toast.LENGTH_LONG);
                toast.show();
            }
        }
        catch (Exception ex){

        }



    }


}
package com.example.praca_inz_03;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DodajNowyTerminLekarz extends AppCompatActivity {

    EditText wizytaStart;
    EditText wizytaKoniec;
    String IP;
    String idLekarza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        bgDodajNowyTermin bgDNT = new bgDodajNowyTermin(DodajNowyTerminLekarz.this);
        String ress=bgDNT.execute(idLekarza,IP,poczatekWizyty,koniecWizyty).toString();


    }
}
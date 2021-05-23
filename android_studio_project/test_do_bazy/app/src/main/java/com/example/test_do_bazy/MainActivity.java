package com.example.test_do_bazy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
TextView dane;
String dl;
String id = "4";
String IP= "192.168.2.9";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dane=findViewById(R.id.dane);


        bgPobierzDaneLekarza bgPDL = new bgPobierzDaneLekarza(this);
        try {
            dl = (String) bgPDL.execute(id,IP).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dane.setText(dl);
    }
}
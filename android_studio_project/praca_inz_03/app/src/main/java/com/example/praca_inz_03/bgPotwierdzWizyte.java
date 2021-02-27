package com.example.praca_inz_03;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class bgPotwierdzWizyte extends AsyncTask <String, Void,String> {

    //utworzenie okienka dialogowego
    AlertDialog dialog;
    Context context;
    //stworzenie pustego stringa wynikowego
    String result = "";
    String tab[]= new String[2];


    //stworzenie zmiennej typu boolean domyslnie falszywej
    // public Boolean login = false;

    //???????
    public bgPotwierdzWizyte(Context context)
    {
        this.context = context;
    }

    @Override
    //funkcja przed wykonaniem????
    protected void onPreExecute() {
        //stworzenie okienka dialogowego z tytulem "login status"
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");
    }


    @Override
    // funkcja po wykonaniu ??????
    protected void onPostExecute(String s) {

            //tworzenie kolejnej aktywnosci - panelu pacjenta
            Intent intent_name = new Intent();
            intent_name.setClass(context.getApplicationContext(),PanelPacjenta.class);////////
            context.startActivity(intent_name);
            Toast toast= Toast.makeText(context,"Wizyta potwierdzona!",Toast.LENGTH_LONG);
            toast.show();
    }

    @Override
    //czemu (String... voids)?????
    protected String doInBackground(String... voids) {

        // dodawanie elementow do tablicy voids
        String idWizyty = voids[0];
        String idPacjenta = voids[1];

        String connstr = "http://192.168.1.164/login.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(idWizyty,"UTF-8")
                    +"&&"+URLEncoder.encode("ID_PACJENTA","UTF-8")+"="+URLEncoder.encode(idPacjenta,"UTF-8");
            writer.write(data);
            Log.d("bgPotwierdzenie",data);
            writer.flush(); // wysyła o co było napisane przez buffered writer
            writer.close(); // zamyka buffered writer
            ops.close(); // konczy tworzenie stringa do wyslania?????????


            http.disconnect();

            //zle sformuowany adres url
        } catch (MalformedURLException e) {
            result = e.getMessage();
            //blad odczytu?
        } catch (IOException e) {
            result = e.getMessage();
        }


        return result;


    }
}
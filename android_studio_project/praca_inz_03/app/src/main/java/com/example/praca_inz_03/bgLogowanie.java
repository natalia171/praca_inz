package com.example.praca_inz_03;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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


public class bgLogowanie extends AsyncTask <String, Void,String> {

    //utworzenie okienka dialogowego
    AlertDialog dialog;
    Context context;
    //stworzenie pustego stringa wynikowego
    String result = "";
    String tab[]= new String[2];
    String ajdi;
    String IP;

    //stworzenie zmiennej typu boolean domyslnie falszywej
   // public Boolean login = false;

    //???????
    public bgLogowanie(Context context)
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
        //s to wynik wykonania doInBackground

        //Log.d("bgLc",s.toString());

        if(s.contains("1"))
        {
            //tworzenie kolejnej aktywnosci - panelu pacjenta
            Intent intent_name = new Intent();
            intent_name.setClass(context.getApplicationContext(),PanelPacjenta.class);////////
            intent_name.putExtra("idPacjenta",ajdi);
           // Log.d("IP", "onPostExecute: IP"+IP);
            intent_name.putExtra("IP",IP);
            Log.d("id", "bg logowanie: ID w bg logowanie"+ajdi);

            context.startActivity(intent_name);


            Toast toast= Toast.makeText(context,"Zalogowano",Toast.LENGTH_LONG);
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.parseColor("#C39BD3"), PorterDuff.Mode.SRC_IN);
            TextView text = view.findViewById(android.R.id.message);
            text.setTextColor(Color.parseColor("#000000"));
            toast.show();


        }else{
            dialog.setMessage("Złe hasło lub login");
            dialog.show();
        }


    }
    @Override
    //czemu (String... voids)?????
    protected String doInBackground(String... voids) {

        // dodawanie elementow do tablicy voids
        String user = voids[0];
        String pass = voids[1];
        IP = voids[2];

       // String connstr = "http://192.168.0.18/login.php";
        String connstr = "http://"+IP+"/login.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")
                    +"&&"+URLEncoder.encode("haslo","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
            writer.write(data);
            Log.d("bgLogowanie",data);
            writer.flush(); // wysyła o co było napisane przez buffered writer
            writer.close(); // zamyka buffered writer
            ops.close(); // konczy tworzenie stringa do wyslania?????????

            //tworzenie stringa do odbioru?????????
            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            //tworzenie pustego stringa "line"
            String line ="";
            int i=0;
            //czytanie linii i sprawdzenie czy jest rozna od null
            while ((line = reader.readLine()) != null)
            {
                tab[i]=line;
                Log.d("bgLb",tab[i]);
                i++;
                // do wyniku dopisana zostaje linia
                result += line;

                Log.d("bgL",line);
                Log.d( "bgLa", result);
            }
            reader.close();
            ips.close();
            http.disconnect();
            ajdi=tab[1];

            // return result;//+tab[1];

            //zle sformuowany adres url
        } catch (MalformedURLException e) {
            result = e.getMessage();
            //blad odczytu?
        } catch (IOException e) {
            result = e.getMessage();
        }

       // Log.d( "bgLa", result);
        //Log.d("bgLd",ajdi);

        return result;


    }
}
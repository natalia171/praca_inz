package com.example.praca_inz_03;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

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

public class bgPobierzDaneLekarza extends AsyncTask <String, Void,String> {

    //utworzenie okienka dialogowego
    AlertDialog dialog;
    Context context;
    String IP;
    //stworzenie pustego stringa wynikowego
    String result = "";
    // String idPacjenta;
    public bgPobierzDaneLekarza(Context context) {
        this.context = context;
    }



    @Override
    // funkcja po wykonaniu ??????
    protected void onPostExecute(String s) {


    }

    @Override
    //czemu (String... voids)?????
    protected String doInBackground(String... voids) {

        // dodawanie elementow do tablicy voids
        String idLekarza = voids[0];
        IP = voids[1];

        String connstr = "http://"+IP+"/pobierzDaneDoProfiluLekarza.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(idLekarza,"UTF-8");
            writer.write(data);
            writer.flush(); // wysyła o co było napisane przez buffered writer
            writer.close(); // zamyka buffered writer
            ops.close(); // konczy tworzenie stringa do wyslania?????????


            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line ="";
            while ((line = reader.readLine()) != null)
            {
                result += line;
                Log.d("dupa","poco "+line);

            }
            reader.close();
            ips.close();
            http.disconnect();

            //zle sformuowany adres url
        } catch (MalformedURLException e) {
            result = e.getMessage();
            //blad odczytu?
        } catch (IOException e) {
            result = e.getMessage();

        }


        JSONArray arr = null;
        try {
            int i;
            arr = new JSONArray(result);
            String data;
            for (i = 0; i < arr.length(); i++) {
                data = arr.getJSONObject(i).getString("specjalizacja")+"  "+arr.getJSONObject(i).getString("imie")+"  "+arr.getJSONObject(i).getString("nazwisko");
                result=data;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;



    }

}
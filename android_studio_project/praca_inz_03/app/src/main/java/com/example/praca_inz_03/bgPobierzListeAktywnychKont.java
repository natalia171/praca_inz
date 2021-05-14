package com.example.praca_inz_03;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;


public class bgPobierzListeAktywnychKont extends AsyncTask<String, Void, LinkedHashMap<String,String>> {
    Context context;
    String result = "";
    String IP;

    List<String> list;

    public bgPobierzListeAktywnychKont(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {}


    @Override
    protected void onPostExecute(LinkedHashMap<String,String> s) {}

    @Override
    protected LinkedHashMap<String,String> doInBackground(String... voids) {
        IP = voids[0];
        String connstr = "http://"+IP+"/pobierzListeAktywnychKont.php";



        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            ops.close();


            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line ="";
            while ((line = reader.readLine()) != null)
            {
                result += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
        }

        catch (Exception e ){
        }

        LinkedHashMap<String,String> LinkedLekarzeAktywniHashMap= new LinkedHashMap<String,String>();
        JSONArray arr = null;
        try {
            int i;
            arr = new JSONArray(result);
            String key;
            String data;
            for (i = 0; i < arr.length(); i++){
                key = arr.getJSONObject(i).getString("ID");
                data = arr.getJSONObject(i).getString("imie")+"  "+arr.getJSONObject(i).getString("nazwisko");
                LinkedLekarzeAktywniHashMap.put( key,data );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return LinkedLekarzeAktywniHashMap;
    }

}

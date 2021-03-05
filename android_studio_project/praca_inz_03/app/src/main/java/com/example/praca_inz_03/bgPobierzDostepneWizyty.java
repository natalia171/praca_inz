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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class bgPobierzDostepneWizyty extends AsyncTask<String, Void, HashMap<String,String>> {
    Context context; // po co jest context??????


    String result = "";
    List<String> list;

    public bgPobierzDostepneWizyty(Context context) {
        this.context = context;
    }



    @Override
    protected void onPreExecute() {
    } //nic nie robi przed wykonaniem





    @Override
    protected void onPostExecute(HashMap<String,String> s) { //nic nie robi po wykoananiu
    }



    @Override
    protected HashMap<String,String> doInBackground(String... voids) {
        String specjalizacja = voids[0];

        String connstr = "http://192.168.0.18/pobierzDostepneWizyty.php";



        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream ops = http.getOutputStream(); //skad pobiera output stream i czym  on jest???????
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("specjalizacja","UTF-8")+"="+URLEncoder.encode(specjalizacja,"UTF-8");
            writer.write(data);
            writer.flush(); // wysyła o co było napisane przez buffered writer
            Log.d("bPDW","Uruchamia try 2");
            writer.close(); // zamyka buffered writer
            Log.d("bPDW","Uruchamia try 3");
            ops.close(); // konczy tworzenie stringa do wyslania?????????



            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));

            String line = "";
            while ((line = reader.readLine()) != null) {
                result += line;

            }
            reader.close();
            ips.close();
            http.disconnect();


            Log.d("bPDW","odczyt "+result);
        }

        catch (Exception e) {
            Log.d("bPDW", e.getMessage().toString());
        }

        HashMap<String,String> wizytyHashMap= new HashMap<String,String>();
        JSONArray arr = null;
        try {
            int i;
            arr = new JSONArray(result);
            String key;
            String data;
            for (i = 0; i < arr.length(); i++) {
                key = arr.getJSONObject(i).getString("ID");
                data = arr.getJSONObject(i).getString("imie")+"  "+arr.getJSONObject(i).getString("nazwisko")+"  "+arr.getJSONObject(i).getString("CZAS_START")+"  "+"ID= "+arr.getJSONObject(i).getString("ID");
                wizytyHashMap.put( key,data );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wizytyHashMap;
    }
}
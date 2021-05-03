package com.example.praca_inz_03;

import android.content.Context;
import android.os.AsyncTask;

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

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;


public class bgPobierzDostepneWizyty extends AsyncTask<String, Void, LinkedHashMap<String,String>> {
    Context context;

    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    String aktualnyCzas = sdf.format(new Date());
    String result = "";
    String IP;


    List<String> list;

    public bgPobierzDostepneWizyty(Context context) {

        this.context = context;
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onPostExecute(LinkedHashMap<String,String> s) {
    }



    @Override
    protected LinkedHashMap<String,String> doInBackground(String... voids) {
        String specjalizacja = voids[0];
        IP = voids[1];
        String connstr = "http://"+IP+"/pobierzDostepneWizyty.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("specjalizacja","UTF-8")+"="+URLEncoder.encode(specjalizacja,"UTF-8")
                    +"&&"+URLEncoder.encode("CZAS_START","UTF-8")+"="+URLEncoder.encode(aktualnyCzas,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();


            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));

            String line = "";
            while ((line = reader.readLine()) != null) {

                result += line;

            }
            reader.close();
            ips.close();
            http.disconnect();

        }

        catch (Exception e) {
        }





        LinkedHashMap<String,String> LinkedwizytyHashMap= new LinkedHashMap<String,String>();
        JSONArray arr = null;
        try {
            int i;
            arr = new JSONArray(result);
            String key;
            String data;
            for (i = 0; i < arr.length(); i++) {
                key = arr.getJSONObject(i).getString("ID");
                data = arr.getJSONObject(i).getString("imie")+"  "+arr.getJSONObject(i).getString("nazwisko")+"  "+arr.getJSONObject(i).getString("CZAS_START_FORMATED");
                LinkedwizytyHashMap.put( key,data );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return LinkedwizytyHashMap;
    }

}

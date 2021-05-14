package com.example.praca_inz_03;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


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
import java.util.LinkedHashMap;

public class bgPobierzDaneLekarzaDoZatwierdzenia extends AsyncTask<String, Void, LinkedHashMap<String, String>> {

    AlertDialog dialog;
    Context context;
    String IP;
    String result = "";
    public bgPobierzDaneLekarzaDoZatwierdzenia(Context context) {
        this.context = context;
    }



    @Override
    protected void onPostExecute(LinkedHashMap<String, String> s) {


    }

    @Override
    protected LinkedHashMap<String, String> doInBackground(String... voids) {
        IP = voids[0];
        String idLekarza = voids[1];


        String connstr = "http://"+IP+"/pobierzDaneLekarzaDoZatwierdzenia.php";

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
            writer.flush();
            writer.close();
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

        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();

        }


        LinkedHashMap<String,String> LinkedLekarzDoZatwierdzeniaHashMap= new LinkedHashMap<String,String>();
        JSONArray arr = null;
        try {
            int i;
            arr = new JSONArray(result);
            String key;
            String data;
            for (i = 0; i < arr.length(); i++){
                key = arr.getJSONObject(i).getString("ID");
                data = " Imię: "+arr.getJSONObject(i).getString("imie")
                        +"\n "+"Nazwisko: "+arr.getJSONObject(i).getString("nazwisko")
                        +"\n "+"Specjalizacja: "+arr.getJSONObject(i).getString("specjalizacja")
                        +"\n "+"E-mail: "+arr.getJSONObject(i).getString("email")
                        +"\n "+"PESEL: "+arr.getJSONObject(i).getString("pesel")
                        +"\n "+"Numer telefonu: "+arr.getJSONObject(i).getString("telefon");
                LinkedLekarzDoZatwierdzeniaHashMap.put( key,data );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return LinkedLekarzDoZatwierdzeniaHashMap;



    }

}
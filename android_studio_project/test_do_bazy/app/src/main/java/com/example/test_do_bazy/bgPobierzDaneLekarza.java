package com.example.test_do_bazy;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;


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

    AlertDialog dialog;
    Context context;
    String IP;
    String result = "";
    public bgPobierzDaneLekarza(Context context) {
        this.context = context;
    }



    @Override
    protected void onPostExecute(String s) {


    }

    @Override
    protected String doInBackground(String... voids) {

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


        JSONArray arr = null;
        try {
            int i;
            arr = new JSONArray(result);
            String data;
            for (i = 0; i < arr.length(); i++) {;
                data = arr.getJSONObject(i).getString("imie")+" "+arr.getJSONObject(i).getString("nazwisko")+" - "+arr.getJSONObject(i).getString("specjalizacja");
                result=data;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;



    }

}
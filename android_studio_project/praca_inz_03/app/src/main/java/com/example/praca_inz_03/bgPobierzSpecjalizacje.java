package com.example.praca_inz_03;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class bgPobierzSpecjalizacje extends AsyncTask<String, Void, List<String>> {
    Context context; // po co jest context??????


    String result = "";
    List<String> list;

    public bgPobierzSpecjalizacje(Context context)
    {
        this.context = context;
    }
    @Override
    protected void onPreExecute() {} //nic nie robi przed wykonaniem
    @Override
    protected void onPostExecute(List<String> s) { //nic nie robi po wykoananiu
    }

    @Override
    protected List<String> doInBackground(String... voids) {
        String connstr = "http://192.168.0.18/pobierzSpecjalizacje.php";
        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream(); //skad pobiera output stream i czym  on jest???????
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
            //Log.d("MW",result);
        }
        // po co ten catch???????
        catch (Exception e ){
            Log.d("bPS",e.getMessage().toString());
        };

        JSONArray arr = null;
        try {
            arr = new JSONArray(result);
            list = new ArrayList<String>();
            for(int i = 0; i < arr.length(); i++){
                list.add(arr.getJSONObject(i).getString("specjalizacja"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
        }

}

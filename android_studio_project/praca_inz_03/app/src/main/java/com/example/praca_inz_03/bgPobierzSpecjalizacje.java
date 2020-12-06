package com.example.praca_inz_03;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class bgPobierzSpecjalizacje extends AsyncTask<String, Void,String> {
    Context context;
    String result = "";
    public bgPobierzSpecjalizacje(Context context)
    {
        this.context = context;
    }
    @Override
    protected void onPreExecute() {}
    @Override
    protected void onPostExecute(String s) {
    }

    @Override
    protected String doInBackground(String... voids) {
        String connstr = "http://192.168.1.164/pobierzSpecjalizacje.php";
        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
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
                Log.d("MW",line);
            }
            reader.close();
            ips.close();
            http.disconnect();
            return result;
        }
        catch (Exception e ){};
        return result;
    }
}

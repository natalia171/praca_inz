package com.example.praca_inz_03;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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

public class bgRejestracja extends AsyncTask<String, Void,String> {
    AlertDialog dialog;
    Context context;
    String result = "";
    public Boolean login = false;

    public bgRejestracja(Context context)
    {
        this.context = context;
    }
    //Po wykonaniu (skad wie ze wykonal - wcisniecie buttona???) wraca do mainactivity
    @Override
    protected void onPostExecute(String s) {
            Intent intent_name = new Intent();
            // po co jest context i czym on jest???????
            intent_name.setClass(context.getApplicationContext(),MainActivity.class);
            context.startActivity(intent_name);
    }
    @Override
    protected String doInBackground(String... voids) {

        String email = voids[0];
        String imie= voids[1];
        String nazwisko = voids[2];
        String pesel= voids[3];
        String telefon = voids[4];
        String data_urodzenia= voids[5];
        String haslo = voids[6];
        String haslo1= voids[7];

        String connstr = "http://192.168.1.164/rejestracja.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")
                    +"&&"+URLEncoder.encode("imie","UTF-8")+"="+URLEncoder.encode(imie,"UTF-8")
                    +"&&"+URLEncoder.encode("nazwisko","UTF-8")+"="+URLEncoder.encode(nazwisko,"UTF-8")
                    +"&&"+URLEncoder.encode("pesel","UTF-8")+"="+URLEncoder.encode(pesel,"UTF-8")
                    +"&&"+URLEncoder.encode("telefon","UTF-8")+"="+URLEncoder.encode(telefon,"UTF-8")
                    +"&&"+URLEncoder.encode("data_urodzenia","UTF-8")+"="+URLEncoder.encode(data_urodzenia,"UTF-8")
                    +"&&"+URLEncoder.encode("haslo","UTF-8")+"="+URLEncoder.encode(haslo,"UTF-8")
                    +"&&"+URLEncoder.encode("haslo1","UTF-8")+"="+URLEncoder.encode(haslo1,"UTF-8");
            writer.write(data);
            Log.d("MW",data);
            writer.flush();
            writer.close();
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

        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }


        return result;

    }
}

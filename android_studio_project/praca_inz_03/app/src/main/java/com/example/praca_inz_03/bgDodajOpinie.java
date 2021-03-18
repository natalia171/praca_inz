package com.example.praca_inz_03;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class bgDodajOpinie extends AsyncTask <String, Void,String> {

    AlertDialog dialog;
    Context context;
    String IP;
    String result = "";


    public bgDodajOpinie(Context context) {
        this.context = context;
    }


    @Override
    protected void onPostExecute(String s) {

//        //tworzenie kolejnej aktywnosci - panelu pacjenta
//        Intent intent_name = new Intent();
//        intent_name.setClass(context.getApplicationContext(),PanelPacjenta.class);
//        intent_name.putExtra("idPacjenta",s);
//        intent_name.putExtra("IP",IP);
//        Log.d("id", "onPostExecute: ID w bg potwierdz wizyte: "+s);
//        context.startActivity(intent_name);
//        Toast toast= Toast.makeText(context,"Wizyta potwierdzona!",Toast.LENGTH_LONG);
//        toast.show();
    }

    @Override
    protected String doInBackground(String... voids) {

        String idLekarza = voids[0];
        String idPacjenta = voids[1];
        IP = voids[2];
        String trescOpinii = voids[3];

        String connstr = "http://"+IP+"/dodanieOpinii.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("ID_LEKARZA","UTF-8")+"="+URLEncoder.encode(idLekarza,"UTF-8")
                    +"&&"+URLEncoder.encode("ID_PACJENTA","UTF-8")+"="+URLEncoder.encode(idPacjenta,"UTF-8")
                    +"&&"+URLEncoder.encode("TRESC_OPINII","UTF-8")+"="+URLEncoder.encode(trescOpinii,"UTF-8");
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

        Log.d("bgPotwierzWizyte", "NoEx "+result);

        return idPacjenta;


    }
}
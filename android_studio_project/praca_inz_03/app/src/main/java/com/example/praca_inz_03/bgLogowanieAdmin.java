package com.example.praca_inz_03;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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


public class bgLogowanieAdmin extends AsyncTask <String, Void,String> {

    AlertDialog dialog;
    Context context;
    String result = "";
    String tab[]= new String[2];
    String ajdi;
    String IP;


    public bgLogowanieAdmin(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");
    }


    @Override
    protected void onPostExecute(String s) {

        if(s.contains("tak"))
        {
            Intent intent_name = new Intent();
            intent_name.setClass(context.getApplicationContext(),PanelAdmina.class);
            intent_name.putExtra("idAdmina",ajdi);
            intent_name.putExtra("IP",IP);

            context.startActivity(intent_name);

            Toast toast= Toast.makeText(context,"Zalogowano",Toast.LENGTH_LONG);
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.parseColor("#C39BD3"), PorterDuff.Mode.SRC_IN);
            TextView text = view.findViewById(android.R.id.message);
            text.setTextColor(Color.parseColor("#000000"));
            toast.show();


        }else{
            dialog.setMessage("Złe hasło lub login");
            dialog.show();
        }


    }
    @Override
    protected String doInBackground(String... voids) {

        String user = voids[0];
        String pass = voids[1];
        IP = voids[2];

        String connstr = "http://"+IP+"/loginAdmin.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")
                    +"&&"+URLEncoder.encode("haslo","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");

            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line ="";
            int i=0;
            while ((line = reader.readLine()) != null)
            {
                tab[i]=line;
                i++;
                result += line;

            }
            reader.close();
            ips.close();
            http.disconnect();
            ajdi=tab[1];

        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }


        return result;


    }
}
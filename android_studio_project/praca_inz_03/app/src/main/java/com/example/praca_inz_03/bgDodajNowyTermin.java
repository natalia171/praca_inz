package com.example.praca_inz_03;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class bgDodajNowyTermin extends AsyncTask <String, Void,String> {

    AlertDialog dialog;
    Context context;
    String IP;
    String result = "";
    public bgDodajNowyTermin(Context context) {
        this.context = context;
    }


    @Override
    protected void onPostExecute(String s) {

        Intent intent_name = new Intent();
        intent_name.setClass(context.getApplicationContext(),PanelLekarza.class);
        intent_name.putExtra("idLekarza",s);
        intent_name.putExtra("IP",IP);
        context.startActivity(intent_name);

        Toast toast= Toast.makeText(context,"Dodano termin!",Toast.LENGTH_LONG);
        View view = toast.getView();
        view.getBackground().setColorFilter(Color.parseColor("#C39BD3"), PorterDuff.Mode.SRC_IN);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor("#000000"));
        toast.show();
    }

    @Override
    protected String doInBackground(String... voids) {

        String idLekarza = voids[0];
        IP = voids[1];
        String poczatekWizyty = voids[2];
        String koniecWizyty = voids[3];

        String connstr = "http://"+IP+"/dodajTerminLekarz.php";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date czas_od =null;
        Date czas_do = null;
        Date czas_od_mod =null;
        Date czas_do_mod = null;
        try {
            czas_od = sdf.parse(poczatekWizyty);
            czas_do = sdf.parse(koniecWizyty);
        } catch (Exception ex) {
        }

        int kwadrans=1000*60*15;
        int iloscKwadransow = (int) ((czas_do.getTime() - czas_od.getTime()) /(kwadrans));


        String s_data_od= null;
        String s_data_do= null;


        for (int i=0;i<iloscKwadransow;i++) {
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                czas_od_mod = new Date(czas_od.getTime()+i*kwadrans);
                czas_do_mod = new Date(czas_od.getTime()+(i+1)*kwadrans);
                s_data_od=sdf.format(czas_od_mod);
                s_data_do=sdf.format(czas_do_mod);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("CZAS_START","UTF-8")+"="+URLEncoder.encode(s_data_od,"UTF-8")
                        +"&&"+ URLEncoder.encode("CZAS_STOP","UTF-8")+"="+URLEncoder.encode(s_data_do,"UTF-8")
                        +"&&"+URLEncoder.encode("ID_LEKARZA","UTF-8")+"="+URLEncoder.encode(idLekarza,"UTF-8");
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
        }

        return idLekarza;

    }
}
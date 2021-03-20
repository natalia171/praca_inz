package com.example.praca_inz_03;
//Branch nowy

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
import java.text.SimpleDateFormat;
import java.util.Date;

public class bgDodajNowyTermin extends AsyncTask <String, Void,String> {

    //utworzenie okienka dialogowego
    AlertDialog dialog;
    Context context;
    String IP;
    //stworzenie pustego stringa wynikowego
    String result = "";
    // String idPacjenta;
    public bgDodajNowyTermin(Context context) {
        this.context = context;
    }


    @Override
    // funkcja po wykonaniu ??????
    protected void onPostExecute(String s) {

        //tworzenie kolejnej aktywnosci - panelu pacjenta
        Intent intent_name = new Intent();
        intent_name.setClass(context.getApplicationContext(),PanelLekarza.class);
        intent_name.putExtra("idLekarza",s);
        intent_name.putExtra("IP",IP);
        Log.d("id", "onPostExecute: ID w bg dodaj nowy termin: "+s);
        context.startActivity(intent_name);
        Toast toast= Toast.makeText(context,"Termin dodany!",Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    //czemu (String... voids)?????
    protected String doInBackground(String... voids) {

        // dodawanie elementow do tablicy voids
        String idLekarza = voids[0];
        IP = voids[1];
        String poczatekWizyty = voids[2];
        String koniecWizyty = voids[3];

        String connstr = "http://"+IP+"/dodajTerminLekarz.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date czas_od =null;
            Date czas_do = null;
            try {
                czas_od = sdf.parse(poczatekWizyty);
                czas_do = sdf.parse(koniecWizyty);
            } catch (Exception ex) {
                Log.v("Exception", ex.getLocalizedMessage());
            }

            int iloscKwadransow = (int) ((czas_do.getTime() - czas_od.getTime()) /(1000*60*15));
            int kwadrans=1000*60*15;
            String s_data_od= null;
            String s_data_do= null;
            for (int i=0;i<=iloscKwadransow;i++) {

                s_data_od= String.valueOf(czas_do.getTime()+(i*kwadrans));
                s_data_do= String.valueOf(czas_od.getTime()+(i*kwadrans));
//                s_data_do=czas_od+(i*kwadrans);


                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));

                String data = URLEncoder.encode("CZAS_START", "UTF-8") + "=" + URLEncoder.encode(s_data_od, "UTF-8")
                        + "&&" + URLEncoder.encode("CZAS_STOP", "UTF-8") + "=" + URLEncoder.encode(s_data_do, "UTF-8")
                        + "&&" + URLEncoder.encode("ID_LEKARZA", "UTF-8") + "=" + URLEncoder.encode(idLekarza, "UTF-8");
                Log.d("15min", data);
                writer.write(data);
                writer.flush(); // wysyła o co było napisane przez buffered writer
                writer.close(); // zamyka buffered writer
                ops.close(); // konczy tworzenie stringa do wyslania?????????


                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                    Log.d("bgPotwierzWizyte", "Odpowiedz " + line);
                }
                reader.close();
                ips.close();
                http.disconnect();
            }
//koniec petli po kwadransach

            //zle sformuowany adres url
        } catch (MalformedURLException e) {
            result = e.getMessage();
            //blad odczytu?
            Log.d("bgPotwierzWizyte", "Ex1 "+result);

        } catch (IOException e) {
            result = e.getMessage();
            Log.d("bgPotwierzWizyte", "Ex2 "+result);

        }

        Log.d("bgPotwierzWizyte", "NoEx "+result);

        return idLekarza;


    }
}
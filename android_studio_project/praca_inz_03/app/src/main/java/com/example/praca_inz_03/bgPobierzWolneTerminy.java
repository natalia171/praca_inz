package com.example.praca_inz_03;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;


import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;


public class bgPobierzWolneTerminy extends AsyncTask<String, Void, LinkedHashMap<String,String>> {
    Context context; // po co jest context??????

    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    String aktualnyCzas = sdf.format(new Date());
    String result = "";
    String IP;

    List<String> list;

    public bgPobierzWolneTerminy(Context context)
    {
        this.context = context;
    }



    @Override
    protected void onPreExecute() {} //nic nie robi przed wykonaniem






    @Override
    protected void onPostExecute(LinkedHashMap<String,String> s) { //nic nie robi po wykoananiu
    }



    @Override
    protected LinkedHashMap<String,String> doInBackground(String... voids) {
        String idLekarza = voids[0];
        IP = voids[1];
        String connstr = "http://"+IP+"/wolneTerminyLekarz.php";



        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream(); //skad pobiera output stream i czym  on jest???????
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("ID_LEKARZA","UTF-8")+"="+URLEncoder.encode(idLekarza,"UTF-8")
                    +"&&"+URLEncoder.encode("CZAS","UTF-8")+"="+URLEncoder.encode(aktualnyCzas,"UTF-8");
            writer.write(data);
            writer.flush(); // wysyła o co było napisane przez buffered writer

            writer.close(); // zamyka buffered writer

            ops.close();


            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line ="";
            while ((line = reader.readLine()) != null)
            {
                result += line;
                Log.d("bgPobierzWolneTerminy","Odpowiedz "+line);
            }
            reader.close();
            ips.close();
            http.disconnect();


            //Log.d("MW",result);
        }

        catch (Exception e ){
            Log.d("bPWT",e.getMessage().toString());
        }

        LinkedHashMap<String,String> LinkedPWTHashMap= new LinkedHashMap<String,String>();
        JSONArray arr = null;
        try {
            int i;
            arr = new JSONArray(result);
            String key;
            String data;
            Date data_wolnego_terminu;
            String dzien_tygodnia;
            for (i = 0; i < arr.length(); i++){
                String date = arr.getJSONObject(i).getString("CZAS_START_FORMAT");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                Log.d("dzien0", date);
                data_wolnego_terminu = format.parse(date);
                dzien_tygodnia=  new SimpleDateFormat("EE").format(data_wolnego_terminu);

                Log.d("dzien", dzien_tygodnia + " # "+data_wolnego_terminu);
                key = arr.getJSONObject(i).getString("ID");
                data = arr.getJSONObject(i).getString("CZAS_START_FORMATED")+" ( "+ dzien_tygodnia + " )";
                LinkedPWTHashMap.put( key,data );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return LinkedPWTHashMap;
    }

}

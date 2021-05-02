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
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.Date;
import java.util.List;
import java.util.Locale;


public class bgPobierzHistorieWizyt extends AsyncTask<String, Void, List<String>> {
    Context context; // po co jest context??????

    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    String aktualnyCzas = sdf.format(new Date());
    String IP;
    String result = "";

    List<String> list;

    public bgPobierzHistorieWizyt(Context context)
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
        String idPacjenta = voids[0];
        IP = voids[1];

        String connstr = "http://"+IP+"/historiaWizyt.php";



        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream(); //skad pobiera output stream i czym  on jest???????
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("ID_PACJENTA","UTF-8")+"="+URLEncoder.encode(idPacjenta,"UTF-8")
                    +"&&"+URLEncoder.encode("CZAS_START","UTF-8")+"="+URLEncoder.encode(aktualnyCzas,"UTF-8");
            writer.write(data);
            writer.flush(); // wysyła o co było napisane przez buffered writer
            Log.d("bPDW","Uruchamia try 2");
            writer.close(); // zamyka buffered writer
            Log.d("bPDW","Uruchamia try 3");
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

                list.add(arr.getJSONObject(i).getString("imie")+" "+arr.getJSONObject(i).getString("nazwisko")+" - "+arr.getJSONObject(i).getString("specjalizacja")+"\n "+arr.getJSONObject(i).getString("CZAS_START_FORMATED"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }

}

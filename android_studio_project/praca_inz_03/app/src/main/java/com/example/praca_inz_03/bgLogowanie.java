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


public class bgLogowanie extends AsyncTask <String, Void,String> {

    //utworzenie okienka dialogowego
    AlertDialog dialog;
    Context context;
    //stworzenie pustego stringa wynikowego
    String result = "";
    //stworzenie zmiennej typu boolean domyslnie falszywej
    public Boolean login = false;

    //???????
    public bgLogowanie(Context context)
    {
        this.context = context;
    }

    @Override
    //funkcja przed wykonaniem????
    protected void onPreExecute() {
        //stworzenie okienka dialogowego z tytulem "login status"
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");
    }


    @Override
    // funkcja po wykonaniu ??????
    protected void onPostExecute(String s) {
        //skąd zna "S"???????
        if(s.contains("1"))
        {
            //tworzenie kolejnej aktywnosci - panelu pacjenta
            Intent intent_name = new Intent();
            intent_name.setClass(context.getApplicationContext(),PanelPacjenta.class);
            context.startActivity(intent_name);
        }else{
            dialog.setMessage("Złe hasło lub login");
            dialog.show();
        }

    }
    @Override
    //czemu (String... voids)?????
    protected String doInBackground(String... voids) {

        //??????? czym jest voids??
        String user = voids[0];
        String pass = voids[1];

        String connstr = "http://192.168.1.164/login.php";

        try {
            //stworzenie nowego połączenia do strony internetowej???
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            //ustawienie metody post do przekazywania elementow
            //????? czemu post a nie get
            http.setRequestMethod("GET");
            //????????włączenie otrzymywania danych???
            http.setDoInput(true);
            //????włączenie wysyłania danych??
            http.setDoOutput(true);

            //stworzenie stringa do wysłania?? czemu getoutputstream??
            OutputStream ops = http.getOutputStream();
            // po co jest buffered writer?
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            //skoro pobrał string przez getoutputstream to po co tworzyc string data
            String data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")
                    +"&&"+URLEncoder.encode("haslo","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
            writer.write(data);
            Log.d("MW",data);
            writer.flush(); // wysyła o co było napisane przez buffered writer
            writer.close(); // zamyka buffered writer
            ops.close(); // konczy tworzenie stringa do wyslania?????????

            //tworzenie stringa do odbioru?????????
            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            //tworzenie pustego stringa "line"
            String line ="";
            //????????? skad czyta linie?
            while ((line = reader.readLine()) != null)
            {
                // skad jest result?
                result += line;
                Log.d("bgL",line);
            }
            reader.close();
            ips.close();
            http.disconnect();
            return result;

            //po co te catche????????
        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }


        return result;
    }
}
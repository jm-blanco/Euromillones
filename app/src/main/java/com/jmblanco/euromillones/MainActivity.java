package com.jmblanco.euromillones;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    String[] numeros = new String[10];
    String[] elementos = new String[5];
    int vueltas = 0;
    int z = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestPermissions(new String[]{"android.permission.CAMERA"}, 123);


        Button bt_main = findViewById(R.id.bt_main);
        bt_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Euromillones.class);
                if (vueltas == 0) {
                    try {
                        numeros = new UltimoSorteo().execute().get();
                        vueltas++;
                    } catch (InterruptedException | ExecutionException e) {
                        Reporte.writer(e.getMessage(),MainActivity.this.getApplicationContext());
                    }
                }
                intent.putExtra("numeros", numeros);
                startActivityForResult(intent, 0);
            }
        });


    }


    // Clase para obtener los tags de la bbdd
    private class Elementos extends AsyncTask<Void,Void,String[]>{
        public String[] doInBackground(Void... nada) {
            HttpURLConnection connection2 = null;
            BufferedReader    reader2     = null;
            String line = "";
            String[] ret = new String[6];
            try {
                String dir  = "https://loteriasfinal.000webhostapp.com/elements.php";
                connection2 = (HttpURLConnection) new URL(dir).openConnection();
                connection2.connect();
                reader2   = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                int count = 0;
                while (true){
                    line = reader2.readLine();
                    if (line == null) {
                        break;
                    }
                    ret[count]=line;
                    count++;
                }
                connection2.disconnect();
                reader2.close();
            }catch (IOException e2) {
                    Reporte.writer(e2.getMessage(),MainActivity.this.getApplicationContext());
            }
            return ret;
        }
        public void onPostExecute(String[] result) {
            super.onPostExecute(result);
        }
    }


    private class UltimoSorteo extends AsyncTask<Void, Void, String[]> {
        public String[] doInBackground(Void... nada) {
            try {
                // obtenemos los tags de la bbdd
                elementos = new Elementos().execute().get();
            } catch (ExecutionException | InterruptedException e) {
                Reporte.writer(e.getMessage(),MainActivity.this.getApplicationContext());
            }
            try {
                Element ele = Jsoup.connect(elementos[0]).get().getElementsByClass(elementos[1]).first();
                for (Element element : ele.getElementsByClass(elementos[2])) {
                    numeros[MainActivity.this.z] = element.text();
                    z++;
                }
                for (Element element : ele.getElementsByClass(elementos[3])) {
                    numeros[MainActivity.this.z] = element.text();
                    z++;
                }
                for (Element element : ele.getElementsByClass(elementos[4])) {
                    numeros[MainActivity.this.z] = element.text();
                    z++;
                }
                numeros[8] = Jsoup.connect(elementos[0]).get().getElementsByClass(elementos[5])
                        .first().getElementsByTag("strong").text();
                return numeros;
            } catch (IOException e) {
                Reporte.writer(e.getMessage(),MainActivity.this.getApplicationContext());
                numeros[0] = e.getMessage();
                return numeros;
            }
        }
        public void onPostExecute(String[] result) {
            super.onPostExecute(result);
        }
    }
}

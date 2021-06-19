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
import java.io.IOException;
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
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestPermissions(new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"}, 123);

        Button bt_main = findViewById(R.id.bt_main);
        bt_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Euromillones.class);
                if (vueltas == 0) {
                    try {
                        elementos = new ElementosBBDD().execute().get();
                        numeros   = new UltimoSorteo().execute(elementos).get();
                        vueltas++;
                    } catch (InterruptedException | ExecutionException e) {
                        Reporte.writer(e.getMessage(),getApplicationContext());
                    }
                }
                intent.putExtra("numeros", numeros);
                startActivityForResult(intent, 0);
            }
        });


    }


    // Clase para obtener los tags de la bbdd
    private class ElementosBBDD extends AsyncTask<Void,Void,String[]>{
        @Override
        public String[] doInBackground(Void... nada) {
            String[] x = new String[6];
            try {
                String ele = Jsoup.connect("https://loteriasfinal.000webhostapp.com/elements.php").get().body().text();
                x = ele.split(" ");
            }catch (IOException e2) {
                Reporte.writer(e2.getMessage(),getApplicationContext());
            }
            return x;
        }
        @Override
        public void onPostExecute(String[] result) {
            super.onPostExecute(result);
        }
    }


    private class UltimoSorteo extends AsyncTask<String[], Void, String[]> {
        @Override
        public String[] doInBackground(String[]... nada) {
            String[] elementos2 = nada[0];
            try {
                Element ele = Jsoup.connect(elementos2[0]).get().getElementsByClass(elementos2[1]).first();
                for (Element element : ele.getElementsByClass(elementos2[2])) {
                    numeros[z] = element.text();
                    z++;
                }
                for (Element element : ele.getElementsByClass(elementos2[3])) {
                    numeros[z] = element.text();
                    z++;
                }
                for (Element element : ele.getElementsByClass(elementos2[4])) {
                    numeros[z] = element.text();
                    z++;
                }
                numeros[8] = Jsoup.connect(elementos2[0]).get().getElementsByClass(elementos2[5])
                        .first().getElementsByTag("strong").text();
            } catch (IOException e) {
                Reporte.writer(e.getMessage(),getApplicationContext());
                numeros[0] = e.getMessage();
            }
            return numeros;
        }
        @Override
        public void onPostExecute(String[] result) {
            super.onPostExecute(result);
        }
    }
}

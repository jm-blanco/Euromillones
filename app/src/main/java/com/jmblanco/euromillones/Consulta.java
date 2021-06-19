package com.jmblanco.euromillones;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

public class Consulta extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TextView tv_Res = findViewById(R.id.tv_conres);
        String token = "";
        String mes = "";


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            token = bundle.getString("resultado");
        } else {
            token = "";
        }
        bundle.clear();
        String[] a = token.split(";");
        int juegos = 0;
        if (a.length > 0) {
            juegos = a[4].length() / 18;
        }

        String num = a[2].substring(5, 12);
        StringBuilder sb = new StringBuilder();
        sb.append("20");
        sb.append(num.substring(5));
        String anio = sb.toString();
        String dia = num.substring(0, 2);
        String mes2 = num.substring(2, 5);
        String mus = num.substring(2, 5);
        String[] mes1 = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
        String[] mes22 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int i = 0;
        while (true) {
            if (i >= 12) {
                mes = mes2;
                break;
            }
            if (mes1[i].equals(mes2)) {
                mes = mes22[i];
                break;
            } else {
                i++;
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(anio);
        sb2.append("-");
        sb2.append(mes);
        sb2.append("-");
        String dia2 = dia;
        sb2.append(dia2);
        String url = "https://loteriasfinal.000webhostapp.com/consulta.php?fecha=" + sb2;
        JSONObject ss = null;
        try {
            ss = new JsonTask2().execute(url).get();
        } catch (InterruptedException | ExecutionException e3) {
            Reporte.writer(e3.getMessage(), Consulta.this.getApplicationContext());
        }
        if (ss != null) {
            try {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(" FECHA:         ");
                sb3.append(dia2);
                sb3.append("-");
                sb3.append(mus);
                sb3.append("-");
                sb3.append(anio);
                String t = sb3.toString();
                String y = "\n NÚMEROS:   " + ss.getString("numeros").replace(" ", "·");
                StringBuilder sb4 = new StringBuilder();
                sb4.append("\n ESTRELLAS: ");
                sb4.append(ss.getString("estrellas").replace(" ", " y "));
                String h = sb4.toString();
                if (y.endsWith("·")) {
                    y = y.substring(0, y.length() - 1);
                    tv_Res.setText(t + y + h);
                }
            } catch (JSONException s) {
                Reporte.writer(s.getMessage(), Consulta.this.getApplicationContext());
            }
        } else {
            tv_Res.setText("\n    SORTEO SIN CELEBRAR \n");
        }
        String[] cn = new String[juegos];
        String[] cm = new String[juegos];
        if (ss != null) {
            try {
                String bbdd = ss.getString("numeros");
                String star = ss.getString("estrellas");
                cn = bbdd.trim().split(" ");
                cm = star.trim().split(" ");
            } catch (JSONException e13) {
                Reporte.writer(e13.getMessage(), Consulta.this.getApplicationContext());
            }
        }

        int[] iArr = new int[2];
        iArr[1] = 10;
        iArr[0] = juegos;
        int[][] numes = (int[][]) Array.newInstance(int.class, iArr);
        int[] iArr2 = new int[2];
        iArr2[1] = 2;
        iArr2[0] = juegos;
        int[][] estre = (int[][]) Array.newInstance(int.class, iArr2);
        int i2 = 0;
        while (i2 < juegos) {
            char[] g = a[4].substring((i2 * 18) + 3, (i2 * 18) + 13).toCharArray();
            numes[i2][0] = Integer.parseInt(g[0] + "" + g[1]);
            numes[i2][1] = Integer.parseInt(g[2] + "" + g[3]);
            numes[i2][2] = Integer.parseInt(g[4] + "" + g[5]);
            numes[i2][3] = Integer.parseInt(g[6] + "" + g[7]);
            numes[i2][4] = Integer.parseInt(g[8] + "" + g[9]);
            i2++;
        }
        for (int i3 = 0; i3 < juegos; i3++) {
            estre[i3][0] = Integer.parseInt(a[4].substring((i3 * 18) + 14, (i3 * 18) + 16));
            estre[i3][1] = Integer.parseInt(a[4].substring((i3 * 18) + 16, (i3 * 18) + 18));
        }
        String tuno = "";
        String tdos = "";
        String ttres = "";
        String tcuatro = "";
        String tcinco = "";
        int aciertosn = 0;
        int aciertose = 0;
        int j = 0;
        while (j < juegos) {
            int k = 0;
            while (true) {
                if (k >= 5) {
                    break;
                }
                int i4 = 0;
                for (int aciertose2 = 5; i4 < aciertose2; aciertose2 = 5) {
                    if (cn[k].equals(numes[j][i4] + "")) {
                        aciertosn++;
                    }
                    i4++;
                    k++;
                }
                int k2 = 0;
                int aciertose3 = aciertose;
                while (true) {
                    if (k2 >= 2) {
                        break;
                    }
                    int i5 = 0;
                    for (int i6 = 2; i5 < i6; i6 = 2) {
                        if (cm[k2].equals(estre[j][i5] + "")) {
                            aciertose3++;
                        }
                        i5++;
                    }
                    k2++;
                }
                tuno = tuno + "Aciertos juego " + (j + 1) + ": \n";
                tdos = tdos + aciertosn + "\n";
                ttres = ttres + " números \n";
                tcuatro = tcuatro + aciertose3 + "\n";
                tcinco = tcinco + " estrellas\n";
                aciertosn = 0;
                aciertose = 0;
                j++;
            }
        }

        Button bt_vcon = findViewById(R.id.bt_volcon);
        bt_vcon.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                finish();
            }
        });

    }

    private class JsonTask2 extends AsyncTask<String, String, JSONObject> {
        @Override
        public JSONObject doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader        reader = null;
            JSONObject        jSONObject = null;
            try {
                connection = (HttpURLConnection) new URL(params[0]).openConnection();
                connection.connect();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    buffer.append(line);
                    buffer.append("\n");
                }
                jSONObject = new JSONObject(buffer.toString());
                connection.disconnect();
                reader.close();
            } catch (IOException | JSONException e2) {
                Reporte.writer(e2.getMessage(),Consulta.this.getApplicationContext());
            }
            return jSONObject;
        }
        @Override
        public void onPostExecute(JSONObject result) {
            super.onPostExecute( result);
        }
    }
}

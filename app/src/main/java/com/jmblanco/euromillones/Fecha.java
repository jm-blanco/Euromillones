package com.jmblanco.euromillones;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

public class Fecha extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        CalendarView cal = findViewById(R.id.cal_v);
        cal.setMaxDate(cal.getDate());
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public final void onSelectedDayChange(CalendarView calendarView, int i, int i2, int i3) {
                TextView tv_fech = findViewById(R.id.tv_res_fecha);
                buscar(tv_fech, calendarView, i, i2, i3);
            }
        });

        Button bt_volfech = findViewById(R.id.bo_vol_fecha);
        bt_volfech.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void buscar(TextView tv_cal, CalendarView view, int year, int month, int day) {
        String mes;
        String dia;
        int i;
        String[] f;
        int i2;
        int[] ese;
        int[] ese2 = {year, month + 1, day};
        String anio = ese2[0] + "-";
        if (ese2[1] < 10) {
            mes = "0" + ese2[1] + "-";
        } else {
            mes = ese2[1] + "-";
        }
        if (ese2[2] < 10) {
            dia = "0" + ese2[2];
        } else {
            dia = ese2[2] + "";
        }
        String url = "https://loteriasfinal.000webhostapp.com/consulta.php?fecha=" + (anio + mes + dia);
        JSONObject ss = null;
        JsonTask jsonTask = new JsonTask();
        String[] strArr = new String[1];
        i = 0;
        try {
            strArr[0] = url;
            ss = jsonTask.execute(strArr).get();
        } catch (InterruptedException | ExecutionException e) {
            Reporte.writer(e.getMessage(),getApplicationContext());
        }
        if (ss != null) {
            try {
                String t = " FECHA:         " + dia + "-" + mes + year;
                String[] f2 = ss.getString("numeros").split(" ");
                String j = "";
                int length = f2.length;
                int i3 = i;
                while (i3 < length) {
                    String value = f2[i3];
                    if (value.startsWith("0")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(j);
                        sb.append(" ");
                        ese = ese2;
                        sb.append(value.replace("0", ""));
                        j = sb.toString();
                    } else {
                        ese = ese2;
                        j = j + " " + value;
                    }
                    i3++;
                    ese2 = ese;
                }
                String y = "\n NÚMEROS:   " + j.trim().replace(" ", "·");
                String[] r = ss.getString("estrellas").split(" ");
                int length2 = r.length;
                String q = "";
                int i4 = i;
                while (i4 < length2) {
                    String s = r[i4];
                    if (s.startsWith("0")) {
                        i2 = length2;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(q);
                        sb2.append(" ");
                        f = f2;
                        sb2.append(s.replace("0", ""));
                        q = sb2.toString();
                    } else {
                        i2 = length2;
                        f = f2;
                        q = q + " " + s;
                    }
                    i4++;
                    length2 = i2;
                    f2 = f;
                }
                tv_cal.setText(t + y + ("\n ESTRELLAS: " + q.trim().replace(" ", " y ")));
            } catch (JSONException e6) {
                Reporte.writer(e6.getMessage(),Fecha.this.getApplicationContext());
            }
        } else {
            tv_cal.setText("\n    EN ESA FECHA NO HUBO SORTEO \n");
        }
    }


    public class JsonTask extends AsyncTask<String, String, JSONObject> {
        @Override
        public JSONObject doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            JSONObject jSONObject = null;
            try {
                connection = (HttpURLConnection) new URL(params[0]).openConnection();
                connection.connect();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer buffer = new StringBuffer();
                String line = reader.readLine();
                buffer.append(line + "\n");
                if (!line.equals("null")) {
                    jSONObject = new JSONObject(buffer.toString());
                    return jSONObject;
                }
                connection.disconnect();
                reader.close();
            }
            catch (IOException | JSONException e3) {
                    Reporte.writer(e3.getMessage(),Fecha.this.getApplicationContext());
            }
            return jSONObject;
        }
        @Override
        public void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
        }
    }
}
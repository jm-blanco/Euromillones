package com.jmblanco.euromillones;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Auxiliar extends AppCompatActivity {
    public String insert(Bundle bundle) {
        String num7;
        String num72;
        String num6;
        String num5;
        String num4;
        String num3;
        String num2;
        String[] numbers;
        if (bundle == null || (numbers = bundle.getStringArray("numeros")) == null) {
            num7 = "";
            num72 = "";
            num6 = "";
            num5 = "";
            num4 = "";
            num3 = "";
            num2 = "";
        } else {
            String num1 = numbers[0];
            String num22 = numbers[1];
            String num32 = numbers[2];
            String num42 = numbers[3];
            String num52 = numbers[4];
            String num62 = numbers[5];
            num7 = numbers[6];
            num72 = num62;
            num6 = num52;
            num5 = num42;
            num4 = num32;
            num3 = num22;
            num2 = num1;
        }
        String fech = getFecha();
        String ins = "https://loteriasfinal.000webhostapp.com/insert.php?fecha=" + fech + "&numeros=" + (num2 + " " + num3 + " " + num4 + " " + num5 + " " + num6) + "&estrellas=" + (num72 + " " + num7);
        try {
            if (new JsonTask().execute("https://loteriasfinal.000webhostapp.com/consulta.php?fecha=" + fech).get() == null) {
                return ins;
            }
            return "";
        } catch (InterruptedException | ExecutionException e) {
            return "";
        }
    }

    public static String getFecha() {
        Date z = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        switch (z.getDay() + 1) {
            case 1:
                z.setDate(z.getDate() - 2);
                return s.format(z);
            case 2:
                z.setDate(z.getDate() - 3);
                return s.format(z);
            case 3:
                if (z.getHours() < 22) {
                    z.setDate(z.getDate() - 4);
                    return s.format(z);
                }
                if (z.getHours() >= 22) {
                    return s.format(z);
                }
                z.setDate(z.getDate() - 1);
                return s.format(z);
            case 4:
                z.setDate(z.getDate() - 1);
                return s.format(z);
            case 5:
                z.setDate(z.getDate() - 2);
                return s.format(z);
            case 6:
                if (z.getHours() < 22) {
                    z.setDate(z.getDate() - 3);
                    return s.format(z);
                }
                if (z.getHours() >= 22) {
                    return s.format(z);
                }
                z.setDate(z.getDate() - 1);
                return s.format(z);
            case 7:
                z.setDate(z.getDate() - 1);
                return s.format(z);
            default:
                return "";
        }
    }

    private class JsonTask extends AsyncTask<String, String, JSONObject> {
        public JSONObject doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                HttpURLConnection connection2 = (HttpURLConnection) new URL(params[0]).openConnection();
                connection2.connect();
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                StringBuffer buffer = new StringBuffer();
                while (true) {
                    String line = reader2.readLine();
                    if (line == null) {
                        break;
                    }
                    buffer.append(line);
                    buffer.append("\n");
                }
                JSONObject jSONObject = new JSONObject(buffer.toString());
                if (connection2 != null) {
                    connection2.disconnect();
                }
                try {
                    reader2.close();
                } catch (IOException e) {
                }
                return jSONObject;
            } catch (IOException | JSONException e2) {
                if (0 != 0) {
                    connection.disconnect();
                }
                if (0 == 0) {
                    return null;
                }
                try {
                    reader.close();
                    return null;
                } catch (IOException e3) {
                    return null;
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    connection.disconnect();
                }
                if (0 != 0) {
                    try {
                        reader.close();
                    } catch (IOException e4) {
                    }
                }
                throw th;
            }
        }

        public void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
        }
    }
}

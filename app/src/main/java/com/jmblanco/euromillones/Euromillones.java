package com.jmblanco.euromillones;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class Euromillones extends AppCompatActivity {
    int t = 0;

    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle;
        String[] numbers;
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_euromillones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle bun = getIntent().getExtras();
        String web = "";
        WebView webView = findViewById(R.id.wb_pp);
        try {
            web = new Auxiliar().insert(bun);
            if (web != null) {
                webView.loadUrl(web);
            }
        } catch (Exception e) {
            Reporte.writer(e.getMessage(),Euromillones.this.getApplicationContext());
        }
        String web2 = web;
        TextView tv_b1   = findViewById(R.id.tv_b1);
        TextView tv_b2   = findViewById(R.id.tv_b2);
        TextView tv_b3   = findViewById(R.id.tv_b3);
        TextView tv_b4   = findViewById(R.id.tv_b4);
        TextView tv_b5   = findViewById(R.id.tv_b5);
        TextView tv_e1   = findViewById(R.id.tv_e1);
        TextView tv_e2   = findViewById(R.id.tv_e2);
        TextView tv_mill = findViewById(R.id.tv_mill);
        TextView tv_bote = findViewById(R.id.tv_bote);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            String[] numbers2 = bundle2.getStringArray("numeros");
            if (numbers2 != null) {
                tv_b1.setText(numbers2[0]);
                tv_b2.setText(numbers2[1]);
                tv_b3.setText(numbers2[2]);
                tv_b4.setText(numbers2[3]);
                tv_b5.setText(numbers2[4]);
                tv_e1.setText(numbers2[5]);
                tv_e2.setText(numbers2[6]);
                StringBuilder sb = new StringBuilder();
                String str = web2;
                sb.append("EL MILLÓN: ");
                bundle = bundle2;
                sb.append(numbers2[7].substring(9));
                tv_mill.setText(sb.toString());
                tv_bote.setText("BOTE: " + numbers2[8] + " MILLONES");
            } else {
                bundle = bundle2;
                tv_b1.setText("0");
                tv_b2.setText("0");
                tv_b3.setText("0");
                tv_b4.setText("0");
                tv_b5.setText("0");
                tv_e1.setText("0");
                tv_e2.setText("0");
                tv_mill.setText("EL MILLÓN: 0");
                tv_bote.setText("BOTE: 0 MILLONES");
            }
            numbers = numbers2;
        } else {
            bundle = bundle2;
            numbers = null;
        }

        Button bt_gen = findViewById(R.id.bt_gen);
        bt_gen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Euromillones.this.numerosRandom();
                Euromillones.this.estrellasRandom();
            }
        });

        final String[] finalNumbers2 = numbers;
        Bundle bun2   = bun;
        Bundle bundle3 = bundle;
        TextView textView = tv_bote;
        TextView tv_bote2 = tv_b1;
        TextView textView2 = tv_mill;
        TextView tv_mill2 = tv_b2;
        TextView tv_e22 = tv_e2;
        TextView tv_e23 = tv_b3;
        TextView tv_e12 = tv_e1;
        TextView tv_e13 = tv_b4;
        TextView tv_b52 = tv_b5;
        TextView tv_b42 = tv_b4;
        TextView tv_b43 = tv_e12;
        TextView tv_b32 = tv_b3;
        TextView tv_b33 = tv_e22;

        Button btqr = findViewById(R.id.bt_qr);
        btqr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Euromillones.this.checkPermiso("android.permission.CAMERA", 100);
                Euromillones.this.startActivityForResult(new Intent(v.getContext(), Scanner.class), 0);
                tv_bote2.setText(finalNumbers2[0]);
                tv_mill2.setText(finalNumbers2[1]);
                tv_e23.setText(finalNumbers2[2]);
                tv_e13.setText(finalNumbers2[3]);
                tv_b5.setText(finalNumbers2[4]);
                tv_b43.setText(finalNumbers2[5]);
                tv_b33.setText(finalNumbers2[6]);
            }
        });
        final String[] finalNumbers1 = numbers;
        final TextView textView3 = tv_b1;
        final TextView textView4 = tv_b2;
        final TextView textView5 = tv_b32;
        TextView tv_b22 = tv_b2;
        final TextView tv_b23 = tv_b42;
        TextView tv_b12 = tv_b1;
        final TextView tv_b13 = tv_b52;
        WebView webView2 = webView;
        final TextView textView6 = tv_e12;
        Button button2 = btqr;
        Bundle bundle4 = bun2;
        final TextView textView7 = tv_e22;

        Button bt_ant = findViewById(R.id.bt_ant);
        bt_ant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Euromillones.this.startActivityForResult(new Intent(v.getContext(), Fecha.class), 0);
                textView3.setText(finalNumbers1[0]);
                textView4.setText(finalNumbers1[1]);
                textView5.setText(finalNumbers1[2]);
                tv_b23.setText(finalNumbers1[3]);
                tv_b13.setText(finalNumbers1[4]);
                textView6.setText(finalNumbers1[5]);
                textView7.setText(finalNumbers1[6]);
            }
        });

        Button bt_out = findViewById(R.id.bt_salir);
        bt_out.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Euromillones.this.getApplicationContext(), "¡Hasta pronto!", Toast.LENGTH_SHORT).show();
                Euromillones.this.finishAffinity();
            }
        });

        final String[] finalNumbers = numbers;
        final TextView textView8 = tv_b12;
        final TextView textView9 = tv_b22;
        final TextView textView10 = tv_b32;
        final TextView textView11 = tv_b42;
        final TextView textView12 = tv_b52;
        final TextView textView13 = tv_e12;
        final TextView textView14 = tv_e22;
        ImageView iv_hide = findViewById(R.id.iv_eur);
        iv_hide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Euromillones.this.t++;
                if (Euromillones.this.t == 10) {
                    Euromillones.this.t = 0;
                    Euromillones.this.startActivityForResult(new Intent(v.getContext(), Oculto.class), 0);
                    textView8.setText(finalNumbers[0]);
                    textView9.setText(finalNumbers[1]);
                    textView10.setText(finalNumbers[2]);
                    textView11.setText(finalNumbers[3]);
                    textView12.setText(finalNumbers[4]);
                    textView13.setText(finalNumbers[5]);
                    textView14.setText(finalNumbers[6]);
                }
            }
        });
    }

    public void checkPermiso(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == -1) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    public void estrellasRandom() {
        Random rng = new Random();
        Set<Integer> generado = new LinkedHashSet<>();
        while (generado.size() < 2) {
            generado.add(Integer.valueOf(rng.nextInt(12) + 1));
        }
        Object[] ob2 = generado.toArray();
        Arrays.sort(ob2);
        ((TextView) findViewById(R.id.tv_e1)).setText(ob2[0] + "");
        ((TextView) findViewById(R.id.tv_e2)).setText(ob2[1] + "");
    }

    public void numerosRandom() {
        Random rng = new Random();
        Set<Integer> generated = new LinkedHashSet<>();
        while (generated.size() < 5) {
            generated.add(Integer.valueOf(rng.nextInt(50) + 1));
        }
        Object[] ob = generated.toArray();
        Arrays.sort(ob);
        ((TextView) findViewById(R.id.tv_b1)).setText(ob[0] + "");
        ((TextView) findViewById(R.id.tv_b2)).setText(ob[1] + "");
        ((TextView) findViewById(R.id.tv_b3)).setText(ob[2] + "");
        ((TextView) findViewById(R.id.tv_b4)).setText(ob[3] + "");
        ((TextView) findViewById(R.id.tv_b5)).setText(ob[4] + "");
    }
}
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
            numbers = bundle2.getStringArray("numeros");
            if (numbers != null) {
                tv_b1.setText(numbers[0]);
                tv_b2.setText(numbers[1]);
                tv_b3.setText(numbers[2]);
                tv_b4.setText(numbers[3]);
                tv_b5.setText(numbers[4]);
                tv_e1.setText(numbers[5]);
                tv_e2.setText(numbers[6]);
                StringBuilder sb = new StringBuilder();
                String str = web2;
                sb.append("EL MILLÓN: ");
                bundle = bundle2;
                sb.append(numbers[7].substring(9));
                tv_mill.setText(sb.toString());
                tv_bote.setText("BOTE: " + numbers[8] + " MILLONES");
            } else {
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
        } else {
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

        Button btqr = findViewById(R.id.bt_qr);
        btqr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Euromillones.this.checkPermiso("android.permission.CAMERA", 100);
                Euromillones.this.startActivityForResult(new Intent(v.getContext(), Scanner.class), 0);
                reinicio(numbers);
            }
        });

        Button bt_ant = findViewById(R.id.bt_ant);
        bt_ant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Euromillones.this.startActivityForResult(new Intent(v.getContext(), Fecha.class), 0);
                reinicio(numbers);
            }
        });

        Button bt_out = findViewById(R.id.bt_salir);
        bt_out.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Euromillones.this.getApplicationContext(), "¡Hasta pronto!", Toast.LENGTH_SHORT).show();
                Euromillones.this.finishAffinity();
            }
        });

        ImageView iv_hide = findViewById(R.id.iv_eur);
        iv_hide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Euromillones.this.t++;
                if (Euromillones.this.t == 10) {
                    Euromillones.this.t = 0;
                    Euromillones.this.startActivityForResult(new Intent(v.getContext(), Oculto.class), 0);
                    reinicio(numbers);
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

    public void reinicio(String[] numbers2){
        TextView tv_b1   = findViewById(R.id.tv_b1);
        TextView tv_b2   = findViewById(R.id.tv_b2);
        TextView tv_b3   = findViewById(R.id.tv_b3);
        TextView tv_b4   = findViewById(R.id.tv_b4);
        TextView tv_b5   = findViewById(R.id.tv_b5);
        TextView tv_e1   = findViewById(R.id.tv_e1);
        TextView tv_e2   = findViewById(R.id.tv_e2);
        TextView tv_mill = findViewById(R.id.tv_mill);
        TextView tv_bote = findViewById(R.id.tv_bote);
        tv_b1.setText(numbers2[0]);
        tv_b2.setText(numbers2[1]);
        tv_b3.setText(numbers2[2]);
        tv_b4.setText(numbers2[3]);
        tv_b5.setText(numbers2[4]);
        tv_e1.setText(numbers2[5]);
        tv_e2.setText(numbers2[6]);
    }

}
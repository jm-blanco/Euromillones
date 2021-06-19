package com.jmblanco.euromillones;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Oculto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oculto);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        WebView  wb = findViewById(R.id.wb);
        EditText ef = findViewById(R.id.et_f);
        EditText en = findViewById(R.id.et_n);
        EditText ee = findViewById(R.id.et_e);
        EditText ep = findViewById(R.id.et_psw);
        RadioButton ins = findViewById(R.id.rb_insert);
        RadioButton del = findViewById(R.id.rb_delete);

        Button bt_enviar = findViewById(R.id.bt_env);
        bt_enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ins.isChecked()) {
                    String url = "https://loteriasfinal.000webhostapp.com/insert.php?fecha=" + ef.getText().toString() + "&numeros=" + en.getText().toString() + "&estrellas=" + ee.getText().toString();
                    if (ep.getText().toString().equals("000721")) {
                        wb.loadUrl(url);
                        Toast.makeText(getApplicationContext(), "inserted\n into database", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "invalid password", Toast.LENGTH_SHORT).show();
                    }
                    ef.setText("");
                    en.setText("");
                    ee.setText("");
                    ep.setText("");
                }
                else if (del.isChecked()) {
                    String url2 = "https://loteriasfinal.000webhostapp.com/delete.php?fecha=" + ef.getText().toString();
                    if (ep.getText().toString().equals("000721")) {
                        wb.loadUrl(url2);
                        Toast.makeText(getApplicationContext(), "delete row", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "invalid password", Toast.LENGTH_SHORT).show();
                    }
                    ef.setText("");
                    en.setText("");
                    ee.setText("");
                    ep.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(), "no option checked", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button bt_volver = findViewById(R.id.bt_oculvol);
        bt_volver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
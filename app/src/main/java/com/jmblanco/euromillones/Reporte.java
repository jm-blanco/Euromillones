package com.jmblanco.euromillones;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import java.io.*;
import static android.widget.Toast.LENGTH_LONG;

// Clase para sacar un txt con errores
public class Reporte {
    public static void writer(String error,Context context) {
        try{
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/music", "log.txt");
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {}
            }
            BufferedWriter buf = new BufferedWriter(new FileWriter(f, true));
            buf.append(error);
            buf.newLine();
            buf.close();
        }catch (IOException e) {
                Toast.makeText(context.getApplicationContext(), e.getMessage(), LENGTH_LONG).show();
        }
    }
}

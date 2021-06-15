package com.jmblanco.euromillones;

import android.content.Context;
import android.widget.Toast;
import java.io.*;
import static android.widget.Toast.LENGTH_LONG;

// Clase para sacar un txt con errores
public class Reporte {
    public static void writer(String error,Context context){
        try {
            File f = new File("./reporte.txt");
            FileOutputStream fos = new FileOutputStream(f, true);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeUTF(error);
            dos.close();
            fos.close();
        }catch(IOException e){
            Toast.makeText(context.getApplicationContext(), e.getMessage(), LENGTH_LONG).show();
        }
    }
}

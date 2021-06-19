package com.jmblanco.euromillones;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class Scanner extends AppCompatActivity {
    private CameraSource cameraSource;
    private SurfaceView cameraView;
    private String token = "";
    private String tokenanterior = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cameraView = findViewById(R.id.camera_view);
        initQR();

        Button bt_qqr = findViewById(R.id.bt_sc);
        bt_qqr.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                finish();
            }
        });

    }


    private void initQR() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getApplicationContext()).setBarcodeFormats(0).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1024, 768)
                .setFacing(0)
                .setFocusMode("FOCUS_MODE_CONTINUOUS_VIDEO")
                .setAutoFocusEnabled(true)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(Scanner.this.getApplicationContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED)
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    Reporte.writer(e.getMessage(),Scanner.this.getApplicationContext());
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    token = barcodes.valueAt(0).displayValue;
                    if (!token.equals(tokenanterior)) {
                        tokenanterior = token;
                        Intent shareIntent = new Intent(Scanner.this.getApplicationContext(), Consulta.class);
                        shareIntent.putExtra("resultado", token);
                        startActivityForResult(shareIntent, 0);
                    }
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                synchronized (this) {
                                    wait(3000);
                                    tokenanterior = "";
                                }
                            } catch (InterruptedException e) {
                                Reporte.writer(e.getMessage(),Scanner.this.getApplicationContext());
                            }
                        }
                    }).start();
                }
            }
        });

    }
}
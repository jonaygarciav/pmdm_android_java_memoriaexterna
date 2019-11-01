package com.example.memoriaexterna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private String filename = "file_mem_ext.txt";

    private boolean sdDisponible = false;
    private boolean sdAccesoEscritura = false;

    private Button bt_check;
    private Button bt_write;
    private Button bt_read;
    private TextView tv_file_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Elements of Activity
        bt_check = findViewById(R.id.button_check);
        bt_write = findViewById(R.id.button_write);
        bt_read = findViewById(R.id.button_read);
        tv_file_content = findViewById(R.id.textview_file_content);

        bt_check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                String toast_mensaje = "";

                //Comprobamos el estado de la memoria externa (tarjeta SD)
                String estado = Environment.getExternalStorageState();

                if (estado.equals(Environment.MEDIA_MOUNTED))
                {
                    sdDisponible = true;
                    sdAccesoEscritura = true;

                    toast_mensaje = "ME con acceso de lectura y escritura";
                }
                else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
                {
                    sdDisponible = true;
                    sdAccesoEscritura = false;

                    toast_mensaje = "ME con acceso sólo de escritura";
                }
                else
                {
                    sdDisponible = false;
                    sdAccesoEscritura = false;

                    toast_mensaje = "ME no disponible";
                }

                Toast toast1 = Toast.makeText(getApplicationContext(), toast_mensaje, Toast.LENGTH_LONG);
                toast1.show();

            }
        });

        bt_write.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                try
                {
                    File ruta_sd = Environment.getExternalStorageDirectory();

                    File f = new File(ruta_sd.getAbsolutePath(), filename);

                    OutputStreamWriter fout =
                            new OutputStreamWriter(
                                    new FileOutputStream(f));

                    fout.write("Escribiendo en un fichero en la memoria externa.");
                    fout.close();

                    String mensaje = "Fichero " + filename + " creado correctamente.";
                    Toast toast1 = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    toast1.show();

                    bt_write.setEnabled(false);
                }
                catch (Exception ex)
                {
                    Log.e("Write SD", ex.getMessage());
                }

            }
        });

        bt_read.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                try
                {
                    File ruta_sd = Environment.getExternalStorageDirectory();

                    File f = new File(ruta_sd.getAbsolutePath(), filename);

                    BufferedReader fin =
                            new BufferedReader(
                                    new InputStreamReader(
                                            new FileInputStream(f)));

                    String texto = fin.readLine();
                    fin.close();

                    tv_file_content.setText(texto);
                }
                catch (Exception ex)
                {
                    Log.e("Read SD", ex.getMessage());
                }

            }
        });
    }
}

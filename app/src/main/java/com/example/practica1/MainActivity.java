package com.example.practica1;

import Gestor.ComprobarExiste;
import Gestor.GestorSesion;
import Gestor.IniciarSesion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainActivity extends AppCompatActivity {

    private boolean existe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        editTextEmail.setHint(R.string.hintEmail);

        EditText editTextContrasena = findViewById(R.id.editTextContraseña);
        editTextContrasena.setHint(R.string.hintContrasena);

        // Configuración de los elementos del layout
        TextView btnClickRegistrar = findViewById(R.id.btnClickRegistrar);
        btnClickRegistrar.setText(R.string.textSinCuenta);
        btnClickRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a la actividad de registro
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Botón para iniciar sesión
        Button btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setText(R.string.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener datos de los input
                String email = "" + editTextEmail.getText();

                String contrasena = "" + editTextContrasena.getText();

                MainActivity.this.existe = false;

                // LLama al primer método para comprobar si existe el email
                MainActivity.this.comprobarExiste(email, contrasena);

            }
        });
    }

    @Override
    public void onBackPressed() {
        // Cambiar la funcionalidad del botón Back
        // Muestra un diálogo preguntando si quieres salir
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.textConfirmarCerrar).setPositiveButton(R.string.textSi, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Si dice que sí, se cierra la actividad
                finish();
            }
        }).setNegativeButton(R.string.textNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Si dice que no, se cierra el Dialog y no hace nada
            }
        });
        builder.show();
    }

    public void iniciarSesion(String email, String contrasena) {
        // Petición http para iniciar sesión
        Data data = new Data.Builder().putString("email", email).putString("contrasena", contrasena).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(IniciarSesion.class).setInputData(data).build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null && workInfo.getState().isFinished()){
                            // Parsear el resultado
                            Data outputData = workInfo.getOutputData();
                            String resultado = outputData.getString("resultado");
                            JSONParser parser = new JSONParser();
                            try {
                                JSONObject json = (JSONObject) parser.parse(resultado);
                                boolean exito = (boolean) json.get("exito");
                                // Si las credenciales son correctas, entra en la app, si no muestra un toast
                                if(exito) {
                                    GestorSesion.getGestorSesion().setEmail(email);
                                    // El inicio de sesión es correcto, se accede a la actividad Home
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Las credenciales no son correctas, se muestra un Toast
                                    Toast.makeText(MainActivity.this, R.string.toastErrorCredenciales, Toast.LENGTH_SHORT).show();
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    public void comprobarExiste (String email, String contrasena) {
        // Petición http para comprobar si el usuario ya existe
        Data data = new Data.Builder().putString("email", email).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ComprobarExiste.class).setInputData(data).build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null && workInfo.getState().isFinished()){
                            Data outputData = workInfo.getOutputData();
                            String resultado = outputData.getString("resultado");
                            JSONParser parser = new JSONParser();
                            try {
                                // Parsea el resultado
                                JSONObject json = (JSONObject) parser.parse(resultado);
                                boolean existe = (boolean) json.get("existe");
                                // Si el usuario existe, llama al método de iniciar sesión
                                if (existe) {
                                    MainActivity.this.iniciarSesion(email, contrasena);
                                } else {
                                    // Toast de error porque no existe el usuario
                                    Toast.makeText(MainActivity.this, R.string.toastErrorCredenciales, Toast.LENGTH_SHORT).show();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }
}
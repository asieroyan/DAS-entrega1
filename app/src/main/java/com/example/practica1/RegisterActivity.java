package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Gestor.ComprobarExiste;
import Gestor.GestorUsuarios;
import Gestor.IniciarSesion;
import Gestor.Registrarse;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Configuración de los elementos del layout
        EditText editTextEmail = findViewById(R.id.editTextEmail2);
        editTextEmail.setHint(R.string.hintEmail);

        EditText editTextContrasena = findViewById(R.id.editTextContraseña2);
        editTextContrasena.setHint(R.string.hintContrasena);

        EditText editTextRepiteContrasena = findViewById(R.id.editTextConfirmarContraseña);
        editTextRepiteContrasena.setHint(R.string.hintRepiteContrasena);

        TextView tituloRegistro = findViewById(R.id.textRegister);
        tituloRegistro.setText(R.string.textRegistro);

        Button btnVolverLogin = findViewById(R.id.btnVolverLogin);
        btnVolverLogin.setText(R.string.btnVolverLogin);
        btnVolverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Termina la actividad
                finish();
            }
        });

        Button btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setText(R.string.btnRegistrarse);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Recoge los datos de los input
                EditText editTextEmail = findViewById(R.id.editTextEmail2);
                EditText editTextContrasena = findViewById(R.id.editTextContraseña2);
                EditText editTextConfirmarContrasena = findViewById(R.id.editTextConfirmarContraseña);
                String email = "" + editTextEmail.getText();
                String contrasena = "" + editTextContrasena.getText();
                String confirmarContrasena = "" + editTextConfirmarContrasena.getText();

                //Comprueba que el email tiene un formato correcto
                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher matcher = pattern.matcher(email);

                if (!matcher.find()) {
                    // Si el email no tiene un formato correcto, muestra un Toast
                    Toast.makeText(RegisterActivity.this, R.string.toastFormatoEmail, Toast.LENGTH_SHORT).show();
                } else {
                    if (contrasena.length() < 8) {
                        // Si la contrasña no tiene los 8 caracteres necesarios, muestra un Toast
                        Toast.makeText(RegisterActivity.this, R.string.toastCaracteresContrasena, Toast.LENGTH_SHORT).show();
                    } else {
                        if (!contrasena.equals(confirmarContrasena)) {
                            // Si las contraseñas no son iguales, muestra un Toast
                            Toast.makeText(RegisterActivity.this, R.string.toastContrasenasCoinciden, Toast.LENGTH_SHORT).show();
                        } else {
                            RegisterActivity.this.comprobarExiste(email, contrasena);
                        }
                    }
                }
            }
        });
    }

    public void registrarse(String email, String contrasena) {
        Data data = new Data.Builder().putString("email", email).putString("contrasena", contrasena).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(Registrarse.class).setInputData(data).build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Data outputData = workInfo.getOutputData();
                            boolean anadido = outputData.getBoolean("anadido", false);
                            if (anadido) {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.putExtra("email", email);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    public void comprobarExiste (String email, String contrasena) {
        Data data = new Data.Builder().putString("email", email).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ComprobarExiste.class).setInputData(data).build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null && workInfo.getState().isFinished()){
                            Data outputData = workInfo.getOutputData();
                            boolean existe = outputData.getBoolean("existe", false);
                            if (existe) {
                                Toast.makeText(RegisterActivity.this, R.string.toastUsuarioExiste, Toast.LENGTH_SHORT).show();
                            } else {

                                RegisterActivity.this.registrarse(email, contrasena);
                            }

                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }
}
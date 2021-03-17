package com.example.practica1;

import Gestor.GestorUsuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView btnClickRegistrar = findViewById(R.id.btnClickRegistrar);
        btnClickRegistrar.setText(R.string.textSinCuenta);
        btnClickRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setText(R.string.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textEmail = findViewById(R.id.editTextEmail);
                String email = "" + textEmail.getText();

                TextView textContrasena = findViewById(R.id.editTextContraseña);
                String contrasena = "" + textContrasena.getText();

                int codigo = GestorUsuarios.getGestorUsuarios().login(MainActivity.this, email, contrasena);
                if (codigo == 0) {
                    Toast.makeText(MainActivity.this, R.string.toastErrorBD, Toast.LENGTH_SHORT).show();
                } else if (codigo == 1) {
                    Toast.makeText(MainActivity.this, R.string.toastErrorCredenciales, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.textConfirmarCerrar).setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.show();
    }
}
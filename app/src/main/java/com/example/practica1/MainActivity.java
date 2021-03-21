package com.example.practica1;

import Gestor.GestorUsuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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

        Button btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setText(R.string.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener datos de los input
                String email = "" + editTextEmail.getText();

                String contrasena = "" + editTextContrasena.getText();

                // Recibe el código del gestor de usuarios con los datos datos
                int codigo = GestorUsuarios.getGestorUsuarios().login(MainActivity.this, email, contrasena);
                if (codigo == 0) {
                    // Ha ocurrido un error con la BD, se muestra un Toast
                    Toast.makeText(MainActivity.this, R.string.toastErrorBD, Toast.LENGTH_SHORT).show();
                } else if (codigo == 1) {
                    // Las credenciales no son correctas, se muestra un Toast
                    Toast.makeText(MainActivity.this, R.string.toastErrorCredenciales, Toast.LENGTH_SHORT).show();
                } else {
                    // El inicio de sesión es correcto, se accede a la actividad Home
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                }
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
}
package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Gestor.GestorUsuarios;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnVolverLogin = findViewById(R.id.btnVolverLogin);
        btnVolverLogin.setText(R.string.btnVolverLogin);
        btnVolverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setText(R.string.btnRegistrarse);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextEmail = findViewById(R.id.editTextEmail2);
                EditText editTextContrasena = findViewById(R.id.editTextContraseña2);
                EditText editTextConfirmarContrasena = findViewById(R.id.editTextConfirmarContraseña);
                String email = "" + editTextEmail.getText();
                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher matcher = pattern.matcher(email);
                String contrasena = "" + editTextContrasena.getText();
                String confirmarContrasena = "" + editTextConfirmarContrasena.getText();
                if (!matcher.find()) {
                    Toast.makeText(RegisterActivity.this, R.string.toastFormatoEmail, Toast.LENGTH_SHORT).show();
                } else {
                    if (contrasena.length() < 8) {
                        Toast.makeText(RegisterActivity.this, R.string.toastCaracteresContrasena, Toast.LENGTH_SHORT).show();
                    } else {
                        if (!contrasena.equals(confirmarContrasena)) {
                            Toast.makeText(RegisterActivity.this, R.string.toastContrasenasCoinciden, Toast.LENGTH_SHORT).show();
                        } else {
                            int codigo = GestorUsuarios.getGestorUsuarios().anadirNuevoUsuario(RegisterActivity.this, email, contrasena);
                            if (codigo == 0) {
                                Toast.makeText(RegisterActivity.this, R.string.toastErrorBD, Toast.LENGTH_SHORT).show();
                            } else if (codigo == 1){
                                Toast.makeText(RegisterActivity.this, R.string.toastUsuarioExiste, Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }
}
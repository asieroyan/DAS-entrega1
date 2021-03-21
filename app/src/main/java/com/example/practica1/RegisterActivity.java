package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Gestor.GestorUsuarios;

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
                            // Si el formato de los datos es correcto, llama al gestor de anuncios para añadir el usuario y recibe un código de resultado
                            int codigo = GestorUsuarios.getGestorUsuarios().anadirNuevoUsuario(RegisterActivity.this, email, contrasena);
                            if (codigo == 0) {
                                // Ha habido un error al conectarse a la BD y muestra un Toast
                                Toast.makeText(RegisterActivity.this, R.string.toastErrorBD, Toast.LENGTH_SHORT).show();
                            } else if (codigo == 1){
                                // El email proporcionado ya está en la BD y devuelve un Toast
                                Toast.makeText(RegisterActivity.this, R.string.toastUsuarioExiste, Toast.LENGTH_SHORT).show();
                            } else {
                                // Todo es correcto y el usuario se ha añadido a la BD
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }
}
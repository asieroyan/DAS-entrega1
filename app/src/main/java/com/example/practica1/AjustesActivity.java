package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class AjustesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        LocaleList locale=getBaseContext().getResources().getConfiguration().getLocales(); //Obtengo el idioma actual de la aplicacion
        String idiomaApp=locale.get(0).toString();

        TextView textIdiomas = findViewById(R.id.textIdiomas);
        textIdiomas.setText(R.string.settingsIdioma);

        Button btnEspanol = findViewById(R.id.btnEspanol);
        btnEspanol.setText(R.string.español);
        btnEspanol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locale.get(0).toString().equals("es")){}
                else {
                    cambiarIdioma("ESP");
                }
            }
        });

        Button btnEuskera = findViewById(R.id.btnEuskera);
        btnEuskera.setText(R.string.euskera);
        btnEuskera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locale.get(0).toString().equals("eu")){}
                else {
                    cambiarIdioma("EUS");
                }
            }
        });

        Button btnEnglish = findViewById(R.id.btnEnglish);
        btnEnglish.setText(R.string.english);
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locale.get(0).toString().equals("en") || locale.get(0).toString().equals("en_GB")){}
                else {
                    cambiarIdioma("ENG");
                }
            }
        });

    }
    protected void onRestoreInstanceState(Bundle savedInstanceState){ //Si cambio a horizontal
        super.onRestoreInstanceState(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cambiarIdioma(prefs.getString("idiomaApp","DEF"));
    }
    private void cambiarIdioma(String idioma){
        Locale nuevaloc= new Locale("es"); //Por defecto español
        if (idioma.equals("ENG")){ //Si el idioma es ingles
            nuevaloc = new Locale("en","GB");
        } else if (idioma.equals("EUS")){ //Si el idioma es ingles
            nuevaloc = new Locale("eu","ES");
        }
        Locale.setDefault(nuevaloc);
        Configuration configuration =
                getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);
        Context context =
                getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =  new Intent(AjustesActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
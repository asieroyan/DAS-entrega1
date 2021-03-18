package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Gestor.GestorAnuncios;
import Modelo.Anuncio;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setText(R.string.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnAjustes = findViewById(R.id.btnAjustes);
        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        final ListView list = findViewById(R.id.listAnunciosAjenos);
        GestorAnuncios.getGestorAnuncios().cargarAnuncios(HomeActivity.this);
        ArrayList<Anuncio> listAnuncios =GestorAnuncios.getGestorAnuncios().getAnuncios();
        ArrayAdapter<Anuncio> arrayAdapter = new ArrayAdapter<Anuncio>(this, android.R.layout.simple_list_item_1, listAnuncios);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Anuncio anuncio = listAnuncios.get(position);
                Intent intent = new Intent(HomeActivity.this, AnuncioActivity.class);
                intent.putExtra("fotourl", anuncio.getFotourl());
                intent.putExtra("titulo", anuncio.getTitulo());
                intent.putExtra("descripcion", anuncio.getContacto());
                intent.putExtra("contacto", anuncio.getContacto());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

}
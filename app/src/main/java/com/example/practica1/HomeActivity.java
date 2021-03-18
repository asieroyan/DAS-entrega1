package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Gestor.GestorAnuncios;
import Gestor.GestorConstantes;
import Modelo.Anuncio;

public class HomeActivity extends AppCompatActivity {

    private boolean datosCargados = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        String email = getIntent().getExtras().getString("email");
        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setText(R.string.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        Button btnAnadirAnuncio = findViewById(R.id.btnAnadirAnuncio);
        btnAnadirAnuncio.setText(R.string.btnAnadirAnuncio);
        btnAnadirAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AnadirAnuncioActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                // finish();
            }
        });

        final ListView list = findViewById(R.id.listAnunciosAjenos);
        GestorAnuncios.getGestorAnuncios().cargarAnuncios(HomeActivity.this);
        ArrayList<Anuncio> listAnuncios = GestorAnuncios.getGestorAnuncios().getAnuncios();
        ArrayAdapter<Anuncio> arrayAdapter = new ArrayAdapter<Anuncio>(this, android.R.layout.simple_list_item_1, listAnuncios);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Anuncio anuncio = listAnuncios.get(position);
                Intent intent = new Intent(HomeActivity.this, AnuncioActivity.class);
                intent.putExtra("fotourl", anuncio.getFotourl());
                intent.putExtra("titulo", anuncio.getTitulo());
                intent.putExtra("descripcion", anuncio.getDescripcion());
                intent.putExtra("contacto", anuncio.getContacto());
                startActivity(intent);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Anuncio anuncio = listAnuncios.get(position);
                // Comprobar si el usuario actual es admin o ha creado este anuncio para permitir borrarlo
                if (email.equals(anuncio.getEmailAnunciante()) || email.equals("as@as.as")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setMessage(R.string.textConfirmarCerrar).setPositiveButton(R.string.textSi, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            GestorAnuncios.getGestorAnuncios().eliminarAnuncio(HomeActivity.this, anuncio);
                            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton(R.string.textNo, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    builder.show();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

}
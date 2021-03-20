package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Adaptador.AdaptadorListView;
import Gestor.GestorAnuncios;
import Gestor.GestorConstantes;
import Modelo.Anuncio;

public class HomeActivity extends AppCompatActivity {

    private boolean datosCargados = false;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        email = getIntent().getStringExtra("email");

        final ListView list = findViewById(R.id.listAnunciosAjenos);
        GestorAnuncios.getGestorAnuncios().cargarAnuncios(HomeActivity.this);
        String[] titulos = GestorAnuncios.getGestorAnuncios().getTitulos();
        String[] descripciones = GestorAnuncios.getGestorAnuncios().getDescripciones();
        String[] fotosUrl = GestorAnuncios.getGestorAnuncios().getFotosUrl();
        String[] contactos = GestorAnuncios.getGestorAnuncios().getContactos();
        ArrayList<Anuncio> listAnuncios = GestorAnuncios.getGestorAnuncios().getAnuncios();
        AdaptadorListView arrayAdapter = new AdaptadorListView(this, titulos, descripciones, fotosUrl, contactos);
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
                    builder.setMessage(R.string.textEliminarAnuncio).setPositiveButton(R.string.textSi, new DialogInterface.OnClickListener() {
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
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAjustes:
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuAnadirAnuncio:
                Intent intent1 = new Intent(HomeActivity.this, AnadirAnuncioActivity.class);
                intent1.putExtra("email", email);
                startActivity(intent1);
                return true;
            case R.id.menuCerrarSesion:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
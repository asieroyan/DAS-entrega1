package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        String fotourl = getIntent().getExtras().getString("fotourl");
        String titulo = getIntent().getExtras().getString("titulo");
        String descripcion = getIntent().getExtras().getString("descripcion");
        String contacto = getIntent().getExtras().getString("contacto");

        TextView textTituloAnuncio = findViewById(R.id.textTituloAnuncio);
        textTituloAnuncio.setText(titulo);

        TextView textDescripcion = findViewById(R.id.textDescripcionAnuncio);
        textDescripcion.setText(descripcion);

        ImageView imageAnuncio = findViewById(R.id.imageAnuncio);
        // imageAnuncio.setImageURI(fotourl);

        TextView textTituloContacto = findViewById(R.id.textTituloContacto);
        textTituloContacto.setText(R.string.textTituloContacto);

        TextView textContacto = findViewById(R.id.textContacto);
        textContacto.setText(contacto);
    }
}
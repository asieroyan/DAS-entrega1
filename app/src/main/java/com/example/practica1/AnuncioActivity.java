package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

public class AnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);
        // Recibe parámetros de la actividad Home
        String foto = getIntent().getExtras().getString("foto");
        String titulo = getIntent().getExtras().getString("titulo");
        String descripcion = getIntent().getExtras().getString("descripcion");
        String contacto = getIntent().getExtras().getString("contacto");

        // Configuración de los elementos del layout
        TextView textTituloAnuncio = findViewById(R.id.textTituloAnuncio);
        textTituloAnuncio.setText(titulo);

        TextView textDescripcion = findViewById(R.id.textDescripcionAnuncio);
        textDescripcion.setText(descripcion);

        ImageView imageAnuncio = findViewById(R.id.imageAnuncio);
        byte[] decodedString = Base64.decode(foto, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageAnuncio.setImageBitmap(decodedByte);

        TextView textTituloContacto = findViewById(R.id.textTituloContacto);
        textTituloContacto.setText(R.string.textTituloContacto);

        TextView textContacto = findViewById(R.id.textContacto);
        textContacto.setText(contacto);
    }
}
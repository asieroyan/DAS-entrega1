package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Gestor.GestorAnuncios;

public class AnadirAnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_anuncio);

        TextView textTituloAnadirAnuncio = findViewById(R.id.textTituloAnadirAnuncio);
        textTituloAnadirAnuncio.setText(R.string.textTituloAnadirAnuncio);

        EditText editTextTitulo = findViewById(R.id.editTextTituloAnadirAnuncio);
        editTextTitulo.setHint(R.string.hintTituloAnuncio);

        EditText editTextDescripcionAnuncio = findViewById(R.id.editTextDescripcionAnuncio);
        editTextDescripcionAnuncio.setHint(R.string.hintDescripcion);

        EditText editTextFotourl = findViewById(R.id.editTextFotourl);
        editTextFotourl.setHint(R.string.hintUrlFoto);

        EditText editTextContactoAnuncio = findViewById(R.id.editTextContactoAnuncio);
        editTextContactoAnuncio.setHint(R.string.hintContacto);

        Button btnConfirmarAnuncio = findViewById(R.id.btnConfirmarAnuncio);
        btnConfirmarAnuncio.setText(R.string.btnConfirmarAnuncio);
        btnConfirmarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = "" + editTextTitulo.getText();
                String descripcion = "" + editTextDescripcionAnuncio.getText();
                String fotourl = "" + editTextFotourl.getText();
                String contacto = "" + editTextContactoAnuncio.getText();
                String email = getIntent().getExtras().getString("email");

                GestorAnuncios.getGestorAnuncios().anadirAnuncio(AnadirAnuncioActivity.this, titulo, descripcion, fotourl, contacto, email);

                NotificationManager elManager = (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getApplicationContext(), "IdCanal");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel elCanal = new NotificationChannel("IdCanal", "NombreCanal", NotificationManager.IMPORTANCE_DEFAULT);
                    elManager.createNotificationChannel(elCanal);
                }
                elBuilder.setSmallIcon(android.R.drawable.stat_sys_upload_done)
                        .setContentTitle(getResources().getString(R.string.notifTitle))
                        .setContentText(getResources().getString(R.string.notifText))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
                elManager.notify(1, elBuilder.build());
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AnadirAnuncioActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
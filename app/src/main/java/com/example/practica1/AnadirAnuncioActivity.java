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

// Actividad que representa la pantalla de añadir un nuevo anuncio y su funcionalidad
public class AnadirAnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_anuncio);

        // Configuración de los cuadros de texto y de input
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

        // Funcionalidad del botón de añadir un anuncio.
        Button btnConfirmarAnuncio = findViewById(R.id.btnConfirmarAnuncio);
        btnConfirmarAnuncio.setText(R.string.btnConfirmarAnuncio);
        btnConfirmarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recoger los datos de los inputs de texto
                String titulo = "" + editTextTitulo.getText();
                String descripcion = "" + editTextDescripcionAnuncio.getText();
                String fotourl = "" + editTextFotourl.getText();
                String contacto = "" + editTextContactoAnuncio.getText();
                String email = getIntent().getExtras().getString("email");

                // Llamar al gestor de anuncios para añadirlo a la base de datos y devuelve un true si todo ha ido bien
                boolean ok = GestorAnuncios.getGestorAnuncios().anadirAnuncio(AnadirAnuncioActivity.this, titulo, descripcion, fotourl, contacto, email);

                //Ejecutar distintas notificaciones dependiendo del resultado
                if (ok) {
                    notificarAnadirAnuncio();
                } else {
                    notificarError();
                }
                // Intent a HomeActivity para que se actualice si hay cambios
                Intent intent = new Intent(AnadirAnuncioActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Vuelve a la actividad Home
        Intent intent = new Intent(AnadirAnuncioActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void notificarAnadirAnuncio(){
        // Construir el manager y el builder
        NotificationManager elManager = (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getApplicationContext(), "IdCanal");

        // Si la versión de android es reciente, construir el canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("IdCanal", "CanalOK", NotificationManager.IMPORTANCE_DEFAULT);
            elManager.createNotificationChannel(elCanal);
        }
        // Configurar los datos de la notificación
        elBuilder.setSmallIcon(android.R.drawable.stat_sys_upload_done)
                .setContentTitle(getResources().getString(R.string.notifTitle))
                .setContentText(getResources().getString(R.string.notifText))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Ejecutar la notificación
        elManager.notify(1, elBuilder.build());
    }

    private void notificarError(){
        // Construir el manager y el builder
        NotificationManager elManager = (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getApplicationContext(), "IdCanalError");

        // Si la versión de android es reciente, construir el canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("IdCanalError", "CanalError", NotificationManager.IMPORTANCE_DEFAULT);
            elManager.createNotificationChannel(elCanal);
        }
        // Configurar los datos de la notificación
        elBuilder.setSmallIcon(android.R.drawable.stat_sys_upload_done)
                .setContentTitle(getResources().getString(R.string.notifErrorTitle))
                .setContentText(getResources().getString(R.string.notifErrorText))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        // Ejecutar la notificación
        elManager.notify(1, elBuilder.build());
    }
}
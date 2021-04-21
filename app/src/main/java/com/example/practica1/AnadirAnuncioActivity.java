package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Gestor.AnadirAnuncio;
import Gestor.GestorAnuncios;
import Gestor.IniciarSesion;

// Actividad que representa la pantalla de añadir un nuevo anuncio y su funcionalidad
public class AnadirAnuncioActivity extends AppCompatActivity {

    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_anuncio);

        // Configuración del imageView del preview
        ImageView imgPreview = findViewById(R.id.imgPreview);
        imgPreview.setVisibility(View.INVISIBLE);

        // Configuración de los cuadros de texto y de input
        TextView textTituloAnadirAnuncio = findViewById(R.id.textTituloAnadirAnuncio);
        textTituloAnadirAnuncio.setText(R.string.textTituloAnadirAnuncio);

        EditText editTextTitulo = findViewById(R.id.editTextTituloAnadirAnuncio);
        editTextTitulo.setHint(R.string.hintTituloAnuncio);

        EditText editTextDescripcionAnuncio = findViewById(R.id.editTextDescripcionAnuncio);
        editTextDescripcionAnuncio.setHint(R.string.hintDescripcion);

        EditText editTextContactoAnuncio = findViewById(R.id.editTextContactoAnuncio);
        editTextContactoAnuncio.setHint(R.string.hintContacto);

        // Funcionalidad del botón de sacar foto
        Button btnSacarFoto = findViewById(R.id.btnSacarFoto);
        btnSacarFoto.setText(R.string.btnSacarFoto);
        btnSacarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent elIntentFoto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(elIntentFoto, 14);
            }
        });

        // Funcionalidad del botón de añadir un anuncio.
        Button btnConfirmarAnuncio = findViewById(R.id.btnConfirmarAnuncio);
        btnConfirmarAnuncio.setText(R.string.btnConfirmarAnuncio);
        btnConfirmarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recoger los datos de los inputs de texto
                String titulo = "" + editTextTitulo.getText();
                String descripcion = "" + editTextDescripcionAnuncio.getText();
                String contacto = "" + editTextContactoAnuncio.getText();
                String emailAnunciante = getIntent().getExtras().getString("email");

                // encode Bitmap to String
                Bitmap bitmapAct = AnadirAnuncioActivity.this.bitmap;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmapAct.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] fototransformada = stream.toByteArray();
                String fotoen64 = Base64.encodeToString(fototransformada,Base64.DEFAULT);

                AnadirAnuncioActivity.this.anadirAnuncio(titulo, descripcion, fotoen64, contacto, emailAnunciante);

            }
        });
    }

    public void anadirAnuncio(String titulo, String descripcion, String foto, String contacto, String emailAnunciante) {
        Data data = new Data.Builder().putString("titulo", titulo).putString("descripcion", descripcion).putString("foto", foto).putString("contacto", contacto).putString("emailAnunciante", emailAnunciante).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(AnadirAnuncio.class).setInputData(data).build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null && workInfo.getState().isFinished()){
                            Data outputData = workInfo.getOutputData();
                            int codigoMax = outputData.getInt("codigoMax", 2);
                            // Llamar al gestor de anuncios para añadirlo a la base de datos y devuelve un true si todo ha ido bien
                            GestorAnuncios.getGestorAnuncios().anadirAnuncio(codigoMax, titulo, descripcion, foto, contacto, emailAnunciante);

                            AnadirAnuncioActivity.this.notificarAnadirAnuncio();

                            // Intent a HomeActivity para que se actualice si hay cambios
                            Intent intent = new Intent(AnadirAnuncioActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 14 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap laminiatura = (Bitmap) extras.get("data");
            this.bitmap = laminiatura;
            ImageView imgPreview = findViewById(R.id.imgPreview);
            imgPreview.setImageBitmap(laminiatura);
            imgPreview.setVisibility(View.VISIBLE);
        }
    }
}
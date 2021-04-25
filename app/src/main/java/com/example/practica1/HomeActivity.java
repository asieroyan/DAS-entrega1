package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Adaptador.AdaptadorListView;
import Gestor.EliminarAnuncio;
import Gestor.GestorAnuncios;
import Gestor.GestorSesion;
import Gestor.IniciarSesion;
import Gestor.Registrarse;
import Modelo.Anuncio;

public class HomeActivity extends AppCompatActivity {

    private boolean datosCargados = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Obtiene el email del login y lo guarda en la clase
        String email = GestorSesion.getGestorSesion().getEmail();

        // Obtiene el ListView del Layout
        ListView list = findViewById(R.id.listAnunciosAjenos);



        // LLama al gestor de anuncios para que cargue todos los anuncios de la BD y obtiene la lista de objetos Anuncio
        ArrayList<Anuncio> listAnuncios = GestorAnuncios.getGestorAnuncios().cargarAnuncios(HomeActivity.this);

        // Obtiene todos los elementos de los anuncios
        String[] titulos = GestorAnuncios.getGestorAnuncios().getTitulos();
        String[] descripciones = GestorAnuncios.getGestorAnuncios().getDescripciones();
        String[] fotosUrl = GestorAnuncios.getGestorAnuncios().getFotosUrl();
        String[] contactos = GestorAnuncios.getGestorAnuncios().getContactos();

        // Crea un adaptador con esos datos obtenidos y lo enlaza al ListView
        AdaptadorListView arrayAdapter = new AdaptadorListView(this, titulos, descripciones, fotosUrl, contactos);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Ejecución al clicar en un elemento (mostrar anuncio).
                // Obtiene los datos del anuncio seleccionado y hace un intent a la actividad Anuncio con ellos
                Anuncio anuncio = listAnuncios.get(position);
                Intent intent = new Intent(HomeActivity.this, AnuncioActivity.class);
                intent.putExtra("foto", anuncio.getFoto());
                intent.putExtra("titulo", anuncio.getTitulo());
                intent.putExtra("descripcion", anuncio.getDescripcion());
                intent.putExtra("contacto", anuncio.getContacto());
                startActivity(intent);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Ejecución al mantener pulsado un elemento (eliminar anuncio).
                // Obtiene el anuncio concreto
                Anuncio anuncio = listAnuncios.get(position);
                System.out.println(email);

                // Comprobar si el usuario actual es admin o ha creado este anuncio para permitir borrarlo
                if (email.equals(anuncio.getEmailAnunciante()) || email.equals("as@as.as")) {

                    // Crear Dialog para confirmar la eliminación del anuncio
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setMessage(R.string.textEliminarAnuncio).setPositiveButton(R.string.textSi, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.out.println(anuncio.getCodigo());
                            // Si se confirma, llamar al gestor de anuncios para eliminar el anuncio actual y relanzar la actividad para actualizar los datos
                            Data data = new Data.Builder().putInt("codigo", anuncio.getCodigo()).build();
                            OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(EliminarAnuncio.class).setInputData(data).build();

                            WorkManager.getInstance().getWorkInfoByIdLiveData(otwr.getId())
                                    .observe(HomeActivity.this, new Observer<WorkInfo>() {
                                        @Override
                                        public void onChanged(WorkInfo workInfo) {
                                            if(workInfo != null && workInfo.getState().isFinished()){
                                                Data outputData = workInfo.getOutputData();
                                                String resultado = outputData.getString("resultado");
                                                System.out.println(resultado);
                                                JSONParser parser = new JSONParser();
                                                try {
                                                    JSONObject json = (JSONObject) parser.parse(resultado);
                                                    boolean existe = (boolean) json.get("existe");
                                                    if (!existe) {
                                                        GestorAnuncios.getGestorAnuncios().eliminarAnuncio(HomeActivity.this, anuncio);
                                                        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                            WorkManager.getInstance().enqueue(otwr);
                        }
                    }).setNegativeButton(R.string.textNo, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // No hacer nada, el usuario ha cancelado el Dialog
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
        // Inutilizar el botón Back
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Añadir el menú a la actividad
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Añadir la funcionalidad a las opciones del menú
        switch (item.getItemId()) {
            case R.id.menuAjustes:
                // Llamar a la actividad Ajustes
                Intent intent = new Intent(HomeActivity.this, AjustesActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menuAnadirAnuncio:
                // Llamar a la actividad de añadir anuncio
                Intent intent1 = new Intent(HomeActivity.this, AnadirAnuncioActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.menuCerrarSesion:
                Intent intent2 = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
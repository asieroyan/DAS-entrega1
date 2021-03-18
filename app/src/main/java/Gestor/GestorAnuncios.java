package Gestor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;

import Modelo.Anuncio;

public class GestorAnuncios {
    private static GestorAnuncios mGestorAnuncios;
    private ArrayList<Anuncio> listaAnuncios;

    private GestorAnuncios(){
        listaAnuncios = new ArrayList<Anuncio>();
    };

    public static GestorAnuncios getGestorAnuncios() {
        if (mGestorAnuncios == null) {
            mGestorAnuncios = new GestorAnuncios();
        }
        return mGestorAnuncios;
    }

    public void anadirAnuncio(Context context, String titulo, String descripcion, String fotourl, String contacto, String email) {
        int resultado = 0; // Error al acceder a la base de datos
        GestorBD gestor = new GestorBD(context, "DBUsuarios", null, 1);
        SQLiteDatabase db = gestor.getWritableDatabase();
        int codigoMax = 0;
        if (db != null){
            resultado = 1; // Anuncio añadido
            String update = "INSERT INTO anuncios(Fotourl, Titulo, Descripcion, Contacto, EmailAnunciante) VALUES(\'" + fotourl + "\',\'" + titulo + "\',\'" + descripcion + "\',\'" + contacto + "\',\'" + email + "\');";
            gestor.ejecutarUpdate(db, update);
        }
        if (db != null){
            try (Cursor cs = gestor.ejecutarConsulta(db, "SELECT Codigo FROM anuncios WHERE EmailAnunciante = \'" + email + "\';")) {
                while (cs.moveToNext()) {
                    int codigoAct = cs.getInt(0);
                    if (codigoAct > codigoMax) {
                        codigoMax = codigoAct;
                    }
                }
            }
        }

        Anuncio anuncio = new Anuncio(codigoMax, fotourl, titulo, descripcion, contacto, email);
        this.listaAnuncios.add(anuncio);
    }
    public void eliminarAnuncio(Context context, Anuncio anuncio) {
        this.listaAnuncios.remove(anuncio);
        GestorBD gestor = new GestorBD(context, "DBUsuarios", null, 1);
        SQLiteDatabase db = gestor.getWritableDatabase();
        String update = "DELETE FROM anuncios WHERE Codigo = \'" +  anuncio.getCodigo() + "\'";
        if (db != null){
            gestor.ejecutarUpdate(db, update);
        }
    }

    public ArrayList<Anuncio> getAnuncios () {
        return this.listaAnuncios;
    }

    public void cargarAnuncios (Context context){
        GestorBD gestor = new GestorBD(context, "DBUsuarios", null, 1);
        SQLiteDatabase db = gestor.getWritableDatabase();
        try (Cursor cs = gestor.ejecutarConsulta(db, "SELECT * FROM anuncios;")) {
            this.listaAnuncios = new ArrayList<Anuncio>();
            while (cs.moveToNext()) {
                int codigo = cs.getInt(0);
                String fotourl = cs.getString(1);
                String titulo = cs.getString(2);
                String descripcion = cs.getString(3);
                String contacto = cs.getString(4);
                String emailAnunciante = cs.getString(5);
                Anuncio anuncio = new Anuncio(codigo, fotourl, titulo, descripcion, contacto, emailAnunciante);
                this.listaAnuncios.add(anuncio);
            }
        }
    }

    public ArrayList<Anuncio> obtenerAnunciosPorEmail (String email) {
        ArrayList<Anuncio> resultado = new ArrayList<Anuncio>();
        for (Anuncio anuncio : this.listaAnuncios) {
            if(anuncio.getEmailAnunciante() == email) {
                resultado.add(anuncio);
            }
        }
        return resultado;
    }
}

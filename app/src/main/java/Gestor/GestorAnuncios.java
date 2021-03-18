package Gestor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;

import Modelo.Anuncio;

public class GestorAnuncios {
    private static GestorAnuncios mGestorAnuncios;
    private final ArrayList<Anuncio> listaAnuncios;

    private GestorAnuncios(){
        listaAnuncios = new ArrayList<Anuncio>();
    };

    public static GestorAnuncios getGestorAnuncios() {
        if (mGestorAnuncios == null) {
            mGestorAnuncios = new GestorAnuncios();
        }
        return mGestorAnuncios;
    }

    public void anadirAnuncio(Anuncio anuncio) {
        this.listaAnuncios.add(anuncio);
    }
    public void eliminarAnuncio(Anuncio anuncio) {
        this.listaAnuncios.remove(anuncio);
    }

    public ArrayList<Anuncio> getAnuncios () {
        return this.listaAnuncios;
    }

    public void cargarAnuncios (Context context){
        GestorBD gestor = new GestorBD(context, "DBUsuarios", null, 1);
        SQLiteDatabase db = gestor.getWritableDatabase();
        try (Cursor cs = gestor.ejecutarConsulta(db, "SELECT * FROM anuncios;")) {
            while (cs.moveToNext()) {
                int codigo = cs.getInt(0);
                String fotourl = cs.getString(1);
                String titulo = cs.getString(2);
                String descripcion = cs.getString(3);
                String contacto = cs.getString(4);
                String emailAnunciante = cs.getString(5);
                Anuncio anuncio = new Anuncio(codigo, fotourl, titulo, descripcion, contacto, emailAnunciante);
                anadirAnuncio(anuncio);
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

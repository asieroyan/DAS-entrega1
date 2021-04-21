
package Gestor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import Modelo.Anuncio;

public class GestorAnuncios {
    // Instancia de patrón Singleton
    private static GestorAnuncios mGestorAnuncios;
    // Lista de anuncios
    private ArrayList<Anuncio> listaAnuncios;

    // Constructora privada
    private GestorAnuncios(){
        listaAnuncios = new ArrayList<Anuncio>();
    };


    public static GestorAnuncios getGestorAnuncios() {
        // Método estático que devuelve la instancia del gestor para patrón Singleton
        if (mGestorAnuncios == null) {
            mGestorAnuncios = new GestorAnuncios();
        }
        return mGestorAnuncios;
    }


    public boolean anadirAnuncio(int codigo, String titulo, String descripcion, String foto, String contacto, String emailAnunciante) {
        Anuncio anuncio = new Anuncio(codigo, foto, titulo, descripcion, contacto, emailAnunciante);
        this.listaAnuncios.add(anuncio);
        return true;
    }

    public void eliminarAnuncio(Context context, Anuncio anuncio) {
        this.listaAnuncios.remove(anuncio);
    }

    public ArrayList<Anuncio> getAnuncios () {
        // Devolver la lista local de anuncios
        return this.listaAnuncios;
    }

    public void cargarAnuncios (Context context){

    }

    private int getCount() {
        // Devuelve el tamaño de la lista local
        return this.listaAnuncios.size();
    }

    public ArrayList<Anuncio> getAnunciosPorEmail (String email) {
        // Obtener una lista con los anuncios de un usuario concreto dado su email
        ArrayList<Anuncio> resultado = new ArrayList<Anuncio>();
        for (Anuncio anuncio : this.listaAnuncios) {
            // Recorre la lista actual y la añade a una nueva
            if(anuncio.getEmailAnunciante() == email) {
                resultado.add(anuncio);
            }
        }
        // Devuelve la lista nueva
        return resultado;
    }

    public String[] getTitulos (){
        // Recorre la lista actual y devuelve un array con todos los títulos
        String[] resultado = new String[this.getCount()];
        int i = 0;
        for (Anuncio anuncio : this.listaAnuncios) {
            resultado[i] = anuncio.getTitulo();
            i++;
        }
        return resultado;
    }

    public String[] getDescripciones (){
        // Recorre la lista actual y devuelve un array con todas las descripciones
        String[] resultado = new String[this.getCount()];
        int i = 0;
        for (Anuncio anuncio : this.listaAnuncios) {
            resultado[i] = anuncio.getDescripcion();
            i++;
        }
        return resultado;
    }

    public String[] getContactos (){
        // Recorre la lista actual y devuelve un array con todos los contactos
        String[] resultado = new String[this.getCount()];
        int i = 0;
        for (Anuncio anuncio : this.listaAnuncios) {
            resultado[i] = anuncio.getContacto();
            i++;
        }
        return resultado;
    }

    public String[] getFotosUrl (){
        // Recorre la lista actual y devuelve un array con todas las url de las fotos
        String[] resultado = new String[this.getCount()];
        int i = 0;
        for (Anuncio anuncio : this.listaAnuncios) {
            resultado[i] = anuncio.getFoto();
            i++;
        }
        return resultado;
    }
}

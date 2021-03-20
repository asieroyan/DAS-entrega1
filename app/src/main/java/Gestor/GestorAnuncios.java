package Gestor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


    public boolean anadirAnuncio(Context context, String titulo, String descripcion, String fotourl, String contacto, String email) {
        // Añade un anuncio a la BD y devuelve el resultado (true si OK y false si ERROR)
        boolean resultado = false; // Error al acceder a la base de datos
        GestorBD gestor = new GestorBD(context, "DBUsuarios", null, 1);
        SQLiteDatabase db = gestor.getWritableDatabase();
        int codigoMax = 0;
        if (db != null){
            // Si se ha podido conectar con la base de datos, insertar los datos en ella
            resultado = true; // Anuncio añadido
            String update = "INSERT INTO anuncios(Fotourl, Titulo, Descripcion, Contacto, EmailAnunciante) VALUES(\'" + fotourl + "\',\'" + titulo + "\',\'" + descripcion + "\',\'" + contacto + "\',\'" + email + "\');";
            gestor.ejecutarUpdate(db, update);
        }
        if (db != null){
            // Obtener el código del anuncio recién introducido
            try (Cursor cs = gestor.ejecutarConsulta(db, "SELECT Codigo FROM anuncios WHERE EmailAnunciante = \'" + email + "\';")) {
                while (cs.moveToNext()) {
                    int codigoAct = cs.getInt(0);
                    if (codigoAct > codigoMax) {
                        codigoMax = codigoAct;
                    }
                }
            }
        }
        // Cerrar la BD
        db.close();
        // Crear el anuncio y añadirlo a la lista de la clase
        Anuncio anuncio = new Anuncio(codigoMax, fotourl, titulo, descripcion, contacto, email);
        this.listaAnuncios.add(anuncio);
        return resultado;
    }

    public void eliminarAnuncio(Context context, Anuncio anuncio) {
        // Eliminar el anuncio de la lista local
        this.listaAnuncios.remove(anuncio);
        // Eliminar el anuncio de la BD
        GestorBD gestor = new GestorBD(context, "DBUsuarios", null, 1);
        SQLiteDatabase db = gestor.getWritableDatabase();
        String update = "DELETE FROM anuncios WHERE Codigo = \'" +  anuncio.getCodigo() + "\'";
        if (db != null){
            // Si se ha podido conectar con la BD, realizar el DELETE
            gestor.ejecutarUpdate(db, update);
        }
        // Cerrar la BD
        db.close();
    }

    public ArrayList<Anuncio> getAnuncios () {
        // Devolver la lista local de anuncios
        return this.listaAnuncios;
    }

    public void cargarAnuncios (Context context){
        // Recoge todos los anucios de la BD y los añade a la lista local
        GestorBD gestor = new GestorBD(context, "DBUsuarios", null, 1);
        SQLiteDatabase db = gestor.getWritableDatabase();
        if (db != null) {
            // Si se peude conectar a la BD, ejecutar la consulta
            try (Cursor cs = gestor.ejecutarConsulta(db, "SELECT * FROM anuncios;")) {
                // Inicializar la lista local a una lista vacía
                this.listaAnuncios = new ArrayList<Anuncio>();

                // Recorrer los resultados de la consulta
                while (cs.moveToNext()) {
                    // Obtener los campos de la consulta
                    int codigo = cs.getInt(0);
                    String fotourl = cs.getString(1);
                    String titulo = cs.getString(2);
                    String descripcion = cs.getString(3);
                    String contacto = cs.getString(4);
                    String emailAnunciante = cs.getString(5);

                    // Convertir los datos en una instancia de Anuncio y añadirlo a la lista local
                    Anuncio anuncio = new Anuncio(codigo, fotourl, titulo, descripcion, contacto, emailAnunciante);
                    this.listaAnuncios.add(anuncio);
                }
            }
        }
        // Cerrar la BD
        db.close();
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
            resultado[i] = anuncio.getFotourl();
            i++;
        }
        return resultado;
    }
}

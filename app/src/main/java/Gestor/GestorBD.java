package Gestor;

// Imports para la gestión de la BD
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Esta clase realiza todas las acciones necesarias para el control de la BD sin tener que hacer una
// llamada desde fuera de ella. Las conexiones abiertas se cierran al acabar de realizar una
// acción para no saturar la BD.
public class GestorBD extends SQLiteOpenHelper {


    // Consultas a ejecutar en la creación de la BD
    private final String sqlCreate =
            "CREATE TABLE usuarios (" +
                "'Codigo' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "'Email' VARCHAR(80), " +
                "'Contrasena' VARCHAR(40)" +
            ")";

    private final String sqlCreate2 =
            "CREATE TABLE anuncios (" +
                "'Codigo' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "'Fotourl' VARCHAR(255), " +
                "'Titulo' VARCHAR(100)," +
                "'Descripcion' TEXT," +
                "'Contacto' VARCHAR(255)," +
                "'EmailAnunciante' VARCHAR(80)" +
            ")";
    // Constructora pública
    GestorBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Método que se ejecuta al crearse la BD, añade las dos tablas necesarias a la BD
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se ejecuta al actualizar la BD
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS anuncios");
    }

    public void ejecutarUpdate(SQLiteDatabase db, String query) {
        // Recibe una sentencia SQL de update y la ejecuta
        db.execSQL(query);
    }

    public Cursor ejecutarConsulta(SQLiteDatabase db, String query) {
        // Recibe una sentencia SQL de consulta y la ejecuta, devolviendo un cursor de resultado
        return db.rawQuery(query, null);
    }
}

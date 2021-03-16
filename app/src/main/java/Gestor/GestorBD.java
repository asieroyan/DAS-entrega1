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

    // Atributos para la conexión con la BD
    private String sqlCreate = "CREATE TABLE usuarios ('Codigo' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'Email' VARCHAR(255), 'Contrasena' VARCHAR(255))";

    // Constructora pública
    GestorBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
    }

    public void ejecutarUpdate(SQLiteDatabase db, String query) {
        db.execSQL(query);
        db.execSQL(sqlCreate);
    }

    public Cursor ejecutarConsulta(SQLiteDatabase db, String query) {
        return db.rawQuery(query, null);
    }
}

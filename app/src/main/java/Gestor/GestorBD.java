package Gestor;

// Imports para la gestión de la BD
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

// Esta clase realiza todas las acciones necesarias para el control de la BD sin tener que hacer una
// llamada desde fuera de ella. Las conexiones abiertas se cierran al acabar de realizar una
// acción para no saturar la BD.
public class GestorBD {

    // Constructora pública
    public GestorBD(){}

    public void ejecutarUpdate(String query) {
        // Recibe una sentencia SQL de update y la ejecuta
    }

    public void ejecutarConsulta(String query) {
        // Recibe una sentencia SQL de consulta y la ejecuta, devolviendo un ResultSet de resultado
    }
}

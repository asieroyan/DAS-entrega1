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
    private static GestorBD mGestorBD;
    private String servidor = "ec2-54-167-31-169.compute-1.amazonaws.com/";
    private String puerto = "3306";
    private String usuario = "Xaoyanguren004";
    private String contrasena = "RKh1N49go";

    // Constructora pública
    private GestorBD(){}


    public static  GestorBD getGestorBD() {
        if (mGestorBD == null) {
            mGestorBD = new GestorBD();
        }
        return mGestorBD;
    }

    public Connection conn() {
        // Se ejecuta al actualizar la BD
        return new DriverManager.getConnection(this.servidor + ":" + this.puerto, this.usuario, this.contrasena);
    }

    public void ejecutarUpdate(String query) {
        // Recibe una sentencia SQL de update y la ejecuta
        Connection
    }

    public ResultSet ejecutarConsulta(String query) {
        // Recibe una sentencia SQL de consulta y la ejecuta, devolviendo un ResultSet de resultado

    }
}

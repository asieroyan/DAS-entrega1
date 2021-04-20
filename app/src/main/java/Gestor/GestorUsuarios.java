package Gestor;
// imports para manejar los resultados de las consultas y realizarlas
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

// clase Singleton para la gestión de los usuarios de la BD
public class GestorUsuarios {
    private static GestorUsuarios mGestorUsuarios = null;

    private GestorUsuarios(){}

    public static GestorUsuarios getGestorUsuarios(){
        if (mGestorUsuarios == null) {
            mGestorUsuarios = new GestorUsuarios();
        }
        return mGestorUsuarios;
    }

    // Comprueba que el usuario especificado no existe y después añade el usuario a la BD
    public int anadirNuevoUsuario (Context context, String email, String contrasena){
        return 2;
    }

    // Comprueba que el usuario dado existe y después comprueba si las contraseñas coinciden
    public int login (Context context, String email, String contrasena) {
        return 2;
    }
}

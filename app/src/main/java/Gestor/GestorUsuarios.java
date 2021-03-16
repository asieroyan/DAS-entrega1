package Gestor;
// imports para manejar los resultados de las consultas
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

// clase Singleton
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
        int resultado = 0; // Error al acceder a la base de datos
        GestorBD gestor = new GestorBD(context, "DBUsuarios", null, 1);
        SQLiteDatabase db = gestor.getWritableDatabase();
        if (db != null){
            resultado = 1; // Accedido a la BD pero ya existe el usuario
            String consultaCountUsuario = "SELECT COUNT(*) FROM usuarios WHERE Email == " + email + ";";
            Cursor cs = gestor.ejecutarConsulta(db, consultaCountUsuario);
            cs.moveToNext();
            if (cs.getInt(0) != 0) {
                resultado = 2; // Nuevo usuario añadido a la BD
                String update = "INSERT INTO usuario (Email, Contrasena) VALUES (" + email + "," + contrasena + ");";
                gestor.ejecutarUpdate(db, update);
            }
        }
        return resultado;
    }

    // Comprueba que el usuario dado existe y después comprueba si las contraseñas coinciden
    public int login (Context context, String email, String contrasena) {
        int resultado = 0; // Error al acceder a la base de datos
        GestorBD gestor = new GestorBD(context, "DBUsuarios", null, 1);
        SQLiteDatabase db = gestor.getWritableDatabase();
        if (db != null){
            resultado = 1; // Accedido a la base de datos pero credenciales incorrectas
            String consultaCountUsuario = "SELECT COUNT (*) FROM usuarios WHERE 'Email' =? " + email;
            Cursor cs = gestor.ejecutarConsulta(db, consultaCountUsuario);
            cs.moveToNext();
            if (cs.getInt(0) != 0) {
                String consultaGetContrasena = "SELECT Contrasena FROM usuarios WHERE 'Email' =? " + email;
                Cursor cs2 = gestor.ejecutarConsulta(db, consultaGetContrasena);
                cs2.moveToNext();
                String contrasenaActual = cs2.getString(0);
                if (contrasena.equals(contrasenaActual)) {
                    resultado = 2; // credenciales correctas
                }
             }
        }
        return resultado;
    }
}

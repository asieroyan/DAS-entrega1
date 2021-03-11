package Gestor;
// imports para manejar los resultados de las consultas
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public boolean anadirNuevoUsuario (String nombre, String apellidos, String usuario, String contrasena){
        try {
            String consultaCountUsuario = "SELECT COUNT(*) FROM usuario WHERE usuario = " + usuario + ";";
            ResultSet rs = GestorBD.getmGestorBD().ejecutarConsulta(consultaCountUsuario);
            int esta = rs.getInt(0);
            if (esta == 0) {
                String update = "INSERT INTO usuario VALUES (" + nombre + "" + apellidos + "," + usuario + "," + contrasena + ");";
                GestorBD.getmGestorBD().ejecutarUpdate(update);
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Comprueba que el usuario dado existe y después comprueba si las contraseñas coinciden
    public boolean login (String usuario, String contrasena) {
        try {
            String consultaCountUsuario = "SELECT COUNT(*) FROM usuario WHERE usuario = " + usuario + ";";
            ResultSet rs = GestorBD.getmGestorBD().ejecutarConsulta(consultaCountUsuario);
            int esta = rs.getInt(0);
            if (esta != 0) {
                String consultaContrasena = "SELECT contrasena FROM usuario WHERE usuario = " + usuario + ";";
                ResultSet rs2 = GestorBD.getmGestorBD().ejecutarConsulta(consultaContrasena);
                String resultado = rs2.getString(0);
                if (contrasena.equals(resultado)) {
                    return true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}

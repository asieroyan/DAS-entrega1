package Gestor;

// Imports para la gestión de la BD
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Esta clase realiza todas las acciones necesarias para el control de la BD sin tener que hacer una
// llamada desde fuera de ella. Las conexiones abiertas se cierran al acabar de realizar una
// acción para no saturar la BD.
public class GestorBD {

    // Atributos para la conexión con la BD
    private String servidorBD;
    private String puertoBD;
    private String usuarioBD;
    private String contrasenaBD;
    private String nombreBD;

    // Atributo singleton
    private static GestorBD mGestorBD = null;

    // Constructora privada
    private GestorBD(){}

    // Método público y estático para ser llamado desde fuera de la clase. Devuelve la instancia
    // de GestorBD
    public static GestorBD getmGestorBD(){
        if (mGestorBD == null){
            mGestorBD = new GestorBD();
        }
        return mGestorBD;
    }

    //Conexión con la BD
    private Connection conn() throws SQLException{
        return DriverManager.getConnection(servidorBD + ':' +  puertoBD + '/' + nombreBD, usuarioBD, contrasenaBD);
    }

    //Ejecutar una consulta en la BD y devolver el ResultSet
    public ResultSet ejecutarConsulta(String consulta) throws SQLException{
        Connection conn = conn();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(consulta);
        conn.close();
        return rs;
    }

    //Ejecutar un update en la BD
    public void ejecutarUpdate (String update) throws SQLException{
        Connection conn = conn();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(update);
        conn.close();
    }
}

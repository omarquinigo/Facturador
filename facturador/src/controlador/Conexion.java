package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    static Connection conexion = null;
    static Connection conexionTest = null;

    public static Connection getConexion() {
        String host = Datos.getHost();
        String puerto = Datos.getPuerto();
        String baseDatos = Datos.getBaseDatos();
        String usuario = Datos.getUsuario();
        String contrasena = Datos.getContrasena();

        String Url = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDatos;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection(Url, usuario, contrasena);
        } catch (Exception e) {
            System.out.println("Error al conectar a BD: \n" + e);
            Metodos.mensajeError("Error al conectar a BD: \n" + e);
        }

        return conexion;
    }

    public static void getConexionTest(String host, String puerto,
            String baseDatos, String usuario, String contrasena)
            throws InstantiationException, IllegalAccessException, SQLException {

        String Url = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDatos;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexionTest = DriverManager.getConnection(Url, usuario, contrasena);
            Metodos.mensajeInformacion("Conexión exitosa.");
        } catch (Exception e) {
            System.out.println("Error al conectar a BD: \n" + e);
            Metodos.mensajeError("Error al conectar a BD: \n" + e);
        }
    }

}

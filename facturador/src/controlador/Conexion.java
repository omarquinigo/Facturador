package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    static Connection conexion = null;

    public static Connection getConexion() throws InstantiationException, IllegalAccessException {

        String Url = "jdbc:mysql://localhost:3306/facturador";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            conexion = DriverManager.getConnection(Url,"root","");
        } catch (SQLException e) {
            Metodos.MensajeError("Error al conectar a BD: \n" + e);
        }
        return conexion;
    }

}

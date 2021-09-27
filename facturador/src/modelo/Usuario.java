package modelo;

import controlador.Conexion;
import controlador.Metodos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Usuario {

    public static ResultSet Consulta(String query) {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt;
            stmt = con.createStatement();
            ResultSet respuesta = stmt.executeQuery(query);
            return respuesta;
        } catch (Exception e) {
            Metodos.MensajeError("Error: \n" + e);
        }
        return null;
    }

    public static void Registrar(String id, String usuario,
            String contrasena, String rol) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into usuario \n"
                    + "(id,usuario,contrasena, \n"
                    + "rol) \n"
                    + "values ('" + id + "','" + usuario + "','"
                    + contrasena + "','" + rol + "');";
            stmt.execute(sql);
            con.close();
            Metodos.MensajeInformacion("Usuario registrado.");
        } catch (Exception e) {
            Metodos.MensajeError("Error registrando usuario: \n" + e);
        }
    }
    
    public static void Actualizar(String id, String usuario,
            String contrasena, String rol) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "update usuario \n"
                    + "set \n"
                    + "usuario = '" + usuario + "', "
                    + "contrasena = '" + contrasena + "', "
                    + "rol = '" + rol + "' \n"
                    + "where id = '" + id + "';";
            stmt.execute(sql);
            con.close();
            Metodos.MensajeInformacion("Usuario actualizado.");
        } catch (Exception e) {
            Metodos.MensajeError("Error actualizando usuario.\n" + e);
        }
    }

}

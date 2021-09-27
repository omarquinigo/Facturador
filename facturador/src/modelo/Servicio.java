package modelo;

import controlador.Conexion;
import controlador.Metodos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Servicio {

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

    public static void Registrar(String codigo, String descripcion,
            String precio1, String precio2, String estado)
            throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into servicio \n"
                    + "(codigo,descripcion, \n"
                    + "precio1,precio2,estado) \n"
                    + "values ('" + codigo + "','" + descripcion + "','"
                    + precio1 + "','" + precio2 + "','"
                    + estado + "');";
            stmt.execute(sql);
            con.close();
            Metodos.MensajeInformacion("Servicio registrado.");
        } catch (Exception e) {
            Metodos.MensajeError("Error registrando servicio: \n" + e);
        }
    }
    
    public static void Actualizar(String id, String codigo, String descripcion,
            String precio1, String precio2, String estado)
            throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "update servicio \n"
                    + "set \n"
                    + "codigo = '" + codigo + "', "
                    + "descripcion = '" + descripcion + "', "
                    + "precio1 = '" + precio1 + "', "
                    + "precio2 = '" + precio2 + "', "
                    + "estado = '" + estado + "' \n"
                    + "where id = '" + id + "';";
            stmt.execute(sql);
            con.close();
            Metodos.MensajeInformacion("Servicio actualizado.");
        } catch (Exception e) {
            Metodos.MensajeError("Error actualizando servicio.\n" + e);
        }
    }
    

}

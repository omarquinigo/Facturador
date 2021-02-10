package modelo;

import controlador.Conexion;
import controlador.Metodos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cliente {

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

    public static void Registrar(String id, String tipoDocumento,
            String numeroDocumento, String nombreRazonSocial,
            String direccion) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into cliente \n"
                    + "(id,tipoDocumento,numeroDocumento, \n"
                    + "nombreRazonSocial,direccion) \n"
                    + "values ('" + id + "','" + tipoDocumento + "','"
                    + numeroDocumento + "','" + nombreRazonSocial + "','"
                    + direccion + "');";
            stmt.execute(sql);
            con.close();
            Metodos.MensajeInformacion("Cliente registrado");
        } catch (Exception e) {
            Metodos.MensajeError("Error registrando cliente: \n" + e);
        }
    }
    
    public static void Actualizar(String id, String tipoDocumento,
            String numeroDocumento, String nombreRazonSocial,
            String direccion) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "update cliente \n"
                    + "set \n"
                    + "tipoDocumento = '" + tipoDocumento + "', "
                    + "numeroDocumento = '" + numeroDocumento + "', "
                    + "nombreRazonSocial = '" + nombreRazonSocial + "', "
                    + "direccion = '" + direccion + "' \n"
                    + "where id = '" + id + "';";
            stmt.execute(sql);
            con.close();
            Metodos.MensajeInformacion("Cliente actualizado");
        } catch (Exception e) {
            Metodos.MensajeError("Error actualizando cliente.\n" + e);
        }
    }

}

package modelo;

import controlador.Conexion;
import controlador.Metodos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Config {

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
    
    public static void Actualizar(String ruc, String razonSocial,
            String direccion, String rutaSunat, String rutaPdf)
            throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "update config \n"
                    + "set \n"
                    + "ruc = '" + ruc + "', "
                    + "razonSocial = '" + razonSocial + "', "
                    + "direccion = '" + direccion + "', "
                    + "rutaSunat = '" + rutaSunat + "', "
                    + "rutaPdf = '" + rutaPdf + "' \n"
                    + "where id = '1';";
            stmt.execute(sql);
            con.close();
            Metodos.MensajeInformacion("Guardado.");
        } catch (Exception e) {
            Metodos.MensajeError("Error actualizando.\n" + e);
        }
    }

}

package modelo;

import controlador.Conexion;
import controlador.Metodos;
import controlador.Rutas;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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
            String direccion, String telefono, String correo, String web,
            String impresion, String serieFactura, String serieBoleta,
            String serieNCreditoFactura, String serieNCreditoBoleta,
            String serieNDebitoFactura, String serieNDebitoBoleta,
            String rutaSunat, String rutaPdf)
            throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "update config \n"
                    + "set \n"
                    + "ruc = '" + ruc + "', "
                    + "razonSocial = '" + razonSocial + "', "
                    + "direccion = '" + direccion + "', "
                    + "telefono = '" + telefono + "', "
                    + "correo = '" + correo + "', "
                    + "web = '" + web + "', "
                    + "impresion = '" + impresion + "', "
                    + "serieFactura = '" + serieFactura + "', "
                    + "serieBoleta = '" + serieBoleta + "', "
                    + "serieNCreditoFactura = '" + serieNCreditoFactura + "', "
                    + "serieNCreditoBoleta = '" + serieNCreditoBoleta + "', "
                    + "serieNDebitoFactura = '" + serieNDebitoFactura + "', "
                    + "serieNDebitoBoleta = '" + serieNDebitoBoleta + "', "
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

    public static void ActualizarENV(String host, String puerto, String baseDatos,
            String usuario, String contrasena) {
        try {
            FileOutputStream fos = new FileOutputStream(Rutas.getRutaConexionBD());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            
            bw.write("host=" + host + "\n" +
                     "puerto="+puerto+"\n" +
                     "baseDatos="+baseDatos+"\n" +
                     "usuario="+usuario+"\n" +
                     "contrasena="+contrasena+"\n");
            
            bw.close();
        } catch (Exception e) {
            Metodos.MensajeError("Error: "+e);
        }
    }

}

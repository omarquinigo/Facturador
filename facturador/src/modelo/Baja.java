package modelo;

import controlador.Conexion;
import controlador.Metodos;
import controlador.Rutas;
import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class Baja {

    public static ResultSet Consulta(String query) {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (Exception e) {
            Metodos.MensajeError("Error: \n" + e);
        }
        return null;
    }

    public static void RegistrarBaja(String id, String fecha) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into baja \n"
                    + "(id,fecha) \n"
                    + "values ('" + id + "','" + fecha + "');";
            stmt.execute(sql);
            con.close();
        } catch (Exception e) {
            Metodos.MensajeError("Error: \n" + e);
        }
    }
    
    public static void RegistrarBajaDet(String id, String idBaja,
            String tipoComprobante, String idComprobante, String motivo)
            throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into bajadet \n"
                    + "(id,idBaja,tipoComprobante,idComprobante,motivo) \n"
                    + "values ('" + id + "','" + idBaja + "','" + tipoComprobante + "','" 
                    + idComprobante + "','" + motivo + "');";
            stmt.execute(sql);
            con.close();
        } catch (Exception e) {
            Metodos.MensajeError("Error: \n" + e);
        }
    }
    
    public static void CrearPDF(String id) {
        // enviar a reporte
        String numeroComprobante = id;
        String fecha = "";
        // otros
        String ruc = Rutas.getRUC();
        String razonSocial = Rutas.getRazonSocial();
        String direccionLegal = Rutas.getDireccion();
        String logo = "\\src\\img\\logo.png";
        try {
            ResultSet rs = Baja.Consulta("select * from baja \n"
                    + "where id = '" + id + "' ;");
            while (rs.next()) {
                fecha = rs.getString("fecha");
            }
            rs.close();
        } catch (Exception e) {
        }
        //parámetros de envio al reporte
        Map parametro = new HashMap();
        parametro.put("numeroComprobante", numeroComprobante);
        parametro.put("fecha", fecha);
        parametro.put("ruc", ruc);
        parametro.put("razonSocial", razonSocial);
        parametro.put("direccionLegal", direccionLegal);
        parametro.put("logo", logo);
        // parametro para buscar el detalle
        parametro.put("id", id);
        try {
            Connection con = Conexion.getConexion();
            JasperPrint jp = JasperFillManager.fillReport("src\\repo\\Baja.jasper", parametro, con);
            JasperExportManager.exportReportToPdfFile(jp, Rutas.getRutaBajaPDF(id));
            Metodos.MensajeInformacion("PDF guardado.");
            con.close();
            //abriendo el pdf
            try {
                File objetofile = new File(Rutas.getRutaBajaPDF(id));
                Desktop.getDesktop().open(objetofile);
            } catch (Exception e) {
                System.out.println("Error abriendo baja: " + id + "\n" + e);
                Metodos.MensajeError("Error abriendo baja: " + id + "\n" + e);
            }
        } catch (Exception e) {
            System.out.println("Error creando PDF: " + id + "\n" + e);
            Metodos.MensajeError("Error creando PDF: " + id + "\n" + e);
        }
    }
    
}

package modelo;

import controlador.Conexion;
import controlador.Datos;
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
    
    static ResultSet rs;

    public static ResultSet consulta(String query) {
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

    public static void registrarBaja(String id, String fecha,
            String fechaComprobante) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into baja \n"
                    + "(id,fecha,fechaComprobante) \n"
                    + "values ('" + id + "','" + fechaComprobante + "','" + fecha + "');";
            stmt.execute(sql);
            con.close();
        } catch (Exception e) {
            Metodos.MensajeError("Error: \n" + e);
        }
    }
    
    public static void registrarBajaDet(String id, String idBaja,
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
    
    public static void crearPDF(String id){
        String papel = Datos.getImpresion();
     
        if (papel.equals("A4")) {
            crearPdfA4(id);
        } else { // Ticket 80 mm
            crearPdfTicket80mm(id);
        }

    }
    
    public static void crearPdfA4(String id) {
        // enviar a reporte
        String numeroComprobante = id;
        String fecha = "";
        // ========= Datos de nuestra empresa =============
        String ruc = Datos.getRUC();
        String razonSocial = Datos.getRazonSocial();
        String direccionLegal = Datos.getDireccion();
        String telefono = Datos.getTelefono();
        String correo = Datos.getCorreo();
        String web = Datos.getWeb();
        // ================================================
        String datosCabecera = direccionLegal + "\n"
                + "Teléfono: " + telefono + "\n"
                + "Correo: " + correo + "\n"
                + "Web: " + web + "\n";
        String logo = "\\src\\img\\logo.png";
        try {
            rs = Baja.consulta("select * from baja \n"
                    + "where id = '" + id + "' ;");
            while (rs.next()) {
                fecha = rs.getString("fecha");
                System.out.println("La fecha es: " + fecha);
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
        parametro.put("datosCabecera", datosCabecera);
        parametro.put("logo", logo);
        // parametro para buscar el detalle
        parametro.put("id", id);
        try {
            Connection con = Conexion.getConexion();
            JasperPrint jp = JasperFillManager.fillReport("src\\repo\\Baja-A4.jasper", parametro, con);
            JasperExportManager.exportReportToPdfFile(jp, Rutas.getRutaBajaPDF(id));
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
    
    public static void crearPdfTicket80mm(String id) {
        // enviar a reporte
        String numeroComprobante = id;
        String fecha = "";
        // ========= Datos de nuestra empresa =============
        String ruc = Datos.getRUC();
        String razonSocial = Datos.getRazonSocial();
        String direccionLegal = Datos.getDireccion();
        String telefono = Datos.getTelefono();
        String correo = Datos.getCorreo();
        String web = Datos.getWeb();
        // ================================================
        String logo = "\\src\\img\\logo.png";
        try {
            rs = Baja.consulta("select * from baja \n"
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
        parametro.put("telefono", telefono);
        parametro.put("correo", correo);
        parametro.put("web", web);
        parametro.put("logo", logo);
        // parametro para buscar el detalle
        parametro.put("id", id);
        try {
            Connection con = Conexion.getConexion();
            JasperPrint jp = JasperFillManager.fillReport("src\\repo\\Baja-Ticket80mm.jasper", parametro, con);
            JasperExportManager.exportReportToPdfFile(jp, Rutas.getRutaBajaPDF(id));
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

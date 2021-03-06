package modelo;

import controlador.Conexion;
import controlador.GenerarQR;
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

public class Factura {

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

    public static void RegistrarFactura(String id, String idCliente, String fecha,
            String moneda, String totalVentasGrabadas, String igv,
            String importeTotal) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into Factura \n"
                    + "(id,idCliente,fecha,moneda,TotalVentasGravadas,igv,importeTotal) \n"
                    + "values ('" + id + "','" + idCliente + "','" + fecha + "','" 
                    + moneda + "','" + totalVentasGrabadas + "','" + igv + "','"
                    + importeTotal + "');";
            stmt.execute(sql);
            con.close();
        } catch (Exception e) {
            Metodos.MensajeError("Error: \n" + e);
        }
    }
    
    public static void RegistrarFacturaDet(String id, String idFactura,
            String item, String cantidad, String tipoUnidad, String descripcion,
            String valorUnitario, String precioUnitarioItem) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into FacturaDet \n"
                    + "(id,idFactura,item,cantidad,tipoUnidad,descripcion,"
                    + "valorUnitario,precioUnitarioItem) \n"
                    + "values ('" + id + "','" + idFactura + "','" + item + "','" 
                    + cantidad + "','" + tipoUnidad + "','" + descripcion + "','"
                    + valorUnitario + "','" + precioUnitarioItem + "');";
            stmt.execute(sql);
            con.close();
        } catch (Exception e) {
            Metodos.MensajeError("Error: \n" + e);
        }
    }
    
    public static void RegistrarHash(String id, String hash)
            throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = " update factura "
                    + "set hash = '" + hash + "'\n"
                    + "  where id = '" + id + "';";
            stmt.execute(sql);
            con.close();
        } catch (Exception e) {
            Metodos.MensajeError("Error: \n" + e);
        }
    }
    
    public static void CrearQR(String id, String hash){
        try {
            String ruc_emisor = Rutas.getRUC();
            String tipo_comprobante = "01"; //indica una factura
            String corre_y_numero = id;
            String sumatoria_tributos = null;
            String total_precio_venta = null;
            String fecha_emision = null;//fecha en formato YYYY-MM-DD
            String tipo_documento_receptor = "6";//ruc
            String numero_documento_cliente = null;
            String CodigoHash = hash;
            String nombre_archivo = "imagenqr_Factura";
            
            ResultSet rs = Factura.Consulta("select * from factura\n"
                    + "inner join cliente\n"
                    + "on cliente.id = factura.id\n"
                    + "where factura.id = '" + id + "' ;");
            while (rs.next()) {
                sumatoria_tributos = rs.getString("igv");
                total_precio_venta = rs.getString("importeTotal");
                fecha_emision = Metodos.FechaFormatoSUNAT(rs.getString("fecha"));
                numero_documento_cliente = rs.getString("numeroDocumento");
            }
            GenerarQR generarQR = new GenerarQR();
                generarQR.GenerarQR(
                    ruc_emisor + "|"
                    + tipo_comprobante + "|"
                    + corre_y_numero + "|"
                    + sumatoria_tributos + "|"
                    + total_precio_venta + "|"
                    + fecha_emision + "|"
                    + tipo_documento_receptor + "|"
                    + numero_documento_cliente + "|"
                    + CodigoHash, nombre_archivo);
        } catch (Exception e) {
            System.out.println("Error creando QR: \n" +e);
            Metodos.MensajeError("Error creando QR: \n" +e);
        }
    }
    
    public static void CrearPDF(String id) {
        // enviar a reporte
        String numeroComprobante = id;
        String numeroDocumento = "";
        String nombreRazonSocial = "";
        String direccion = "";
        String fecha = "";
        String moneda = "";
        // totales
        String totalVentasGravadas = "";
        String igv = "";
        String importeTotal = "";
        // otros
        String montoEnTexto = "";
        String hash = "";
        String ruc = Rutas.getRUC();
        String razonSocial = Rutas.getRazonSocial();
        String direccionLegal = Rutas.getDireccion();
        String qr = Rutas.getRutaQR() + "imagenqr_Factura.jpg";
        String logo = "\\src\\img\\logo.png";
        try {
            ResultSet rs = Factura.Consulta("select * from factura \n"
                    + "inner join cliente \n"
                    + "on cliente.id = factura.idCliente \n"
                    + "where factura.id = '" + id + "' ;");
            while (rs.next()) {
                numeroDocumento = rs.getString("numeroDocumento");
                nombreRazonSocial = rs.getString("nombreRazonSocial");
                direccion = rs.getString("direccion");
                fecha = rs.getString("fecha");
                moneda = rs.getString("moneda");
                totalVentasGravadas = rs.getString("totalVentasGravadas");
                igv = rs.getString("igv");
                if(moneda.equals("PEN")){
                    importeTotal = "S/ " + rs.getString("importeTotal");
                }else{
                    importeTotal = "US$ "+ rs.getString("importeTotal");
                }
                // enviamos el monto en texto
                montoEnTexto = Metodos.ConvertirNumTexto(String.valueOf(Metodos.FormatoDecimalMostrar(rs.getString("importeTotal"))), moneda);
                hash = rs.getString("hash");
            }
            rs.close();
        } catch (Exception e) {
        }
        //parámetros de envio al reporte
        Map parametro = new HashMap();
        parametro.put("numeroComprobante", numeroComprobante);
        parametro.put("numeroDocumento", numeroDocumento);
        parametro.put("nombreRazonSocial", nombreRazonSocial);
        parametro.put("direccion", direccion);
        parametro.put("fecha", fecha);
        parametro.put("moneda", moneda);
        parametro.put("totalVentasGravadas", totalVentasGravadas);
        parametro.put("igv", igv);
        parametro.put("importeTotal", importeTotal);
        parametro.put("montoEnTexto", montoEnTexto);
        parametro.put("hash", hash);
        parametro.put("qr", qr);
        parametro.put("ruc", ruc);
        parametro.put("razonSocial", razonSocial);
        parametro.put("direccionLegal", direccionLegal);
        parametro.put("logo", logo);
        // parametro para buscar el detalle
        parametro.put("id", id);
        try {
            Connection con = Conexion.getConexion();
            JasperPrint jp = JasperFillManager.fillReport("src\\repo\\Factura.jasper", parametro, con);
            JasperExportManager.exportReportToPdfFile(jp, Rutas.getRutaFacturaPDF(id));
            Metodos.MensajeInformacion("PDF guardado.");
            con.close();
            //abriendo el pdf
            try {
                File objetofile = new File(Rutas.getRutaFacturaPDF(id));
                Desktop.getDesktop().open(objetofile);
            } catch (Exception e) {
                System.out.println("Error abriendo factura: " + id + "\n" + e);
                Metodos.MensajeError("Error abriendo factura: " + id + "\n" + e);
            }
        } catch (Exception e) {
            System.out.println("Error creando PDF: " + id + "\n" + e);
            Metodos.MensajeError("Error creando PDF: " + id + "\n" + e);
        }
    }

}

package modelo;

import controlador.Conexion;
import controlador.Datos;
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
    
    static ResultSet rs;

    public static ResultSet consulta(String query) {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt;
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            return rs;
        } catch (Exception e) {
            //Metodos.MensajeError("Error: \n" + e);
            System.out.println("Error: \n" + e);
        }
        return null;
    }

    public static void registrarFactura(String id, String idCliente,
            String fecha, String horaEmision, String fechaVencimiento,
            String moneda, String formaPago, String cuotas, String montoCuota,
            String medioPago, String totalVentasGravadas, String totalGratuito,
            String igv, String importeTotal) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into factura \n"
                    + "(id,"
                    + "idCliente,"
                    + "fecha,"
                    + "horaEmision,"
                    + "fechaVencimiento,"
                    + "moneda,"
                    + "formaPago,"
                    + "cuotas,"
                    + "montoCuota,"
                    + "medioPago,"
                    + "totalVentasGravadas,"
                    + "totalGratuito,"
                    + "igv,"
                    + "importeTotal) \n"
                    + "values ('" + id + "','" 
                    + idCliente + "','" 
                    + fecha + "','" 
                    + horaEmision + "','" 
                    + fechaVencimiento + "','" 
                    + moneda + "','"
                    + formaPago + "','" 
                    + cuotas + "','" 
                    + montoCuota + "','" 
                    + medioPago + "','" 
                    + totalVentasGravadas + "','" 
                    + totalGratuito + "','" 
                    + igv + "','"
                    + importeTotal + "');";
            stmt.execute(sql);
            con.close();
        } catch (Exception e) {
            Metodos.mensajeError("Error: \n" + e);
        }
    }
    
    public static void registrarFacturaDet(String id, String idFactura,
            String item, String cantidad, String tipoUnidad, String codigo, String descripcion,
            String tributo, String montoTributo, String tipoAfectacionTributo,
            String valorUnitario,String valorUnitarioGratuito,
            String precioUnitarioItem) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into facturadet \n"
                    + "(id,idFactura,item,cantidad,tipoUnidad,codigo,"
                    + "descripcion,tributo,montotributo,tipoAfectacionTributo,"
                    + "valorUnitario,valorUnitarioGratuito,precioUnitarioItem) \n"
                    + "values ('" + id + "','"
                    + idFactura + "','"
                    + item + "','" 
                    + cantidad + "','"
                    + tipoUnidad + "','"
                    + codigo + "','"
                    + descripcion + "','"
                    + tributo + "','"
                    + montoTributo + "','"
                    + tipoAfectacionTributo + "','"
                    + valorUnitario + "','"
                    + valorUnitarioGratuito + "','"
                    + precioUnitarioItem + "');";
            stmt.execute(sql);
            con.close();
        } catch (Exception e) {
            Metodos.mensajeError("Error: \n" + e);
        }
    }
    
    public static boolean existeFactura(String id) {
        boolean existe = false;
        try {
            rs = consulta("select * \n"
                    + "from factura \n"
                    + "where id = '" + id + "';");
            if (!rs.next()) {
                existe = false;
           } else {
               do {
                   existe = true;
               } while (rs.next());
           }
            rs.close();
        } catch (Exception e) {
        }
        return existe;
    }
    
    public static void registrarHash(String id, String hash)
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
            Metodos.mensajeError("Error: \n" + e);
        }
    }
    
    public static void crearQR(String id, String hash){
        try {
            String ruc_emisor = Datos.getRUC();
            String tipo_comprobante = "01"; //indica una factura
            String corre_y_numero = id;
            String sumatoria_tributos = null;
            String total_precio_venta = null;
            String fecha_emision = null;//fecha en formato YYYY-MM-DD
            String tipo_documento_receptor = "6";//ruc
            String numero_documento_cliente = null;
            String CodigoHash = hash;
            String nombre_archivo = "imagenqr_Factura";
            
            ResultSet rs = Factura.consulta("select * \n"
                    + "from factura \n"
                    + "inner join cliente \n"
                    + "on cliente.id = factura.idCliente\n"
                    + "where factura.id = '" + id + "' ;");
            while (rs.next()) {
                sumatoria_tributos = rs.getString("igv");
                total_precio_venta = rs.getString("importeTotal");
                fecha_emision = Metodos.fechaFormatoSUNAT(rs.getString("fecha"));
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
            Metodos.mensajeError("Error creando QR: \n" +e);
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
        String numeroDocumento = "";
        String nombreRazonSocial = "";
        String direccion = "";
        String fecha = "";
        String fechaVencimiento = "";
        String moneda = "";
        String formaPago = "";
        // totales
        String totalVentasGravadas = "";
        String totalGratuito = "";
        String igv = "";
        String importeTotal = "";
        // otros
        String montoEnTexto = "";
        String hash = "aqui va el hash";
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
        String qr = Rutas.getRutaQR() + "imagenqr_Factura.jpg";
        String logo = "\\src\\img\\logo.png";
        try {
            rs = Factura.consulta("select * from factura \n"
                    + "inner join cliente \n"
                    + "on cliente.id = factura.idCliente \n"
                    + "where factura.id = '" + id + "' ;");
            while (rs.next()) {
                numeroDocumento = rs.getString("numeroDocumento");
                nombreRazonSocial = rs.getString("nombreRazonSocial");
                direccion = rs.getString("direccion");
                fecha = rs.getString("fecha");
                fechaVencimiento = rs.getString("fechaVencimiento");
                moneda = rs.getString("moneda");
                formaPago = rs.getString("formaPago");
                if(formaPago.equals("Credito")){
                    String cuotas = rs.getString("cuotas");
                    formaPago = formaPago + "\n" + cuotas + " cuotas";
                }
                totalVentasGravadas = rs.getString("totalVentasGravadas");
                totalGratuito = rs.getString("totalGratuito");
                igv = rs.getString("igv");
                hash = rs.getString("hash");
                if(moneda.equals("PEN")){
                    importeTotal = "S/ " + rs.getString("importeTotal");
                }else{
                    importeTotal = "US$ "+ rs.getString("importeTotal");
                }
                // enviamos el monto en texto
                montoEnTexto = Metodos.convertirNumTexto(String.valueOf(Metodos.formatoDecimalMostrar(Double.parseDouble(rs.getString("importeTotal")))), moneda);
                
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
        parametro.put("fechaVencimiento", fechaVencimiento);
        parametro.put("moneda", moneda);
        parametro.put("formaPago", formaPago);
        parametro.put("totalVentasGravadas", totalVentasGravadas);
        parametro.put("totalGratuito", totalGratuito);
        parametro.put("igv", igv);
        parametro.put("importeTotal", importeTotal);
        parametro.put("montoEnTexto", montoEnTexto);
        parametro.put("hash", hash);
        parametro.put("qr", qr);
        parametro.put("ruc", ruc);
        parametro.put("razonSocial", razonSocial);
        parametro.put("datosCabecera", datosCabecera);
        parametro.put("logo", logo);
        // parametro para buscar el detalle
        parametro.put("id", id);
        try {
            Connection con = Conexion.getConexion();
            JasperPrint jp = JasperFillManager.fillReport("src\\repo\\Factura-A4.jasper", parametro, con);
            JasperExportManager.exportReportToPdfFile(jp, Rutas.getRutaFacturaPDF(id));
            con.close();
            //abriendo el pdf
            try {
                File objetofile = new File(Rutas.getRutaFacturaPDF(id));
                Desktop.getDesktop().open(objetofile);
            } catch (Exception e) {
                System.out.println("Error abriendo factura: " + id + "\n" + e);
                Metodos.mensajeError("Error abriendo factura: " + id + "\n" + e);
            }
        } catch (Exception e) {
            System.out.println("Error creando PDF: " + id + "\n" + e);
            Metodos.mensajeError("Error creando PDF: " + id + "\n" + e);
        }
    }
    
    public static void crearPdfTicket80mm(String id) {
        // enviar a reporte
        String numeroComprobante = id;
        String numeroDocumento = "";
        String nombreRazonSocial = "";
        String direccion = "";
        String fecha = "";
        String horaEmision = "";
        String fechaVencimiento = "";
        String moneda = "";
        String formaPago = "";
        // totales
        String totalVentasGravadas = "";
        String totalGratuito = "";
        String igv = "";
        String importeTotal = "";
        // otros
        String montoEnTexto = "";
        String hash = "aqui va el hash";
        // ========= Datos de nuestra empresa =============
        String ruc = Datos.getRUC();
        String razonSocial = Datos.getRazonSocial();
        String direccionLegal = Datos.getDireccion();
        String telefono = Datos.getTelefono();
        String correo = Datos.getCorreo();
        String web = Datos.getWeb();
        // ================================================
        String qr = Rutas.getRutaQR() + "imagenqr_Factura.jpg";
        String logo = "\\src\\img\\logo.png";
        try {
            ResultSet rs = Factura.consulta("select * from factura \n"
                    + "inner join cliente \n"
                    + "on cliente.id = factura.idCliente \n"
                    + "where factura.id = '" + id + "' ;");
            while (rs.next()) {
                numeroDocumento = rs.getString("numeroDocumento");
                nombreRazonSocial = rs.getString("nombreRazonSocial");
                direccion = rs.getString("direccion");
                fecha = rs.getString("fecha");
                horaEmision = rs.getString("horaEmision");
                fechaVencimiento = rs.getString("fechaVencimiento");
                moneda = rs.getString("moneda");
                formaPago = rs.getString("formaPago").toUpperCase();
                if(formaPago.equals("CREDITO")){
                    String cuotas = rs.getString("cuotas");
                    formaPago = formaPago + " " + cuotas + " CUOTAS";
                }
                totalVentasGravadas = rs.getString("totalVentasGravadas");
                totalGratuito = rs.getString("totalGratuito");
                igv = rs.getString("igv");
                hash = rs.getString("hash");
                if(moneda.equals("PEN")){
                    importeTotal = "S/ " + rs.getString("importeTotal");
                }else{
                    importeTotal = "US$ "+ rs.getString("importeTotal");
                }
                // enviamos el monto en texto
                montoEnTexto = Metodos.convertirNumTexto(String.valueOf(Metodos.formatoDecimalMostrar(Double.parseDouble(rs.getString("importeTotal")))), moneda);
                
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
        parametro.put("horaEmision", horaEmision);
        parametro.put("fechaVencimiento", fechaVencimiento);
        parametro.put("moneda", moneda);
        parametro.put("formaPago", formaPago);
        parametro.put("totalVentasGravadas", totalVentasGravadas);
        parametro.put("totalGratuito", totalGratuito);
        parametro.put("igv", igv);
        parametro.put("importeTotal", importeTotal);
        parametro.put("montoEnTexto", montoEnTexto);
        parametro.put("hash", hash);
        parametro.put("qr", qr);
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
            JasperPrint jp = JasperFillManager.fillReport("src\\repo\\Factura-Ticket80mm.jasper", parametro, con);
            JasperExportManager.exportReportToPdfFile(jp, Rutas.getRutaFacturaPDF(id));
            con.close();
            //abriendo el pdf
            try {
                File objetofile = new File(Rutas.getRutaFacturaPDF(id));
                Desktop.getDesktop().open(objetofile);
            } catch (Exception e) {
                System.out.println("Error abriendo factura: " + id + "\n" + e);
                Metodos.mensajeError("Error abriendo factura: " + id + "\n" + e);
            }
        } catch (Exception e) {
            System.out.println("Error creando PDF: " + id + "\n" + e);
            Metodos.mensajeError("Error creando PDF: " + id + "\n" + e);
        }
    }

}

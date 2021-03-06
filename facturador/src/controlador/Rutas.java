package controlador;

import java.sql.ResultSet;
import modelo.Config;

public class Rutas {
    
    static ResultSet rs;
    
    public static String getRUC(){
        String ruc = null;
        try {
            rs = Config.Consulta("select ruc \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                ruc = rs.getString("ruc");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando ruc: \n" + e);
            Metodos.MensajeError("Error cargando ruc: \n" + e);
        }
        return ruc;
    }
    
    public static String getRazonSocial(){
        String razonSocial = null;
        try {
            rs = Config.Consulta("select razonSocial \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                razonSocial = rs.getString("razonSocial");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando razon social: \n" + e);
            Metodos.MensajeError("Error cargando razon social: \n" + e);
        }
        return razonSocial;
    }
    
    public static String getDireccion(){
        String direccion = null;
        try {
            rs = Config.Consulta("select direccion \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                direccion = rs.getString("direccion");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando direccion: \n" + e);
            Metodos.MensajeError("Error cargando direccion: \n" + e);
        }
        return direccion;
    }
    
    public static String getRutaSunat(){
        String rutaSunat = null;
        try {
            rs = Config.Consulta("select rutaSunat \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                rutaSunat = rs.getString("rutaSunat");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando ruta de SUNAT: \n" + e);
            Metodos.MensajeError("Error cargando ruta de SUNAT: \n" + e);
        }
        return rutaSunat;
    }
    
    public static String getRutaAP(String tipoDocumento,String nombre, String ext){
        String ruta = getRutaSunat() + "\\sunat_archivos\\sfs\\DATA\\"
                +getRUC()+"-"+tipoDocumento+"-" + nombre + "." + ext;
        return ruta;
    }
    
    public static String getRutaAPBaja(String nombre) {
        String ruta = getRutaSunat() + "\\sunat_archivos\\sfs\\DATA\\"
                + getRUC() + "-" +nombre + ".CBA";
        return ruta;
    }
    
    public static String getRutaPdf(){
        String rutaPdf = null;
        try {
            rs = Config.Consulta("select rutaPdf \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                rutaPdf = rs.getString("rutaPdf");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando ruta de SUNAT: \n" + e);
            Metodos.MensajeError("Error cargando ruta de SUNAT: \n" + e);
        }
        return rutaPdf;
    }
  
    public static String getRutaFacturaPDF(String id) {
        String ruta = getRutaPdf() + "\\Facturas\\" + id + ".pdf";
        return ruta;
    }
    
    public static String getRutaBajaPDF(String id) {
        String ruta = getRutaPdf() + "\\Bajas\\" + id + ".pdf";
        return ruta;
    }
    
    public static String getRutaHash(String tipoDocumento, String nombre) {
        String ruta = getRutaSunat() + "\\sunat_archivos\\sfs\\FIRMA\\"
                + getRUC() + "-" + tipoDocumento + "-" + nombre + ".xml";
        return ruta;
    }
    
    public static String getRutaQR(){
        String ruta = getRutaSunat() + "\\sunat_archivos\\sfs\\ORIDAT\\";
        return ruta;
    }
    
}

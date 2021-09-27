package controlador;

import java.io.File;
import java.sql.ResultSet;
import modelo.Config;

public class Rutas {
    
    static ResultSet rs;
    
    public static String getRutaConexionBD(){
        return "env.txt";
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
                + Datos.getRUC()+"-"+tipoDocumento+"-" + nombre + "." + ext;
        return ruta;
    }
    
    public static String getRutaAPBaja(String nombre) {
        String ruta = getRutaSunat() + "\\sunat_archivos\\sfs\\DATA\\"
                + Datos.getRUC() + "-" +nombre + ".CBA";
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
            System.out.println("Error cargando ruta de los PDFs: \n" + e);
            Metodos.MensajeError("Error cargando ruta de los PDFs: \n" + e);
        }
        return rutaPdf;
    }
  
    public static String getRutaFacturaPDF(String id) {
        String ruta = getRutaPdf() + "\\Facturas\\" + id + ".pdf";
        File directorio = new File(getRutaPdf() + "\\Facturas");
        directorio.mkdirs();
        return ruta;
    }
    
    public static String getRutaBoletaPDF(String id) {
        String ruta = getRutaPdf() + "\\Boletas\\" + id + ".pdf";
        return ruta;
    }
    
    public static String getRutaNotaCreditoPDF(String id) {
        String ruta = getRutaPdf() + "\\Notas Credito\\" + id + ".pdf";
        return ruta;
    }
    
    public static String getRutaNotaDebitoPDF(String id) {
        String ruta = getRutaPdf() + "\\Notas Debito\\" + id + ".pdf";
        return ruta;
    }
    
    public static String getRutaBajaPDF(String id) {
        String ruta = getRutaPdf() + "\\Bajas\\" + id + ".pdf";
        return ruta;
    }
    
    public static String getRutaHash(String tipoDocumento, String nombre) {
        String ruta = getRutaSunat() + "\\sunat_archivos\\sfs\\FIRMA\\"
                + Datos.getRUC() + "-" + tipoDocumento + "-" + nombre + ".xml";
        return ruta;
    }
    
    public static String getRutaQR(){
        String ruta = getRutaSunat() + "\\sunat_archivos\\sfs\\ORIDAT\\";
        return ruta;
    }
    
}

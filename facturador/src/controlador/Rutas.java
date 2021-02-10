package controlador;

//ruta archivos planos SFS 1.3.4.2

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
  
    public static String getRutaFacturaPDF(String IdFactura) {
        String ruta = getRutaSunat() + "\\sunat_archivos\\sfs\\REPO\\" + getRUC() + "-01-" + IdFactura + ".pdf";
        return ruta;
    }
    
}

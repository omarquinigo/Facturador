
package modelo;

import controlador.Conexion;
import controlador.Metodos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


}

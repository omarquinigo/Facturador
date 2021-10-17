package modelo;

import controlador.Conexion;
import controlador.Metodos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cliente {
    
    static ResultSet rs;

    public static ResultSet Consulta(String query) {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt;
            stmt = con.createStatement();
            ResultSet respuesta = stmt.executeQuery(query);
            return respuesta;
        } catch (Exception e) {
            Metodos.mensajeError("Error: \n" + e);
        }
        return null;
    }
    
    public static String generarIdCliente() {
        String Id = "";
        try {
            rs = Cliente.Consulta("select id \n"
                    + "from cliente \n"
                    + "ORDER BY CAST(id as SIGNED );");
            if(rs.last() == true){
                String Codigo = rs.getString("id");
                //String Codigo = ("50");
                int numero_cliente = Integer.parseInt(Codigo) + 1;//convierto a entero para sumar +1
                String CodigoCliente = String.valueOf(numero_cliente);
                Id = CodigoCliente;
            }   else {
                Id = "1";
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error generando id de cliente: \n" + e);
            Metodos.mensajeError("Error generando id de cliente: \n" + e);
        }
        return Id;
    }

    public static void Registrar(String id, String tipoDocumento,
            String numeroDocumento, String nombreRazonSocial,
            String direccion) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into cliente \n"
                    + "(id,tipoDocumento,numeroDocumento, \n"
                    + "nombreRazonSocial,direccion) \n"
                    + "values ('" + id + "','" + tipoDocumento + "','"
                    + numeroDocumento + "','" + nombreRazonSocial + "','"
                    + direccion + "');";
            stmt.execute(sql);
            con.close();
            Metodos.mensajeInformacion("Cliente registrado");
        } catch (Exception e) {
            Metodos.mensajeError("Error registrando cliente: \n" + e);
        }
    }
    
    public static void RegistrarComprobante(String id, String tipoDocumento,
            String numeroDocumento, String nombreRazonSocial,
            String direccion) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "insert into cliente \n"
                    + "(id,tipoDocumento,numeroDocumento, \n"
                    + "nombreRazonSocial,direccion) \n"
                    + "values ('" + id + "','" + tipoDocumento + "','"
                    + numeroDocumento + "','" + nombreRazonSocial + "','"
                    + direccion + "');";
            stmt.execute(sql);
            con.close();
        } catch (Exception e) {
            Metodos.mensajeError("Error registrando cliente: \n" + e);
        }
    }
    
    
    
    public static void Actualizar(String id, String tipoDocumento,
            String numeroDocumento, String nombreRazonSocial,
            String direccion) throws SQLException {
        try {
            Connection con = Conexion.getConexion();
            Statement stmt = con.createStatement();
            String sql = "update cliente \n"
                    + "set \n"
                    + "tipoDocumento = '" + tipoDocumento + "', "
                    + "numeroDocumento = '" + numeroDocumento + "', "
                    + "nombreRazonSocial = '" + nombreRazonSocial + "', "
                    + "direccion = '" + direccion + "' \n"
                    + "where id = '" + id + "';";
            stmt.execute(sql);
            con.close();
            Metodos.mensajeInformacion("Cliente actualizado");
        } catch (Exception e) {
            Metodos.mensajeError("Error actualizando cliente.\n" + e);
        }
    }

}

package controlador;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.stream.Stream;
import modelo.Config;

public class Datos {
    
    static ResultSet rs;
    
    public static String getHost() {
        String host = null;
        Path filePath = Paths.get(Rutas.getRutaConexionBD());

        try (Stream<String> lines = Files.lines(filePath)) {
            host = lines.skip(0).findFirst().get().substring(5);
        } catch (Exception e) {
            System.out.println("Error cargando host.");
        }
        return host;
    }
    
    public static String getPuerto(){
        String puerto = null;
        Path filePath = Paths.get(Rutas.getRutaConexionBD());

        try (Stream<String> lines = Files.lines(filePath)) {
            puerto = lines.skip(1).findFirst().get().substring(7);
        } catch (Exception e) {
            System.out.println("Error cargando puerto.");
        }
        return puerto;
    }
    
    public static String getBaseDatos(){
        String baseDatos = null;
        Path filePath = Paths.get(Rutas.getRutaConexionBD());

        try (Stream<String> lines = Files.lines(filePath)) {
            baseDatos = lines.skip(2).findFirst().get().substring(10);
        } catch (Exception e) {
            System.out.println("Error cargando baseDatos.");
        }
        return baseDatos;
    }
    
    public static String getUsuario(){
        String baseDatos = null;
        Path filePath = Paths.get(Rutas.getRutaConexionBD());

        try (Stream<String> lines = Files.lines(filePath)) {
            baseDatos = lines.skip(3).findFirst().get().substring(8);
        } catch (Exception e) {
            System.out.println("Error cargando usuario.");
        }
        return baseDatos;
    }
    
    public static String getContrasena(){
        String contrasena = null;
        Path filePath = Paths.get(Rutas.getRutaConexionBD());

        try (Stream<String> lines = Files.lines(filePath)) {
            contrasena = lines.skip(4).findFirst().get().substring(11);
        } catch (Exception e) {
            System.out.println("Error cargando contrasena.");
        }
        return contrasena;
    }
    
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
            //Metodos.MensajeError("Error cargando razon social: \n" + e);
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
            //Metodos.MensajeError("Error cargando direccion: \n" + e);
        }
        return direccion;
    }
    
    public static String getTelefono(){
        String telefono = null;
        try {
            rs = Config.Consulta("select telefono \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                telefono = rs.getString("telefono");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando telefono: \n" + e);
            //Metodos.MensajeError("Error cargando telefono: \n" + e);
        }
        return telefono;
    }
    
    public static String getCorreo(){
        String correo = null;
        try {
            rs = Config.Consulta("select correo \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                correo = rs.getString("correo");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando correo: \n" + e);
            //Metodos.MensajeError("Error cargando correo: \n" + e);
        }
        return correo;
    }
    
    public static String getWeb(){
        String web = null;
        try {
            rs = Config.Consulta("select web \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                web = rs.getString("web");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando web: \n" + e);
            //Metodos.MensajeError("Error cargando web: \n" + e);
        }
        return web;
    }
    
    public static String getSerieNCreditoFactura(){
        String serieNCreditoFactura = null;
        try {
            rs = Config.Consulta("select serieNCreditoFactura \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                serieNCreditoFactura = rs.getString("serieNCreditoFactura");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando serieNCreditoFactura: \n" + e);
            //Metodos.MensajeError("Error cargando web: \n" + e);
        }
        return serieNCreditoFactura;
    }
    
    public static String getSerieNCreditoBoleta(){
        String serieNCreditoBoleta = null;
        try {
            rs = Config.Consulta("select serieNCreditoBoleta \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                serieNCreditoBoleta = rs.getString("serieNCreditoBoleta");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando serieNCreditoFactura: \n" + e);
            //Metodos.MensajeError("Error cargando web: \n" + e);
        }
        return serieNCreditoBoleta;
    }
    
    public static String getSerieNDebitoFactura(){
        String serieNDebitoFactura = null;
        try {
            rs = Config.Consulta("select serieNDebitoFactura \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                serieNDebitoFactura = rs.getString("serieNDebitoFactura");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando serieNDebitoFactura: \n" + e);
            //Metodos.MensajeError("Error cargando web: \n" + e);
        }
        return serieNDebitoFactura;
    }
    
    public static String getSerieNDebitoBoleta(){
        String serieNDebitoBoleta = null;
        try {
            rs = Config.Consulta("select serieNDebitoBoleta \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                serieNDebitoBoleta = rs.getString("serieNDebitoBoleta");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando serieNDebitoBoleta: \n" + e);
            //Metodos.MensajeError("Error cargando web: \n" + e);
        }
        return serieNDebitoBoleta;
    }
    
    public static String getImpresion(){
        String impresion = null;
        try {
            rs = Config.Consulta("select impresion \n"
                    + "from config \n"
                    + "where id = '1';");
            while (rs.next()) {
                impresion = rs.getString("impresion");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando impresion: \n" + e);
            //Metodos.MensajeError("Error cargando impresion: \n" + e);
        }
        return impresion;
    }
    
}

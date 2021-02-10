package controlador;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import static vista.JFramePrincipal.jpnlPrincipal;

public class Metodos {
    
    public static void ConfigurarVentana(JFrame jFrame,String tituloVentana){
        //posiciono el frame al centro de la pantalla
        jFrame.setLocationRelativeTo(null);
        //activa el cambio de tamaño de la ventana
        jFrame.setResizable(false);
        //asigno titulo a mostrar del frame
        jFrame.setTitle(tituloVentana);
        //configurando logo
        Image icono = new ImageIcon(jFrame.getClass().getResource("/img/icono.png")).getImage();//carga icono
        jFrame.setIconImage(icono);//coloca el icono en la barra de título
    }
    
    public static void MensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error :(", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void MensajeInformacion(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Mensaje :)", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void MensajeAlerta(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Alerta :|", JOptionPane.WARNING_MESSAGE);
    }
    public static void CambiarPanel(JPanel jPanel){
        Dimension d = jpnlPrincipal.getSize();//capturamos la dimensión del panel actual
        jPanel.setSize(d);//le enviamos el mismo tamaño del panel principal
        jPanel.setLocation(0,0);//que se inice desde el punto 0,0
        jpnlPrincipal.removeAll();//quitamos los demás complementos
        jpnlPrincipal.add(jPanel, BorderLayout.CENTER);
        jpnlPrincipal.revalidate();
        jpnlPrincipal.repaint();
    }
    
    public static String ObtenerUltimosXCaracteres(String cadena, int caracteres) {
        String ultimos_caracteres = cadena.substring(cadena.length() - caracteres, cadena.length());//capturo ultimos 8 digitos
        return ultimos_caracteres;// retorno lo últimos 8 digitos
    }
    
    // dd/MM/yyyy
    public static String CargarFechaActual() {
        String fecha_actual;
        Date fecha = new Date();//se crea objeto de tipo Date
        fecha_actual = new SimpleDateFormat("dd/MM/yyyy").format(fecha);//envio al text field al mismo tiempo se cambia el formato
        return fecha_actual;
    } 
    
    public static Frame FormatoJDialog(){
        JPanel panel = new JPanel();
        Window parentWindow = SwingUtilities.windowForComponent(panel);
        // or pass 'this' if you are inside the panel
        Frame parentFrame = null;
        if (parentWindow instanceof Frame) {
            parentFrame = (Frame) parentWindow;
        }
        return parentFrame;
    }
    
    public static void ActulizarNumeroItem(JTable table) {
        int filas = table.getRowCount();
        for (int i = 0; i < filas; i++) {
            table.setValueAt(i + 1, i, 0);
        }
    }
    
    public static String ConvertirNumTexto(String numero, String moneda) {
        String moneda_texto;
        ConvertirNumeroTexto NumLetra = new ConvertirNumeroTexto();
        if (moneda.equalsIgnoreCase("PEN")) {
            moneda_texto = "SOLES.";
        } else if (moneda.equalsIgnoreCase("USD")) {
            moneda_texto = "DÓLARES AMERICANOS.";
        } else {
            moneda_texto = "";
        }
        String letritas = (NumLetra.Convertir(numero, band()));
        return letritas + moneda_texto;
    }
    
    //para el numero en letras
    private static boolean band() {
        if (Math.random() > .5) {
            return true;
        } else {
            return false;
        }
    }
    
    // YYYY-MM-DD según SUNAT
    public static String FechaActualFormatoSUNAT() {
        //se crea objeto de tipo Date
        Date fecha = new Date();
        //envio al text field al mismo tiempo se cambia el formato
        String fecha_sunat = (new SimpleDateFormat("yyyy-MM-dd").format(fecha));
        return fecha_sunat;
    }
    
    public static String ObtenerHora() {
        Calendar calendario = Calendar.getInstance();
        String hh, mm, ss;
        hh = String.format("%02d", (calendario.get(Calendar.HOUR_OF_DAY)));
        mm = String.format("%02d", (calendario.get(Calendar.MINUTE)));
        ss = String.format("%02d", (calendario.get(Calendar.SECOND)));
        String hora_completa = hh + ":" + mm + ":" + ss;
        return hora_completa;
    }
    
    public static void AbrirPDF(String carpeta, String nombre){
        try {
            File archivo = null;
            if(carpeta.equalsIgnoreCase("Factura")){
                archivo = new File(Rutas.getRutaFacturaPDF(nombre));
            }
            Desktop.getDesktop().open(archivo);
        } catch (Exception e) {
            System.out.println("Error abriendo PDF: " + nombre + ".pdf\n" + e);
            Metodos.MensajeError("Error abriendo PDF: " + nombre + ".pdf\n" + e);
        }
    }
    
}

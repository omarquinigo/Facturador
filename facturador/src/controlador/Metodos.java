package controlador;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
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
    
    //puede llegar a tener hasta 4 decimales
    public static String FormatoDecimalOperar(Double numero){
        DecimalFormat df = new DecimalFormat("#0.00##");
        String num = df.format(numero);
        return num;
    }
    
    // 2 decimales
    public static String FormatoDecimalMostrar(String numero){
        double n = Double.parseDouble(numero);
        DecimalFormat df = new DecimalFormat("#0.00");
        String num = df.format(n);
        return num;
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
    
    public static String FechaFormatoSUNAT(String fecha){
        //se crea objeto de tipo Date
        String dia = fecha.substring(0,2);
        String mes = fecha.substring(3,5);
        String anio = fecha.substring(6,10);
        //envio al text field al mismo tiempo se cambia el formato
        String fecha_sunat = anio+"-"+mes+"-"+dia;
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
    
    public static Boolean ExistePDF(String carpeta,String id){
        Boolean b;
        File archivo = null;
        if (carpeta.equalsIgnoreCase("Facturas")){
            archivo = new File(Rutas.getRutaFacturaPDF(id));
        } else if (carpeta.equalsIgnoreCase("Boletas")){
            archivo = new File(Rutas.getRutaBoletaPDF(id));
        } else if (carpeta.equalsIgnoreCase("NotasCredito")){
            archivo = new File(Rutas.getRutaNotaCreditoPDF(id));
        } else if (carpeta.equalsIgnoreCase("NotasDebito")){
            archivo = new File(Rutas.getRutaNotaDebitoPDF(id));
        } else {
            archivo = new File(Rutas.getRutaBajaPDF(id));
        }
        if (archivo.exists())
            b = true;
        else
            b = false;
        return b;
    }
    
    public static void AbrirPDF(String carpeta, String nombre){
        try {
            File archivo = null;
            if(carpeta.equalsIgnoreCase("Factura")){
                archivo = new File(Rutas.getRutaFacturaPDF(nombre));
            } else if(carpeta.equalsIgnoreCase("Boleta")){
                archivo = new File(Rutas.getRutaBoletaPDF(nombre));
            } else if(carpeta.equalsIgnoreCase("NotaCredito")){
                archivo = new File(Rutas.getRutaNotaCreditoPDF(nombre));
            } else if(carpeta.equalsIgnoreCase("NotaDebito")){
                archivo = new File(Rutas.getRutaNotaDebitoPDF(nombre));
            } else {
                archivo = new File(Rutas.getRutaBajaPDF(nombre));
            }
            Desktop.getDesktop().open(archivo);
        } catch (Exception e) {
            System.out.println("Error abriendo PDF: " + nombre + ".pdf\n" + e);
            Metodos.MensajeError("Error abriendo PDF: " + nombre + ".pdf\n" + e);
        }
    }
    
    public static String getHash(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        String linea5 = null;// se encuentra hash en XML
        String hash;
        
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        
        long numeroLinea = 0;
        while ((cadena = br.readLine()) != null) {
            numeroLinea ++;
            if(numeroLinea == 5)
                linea5 = cadena;
        }
        br.close();
        hash = linea5.substring(linea5.indexOf("<ds:DigestValue>"),linea5.indexOf("</ds:DigestValue>"));
        //eliminando los primeros 16 caracteres
        hash = hash.substring(16);
        return hash;
    }
    
    public static void LimpiarTabla(JTable tabla) {
        //limpiar jtable para que no se dupliquen datos
        DefaultTableModel dtm = (DefaultTableModel) tabla.getModel();
        int a = tabla.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            dtm.removeRow(dtm.getRowCount() - 1);
        }
    }
    
    //convierte a String un JDateChooser
    public static String CapturarDateChooser(JDateChooser jDateChooser){
        String fecha_final = "error";
        try {
            if(jDateChooser.getDate() == null){//Si es nulo
                fecha_final = "";
            } else {//si tiene una fecha válida
                String formato_fecha = jDateChooser.getDateFormatString();
                Date fecha = jDateChooser.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat(formato_fecha);
                fecha_final = String.valueOf(sdf.format(fecha));
            }
        } catch (Exception e) {
            Metodos.MensajeError("Error al capturar el JdateChooser 'jDateChooser'\n"+e);
        }
        return fecha_final;
    }
    
    // YYYYMMDD
    public static String FechaActualFormatoSUNATSinGuiones(){
        String fecha = FechaActualFormatoSUNAT();
        return fecha.replace("-", "");
    }
    
    // obtener código de medio de Pago Catálogo 59
    public static String ObtenerCodigoMedioPago(String mp){
        String codigo;
        if(mp.equalsIgnoreCase("Depósito en cuenta")){
            codigo = "001";
        }else if (mp.equalsIgnoreCase("Giro")){
            codigo = "002";
        }else if (mp.equalsIgnoreCase("Transferencia de fondos")){
            codigo = "003";
        }else if (mp.equalsIgnoreCase("Orden de pago")){
            codigo = "004";
        }else if (mp.equalsIgnoreCase("Tarjeta de débito")){
            codigo = "005";
        }else if (mp.equalsIgnoreCase("Efectivo")){
            codigo = "008";
        }else if (mp.equalsIgnoreCase("Tarjeta de crédito")){
            codigo = "012";
        }else{
            codigo = "999";
        }
        return codigo;
    }
    
    //catalogo 9
    public static String CodigoTipoNotaCredito (JComboBox jcbxTipoNotaCredito){
        String tipo_nota_credito;
        int indice = jcbxTipoNotaCredito.getSelectedIndex();
        if (indice == 1) {
            tipo_nota_credito = "01";
        } else if (indice == 2) {
            tipo_nota_credito = "02";
        } else if (indice == 3) {
            tipo_nota_credito = "03";
        } else if (indice == 4) {
            tipo_nota_credito = "04";
        } else if (indice == 5) {
            tipo_nota_credito = "05";
        } else if (indice == 6) {
            tipo_nota_credito = "06";
        } else if (indice == 7) {
            tipo_nota_credito = "07";
        } else if (indice == 8) {
            tipo_nota_credito = "08";
        } else if (indice == 9) {
            tipo_nota_credito = "09";
        } else {
            tipo_nota_credito = "error";
        }
        return tipo_nota_credito;
    }
    
    //catalogo 10
    public static String CodigoTipoNotaDebito (JComboBox jcbxTipoNotaDebito){
        String tipo_nota_debito;
        int indice = jcbxTipoNotaDebito.getSelectedIndex();
        if (indice == 1) {
            tipo_nota_debito = "01";
        } else if (indice == 2) {
            tipo_nota_debito = "02";
        } else if (indice == 3) {
            tipo_nota_debito = "03";
        } else {
            tipo_nota_debito = "error";
        } 
        return tipo_nota_debito;
    }
    
    public static String ObtenerCodigoTipoDocumento(String tipoDoc){
        String codigo_tipo_documento = "";
        if(tipoDoc.equalsIgnoreCase("RUC")){
            codigo_tipo_documento = "6";
        } else if(tipoDoc.equalsIgnoreCase("DNI")){
            codigo_tipo_documento = "1";
        } else if(tipoDoc.equalsIgnoreCase("Carne de extrangeria")){
            codigo_tipo_documento = "4";
        } else codigo_tipo_documento = "7";
        return codigo_tipo_documento;
    }
    
}

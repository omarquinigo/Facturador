package controlador;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.sql.ResultSet;
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
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Cliente;
import vista.JFramePrincipal;
import static vista.JFramePrincipal.jpnlPrincipal;

public class Metodos {
    
    static TableRowSorter trs;
    static ResultSet rs;
    
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
            Metodos.mensajeError("Error abriendo PDF: " + nombre + ".pdf\n" + e);
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
            Metodos.mensajeError("Error al capturar el JdateChooser 'jDateChooser'\n"+e);
        }
        return fecha_final;
    }

    // ========= Métodos ==========
    
    public static void configurarVentanaJFrame(JFrame jFrame,String tituloVentana){
        //posiciono el frame al centro de la pantalla
        jFrame.setLocationRelativeTo(null);
        //activa el cambio de tamaño de la ventana
        jFrame.setResizable(true);
        //asigno titulo a mostrar del frame
        jFrame.setTitle(tituloVentana);
        //configurando logo
        Image icono = new ImageIcon(jFrame.getClass().getResource("/img/icono.png")).getImage();//carga icono
        jFrame.setIconImage(icono);//coloca el icono en la barra de título
    }
    
    public static void cambiarPanel(JPanel jPanel){
        JFramePrincipal.jpActivo = jPanel;// envia el panel actual
        Dimension d = jpnlPrincipal.getSize();//capturamos la dimensión del panel actual
        jPanel.setSize(d);//le enviamos el mismo tamaño del panel principal
        jPanel.setLocation(0,0);//que se inice desde el punto 0,0
        jpnlPrincipal.removeAll();//quitamos los demás complementos
        jpnlPrincipal.add(jPanel, BorderLayout.CENTER);
        jpnlPrincipal.revalidate();
        jpnlPrincipal.repaint();
        // el tamaño de ventana es 1050 x 600
    }
    
    public static void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error :(", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void mensajeInformacion(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Mensaje :)", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void mensajeAlerta(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Alerta :|", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void filtrarProducto(JTextField jtxt, int columna1,int columna2, DefaultTableModel tableModel, JTable table){
        jtxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                trs.setRowFilter(RowFilter.regexFilter("(?i)"+jtxt.getText(), columna1, columna2));
            }
        });
        trs = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(trs);
    }
    
    public static void filtrarServicio(JTextField jtxt, int columna1,int columna2, DefaultTableModel tableModel, JTable table){
        jtxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                trs.setRowFilter(RowFilter.regexFilter("(?i)"+jtxt.getText(), columna1, columna2));
            }
        });
        trs = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(trs);
    }
    
    public static void filtrarCliente(JTextField jtxt, int columna1,int columna2, DefaultTableModel tableModel, JTable table){
        jtxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                trs.setRowFilter(RowFilter.regexFilter("(?i)"+jtxt.getText(), columna1, columna2));
            }
        });
        trs = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(trs);
    }
    
    public static void filtrarFactura(JTextField jtxt, int columna1,int columna2, DefaultTableModel tableModel, JTable table){
        jtxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                trs.setRowFilter(RowFilter.regexFilter("(?i)"+jtxt.getText(), columna1, columna2));
            }
        });
        trs = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(trs);
    }
    
    public static void filtrarBoleta(JTextField jtxt, int columna1,int columna2, DefaultTableModel tableModel, JTable table){
        jtxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                trs.setRowFilter(RowFilter.regexFilter("(?i)"+jtxt.getText(), columna1, columna2));
            }
        });
        trs = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(trs);
    }
    
    public static void filtrarNotaCredito(JTextField jtxt,
            int columna1,int columna2,
            int columna3,int columna4,
            DefaultTableModel tableModel, JTable table){
        jtxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                trs.setRowFilter(RowFilter.regexFilter("(?i)"+jtxt.getText(),
                        columna1, columna2,columna3, columna4));
            }
        });
        trs = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(trs);
    }
    
    public static void filtrarNotaDebito(JTextField jtxt,
            int columna1,int columna2,
            int columna3,int columna4,
            DefaultTableModel tableModel, JTable table){
        jtxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                trs.setRowFilter(RowFilter.regexFilter("(?i)"+jtxt.getText(),
                        columna1, columna2,columna3, columna4));
            }
        });
        trs = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(trs);
    }
    
    public static void ValidarDecimalTXT(java.awt.event.KeyEvent evt, JTextField campo) {////****mejorar metodo
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car < '.' || car > '.')) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
                && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && campo.getText().contains(".")) {
            evt.consume();
        }
    }
    
    public static void ValidarDecimalCBX(java.awt.event.KeyEvent evt, JComboBox combo) {////****mejorar metodo
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car < '.' || car > '.')) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
                && (c != '.')) {
            evt.consume();
        }
        if (c == '.' && combo.getSelectedItem().toString().contains(".")) {
            evt.consume();
        }
    }
    
    public static String validarCliente(String tipoDocumento,
            String numeroDocumento, String nombreRazonSocial, String direccion){
        String mensaje = "";
        
        if (nombreRazonSocial.equals("")) {
            mensaje = "Escriba el nombre o razón social.";
        }
        
        if (tipoDocumento.equals("---SELECCIONE---")) {
            mensaje = "Seleccione el tipo de documento.";
        } else {
            if (numeroDocumento.equals("")) {
                mensaje = "Escriba el número de documento.";
            } else if (numeroDocumento != ("")) {
                if (tipoDocumento.equals("DNI")) {
                    if (numeroDocumento.length() != 8) {
                        mensaje = "El " + tipoDocumento + " debe tener 8 dígitos.";
                    }
                } else if (tipoDocumento.equals("RUC")) {
                    if (numeroDocumento.length() != 11) {
                        mensaje = "El " + tipoDocumento + " debe tener 11 dígitos.";
                    }
                } else if (tipoDocumento.equals("Carnet de extranjería")) {
                    if (numeroDocumento.length() != 12) {
                        mensaje = "El " + tipoDocumento + " debe tener 12 dígitos.";
                    }
                } else if (tipoDocumento.equals("Pasaporte")) {
                    if (numeroDocumento.length() != 12) {
                        mensaje = "El " + tipoDocumento + " debe tener 12 dígitos.";
                    }
                }
            }
        }
        
        return mensaje;
    }
    
    public static boolean existeCliente(String id, String numeroDocumento) {
        boolean existe = false;
        try {
            rs = Cliente.Consulta("select * \n"
                    + "from cliente \n"
                    + "where numeroDocumento = '" + numeroDocumento + "' "
                    + "and id <> '" + id + "'  ;");
            while (rs.next()) {
                existe = true;
            }
            rs.close();
        } catch (Exception e) {
        }
        return existe;
    }
    
    public static String validarFechaVencimiento(JDateChooser Fecha){
        String mensaje = "";
        if (((JTextField) Fecha.getDateEditor().getUiComponent()).getText().isEmpty()) {
            //mensaje = "No hay datos: (-)";
        } else {
            Date d = Fecha.getDate();
            if (d == null) {
                mensaje = "Fecha de vencimiento errónea, corrija o deje blanco.";
            }
        }
        return mensaje;
        
    }
    
    public static String getFechaJDC(JDateChooser Fecha) {
        String fecha = "";
        try {
            Date d = Fecha.getDate();

            if (d == null) {
                fecha = "-";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");                
                fecha = sdf.format(d);
            }
        } catch (Exception e) {}

        return fecha;
    }
    
    public static boolean validarExisteAPFactura(String id){
        boolean existe;
        
        String tipoComprobante[] = Catalogos.tipoDocumento("", "Factura");
        String rutaData = Rutas.getRutaSunat() + "\\sunat_archivos\\sfs\\DATA\\" + Datos.getRUC() + "-" + tipoComprobante[0] + "-" + id;
        
        File apCab = new File(rutaData + ".CAB");
        File apDet = new File(rutaData + ".DET");
        File apTri = new File(rutaData + ".TRI");
        File apLey = new File(rutaData + ".LEY");

        //File archivo = null;
        if (apCab.exists() || apDet.exists() ||
                apTri.exists() || apLey.exists()) {
            existe = true;
        } else {
            existe = false;
        }
        return existe;
    }
    
    public static boolean validarExisteAPBoleta(String id){
        boolean existe;
        
        String tipoComprobante[] = Catalogos.tipoDocumento("", "Boleta");
        String rutaData = Rutas.getRutaSunat() + "\\sunat_archivos\\sfs\\DATA\\" + Datos.getRUC() + "-" + tipoComprobante[0] + "-" + id;
        
        File apCab = new File(rutaData + ".CAB");
        File apDet = new File(rutaData + ".DET");
        File apTri = new File(rutaData + ".TRI");
        File apLey = new File(rutaData + ".LEY");

        //File archivo = null;
        if (apCab.exists() || apDet.exists() ||
                apTri.exists() || apLey.exists()) {
            existe = true;
        } else {
            existe = false;
        }
        return existe;
    }
    
    public static boolean validarExisteAPNotaCredito(String id){
        boolean existe;
        
        String tipoComprobante[] = Catalogos.tipoDocumento("", "Nota de crédito");
        String rutaData = Rutas.getRutaSunat() + "\\sunat_archivos\\sfs\\DATA\\" + Datos.getRUC() + "-" + tipoComprobante[0] + "-" + id;
        
        File apCab = new File(rutaData + ".CAB");
        File apDet = new File(rutaData + ".DET");
        File apTri = new File(rutaData + ".TRI");
        File apLey = new File(rutaData + ".LEY");

        //File archivo = null;
        if (apCab.exists() || apDet.exists() ||
                apTri.exists() || apLey.exists()) {
            existe = true;
        } else {
            existe = false;
        }
        return existe;
    }
    
    public static boolean validarExisteAPNotaDebito(String id){
        boolean existe;
        
        String tipoComprobante[] = Catalogos.tipoDocumento("", "Nota de débito");
        String rutaData = Rutas.getRutaSunat() + "\\sunat_archivos\\sfs\\DATA\\" + Datos.getRUC() + "-" + tipoComprobante[0] + "-" + id;
        
        File apCab = new File(rutaData + ".CAB");
        File apDet = new File(rutaData + ".DET");
        File apTri = new File(rutaData + ".TRI");
        File apLey = new File(rutaData + ".LEY");

        //File archivo = null;
        if (apCab.exists() || apDet.exists() ||
                apTri.exists() || apLey.exists()) {
            existe = true;
        } else {
            existe = false;
        }
        return existe;
    }
    
    // YYYY-MM-DD según SUNAT
    public static String fechaFormatoSUNAT(String fecha) {
        String fechaSunat;
        if(fecha.equals("-")){
            fechaSunat = fecha;
        } else {
            try {
                Date d = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fechaSunat = sdf.format(d);
            } catch (Exception e) {
                System.out.println("Error: " + e);
                fechaSunat = "";
            }
        }
        return fechaSunat;
    }
    
    // máximo 10 decimales SUNAT
    public static double formatoDecimalOperar(String numero){
        numero = numero.replace(",", "");
        double num = Double.parseDouble(numero);
        //DecimalFormat df = new DecimalFormat("#0.00########");
        //numero = df.format(num);
        return num;
    }
    
    // 2 decimales
    public static String formatoDecimalMostrar(double numero){
        DecimalFormat df = new DecimalFormat("#0.00");
        String num = df.format(numero);
        return num;
    }
    
    // max 10 decimales
    public static String formatoDecimalMostrar10Dec(double numero){
        DecimalFormat df = new DecimalFormat("#0.00########");
        String num = df.format(numero);
        return num;
    }
    
    public static String convertirNumTexto(String numero, String moneda) {
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
    
    // YYYYMMDD
    public static String FechaActualFormatoSUNATSinGuiones(String f){
        String fecha = fechaFormatoSUNAT(f);
        return fecha.replace("-", "");
    }
    
    public static void abrirLink(String link) {
        Desktop enlace = Desktop.getDesktop();
        try {
            enlace.browse(new URI(link));
        } catch (Exception e) {}
    }
    
    public static void ejecutarSfsSunat(){
        try {
            String unidad = Rutas.getRutaSunat().substring(0, 2);
            String ruta = Rutas.getRutaSunat();
            Runtime.getRuntime().exec("cmd.exe /K start EjecutarSFS.bat " + unidad + " " + ruta);
        } catch (Exception ex) {
            Metodos.mensajeError(ex.toString());
        }
    }
    
    public static void abrirBandeja(){
        try {
            Runtime.getRuntime().exec("cmd.exe /K AbrirBandeja.bat");
        } catch (Exception ex) {
            Metodos.mensajeError(ex.toString());
        }
    }
    
    public static void actualizaTamañoJPanel(JPanel jPanel){
        Dimension d = jpnlPrincipal.getSize();//capturamos la dimensión del panel actual
        jPanel.setSize(d);//le enviamos el mismo tamaño del panel principal
        jPanel.setLocation(0,0);//que se inice desde el punto 0,0
        jpnlPrincipal.removeAll();//quitamos los demás complementos
        jpnlPrincipal.add(jPanel, BorderLayout.CENTER);
        jpnlPrincipal.revalidate();
        jpnlPrincipal.repaint();
    }
    

}

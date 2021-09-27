package vista.factura;

import controlador.ArchivosPlanos;
import controlador.Catalogos;
import controlador.Metodos;
import controlador.Rutas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Config;
import modelo.Factura;

public class JPanelFacturaNueva extends javax.swing.JPanel {
    
    static ResultSet rs;
    String id;
    String idCliente;
    DefaultTableModel dtmDetalle;

    public JPanelFacturaNueva() {
        initComponents();
        cargarIdFactura();
        jtxtFechaEmision.setText(Metodos.CargarFechaActual());
    }
    
    private void cargarIdFactura(){
        String nombreFactura = "";
        //cargando la serie
        try {
            rs = Config.Consulta("select serieFactura \n"
                    + "from config;");
            if(rs.last() == true){
                if(rs.getString("serieFactura") == null || rs.getString("serieFactura").equalsIgnoreCase("")){
                    System.out.println("Debe registrar una serie en configuraciones.");
                    jbtnBuscar.setEnabled(false);
                    Metodos.MensajeError("Debe registrar una serie en configuraciones.");
                } else {
                    nombreFactura = "FF" + rs.getString("serieFactura") + "-";    
                }
            }
            rs.close();
        } catch (Exception e) {}
        
        try {
            rs = Factura.consulta("select *\n"
                    + "from factura;");
            if(rs.last() == true){
                // obtengo el id
                String Id = rs.getString("id");
                // obtengo ultimos 8 caracteres
                String numero_factura = Metodos.ObtenerUltimosXCaracteres(Id, 8);
                // convierto a entero para sumar +1
                int numero = Integer.parseInt(numero_factura) + 1;
                String numeroFactura = String.format("%08d", numero);
                nombreFactura = nombreFactura + numeroFactura;
                id = nombreFactura;
                jtxtId.setText(id);
                
            }   else {
                nombreFactura = nombreFactura + "00000001";
                id = nombreFactura;
                jtxtId.setText(id);
            }         
            rs.close();
        } catch (Exception e) {
            System.out.println("Error generando id de factura: \n" + e);
            Metodos.MensajeError("Error generando id de factura: \n" + e);
        }
    }
    
    private void cargarCliente() {
        try {
            rs = Cliente.Consulta("select *\n"
                    + "from cliente \n"
                    + "where id = '"+idCliente+"';");
            rs.next();
            String tipoDocumento = rs.getString("tipoDocumento");
            String numeroDocumento = rs.getString("numeroDocumento");
            String nombreRazonSocial = rs.getString("nombreRazonSocial");
            String direccion = rs.getString("direccion");
            // mostrando datos
            jcbxTipoDocumento.setSelectedItem(tipoDocumento);
            jtxtNumeroDocumento.setText(numeroDocumento);
            jtxtNombreRazonSocial.setText(nombreRazonSocial);
            jtxtDireccion.setText(direccion);
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando cliente Seleccionado: \n"+e);
            //No se muestra el mensaje xq el primero siempre dará error por las ??? incógnitas
            //Metodos.MensajeError("Error cargando Cliente Seleccionado: \n" + e);
        }
    }
    
    private void cargarProducto(String idProducto) {
        try {
            rs = Cliente.Consulta("select *\n"
                    + "from producto \n"
                    + "where id = '"+idProducto+"';");
            rs.next();
            String codigo = rs.getString("codigo");
            String descripcion = rs.getString("descripcion");
            String precio1 = rs.getString("precio1");
            String precio2 = rs.getString("precio2");
            // mostrando datos
            jtxtCodigo.setText(codigo);
            jtxtDescripcion.setText(descripcion);
            jcbxPrecioUnitario.removeAllItems();
            jcbxPrecioUnitario.addItem(precio1);
            jcbxPrecioUnitario.addItem(precio2);
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando producto Seleccionado: \n" + e);
            //No se muestra el mensaje xq el primero siempre dará error por las ??? incógnitas
            //Metodos.MensajeError("Error cargando Cliente Seleccionado: \n" + e);
        }
    }
    
    private void cargarServicio(String idServicio) {
        try {
            rs = Cliente.Consulta("select *\n"
                    + "from servicio \n"
                    + "where id = '"+idServicio+"';");
            rs.next();
            String codigo = rs.getString("codigo");
            String descripcion = rs.getString("descripcion");
            String precio1 = rs.getString("precio1");
            String precio2 = rs.getString("precio2");
            // mostrando datos
            jtxtCodigo.setText(codigo);
            jtxtDescripcion.setText(descripcion);
            jcbxPrecioUnitario.removeAllItems();
            jcbxPrecioUnitario.addItem(precio1);
            jcbxPrecioUnitario.addItem(precio2);
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando producto Seleccionado: \n" + e);
            //No se muestra el mensaje xq el primero siempre dará error por las ??? incógnitas
            //Metodos.MensajeError("Error cargando Cliente Seleccionado: \n" + e);
        }
    }

    public void agregarDetalle() {
        // capturando
        double cantidad = Metodos.formatoDecimalOperar(jtxtCantidad.getText());
        String tipoUnidad = jcbxTipoUnidad.getSelectedItem().toString();
        double precioUnitario = Metodos.formatoDecimalOperar(jcbxPrecioUnitario.getSelectedItem().toString());
        String codigo = jtxtCodigo.getText();
        String descripcion = jtxtDescripcion.getText();
        String tributo = Catalogos.tipoTributo("", jcbxTributo.getSelectedItem().toString(), "", "")[3];
        // calculando
        //String precioTotal = Metodos.FormatoDecimalOperar(Double.parseDouble(cantidad) * Double.parseDouble(precioUnitario));
        double precioTotal = 0.00;
        double montoTributo = 0.00;
        
        switch (tributo) {
            case "IGV":
                precioTotal = cantidad * precioUnitario;
                montoTributo = precioTotal * 0.18;
                break;
            case "GRA":
                precioTotal = 0.00;
                montoTributo = 0.00;
                break;
            default: //INA
                
                break;
        }
        
        dtmDetalle = (DefaultTableModel) jtblDetalle.getModel();
        Object[] fila = new Object[10];
        fila[0] = "";
        fila[1] = Metodos.formatoDecimalMostrar(cantidad);
        fila[2] = tipoUnidad;
        fila[3] = codigo;
        fila[4] = descripcion;
        fila[5] = Metodos.formatoDecimalMostrar(precioUnitario);
        fila[6] = tributo;
        fila[7] = Metodos.formatoDecimalMostrar(montoTributo);
        fila[8] = Metodos.formatoDecimalMostrar(precioTotal);
        // precioTotal 10 decimales (no se muestra)
        fila[9] = precioTotal;
        dtmDetalle.addRow(fila);
        jtblDetalle.setModel(dtmDetalle);
        //se limpia los campos
        jtxtCantidad.setText("");
        jcbxTipoUnidad.setSelectedIndex(0);
        jtxtCodigo.setText("");
        jtxtDescripcion.setText("");
        jcbxPrecioUnitario.setSelectedItem("");
        jcbxTributo.setSelectedIndex(0);
        // activo botones
        jbtnQuitar.setEnabled(true);
        jbtnCrearArchivosPlanos.setEnabled(true);
        // metodos
        Metodos.ActulizarNumeroItem(jtblDetalle);
        ActualizarTotales();
    }

    public void ActualizarTotales() {
        double importe = 0.00;
        double importeTributo = 0.00;
        double importeTotal = 0.00;
        double gratuito = 0.00;

        int numero_filas = jtblDetalle.getRowCount();
        for (int i = 0; i < numero_filas; i++) {
            double cantidadFila = Double.parseDouble(jtblDetalle.getModel().getValueAt(i, 1).toString());
            double precioUnitarioFila = Double.parseDouble(jtblDetalle.getModel().getValueAt(i, 5).toString());
            String tributoFila = jtblDetalle.getModel().getValueAt(i, 6).toString();
            double importeTributoFila = Double.parseDouble(jtblDetalle.getModel().getValueAt(i, 7).toString());
            //capturo importeTotal con decimales
            double importeDecimal = Double.parseDouble(jtblDetalle.getModel().getValueAt(i, 9).toString());
            // importes depende del tributo
            switch (tributoFila) {
                case "IGV":
                    importe = importe + importeDecimal;
                    importeTributo = importeTributo + importeTributoFila;
                    importeTotal = importe + importeTributo;
                    break;
                case "GRA":
                    gratuito = gratuito + (cantidadFila * precioUnitarioFila);
                    break;
                // inafecto
                default:
                    break;
            }
        }

        //actualizando totales
        jtxtImporte.setText(Metodos.formatoDecimalMostrar(importe));
        jtxtIgv.setText(Metodos.formatoDecimalMostrar(importeTributo));
        jtxtImporteTotal.setText(Metodos.formatoDecimalMostrar(importeTotal));
        jtxtTotalGratuito.setText(Metodos.formatoDecimalMostrar(gratuito));
        // monto en texto
        String moneda = jcbxMoneda.getSelectedItem().toString();
        String montoEnTexto = Metodos.convertirNumTexto(Metodos.formatoDecimalMostrar(importeTotal), moneda);
        jlblMontoEnTexto.setText(montoEnTexto);
    }
    
    private void registrarCliente() {
        try {
            Cliente.Registrar(idCliente,
                    jcbxTipoDocumento.getSelectedItem().toString(),
                    jtxtNumeroDocumento.getText(), jtxtNombreRazonSocial.getText(),
                    jtxtDireccion.getText());
        } catch (Exception e) {
        }
    }
    
    private void registrarFactura() {
        //capturamos los datos a enviar desde el frame
        String fecha = jtxtFechaEmision.getText();
        String horaEmision = Metodos.ObtenerHora();
        String fechaVencimiento = Metodos.getFechaJDC(jdcFechaVencimiento);
        String moneda = jcbxMoneda.getSelectedItem().toString();
        String medioPago = jcbxMedioPago.getSelectedItem().toString();
        String totalVentasGravadas = jtxtImporte.getText();
        String totalGratuito = jtxtTotalGratuito.getText();
        String igv = jtxtIgv.getText();
        String totalImporteVenta = jtxtImporteTotal.getText();
        try {
            Factura.registrarFactura(id, idCliente, fecha, horaEmision, fechaVencimiento,
                    moneda, medioPago, totalVentasGravadas, totalGratuito, igv, totalImporteVenta);
        } catch (Exception e) {
            System.out.println("Error registrando factura a la base de datos: \n" + e);
            Metodos.MensajeError("Error registrando factura a la base de datos: \n" + e);
        }
    }
    
    private void registrarFacturaDet() {
        try {
            int numero = 1;//contador para IdFacturaDet
            for (int i = 0; i < jtblDetalle.getRowCount(); i++) {
                String item = jtblDetalle.getValueAt(i, 0).toString();
                String cantidad = jtblDetalle.getValueAt(i, 1).toString();
                String tipoUnidad = jtblDetalle.getValueAt(i, 2).toString();
                String codigo = jtblDetalle.getValueAt(i, 3).toString();
                String descripcion = jtblDetalle.getValueAt(i, 4).toString();
                String precioUnitario = jtblDetalle.getValueAt(i, 5).toString();
                String tributo = jtblDetalle.getValueAt(i, 6).toString();
                String montoTributo = jtblDetalle.getValueAt(i, 7).toString();
                
                String tipoAfectacionTributo;
                String valorUnitarioGratuito = "0.00";
                switch (tributo) {
                    case "IGV":
                        tipoAfectacionTributo = "10";
                        break;
                    case "GRA":
                        tipoAfectacionTributo = "21";
                        valorUnitarioGratuito = Metodos.formatoDecimalMostrar(
                                Metodos.formatoDecimalOperar(cantidad) * Metodos.formatoDecimalOperar(precioUnitario)
                        );
                        precioUnitario = "0.00";
                        break;
                    default:
                        //INA
                        tipoAfectacionTributo = "30";
                        break;
                }
                
                String precioTotal = jtblDetalle.getValueAt(i, 8).toString();
                String Id = id + "-" + String.format("%03d", (numero++));
                String IdFactura = id;
                Factura.registrarFacturaDet(Id, IdFactura, item, cantidad, tipoUnidad,
                        codigo, descripcion, tributo, montoTributo, tipoAfectacionTributo,
                        precioUnitario, valorUnitarioGratuito, precioTotal);
            }
        } catch (Exception e) {
            System.out.println("Error registrando detalle de factura en base de datos: \n" + e);
            Metodos.MensajeError("Error registrando detalle de factura en base de datos: \n" + e);            
        }
    }
    
    private void crearArchivosPlanos(){
        boolean validar = Metodos.validarExisteAPFactura(id);
        if (validar == true) {
            Metodos.MensajeError("Uno o varios AP del comprobante " + id + " ya existe.\n"
                    + "Elimínelos manualmente y vuelva a crear los AP.");
            jbtnCrearArchivosPlanos.setEnabled(true);
        } else {
            ArchivosPlanos.apFactura(id);
            Metodos.MensajeInformacion("Archivos planos generados.");
            jbtnImprimir.setEnabled(true);
            jbtnNuevoComprobante.setEnabled(true);
        }
    }
    
    private String validar() {
        String mensaje;
        String validarFechaVencimiento = Metodos.validarFechaVencimiento(jdcFechaVencimiento);
        String validarCliente = Metodos.validarCliente(jcbxTipoDocumento.getSelectedItem().toString(),
                jtxtNumeroDocumento.getText(), jtxtNombreRazonSocial.getText(),
                jtxtDireccion.getText());
        if (validarFechaVencimiento != "") {
            mensaje = validarFechaVencimiento;
        } else if (validarCliente != ("")) {
            mensaje = validarCliente;
        } else if (jcbxMedioPago.getSelectedIndex() == 0) {
            mensaje = "Seleccione el medio de pago.";
        } else {

            mensaje = "";
        }
        return mensaje;
    }
    
    private void bloquearCampos(){
        jdcFechaVencimiento.setEnabled(false);
        jcbxMedioPago.setEnabled(false);
        jcbxMoneda.setEnabled(false);
        jcbxTipoDocumento.setEnabled(false);
        jtxtNumeroDocumento.setEnabled(false);
        jtxtNombreRazonSocial.setEnabled(false);
        jtxtDireccion.setEnabled(false);
        jbtnBuscar.setEnabled(false);
        jtxtCantidad.setEnabled(false);
        jcbxTipoUnidad.setEnabled(false);
        jcbxPrecioUnitario.setEnabled(false);
        jtxtCodigo.setEnabled(false);
        jtxtDescripcion.setEnabled(false);
        jbtnBuscarProductoServicio.setEnabled(false);
        jbtnAgregar.setEnabled(false);
        jbtnQuitar.setEnabled(false);
        jbtnCrearArchivosPlanos.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtxtNombreRazonSocial = new javax.swing.JTextField();
        jtxtDireccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jcbxMoneda = new javax.swing.JComboBox<>();
        jbtnBuscar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jtxtNumeroDocumento = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jcbxMedioPago = new javax.swing.JComboBox<>();
        jcbxTipoDocumento = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblDetalle = new javax.swing.JTable();
        jbtnAgregar = new javax.swing.JButton();
        jbtnQuitar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtxtCantidad = new javax.swing.JTextField();
        jtxtDescripcion = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jcbxTipoUnidad = new javax.swing.JComboBox<>();
        jbtnBuscarProductoServicio = new javax.swing.JButton();
        jcbxPrecioUnitario = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jtxtCodigo = new javax.swing.JTextField();
        jcbxTributo = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtxtFechaEmision = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtxtImporte = new javax.swing.JTextField();
        jtxtIgv = new javax.swing.JTextField();
        jtxtImporteTotal = new javax.swing.JTextField();
        jbtnCrearArchivosPlanos = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jlblMontoEnTexto = new javax.swing.JLabel();
        jtxtId = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jdcFechaVencimiento = new com.toedter.calendar.JDateChooser();
        jbtnImprimir = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jtxtTotalGratuito = new javax.swing.JTextField();
        jbtnNuevoComprobante = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 192, 192));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Factura N°");

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cabecera:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Tipo Doc.:");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Razón Social:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Dirección:");

        jtxtNombreRazonSocial.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtNombreRazonSocial.setNextFocusableComponent(jtxtDireccion);

        jtxtDireccion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Moneda:");

        jcbxMoneda.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxMoneda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PEN", "USD" }));
        jcbxMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxMonedaActionPerformed(evt);
            }
        });

        jbtnBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnBuscar.setText("Buscar");
        jbtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBuscarActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel14.setText("N° Doc.:");

        jtxtNumeroDocumento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtNumeroDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtNumeroDocumentoKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel16.setText("Medio de pago:");

        jcbxMedioPago.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxMedioPago.setMaximumRowCount(9);
        jcbxMedioPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione-", "Depósito en cuenta", "Giro", "Transferencia de fondos", "Orden de pago", "Tarjeta de débito", "Efectivo", "Tarjeta de crédito", "Otros" }));

        jcbxTipoDocumento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RUC" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtxtDireccion, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jcbxTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbxMedioPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jtxtNombreRazonSocial, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbxMoneda, 0, 100, Short.MAX_VALUE))
                    .addComponent(jbtnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jcbxMedioPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jtxtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jcbxMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jcbxTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jtxtNombreRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jtxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jbtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setBackground(new java.awt.Color(141, 170, 235));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12))); // NOI18N

        jtblDetalle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Cantidad", "U.M.", "Código", "Descripción", "P.U.", "Tributo", "Monto tributo", "Importe", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblDetalle.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtblDetalle);
        if (jtblDetalle.getColumnModel().getColumnCount() > 0) {
            jtblDetalle.getColumnModel().getColumn(0).setPreferredWidth(50);
            jtblDetalle.getColumnModel().getColumn(1).setPreferredWidth(150);
            jtblDetalle.getColumnModel().getColumn(2).setPreferredWidth(100);
            jtblDetalle.getColumnModel().getColumn(3).setMinWidth(80);
            jtblDetalle.getColumnModel().getColumn(3).setPreferredWidth(80);
            jtblDetalle.getColumnModel().getColumn(3).setMaxWidth(80);
            jtblDetalle.getColumnModel().getColumn(4).setPreferredWidth(600);
            jtblDetalle.getColumnModel().getColumn(5).setPreferredWidth(150);
            jtblDetalle.getColumnModel().getColumn(6).setPreferredWidth(100);
            jtblDetalle.getColumnModel().getColumn(7).setPreferredWidth(150);
            jtblDetalle.getColumnModel().getColumn(8).setPreferredWidth(150);
            jtblDetalle.getColumnModel().getColumn(9).setMinWidth(0);
            jtblDetalle.getColumnModel().getColumn(9).setPreferredWidth(0);
            jtblDetalle.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        jbtnAgregar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnAgregar.setText("Agregar");
        jbtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAgregarActionPerformed(evt);
            }
        });

        jbtnQuitar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnQuitar.setText("Quitar");
        jbtnQuitar.setEnabled(false);
        jbtnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnQuitarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Descripción:");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Cantidad:");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("P. unitario:");

        jtxtCantidad.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtCantidadKeyTyped(evt);
            }
        });

        jtxtDescripcion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel15.setText("Tipo unidad:");

        jcbxTipoUnidad.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxTipoUnidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UN", "MT", "KG", "CJA", "LT" }));

        jbtnBuscarProductoServicio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnBuscarProductoServicio.setText("+");
        jbtnBuscarProductoServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBuscarProductoServicioActionPerformed(evt);
            }
        });

        jcbxPrecioUnitario.setEditable(true);
        jcbxPrecioUnitario.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxPrecioUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jcbxPrecioUnitarioKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel17.setText("Código:");

        jtxtCodigo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtCodigoKeyTyped(evt);
            }
        });

        jcbxTributo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxTributo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IGV Impuesto General a las Ventas", "Gratuito" }));

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setText("Tributo:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtxtCantidad)
                                    .addComponent(jcbxTipoUnidad, 0, 126, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcbxPrecioUnitario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtxtCodigo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbxTributo, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jtxtDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtnBuscarProductoServicio)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jbtnBuscarProductoServicio)
                    .addComponent(jLabel17)
                    .addComponent(jtxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnQuitar)
                    .addComponent(jbtnAgregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jcbxTipoUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jcbxTributo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jcbxPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Fecha emisión:");

        jtxtFechaEmision.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtFechaEmision.setEnabled(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Importe:");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("IGV:");

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText("Importe Total:");

        jtxtImporte.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtImporte.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtImporte.setText("0.00");
        jtxtImporte.setEnabled(false);

        jtxtIgv.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtIgv.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtIgv.setText("0.00");
        jtxtIgv.setEnabled(false);

        jtxtImporteTotal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jtxtImporteTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtImporteTotal.setText("0.00");
        jtxtImporteTotal.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtImporte, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jtxtImporteTotal)
                    .addComponent(jtxtIgv))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jtxtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtxtIgv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jtxtImporteTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jbtnCrearArchivosPlanos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnCrearArchivosPlanos.setText("Crear Archivos Planos");
        jbtnCrearArchivosPlanos.setEnabled(false);
        jbtnCrearArchivosPlanos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCrearArchivosPlanosActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("SON:");

        jlblMontoEnTexto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblMontoEnTexto.setText("?");

        jtxtId.setEditable(false);
        jtxtId.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel18.setText("Fecha vencimiento:");

        jdcFechaVencimiento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jbtnImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnImprimir.setText("Imprimir");
        jbtnImprimir.setEnabled(false);
        jbtnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnImprimirActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel20.setText("Operaciones gratuitas:");

        jtxtTotalGratuito.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtTotalGratuito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtTotalGratuito.setText("0.00");
        jtxtTotalGratuito.setEnabled(false);

        jbtnNuevoComprobante.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNuevoComprobante.setText("Nueva Factura");
        jbtnNuevoComprobante.setEnabled(false);
        jbtnNuevoComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoComprobanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtFechaEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbtnCrearArchivosPlanos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnNuevoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxtTotalGratuito, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlblMontoEnTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(15, 15, 15)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxtFechaEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel18))
                    .addComponent(jdcFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jtxtTotalGratuito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlblMontoEnTexto)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnCrearArchivosPlanos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnNuevoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAgregarActionPerformed
        if (jtxtCantidad.getText().equalsIgnoreCase("")) {
            Metodos.MensajeAlerta("Escriba la cantidad.");
        } else if (jtxtDescripcion.getText().equalsIgnoreCase("")) {
            Metodos.MensajeAlerta("Escriba una descripción.");
        } else if (jcbxPrecioUnitario.getSelectedItem().toString().equalsIgnoreCase("")) {
            Metodos.MensajeAlerta("Escriba el precio unitario.");
        } else {
            agregarDetalle();
        }
    }//GEN-LAST:event_jbtnAgregarActionPerformed

    private void jbtnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnQuitarActionPerformed
        int fila = jtblDetalle.getSelectedRow();
        if (fila >= 0) {//si hay fila seleccionada
            dtmDetalle.removeRow(jtblDetalle.getSelectedRow());
            Metodos.ActulizarNumeroItem(jtblDetalle);
            ActualizarTotales();
            //verifica que haya datos en el jtable
            if (jtblDetalle.getRowCount() != 0) {
                //si hay datos
            } else {//si no
                //desactiva botones
                jbtnCrearArchivosPlanos.setEnabled(false);
                jbtnQuitar.setEnabled(false);
                //limpia los campos totales
                jtxtImporte.setText("0.00");
                jtxtIgv.setText("0.00");
                jtxtImporteTotal.setText("0.00");
                jlblMontoEnTexto.setText("?");
            }
        } else {//no hay fila seleccionada
            Metodos.MensajeAlerta("Seleccione un detalle");
        }
    }//GEN-LAST:event_jbtnQuitarActionPerformed

    private void jbtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscarActionPerformed
        JFrame FormularioPrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialogClienteBuscar jdcb = new JDialogClienteBuscar(FormularioPrincipal, true);
        jdcb.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                idCliente = jdcb.id;
                if (idCliente != null) {
                    jcbxTipoDocumento.setEnabled(false);
                    jtxtNombreRazonSocial.setEnabled(false);
                    jtxtDireccion.setEnabled(false);
                }
                cargarCliente();
            }
        });
        jdcb.setVisible(true);
    }//GEN-LAST:event_jbtnBuscarActionPerformed

    private void jbtnCrearArchivosPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCrearArchivosPlanosActionPerformed
        if (validar().equals("")) {
            if (idCliente == null) {
                idCliente = Cliente.generarIdCliente();
                registrarCliente();
            }

            if (Factura.existeFactura(id) == false) {
                //crea comprobante y ap
                registrarFactura();
                registrarFacturaDet();
                bloquearCampos();
                crearArchivosPlanos();
            } else {
                //si existe solo crear AP
                bloquearCampos();
                crearArchivosPlanos();
            }
        } else {
            Metodos.MensajeAlerta(validar());
        }
    }//GEN-LAST:event_jbtnCrearArchivosPlanosActionPerformed

    private void jcbxMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxMonedaActionPerformed
        ActualizarTotales();
    }//GEN-LAST:event_jcbxMonedaActionPerformed

    private void jtxtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCantidadKeyTyped
        Metodos.ValidarDecimalTXT(evt, jtxtCantidad);
    }//GEN-LAST:event_jtxtCantidadKeyTyped

    private void jcbxPrecioUnitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcbxPrecioUnitarioKeyTyped
        Metodos.ValidarDecimalCBX(evt, jcbxPrecioUnitario);
        System.out.println("Key Typed " + evt.getKeyCode());
    }//GEN-LAST:event_jcbxPrecioUnitarioKeyTyped

    private void jbtnBuscarProductoServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscarProductoServicioActionPerformed
        JFrame FormularioPrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialogProductoServicioBuscar jdpsb = new JDialogProductoServicioBuscar(FormularioPrincipal, true);
        jdpsb.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    String idProductoServicio = jdpsb.id;
                    String tipo = jdpsb.tipo;
                    if (tipo.equals("producto")) {
                        cargarProducto(idProductoServicio);
                    } else { //servicio
                        cargarServicio(idProductoServicio);
                    }
                } catch (Exception ex) {
                }
            }
        });
        jdpsb.setVisible(true);
    }//GEN-LAST:event_jbtnBuscarProductoServicioActionPerformed

    private void jtxtNumeroDocumentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtNumeroDocumentoKeyReleased
        String numeroDocumento = jtxtNumeroDocumento.getText();
        try {
            rs = Cliente.Consulta("select * \n"
                    + "from cliente \n"
                    + "where numeroDocumento = '"+numeroDocumento+"'"
                    + "and tipoDocumento = 'RUC';");
            rs.next();
            idCliente = rs.getString("id");
            String tipoDocumento = rs.getString("tipoDocumento");
            //String numeroDocumento = rs.getString("numeroDocumento");
            String nombreRazonSocial = rs.getString("nombreRazonSocial");
            String direccion = rs.getString("direccion");
            // mostrando datos
            jcbxTipoDocumento.setSelectedItem(tipoDocumento);
            jtxtNumeroDocumento.setText(numeroDocumento);
            jtxtNombreRazonSocial.setText(nombreRazonSocial);
            jtxtDireccion.setText(direccion);
            // desactivanto campos
            jcbxTipoDocumento.setEnabled(false);
            jtxtNombreRazonSocial.setEnabled(false);
            jtxtDireccion.setEnabled(false);
            rs.close();
        } catch (Exception e) {
            idCliente = null;
            // activando campos
            jcbxTipoDocumento.setEnabled(true);
            jtxtNombreRazonSocial.setEnabled(true);
            jtxtDireccion.setEnabled(true);
        }
    }//GEN-LAST:event_jtxtNumeroDocumentoKeyReleased

    private void jtxtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCodigoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCodigoKeyTyped

    private void jbtnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnImprimirActionPerformed
        try {
            String hash = Metodos.getHash(Rutas.getRutaHash("01", id));
            Factura.registrarHash(id, hash);
            Factura.crearQR(id, hash);
            Factura.crearPDF(id);
        } catch (Exception e) {
            System.out.println("Genere el XML primero.\n" + e);
            Metodos.MensajeAlerta("Genere el XML primero.");
        }
    }//GEN-LAST:event_jbtnImprimirActionPerformed

    private void jbtnNuevoComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoComprobanteActionPerformed
        JPanelFacturaNueva jpfn = new JPanelFacturaNueva();
        Metodos.CambiarPanel(jpfn);
    }//GEN-LAST:event_jbtnNuevoComprobanteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JButton jbtnAgregar;
    public static javax.swing.JButton jbtnBuscar;
    private javax.swing.JButton jbtnBuscarProductoServicio;
    private javax.swing.JButton jbtnCrearArchivosPlanos;
    private javax.swing.JButton jbtnImprimir;
    private javax.swing.JButton jbtnNuevoComprobante;
    private javax.swing.JButton jbtnQuitar;
    public static javax.swing.JComboBox<String> jcbxMedioPago;
    public static javax.swing.JComboBox<String> jcbxMoneda;
    public static javax.swing.JComboBox<String> jcbxPrecioUnitario;
    private javax.swing.JComboBox<String> jcbxTipoDocumento;
    public static javax.swing.JComboBox<String> jcbxTipoUnidad;
    public static javax.swing.JComboBox<String> jcbxTributo;
    private com.toedter.calendar.JDateChooser jdcFechaVencimiento;
    private javax.swing.JLabel jlblMontoEnTexto;
    private javax.swing.JTable jtblDetalle;
    public static javax.swing.JTextField jtxtCantidad;
    public static javax.swing.JTextField jtxtCodigo;
    public static javax.swing.JTextField jtxtDescripcion;
    private javax.swing.JTextField jtxtDireccion;
    private javax.swing.JTextField jtxtFechaEmision;
    private javax.swing.JTextField jtxtId;
    private javax.swing.JTextField jtxtIgv;
    private javax.swing.JTextField jtxtImporte;
    private javax.swing.JTextField jtxtImporteTotal;
    private javax.swing.JTextField jtxtNombreRazonSocial;
    private javax.swing.JTextField jtxtNumeroDocumento;
    private javax.swing.JTextField jtxtTotalGratuito;
    // End of variables declaration//GEN-END:variables
}

package vista.factura;

import controlador.Metodos;
import controlador.Rutas;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Factura;
import vista.JPanelComprobantes;

public class JPanelFacturaNueva extends javax.swing.JPanel {
    
    static ResultSet rs;
    String id;
    String idCliente;
    DefaultTableModel dtmDetalle;

    public JPanelFacturaNueva() {
        initComponents();
        CargarIdFactura();
        jtxtFecha.setText(Metodos.CargarFechaActual());
        CargarCliente();
    }
    
    void CargarIdFactura(){
        String nombreFactura = "FF01-";//valor que siempre tendrá
        try {
            rs = Factura.Consulta("select *\n"
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
    
    void CargarCliente() {
        JDialogClienteBuscar jdcb = new JDialogClienteBuscar(Metodos.FormatoJDialog(), true);
        //capturo los datos en variables
        idCliente = jdcb.id;
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
            jtxtTipoDocumento.setText(tipoDocumento);
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

    public void AgregarDetalle() {
        //capturo los datos del detalle
        String cantidad = Metodos.FormatoDecimalOperar(Double.parseDouble(jtxtCantidad.getText()));
        String tipoUnidad = jcbxTipoUnidad.getSelectedItem().toString();
        String precioUnitario = Metodos.FormatoDecimalOperar(Double.parseDouble(jtxtPrecioUnitario.getText()));
        String descripcion = jtxtDescripcion.getText();
        String precioTotal = Metodos.FormatoDecimalOperar(Double.parseDouble(cantidad) * Double.parseDouble(precioUnitario));
        dtmDetalle = (DefaultTableModel) jtblDetalle.getModel();
        Object[] fila = new Object[7];
        fila[0] = "";
        fila[1] = Metodos.FormatoDecimalMostrar(cantidad);
        fila[2] = tipoUnidad;
        fila[3] = descripcion;
        fila[4] = Metodos.FormatoDecimalMostrar(precioUnitario);
        fila[5] = Metodos.FormatoDecimalMostrar(precioTotal);
        // precioTotal 4 decimales (no se muestra)
        fila[6] = precioTotal;
        dtmDetalle.addRow(fila);
        jtblDetalle.setModel(dtmDetalle);
        //se limpia los campos
        jtxtCantidad.setText("");
        jcbxTipoUnidad.setSelectedIndex(0);
        jtxtDescripcion.setText("");
        jtxtPrecioUnitario.setText("");
        // activo botones
        jbtnQuitar.setEnabled(true);
        jbtnCrearArchivosPlanos.setEnabled(true);
        // metodos
        Metodos.ActulizarNumeroItem(jtblDetalle);
        ActualizarTotales();
    }

    public void ActualizarTotales() {
        String importe = "0.0000";
        String igv = "0.00";
        String importeTotal = "0.00";
        int numero_filas = jtblDetalle.getRowCount();
        for (int i = 0; i < numero_filas; i++) {
            //capturo importeTotal con decimales
            double importe_decimal = Double.parseDouble(jtblDetalle.getModel().getValueAt(i, 6).toString());
            //acumulamos todos los importes
            importe =  String.valueOf(Double.parseDouble(importe) + importe_decimal);
        }
        igv = String.valueOf(Double.parseDouble(importe)*0.18);
        importeTotal = String.valueOf(Double.parseDouble(importe) + Double.parseDouble(igv));
        //mostrando totales actualizados
        jtxtImporte.setText(Metodos.FormatoDecimalMostrar(importe));
        jtxtIgv.setText(Metodos.FormatoDecimalMostrar(igv));
        jtxtImporteTotal.setText(Metodos.FormatoDecimalMostrar(importeTotal));
        // enviamos el monto en texto
        String moneda = jcbxMoneda.getSelectedItem().toString();
        String montoEnTexto = Metodos.ConvertirNumTexto(Metodos.FormatoDecimalMostrar(importeTotal), moneda);
        jlblMontoEnTexto.setText(montoEnTexto);
    }
    
    public void CrearArchivosPlanos(){
        ArchivoPlanoCAB();
        ArchivoPlanoDET();
        ArchivoPlanoTRI();
        ArchivoPlanoLEY();
        ArchivoPlanoACA();
        Metodos.MensajeInformacion("Archivos planos generados.");
    }
    
    public void ArchivoPlanoCAB(){
        //se crea archivo con el nombre establecido
        File ap = new File(Rutas.getRutaAP("01", id, "CAB"));
        //para almacenar datos
        BufferedWriter bufferedWriter;
        if (ap.exists()) {
            Metodos.MensajeError("Error generando archivo\n"
                    + "plano '" + id + ".CAB':\n"
                    + "El archivo ya existe");
        } else {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(ap));
                //18 valores SUNAT CAB SFS 1.3.4.2
                String tipo_operacion = "0101"; //venta interna
                String fecha_emision = Metodos.FechaActualFormatoSUNAT();//fecha en formato YYYY-MM-DD
                String hora_emision = Metodos.ObtenerHora();
                String fecha_vencimiento = "-";
                String codigo_domicilio_fiscal = "0000";//domicilio fiscal
                String tipo_documento_receptor = "6";//registro unico de contribyentes
                String numero_documento_cliente = jtxtNumeroDocumento.getText();//ruc
                String nombre_o_razon_social = jtxtNombreRazonSocial.getText();//razon social dinamico
                String moneda = jcbxMoneda.getSelectedItem().toString();//PEN,USD
                String sumatoria_tributos = jtxtIgv.getText();
                String total_valor_venta = jtxtImporte.getText();
                String total_precio_venta = jtxtImporteTotal.getText();
                String total_descuentos = "0.00";
                String sumatoria_otros_cargos = "0.00";
                String total_anticipos = "0.00";
                String importe_total_venta = total_precio_venta;
                String version_UBL = "2.1";
                String custo_documento = "2.0";
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                    tipo_operacion + "|"
                    + fecha_emision + "|"
                    + hora_emision + "|"
                    + fecha_vencimiento + "|"
                    + codigo_domicilio_fiscal + "|"
                    + tipo_documento_receptor + "|"
                    + numero_documento_cliente + "|"
                    + nombre_o_razon_social + "|"
                    + moneda + "|"
                    + sumatoria_tributos + "|"
                    + total_valor_venta + "|"
                    + total_precio_venta + "|"
                    + total_descuentos + "|"
                    + sumatoria_otros_cargos + "|"
                    + total_anticipos + "|"
                    + importe_total_venta + "|"
                    + version_UBL + "|"
                    + custo_documento
                );
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println("Error creando archivo plano " + id + ".CAB:\n" + e);
                Metodos.MensajeError("Error creando el archivo plano " + id + ".CAB:\n" + e);
            }
        }
    }
    
    public void ArchivoPlanoDET(){
        //se crea archivo con el nombre establecido
        File ap = new File(Rutas.getRutaAP("01", id, "DET"));
        BufferedWriter bufferedWriter;
        if (ap.exists()) {
            Metodos.MensajeError("Error generando archivo\n"
                    + "plano " + id + ".DET:\n"
                    + "El archivo ya existe");
        } else {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(ap));
                //recorremos la tabla detealle para capturar los datos
                for (int i = 0; i < jtblDetalle.getRowCount(); i++) {
                    //guardamos los campos en variables
                    String Cantidad = (jtblDetalle.getValueAt(i, 1).toString()).replace(",", "");
                    String PrecioUnitario = (jtblDetalle.getValueAt(i, 4).toString()).replace(",", "");
                    String PrecioTotal = (jtblDetalle.getValueAt(i, 5).toString()).replace(",", "");
                    String Descripcion = jtblDetalle.getValueAt(i, 3).toString().replaceAll("\n", "");// sin salto de linea
                    //36 valores SUNAT DET SFS 1.3.4.2
                    String codigo_unidad_medida_item = "EA";//???antes EA
                    String cantidad_unidades_item = Cantidad;
                    String codigo_producto = "-";
                    String codigo_producto_sunat = "-";
                    String descripcion_detallada = Descripcion;
                    String valor_unitario_item = PrecioUnitario;
                    String sumatoria_tributos_item = Metodos.FormatoDecimalMostrar(String.valueOf(Double.parseDouble(PrecioTotal)*0.18));
                    String codigo_tipo_tributo_igv = "1000";//hace referencia al IGV
                    String monto_igv_item = sumatoria_tributos_item;
                    String base_imponible_igv_item = PrecioTotal;
                    String nombre_tributo_item = "IGV";
                    String codigo_tributo_item = "VAT";
                    String afectacion_igv_item = "10";
                    String porcentaje_igv = "18.00";
                    String codigo_tipo_tributo_isc = "-";
                    String monto_isc_item = "0.00";
                    String base_imponible_isc_item = "";
                    String nombre_tributo_item_isc = "ISC";
                    String codigo_tipo_tributo_item = "EXC";
                    String tipo_sistema_isc = "01";
                    String porcentaje_isc = "2.00";
                    String codigo_tipo_tributo_otro = "-";
                    String monto_tributo_otro_item = "";
                    String base_imponible_tributo_otro_item = "";
                    String nombre_tributo_otro_item = "";
                    String codigo_tipo_tributo_otro_item = "";
                    String porcentaje_tributo_otro_item = "";
                    String codigo_icbper = "-";
                    String monto_icbper_item = "";
                    String cantidad_icbper_item = "";
                    String nombre_icbper_item = "";
                    String codigo_icbper_item = "";
                    String monto_icbper_unidad = "";
                    String precio_venta_unitario = Metodos.FormatoDecimalMostrar(String.valueOf(Double.parseDouble(PrecioUnitario) + (Double.parseDouble(PrecioUnitario) * 0.18)));
                    String valor_venta_item = base_imponible_igv_item;
                    String valor_referencial_gratuito = "0.00";
                    //se esccribe la linea en el archivo
                    bufferedWriter.write(
                            codigo_unidad_medida_item + "|"
                            + cantidad_unidades_item + "|"
                            + codigo_producto + "|"
                            + codigo_producto_sunat + "|"
                            + descripcion_detallada + "|"
                            + valor_unitario_item + "|"
                            + sumatoria_tributos_item + "|"
                            + codigo_tipo_tributo_igv +"|"
                            + monto_igv_item + "|"
                            + base_imponible_igv_item + "|"
                            + nombre_tributo_item + "|"
                            + codigo_tributo_item + "|"
                            + afectacion_igv_item + "|"
                            + porcentaje_igv + "|"
                            + codigo_tipo_tributo_isc +"|"
                            + monto_isc_item + "|"
                            + base_imponible_isc_item + "|"
                            + nombre_tributo_item_isc + "|"
                            + codigo_tipo_tributo_item + "|"
                            + tipo_sistema_isc + "|"
                            + porcentaje_isc + "|"
                            + codigo_tipo_tributo_otro + "|"
                            + monto_tributo_otro_item + "|"
                            + base_imponible_tributo_otro_item + "|"
                            + nombre_tributo_otro_item + "|"
                            + codigo_tipo_tributo_otro_item + "|"
                            + porcentaje_tributo_otro_item + "|"
                            + codigo_icbper + "|"
                            + monto_icbper_item + "|"
                            + cantidad_icbper_item + "|"
                            + nombre_icbper_item + "|"
                            + codigo_icbper_item + "|"
                            + monto_icbper_unidad + "|"
                            + precio_venta_unitario + "|"
                            + valor_venta_item + "|"
                            + valor_referencial_gratuito + "|\n");
                }
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println("Error creando archivo plano " + id + ".DET:\n" + e);
                Metodos.MensajeError("Error creando archivo plano " + id + ".DET:\n" + e);
            }
        }
    }
    
    public void ArchivoPlanoTRI(){
        //se crea archivo con el nombre establecido
        File ap = new File(Rutas.getRutaAP("01", id, "TRI"));
        BufferedWriter bufferedWriter;
        if (ap.exists()) {
            Metodos.MensajeError("Error generando archivo\n"
                    + "plano " + id + ".TRI:\n"
                    + "El archivo ya existe");
        } else {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(ap));
                String identificador_tributo = "1000";
                String nombre_tributo = "IGV";
                String codigo_tipo_tributo = "VAT";
                String base_imponible = jtxtImporte.getText();
                String monto_tributo_item = String.valueOf(jtxtIgv.getText());
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                        identificador_tributo + "|"
                        + nombre_tributo + "|"
                        + codigo_tipo_tributo + "|"
                        + base_imponible + "|"
                        + monto_tributo_item + "|\n");
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println("Error creando archivo plano " + id + ".TRI:\n" + e);
                Metodos.MensajeError("Error creando archivo plano " + id + ".TRI:\n" + e);
            }
        }
    }
    
    public void ArchivoPlanoLEY(){
        //se crea archivo con el nombre establecido
        File ap = new File(Rutas.getRutaAP("01", id, "LEY"));
        BufferedWriter bufferedWriter;
        if (ap.exists()) {
            Metodos.MensajeError("Error generando archivo\n"
                    + "plano '" + id + ".LEY':\n"
                    + "El archivo ya existe");
        } else {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(ap));
                String codigo_leyenda = "1000";//venta interna
                String descripcion_leyenda = jlblMontoEnTexto.getText();
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                        codigo_leyenda + "|"
                        + descripcion_leyenda
                );
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println("Error creando archivo plano " + id + ".LEY:\n" + e);
                Metodos.MensajeError("Error creando archivo plano " + id + ".LEY:\n" + e);
            }
        }
    }
    
    public void ArchivoPlanoACA(){
        //se crea archivo con el nombre establecido
        File ap = new File(Rutas.getRutaAP("01", id, "ACA"));
        BufferedWriter bufferedWriter;
        if (ap.exists()) {
            Metodos.MensajeError("Error generando archivo\n"
                    + "plano '" + id + ".ACA':\n"
                    + "El archivo ya existe");
        } else {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(ap));
                String cuenta_detraccion = "";
                String codigo_producto_detraccion = "";
                String porcentaje_detraccion = "";
                String monto_detraccion = "";
                String medio_pago = Metodos.ObtenerCodigoMedioPago(jcbxMedioPago.getSelectedItem().toString());
                //direccion cliente
                String direccion_cliente_pais = "-";
                String direccion_cliente_ubigeo = "-";
                String direccion_cliente_detallada = "-";
                //direccion distinta a la del cliente
                String direccion_cliente_pais_distinta = "-";
                String direccion_cliente_ubigeo_distinta = "-";
                String direccion_cliente_detallada_distinta = "-";
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                        cuenta_detraccion + "|"
                        + codigo_producto_detraccion + "|"
                        + porcentaje_detraccion + "|"
                        + monto_detraccion + "|"
                        + medio_pago + "|"
                        + direccion_cliente_pais + "|"
                        + direccion_cliente_ubigeo + "|"
                        + direccion_cliente_detallada + "|"
                        + direccion_cliente_pais_distinta + "|"
                        + direccion_cliente_ubigeo_distinta + "|"                                
                        + direccion_cliente_detallada_distinta
                );
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println("Error creando archivo plano " + id + ".ACA:\n" + e);
                Metodos.MensajeError("Error creando archivo plano " + id + ".ACA:\n" + e);
            }
        }
    }
    
    private void RegistrarFactura() {
        //capturamos los datos a enviar desde el frame
        String fecha = jtxtFecha.getText();
        String moneda = jcbxMoneda.getSelectedItem().toString();
        String medioPago = jcbxMedioPago.getSelectedItem().toString();
        String totalVentasGravadas = jtxtImporte.getText();
        String igv = jtxtIgv.getText();
        String totalImporteVenta = jtxtImporteTotal.getText();
        try {
            Factura.RegistrarFactura(id, idCliente, fecha,
                    moneda, medioPago, totalVentasGravadas, igv, totalImporteVenta);
        } catch (Exception e) {
            System.out.println("Error registrando factura a la base de datos: \n" + e);
            Metodos.MensajeError("Error registrando factura a la base de datos: \n" + e);
        }
    }
    
    private void RegistrarFacturaDet() {
        try {
            int numero = 1;//contador para IdFacturaDet
            for (int i = 0; i < jtblDetalle.getRowCount(); i++) {
                String Item = jtblDetalle.getValueAt(i, 0).toString();
                String Cantidad = jtblDetalle.getValueAt(i, 1).toString();
                String TipoUnidad = jtblDetalle.getValueAt(i, 2).toString();
                String Descripcion = jtblDetalle.getValueAt(i, 3).toString();
                String PrecioUnitario = jtblDetalle.getValueAt(i, 4).toString();
                String PrecioTotal = jtblDetalle.getValueAt(i, 5).toString();
                String Id = id + "-" + String.format("%03d", (numero++));
                String IdFactura = id;
                Factura.RegistrarFacturaDet(Id, IdFactura, Item, Cantidad,
                        TipoUnidad, Descripcion, PrecioUnitario, PrecioTotal);
            }
        } catch (Exception e) {
            System.out.println("Error registrando detalle de factura en base de datos: \n" + e);
            Metodos.MensajeError("Error registrando detalle de factura en base de datos: \n" + e);            
        }
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
        jtxtTipoDocumento = new javax.swing.JTextField();
        jtxtDireccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jcbxMoneda = new javax.swing.JComboBox<>();
        jbtnBuscar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jtxtNumeroDocumento = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jcbxMedioPago = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblDetalle = new javax.swing.JTable();
        jbtnAgregar = new javax.swing.JButton();
        jbtnQuitar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtxtPrecioUnitario = new javax.swing.JTextField();
        jtxtCantidad = new javax.swing.JTextField();
        jtxtDescripcion = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jcbxTipoUnidad = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jtxtFecha = new javax.swing.JTextField();
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

        jtxtNombreRazonSocial.setEditable(false);
        jtxtNombreRazonSocial.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jtxtTipoDocumento.setEditable(false);
        jtxtTipoDocumento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jtxtDireccion.setEditable(false);
        jtxtDireccion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Moneda:");

        jcbxMoneda.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxMoneda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PEN", "USD" }));
        jcbxMoneda.setEnabled(false);
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

        jtxtNumeroDocumento.setEditable(false);
        jtxtNumeroDocumento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel16.setText("Medio de pago:");

        jcbxMedioPago.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxMedioPago.setMaximumRowCount(9);
        jcbxMedioPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione-", "Depósito en cuenta", "Giro", "Transferencia de fondos", "Orden de pago", "Tarjeta de débito", "Efectivo", "Tarjeta de crédito", "Otros" }));
        jcbxMedioPago.setEnabled(false);

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
                        .addComponent(jtxtTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                        .addComponent(jLabel2)
                        .addComponent(jtxtTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(jtxtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcbxMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
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
                "Item", "Cantidad", "Tipo unidad", "Descripción", "Precio unittario", "Importe", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblDetalle.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtblDetalle);
        if (jtblDetalle.getColumnModel().getColumnCount() > 0) {
            jtblDetalle.getColumnModel().getColumn(0).setPreferredWidth(50);
            jtblDetalle.getColumnModel().getColumn(1).setPreferredWidth(100);
            jtblDetalle.getColumnModel().getColumn(2).setPreferredWidth(100);
            jtblDetalle.getColumnModel().getColumn(3).setPreferredWidth(300);
            jtblDetalle.getColumnModel().getColumn(4).setPreferredWidth(100);
            jtblDetalle.getColumnModel().getColumn(5).setPreferredWidth(100);
            jtblDetalle.getColumnModel().getColumn(6).setMinWidth(0);
            jtblDetalle.getColumnModel().getColumn(6).setPreferredWidth(0);
            jtblDetalle.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        jbtnAgregar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnAgregar.setText("Agregar");
        jbtnAgregar.setEnabled(false);
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
        jLabel9.setText("Precio unitario:");

        jtxtPrecioUnitario.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtPrecioUnitario.setEnabled(false);

        jtxtCantidad.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtCantidad.setEnabled(false);

        jtxtDescripcion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtDescripcion.setEnabled(false);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel15.setText("Tipo unidad:");

        jcbxTipoUnidad.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxTipoUnidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UN", "MT", "KG", "CJA", "LT" }));
        jcbxTipoUnidad.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxtCantidad)
                            .addComponent(jcbxTipoUnidad, 0, 100, Short.MAX_VALUE)
                            .addComponent(jtxtPrecioUnitario)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnQuitar, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtDescripcion))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jcbxTipoUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jtxtPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(31, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Fecha:");

        jtxtFecha.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtFecha.setEnabled(false);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnCrearArchivosPlanos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlblMontoEnTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblMontoEnTexto)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbtnCrearArchivosPlanos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAgregarActionPerformed
        if (jtxtCantidad.getText().equalsIgnoreCase("")) {
            Metodos.MensajeAlerta("Escriba la cantidad.");
        } else if (jtxtDescripcion.getText().equalsIgnoreCase("")) {
            Metodos.MensajeAlerta("Escriba una descripción.");
        } else if (jtxtPrecioUnitario.getText().equalsIgnoreCase("")) {
            Metodos.MensajeAlerta("Escriba el precio unitario.");
        } else {
            AgregarDetalle();
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
        JDialogClienteBuscar jdcb = new JDialogClienteBuscar(Metodos.FormatoJDialog(), true);
        jdcb.setVisible(true);
    }//GEN-LAST:event_jbtnBuscarActionPerformed

    private void jbtnCrearArchivosPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCrearArchivosPlanosActionPerformed
        // valido medio de pago
        if(jcbxMedioPago.getSelectedIndex() == 0){
            Metodos.MensajeAlerta("Seleccione el medio de pago.");
        }else {
            CrearArchivosPlanos();
            RegistrarFactura();
            RegistrarFacturaDet();
            JPanelComprobantes jpc = new JPanelComprobantes();
            Metodos.CambiarPanel(jpc);
        }
    }//GEN-LAST:event_jbtnCrearArchivosPlanosActionPerformed

    private void jcbxMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxMonedaActionPerformed
        ActualizarTotales();
    }//GEN-LAST:event_jcbxMonedaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JButton jbtnCrearArchivosPlanos;
    private javax.swing.JButton jbtnQuitar;
    public static javax.swing.JComboBox<String> jcbxMedioPago;
    public static javax.swing.JComboBox<String> jcbxMoneda;
    public static javax.swing.JComboBox<String> jcbxTipoUnidad;
    private javax.swing.JLabel jlblMontoEnTexto;
    private javax.swing.JTable jtblDetalle;
    public static javax.swing.JTextField jtxtCantidad;
    public static javax.swing.JTextField jtxtDescripcion;
    private javax.swing.JTextField jtxtDireccion;
    private javax.swing.JTextField jtxtFecha;
    private javax.swing.JTextField jtxtId;
    private javax.swing.JTextField jtxtIgv;
    private javax.swing.JTextField jtxtImporte;
    private javax.swing.JTextField jtxtImporteTotal;
    private javax.swing.JTextField jtxtNombreRazonSocial;
    private javax.swing.JTextField jtxtNumeroDocumento;
    public static javax.swing.JTextField jtxtPrecioUnitario;
    private javax.swing.JTextField jtxtTipoDocumento;
    // End of variables declaration//GEN-END:variables
}

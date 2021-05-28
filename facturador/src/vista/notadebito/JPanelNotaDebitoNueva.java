package vista.notadebito;

import controlador.Metodos;
import controlador.Rutas;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.NotaDebito;
import vista.JPanelComprobantes;

public class JPanelNotaDebitoNueva extends javax.swing.JPanel {
    
    static ResultSet rs;
    String id;
    DefaultTableModel dtmDetalle;

    public JPanelNotaDebitoNueva() {
        initComponents();
        jtxtFecha.setText(Metodos.CargarFechaActual());
        CargarComprobante();
    }
    
    void CargarComprobante() {
        JDialogComprobanteBuscar jdcb = new JDialogComprobanteBuscar(Metodos.FormatoJDialog(), true);
        //capturo los datos en variables
        String idComprobante = jdcb.id;
        try {
            //defino si el comprobantes es factura o boleta
            String tipoComprobante;
            if(idComprobante.contains("FF")){
                tipoComprobante = "factura";
            } else {
                tipoComprobante = "boleta";
            }
            rs = Cliente.Consulta("select * \n"
                    + "from "+ tipoComprobante +" \n"
                    + "inner join cliente \n"
                    + "on cliente.id = "+ tipoComprobante +".idCliente \n"
                    + "where "+ tipoComprobante +".id = '"+idComprobante+"';");
            rs.next();
            String tipoDocumento = rs.getString("tipoDocumento");
            String numeroDocumento = rs.getString("numeroDocumento");
            String nombreRazonSocial = rs.getString("nombreRazonSocial");
            String direccion = rs.getString("direccion");
            String moneda = rs.getString("moneda");
            String medioPago = rs.getString("medioPago");
            String totalVentasGravadas = rs.getString("totalVentasGravadas");
            String igv = rs.getString("igv");
            String importeTotal = rs.getString("importeTotal");
            // mostrando datos
            jlblComprobante.setText(idComprobante);
            jtxtTipoDocumento.setText(tipoDocumento);
            jtxtNumeroDocumento.setText(numeroDocumento);
            jtxtNombreRazonSocial.setText(nombreRazonSocial);
            jtxtDireccion.setText(direccion);
            jcbxMoneda.setSelectedItem(moneda);
            jcbxMedioPago.setSelectedItem(medioPago);
            // mostrando totales
            jlblTotalVentasGravadas.setText(totalVentasGravadas);
            jlblIgv.setText(igv);
            jlblImporteTotal.setText(importeTotal);
            rs.close();
            CargarIdNotaDebito(tipoComprobante);
            CargarComprobanteDet(idComprobante);
        } catch (Exception e) {
            System.out.println("Error cargando comprobante seleccionado: \n"+e);
            //No se muestra el mensaje xq el primero siempre dará error por las ??? incógnitas
            //Metodos.MensajeError("Error cargando Cliente Seleccionado: \n" + e);
        }
    }
    
    void CargarIdNotaDebito(String tipoComprobante){
        String nombreNotaDebito;//valor que siempre tendrá
        String x;
        if (tipoComprobante.equalsIgnoreCase("factura")){
            nombreNotaDebito = "FD01-";
            x = "F";
        } else {
            nombreNotaDebito = "BD01-";
            x = "B";
        }
        try {
            rs = NotaDebito.Consulta("select * \n"
                    + "from notadebito \n"
                    + "where id LIKE '%"+x+"D%';");
            if(rs.last() == true){
                // obtengo el id
                String Id = rs.getString("id");
                // obtengo ultimos 8 caracteres
                String numero_notadebito = Metodos.ObtenerUltimosXCaracteres(Id, 8);
                // convierto a entero para sumar +1
                int numero = Integer.parseInt(numero_notadebito) + 1;
                String numeroNotaDebito = String.format("%08d", numero);
                nombreNotaDebito = nombreNotaDebito + numeroNotaDebito;
                id = nombreNotaDebito;
                jtxtId.setText(id);
            }   else {
                nombreNotaDebito = nombreNotaDebito + "00000001";
                id = nombreNotaDebito;
                jtxtId.setText(id);
            }         
            rs.close();
        } catch (Exception e) {
            System.out.println("Error generando id de nota de débito: \n" + e);
            Metodos.MensajeError("Error generando id de nota de débito: \n" + e);
        }
    }
    
    void CargarComprobanteDet(String idComprobante){
        try {
            //defino si el comprobantes es factura o boleta
            String tipoComprobante;
            if(idComprobante.contains("FF")){
                tipoComprobante = "factura";
            } else {
                tipoComprobante = "boleta";
            }
            dtmDetalle = (DefaultTableModel) jtblDetalle.getModel();
            dtmDetalle.setRowCount(0);
            rs = Cliente.Consulta("select * \n"
                    + "from " + tipoComprobante + "det \n"
                    + "where id" + tipoComprobante + " = '" + idComprobante + "';");
            String fila[] = new String[7];
            while (rs.next()) {
                fila[0] = rs.getString("item");
                fila[1] = rs.getString("cantidad");
                fila[2] = rs.getString("tipoUnidad");
                fila[3] = rs.getString("descripcion");
                fila[4] = rs.getString("valorUnitario");
                fila[5] = rs.getString("precioUnitarioItem");
                //oculto
                fila[6] = rs.getString("precioUnitarioItem");
                dtmDetalle.addRow(fila);
            }
            rs.close();
            ActualizarTotales();
        } catch (Exception e) {
            System.out.println("Error cargando detalle de comprobante: \n" + e);
            Metodos.MensajeError("Error cargando detalle de comprobante: \n" + e);
        }
    }
    
    private void CambiarMotivo() {
        int motivo = jcbxMotivo.getSelectedIndex();
        String idComprobante = jlblComprobante.getText();
        switch (motivo){
            case 0:// seleccione
                Metodos.LimpiarTabla(jtblDetalle);
                LimpiarCamposDetalle();
                CargarComprobanteDet(idComprobante);
                BloquearCamposDetalle();
                BloquearBotonesDetalle();
                BloquearBotonesNotaDebito();
                jtblDetalle.setEnabled(false);
                jtblDetalle.setRowSelectionAllowed(false);
                break;
            case 1://Interés por mora
                Metodos.LimpiarTabla(jtblDetalle);
                LimpiarCamposDetalle();
                //activando campos del detalle documento
                jtxtCantidad.setEnabled(true);
                jcbxTipoUnidad.setEnabled(true);
                jtxtPrecioUnitario.setEnabled(true);
                jtxtDescripcion.setEnabled(true);
                //activar/desactivar botones detalle documento
                BloquearBotonesDetalle();
                jbtnAgregar.setEnabled(true);
                BloquearBotonesNotaDebito();
                jtblDetalle.setEnabled(true);
                jtblDetalle.setRowSelectionAllowed(true);
                break;
            case 2://Aumento en el valor
                Metodos.LimpiarTabla(jtblDetalle);
                LimpiarCamposDetalle();
                CargarComprobanteDet(idComprobante);
                BloquearCamposDetalle();
                BloquearBotonesDetalle();
                BloquearBotonesNotaDebito();
                jbtnEditar.setEnabled(true);
                jbtnCrearArchivosPlanos.setEnabled(true);
                jtblDetalle.setEnabled(true);
                jtblDetalle.setRowSelectionAllowed(true);
                break;
            default://Penalidades
                Metodos.LimpiarTabla(jtblDetalle);
                LimpiarCamposDetalle();//limpiando campos
                //activando campos del detalle documento
                jtxtCantidad.setEnabled(true);
                jcbxTipoUnidad.setEnabled(true);
                jtxtPrecioUnitario.setEnabled(true);
                jtxtDescripcion.setEnabled(true);
                BloquearBotonesDetalle();
                jbtnAgregar.setEnabled(true);
                BloquearBotonesNotaDebito();
                jbtnCrearArchivosPlanos.setEnabled(false);
                jtblDetalle.setEnabled(true);
                jtblDetalle.setRowSelectionAllowed(true);
                break;
        }
    }
    
    private void LimpiarCamposDetalle() {
        jtxtCantidad.setText("");
        jcbxTipoUnidad.setSelectedIndex(0);
        jtxtPrecioUnitario.setText("");
        jtxtDescripcion.setText("");
    }
    
    private void BloquearCamposDetalle() {
        jtxtCantidad.setEnabled(false);
        jcbxTipoUnidad.setEnabled(false);
        jtxtPrecioUnitario.setEnabled(false);
        jtxtDescripcion.setEnabled(false);
    }
    
    private void BloquearBotonesDetalle() {
        jbtnAgregar.setEnabled(false);
        jbtnQuitar.setEnabled(false);
        jbtnEditar.setEnabled(false);
        jbtnActualizar.setEnabled(false);
    }
    
    private void BloquearBotonesNotaDebito() {
        jbtnCrearArchivosPlanos.setEnabled(false);
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
    
    private void ActualizarDetalle() {
        int fila = jtblDetalle.getSelectedRow();
        double Cantidad = Double.parseDouble(jtxtCantidad.getText());//no se da formato decimal aun para poder operar
        String TipoUnidad = jcbxTipoUnidad.getSelectedItem().toString();
        double PrecioUnitario = Double.parseDouble(jtxtPrecioUnitario.getText());
        String Descripcion = jtxtDescripcion.getText();
        double PrecioTotal = Cantidad * PrecioUnitario;
        jtblDetalle.setValueAt(Metodos.FormatoDecimalMostrar(String.valueOf(Cantidad)), fila, 1);
        jtblDetalle.setValueAt(TipoUnidad, fila, 2);
        jtblDetalle.setValueAt(Descripcion, fila, 3);
        jtblDetalle.setValueAt(Metodos.FormatoDecimalMostrar(String.valueOf(PrecioUnitario)), fila, 4);
        jtblDetalle.setValueAt(Metodos.FormatoDecimalMostrar(String.valueOf(PrecioTotal)), fila, 5);
        jtblDetalle.setValueAt(Metodos.FormatoDecimalMostrar(String.valueOf(PrecioTotal)), fila, 6);
        // activar/desactivar objetos
        jbtnAgregar.setEnabled(true);
        jbtnQuitar.setEnabled(true);
        jbtnEditar.setEnabled(true);
        jbtnActualizar.setEnabled(false);
        jtblDetalle.setEnabled(true);
        // limpiamos campos
        jtxtCantidad.setText("");
        jcbxTipoUnidad.setSelectedIndex(0);
        jtxtDescripcion.setText("");
        jtxtPrecioUnitario.setText("");
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
        ArchivoPlanoNOT();
        ArchivoPlanoDET();
        ArchivoPlanoTRI();
        ArchivoPlanoLEY();
        ArchivoPlanoACA();
        Metodos.MensajeInformacion("Archivos planos generados.");
    }
    
    public void ArchivoPlanoNOT(){
        //se crea archivo con el nombre establecido
        File ap = new File(Rutas.getRutaAP("08", id, "NOT"));
        //para almacenar datos
        BufferedWriter bufferedWriter;
        if (ap.exists()) {
            Metodos.MensajeError("Error generando archivo\n"
                    + "plano '" + id + ".NOT':\n"
                    + "El archivo ya existe");
        } else {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(ap));
                //21 valores SUNAT CAB SFS 1.3.4.4
                String tipo_operacion = "0101";//venta interna       
                String fecha_emision = Metodos.FechaActualFormatoSUNAT();//fecha en formato YYYY-MM-DD
                String hora_emision = Metodos.ObtenerHora();
                String codigo_domicilio_fiscal = "0000";
                String tipo_documento_receptor = Metodos.ObtenerCodigoTipoDocumento(jtxtTipoDocumento.getText());//6=RUC, 1=DNI
                String numero_documento_cliente = jtxtNumeroDocumento.getText();
                String nombre_o_razon_social = jtxtNombreRazonSocial.getText();
                String moneda = jcbxMoneda.getSelectedItem().toString();//PEN,USD
                String codigo_motivo = Metodos.CodigoTipoNotaDebito(jcbxMotivo);//catalogo 9
                String descripcion_motivo = jcbxMotivo.getSelectedItem().toString();
                String tipo_documento_modifica;
                if (jlblComprobante.getText().contains("FF"))
                    tipo_documento_modifica = "01";
                else 
                    tipo_documento_modifica = "03";
                String serie_y_numero_documento = jlblComprobante.getText();
                String sumatoria_tributos = String.valueOf(jtxtIgv.getText()).replace(",", "");
                String total_valor_venta = String.valueOf(jtxtImporte.getText()).replace(",", "");
                String total_precio_venta = String.valueOf(jtxtImporteTotal.getText()).replace(",", "");;
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
                    + codigo_domicilio_fiscal + "|"
                    + tipo_documento_receptor + "|"
                    + numero_documento_cliente + "|"
                    + nombre_o_razon_social + "|"
                    + moneda + "|"
                    + codigo_motivo + "|"
                    + descripcion_motivo + "|"
                    + tipo_documento_modifica + "|"
                    + serie_y_numero_documento + "|"
                    + sumatoria_tributos + "|"
                    + total_valor_venta + "|"
                    + total_precio_venta + "|"
                    + total_descuentos + "|"
                    + sumatoria_otros_cargos + "|"
                    + total_anticipos + "|"
                    + importe_total_venta + "|"
                    + version_UBL + "|"
                    + custo_documento + "|"
                );
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println("Error creando archivo plano " + id + ".NOT:\n" + e);
                Metodos.MensajeError("Error creando el archivo plano " + id + ".NOT:\n" + e);
            }
        }
    }
    
    public void ArchivoPlanoDET(){
        //se crea archivo con el nombre establecido
        File ap = new File(Rutas.getRutaAP("08", id, "DET"));
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
        File ap = new File(Rutas.getRutaAP("08", id, "TRI"));
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
        File ap = new File(Rutas.getRutaAP("08", id, "LEY"));
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
        File ap = new File(Rutas.getRutaAP("08", id, "ACA"));
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
    
    private void RegistrarNotaDebito() {
        //capturamos los datos a enviar desde el frame
        String idComprobante = jlblComprobante.getText();
        String fecha = jtxtFecha.getText();
        String motivo = jcbxMotivo.getSelectedItem().toString();
        String totalVentasGravadas = jtxtImporte.getText();
        String igv = jtxtIgv.getText();
        String totalImporteVenta = jtxtImporteTotal.getText();
        try {
            NotaDebito.RegistrarNotaDebito(id, idComprobante, fecha,
                    motivo, totalVentasGravadas, igv, totalImporteVenta);
        } catch (Exception e) {
            System.out.println("Error registrando nota de débito a la base de datos: \n" + e);
            Metodos.MensajeError("Error registrando nota de débito a la base de datos: \n" + e);
        }
    }
    
    private void RegistrarNotaDebitoDet() {
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
                String IdNotaDebito = id;
                NotaDebito.RegistrarNotaDebitoDet(Id, IdNotaDebito, Item, Cantidad,
                        TipoUnidad, Descripcion, PrecioUnitario, PrecioTotal);
            }
        } catch (Exception e) {
            System.out.println("Error registrando detalle de nota de crédito en base de datos: \n" + e);
            Metodos.MensajeError("Error registrando detalle de nota de crédito en base de datos: \n" + e);            
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
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jlblTotalVentasGravadas = new javax.swing.JLabel();
        jlblIgv = new javax.swing.JLabel();
        jlblImporteTotal = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jlblComprobante = new javax.swing.JLabel();
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
        jbtnActualizar = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();
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
        jLabel20 = new javax.swing.JLabel();
        jcbxMotivo = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 192, 192));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Nota Débito N°");

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

        jLabel17.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel17.setText("Sub total:");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel18.setText("IGV:");

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setText("Importe Total:");

        jlblTotalVentasGravadas.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblTotalVentasGravadas.setText("0.00");

        jlblIgv.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblIgv.setText("0.00");

        jlblImporteTotal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblImporteTotal.setText("0.00");

        jLabel21.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel21.setText("Comprobante:");

        jlblComprobante.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblComprobante.setText("???");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtDireccion)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtxtTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbxMedioPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtxtNombreRazonSocial))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbxMoneda, 0, 100, Short.MAX_VALUE))
                            .addComponent(jbtnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlblTotalVentasGravadas, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addGap(29, 29, 29)
                        .addComponent(jlblIgv, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(jlblImporteTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlblComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(jbtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblTotalVentasGravadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(jLabel19)
                        .addComponent(jLabel18)
                        .addComponent(jlblIgv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlblImporteTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jlblComprobante))
                        .addGap(0, 0, Short.MAX_VALUE))))
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

        jbtnActualizar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnActualizar.setText("Actualizar");
        jbtnActualizar.setEnabled(false);
        jbtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnActualizarActionPerformed(evt);
            }
        });

        jbtnEditar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnEditar.setText("Editar");
        jbtnEditar.setEnabled(false);
        jbtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxtCantidad)
                            .addComponent(jcbxTipoUnidad, 0, 100, Short.MAX_VALUE)
                            .addComponent(jtxtPrecioUnitario)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnQuitar, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jbtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                            .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        jLabel20.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel20.setText("Motivo:");

        jcbxMotivo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxMotivo.setMaximumRowCount(10);
        jcbxMotivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione-", "Interés por mora", "Aumento en el valor", "Penalidades" }));
        jcbxMotivo.setEnabled(false);
        jcbxMotivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxMotivoActionPerformed(evt);
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
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(jcbxMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbtnCrearArchivosPlanos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlblMontoEnTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(102, 102, 102)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(jLabel5)
                    .addComponent(jLabel20)
                    .addComponent(jcbxMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlblMontoEnTexto)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnCrearArchivosPlanos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            Metodos.MensajeAlerta("Seleccione un detalle.");
        }
    }//GEN-LAST:event_jbtnQuitarActionPerformed

    private void jbtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscarActionPerformed
        JDialogComprobanteBuscar jdcb = new JDialogComprobanteBuscar(Metodos.FormatoJDialog(), true);
        jdcb.setVisible(true);
    }//GEN-LAST:event_jbtnBuscarActionPerformed

    private void jbtnCrearArchivosPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCrearArchivosPlanosActionPerformed
        // valido medio de pago
        if(jcbxMedioPago.getSelectedIndex() == 0){
            Metodos.MensajeAlerta("Seleccione el medio de pago.");
        }else {
            CrearArchivosPlanos();
            RegistrarNotaDebito();
            RegistrarNotaDebitoDet();
            JPanelComprobantes jpc = new JPanelComprobantes();
            Metodos.CambiarPanel(jpc);
        }
    }//GEN-LAST:event_jbtnCrearArchivosPlanosActionPerformed

    private void jcbxMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxMonedaActionPerformed
        ActualizarTotales();
    }//GEN-LAST:event_jcbxMonedaActionPerformed

    private void jcbxMotivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxMotivoActionPerformed
        CambiarMotivo();
    }//GEN-LAST:event_jcbxMotivoActionPerformed

    private void jbtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnActualizarActionPerformed
        if (jtxtCantidad.getText().equalsIgnoreCase("")) {
            Metodos.MensajeAlerta("Escriba la cantidad.");
        } else if (jtxtDescripcion.getText().equalsIgnoreCase("")) {
            Metodos.MensajeAlerta("Escriba una descripción.");
        } else if (jtxtPrecioUnitario.getText().equalsIgnoreCase("")) {
            Metodos.MensajeAlerta("Escriba el precio unitario.");
        } else {
            ActualizarDetalle();
        }
    }//GEN-LAST:event_jbtnActualizarActionPerformed

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
        int fila = jtblDetalle.getSelectedRow();
        if (fila >= 0) {//si hay fila seleccionada
            String Cantidad = Metodos.FormatoDecimalOperar(Double.parseDouble(jtblDetalle.getValueAt(fila, 1).toString()));
            String TipoUnidad = jtblDetalle.getValueAt(fila, 2).toString();
            String PrecioUnitario = Metodos.FormatoDecimalOperar(Double.parseDouble(jtblDetalle.getValueAt(fila, 4).toString()));
            String Descripcion = jtblDetalle.getValueAt(fila, 3).toString();
            jtxtCantidad.setText(String.valueOf(Cantidad));
            jcbxTipoUnidad.setSelectedItem(TipoUnidad);
            jtxtDescripcion.setText(Descripcion);
            jtxtPrecioUnitario.setText(String.valueOf(PrecioUnitario));
            // activar campos det
            jtxtCantidad.setEnabled(true);
            jcbxTipoUnidad.setEnabled(true);
            jtxtPrecioUnitario.setEnabled(true);
            jtxtDescripcion.setEnabled(true);
            // activar/desactivar objetos
            jbtnAgregar.setEnabled(false);
            jbtnQuitar.setEnabled(false);
            jbtnEditar.setEnabled(false);
            jbtnActualizar.setEnabled(true);
            jtblDetalle.setEnabled(false);
        } else {//si no hay fila seleccionada
            Metodos.MensajeAlerta("Seleccione un detalle");
        }
    }//GEN-LAST:event_jbtnEditarActionPerformed


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
    private javax.swing.JLabel jLabel21;
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
    public static javax.swing.JButton jbtnActualizar;
    public static javax.swing.JButton jbtnAgregar;
    public static javax.swing.JButton jbtnBuscar;
    private javax.swing.JButton jbtnCrearArchivosPlanos;
    public static javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnQuitar;
    public static javax.swing.JComboBox<String> jcbxMedioPago;
    public static javax.swing.JComboBox<String> jcbxMoneda;
    public static javax.swing.JComboBox<String> jcbxMotivo;
    public static javax.swing.JComboBox<String> jcbxTipoUnidad;
    private javax.swing.JLabel jlblComprobante;
    private javax.swing.JLabel jlblIgv;
    private javax.swing.JLabel jlblImporteTotal;
    private javax.swing.JLabel jlblMontoEnTexto;
    private javax.swing.JLabel jlblTotalVentasGravadas;
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

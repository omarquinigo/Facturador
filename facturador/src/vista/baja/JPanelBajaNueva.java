package vista.baja;

import controlador.ArchivosPlanos;
import controlador.Metodos;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import modelo.Baja;
import modelo.Boleta;
import modelo.Factura;
import modelo.NotaCredito;
import modelo.NotaDebito;

public class JPanelBajaNueva extends javax.swing.JPanel {
    
    ResultSet rs;
    String id;
    DefaultTableModel dtmComprobantes;
    DefaultTableModel dtmBajas;

    public JPanelBajaNueva() {
        initComponents();
        jtxtFecha.setText(Metodos.CargarFechaActual());
        cargarIdBaja();
    }
    
    private void cargarIdBaja(){
        String fechaActual = Metodos.FechaActualFormatoSUNATSinGuiones(jtxtFecha.getText());
        String nombreBaja = "RA-" + fechaActual + "-";
        try {
            rs = Baja.consulta("select * \n"
                    + "from baja \n"
                    + "where id LIKE '%" + fechaActual + "%';");
            if (rs.last() == true) {
                // obtengo el id
                String idBaja = rs.getString("id");
                // obtengo ultimos 8 caracteres
                String numero_baja = Metodos.ObtenerUltimosXCaracteres(idBaja, 3);
                // convierto a entero para sumar +1
                int numero = Integer.parseInt(numero_baja) + 1;
                String numeroBaja = String.format("%03d", numero);
                nombreBaja = nombreBaja + numeroBaja;
                id = nombreBaja;
                jtxtId.setText(id);
            }   else {
                nombreBaja = nombreBaja + "001";
                id = nombreBaja;
                jtxtId.setText(id);
            }         
            rs.close();
        } catch (Exception e) {
            System.out.println("Error generando id de baja: \n" + e);
            Metodos.mensajeError("Error generando id de baja: \n" + e);
        }
    }
    
    private void cargarFacturas(String fecha) {
        try {
            dtmComprobantes = (DefaultTableModel) jtblComprobantes.getModel();
            rs = Factura.consulta("select * \n"
                    + "from factura\n"
                    + "inner join cliente\n"
                    + "on cliente.id = factura.idCliente\n"
                    + "where factura.fecha = '" + fecha + "' "
                    + "order by factura.id desc;");
            String fila[] = new String[3];
            while (rs.next()) {
                fila[0] = "Factura";
                fila[1] = rs.getString("factura.id");
                fila[2] = rs.getString("nombreRazonSocial");
                dtmComprobantes.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando facturas: \n" + e);
            Metodos.mensajeError("Error cargando facturas: \n" + e);
        }
    }
    
    private void cargarBoletas(String fecha) {
        try {
            dtmComprobantes = (DefaultTableModel) jtblComprobantes.getModel();
            rs = Boleta.consulta("select * \n"
                    + "from boleta \n"
                    + "inner join cliente\n"
                    + "on cliente.id = boleta.idCliente\n"
                    + "where boleta.fecha = '" + fecha + "' "
                    + "order by boleta.id desc;");
            String fila[] = new String[3];
            while (rs.next()) {
                fila[0] = "Boleta";
                fila[1] = rs.getString("boleta.id");
                fila[2] = rs.getString("nombreRazonSocial");
                dtmComprobantes.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando boletas: \n" + e);
            Metodos.mensajeError("Error cargando boletas: \n" + e);
        }
    }
    
    private void cargarNotasCredito(String fecha) {
        try {
            dtmComprobantes = (DefaultTableModel) jtblComprobantes.getModel();
            rs = NotaCredito.consulta("select * \n"
                    + "from notacredito \n"
                    + "left join factura \n"
                    + "on factura.id = notacredito.idComprobante \n"
                    + "left join boleta \n"
                    + "on boleta.id = notacredito.idComprobante \n"
                    + "left join cliente as cf \n"
                    + "on cf.id = factura.idCliente \n"
                    + "left join cliente as cb \n"
                    + "on cb.id = boleta.idCliente \n"
                    + "where notacredito.fecha = '" + fecha + "' "
                    + "order by STR_TO_DATE(notacredito.fecha, '%d/%m/%Y') desc, \n"
                    + "notacredito.id desc;");
            String fila[] = new String[3];
            while (rs.next()) {
                fila[0] = "Nota de crédito";
                fila[1] = rs.getString("notacredito.id");
                String aux = rs.getString("factura.id");
                if(aux!= null){ // cliente factura
                    fila[2] = rs.getString("cf.nombreRazonSocial");
                } else { // cliente boleta
                    fila[2] = rs.getString("cb.nombreRazonSocial");
                }  
                dtmComprobantes.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando notas de crédito: \n" + e);
            Metodos.mensajeError("Error cargando crédito: \n" + e);
        }
    }
    
    private void cargarNotasDebito(String fecha) {
        try {
            dtmComprobantes = (DefaultTableModel) jtblComprobantes.getModel();
            rs = NotaDebito.consulta("select * \n"
                    + "from notadebito \n"
                    + "left join factura \n"
                    + "on factura.id = notadebito.idComprobante \n"
                    + "left join boleta \n"
                    + "on boleta.id = notadebito.idComprobante \n"
                    + "left join cliente as cf \n"
                    + "on cf.id = factura.idCliente \n"
                    + "left join cliente as cb \n"
                    + "on cb.id = boleta.idCliente \n"
                    + "where notadebito.fecha = '" + fecha + "' "
                    + "order by STR_TO_DATE(notadebito.fecha, '%d/%m/%Y') desc, \n"
                    + "notadebito.id desc;");
            String fila[] = new String[3];
            while (rs.next()) {
                fila[0] = "Nota de débito";
                fila[1] = rs.getString("notadebito.id");
                String aux = rs.getString("factura.id");
                if(aux!= null){ // cliente factura
                    fila[2] = rs.getString("cf.nombreRazonSocial");
                } else { // cliente boleta
                    fila[2] = rs.getString("cb.nombreRazonSocial");
                }  
                dtmComprobantes.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando notas de débito: \n" + e);
            Metodos.mensajeError("Error cargando débito: \n" + e);
        }
    }
    
    private void limpiarTablas(){
        Metodos.LimpiarTabla(jtblComprobantes);
        Metodos.LimpiarTabla(jtblBajas);
    }
    
    private void agregar(){
        //capturo los datos de la fila seleccionada
        int fila = jtblComprobantes.getSelectedRow();
        String tipoComprobante = jtblComprobantes.getValueAt(fila, 0).toString();
        String comprobante = jtblComprobantes.getValueAt(fila, 1).toString();
        String motivo = jcbxMotivo.getSelectedItem().toString();
        //creamos modelo de la tabla y enviamos la cabecera
        dtmBajas = (DefaultTableModel) jtblBajas.getModel();
        String row[] = new String[3];
        row[0] = tipoComprobante;
        row[1] = comprobante;
        row[2] = motivo;
        dtmBajas.addRow(row);
        jtblBajas.setModel(dtmBajas);//enviamos el modelo creado a la tabla
        jcbxMotivo.setSelectedIndex(0);
        jbtnBuscar.setEnabled(false);
        jdcFecha.setEnabled(false);
        jbtnCrearArchivosPlanos.setEnabled(true);
    }
    
    private void crearArchivoPlano() {
        ArchivosPlanos.apBaja(id);
        Metodos.mensajeInformacion("Archivos planos generados.");
        bloquearCampos();
        jbtnImprimir.setEnabled(true);
    }
    
    private void registrarBaja() {
        //capturamos los datos a enviar desde el frame
        String fecha = jtxtFecha.getText();
        String fechaComprobante = Metodos.getFechaJDC(jdcFecha);
        try {
            Baja.registrarBaja(id, fecha, fechaComprobante);
        } catch (Exception e) {
            System.out.println("Error registrando baja: \n" + e);
            Metodos.mensajeError("Error registrando baja: \n" + e);
        }
    }
    
    private void registrarBajaDet() {
        try {
            int numero = 1;//contador para IdFacturaDet
            for (int i = 0; i < jtblBajas.getRowCount(); i++) {
                String idBaja = id + "-" + String.format("%03d", (numero++));
                String tipoComprobante = jtblBajas.getValueAt(i, 0).toString();
                String idComprobante = jtblBajas.getValueAt(i, 1).toString();
                String motivo = jtblBajas.getValueAt(i, 2).toString();
                Baja.registrarBajaDet(idBaja, id, tipoComprobante, idComprobante, motivo);
            }
        } catch (Exception e) {
            System.out.println("Error registrando detalle de baja: \n" + e);
            Metodos.mensajeError("Error registrando detalle de baja: \n" + e);
        }
    }
    
    private void bloquearCampos(){
        jcbxMotivo.setEnabled(false);
        jbtnAgregar.setEnabled(false);
        jbtnCrearArchivosPlanos.setEnabled(false);
        jbtnImprimir.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtxtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtFecha = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblComprobantes = new javax.swing.JTable();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        jcbxMotivo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jbtnBuscar = new javax.swing.JButton();
        jbtnAgregar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblBajas = new javax.swing.JTable();
        jbtnCrearArchivosPlanos = new javax.swing.JButton();
        jbtnImprimir = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 192, 192));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Baja N°:");

        jtxtId.setEditable(false);
        jtxtId.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Fecha:");

        jtxtFecha.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar comrpobantes:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12))); // NOI18N

        jtblComprobantes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblComprobantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo comprobante", "Comprobante", "Cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblComprobantes.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtblComprobantes);
        if (jtblComprobantes.getColumnModel().getColumnCount() > 0) {
            jtblComprobantes.getColumnModel().getColumn(0).setPreferredWidth(50);
            jtblComprobantes.getColumnModel().getColumn(1).setPreferredWidth(50);
            jtblComprobantes.getColumnModel().getColumn(2).setPreferredWidth(350);
        }

        jdcFecha.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jcbxMotivo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxMotivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione-", "Error en descripción", "Error en moneda", "Error en fecha", "Error en RUC o razón social", "Error en dirección", "Error en cálculo de importes", "Otros" }));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Motivo:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Fecha:");

        jbtnBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnBuscar.setText("Buscar");
        jbtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBuscarActionPerformed(evt);
            }
        });

        jbtnAgregar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnAgregar.setText("Agregar");
        jbtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jbtnAgregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(144, 144, 144))
                        .addComponent(jbtnBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jcbxMotivo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbxMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 84, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(141, 170, 235));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comprobantes a anular:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12))); // NOI18N

        jtblBajas.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblBajas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo comprobante", "Comprobante", "Motivo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblBajas.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jtblBajas);
        if (jtblBajas.getColumnModel().getColumnCount() > 0) {
            jtblBajas.getColumnModel().getColumn(0).setPreferredWidth(50);
            jtblBajas.getColumnModel().getColumn(1).setPreferredWidth(50);
            jtblBajas.getColumnModel().getColumn(2).setPreferredWidth(350);
        }

        jbtnCrearArchivosPlanos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnCrearArchivosPlanos.setText("Crear Archivos Planos");
        jbtnCrearArchivosPlanos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCrearArchivosPlanosActionPerformed(evt);
            }
        });

        jbtnImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnImprimir.setText("Imprimir");
        jbtnImprimir.setEnabled(false);
        jbtnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnCrearArchivosPlanos, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jbtnCrearArchivosPlanos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 103, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscarActionPerformed
        limpiarTablas();
        try {
            String fecha = Metodos.CapturarDateChooser(jdcFecha);
            cargarFacturas(fecha);
            cargarBoletas(fecha);
            cargarNotasCredito(fecha);
            cargarNotasDebito(fecha);
        } catch (Exception e) {
            System.out.println("Fecha incorrecta: \n" + e);
            Metodos.mensajeError("Fecha incorrecta: \n" + e);
        }
    }//GEN-LAST:event_jbtnBuscarActionPerformed

    private void jbtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAgregarActionPerformed
        if (jcbxMotivo.getSelectedIndex() == 0) {
            Metodos.mensajeAlerta("Seleccione el motivo de baja.");
        } else {
            if (jtblComprobantes.getSelectedRow() >= 0) {
                agregar();
            } else {//si no existe ninguna fila seleccionada
                Metodos.mensajeAlerta("Debe seleccionar una comprobante.");
            }
        }
    }//GEN-LAST:event_jbtnAgregarActionPerformed

    private void jbtnCrearArchivosPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCrearArchivosPlanosActionPerformed
        try {
            registrarBaja();
            registrarBajaDet();
            crearArchivoPlano();
        } catch (Exception e) {
            Metodos.mensajeError("Error: "+ e);
        }
    }//GEN-LAST:event_jbtnCrearArchivosPlanosActionPerformed

    private void jbtnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnImprimirActionPerformed
        try {
            Baja.crearPDF(id);
        } catch (Exception e) {
            System.out.println("Error.\n" + e);
            Metodos.mensajeAlerta("Error." + e);
        }
    }//GEN-LAST:event_jbtnImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnAgregar;
    private javax.swing.JButton jbtnBuscar;
    private javax.swing.JButton jbtnCrearArchivosPlanos;
    private javax.swing.JButton jbtnImprimir;
    private javax.swing.JComboBox<String> jcbxMotivo;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JTable jtblBajas;
    private javax.swing.JTable jtblComprobantes;
    private javax.swing.JTextField jtxtFecha;
    private javax.swing.JTextField jtxtId;
    // End of variables declaration//GEN-END:variables
}

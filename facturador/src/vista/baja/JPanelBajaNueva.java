package vista.baja;

import controlador.Metodos;
import controlador.Rutas;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import modelo.Baja;
import modelo.Factura;
import vista.JPanelComprobantes;

public class JPanelBajaNueva extends javax.swing.JPanel {
    
    ResultSet rs;
    String id;
    DefaultTableModel dtmComprobantes;
    DefaultTableModel dtmBajas;

    public JPanelBajaNueva() {
        initComponents();
        jtxtFecha.setText(Metodos.CargarFechaActual());
        CargarIdBaja();
    }
    
    void CargarIdBaja(){
        String fechaActual = Metodos.FechaActualFormatoSUNATSinGuiones();
        String nombreBaja = "RA-" + fechaActual + "-";
        try {
            rs = Factura.Consulta("select *\n"
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
            Metodos.MensajeError("Error generando id de baja: \n" + e);
        }
    }
    
    private void CargarFacturas(String fecha) {
        try {
            dtmComprobantes = (DefaultTableModel) jtblComprobantes.getModel();
            dtmComprobantes.setRowCount(0);
            rs = Factura.Consulta("select *\n"
                    + "from factura\n"
                    + "inner join cliente\n"
                    + "on cliente.id = factura.idCliente\n"
                    + "where factura.fecha = '" + fecha + "' "
                    + "order by factura.id desc;");
            String fila[] = new String[3];
            while (rs.next()) {
                fila[0] = "Factura";
                fila[1] = rs.getString("factura.id");
                fila[2] = rs.getString("cliente.nombreRazonSocial");
                dtmComprobantes.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando facturas: \n" + e);
            Metodos.MensajeError("Error cargando facturas: \n" + e);
        }
    }
    
    void LimpiarTablas(){
        Metodos.LimpiarTabla(jtblComprobantes);
        Metodos.LimpiarTabla(jtblBajas);
    }
    
    void Agregar(){
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
    
    void CrearArchivoPlano() {
        //se crea archivo con el nombre establecido
        File ap = new File(Rutas.getRutaAPBaja(id));
        //para almacenar datos
        BufferedWriter bufferedWriter;
        if (ap.exists()) {
            Metodos.MensajeError("Error generando archivo\n"
                    + "plano '" + id + ".CBA':\n"
                    + "El archivo ya existe");
        } else {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(ap));
                //recorremos la tabla detealle para capturar los datos
                for (int i = 0; i < jtblBajas.getRowCount(); i++) {
                    //guardamos los campos en variables
                    String fechaEmision = Metodos.FechaFormatoSUNAT(Metodos.CapturarDateChooser(jdcFecha));
                    String fechaActual = Metodos.FechaActualFormatoSUNAT();
                    String tipo_comprobante = jtblBajas.getValueAt(i, 0).toString();
                    String tipo_comp;
                    if (tipo_comprobante.equals("Factura")) {
                        tipo_comp = "01";
                    } else {
                        tipo_comp = "error";
                    }
                    String numero_documento = jtblBajas.getValueAt(i, 1).toString();
                    String motivo = jtblBajas.getValueAt(i, 2).toString();
                    bufferedWriter.write(
                            fechaEmision + "|"
                            + fechaActual + "|"
                            + tipo_comp + "|"
                            + numero_documento + "|"
                            + motivo + "\n");
                }
                bufferedWriter.close();
                Metodos.MensajeInformacion("Archivos planos generados.");
            } catch (Exception e) {
                Metodos.MensajeError("Error creando archivo plano " + id + ".CBA:\n" + e);
                System.out.println(e);
            }
        }
    }
    
    void RegistrarBaja() {
        //capturamos los datos a enviar desde el frame
        String fecha = jtxtFecha.getText();
        try {
            Baja.RegistrarBaja(id, fecha);
        } catch (Exception e) {
            System.out.println("Error registrando baja a la base de datos: \n" + e);
            Metodos.MensajeError("Error registrando baja a la base de datos: \n" + e);
        }
    }
    
    void RegistrarBajaDet() {
        //capturamos los datos a enviar desde el frame
        String fecha = jtxtFecha.getText();
        try {
            int numero = 1;//contador para IdFacturaDet
            for (int i = 0; i < jtblBajas.getRowCount(); i++) {
                String idBaja = id + "-" + String.format("%03d", (numero++));
                String tipoComprobante = jtblBajas.getValueAt(i, 0).toString();
                String idComprobante = jtblBajas.getValueAt(i, 1).toString();
                String motivo = jtblBajas.getValueAt(i, 2).toString();
                Baja.RegistrarBajaDet(idBaja, id, tipoComprobante, idComprobante, motivo);
            }
        } catch (Exception e) {
            System.out.println("Error registrando detalle de baja\n a la base de datos: \n" + e);
            Metodos.MensajeError("Error registrando detalle de baja\n a la base de datos: \n" + e);
        }
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

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
            jtblComprobantes.getColumnModel().getColumn(2).setPreferredWidth(250);
        }

        jdcFecha.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jcbxMotivo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxMotivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---SELECCIONE---", "ERROR EN DESCRIPCION", "ERROR DE MONEDA", "ERROR DE FECHA", "ERROR EN RUC O RAZÓN SOCIAL", "ERROR EN DIRECCION", "ERROR DE MAL CALCULO DE IGV" }));

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jcbxMotivo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbxMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
            jtblBajas.getColumnModel().getColumn(2).setPreferredWidth(250);
        }

        jbtnCrearArchivosPlanos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnCrearArchivosPlanos.setText("Crear Archivos Planos");
        jbtnCrearArchivosPlanos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCrearArchivosPlanosActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Requisitos: La factura fue emitida");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("máximo en 7 dias pasados");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnCrearArchivosPlanos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel6)
                        .addGap(31, 31, 31)
                        .addComponent(jbtnCrearArchivosPlanos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        LimpiarTablas();
        try {
            String fecha = Metodos.CapturarDateChooser(jdcFecha);
            CargarFacturas(fecha);
        } catch (Exception e) {
            System.out.println("Error buscando fecha: \n" + e);
            Metodos.MensajeError("Error buscando fecha: \n" + e);
        }
    }//GEN-LAST:event_jbtnBuscarActionPerformed

    private void jbtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAgregarActionPerformed
        if (jcbxMotivo.getSelectedIndex() == 0) {
            Metodos.MensajeAlerta("Seleccione el motivo de baja del comprobante.");
        } else {
            if (jtblComprobantes.getSelectedRow() >= 0) {
                Agregar();
            } else {//si no existe ninguna fila seleccionada
                Metodos.MensajeAlerta("Debe seleccionar una comprobante.");
            }
        }
    }//GEN-LAST:event_jbtnAgregarActionPerformed

    private void jbtnCrearArchivosPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCrearArchivosPlanosActionPerformed
        try {
            CrearArchivoPlano();
            RegistrarBaja();
            RegistrarBajaDet();
            JPanelComprobantes jpc = new JPanelComprobantes();
            Metodos.CambiarPanel(jpc);
        } catch (Exception e) {
            Metodos.MensajeError("Error: "+ e);
        }

    }//GEN-LAST:event_jbtnCrearArchivosPlanosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnAgregar;
    private javax.swing.JButton jbtnBuscar;
    private javax.swing.JButton jbtnCrearArchivosPlanos;
    private javax.swing.JComboBox<String> jcbxMotivo;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JTable jtblBajas;
    private javax.swing.JTable jtblComprobantes;
    private javax.swing.JTextField jtxtFecha;
    private javax.swing.JTextField jtxtId;
    // End of variables declaration//GEN-END:variables
}

package vista.notacredito;

import controlador.Metodos;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;

public class JDialogComprobanteBuscar extends javax.swing.JDialog {
    
    DefaultTableModel dtmFacturas;
    DefaultTableModel dtmBoletas;
    static ResultSet rs;
    static String id;

    public JDialogComprobanteBuscar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ConfigurarVentana();
        CargarComprobantes();
    }
    
    void ConfigurarVentana() {
        //posiciono el frame al centro de la pantalla
        this.setLocationRelativeTo(null);
        //desactiva el cambio de tamaño de la ventana
        this.setResizable(false);
        //asigno titulo a mostrar del frame
        this.setTitle("Buscar comprobante");
    }
    
    void CargarComprobantes(){
        CargarFacturas();
        CargarBoletas();
    }
    
    private void CargarFacturas() {
        try {
            dtmFacturas = (DefaultTableModel) jtblFacturas.getModel();
            dtmFacturas.setRowCount(0);
            rs = Cliente.Consulta("select * \n"
                    + "from factura \n"
                    + "inner join cliente \n"
                    + "on cliente.id = factura.idCliente \n"
                    + "order by factura.id desc;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("factura.id");
                fila[1] = rs.getString("cliente.nombreRazonSocial");
                fila[2] = rs.getString("factura.fecha");
                fila[3] = rs.getString("factura.moneda");
                fila[4] = rs.getString("factura.importeTotal");
                dtmFacturas.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando facturas: \n" + e);
            Metodos.MensajeError("Error cargando facturas: \n" + e);
        }
    }
    
    private void CargarBoletas() {
        try {
            dtmBoletas = (DefaultTableModel) jtblBoletas.getModel();
            dtmBoletas.setRowCount(0);
            rs = Cliente.Consulta("select * \n"
                    + "from boleta \n"
                    + "inner join cliente \n"
                    + "on cliente.id = boleta.idCliente \n"
                    + "order by boleta.id desc;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("boleta.id");
                fila[1] = rs.getString("cliente.nombreRazonSocial");
                fila[2] = rs.getString("boleta.fecha");
                fila[3] = rs.getString("boleta.moneda");
                fila[4] = rs.getString("boleta.importeTotal");
                dtmBoletas.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando boletas: \n" + e);
            Metodos.MensajeError("Error cargando boletas: \n" + e);
        }
    }
    
    public void SeleccionarFactura() {
        int fila = jtblFacturas.getSelectedRow();//captura la fila seleccionada
        dtmFacturas = (DefaultTableModel) jtblFacturas.getModel();
        //capturo número documento para buscarlo más adelante
        id = jtblFacturas.getValueAt(fila, 0).toString();
        //actualizamos el panel Cliente con para que salgan los datos del cliente
        JPanelNotaCreditoNueva jpncn = new JPanelNotaCreditoNueva();
        Metodos.CambiarPanel(jpncn);
        dispose();
        //activamos objetos cabecera factura
        jpncn.jcbxMotivo.setEnabled(true);
        jpncn.jbtnBuscar.setEnabled(false);
        // activamos objetos de detalle factura
        jpncn.jtxtCantidad.setEnabled(true);
        jpncn.jcbxTipoUnidad.setEnabled(true);
        jpncn.jtxtPrecioUnitario.setEnabled(true);
        jpncn.jtxtDescripcion.setEnabled(true);
        jpncn.jbtnAgregar.setEnabled(true);
        //limpio cliente
        id = null;
    }
    
    public void SeleccionarBoleta() {
        int fila = jtblBoletas.getSelectedRow();//captura la fila seleccionada
        dtmBoletas = (DefaultTableModel) jtblBoletas.getModel();
        //capturo número documento para buscarlo más adelante
        id = jtblBoletas.getValueAt(fila, 0).toString();
        //actualizamos el panel Cliente con para que salgan los datos del cliente
        JPanelNotaCreditoNueva jpncn = new JPanelNotaCreditoNueva();
        Metodos.CambiarPanel(jpncn);
        dispose();
        //activamos objetos cabecera factura
        jpncn.jcbxMotivo.setEnabled(true);
        jpncn.jbtnBuscar.setEnabled(false);
        // activamos objetos de detalle factura
        jpncn.jtxtCantidad.setEnabled(true);
        jpncn.jcbxTipoUnidad.setEnabled(true);
        jpncn.jtxtPrecioUnitario.setEnabled(true);
        jpncn.jtxtDescripcion.setEnabled(true);
        jpncn.jbtnAgregar.setEnabled(true);
        //limpio cliente
        id = null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblFacturas = new javax.swing.JTable();
        jbtnFacturaCancelar = new javax.swing.JButton();
        jbtnFacturaSeleccionar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblBoletas = new javax.swing.JTable();
        jbtnBoletaCancelar = new javax.swing.JButton();
        jbtnBoletaSeleccionar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(141, 170, 235));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Buscar comprobante:");

        jtblFacturas.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Nombre/Razón social", "Fecha", "Moneda", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblFacturas.getTableHeader().setReorderingAllowed(false);
        jtblFacturas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblFacturasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtblFacturas);
        if (jtblFacturas.getColumnModel().getColumnCount() > 0) {
            jtblFacturas.getColumnModel().getColumn(0).setPreferredWidth(100);
            jtblFacturas.getColumnModel().getColumn(1).setPreferredWidth(200);
            jtblFacturas.getColumnModel().getColumn(2).setPreferredWidth(50);
            jtblFacturas.getColumnModel().getColumn(3).setPreferredWidth(50);
            jtblFacturas.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jbtnFacturaCancelar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnFacturaCancelar.setText("Cancelar");
        jbtnFacturaCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFacturaCancelarActionPerformed(evt);
            }
        });

        jbtnFacturaSeleccionar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnFacturaSeleccionar.setText("Seleccionar");
        jbtnFacturaSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFacturaSeleccionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnFacturaSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnFacturaCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnFacturaCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnFacturaSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Facturas", jPanel2);

        jtblBoletas.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblBoletas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Boleta", "Nombre/Razón social", "Fecha", "Moneda", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblBoletas.getTableHeader().setReorderingAllowed(false);
        jtblBoletas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblBoletasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtblBoletas);
        if (jtblBoletas.getColumnModel().getColumnCount() > 0) {
            jtblBoletas.getColumnModel().getColumn(0).setPreferredWidth(100);
            jtblBoletas.getColumnModel().getColumn(1).setPreferredWidth(200);
            jtblBoletas.getColumnModel().getColumn(2).setPreferredWidth(50);
            jtblBoletas.getColumnModel().getColumn(3).setPreferredWidth(50);
            jtblBoletas.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jbtnBoletaCancelar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnBoletaCancelar.setText("Cancelar");
        jbtnBoletaCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBoletaCancelarActionPerformed(evt);
            }
        });

        jbtnBoletaSeleccionar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnBoletaSeleccionar.setText("Seleccionar");
        jbtnBoletaSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBoletaSeleccionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnBoletaSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBoletaCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnBoletaCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnBoletaSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Boletas", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnBoletaSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBoletaSeleccionarActionPerformed
        SeleccionarBoleta();
    }//GEN-LAST:event_jbtnBoletaSeleccionarActionPerformed

    private void jbtnBoletaCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBoletaCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_jbtnBoletaCancelarActionPerformed

    private void jtblBoletasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblBoletasMouseClicked
        //si la fila en la tabla ha sido presionado 2 veces
        if (evt.getClickCount() == 2) {
            SeleccionarBoleta();
        }
    }//GEN-LAST:event_jtblBoletasMouseClicked

    private void jbtnFacturaSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFacturaSeleccionarActionPerformed
        SeleccionarFactura();
    }//GEN-LAST:event_jbtnFacturaSeleccionarActionPerformed

    private void jbtnFacturaCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFacturaCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_jbtnFacturaCancelarActionPerformed

    private void jtblFacturasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblFacturasMouseClicked
        //si la fila en la tabla ha sido presionado 2 veces
        if (evt.getClickCount() == 2) {
            SeleccionarFactura();
        }
    }//GEN-LAST:event_jtblFacturasMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialogComprobanteBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogComprobanteBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogComprobanteBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogComprobanteBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogComprobanteBuscar dialog = new JDialogComprobanteBuscar(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnBoletaCancelar;
    private javax.swing.JButton jbtnBoletaSeleccionar;
    private javax.swing.JButton jbtnFacturaCancelar;
    private javax.swing.JButton jbtnFacturaSeleccionar;
    private javax.swing.JTable jtblBoletas;
    private javax.swing.JTable jtblFacturas;
    // End of variables declaration//GEN-END:variables
}

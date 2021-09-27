package vista.factura;

import controlador.Metodos;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;

public class JDialogProductoServicioBuscar extends javax.swing.JDialog {
    
    DefaultTableModel dtmProductos, dtmServicios;
    static ResultSet rs;
    String id;
    String tipo;

    public JDialogProductoServicioBuscar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        configurarVentana();
        cargarProductos();
        cargarServicios();
    }
    
    private void configurarVentana() {
        //posiciono el frame al centro de la pantalla
        this.setLocationRelativeTo(null);
        //desactiva el cambio de tamaño de la ventana
        this.setResizable(false);
        //asigno titulo a mostrar del frame
        this.setTitle("Buscar producto");
    }
    
    private void cargarProductos() {
        try {
            dtmProductos = (DefaultTableModel) jtblProductos.getModel();
            dtmProductos.setRowCount(0);
            rs = Producto.Consulta("select *\n"
                    + "from producto;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("codigo");
                fila[2] = rs.getString("descripcion");
                fila[3] = rs.getString("precio1");
                fila[4] = rs.getString("precio2");
                dtmProductos.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando productos: \n" + e);
            Metodos.MensajeError("Error cargando productos: \n" + e);
        }
    }
    
    private void cargarServicios() {
        try {
            dtmServicios = (DefaultTableModel) jtblServicios.getModel();
            dtmServicios.setRowCount(0);
            rs = Producto.Consulta("select * \n"
                    + "from servicio;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("codigo");
                fila[2] = rs.getString("descripcion");
                fila[3] = rs.getString("precio1");
                fila[4] = rs.getString("precio2");
                dtmServicios.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando servicios: \n" + e);
            Metodos.MensajeError("Error cargando servicios: \n" + e);
        }
    }
    
    public void seleccionarProducto() {
        int fila = jtblProductos.getSelectedRow();
        dtmProductos = (DefaultTableModel) jtblProductos.getModel();
        //capturo número documento para buscarlo más adelante
        id = jtblProductos.getValueAt(fila, 0).toString();
        tipo = "producto";
        dispose();
    }
    
    public void seleccionarServicio() {
        int fila = jtblServicios.getSelectedRow();//captura la fila seleccionada
        dtmServicios = (DefaultTableModel) jtblServicios.getModel();
        //capturo número documento para buscarlo más adelante
        id = jtblServicios.getValueAt(fila, 0).toString();
        tipo = "servicio";
        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtBuscarProducto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblProductos = new javax.swing.JTable();
        jbtnSeleccionarProducto = new javax.swing.JButton();
        btnCancelarProducto = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtxtBuscarServicio = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblServicios = new javax.swing.JTable();
        jbtnSeleccionarServicio = new javax.swing.JButton();
        btnCancelarServicio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(141, 170, 235));

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Buscar producto:");

        jtxtBuscarProducto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtBuscarProductoKeyTyped(evt);
            }
        });

        jtblProductos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Código", "Descripción", "Precio 1", "Precio 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblProductos.getTableHeader().setReorderingAllowed(false);
        jtblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtblProductos);
        if (jtblProductos.getColumnModel().getColumnCount() > 0) {
            jtblProductos.getColumnModel().getColumn(0).setMinWidth(0);
            jtblProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtblProductos.getColumnModel().getColumn(0).setMaxWidth(0);
            jtblProductos.getColumnModel().getColumn(1).setPreferredWidth(80);
            jtblProductos.getColumnModel().getColumn(2).setPreferredWidth(200);
            jtblProductos.getColumnModel().getColumn(3).setPreferredWidth(20);
            jtblProductos.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        jbtnSeleccionarProducto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnSeleccionarProducto.setText("Seleccionar");
        jbtnSeleccionarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeleccionarProductoActionPerformed(evt);
            }
        });

        btnCancelarProducto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnCancelarProducto.setText("Cancelar");
        btnCancelarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnSeleccionarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnSeleccionarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Productos", jPanel2);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Buscar servicio:");

        jtxtBuscarServicio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtBuscarServicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtBuscarServicioKeyTyped(evt);
            }
        });

        jtblServicios.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblServicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Código", "Descripción", "Precio 1", "Precio 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblServicios.getTableHeader().setReorderingAllowed(false);
        jtblServicios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblServiciosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtblServicios);
        if (jtblServicios.getColumnModel().getColumnCount() > 0) {
            jtblServicios.getColumnModel().getColumn(0).setMinWidth(0);
            jtblServicios.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtblServicios.getColumnModel().getColumn(0).setMaxWidth(0);
            jtblServicios.getColumnModel().getColumn(1).setPreferredWidth(80);
            jtblServicios.getColumnModel().getColumn(2).setPreferredWidth(200);
            jtblServicios.getColumnModel().getColumn(3).setPreferredWidth(20);
            jtblServicios.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        jbtnSeleccionarServicio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnSeleccionarServicio.setText("Seleccionar");
        jbtnSeleccionarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeleccionarServicioActionPerformed(evt);
            }
        });

        btnCancelarServicio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnCancelarServicio.setText("Cancelar");
        btnCancelarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarServicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxtBuscarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnSeleccionarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtBuscarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnSeleccionarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Servicios", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnCancelarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarServicioActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarServicioActionPerformed

    private void jbtnSeleccionarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeleccionarServicioActionPerformed
        seleccionarServicio();
    }//GEN-LAST:event_jbtnSeleccionarServicioActionPerformed

    private void jtblServiciosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblServiciosMouseClicked
        //si la fila en la tabla ha sido presionado 2 veces
        if (evt.getClickCount() == 2) {
            seleccionarServicio();
        }
    }//GEN-LAST:event_jtblServiciosMouseClicked

    private void jtxtBuscarServicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBuscarServicioKeyTyped
        // mandamos 2 columnaS, codigo y descripcion
        Metodos.filtrarServicio(jtxtBuscarServicio, 1, 2, dtmServicios, jtblServicios);
    }//GEN-LAST:event_jtxtBuscarServicioKeyTyped

    private void btnCancelarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarProductoActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarProductoActionPerformed

    private void jbtnSeleccionarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeleccionarProductoActionPerformed
        seleccionarProducto();
    }//GEN-LAST:event_jbtnSeleccionarProductoActionPerformed

    private void jtblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblProductosMouseClicked
        //si la fila en la tabla ha sido presionado 2 veces
        if (evt.getClickCount() == 2) {
            seleccionarProducto();
        }
    }//GEN-LAST:event_jtblProductosMouseClicked

    private void jtxtBuscarProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBuscarProductoKeyTyped
        // mandamos 2 columnaS, codigo y descripcion
        Metodos.FiltrarProducto(jtxtBuscarProducto, 1, 2, dtmProductos, jtblProductos);
    }//GEN-LAST:event_jtxtBuscarProductoKeyTyped

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
            java.util.logging.Logger.getLogger(JDialogProductoServicioBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogProductoServicioBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogProductoServicioBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogProductoServicioBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                JDialogProductoServicioBuscar dialog = new JDialogProductoServicioBuscar(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelarProducto;
    private javax.swing.JButton btnCancelarServicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnSeleccionarProducto;
    private javax.swing.JButton jbtnSeleccionarServicio;
    private javax.swing.JTable jtblProductos;
    private javax.swing.JTable jtblServicios;
    private javax.swing.JTextField jtxtBuscarProducto;
    private javax.swing.JTextField jtxtBuscarServicio;
    // End of variables declaration//GEN-END:variables
}

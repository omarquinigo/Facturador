package vista.factura;

import controlador.Metodos;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;

public class JDialogClienteBuscar extends javax.swing.JDialog {
    
    DefaultTableModel dtmClientes;
    static ResultSet rs;
    static String id;

    public JDialogClienteBuscar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        configurarVentana();
        cargarClientes();
    }
    
    private void configurarVentana() {
        //posiciono el frame al centro de la pantalla
        this.setLocationRelativeTo(null);
        //desactiva el cambio de tamaño de la ventana
        this.setResizable(false);
        //asigno titulo a mostrar del frame
        this.setTitle("Buscar cliente");
    }
    
    private void cargarClientes() {
        try {
            dtmClientes = (DefaultTableModel) jtblClientes.getModel();
            dtmClientes.setRowCount(0);
            rs = Cliente.Consulta("select *\n"
                    + "from cliente \n"
                    + "where tipoDocumento = 'RUC';");
            String fila[] = new String[4];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("tipoDocumento");
                fila[2] = rs.getString("numeroDocumento");
                fila[3] = rs.getString("NombreRazonSocial");
                dtmClientes.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando clientes: \n" + e);
            Metodos.MensajeError("Error cargando clientes: \n" + e);
        }
    }
    
    public void seleccionarCliente() {
        int fila = jtblClientes.getSelectedRow();//captura la fila seleccionada
        dtmClientes = (DefaultTableModel) jtblClientes.getModel();
        //capturo número documento para buscarlo más adelante
        id = jtblClientes.getValueAt(fila, 0).toString();
        //actualizamos el panel Cliente con para que salgan los datos del cliente
        //JPanelFacturaNueva jpfn = new JPanelFacturaNueva();
        //Metodos.CambiarPanel(jpfn);
        dispose();
        //activamos objetos cabecera factura
        //jpfn.jbtnBuscar.setEnabled(false);
        // activamos objetos de detalle factura
        //jpfn.jtxtCantidad.setEnabled(true);
        //jpfn.jcbxTipoUnidad.setEnabled(true);
        //jpfn.jtxtPrecioUnitario.setEnabled(true);
        //jpfn.jtxtDescripcion.setEnabled(true);
        //jpfn.jbtnAgregar.setEnabled(true);
        //limpio cliente
        //id = null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblClientes = new javax.swing.JTable();
        jbtnSeleccionar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtxtBuscar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(141, 170, 235));

        jtblClientes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tipo Doc.", "N° Doc.", "Nombre / Razón social"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblClientes.getTableHeader().setReorderingAllowed(false);
        jtblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtblClientes);
        if (jtblClientes.getColumnModel().getColumnCount() > 0) {
            jtblClientes.getColumnModel().getColumn(0).setMinWidth(0);
            jtblClientes.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtblClientes.getColumnModel().getColumn(0).setMaxWidth(0);
            jtblClientes.getColumnModel().getColumn(1).setPreferredWidth(100);
            jtblClientes.getColumnModel().getColumn(2).setPreferredWidth(150);
            jtblClientes.getColumnModel().getColumn(3).setPreferredWidth(600);
        }

        jbtnSeleccionar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnSeleccionar.setText("Seleccionar");
        jbtnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeleccionarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Buscar cliente:");

        jtxtBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtBuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jbtnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeleccionarActionPerformed
        seleccionarCliente();
    }//GEN-LAST:event_jbtnSeleccionarActionPerformed

    private void jtblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblClientesMouseClicked
        //si la fila en la tabla ha sido presionado 2 veces
        if (evt.getClickCount() == 2) {    
            seleccionarCliente();
        }
    }//GEN-LAST:event_jtblClientesMouseClicked

    private void jtxtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBuscarKeyTyped
        Metodos.filtrarCliente(jtxtBuscar, 2, 3, dtmClientes, jtblClientes);
    }//GEN-LAST:event_jtxtBuscarKeyTyped

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
            java.util.logging.Logger.getLogger(JDialogClienteBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogClienteBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogClienteBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogClienteBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogClienteBuscar dialog = new JDialogClienteBuscar(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnSeleccionar;
    private javax.swing.JTable jtblClientes;
    private javax.swing.JTextField jtxtBuscar;
    // End of variables declaration//GEN-END:variables
}

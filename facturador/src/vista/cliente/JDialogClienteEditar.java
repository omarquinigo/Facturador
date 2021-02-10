package vista.cliente;

import controlador.Metodos;
import java.sql.ResultSet;
import modelo.Cliente;

public class JDialogClienteEditar extends javax.swing.JDialog {
    
    static String id;
    static ResultSet rs;

    public JDialogClienteEditar(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        initComponents();
        ConfigurarVentana();
        CargarCliente(id);
    }
    
    void ConfigurarVentana() {
        //posiciono el frame al centro de la pantalla
        this.setLocationRelativeTo(null);
        //desactiva el cambio de tamaño de la ventana
        this.setResizable(false);
        //asigno titulo a mostrar del frame
        this.setTitle("Editar cliente");
    }
    
    void CargarCliente(String idCliente) {
        id = idCliente;
        try {
            rs = Cliente.Consulta("select * \n"
                    + "from cliente \n"
                    + "where id = '" + id + "';");
            while (rs.next()) {
                String tipoDocumento = rs.getString("tipoDocumento");
                String numeroDocumento = rs.getString("numeroDocumento");
                String nombreRazonSocial = rs.getString("nombreRazonSocial");
                String direccion = rs.getString("Direccion");
                jcbxTipoDocumento.setSelectedItem(tipoDocumento);
                jtxtNumeroDocumento.setText(numeroDocumento);
                jtxtNombreRazonSocial.setText(nombreRazonSocial);
                jtxtDireccion.setText(direccion);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando cliente: \n" + e);
            Metodos.MensajeError("Error cargando cliente: \n" + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbtnCancelar = new javax.swing.JButton();
        jbtnActualizar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcbxTipoDocumento = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jtxtNumeroDocumento = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtNombreRazonSocial = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtDireccion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(141, 170, 235));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbtnCancelar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 100, 40));

        jbtnActualizar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnActualizar.setText("Actualizar");
        jbtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(384, 140, 100, 40));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Editar cliente");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Tipo Doc.:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, -1, -1));

        jcbxTipoDocumento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcbxTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---SELECCIONE---", "DNI", "RUC", "CARNE EXT", "PASS" }));
        jPanel1.add(jcbxTipoDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 32, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Número Doc.:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 62, -1, -1));

        jtxtNumeroDocumento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel1.add(jtxtNumeroDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 59, 128, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Nombre / Razón Social:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 89, -1, -1));

        jtxtNombreRazonSocial.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel1.add(jtxtNombreRazonSocial, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 86, 408, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Dirección:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 113, -1, -1));

        jtxtDireccion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtDireccion.setMaximumSize(null);
        jtxtDireccion.setMinimumSize(null);
        jPanel1.add(jtxtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 113, 408, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnActualizarActionPerformed
        String tipoDocumento = jcbxTipoDocumento.getSelectedItem().toString();
        String numeroDocumento = jtxtNumeroDocumento.getText();
        String nombreRazonSocial = jtxtNombreRazonSocial.getText();
        String direccion = jtxtDireccion.getText();

        if (tipoDocumento.equalsIgnoreCase("---SELECCIONE---")
                || numeroDocumento.equals("")
                || nombreRazonSocial.equals("")
                || direccion.equals("")) {
            Metodos.MensajeAlerta("Verifique datos.");
        } else {
            try {
                Cliente.Actualizar(id, tipoDocumento, numeroDocumento,
                        nombreRazonSocial, direccion);
                dispose();
            } catch (Exception e) {
                
            }
        }
    }//GEN-LAST:event_jbtnActualizarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_jbtnCancelarActionPerformed


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
            java.util.logging.Logger.getLogger(JDialogClienteEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogClienteEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogClienteEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogClienteEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogClienteEditar dialog = new JDialogClienteEditar(new javax.swing.JFrame(), true, id);
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtnActualizar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JComboBox<String> jcbxTipoDocumento;
    private javax.swing.JTextField jtxtDireccion;
    private javax.swing.JTextField jtxtNombreRazonSocial;
    private javax.swing.JTextField jtxtNumeroDocumento;
    // End of variables declaration//GEN-END:variables
}

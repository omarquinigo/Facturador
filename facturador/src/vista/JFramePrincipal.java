package vista;

import controlador.Metodos;
import vista.cliente.JPanelClienteListar;

public class JFramePrincipal extends javax.swing.JFrame {

    public JFramePrincipal() {
        initComponents();
        Metodos.ConfigurarVentana(this, "Facturador by Omashi");
        JPanelInicio jpi = new JPanelInicio();
        Metodos.CambiarPanel(jpi);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnlPrincipal = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menArchivo = new javax.swing.JMenu();
        jmiInicio = new javax.swing.JMenuItem();
        jmiConfigurar = new javax.swing.JMenuItem();
        jmiSalir = new javax.swing.JMenuItem();
        jmenAdministrar = new javax.swing.JMenu();
        jmiComprobantes = new javax.swing.JMenuItem();
        jmiClientes = new javax.swing.JMenuItem();
        menAcercaDe = new javax.swing.JMenu();
        jmiVersion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jpnlPrincipalLayout = new javax.swing.GroupLayout(jpnlPrincipal);
        jpnlPrincipal.setLayout(jpnlPrincipalLayout);
        jpnlPrincipalLayout.setHorizontalGroup(
            jpnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        jpnlPrincipalLayout.setVerticalGroup(
            jpnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 479, Short.MAX_VALUE)
        );

        menArchivo.setText("File");

        jmiInicio.setText("Inicio");
        jmiInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInicioActionPerformed(evt);
            }
        });
        menArchivo.add(jmiInicio);

        jmiConfigurar.setText("Configurar");
        jmiConfigurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiConfigurarActionPerformed(evt);
            }
        });
        menArchivo.add(jmiConfigurar);

        jmiSalir.setText("Salir");
        jmiSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSalirActionPerformed(evt);
            }
        });
        menArchivo.add(jmiSalir);

        jMenuBar1.add(menArchivo);

        jmenAdministrar.setText("Administrar");

        jmiComprobantes.setText("Comprobantes");
        jmiComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiComprobantesActionPerformed(evt);
            }
        });
        jmenAdministrar.add(jmiComprobantes);

        jmiClientes.setText("Clientes");
        jmiClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiClientesActionPerformed(evt);
            }
        });
        jmenAdministrar.add(jmiClientes);

        jMenuBar1.add(jmenAdministrar);

        menAcercaDe.setText("Acerca de");

        jmiVersion.setText("Versión");
        jmiVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiVersionActionPerformed(evt);
            }
        });
        menAcercaDe.add(jmiVersion);

        jMenuBar1.add(menAcercaDe);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInicioActionPerformed
        JPanelInicio jpi = new JPanelInicio();
        Metodos.CambiarPanel(jpi);
    }//GEN-LAST:event_jmiInicioActionPerformed

    private void jmiClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiClientesActionPerformed
        JPanelClienteListar jpcl = new JPanelClienteListar();
        Metodos.CambiarPanel(jpcl);
    }//GEN-LAST:event_jmiClientesActionPerformed

    private void jmiComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiComprobantesActionPerformed
        JPanelComprobantes jpc = new JPanelComprobantes();
        Metodos.CambiarPanel(jpc);
    }//GEN-LAST:event_jmiComprobantesActionPerformed

    private void jmiVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiVersionActionPerformed
        JDialogVersion v = new JDialogVersion(this, true);
        v.setVisible(true);
    }//GEN-LAST:event_jmiVersionActionPerformed

    private void jmiSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jmiSalirActionPerformed

    private void jmiConfigurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiConfigurarActionPerformed
        JPanelConfigurar jpc = new JPanelConfigurar();
        Metodos.CambiarPanel(jpc);
    }//GEN-LAST:event_jmiConfigurarActionPerformed

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
            java.util.logging.Logger.getLogger(JFramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFramePrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jmenAdministrar;
    private javax.swing.JMenuItem jmiClientes;
    private javax.swing.JMenuItem jmiComprobantes;
    private javax.swing.JMenuItem jmiConfigurar;
    private javax.swing.JMenuItem jmiInicio;
    private javax.swing.JMenuItem jmiSalir;
    private javax.swing.JMenuItem jmiVersion;
    public static javax.swing.JPanel jpnlPrincipal;
    private javax.swing.JMenu menAcercaDe;
    private javax.swing.JMenu menArchivo;
    // End of variables declaration//GEN-END:variables
}

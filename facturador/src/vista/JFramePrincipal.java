package vista;

import controlador.Metodos;
import controlador.Rutas;
import vista.cliente.JPanelClienteListar;
import vista.producto.JPanelProductoListar;
import vista.servicio.JPanelServicioListar;
import vista.usuario.JPanelUsuarioListar;

public class JFramePrincipal extends javax.swing.JFrame {
    
    public static String rol;

    public JFramePrincipal(String user_rol) {
        initComponents();
        rol = user_rol;
        Metodos.ConfigurarVentana(this, "Facturador Omashi 1.0.3 | SFS SUNAT 1.3.4.4");
        JPanelInicio jpi = new JPanelInicio(rol);
        Metodos.CambiarPanel(jpi);
        cargarRol();
    }
    
    private void cargarRol(){
        if(rol.equalsIgnoreCase("Usuario")){
            jmiUsuarios.setEnabled(false);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnlPrincipal = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menArchivo = new javax.swing.JMenu();
        jmiInicio = new javax.swing.JMenuItem();
        jmiCerrarSesion = new javax.swing.JMenuItem();
        jmiEjecutarSFS = new javax.swing.JMenuItem();
        jmiAbrirBandeja = new javax.swing.JMenuItem();
        jmiConfigurar = new javax.swing.JMenuItem();
        jmiSalir = new javax.swing.JMenuItem();
        jmenAdministrar = new javax.swing.JMenu();
        jmiComprobantes = new javax.swing.JMenuItem();
        jmiClientes = new javax.swing.JMenuItem();
        jmiProductos = new javax.swing.JMenuItem();
        jmiServicios = new javax.swing.JMenuItem();
        jmiUsuarios = new javax.swing.JMenuItem();
        menAcercaDe = new javax.swing.JMenu();
        jmiInfo = new javax.swing.JMenuItem();
        jmiVersion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jpnlPrincipalLayout = new javax.swing.GroupLayout(jpnlPrincipal);
        jpnlPrincipal.setLayout(jpnlPrincipalLayout);
        jpnlPrincipalLayout.setHorizontalGroup(
            jpnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1024, Short.MAX_VALUE)
        );
        jpnlPrincipalLayout.setVerticalGroup(
            jpnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
        );

        jMenuBar1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        menArchivo.setText("Archivo");

        jmiInicio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/inicio.png"))); // NOI18N
        jmiInicio.setText("Inicio");
        jmiInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInicioActionPerformed(evt);
            }
        });
        menArchivo.add(jmiInicio);

        jmiCerrarSesion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/cerrar_sesion.png"))); // NOI18N
        jmiCerrarSesion.setText("Cerrar sesión");
        jmiCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCerrarSesionActionPerformed(evt);
            }
        });
        menArchivo.add(jmiCerrarSesion);

        jmiEjecutarSFS.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiEjecutarSFS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/ejecutarSFS.png"))); // NOI18N
        jmiEjecutarSFS.setText("Ejecutar SFS");
        jmiEjecutarSFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiEjecutarSFSActionPerformed(evt);
            }
        });
        menArchivo.add(jmiEjecutarSFS);

        jmiAbrirBandeja.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiAbrirBandeja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/abrirBandejaSFS.png"))); // NOI18N
        jmiAbrirBandeja.setText("Bandeja SFS");
        jmiAbrirBandeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAbrirBandejaActionPerformed(evt);
            }
        });
        menArchivo.add(jmiAbrirBandeja);

        jmiConfigurar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiConfigurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/configurar.png"))); // NOI18N
        jmiConfigurar.setText("Configurar");
        jmiConfigurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiConfigurarActionPerformed(evt);
            }
        });
        menArchivo.add(jmiConfigurar);

        jmiSalir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/salir.png"))); // NOI18N
        jmiSalir.setText("Salir");
        jmiSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSalirActionPerformed(evt);
            }
        });
        menArchivo.add(jmiSalir);

        jMenuBar1.add(menArchivo);

        jmenAdministrar.setText("Administrar");

        jmiComprobantes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiComprobantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/comprobantes.png"))); // NOI18N
        jmiComprobantes.setText("Comprobantes");
        jmiComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiComprobantesActionPerformed(evt);
            }
        });
        jmenAdministrar.add(jmiComprobantes);

        jmiClientes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/clientes.png"))); // NOI18N
        jmiClientes.setText("Clientes");
        jmiClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiClientesActionPerformed(evt);
            }
        });
        jmenAdministrar.add(jmiClientes);

        jmiProductos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/productos.png"))); // NOI18N
        jmiProductos.setText("Productos");
        jmiProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProductosActionPerformed(evt);
            }
        });
        jmenAdministrar.add(jmiProductos);

        jmiServicios.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiServicios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/servicios.png"))); // NOI18N
        jmiServicios.setText("Servicios");
        jmiServicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiServiciosActionPerformed(evt);
            }
        });
        jmenAdministrar.add(jmiServicios);

        jmiUsuarios.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/usuarios.png"))); // NOI18N
        jmiUsuarios.setText("Usuarios");
        jmiUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiUsuariosActionPerformed(evt);
            }
        });
        jmenAdministrar.add(jmiUsuarios);

        jMenuBar1.add(jmenAdministrar);

        menAcercaDe.setText("Acerca de");

        jmiInfo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jmiInfo.setText("Info");
        jmiInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiInfoActionPerformed(evt);
            }
        });
        menAcercaDe.add(jmiInfo);

        jmiVersion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
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
        JPanelInicio jpi = new JPanelInicio(rol);
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
        JDialogVersion jdv = new JDialogVersion(this, true);
        jdv.setVisible(true);
    }//GEN-LAST:event_jmiVersionActionPerformed

    private void jmiSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jmiSalirActionPerformed

    private void jmiConfigurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiConfigurarActionPerformed
        JPanelConfigurar jpc = new JPanelConfigurar();
        Metodos.CambiarPanel(jpc);
    }//GEN-LAST:event_jmiConfigurarActionPerformed

    private void jmiEjecutarSFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiEjecutarSFSActionPerformed
        try {
            String unidad = Rutas.getRutaSunat().substring(0, 2);
            String ruta = Rutas.getRutaSunat();
            Runtime.getRuntime().exec("cmd.exe /K start EjecutarSFS.bat " + unidad + " " + ruta);
        } catch (Exception ex) {
            Metodos.MensajeError(ex.toString());
        }
    }//GEN-LAST:event_jmiEjecutarSFSActionPerformed

    private void jmiAbrirBandejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAbrirBandejaActionPerformed
        try {
            Runtime.getRuntime().exec("cmd.exe /K AbrirBandeja.bat");
        } catch (Exception ex) {
            Metodos.MensajeError(ex.toString());
        }
    }//GEN-LAST:event_jmiAbrirBandejaActionPerformed

    private void jmiProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiProductosActionPerformed
        JPanelProductoListar jppl = new JPanelProductoListar(rol);
        Metodos.CambiarPanel(jppl);
    }//GEN-LAST:event_jmiProductosActionPerformed

    private void jmiUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiUsuariosActionPerformed
        JPanelUsuarioListar jpul = new JPanelUsuarioListar();
        Metodos.CambiarPanel(jpul);
    }//GEN-LAST:event_jmiUsuariosActionPerformed

    private void jmiServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiServiciosActionPerformed
        JPanelServicioListar jpsl = new JPanelServicioListar(rol);
        Metodos.CambiarPanel(jpsl);
    }//GEN-LAST:event_jmiServiciosActionPerformed

    private void jmiCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiCerrarSesionActionPerformed
        dispose();
        JFrameLogin jfl = new JFrameLogin();
        jfl.setVisible(true);
    }//GEN-LAST:event_jmiCerrarSesionActionPerformed

    private void jmiInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiInfoActionPerformed
        JDialogInfo jdi = new JDialogInfo(this, true);
        jdi.setVisible(true);
    }//GEN-LAST:event_jmiInfoActionPerformed

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
                new JFramePrincipal(rol).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jmenAdministrar;
    private javax.swing.JMenuItem jmiAbrirBandeja;
    private javax.swing.JMenuItem jmiCerrarSesion;
    private javax.swing.JMenuItem jmiClientes;
    private javax.swing.JMenuItem jmiComprobantes;
    private javax.swing.JMenuItem jmiConfigurar;
    private javax.swing.JMenuItem jmiEjecutarSFS;
    private javax.swing.JMenuItem jmiInfo;
    private javax.swing.JMenuItem jmiInicio;
    private javax.swing.JMenuItem jmiProductos;
    private javax.swing.JMenuItem jmiSalir;
    private javax.swing.JMenuItem jmiServicios;
    private javax.swing.JMenuItem jmiUsuarios;
    private javax.swing.JMenuItem jmiVersion;
    public static javax.swing.JPanel jpnlPrincipal;
    private javax.swing.JMenu menAcercaDe;
    private javax.swing.JMenu menArchivo;
    // End of variables declaration//GEN-END:variables
}

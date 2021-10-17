package vista;

import controlador.Metodos;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import vista.cliente.JPanelClienteListar;
import vista.producto.JPanelProductoListar;
import vista.servicio.JPanelServicioListar;

public class JPanelInicio extends javax.swing.JPanel {
    
    public static String rol;
    public static JPanel panel;

    public JPanelInicio(String user_rol) {
        initComponents();
        rol = user_rol;
    }

    @Override
    public void paint(Graphics g){
        Dimension d = getSize();
        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/fondo.jpg"));
        g.drawImage(imagen.getImage(), 0, 0, d.width, d.height, null);
        setOpaque(false);
        super.paint(g);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlblVersion = new javax.swing.JLabel();
        jlblVersionFacturadorSUNAT = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jbtnEjecutarSFS = new javax.swing.JButton();
        jbtnAbrirBandeja = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jbntComprobantes = new javax.swing.JButton();
        jbtnClientes = new javax.swing.JButton();
        jbtnProductos = new javax.swing.JButton();
        jbtnServicios = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 0, 0));

        jlblVersion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblVersion.setForeground(new java.awt.Color(255, 255, 255));
        jlblVersion.setText("Versión Facturador SUNAT 1.4");

        jlblVersionFacturadorSUNAT.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblVersionFacturadorSUNAT.setForeground(new java.awt.Color(255, 255, 255));
        jlblVersionFacturadorSUNAT.setText("Versión 1.0.4");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cpe.png"))); // NOI18N

        jbtnEjecutarSFS.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jbtnEjecutarSFS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/ejecutarSFS.png"))); // NOI18N
        jbtnEjecutarSFS.setText("Ejecutar SFS");
        jbtnEjecutarSFS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnEjecutarSFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEjecutarSFSActionPerformed(evt);
            }
        });

        jbtnAbrirBandeja.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jbtnAbrirBandeja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/abrirBandejaSFS.png"))); // NOI18N
        jbtnAbrirBandeja.setText("Abrir bandeja");
        jbtnAbrirBandeja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnAbrirBandeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAbrirBandejaActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/facturador.png"))); // NOI18N

        jbntComprobantes.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jbntComprobantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/comprobantes.png"))); // NOI18N
        jbntComprobantes.setText("Comprobantes");
        jbntComprobantes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbntComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbntComprobantesActionPerformed(evt);
            }
        });

        jbtnClientes.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jbtnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/clientes.png"))); // NOI18N
        jbtnClientes.setText("Clientes");
        jbtnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnClientesActionPerformed(evt);
            }
        });

        jbtnProductos.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jbtnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/productos.png"))); // NOI18N
        jbtnProductos.setText("Productos");
        jbtnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnProductosActionPerformed(evt);
            }
        });

        jbtnServicios.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jbtnServicios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/servicios.png"))); // NOI18N
        jbtnServicios.setText("Servicios");
        jbtnServicios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnServicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnServiciosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jbtnProductos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbntComprobantes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnServicios, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlblVersionFacturadorSUNAT)
                            .addComponent(jlblVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jbtnEjecutarSFS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbtnAbrirBandeja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbntComprobantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnEjecutarSFS, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jbtnClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnAbrirBandeja, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jbtnServicios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 253, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblVersionFacturadorSUNAT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlblVersion))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnEjecutarSFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEjecutarSFSActionPerformed
        Metodos.ejecutarSfsSunat();
    }//GEN-LAST:event_jbtnEjecutarSFSActionPerformed

    private void jbtnAbrirBandejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAbrirBandejaActionPerformed
        Metodos.abrirBandeja();
    }//GEN-LAST:event_jbtnAbrirBandejaActionPerformed

    private void jbntComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbntComprobantesActionPerformed
        JPanelComprobantes jpc = new JPanelComprobantes();
        Metodos.cambiarPanel(jpc);
    }//GEN-LAST:event_jbntComprobantesActionPerformed

    private void jbtnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnClientesActionPerformed
        JPanelClienteListar jpcl = new JPanelClienteListar();
        Metodos.cambiarPanel(jpcl);
    }//GEN-LAST:event_jbtnClientesActionPerformed

    private void jbtnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnProductosActionPerformed
        JPanelProductoListar jppl = new JPanelProductoListar(rol);
        Metodos.cambiarPanel(jppl);
    }//GEN-LAST:event_jbtnProductosActionPerformed

    private void jbtnServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnServiciosActionPerformed
        JPanelServicioListar jpsl = new JPanelServicioListar(rol);
        Metodos.cambiarPanel(jpsl);
    }//GEN-LAST:event_jbtnServiciosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jbntComprobantes;
    private javax.swing.JButton jbtnAbrirBandeja;
    private javax.swing.JButton jbtnClientes;
    private javax.swing.JButton jbtnEjecutarSFS;
    private javax.swing.JButton jbtnProductos;
    private javax.swing.JButton jbtnServicios;
    private javax.swing.JLabel jlblVersion;
    private javax.swing.JLabel jlblVersionFacturadorSUNAT;
    // End of variables declaration//GEN-END:variables
}

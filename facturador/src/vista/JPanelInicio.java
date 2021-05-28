package vista;

import controlador.Metodos;
import controlador.Rutas;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class JPanelInicio extends javax.swing.JPanel {

    public JPanelInicio() {
        initComponents();
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

        setBackground(new java.awt.Color(0, 0, 0));

        jlblVersion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblVersion.setForeground(new java.awt.Color(255, 255, 255));
        jlblVersion.setText("Versión Facturador SUNAT 1.3.4.4");

        jlblVersionFacturadorSUNAT.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblVersionFacturadorSUNAT.setForeground(new java.awt.Color(255, 255, 255));
        jlblVersionFacturadorSUNAT.setText("Versión 1.0.2");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cpe.png"))); // NOI18N

        jbtnEjecutarSFS.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jbtnEjecutarSFS.setText("Ejecutar SFS");
        jbtnEjecutarSFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEjecutarSFSActionPerformed(evt);
            }
        });

        jbtnAbrirBandeja.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jbtnAbrirBandeja.setText("Abrir bandeja");
        jbtnAbrirBandeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAbrirBandejaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(660, Short.MAX_VALUE)
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
                .addComponent(jbtnEjecutarSFS, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnAbrirBandeja, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblVersionFacturadorSUNAT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblVersion)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnEjecutarSFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEjecutarSFSActionPerformed
        try {
            String unidad = Rutas.getRutaSunat().substring(0, 2);
            String ruta = Rutas.getRutaSunat();
            Runtime.getRuntime().exec("cmd.exe /K start EjecutarSFS.bat " + unidad + " " + ruta);
        } catch (Exception ex) {
            Metodos.MensajeError(ex.toString());
        }
    }//GEN-LAST:event_jbtnEjecutarSFSActionPerformed

    private void jbtnAbrirBandejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAbrirBandejaActionPerformed
        try {
            Runtime.getRuntime().exec("cmd.exe /K AbrirBandeja.bat");
        } catch (Exception ex) {
            Metodos.MensajeError(ex.toString());
        }
    }//GEN-LAST:event_jbtnAbrirBandejaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbtnAbrirBandeja;
    private javax.swing.JButton jbtnEjecutarSFS;
    private javax.swing.JLabel jlblVersion;
    private javax.swing.JLabel jlblVersionFacturadorSUNAT;
    // End of variables declaration//GEN-END:variables
}

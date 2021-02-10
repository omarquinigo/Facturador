package vista;

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

        setBackground(new java.awt.Color(0, 0, 0));

        jlblVersion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblVersion.setForeground(new java.awt.Color(255, 255, 255));
        jlblVersion.setText("Versión Facturador SUNAT 1.3.4.2");

        jlblVersionFacturadorSUNAT.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblVersionFacturadorSUNAT.setForeground(new java.awt.Color(255, 255, 255));
        jlblVersionFacturadorSUNAT.setText("Versión 1.0.0");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cpe.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(699, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlblVersionFacturadorSUNAT)
                    .addComponent(jlblVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(321, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblVersionFacturadorSUNAT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblVersion)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jlblVersion;
    private javax.swing.JLabel jlblVersionFacturadorSUNAT;
    // End of variables declaration//GEN-END:variables
}

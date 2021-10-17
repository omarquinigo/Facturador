package vista;

import controlador.Metodos;

public class JDialogInfo extends javax.swing.JDialog {

    public JDialogInfo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        configurarVentana();
    }
    
    private void configurarVentana() {
        //posiciono el frame al centro de la pantalla
        this.setLocationRelativeTo(null);
        //desactiva el cambio de tamaño de la ventana
        this.setResizable(false);
        this.setTitle("Info");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jlblFacebookPagina = new javax.swing.JLabel();
        jlblFacebookGrupo = new javax.swing.JLabel();
        jlblWhatsAppGrupo = new javax.swing.JLabel();
        jlblTelegramGrupo = new javax.swing.JLabel();
        jlblYouTube = new javax.swing.JLabel();
        jlblWebFacturadorSunatSfs = new javax.swing.JLabel();
        JlblWebSmartCuyTec = new javax.swing.JLabel();
        jlblInstagramFacturadorSunatSfs = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(141, 170, 235));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Nuestro objetivo:");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Genera archivos planos de:\n- Factura\n- Boleta de venta\n- Nota de crédito\n- Nota de débito\n- Comunicación de Baja\n\nRepresentación impresa en:\n- A4\n- Ticket 80 mm\n\nSoporta:\n- IGV\n- Operaciones gratuitas\n- Forma de pago (crédito,\n  contado)\n\nCaracterísticas:\n- Registro de clientes\n- Registro de productos\n- Registro de servicios\n- Registro de usuarios");
        jScrollPane1.setViewportView(jTextArea1);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Que todos puedan emitir comprobantes electrónicos");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Nuestras redes:");

        jlblFacebookPagina.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlblFacebookPagina.setForeground(new java.awt.Color(0, 0, 255));
        jlblFacebookPagina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/social/facebook.png"))); // NOI18N
        jlblFacebookPagina.setText("Facturador SUNAT SFS (Página)");
        jlblFacebookPagina.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlblFacebookPagina.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlblFacebookPaginaMouseClicked(evt);
            }
        });

        jlblFacebookGrupo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlblFacebookGrupo.setForeground(new java.awt.Color(0, 0, 255));
        jlblFacebookGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/social/facebook.png"))); // NOI18N
        jlblFacebookGrupo.setText("Facturador SUNAT SFS (Grupo)");
        jlblFacebookGrupo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlblFacebookGrupo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlblFacebookGrupoMouseClicked(evt);
            }
        });

        jlblWhatsAppGrupo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlblWhatsAppGrupo.setForeground(new java.awt.Color(0, 0, 255));
        jlblWhatsAppGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/social/whatsapp.png"))); // NOI18N
        jlblWhatsAppGrupo.setText("WhatsApp (Grupo)");
        jlblWhatsAppGrupo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlblWhatsAppGrupo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlblWhatsAppGrupoMouseClicked(evt);
            }
        });

        jlblTelegramGrupo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlblTelegramGrupo.setForeground(new java.awt.Color(0, 0, 255));
        jlblTelegramGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/social/telegrama.png"))); // NOI18N
        jlblTelegramGrupo.setText("Telegram (Grupo)");
        jlblTelegramGrupo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlblTelegramGrupo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlblTelegramGrupoMouseClicked(evt);
            }
        });

        jlblYouTube.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlblYouTube.setForeground(new java.awt.Color(0, 0, 255));
        jlblYouTube.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/social/youtube.png"))); // NOI18N
        jlblYouTube.setText("FacturadorSUNATSFS");
        jlblYouTube.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlblYouTube.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlblYouTubeMouseClicked(evt);
            }
        });

        jlblWebFacturadorSunatSfs.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlblWebFacturadorSunatSfs.setForeground(new java.awt.Color(0, 0, 255));
        jlblWebFacturadorSunatSfs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/social/red-mundial.png"))); // NOI18N
        jlblWebFacturadorSunatSfs.setText("Facturador SUNAT SFS");
        jlblWebFacturadorSunatSfs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlblWebFacturadorSunatSfs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlblWebFacturadorSunatSfsMouseClicked(evt);
            }
        });

        JlblWebSmartCuyTec.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        JlblWebSmartCuyTec.setForeground(new java.awt.Color(0, 0, 255));
        JlblWebSmartCuyTec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/social/red-mundial.png"))); // NOI18N
        JlblWebSmartCuyTec.setText("Smart Cuy Tec (Desarrollador)");
        JlblWebSmartCuyTec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JlblWebSmartCuyTec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JlblWebSmartCuyTecMouseClicked(evt);
            }
        });

        jlblInstagramFacturadorSunatSfs.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlblInstagramFacturadorSunatSfs.setForeground(new java.awt.Color(0, 0, 255));
        jlblInstagramFacturadorSunatSfs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/social/instagram.png"))); // NOI18N
        jlblInstagramFacturadorSunatSfs.setText("facturadorsunatsfs");
        jlblInstagramFacturadorSunatSfs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlblInstagramFacturadorSunatSfs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlblInstagramFacturadorSunatSfsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1))
                    .addComponent(jLabel1)
                    .addComponent(jlblFacebookPagina)
                    .addComponent(jlblFacebookGrupo)
                    .addComponent(jlblWhatsAppGrupo)
                    .addComponent(jlblTelegramGrupo)
                    .addComponent(jlblYouTube)
                    .addComponent(jlblWebFacturadorSunatSfs)
                    .addComponent(JlblWebSmartCuyTec)
                    .addComponent(jlblInstagramFacturadorSunatSfs))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblFacebookPagina)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblFacebookGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblWhatsAppGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblTelegramGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblYouTube)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblInstagramFacturadorSunatSfs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblWebFacturadorSunatSfs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JlblWebSmartCuyTec)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jlblFacebookPaginaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlblFacebookPaginaMouseClicked
        Metodos.abrirLink("https://www.facebook.com/FacturadorSUNATSFS");
    }//GEN-LAST:event_jlblFacebookPaginaMouseClicked

    private void jlblFacebookGrupoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlblFacebookGrupoMouseClicked
        Metodos.abrirLink("https://www.facebook.com/groups/facturadorsunatsfs");
    }//GEN-LAST:event_jlblFacebookGrupoMouseClicked

    private void jlblWhatsAppGrupoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlblWhatsAppGrupoMouseClicked
        Metodos.abrirLink("https://chat.whatsapp.com/H2PQcHCd5bP9iBeFzmJVca");
    }//GEN-LAST:event_jlblWhatsAppGrupoMouseClicked

    private void jlblTelegramGrupoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlblTelegramGrupoMouseClicked
        Metodos.abrirLink("https://t.me/joinchat/NnMIbaTIU5RiMWUx");
    }//GEN-LAST:event_jlblTelegramGrupoMouseClicked

    private void jlblYouTubeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlblYouTubeMouseClicked
        Metodos.abrirLink("https://www.youtube.com/c/FacturadorSUNATSFS");
    }//GEN-LAST:event_jlblYouTubeMouseClicked

    private void jlblWebFacturadorSunatSfsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlblWebFacturadorSunatSfsMouseClicked
        Metodos.abrirLink("https://www.smartcuytec.com/FacturadorSUNATSFS");
    }//GEN-LAST:event_jlblWebFacturadorSunatSfsMouseClicked

    private void JlblWebSmartCuyTecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JlblWebSmartCuyTecMouseClicked
        Metodos.abrirLink("https://smartcuytec.com");
    }//GEN-LAST:event_JlblWebSmartCuyTecMouseClicked

    private void jlblInstagramFacturadorSunatSfsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlblInstagramFacturadorSunatSfsMouseClicked
        Metodos.abrirLink("https://www.instagram.com/facturadorsunatsfs");
    }//GEN-LAST:event_jlblInstagramFacturadorSunatSfsMouseClicked

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
            java.util.logging.Logger.getLogger(JDialogInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogInfo dialog = new JDialogInfo(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel JlblWebSmartCuyTec;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel jlblFacebookGrupo;
    private javax.swing.JLabel jlblFacebookPagina;
    private javax.swing.JLabel jlblInstagramFacturadorSunatSfs;
    private javax.swing.JLabel jlblTelegramGrupo;
    private javax.swing.JLabel jlblWebFacturadorSunatSfs;
    private javax.swing.JLabel jlblWhatsAppGrupo;
    private javax.swing.JLabel jlblYouTube;
    // End of variables declaration//GEN-END:variables
}

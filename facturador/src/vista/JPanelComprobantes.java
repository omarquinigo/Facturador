package vista;

import controlador.Metodos;
import controlador.Rutas;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import modelo.Baja;
import vista.factura.JPanelFacturaNueva;
import modelo.Factura;
import vista.baja.JPanelBajaNueva;

public class JPanelComprobantes extends javax.swing.JPanel {
    
    ResultSet rs;
    DefaultTableModel dtmFacturas;
    DefaultTableModel dtmBajas;

    public JPanelComprobantes() {
        initComponents();
        CargarFacturas();
        CargarBajas();
    }
    
    private void CargarFacturas() {
        try {
            dtmFacturas = (DefaultTableModel) jtblFactura.getModel();
            dtmFacturas.setRowCount(0);
            rs = Factura.Consulta("select *\n"
                    + "from factura\n"
                    + "inner join cliente\n"
                    + "on cliente.id = factura.idCliente\n"
                    + "order by factura.id desc;");
            String fila[] = new String[6];
            while (rs.next()) {
                fila[0] = rs.getString("factura.id");
                fila[1] = rs.getString("cliente.nombreRazonSocial");
                fila[2] = rs.getString("factura.fecha");
                fila[3] = rs.getString("factura.moneda");
                fila[4] = rs.getString("factura.igv");
                fila[5] = rs.getString("factura.importeTotal");
                dtmFacturas.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando facturas: \n" + e);
            Metodos.MensajeError("Error cargando facturas: \n" + e);
        }
    }
    
    private void CargarBajas() {
        try {
            dtmBajas = (DefaultTableModel) jtblBaja.getModel();
            dtmBajas.setRowCount(0);
            rs = Factura.Consulta("select * \n"
                    + "from baja\n"
                    + "inner join bajadet\n"
                    + "on bajadet.idBaja = baja.id\n"
                    + "order by baja.id desc;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("bajadet.tipoComprobante");
                fila[1] = rs.getString("bajadet.idComprobante");
                fila[2] = rs.getString("baja.fecha");
                fila[3] = rs.getString("baja.id");
                fila[4] = rs.getString("bajadet.motivo");
                dtmBajas.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando bajas: \n" + e);
            Metodos.MensajeError("Error cargando bajas: \n" + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlblComprobantesElectronicos = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpnlFacturas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblFactura = new javax.swing.JTable();
        jbtnFacturaNueva = new javax.swing.JButton();
        jbtnFacturaImprimir = new javax.swing.JButton();
        jbtnFacturaCrearPDF = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblBaja = new javax.swing.JTable();
        jbtnFacturaNueva1 = new javax.swing.JButton();
        jbtnBajaCrearPDF = new javax.swing.JButton();
        jbtnBajaImprimir = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 192, 192));

        jlblComprobantesElectronicos.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblComprobantesElectronicos.setText("Comprobantes electrónicos");

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jpnlFacturas.setBackground(new java.awt.Color(141, 170, 235));

        jtblFactura.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jtblFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Cliente", "Fecha", "Moneda", "IGV", "Importe T."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblFactura.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtblFactura);
        if (jtblFactura.getColumnModel().getColumnCount() > 0) {
            jtblFactura.getColumnModel().getColumn(0).setPreferredWidth(100);
            jtblFactura.getColumnModel().getColumn(1).setPreferredWidth(200);
            jtblFactura.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblFactura.getColumnModel().getColumn(3).setPreferredWidth(100);
            jtblFactura.getColumnModel().getColumn(3).setHeaderValue("Moneda");
            jtblFactura.getColumnModel().getColumn(4).setPreferredWidth(80);
            jtblFactura.getColumnModel().getColumn(5).setPreferredWidth(80);
            jtblFactura.getColumnModel().getColumn(5).setHeaderValue("Importe T.");
        }

        jbtnFacturaNueva.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnFacturaNueva.setText("Nuevo");
        jbtnFacturaNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFacturaNuevaActionPerformed(evt);
            }
        });

        jbtnFacturaImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnFacturaImprimir.setText("Imprimir");
        jbtnFacturaImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFacturaImprimirActionPerformed(evt);
            }
        });

        jbtnFacturaCrearPDF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnFacturaCrearPDF.setText("Crear PDF");
        jbtnFacturaCrearPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFacturaCrearPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlFacturasLayout = new javax.swing.GroupLayout(jpnlFacturas);
        jpnlFacturas.setLayout(jpnlFacturasLayout);
        jpnlFacturasLayout.setHorizontalGroup(
            jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlFacturasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnFacturaImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jbtnFacturaNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnFacturaCrearPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnlFacturasLayout.setVerticalGroup(
            jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlFacturasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                    .addGroup(jpnlFacturasLayout.createSequentialGroup()
                        .addComponent(jbtnFacturaNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnFacturaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnFacturaImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Facturas", jpnlFacturas);

        jPanel1.setBackground(new java.awt.Color(141, 170, 235));

        jtblBaja.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jtblBaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo comprobante", "Comprobante", "Fecha", "Baja", "Motivo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblBaja.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jtblBaja);
        if (jtblBaja.getColumnModel().getColumnCount() > 0) {
            jtblBaja.getColumnModel().getColumn(0).setPreferredWidth(80);
            jtblBaja.getColumnModel().getColumn(1).setPreferredWidth(150);
            jtblBaja.getColumnModel().getColumn(2).setPreferredWidth(50);
            jtblBaja.getColumnModel().getColumn(3).setPreferredWidth(80);
            jtblBaja.getColumnModel().getColumn(4).setPreferredWidth(150);
        }

        jbtnFacturaNueva1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnFacturaNueva1.setText("Nuevo");
        jbtnFacturaNueva1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFacturaNueva1ActionPerformed(evt);
            }
        });

        jbtnBajaCrearPDF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnBajaCrearPDF.setText("Crear PDF");
        jbtnBajaCrearPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBajaCrearPDFActionPerformed(evt);
            }
        });

        jbtnBajaImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnBajaImprimir.setText("Imprimir");
        jbtnBajaImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBajaImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnFacturaNueva1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnBajaImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnBajaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbtnFacturaNueva1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBajaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBajaImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bajas", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlblComprobantesElectronicos)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblComprobantesElectronicos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnFacturaImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFacturaImprimirActionPerformed
        int fila = jtblFactura.getSelectedRow();
        if (fila != -1) {
            String id = jtblFactura.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("Facturas", id) == true){
                Metodos.AbrirPDF("Factura", id);
            } else {
                Metodos.MensajeAlerta("El PDF no existe.");
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una factura");
        }
    }//GEN-LAST:event_jbtnFacturaImprimirActionPerformed

    private void jbtnFacturaNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFacturaNuevaActionPerformed
        JPanelFacturaNueva jpfn = new JPanelFacturaNueva();
        Metodos.CambiarPanel(jpfn);
    }//GEN-LAST:event_jbtnFacturaNuevaActionPerformed

    private void jbtnFacturaCrearPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFacturaCrearPDFActionPerformed
        int fila = jtblFactura.getSelectedRow();
        if (fila != -1) {
            String id = jtblFactura.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("Facturas", id) == true) {
                Metodos.MensajeAlerta("El PDF ya existe.");
            } else {
                try {
                    String hash = Metodos.getHash(Rutas.getRutaHash("01", id));
                    Factura.RegistrarHash(id, hash);
                    Factura.CrearQR(id, hash);
                    Factura.CrearPDF(id);
                } catch (Exception e) {
                    System.out.println("Error creando PDF: " + e);
                    Metodos.MensajeError("Error creando PDF: " + e);
                }
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una factura");
        }
    }//GEN-LAST:event_jbtnFacturaCrearPDFActionPerformed

    private void jbtnFacturaNueva1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFacturaNueva1ActionPerformed
        JPanelBajaNueva jpbn = new JPanelBajaNueva();
        Metodos.CambiarPanel(jpbn);
    }//GEN-LAST:event_jbtnFacturaNueva1ActionPerformed

    private void jbtnBajaCrearPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBajaCrearPDFActionPerformed
        int fila = jtblBaja.getSelectedRow();
        if (fila != -1) {
            String id = jtblBaja.getValueAt(fila, 3).toString();
            if (Metodos.ExistePDF("Bajas", id) == true) {
                Metodos.MensajeAlerta("El PDF ya existe.");
            } else {
                try {
                    Baja.CrearPDF(id);
                } catch (Exception e) {
                    System.out.println("Error creando PDF: " + e);
                    Metodos.MensajeError("Error creando PDF: " + e);
                }
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una baja");
        }
    }//GEN-LAST:event_jbtnBajaCrearPDFActionPerformed

    private void jbtnBajaImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBajaImprimirActionPerformed
        int fila = jtblBaja.getSelectedRow();
        if (fila != -1) {
            String id = jtblBaja.getValueAt(fila, 3).toString();
            if (Metodos.ExistePDF("Bajas", id) == true){
                Metodos.AbrirPDF("Baja", id);
            } else {
                Metodos.MensajeAlerta("El PDF no existe.");
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una factura");
        }
    }//GEN-LAST:event_jbtnBajaImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnBajaCrearPDF;
    private javax.swing.JButton jbtnBajaImprimir;
    private javax.swing.JButton jbtnFacturaCrearPDF;
    private javax.swing.JButton jbtnFacturaImprimir;
    private javax.swing.JButton jbtnFacturaNueva;
    private javax.swing.JButton jbtnFacturaNueva1;
    private javax.swing.JLabel jlblComprobantesElectronicos;
    private javax.swing.JPanel jpnlFacturas;
    private javax.swing.JTable jtblBaja;
    private javax.swing.JTable jtblFactura;
    // End of variables declaration//GEN-END:variables
}

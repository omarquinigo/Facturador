package vista;

import controlador.Metodos;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import vista.factura.JPanelFacturaNueva;
import modelo.Factura;

public class JPanelComprobantes extends javax.swing.JPanel {
    
    ResultSet rs;
    DefaultTableModel dtmFacturas;

    public JPanelComprobantes() {
        initComponents();
        CargarFacturas();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlblComprobantesElectronicos = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpnlFacturas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblFactura = new javax.swing.JTable();
        jbtnNuevaFactura = new javax.swing.JButton();
        jbtnImprimirFactura = new javax.swing.JButton();

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
            jtblFactura.getColumnModel().getColumn(1).setPreferredWidth(300);
            jtblFactura.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblFactura.getColumnModel().getColumn(3).setPreferredWidth(50);
            jtblFactura.getColumnModel().getColumn(4).setPreferredWidth(80);
            jtblFactura.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        jbtnNuevaFactura.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNuevaFactura.setText("Nuevo");
        jbtnNuevaFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevaFacturaActionPerformed(evt);
            }
        });

        jbtnImprimirFactura.setText("Imprimir");
        jbtnImprimirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnImprimirFacturaActionPerformed(evt);
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
                    .addComponent(jbtnImprimirFactura, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jbtnNuevaFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnlFacturasLayout.setVerticalGroup(
            jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlFacturasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                    .addGroup(jpnlFacturasLayout.createSequentialGroup()
                        .addComponent(jbtnNuevaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnImprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Facturas", jpnlFacturas);

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

    private void jbtnNuevaFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevaFacturaActionPerformed
        JPanelFacturaNueva jpfn = new JPanelFacturaNueva();
        Metodos.CambiarPanel(jpfn);
    }//GEN-LAST:event_jbtnNuevaFacturaActionPerformed

    private void jbtnImprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnImprimirFacturaActionPerformed
        int fila = jtblFactura.getSelectedRow();
        if (fila != -1) {
            String IdFactura = jtblFactura.getValueAt(fila, 0).toString();
            Metodos.AbrirPDF("Factura", IdFactura);
        } else {
            Metodos.MensajeAlerta("Seleccione una factura");
        }
    }//GEN-LAST:event_jbtnImprimirFacturaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnImprimirFactura;
    private javax.swing.JButton jbtnNuevaFactura;
    private javax.swing.JLabel jlblComprobantesElectronicos;
    private javax.swing.JPanel jpnlFacturas;
    private javax.swing.JTable jtblFactura;
    // End of variables declaration//GEN-END:variables
}

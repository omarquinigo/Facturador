package vista;

import controlador.Metodos;
import controlador.Rutas;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import modelo.Baja;
import modelo.Boleta;
import vista.factura.JPanelFacturaNueva;
import modelo.Factura;
import modelo.NotaCredito;
import modelo.NotaDebito;
import vista.baja.JPanelBajaNueva;
import vista.boleta.JPanelBoletaNueva;
import vista.notacredito.JPanelNotaCreditoNueva;
import vista.notadebito.JPanelNotaDebitoNueva;

public class JPanelComprobantes extends javax.swing.JPanel {
    
    ResultSet rs;
    DefaultTableModel dtmFacturas;
    DefaultTableModel dtmBoletas;
    DefaultTableModel dtmNotasCreditoFactura;
    DefaultTableModel dtmNotasCreditoBoleta;
    DefaultTableModel dtmNotasDebitoFactura;
    DefaultTableModel dtmNotasDebitoBoleta;
    DefaultTableModel dtmBajas;

    public JPanelComprobantes() {
        initComponents();
        CargarFacturas();
        CargarBoletas();
        CargarNotasCreditoFactura();
        CargarNotasCreditoBoleta();
        CargarNotasDebitoFactura();
        CargarNotasDebitoBoleta();
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
    
    private void CargarBoletas() {
        try {
            dtmBoletas = (DefaultTableModel) jtblBoleta.getModel();
            dtmBoletas.setRowCount(0);
            rs = Factura.Consulta("select *\n"
                    + "from boleta\n"
                    + "inner join cliente\n"
                    + "on cliente.id = boleta.idCliente\n"
                    + "order by boleta.id desc;");
            String fila[] = new String[6];
            while (rs.next()) {
                fila[0] = rs.getString("boleta.id");
                fila[1] = rs.getString("cliente.nombreRazonSocial");
                fila[2] = rs.getString("boleta.fecha");
                fila[3] = rs.getString("boleta.moneda");
                fila[4] = rs.getString("boleta.igv");
                fila[5] = rs.getString("boleta.importeTotal");
                dtmBoletas.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando boletas: \n" + e);
            Metodos.MensajeError("Error cargando boletas: \n" + e);
        }
    }
    
    private void CargarNotasCreditoFactura() {
        try {
            dtmNotasCreditoFactura = (DefaultTableModel) jtblNotaCreditoFactura.getModel();
            dtmNotasCreditoFactura.setRowCount(0);
            rs = NotaCredito.Consulta("select * \n"
                    + "from notacredito \n"
                    + "inner join factura \n"
                    + "on factura.id = notacredito.idComprobante \n"
                    + "inner join cliente \n"
                    + "on cliente.id = factura.idCliente \n"
                    + "where notacredito.id LIKE '%FC%' \n"
                    + "order by notacredito.id desc;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("notacredito.id");
                fila[1] = rs.getString("cliente.nombreRazonSocial");
                fila[2] = rs.getString("notacredito.fecha");
                fila[3] = rs.getString("notacredito.idComprobante");
                fila[4] = rs.getString("notacredito.motivo");
                dtmNotasCreditoFactura.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando notas de crédito de facturas: \n" + e);
            Metodos.MensajeError("Error cargando notas de crédito de facturas: \n" + e);
        }
    }
    
    private void CargarNotasCreditoBoleta() {
        try {
            dtmNotasCreditoBoleta = (DefaultTableModel) jtblNotaCreditoBoleta.getModel();
            dtmNotasCreditoBoleta.setRowCount(0);
            rs = NotaCredito.Consulta("select * \n"
                    + "from notacredito \n"
                    + "inner join boleta \n"
                    + "on boleta.id = notacredito.idComprobante \n"
                    + "inner join cliente \n"
                    + "on cliente.id = boleta.idCliente \n"
                    + "where notacredito.id LIKE '%BC%' \n"
                    + "order by notacredito.id desc;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("notacredito.id");
                fila[1] = rs.getString("cliente.nombreRazonSocial");
                fila[2] = rs.getString("notacredito.fecha");
                fila[3] = rs.getString("notacredito.idComprobante");
                fila[4] = rs.getString("notacredito.motivo");
                dtmNotasCreditoBoleta.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando notas de crédito de boletas: \n" + e);
            Metodos.MensajeError("Error cargando notas de crédito de boletas: \n" + e);
        }
    }
    
    private void CargarNotasDebitoFactura() {
        try {
            dtmNotasDebitoFactura = (DefaultTableModel) jtblNotaDebitoFactura.getModel();
            dtmNotasDebitoFactura.setRowCount(0);
            rs = NotaDebito.Consulta("select * \n"
                    + "from notadebito \n"
                    + "inner join factura \n"
                    + "on factura.id = notadebito.idComprobante \n"
                    + "inner join cliente \n"
                    + "on cliente.id = factura.idCliente \n"
                    + "where notadebito.id LIKE '%FD%' \n"
                    + "order by notadebito.id desc;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("notadebito.id");
                fila[1] = rs.getString("cliente.nombreRazonSocial");
                fila[2] = rs.getString("notadebito.fecha");
                fila[3] = rs.getString("notadebito.idComprobante");
                fila[4] = rs.getString("notadebito.motivo");
                dtmNotasDebitoFactura.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando notas de débito de facturas: \n" + e);
            Metodos.MensajeError("Error cargando notas de débito de facturas: \n" + e);
        }
    }
    
    private void CargarNotasDebitoBoleta() {
        try {
            dtmNotasDebitoBoleta = (DefaultTableModel) jtblNotaDebitoBoleta.getModel();
            dtmNotasDebitoBoleta.setRowCount(0);
            rs = NotaDebito.Consulta("select * \n"
                    + "from notadebito \n"
                    + "inner join boleta \n"
                    + "on boleta.id = notadebito.idComprobante \n"
                    + "inner join cliente \n"
                    + "on cliente.id = boleta.idCliente \n"
                    + "where notadebito.id LIKE '%BD%' \n"
                    + "order by notadebito.id desc;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("notadebito.id");
                fila[1] = rs.getString("cliente.nombreRazonSocial");
                fila[2] = rs.getString("notadebito.fecha");
                fila[3] = rs.getString("notadebito.idComprobante");
                fila[4] = rs.getString("notadebito.motivo");
                dtmNotasDebitoBoleta.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando notas de débito de boletas: \n" + e);
            Metodos.MensajeError("Error cargando notas de débito de boletas: \n" + e);
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
        jpnlBoletas = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblBoleta = new javax.swing.JTable();
        jbtnBoletaNueva = new javax.swing.JButton();
        jbtnBoletaImprimir = new javax.swing.JButton();
        jbtnBoletaCrearPDF = new javax.swing.JButton();
        jpnlNotasCredito = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtblNotaCreditoFactura = new javax.swing.JTable();
        jbtnNotaCreditoNueva = new javax.swing.JButton();
        jbtnNotaCreditoFacturaImprimir = new javax.swing.JButton();
        jbtnNotaCreditoFacturaCrearPDF = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtblNotaCreditoBoleta = new javax.swing.JTable();
        jbtnNotaCreditoBoletaCrearPDF = new javax.swing.JButton();
        jbtnNotaCreditoBoletaImprimir = new javax.swing.JButton();
        jpnlNotasDebito = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtblNotaDebitoFactura = new javax.swing.JTable();
        jbtnNotaDebitoNueva = new javax.swing.JButton();
        jbtnNotaDebitoFacturaImprimir = new javax.swing.JButton();
        jbtnNotaDebitoFacturaCrearPDF = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jtblNotaDebitoBoleta = new javax.swing.JTable();
        jbtnNotaDebitoBoletaCrearPDF = new javax.swing.JButton();
        jbtnNotaDebitoBoletaImprimir = new javax.swing.JButton();
        jpnlBajas = new javax.swing.JPanel();
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
            jtblFactura.getColumnModel().getColumn(4).setPreferredWidth(80);
            jtblFactura.getColumnModel().getColumn(5).setPreferredWidth(80);
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

        jpnlBoletas.setBackground(new java.awt.Color(141, 170, 235));

        jtblBoleta.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jtblBoleta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Boleta", "Cliente", "Fecha", "Moneda", "IGV", "Importe T."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblBoleta.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jtblBoleta);
        if (jtblBoleta.getColumnModel().getColumnCount() > 0) {
            jtblBoleta.getColumnModel().getColumn(0).setPreferredWidth(100);
            jtblBoleta.getColumnModel().getColumn(1).setPreferredWidth(200);
            jtblBoleta.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblBoleta.getColumnModel().getColumn(3).setPreferredWidth(100);
            jtblBoleta.getColumnModel().getColumn(4).setPreferredWidth(80);
            jtblBoleta.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        jbtnBoletaNueva.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnBoletaNueva.setText("Nuevo");
        jbtnBoletaNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBoletaNuevaActionPerformed(evt);
            }
        });

        jbtnBoletaImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnBoletaImprimir.setText("Imprimir");
        jbtnBoletaImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBoletaImprimirActionPerformed(evt);
            }
        });

        jbtnBoletaCrearPDF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnBoletaCrearPDF.setText("Crear PDF");
        jbtnBoletaCrearPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBoletaCrearPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlBoletasLayout = new javax.swing.GroupLayout(jpnlBoletas);
        jpnlBoletas.setLayout(jpnlBoletasLayout);
        jpnlBoletasLayout.setHorizontalGroup(
            jpnlBoletasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBoletasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlBoletasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnBoletaImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jbtnBoletaNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnBoletaCrearPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnlBoletasLayout.setVerticalGroup(
            jpnlBoletasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBoletasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlBoletasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                    .addGroup(jpnlBoletasLayout.createSequentialGroup()
                        .addComponent(jbtnBoletaNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBoletaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBoletaImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Boletas", jpnlBoletas);

        jpnlNotasCredito.setBackground(new java.awt.Color(141, 170, 235));

        jtblNotaCreditoFactura.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jtblNotaCreditoFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nota Crédito", "Cliente", "Fecha", "Comprobante", "Motivo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblNotaCreditoFactura.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jtblNotaCreditoFactura);
        if (jtblNotaCreditoFactura.getColumnModel().getColumnCount() > 0) {
            jtblNotaCreditoFactura.getColumnModel().getColumn(0).setPreferredWidth(100);
            jtblNotaCreditoFactura.getColumnModel().getColumn(1).setPreferredWidth(200);
            jtblNotaCreditoFactura.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblNotaCreditoFactura.getColumnModel().getColumn(3).setPreferredWidth(100);
            jtblNotaCreditoFactura.getColumnModel().getColumn(4).setPreferredWidth(160);
        }

        jbtnNotaCreditoNueva.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaCreditoNueva.setText("Nuevo");
        jbtnNotaCreditoNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaCreditoNuevaActionPerformed(evt);
            }
        });

        jbtnNotaCreditoFacturaImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaCreditoFacturaImprimir.setText("Imprimir");
        jbtnNotaCreditoFacturaImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaCreditoFacturaImprimirActionPerformed(evt);
            }
        });

        jbtnNotaCreditoFacturaCrearPDF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaCreditoFacturaCrearPDF.setText("Crear PDF");
        jbtnNotaCreditoFacturaCrearPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaCreditoFacturaCrearPDFActionPerformed(evt);
            }
        });

        jtblNotaCreditoBoleta.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jtblNotaCreditoBoleta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nota Crédito", "Cliente", "Fecha", "Comprobante", "Motivo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblNotaCreditoBoleta.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(jtblNotaCreditoBoleta);
        if (jtblNotaCreditoBoleta.getColumnModel().getColumnCount() > 0) {
            jtblNotaCreditoBoleta.getColumnModel().getColumn(0).setPreferredWidth(100);
            jtblNotaCreditoBoleta.getColumnModel().getColumn(1).setPreferredWidth(200);
            jtblNotaCreditoBoleta.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblNotaCreditoBoleta.getColumnModel().getColumn(3).setPreferredWidth(100);
            jtblNotaCreditoBoleta.getColumnModel().getColumn(4).setPreferredWidth(160);
        }

        jbtnNotaCreditoBoletaCrearPDF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaCreditoBoletaCrearPDF.setText("Crear PDF");
        jbtnNotaCreditoBoletaCrearPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaCreditoBoletaCrearPDFActionPerformed(evt);
            }
        });

        jbtnNotaCreditoBoletaImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaCreditoBoletaImprimir.setText("Imprimir");
        jbtnNotaCreditoBoletaImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaCreditoBoletaImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlNotasCreditoLayout = new javax.swing.GroupLayout(jpnlNotasCredito);
        jpnlNotasCredito.setLayout(jpnlNotasCreditoLayout);
        jpnlNotasCreditoLayout.setHorizontalGroup(
            jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlNotasCreditoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jbtnNotaCreditoFacturaImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                        .addComponent(jbtnNotaCreditoNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnNotaCreditoFacturaCrearPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jbtnNotaCreditoBoletaImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnNotaCreditoBoletaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpnlNotasCreditoLayout.setVerticalGroup(
            jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlNotasCreditoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlNotasCreditoLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlNotasCreditoLayout.createSequentialGroup()
                        .addComponent(jbtnNotaCreditoFacturaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnNotaCreditoFacturaImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jbtnNotaCreditoNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnNotaCreditoBoletaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnNotaCreditoBoletaImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("N. Crédito", jpnlNotasCredito);

        jpnlNotasDebito.setBackground(new java.awt.Color(141, 170, 235));

        jtblNotaDebitoFactura.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jtblNotaDebitoFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nota Débito", "Cliente", "Fecha", "Comprobante", "Motivo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblNotaDebitoFactura.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(jtblNotaDebitoFactura);
        if (jtblNotaDebitoFactura.getColumnModel().getColumnCount() > 0) {
            jtblNotaDebitoFactura.getColumnModel().getColumn(0).setPreferredWidth(100);
            jtblNotaDebitoFactura.getColumnModel().getColumn(1).setPreferredWidth(200);
            jtblNotaDebitoFactura.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblNotaDebitoFactura.getColumnModel().getColumn(3).setPreferredWidth(100);
            jtblNotaDebitoFactura.getColumnModel().getColumn(4).setPreferredWidth(160);
        }

        jbtnNotaDebitoNueva.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaDebitoNueva.setText("Nuevo");
        jbtnNotaDebitoNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaDebitoNuevaActionPerformed(evt);
            }
        });

        jbtnNotaDebitoFacturaImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaDebitoFacturaImprimir.setText("Imprimir");
        jbtnNotaDebitoFacturaImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaDebitoFacturaImprimirActionPerformed(evt);
            }
        });

        jbtnNotaDebitoFacturaCrearPDF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaDebitoFacturaCrearPDF.setText("Crear PDF");
        jbtnNotaDebitoFacturaCrearPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaDebitoFacturaCrearPDFActionPerformed(evt);
            }
        });

        jtblNotaDebitoBoleta.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jtblNotaDebitoBoleta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nota Débito", "Cliente", "Fecha", "Comprobante", "Motivo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblNotaDebitoBoleta.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(jtblNotaDebitoBoleta);
        if (jtblNotaDebitoBoleta.getColumnModel().getColumnCount() > 0) {
            jtblNotaDebitoBoleta.getColumnModel().getColumn(0).setPreferredWidth(100);
            jtblNotaDebitoBoleta.getColumnModel().getColumn(1).setPreferredWidth(200);
            jtblNotaDebitoBoleta.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblNotaDebitoBoleta.getColumnModel().getColumn(3).setPreferredWidth(100);
            jtblNotaDebitoBoleta.getColumnModel().getColumn(4).setPreferredWidth(160);
        }

        jbtnNotaDebitoBoletaCrearPDF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaDebitoBoletaCrearPDF.setText("Crear PDF");
        jbtnNotaDebitoBoletaCrearPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaDebitoBoletaCrearPDFActionPerformed(evt);
            }
        });

        jbtnNotaDebitoBoletaImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaDebitoBoletaImprimir.setText("Imprimir");
        jbtnNotaDebitoBoletaImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaDebitoBoletaImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlNotasDebitoLayout = new javax.swing.GroupLayout(jpnlNotasDebito);
        jpnlNotasDebito.setLayout(jpnlNotasDebitoLayout);
        jpnlNotasDebitoLayout.setHorizontalGroup(
            jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlNotasDebitoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane8)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jbtnNotaDebitoFacturaImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                        .addComponent(jbtnNotaDebitoNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnNotaDebitoFacturaCrearPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jbtnNotaDebitoBoletaImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnNotaDebitoBoletaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpnlNotasDebitoLayout.setVerticalGroup(
            jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlNotasDebitoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlNotasDebitoLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlNotasDebitoLayout.createSequentialGroup()
                        .addComponent(jbtnNotaDebitoFacturaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnNotaDebitoFacturaImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jbtnNotaDebitoNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnNotaDebitoBoletaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnNotaDebitoBoletaImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("N. Débito", jpnlNotasDebito);

        jpnlBajas.setBackground(new java.awt.Color(141, 170, 235));

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

        javax.swing.GroupLayout jpnlBajasLayout = new javax.swing.GroupLayout(jpnlBajas);
        jpnlBajas.setLayout(jpnlBajasLayout);
        jpnlBajasLayout.setHorizontalGroup(
            jpnlBajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBajasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlBajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnFacturaNueva1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBajasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jpnlBajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnBajaImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnBajaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jpnlBajasLayout.setVerticalGroup(
            jpnlBajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBajasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlBajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlBajasLayout.createSequentialGroup()
                        .addComponent(jbtnFacturaNueva1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBajaCrearPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBajaImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bajas", jpnlBajas);

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
                    System.out.println("Error creando PDF: Debe generar el XML primero\n" + e);
                    Metodos.MensajeError("Error creando PDF: Debe generar el XML primero\n" + e);
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

    private void jbtnBoletaNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBoletaNuevaActionPerformed
        JPanelBoletaNueva jpbn = new JPanelBoletaNueva();
        Metodos.CambiarPanel(jpbn);
    }//GEN-LAST:event_jbtnBoletaNuevaActionPerformed

    private void jbtnBoletaImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBoletaImprimirActionPerformed
        int fila = jtblBoleta.getSelectedRow();
        if (fila != -1) {
            String id = jtblBoleta.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("Boletas", id) == true){
                Metodos.AbrirPDF("Boleta", id);
            } else {
                Metodos.MensajeAlerta("El PDF no existe.");
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una boleta.");
        }
    }//GEN-LAST:event_jbtnBoletaImprimirActionPerformed

    private void jbtnBoletaCrearPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBoletaCrearPDFActionPerformed
        int fila = jtblBoleta.getSelectedRow();
        if (fila != -1) {
            String id = jtblBoleta.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("Boletas", id) == true) {
                Metodos.MensajeAlerta("El PDF ya existe.");
            } else {
                try {
                    String hash = Metodos.getHash(Rutas.getRutaHash("03", id));
                    Boleta.RegistrarHash(id, hash);
                    Boleta.CrearQR(id, hash);
                    Boleta.CrearPDF(id);
                } catch (Exception e) {
                    System.out.println("Error creando PDF: Debe generar el XML primero\n" + e);
                    Metodos.MensajeError("Error creando PDF: Debe generar el XML primero\n" + e);
                }
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una boleta.");
        }
    }//GEN-LAST:event_jbtnBoletaCrearPDFActionPerformed

    private void jbtnNotaCreditoNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaCreditoNuevaActionPerformed
        JPanelNotaCreditoNueva jpncn = new JPanelNotaCreditoNueva();
        Metodos.CambiarPanel(jpncn);
    }//GEN-LAST:event_jbtnNotaCreditoNuevaActionPerformed

    private void jbtnNotaCreditoFacturaImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaCreditoFacturaImprimirActionPerformed
        int fila = jtblNotaCreditoFactura.getSelectedRow();
        if (fila != -1) {
            String id = jtblNotaCreditoFactura.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("NotasCredito", id) == true){
                Metodos.AbrirPDF("NotaCredito", id);
            } else {
                Metodos.MensajeAlerta("El PDF no existe.");
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una nota de crédito de factura.");
        }
    }//GEN-LAST:event_jbtnNotaCreditoFacturaImprimirActionPerformed

    private void jbtnNotaCreditoFacturaCrearPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaCreditoFacturaCrearPDFActionPerformed
        int fila = jtblNotaCreditoFactura.getSelectedRow();
        if (fila != -1) {
            String id = jtblNotaCreditoFactura.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("NotasCredito", id) == true) {
                Metodos.MensajeAlerta("El PDF ya existe.");
            } else {
                try {
                    String hash = Metodos.getHash(Rutas.getRutaHash("07", id));
                    NotaCredito.RegistrarHash(id, hash);
                    NotaCredito.CrearQR(id, hash);
                    NotaCredito.CrearPDF(id);
                } catch (Exception e) {
                    System.out.println("Error creando PDF: Debe generar el XML primero\n" + e);
                    Metodos.MensajeError("Error creando PDF: Debe generar el XML primero\n" + e);
                }
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una nota de crédito de factura.");
        }
    }//GEN-LAST:event_jbtnNotaCreditoFacturaCrearPDFActionPerformed

    private void jbtnNotaCreditoBoletaCrearPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaCreditoBoletaCrearPDFActionPerformed
        int fila = jtblNotaCreditoBoleta.getSelectedRow();
        if (fila != -1) {
            String id = jtblNotaCreditoBoleta.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("NotasCredito", id) == true) {
                Metodos.MensajeAlerta("El PDF ya existe.");
            } else {
                try {
                    String hash = Metodos.getHash(Rutas.getRutaHash("07", id));
                    NotaCredito.RegistrarHash(id, hash);
                    NotaCredito.CrearQR(id, hash);
                    NotaCredito.CrearPDF(id);
                } catch (Exception e) {
                    System.out.println("Error creando PDF: Debe generar el XML primero\n" + e);
                    Metodos.MensajeError("Error creando PDF: Debe generar el XML primero\n" + e);
                }
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una nota de crédito de boleta.");
        }
    }//GEN-LAST:event_jbtnNotaCreditoBoletaCrearPDFActionPerformed

    private void jbtnNotaCreditoBoletaImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaCreditoBoletaImprimirActionPerformed
        int fila = jtblNotaCreditoBoleta.getSelectedRow();
        if (fila != -1) {
            String id = jtblNotaCreditoBoleta.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("NotasCredito", id) == true){
                Metodos.AbrirPDF("NotaCredito", id);
            } else {
                Metodos.MensajeAlerta("El PDF no existe.");
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una nota de crédito de boleta.");
        }
    }//GEN-LAST:event_jbtnNotaCreditoBoletaImprimirActionPerformed

    private void jbtnNotaDebitoNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaDebitoNuevaActionPerformed
        JPanelNotaDebitoNueva jpndn = new JPanelNotaDebitoNueva();
        Metodos.CambiarPanel(jpndn);
    }//GEN-LAST:event_jbtnNotaDebitoNuevaActionPerformed

    private void jbtnNotaDebitoFacturaImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaDebitoFacturaImprimirActionPerformed
        int fila = jtblNotaDebitoFactura.getSelectedRow();
        if (fila != -1) {
            String id = jtblNotaDebitoFactura.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("NotasDebito", id) == true){
                Metodos.AbrirPDF("NotaDebito", id);
            } else {
                Metodos.MensajeAlerta("El PDF no existe.");
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una nota de débito de factura.");
        }
    }//GEN-LAST:event_jbtnNotaDebitoFacturaImprimirActionPerformed

    private void jbtnNotaDebitoFacturaCrearPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaDebitoFacturaCrearPDFActionPerformed
        int fila = jtblNotaDebitoFactura.getSelectedRow();
        if (fila != -1) {
            String id = jtblNotaDebitoFactura.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("NotasDebito", id) == true) {
                Metodos.MensajeAlerta("El PDF ya existe.");
            } else {
                try {
                    String hash = Metodos.getHash(Rutas.getRutaHash("08", id));
                    NotaDebito.RegistrarHash(id, hash);
                    NotaDebito.CrearQR(id, hash);
                    NotaDebito.CrearPDF(id);
                } catch (Exception e) {
                    System.out.println("Error creando PDF: Debe generar el XML primero\n" + e);
                    Metodos.MensajeError("Error creando PDF: Debe generar el XML primero\n" + e);
                }
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una nota de débito de factura.");
        }
    }//GEN-LAST:event_jbtnNotaDebitoFacturaCrearPDFActionPerformed

    private void jbtnNotaDebitoBoletaCrearPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaDebitoBoletaCrearPDFActionPerformed
        int fila = jtblNotaDebitoBoleta.getSelectedRow();
        if (fila != -1) {
            String id = jtblNotaDebitoBoleta.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("NotasDebito", id) == true) {
                Metodos.MensajeAlerta("El PDF ya existe.");
            } else {
                try {
                    String hash = Metodos.getHash(Rutas.getRutaHash("08", id));
                    NotaDebito.RegistrarHash(id, hash);
                    NotaDebito.CrearQR(id, hash);
                    NotaDebito.CrearPDF(id);
                } catch (Exception e) {
                    System.out.println("Error creando PDF: Debe generar el XML primero\n" + e);
                    Metodos.MensajeError("Error creando PDF: Debe generar el XML primero\n" + e);
                }
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una nota de débito de boleta.");
        }
    }//GEN-LAST:event_jbtnNotaDebitoBoletaCrearPDFActionPerformed

    private void jbtnNotaDebitoBoletaImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaDebitoBoletaImprimirActionPerformed
        int fila = jtblNotaDebitoBoleta.getSelectedRow();
        if (fila != -1) {
            String id = jtblNotaDebitoBoleta.getValueAt(fila, 0).toString();
            if (Metodos.ExistePDF("NotasDebito", id) == true){
                Metodos.AbrirPDF("NotaDebito", id);
            } else {
                Metodos.MensajeAlerta("El PDF no existe.");
            }
        } else {
            Metodos.MensajeAlerta("Seleccione una nota de débito de boleta.");
        }
    }//GEN-LAST:event_jbtnNotaDebitoBoletaImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnBajaCrearPDF;
    private javax.swing.JButton jbtnBajaImprimir;
    private javax.swing.JButton jbtnBoletaCrearPDF;
    private javax.swing.JButton jbtnBoletaImprimir;
    private javax.swing.JButton jbtnBoletaNueva;
    private javax.swing.JButton jbtnFacturaCrearPDF;
    private javax.swing.JButton jbtnFacturaImprimir;
    private javax.swing.JButton jbtnFacturaNueva;
    private javax.swing.JButton jbtnFacturaNueva1;
    private javax.swing.JButton jbtnNotaCreditoBoletaCrearPDF;
    private javax.swing.JButton jbtnNotaCreditoBoletaImprimir;
    private javax.swing.JButton jbtnNotaCreditoFacturaCrearPDF;
    private javax.swing.JButton jbtnNotaCreditoFacturaImprimir;
    private javax.swing.JButton jbtnNotaCreditoNueva;
    private javax.swing.JButton jbtnNotaDebitoBoletaCrearPDF;
    private javax.swing.JButton jbtnNotaDebitoBoletaImprimir;
    private javax.swing.JButton jbtnNotaDebitoFacturaCrearPDF;
    private javax.swing.JButton jbtnNotaDebitoFacturaImprimir;
    private javax.swing.JButton jbtnNotaDebitoNueva;
    private javax.swing.JLabel jlblComprobantesElectronicos;
    private javax.swing.JPanel jpnlBajas;
    private javax.swing.JPanel jpnlBoletas;
    private javax.swing.JPanel jpnlFacturas;
    private javax.swing.JPanel jpnlNotasCredito;
    private javax.swing.JPanel jpnlNotasDebito;
    private javax.swing.JTable jtblBaja;
    private javax.swing.JTable jtblBoleta;
    private javax.swing.JTable jtblFactura;
    private javax.swing.JTable jtblNotaCreditoBoleta;
    private javax.swing.JTable jtblNotaCreditoFactura;
    private javax.swing.JTable jtblNotaDebitoBoleta;
    private javax.swing.JTable jtblNotaDebitoFactura;
    // End of variables declaration//GEN-END:variables
}

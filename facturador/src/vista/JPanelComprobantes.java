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
import vista.guiaremision.JPanelGuiaRemisionNueva;
import vista.notacredito.JPanelNotaCreditoNueva;
import vista.notadebito.JPanelNotaDebitoNueva;

public class JPanelComprobantes extends javax.swing.JPanel {
    
    ResultSet rs;
    DefaultTableModel dtmFacturas;
    DefaultTableModel dtmBoletas;
    DefaultTableModel dtmNotasCredito;
    DefaultTableModel dtmNotasDebito;
    DefaultTableModel dtmBajas;

    public JPanelComprobantes() {
        initComponents();
        cargarFacturas();
        cargarBoletas();
        cargarNotasCredito();
        cargarNotasDebito();
        CargarBajas();
    }
    
    private void cargarFacturas() {
        try {
            dtmFacturas = (DefaultTableModel) jtblFactura.getModel();
            dtmFacturas.setRowCount(0);
            rs = Factura.consulta("select *\n"
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
            Metodos.mensajeError("Error cargando facturas: \n" + e);
        }
    }
    
    private void cargarBoletas() {
        try {
            dtmBoletas = (DefaultTableModel) jtblBoleta.getModel();
            dtmBoletas.setRowCount(0);
            rs = Factura.consulta("select *\n"
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
            Metodos.mensajeError("Error cargando boletas: \n" + e);
        }
    }
    
    private void cargarNotasCredito() {
        try {
            dtmNotasCredito = (DefaultTableModel) jtblNotaCredito.getModel();
            dtmNotasCredito.setRowCount(0);
            rs = NotaCredito.consulta("select * \n"
                    + "from notacredito \n"
                    
                    + "left join factura \n"
                    + "on factura.id = notacredito.idComprobante \n"
                    
                    + "left join boleta \n"
                    + "on boleta.id = notacredito.idComprobante \n"
                    
                    + "left join cliente as cf \n"
                    + "on cf.id = factura.idCliente \n"
                    
                    + "left join cliente as cb \n"
                    + "on cb.id = boleta.idCliente \n"
                    
                    //+ "where notacredito.id LIKE '%FC%' \n"
              + "order by STR_TO_DATE(notacredito.fecha, '%d/%m/%Y') desc, \n"
                    + "notacredito.id desc;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("notacredito.id");
                String aux = rs.getString("factura.id");
                if(aux!= null){ // cliente factura
                    fila[1] = rs.getString("cf.nombreRazonSocial");
                } else { // cliente boleta
                    fila[1] = rs.getString("cb.nombreRazonSocial");
                }                
                
                fila[2] = rs.getString("notacredito.fecha");
                fila[3] = rs.getString("notacredito.idComprobante");
                fila[4] = rs.getString("notacredito.motivo");
                dtmNotasCredito.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando notas de crédito: \n" + e);
            Metodos.mensajeError("Error cargando notas de crédito: \n" + e);
        }
    }
    
    private void cargarNotasDebito() {
        try {
            dtmNotasDebito = (DefaultTableModel) jtblNotaDebito.getModel();
            dtmNotasDebito.setRowCount(0);
            rs = NotaCredito.consulta("select * \n"
                    + "from notadebito \n"
                    
                    + "left join factura \n"
                    + "on factura.id = notadebito.idComprobante \n"
                    
                    + "left join boleta \n"
                    + "on boleta.id = notadebito.idComprobante \n"
                    
                    + "left join cliente as cf \n"
                    + "on cf.id = factura.idCliente \n"
                    
                    + "left join cliente as cb \n"
                    + "on cb.id = boleta.idCliente \n"
                    
                    //+ "where notacredito.id LIKE '%FC%' \n"
              + "order by STR_TO_DATE(notadebito.fecha, '%d/%m/%Y') desc, \n"
                    + "notadebito.id desc;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("notadebito.id");
                String aux = rs.getString("notadebito.id");
                if(aux!= null){ // cliente factura
                    fila[1] = rs.getString("cf.nombreRazonSocial");
                } else { // cliente boleta
                    fila[1] = rs.getString("cb.nombreRazonSocial");
                }                
                
                fila[2] = rs.getString("notadebito.fecha");
                fila[3] = rs.getString("notadebito.idComprobante");
                fila[4] = rs.getString("notadebito.motivo");
                dtmNotasDebito.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando notas de débito: \n" + e);
            Metodos.mensajeError("Error cargando notas de débito: \n" + e);
        }
    }
    
    private void CargarBajas() {
        try {
            dtmBajas = (DefaultTableModel) jtblBaja.getModel();
            dtmBajas.setRowCount(0);
            rs = Factura.consulta("select * \n"
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
            Metodos.mensajeError("Error cargando bajas: \n" + e);
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
        jbtnImprimir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtxtFacturaBuscar = new javax.swing.JTextField();
        jpnlBoletas = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblBoleta = new javax.swing.JTable();
        jbtnBoletaNueva = new javax.swing.JButton();
        jbtnBoletaImprimir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jtxtBoletaBuscar = new javax.swing.JTextField();
        jpnlNotasCredito = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtblNotaCredito = new javax.swing.JTable();
        jbtnNotaCreditoNueva = new javax.swing.JButton();
        jbtnNotaCreditoImprimir = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jtxtNCreditoBuscar = new javax.swing.JTextField();
        jpnlNotasDebito = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtblNotaDebito = new javax.swing.JTable();
        jbtnNotaDebitoNueva = new javax.swing.JButton();
        jbtnNotaDebitoImprimir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jtxtNDebitoBuscar = new javax.swing.JTextField();
        jpnlGuiasRemision = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtblGRemision = new javax.swing.JTable();
        jbtnGuiaRemisionNueva = new javax.swing.JButton();
        jbtnImprimir1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jtxtFacturaBuscar1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jpnlBajas = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblBaja = new javax.swing.JTable();
        jbtnFacturaNueva1 = new javax.swing.JButton();
        jbtnBajaImprimir = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jtxtNDebitoBuscar1 = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 192, 192));

        jlblComprobantesElectronicos.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jlblComprobantesElectronicos.setText("Comprobantes electrónicos");

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jpnlFacturas.setBackground(new java.awt.Color(141, 170, 235));

        jtblFactura.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
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
            jtblFactura.getColumnModel().getColumn(0).setPreferredWidth(80);
            jtblFactura.getColumnModel().getColumn(1).setPreferredWidth(300);
            jtblFactura.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblFactura.getColumnModel().getColumn(3).setPreferredWidth(50);
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

        jbtnImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnImprimir.setText("Imprimir");
        jbtnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnImprimirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Buscar:");

        jtxtFacturaBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtFacturaBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtFacturaBuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jpnlFacturasLayout = new javax.swing.GroupLayout(jpnlFacturas);
        jpnlFacturas.setLayout(jpnlFacturasLayout);
        jpnlFacturasLayout.setHorizontalGroup(
            jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlFacturasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlFacturasLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnFacturaNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                    .addGroup(jpnlFacturasLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtFacturaBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpnlFacturasLayout.setVerticalGroup(
            jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlFacturasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtFacturaBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlFacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                    .addGroup(jpnlFacturasLayout.createSequentialGroup()
                        .addComponent(jbtnFacturaNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Facturas", jpnlFacturas);

        jpnlBoletas.setBackground(new java.awt.Color(141, 170, 235));

        jtblBoleta.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
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
            jtblBoleta.getColumnModel().getColumn(0).setPreferredWidth(80);
            jtblBoleta.getColumnModel().getColumn(1).setPreferredWidth(300);
            jtblBoleta.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblBoleta.getColumnModel().getColumn(3).setPreferredWidth(50);
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

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Buscar:");

        jtxtBoletaBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtBoletaBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtBoletaBuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jpnlBoletasLayout = new javax.swing.GroupLayout(jpnlBoletas);
        jpnlBoletas.setLayout(jpnlBoletasLayout);
        jpnlBoletasLayout.setHorizontalGroup(
            jpnlBoletasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBoletasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlBoletasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlBoletasLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlBoletasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnBoletaImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jbtnBoletaNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jpnlBoletasLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtBoletaBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpnlBoletasLayout.setVerticalGroup(
            jpnlBoletasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBoletasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlBoletasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtBoletaBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlBoletasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                    .addGroup(jpnlBoletasLayout.createSequentialGroup()
                        .addComponent(jbtnBoletaNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBoletaImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Boletas", jpnlBoletas);

        jpnlNotasCredito.setBackground(new java.awt.Color(141, 170, 235));

        jtblNotaCredito.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblNotaCredito.setModel(new javax.swing.table.DefaultTableModel(
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
        jtblNotaCredito.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jtblNotaCredito);
        if (jtblNotaCredito.getColumnModel().getColumnCount() > 0) {
            jtblNotaCredito.getColumnModel().getColumn(0).setPreferredWidth(80);
            jtblNotaCredito.getColumnModel().getColumn(1).setPreferredWidth(300);
            jtblNotaCredito.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblNotaCredito.getColumnModel().getColumn(3).setPreferredWidth(80);
            jtblNotaCredito.getColumnModel().getColumn(4).setPreferredWidth(160);
        }

        jbtnNotaCreditoNueva.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaCreditoNueva.setText("Nuevo");
        jbtnNotaCreditoNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaCreditoNuevaActionPerformed(evt);
            }
        });

        jbtnNotaCreditoImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaCreditoImprimir.setText("Imprimir");
        jbtnNotaCreditoImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaCreditoImprimirActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Buscar:");

        jtxtNCreditoBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtNCreditoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtNCreditoBuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jpnlNotasCreditoLayout = new javax.swing.GroupLayout(jpnlNotasCredito);
        jpnlNotasCredito.setLayout(jpnlNotasCreditoLayout);
        jpnlNotasCreditoLayout.setHorizontalGroup(
            jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlNotasCreditoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlNotasCreditoLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnNotaCreditoImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jbtnNotaCreditoNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jpnlNotasCreditoLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtNCreditoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpnlNotasCreditoLayout.setVerticalGroup(
            jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlNotasCreditoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtxtNCreditoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlNotasCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlNotasCreditoLayout.createSequentialGroup()
                        .addComponent(jbtnNotaCreditoNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnNotaCreditoImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("N. Crédito", jpnlNotasCredito);

        jpnlNotasDebito.setBackground(new java.awt.Color(141, 170, 235));

        jtblNotaDebito.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblNotaDebito.setModel(new javax.swing.table.DefaultTableModel(
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
        jtblNotaDebito.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(jtblNotaDebito);
        if (jtblNotaDebito.getColumnModel().getColumnCount() > 0) {
            jtblNotaDebito.getColumnModel().getColumn(0).setPreferredWidth(80);
            jtblNotaDebito.getColumnModel().getColumn(1).setPreferredWidth(300);
            jtblNotaDebito.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblNotaDebito.getColumnModel().getColumn(3).setPreferredWidth(80);
            jtblNotaDebito.getColumnModel().getColumn(4).setPreferredWidth(160);
        }

        jbtnNotaDebitoNueva.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaDebitoNueva.setText("Nuevo");
        jbtnNotaDebitoNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaDebitoNuevaActionPerformed(evt);
            }
        });

        jbtnNotaDebitoImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNotaDebitoImprimir.setText("Imprimir");
        jbtnNotaDebitoImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNotaDebitoImprimirActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Buscar:");

        jtxtNDebitoBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtNDebitoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtNDebitoBuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jpnlNotasDebitoLayout = new javax.swing.GroupLayout(jpnlNotasDebito);
        jpnlNotasDebito.setLayout(jpnlNotasDebitoLayout);
        jpnlNotasDebitoLayout.setHorizontalGroup(
            jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlNotasDebitoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlNotasDebitoLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnNotaDebitoImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jbtnNotaDebitoNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jpnlNotasDebitoLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtNDebitoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpnlNotasDebitoLayout.setVerticalGroup(
            jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlNotasDebitoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxtNDebitoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlNotasDebitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlNotasDebitoLayout.createSequentialGroup()
                        .addComponent(jbtnNotaDebitoNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnNotaDebitoImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("N. Débito", jpnlNotasDebito);

        jpnlGuiasRemision.setBackground(new java.awt.Color(141, 170, 235));

        jtblGRemision.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblGRemision.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "G. Remisión", "Cliente", "Fecha", "Moneda", "IGV", "Importe T."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblGRemision.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jtblGRemision);
        if (jtblGRemision.getColumnModel().getColumnCount() > 0) {
            jtblGRemision.getColumnModel().getColumn(0).setPreferredWidth(80);
            jtblGRemision.getColumnModel().getColumn(1).setPreferredWidth(300);
            jtblGRemision.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtblGRemision.getColumnModel().getColumn(3).setPreferredWidth(50);
            jtblGRemision.getColumnModel().getColumn(4).setPreferredWidth(80);
            jtblGRemision.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        jbtnGuiaRemisionNueva.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnGuiaRemisionNueva.setText("Nuevo");
        jbtnGuiaRemisionNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuiaRemisionNuevaActionPerformed(evt);
            }
        });

        jbtnImprimir1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnImprimir1.setText("Imprimir");
        jbtnImprimir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnImprimir1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Buscar:");

        jtxtFacturaBuscar1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtFacturaBuscar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtFacturaBuscar1KeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel7.setText("P R O X I M A M E N T E   C:");

        javax.swing.GroupLayout jpnlGuiasRemisionLayout = new javax.swing.GroupLayout(jpnlGuiasRemision);
        jpnlGuiasRemision.setLayout(jpnlGuiasRemisionLayout);
        jpnlGuiasRemisionLayout.setHorizontalGroup(
            jpnlGuiasRemisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlGuiasRemisionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlGuiasRemisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlGuiasRemisionLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtFacturaBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlGuiasRemisionLayout.createSequentialGroup()
                        .addGroup(jpnlGuiasRemisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlGuiasRemisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnGuiaRemisionNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnImprimir1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jpnlGuiasRemisionLayout.setVerticalGroup(
            jpnlGuiasRemisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlGuiasRemisionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlGuiasRemisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtxtFacturaBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlGuiasRemisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlGuiasRemisionLayout.createSequentialGroup()
                        .addComponent(jbtnGuiaRemisionNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnImprimir1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("G. Remisión", jpnlGuiasRemision);

        jpnlBajas.setBackground(new java.awt.Color(141, 170, 235));

        jtblBaja.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
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

        jbtnBajaImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnBajaImprimir.setText("Imprimir");
        jbtnBajaImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBajaImprimirActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Buscar:");

        jtxtNDebitoBuscar1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtNDebitoBuscar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtNDebitoBuscar1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jpnlBajasLayout = new javax.swing.GroupLayout(jpnlBajas);
        jpnlBajas.setLayout(jpnlBajasLayout);
        jpnlBajasLayout.setHorizontalGroup(
            jpnlBajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBajasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlBajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlBajasLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnlBajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnBajaImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jbtnFacturaNueva1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jpnlBajasLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtNDebitoBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpnlBajasLayout.setVerticalGroup(
            jpnlBajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBajasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlBajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtxtNDebitoBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnlBajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlBajasLayout.createSequentialGroup()
                        .addComponent(jbtnFacturaNueva1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBajaImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlblComprobantesElectronicos)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1))
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

    private void jbtnFacturaNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFacturaNuevaActionPerformed
        JPanelFacturaNueva jpfn = new JPanelFacturaNueva();
        Metodos.cambiarPanel(jpfn);
    }//GEN-LAST:event_jbtnFacturaNuevaActionPerformed

    private void jbtnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnImprimirActionPerformed
        int fila = jtblFactura.getSelectedRow();
        if (fila != -1) {
            String id = jtblFactura.getValueAt(fila, 0).toString();
            try {
                String hash = Metodos.getHash(Rutas.getRutaHash("01", id));
                Factura.registrarHash(id, hash);
                Factura.crearQR(id, hash);
                Factura.crearPDF(id);
            } catch (Exception e) {
                System.out.println("Genere el XML primero.\n" + e);
                Metodos.mensajeAlerta("Genere el XML primero.");
            }
        } else {
            Metodos.mensajeAlerta("Seleccione una factura.");
        }
    }//GEN-LAST:event_jbtnImprimirActionPerformed

    private void jbtnFacturaNueva1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFacturaNueva1ActionPerformed
        JPanelBajaNueva jpbn = new JPanelBajaNueva();
        Metodos.cambiarPanel(jpbn);
    }//GEN-LAST:event_jbtnFacturaNueva1ActionPerformed

    private void jbtnBajaImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBajaImprimirActionPerformed
        int fila = jtblBaja.getSelectedRow();
        if (fila != -1) {
            String id = jtblBaja.getValueAt(fila, 3).toString();
            try {
                Baja.crearPDF(id);
            } catch (Exception e) {
                System.out.println("Error.\n" + e);
                Metodos.mensajeAlerta("Error." +e);
            }
        } else {
            Metodos.mensajeAlerta("Seleccione una baja.");
        }
    }//GEN-LAST:event_jbtnBajaImprimirActionPerformed

    private void jbtnBoletaNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBoletaNuevaActionPerformed
        JPanelBoletaNueva jpbn = new JPanelBoletaNueva();
        Metodos.cambiarPanel(jpbn);
    }//GEN-LAST:event_jbtnBoletaNuevaActionPerformed

    private void jbtnNotaCreditoNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaCreditoNuevaActionPerformed
        JPanelNotaCreditoNueva jpncn = new JPanelNotaCreditoNueva();
        Metodos.cambiarPanel(jpncn);
    }//GEN-LAST:event_jbtnNotaCreditoNuevaActionPerformed

    private void jbtnNotaCreditoImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaCreditoImprimirActionPerformed
        int fila = jtblNotaCredito.getSelectedRow();
        if (fila != -1) {
            String id = jtblNotaCredito.getValueAt(fila, 0).toString();
            try {
                String hash = Metodos.getHash(Rutas.getRutaHash("07", id));
                NotaCredito.registrarHash(id, hash);
                NotaCredito.crearQR(id, hash);
                NotaCredito.crearPDF(id);
            } catch (Exception e) {
                System.out.println("Genere el XML primero.\n" + e);
                Metodos.mensajeAlerta("Genere el XML primero.");
            }
        } else {
            Metodos.mensajeAlerta("Seleccione una nota de crédito.");
        }
    }//GEN-LAST:event_jbtnNotaCreditoImprimirActionPerformed

    private void jbtnNotaDebitoNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaDebitoNuevaActionPerformed
        JPanelNotaDebitoNueva jpndn = new JPanelNotaDebitoNueva();
        Metodos.cambiarPanel(jpndn);
    }//GEN-LAST:event_jbtnNotaDebitoNuevaActionPerformed

    private void jbtnNotaDebitoImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNotaDebitoImprimirActionPerformed
        int fila = jtblNotaDebito.getSelectedRow();
        if (fila != -1) {
            String id = jtblNotaDebito.getValueAt(fila, 0).toString();
            try {
                String hash = Metodos.getHash(Rutas.getRutaHash("08", id));
                NotaDebito.registrarHash(id, hash);
                NotaDebito.crearQR(id, hash);
                NotaDebito.crearPDF(id);
            } catch (Exception e) {
                System.out.println("Genere el XML primero.\n" + e);
                Metodos.mensajeAlerta("Genere el XML primero.");
            }
        } else {
            Metodos.mensajeAlerta("Seleccione una nota de débito.");
        }
    }//GEN-LAST:event_jbtnNotaDebitoImprimirActionPerformed

    private void jtxtFacturaBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtFacturaBuscarKeyTyped
        // mandamos 2 columnaS, nombre/rz y n doc
        Metodos.filtrarFactura(jtxtFacturaBuscar, 0, 1, dtmFacturas, jtblFactura);
    }//GEN-LAST:event_jtxtFacturaBuscarKeyTyped

    private void jbtnBoletaImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBoletaImprimirActionPerformed
        int fila = jtblBoleta.getSelectedRow();
        if (fila != -1) {
            String id = jtblBoleta.getValueAt(fila, 0).toString();
            try {
                String hash = Metodos.getHash(Rutas.getRutaHash("03", id));
                Boleta.registrarHash(id, hash);
                Boleta.crearQR(id, hash);
                Boleta.crearPDF(id);
            } catch (Exception e) {
                System.out.println("Genere el XML primero.\n" + e);
                Metodos.mensajeAlerta("Genere el XML primero.");
            }
        } else {
            Metodos.mensajeAlerta("Seleccione una boleta.");
        }
    }//GEN-LAST:event_jbtnBoletaImprimirActionPerformed

    private void jtxtBoletaBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBoletaBuscarKeyTyped
        Metodos.filtrarBoleta(jtxtBoletaBuscar, 0, 1, dtmBoletas, jtblBoleta);
    }//GEN-LAST:event_jtxtBoletaBuscarKeyTyped

    private void jtxtNCreditoBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtNCreditoBuscarKeyTyped
        // mandamos 4 columnaS (nota, cliente, comprobante, motivo)
        Metodos.filtrarNotaCredito(jtxtNCreditoBuscar, 0, 1,3,4,dtmNotasCredito, jtblNotaCredito);
    }//GEN-LAST:event_jtxtNCreditoBuscarKeyTyped

    private void jtxtNDebitoBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtNDebitoBuscarKeyTyped
        // mandamos 4 columnaS (nota, cliente, comprobante, motivo)
        Metodos.filtrarNotaCredito(jtxtNDebitoBuscar, 0, 1,3,4,dtmNotasDebito, jtblNotaDebito);
    }//GEN-LAST:event_jtxtNDebitoBuscarKeyTyped

    private void jtxtNDebitoBuscar1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtNDebitoBuscar1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNDebitoBuscar1KeyTyped

    private void jbtnGuiaRemisionNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuiaRemisionNuevaActionPerformed
//        JPanelGuiaRemisionNueva jpgrn = new JPanelGuiaRemisionNueva();
//        Metodos.cambiarPanel(jpgrn);
    }//GEN-LAST:event_jbtnGuiaRemisionNuevaActionPerformed

    private void jbtnImprimir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnImprimir1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnImprimir1ActionPerformed

    private void jtxtFacturaBuscar1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtFacturaBuscar1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtFacturaBuscar1KeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnBajaImprimir;
    private javax.swing.JButton jbtnBoletaImprimir;
    private javax.swing.JButton jbtnBoletaNueva;
    private javax.swing.JButton jbtnFacturaNueva;
    private javax.swing.JButton jbtnFacturaNueva1;
    private javax.swing.JButton jbtnGuiaRemisionNueva;
    private javax.swing.JButton jbtnImprimir;
    private javax.swing.JButton jbtnImprimir1;
    private javax.swing.JButton jbtnNotaCreditoImprimir;
    private javax.swing.JButton jbtnNotaCreditoNueva;
    private javax.swing.JButton jbtnNotaDebitoImprimir;
    private javax.swing.JButton jbtnNotaDebitoNueva;
    private javax.swing.JLabel jlblComprobantesElectronicos;
    private javax.swing.JPanel jpnlBajas;
    private javax.swing.JPanel jpnlBoletas;
    private javax.swing.JPanel jpnlFacturas;
    private javax.swing.JPanel jpnlGuiasRemision;
    private javax.swing.JPanel jpnlNotasCredito;
    private javax.swing.JPanel jpnlNotasDebito;
    private javax.swing.JTable jtblBaja;
    private javax.swing.JTable jtblBoleta;
    private javax.swing.JTable jtblFactura;
    private javax.swing.JTable jtblGRemision;
    private javax.swing.JTable jtblNotaCredito;
    private javax.swing.JTable jtblNotaDebito;
    private javax.swing.JTextField jtxtBoletaBuscar;
    private javax.swing.JTextField jtxtFacturaBuscar;
    private javax.swing.JTextField jtxtFacturaBuscar1;
    private javax.swing.JTextField jtxtNCreditoBuscar;
    private javax.swing.JTextField jtxtNDebitoBuscar;
    private javax.swing.JTextField jtxtNDebitoBuscar1;
    // End of variables declaration//GEN-END:variables
}

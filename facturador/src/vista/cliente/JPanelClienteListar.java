package vista.cliente;

import controlador.Metodos;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;

public class JPanelClienteListar extends javax.swing.JPanel {

    static ResultSet rs;
    DefaultTableModel dtmClientes;

    public JPanelClienteListar() {
        initComponents();
        cargarClientes();
    }

    private void cargarClientes() {
        try {
            dtmClientes = (DefaultTableModel) jtblClientes.getModel();
            dtmClientes.setRowCount(0);
            rs = Cliente.Consulta("select * \n"
                    + "from cliente \n"
                    + "order by nombreRazonSocial;");
            String fila[] = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("tipoDocumento");
                fila[2] = rs.getString("numeroDocumento");
                fila[3] = rs.getString("nombreRazonSocial");
                fila[4] = rs.getString("direccion");
                dtmClientes.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando clientes: \n" + e);
            Metodos.mensajeError("Error cargando clientes: \n" + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblClientes = new javax.swing.JTable();
        jbtnNuevo = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jtxtBuscar = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 192, 192));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Lista de Clientes");

        jtblClientes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tipo doc.", "N° doc.", "Nombre / Razón social", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblClientes.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtblClientes);
        if (jtblClientes.getColumnModel().getColumnCount() > 0) {
            jtblClientes.getColumnModel().getColumn(0).setMinWidth(0);
            jtblClientes.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtblClientes.getColumnModel().getColumn(0).setMaxWidth(0);
            jtblClientes.getColumnModel().getColumn(1).setPreferredWidth(150);
            jtblClientes.getColumnModel().getColumn(2).setPreferredWidth(200);
            jtblClientes.getColumnModel().getColumn(3).setPreferredWidth(500);
            jtblClientes.getColumnModel().getColumn(4).setPreferredWidth(700);
        }

        jbtnNuevo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnNuevo.setText("Nuevo");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });

        jbtnEditar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnEditar.setText("Editar");
        jbtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Buscar:");

        jtxtBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtBuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 395, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        JFrame FormularioPrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialogClienteNuevo jdcn = new JDialogClienteNuevo(FormularioPrincipal, true);
        jdcn.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                JPanelClienteListar jpcl = new JPanelClienteListar();
                Metodos.cambiarPanel(jpcl);
            }
        });
        jdcn.setVisible(true);
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
        int fila = jtblClientes.getSelectedRow();
        if (fila == -1) {
            Metodos.mensajeAlerta("Seleccione un cliente.");
        } else if (fila != -1) {
            String id = jtblClientes.getValueAt(fila, 0).toString();
            JFrame FormularioPrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);
            JDialogClienteEditar jdce = new JDialogClienteEditar(FormularioPrincipal, true, id);
            jdce.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    JPanelClienteListar jpcl = new JPanelClienteListar();
                    Metodos.cambiarPanel(jpcl);
                }
            });
            jdce.setVisible(true);
        }
    }//GEN-LAST:event_jbtnEditarActionPerformed

    private void jtxtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBuscarKeyTyped
        // mandamos 2 columnaS, nombre/rz y n doc
        Metodos.filtrarCliente(jtxtBuscar, 2, 3, dtmClientes, jtblClientes);
    }//GEN-LAST:event_jtxtBuscarKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JTable jtblClientes;
    private javax.swing.JTextField jtxtBuscar;
    // End of variables declaration//GEN-END:variables
}

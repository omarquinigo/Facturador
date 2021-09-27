package vista.producto;

import controlador.Metodos;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;

public class JPanelProductoListar extends javax.swing.JPanel {

    static ResultSet rs;
    DefaultTableModel dtmProductos;
    
    public static String rol;

    public JPanelProductoListar(String user_rol) {
        initComponents();
        rol = user_rol;
        cargarRol();
        cargarProductos();
    }
    
    private void cargarRol(){
        if(rol.equalsIgnoreCase("Usuario")){
            jbtnNuevo.setEnabled(false);
            jbtnEditar.setEnabled(false);
        }
    }

    private void cargarProductos() {
        try {
            dtmProductos = (DefaultTableModel) jtblProductos.getModel();
            dtmProductos.setRowCount(0);
            rs = Producto.Consulta("select * \n"
                    + "from producto \n"
                    + "order by descripcion;");
            String fila[] = new String[6];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("codigo");
                fila[2] = rs.getString("descripcion");
                fila[3] = rs.getString("precio1");
                fila[4] = rs.getString("precio2");
                fila[5] = rs.getString("estado");
                dtmProductos.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando productos: \n" + e);
            Metodos.MensajeError("Error cargando productos: \n" + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblProductos = new javax.swing.JTable();
        jbtnNuevo = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();
        jtxtBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 192, 192));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Lista de Productos:");

        jtblProductos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Código", "Descripción", "Precio 1", "Precio 2", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblProductos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtblProductos);
        if (jtblProductos.getColumnModel().getColumnCount() > 0) {
            jtblProductos.getColumnModel().getColumn(0).setMinWidth(0);
            jtblProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtblProductos.getColumnModel().getColumn(0).setMaxWidth(0);
            jtblProductos.getColumnModel().getColumn(1).setPreferredWidth(50);
            jtblProductos.getColumnModel().getColumn(2).setPreferredWidth(200);
            jtblProductos.getColumnModel().getColumn(3).setPreferredWidth(50);
            jtblProductos.getColumnModel().getColumn(4).setPreferredWidth(50);
            jtblProductos.getColumnModel().getColumn(5).setPreferredWidth(50);
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

        jtxtBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtBuscarKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Buscar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 878, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
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
        JDialogProductoNuevo jdpn = new JDialogProductoNuevo(FormularioPrincipal, true);
        jdpn.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                JPanelProductoListar jppl = new JPanelProductoListar(rol);
                Metodos.CambiarPanel(jppl);
            }
        });
        jdpn.setVisible(true);

    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
        int fila = jtblProductos.getSelectedRow();
        if (fila == -1) {
            Metodos.MensajeAlerta("Seleccione un producto.");
        } else if (fila != -1) {
            String id = jtblProductos.getValueAt(fila, 0).toString();
            JFrame FormularioPrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);
            JDialogProductoEditar jdpe = new JDialogProductoEditar(FormularioPrincipal, true, id);
            jdpe.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    JPanelProductoListar jppl = new JPanelProductoListar(rol);
                    Metodos.CambiarPanel(jppl);
                }
            });
            jdpe.setVisible(true);
        }
    }//GEN-LAST:event_jbtnEditarActionPerformed

    private void jtxtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBuscarKeyTyped
        // mandamos 2 columnaS, codigo y descripcion
        Metodos.FiltrarProducto(jtxtBuscar, 1, 2, dtmProductos, jtblProductos);
    }//GEN-LAST:event_jtxtBuscarKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JTable jtblProductos;
    private javax.swing.JTextField jtxtBuscar;
    // End of variables declaration//GEN-END:variables
}

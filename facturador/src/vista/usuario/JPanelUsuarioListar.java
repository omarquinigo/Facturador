package vista.usuario;

import controlador.Metodos;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;

public class JPanelUsuarioListar extends javax.swing.JPanel {

    static ResultSet rs;
    DefaultTableModel dtmUsuarios;

    public JPanelUsuarioListar() {
        initComponents();
        CargarUsuarios();
    }

    void CargarUsuarios() {
        try {
            dtmUsuarios = (DefaultTableModel) jtblUsuarios.getModel();
            dtmUsuarios.setRowCount(0);
            rs = Usuario.Consulta("select * \n"
                    + "from usuario \n"
                    + "order by id;");
            String fila[] = new String[3];
            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("usuario");
                fila[2] = rs.getString("rol");
                dtmUsuarios.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error cargando usuarios: \n" + e);
            Metodos.mensajeError("Error cargando usuarios: \n" + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblUsuarios = new javax.swing.JTable();
        jbtnNuevo = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 192, 192));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Usuarios");

        jtblUsuarios.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Usuario", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblUsuarios.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtblUsuarios);
        if (jtblUsuarios.getColumnModel().getColumnCount() > 0) {
            jtblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(250);
            jtblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(2000);
            jtblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(2000);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(459, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(282, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        JFrame FormularioPrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialogUsuarioNuevo jdun = new JDialogUsuarioNuevo(FormularioPrincipal, true);
        jdun.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                JPanelUsuarioListar jpul = new JPanelUsuarioListar();
                Metodos.cambiarPanel(jpul);
            }
        });
        jdun.setVisible(true);
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
        int fila = jtblUsuarios.getSelectedRow();
        if (fila == -1) {
            Metodos.mensajeAlerta("Seleccione un cliente.");
        } else if (fila != -1) {
            String id = jtblUsuarios.getValueAt(fila, 0).toString();
            JFrame FormularioPrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);
            JDialogUsuarioEditar jdue = new JDialogUsuarioEditar(FormularioPrincipal, true, id);
            jdue.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    JPanelUsuarioListar jpcl = new JPanelUsuarioListar();
                    Metodos.cambiarPanel(jpcl);
                }
            });
            jdue.setVisible(true);
        }
    }//GEN-LAST:event_jbtnEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JTable jtblUsuarios;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Project_LendMe;

/**
 *
 * @author Anja
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        top_penal = new javax.swing.JPanel();
        side_penal = new javax.swing.JPanel();
        jnew_rentals = new javax.swing.JPanel();
        l_new_rentals = new java.awt.Label();
        jreturn = new javax.swing.JPanel();
        l_return = new java.awt.Label();
        jarchive = new javax.swing.JPanel();
        l_archive = new java.awt.Label();
        jnew_device = new javax.swing.JPanel();
        l_new_device = new java.awt.Label();
        jinventory = new javax.swing.JPanel();
        l_inventory = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout());

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        background.setPreferredSize(new java.awt.Dimension(82, 20));
        background.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout top_penalLayout = new javax.swing.GroupLayout(top_penal);
        top_penal.setLayout(top_penalLayout);
        top_penalLayout.setHorizontalGroup(
            top_penalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        top_penalLayout.setVerticalGroup(
            top_penalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );

        background.add(top_penal, java.awt.BorderLayout.PAGE_START);

        jnew_rentals.setBackground(new java.awt.Color(51, 51, 51));
        jnew_rentals.setLayout(new java.awt.GridBagLayout());

        l_new_rentals.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        l_new_rentals.setForeground(new java.awt.Color(255, 255, 255));
        l_new_rentals.setText("Neuer Verleih");
        jnew_rentals.add(l_new_rentals, new java.awt.GridBagConstraints());

        jreturn.setBackground(new java.awt.Color(51, 51, 51));
        jreturn.setMinimumSize(new java.awt.Dimension(82, 20));
        jreturn.setLayout(new java.awt.GridBagLayout());

        l_return.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        l_return.setForeground(new java.awt.Color(255, 255, 255));
        l_return.setName(""); // NOI18N
        l_return.setText("Verleihliste/Rückgabe");
        jreturn.add(l_return, new java.awt.GridBagConstraints());

        jarchive.setBackground(new java.awt.Color(51, 51, 51));
        jarchive.setMinimumSize(new java.awt.Dimension(82, 20));
        jarchive.setLayout(new java.awt.GridBagLayout());

        l_archive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        l_archive.setForeground(new java.awt.Color(255, 255, 255));
        l_archive.setText("Archiv");
        jarchive.add(l_archive, new java.awt.GridBagConstraints());

        jnew_device.setBackground(new java.awt.Color(51, 51, 51));
        jnew_device.setLayout(new java.awt.GridBagLayout());

        l_new_device.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        l_new_device.setForeground(new java.awt.Color(255, 255, 255));
        l_new_device.setText("Neues Gerät");
        jnew_device.add(l_new_device, new java.awt.GridBagConstraints());

        jinventory.setBackground(new java.awt.Color(51, 51, 51));
        jinventory.setLayout(new java.awt.GridBagLayout());

        l_inventory.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        l_inventory.setForeground(new java.awt.Color(255, 255, 255));
        l_inventory.setText("Inventar");
        jinventory.add(l_inventory, new java.awt.GridBagConstraints());

        javax.swing.GroupLayout side_penalLayout = new javax.swing.GroupLayout(side_penal);
        side_penal.setLayout(side_penalLayout);
        side_penalLayout.setHorizontalGroup(
            side_penalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jarchive, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(jreturn, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(jnew_rentals, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(jnew_device, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(jinventory, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
        );
        side_penalLayout.setVerticalGroup(
            side_penalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(side_penalLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jnew_rentals, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jreturn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jarchive, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jnew_device, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jinventory, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        background.add(side_penal, java.awt.BorderLayout.LINE_START);

        getContentPane().add(background);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JPanel jarchive;
    private javax.swing.JPanel jinventory;
    private javax.swing.JPanel jnew_device;
    private javax.swing.JPanel jnew_rentals;
    private javax.swing.JPanel jreturn;
    private java.awt.Label l_archive;
    private java.awt.Label l_inventory;
    private java.awt.Label l_new_device;
    private java.awt.Label l_new_rentals;
    private java.awt.Label l_return;
    private javax.swing.JPanel side_penal;
    private javax.swing.JPanel top_penal;
    // End of variables declaration//GEN-END:variables
}

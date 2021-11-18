/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Project_LendMe;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

/**
 *
 * @author Anja
 */
public class NewDeviceGUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public NewDeviceGUI() {
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

        toppenal = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        parentpanel = new javax.swing.JPanel();
        sidepanel = new javax.swing.JPanel();
        newrental = new javax.swing.JButton();
        rentallist = new javax.swing.JButton();
        archive = new javax.swing.JButton();
        inventory = new javax.swing.JButton();
        startpage = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));

        toppenal.setBackground(new java.awt.Color(87, 121, 50));
        toppenal.setPreferredSize(new java.awt.Dimension(802, 110));

        jTextField1.setBackground(new java.awt.Color(220, 229, 211));
        jTextField1.setText("Suche");
        jTextField1.setToolTipText("Suche");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout toppenalLayout = new javax.swing.GroupLayout(toppenal);
        toppenal.setLayout(toppenalLayout);
        toppenalLayout.setHorizontalGroup(
            toppenalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toppenalLayout.createSequentialGroup()
                .addContainerGap(249, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );
        toppenalLayout.setVerticalGroup(
            toppenalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toppenalLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        getContentPane().add(toppenal, java.awt.BorderLayout.PAGE_START);

        parentpanel.setBackground(new java.awt.Color(255, 255, 255));
        parentpanel.setPreferredSize(new java.awt.Dimension(802, 800));
        parentpanel.setLayout(new java.awt.BorderLayout());

        sidepanel.setBackground(new java.awt.Color(87, 121, 50));
        sidepanel.setMinimumSize(new java.awt.Dimension(100, 500));
        sidepanel.setPreferredSize(new java.awt.Dimension(250, 300));
        sidepanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        newrental.setBackground(new java.awt.Color(55, 57, 58));
        newrental.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        newrental.setForeground(new java.awt.Color(255, 255, 255));
        newrental.setIcon(new javax.swing.ImageIcon("C:\\Users\\linda\\OneDrive\\Dokumente\\NetBeansProjects\\LendMe\\images\\add-button-12018.png")); // NOI18N
        newrental.setText("Neuer Verleih");
        newrental.setBorderPainted(false);
        newrental.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        newrental.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        newrental.setIconTextGap(15);
        newrental.setPreferredSize(new java.awt.Dimension(250, 70));
        newrental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newrentalActionPerformed(evt);
            }
        });
        sidepanel.add(newrental);
        newrental.getAccessibleContext().setAccessibleDescription("");

        rentallist.setBackground(new java.awt.Color(55, 57, 58));
        rentallist.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rentallist.setForeground(new java.awt.Color(255, 255, 255));
        rentallist.setIcon(new javax.swing.ImageIcon("C:\\Users\\linda\\OneDrive\\Dokumente\\NetBeansProjects\\LendMe\\images\\opened-book-3159.png")); // NOI18N
        rentallist.setText("Verleihliste");
        rentallist.setBorderPainted(false);
        rentallist.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        rentallist.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rentallist.setIconTextGap(15);
        rentallist.setPreferredSize(new java.awt.Dimension(250, 70));
        rentallist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentallistActionPerformed(evt);
            }
        });
        sidepanel.add(rentallist);

        archive.setBackground(new java.awt.Color(55, 57, 58));
        archive.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        archive.setForeground(new java.awt.Color(255, 255, 255));
        archive.setIcon(new javax.swing.ImageIcon("C:\\Users\\linda\\OneDrive\\Dokumente\\NetBeansProjects\\LendMe\\images\\open-folder-11477.png")); // NOI18N
        archive.setText("Archiv");
        archive.setToolTipText("");
        archive.setActionCommand("Verleihliste");
        archive.setBorderPainted(false);
        archive.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        archive.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        archive.setIconTextGap(15);
        archive.setPreferredSize(new java.awt.Dimension(250, 70));
        archive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveActionPerformed(evt);
            }
        });
        sidepanel.add(archive);

        inventory.setBackground(new java.awt.Color(55, 57, 58));
        inventory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        inventory.setForeground(new java.awt.Color(255, 255, 255));
        inventory.setIcon(new javax.swing.ImageIcon("C:\\Users\\linda\\OneDrive\\Dokumente\\NetBeansProjects\\LendMe\\images\\list-6225.png")); // NOI18N
        inventory.setText("Inventar");
        inventory.setToolTipText("");
        inventory.setActionCommand("Verleihliste");
        inventory.setBorderPainted(false);
        inventory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        inventory.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        inventory.setIconTextGap(15);
        inventory.setPreferredSize(new java.awt.Dimension(250, 70));
        inventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryActionPerformed(evt);
            }
        });
        sidepanel.add(inventory);

        parentpanel.add(sidepanel, java.awt.BorderLayout.LINE_START);
        sidepanel.getAccessibleContext().setAccessibleName("");

        startpage.setBackground(new java.awt.Color(220, 229, 211));
        startpage.setLayout(new java.awt.GridBagLayout());
        parentpanel.add(startpage, java.awt.BorderLayout.CENTER);

        getContentPane().add(parentpanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void newrentalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newrentalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newrentalActionPerformed

    private void rentallistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentallistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rentallistActionPerformed

    private void archiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_archiveActionPerformed

    private void inventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inventoryActionPerformed

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
            java.util.logging.Logger.getLogger(NewDeviceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewDeviceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewDeviceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewDeviceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewDeviceGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton archive;
    private javax.swing.JButton inventory;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton newrental;
    private javax.swing.JPanel parentpanel;
    private javax.swing.JButton rentallist;
    private javax.swing.JPanel sidepanel;
    private javax.swing.JPanel startpage;
    private javax.swing.JPanel toppenal;
    // End of variables declaration//GEN-END:variables
}

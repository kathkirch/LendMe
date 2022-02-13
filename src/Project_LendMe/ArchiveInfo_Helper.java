/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Helper Class for ArchiveInfo_Panel 
 * 
 * @author Katharina
 */
public class ArchiveInfo_Helper {
    
    JPanel panel;
    JTextField productname;
    JTextField manufacturer;
    JTextField inventoryNumber;
    JTextField userID;
    JTextField userName;
    JTextField userPhone;
    JTextField userEmail;
    JTextField adminID;
    JTextField adminName;
    JTextField rentalDate;
    JTextField returnDate;
    JTextArea notes;
    
    JButton cancel;
    
    JLayeredPane lp;
    JPanel archive_panel;
    
    static final DatabaseHelper dbH = new DatabaseHelper();

    public ArchiveInfo_Helper(JPanel panel, JLayeredPane lp, JPanel archive_panel) {
        this.panel = panel;
        
        this.productname = (JTextField) panel.getComponent(15);
        this.manufacturer = (JTextField) panel.getComponent(16);
        this.inventoryNumber = (JTextField) panel.getComponent(17);
        this.userID = (JTextField) panel.getComponent(18);
        this.userName = (JTextField) panel.getComponent(19);
        this.userPhone = (JTextField) panel.getComponent(20);
        this.userEmail = (JTextField) panel.getComponent(21);
        this.adminID = (JTextField) panel.getComponent(23);
        this.adminName = (JTextField) panel.getComponent(26);
        this.rentalDate = (JTextField) panel.getComponent(24);
        this.returnDate = (JTextField) panel.getComponent(25);
        this.notes = (JTextArea) panel.getComponent(22);
        this.cancel = (JButton) panel.getComponent(1);
        this.lp = lp;
        this.archive_panel = archive_panel;
    }
    
    /**
     *method to make all the textfield an textarea 
     * in this component not editable
     */
    public void notEditable () {
        
        Component [] comps = panel.getComponents();
        for (Component field : comps){
            if (field instanceof JTextField) {
                ((JTextField) field).setEditable(false);
            }
            if (field instanceof JTextArea) {
                ((JTextArea) field).setEditable(false);
            }
        }
    }
    
    /**
     * method to set the data from database in JTextFields 
     */
    public void showData () {
        
        Users u = dbH.getUserByID(String.valueOf(Archiv_Helper.USERID_ARCHIVE));
        
        Rentals r = dbH.getRentalByID(Archiv_Helper.RENTALID_ARCHIVE);
        
        Devices d = dbH.getDeviceByID(String.valueOf(Archiv_Helper.INVNUMB_ARCHIVE));
        
        int admin_ID = d.getAdminID();
        
        productname.setText(d.getProductName());
        manufacturer.setText(d.getManufacturer());
        inventoryNumber.setText(String.valueOf(d.getInventoryNumber()));
        userID.setText(String.valueOf(u.getUserID()));
        userName.setText(u.getUserFirstName() + " " + u.getUserLastName());
        userPhone.setText(u.getUserPhone());
        userEmail.setText(u.getUserEmail());
        adminID.setText(String.valueOf(admin_ID));
        adminName.setText(dbH.getAdminNameByID(String.valueOf(admin_ID)));
        rentalDate.setText(r.getRentalDate().toString());
        returnDate.setText(r.getReturnDate().toString());
        notes.setText(d.getNotes());
        
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        
    }
    
    /**
     * adds an Listiener for the 'Abbrechen' Button to go back to the 
     * archive_panel
     */
    public void cancel () {
        cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                backToArchive();
            }
        });
    }
    
    /**
     * method to repaint the panel 
     */
    public void backToArchive () {
    
        lp.removeAll();
        lp.add(archive_panel);
        lp.repaint();
        lp.revalidate();
        
    }
}

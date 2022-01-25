/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 *
 * @author Katharina, bstra
 */
public class InventoryInfo_Helper {
    
    private JPanel panel;
    private JLayeredPane lp;
    private JPanel inventory_panel;
    private JTextField invNumber;
    private JTextField productName;
    private JTextField manufacturer;
    private JTextField imei;
    private JTextField location;
    private JTextField acqValue;
    private JTextField acqDate;
    private JTextArea notes;
    private JTextField adminId;
    private JTextField adminName;
    private JButton back;
    
    private int adminIDtoCheck;
    private long invNoToCheck;
    
    private static final DatabaseHelper dbh = new DatabaseHelper();

    public InventoryInfo_Helper(JPanel panel, JLayeredPane lp, JPanel inventory_panel, 
            JTextField invNumber, JTextField productName, JTextField manufacturer, 
            JTextField imei, JTextField location, JTextField acqValue, 
            JTextField acqDate, JTextArea notes, JTextField adminId, 
            JTextField adminName, JButton back, int adminIDtoCheck, 
            long invNoToCheck) {
        
        this.panel = panel;
        this.lp = lp;
        this.inventory_panel = inventory_panel;
        this.invNumber = invNumber;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.imei = imei;
        this.location = location;
        this.acqValue = acqValue;
        this.acqDate = acqDate;
        this.notes = notes;
        this.adminId = adminId;
        this.adminName = adminName;
        this.back = back;
        this.adminIDtoCheck = adminIDtoCheck;
        this.invNoToCheck = invNoToCheck;
    }
    
    /**
     *cycle through all Components, set editable on JTextFields and JTextArea to false
     */
    public void notEditable () {
        
        Component [] comps = panel.getComponents();
        for (Component field : comps){
            if (field instanceof JTextField) {
                ((JTextField) field).setEditable(false);
            } 
        }
    }
    
    /**
     * 
     * @param adminIDtoCheck get selected adminID to query DB
     * @param invNoToCheck  get selected inventoryNumber to query DB
     */
    public void getValuesToCheck (int adminIDtoCheck, long invNoToCheck) {
        this.adminIDtoCheck = adminIDtoCheck;
        this.invNoToCheck = invNoToCheck;
    }
    
    /**
     * method to set the data from database in JTextFields 
     */
    public void showData () {
        
        Devices d = dbh.getDeviceByID(String.valueOf(invNoToCheck));
        
        invNumber.setText(String.valueOf(d.getInventoryNumber()));
        productName.setText(d.getProductName());
        manufacturer.setText(d.getManufacturer());
        imei.setText(d.getImei());
        location.setText(d.getLocation());
        acqValue.setText(String.valueOf(d.getAquisitionValue()));
        acqDate.setText(d.getAquistionDate().toString());
        notes.setText(d.getNotes());
                
        adminId.setText(String.valueOf(adminIDtoCheck));
        adminName.setText(dbh.getAdminNameByID(String.valueOf(adminIDtoCheck)));
        
    }
    
    /**
     * adds an Listener for the 'Zurück' Button to go back to the 
     * inventory_panel
     */
    public void back () {
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                backToInventory();
            }
        });
    }
    
    /**
     * method to repaint the panel 
     */
    public void backToInventory () {
    
        lp.removeAll();
        lp.add(inventory_panel);
        lp.repaint();
        lp.revalidate();
        
    }
}

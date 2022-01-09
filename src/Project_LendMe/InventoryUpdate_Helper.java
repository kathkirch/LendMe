/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import com.raven.datechooser.DateChooser;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author bstra
 */
public class InventoryUpdate_Helper {
    
    JTextField inventoryNumber;
    JTextField manufacturer;
    JTextField productname;
    JTextArea notes;
    JTextField location;
    JTextField imei;
    JTextField acquisitionValue;
    DateChooser acquisitionDate;
    JComboBox administrator;
    JButton save;
    JButton cancel;

    public InventoryUpdate_Helper(JTextField inventoryNumber, JTextField manufacturer, JTextField productname, JTextArea notes, JTextField location, JTextField imei, JTextField acquisitionValue, DateChooser acquisitionDate, JComboBox administrator, JButton save, JButton cancel) {
        this.inventoryNumber = inventoryNumber;
        this.manufacturer = manufacturer;
        this.productname = productname;
        this.notes = notes;
        this.location = location;
        this.imei = imei;
        this.acquisitionValue = acquisitionValue;
        this.acquisitionDate = acquisitionDate;
        this.administrator = administrator;
        this.save = save;
        this.cancel = cancel;
    }
    
    
    
}

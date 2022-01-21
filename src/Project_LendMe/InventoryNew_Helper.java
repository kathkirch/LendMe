/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.SelectedDate;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 * Helper Class handling logic for New-Inventory-Panel
 *
 * @author bstra
 */
public class InventoryNew_Helper {

    //declaring relevant user-input fields as class-variables
    private JTextField productname;
    private JTextField manufacturer;
    private JTextField inventoryNumber;
    private JTextField imei;
    private JTextField location;
    private JTextArea notes;
    private JComboBox admin;
    private JTextField acqValue;
    private DateChooser acqDate;
    private JButton save;
    private JButton clear;

    //instantiating our trusty DBHelper
    DatabaseHelper dbh = new DatabaseHelper();

    //constructor; initializes class-variables
    public InventoryNew_Helper(JTextField productname, JTextField manufacturer,
            JTextField inventoryNumber, JTextField imei, JTextField location,
            JTextArea notes, JComboBox admin, JTextField acqValue, DateChooser acqDate,
            JButton save, JButton clear) {

        this.productname = productname;
        this.manufacturer = manufacturer;
        this.inventoryNumber = inventoryNumber;
        this.imei = imei;
        this.location = location;
        this.notes = notes;
        this.admin = admin;
        this.acqValue = acqValue;
        this.acqDate = acqDate;
        this.save = save;
        this.clear = clear;
    }

    /**
     * Sets Values for Drop-Down Selection of Admin-ComboBox
     */
    public void fillCbAdmin() {

        //query DB for all existing admins
        List<String> adminIDs = dbh.getAdminIDs();

        //set Model, make it searchable by user, set first entry to blank
        admin.setModel(new DefaultComboBoxModel<>(adminIDs.toArray((new String[0]))));
        admin.setEditable(false);
        admin.setSelectedIndex(0);
        //allow autocomplete for valid user input
        AutoCompleteDecorator.decorate(admin);
    }

    /**
     * Initializes Input Verifiers for Textfields verifies if user input matches
     * required Parameters if YES: yield focus as usual if NO: do not yield
     * focus, display error message
     */
    public void setInputVerifiers() {

        // query DB for all existing Inventory Numbers and Imeis
        // to ascertain that newly entered value is UNIQUE
        ArrayList<String> invNos = dbh.allInventoryNumbers();
        ArrayList<String> imeis = dbh.allImeiNumbers();
        //instantiate Inventory Input Verifier
        Inventory_Verifier iv = new Inventory_Verifier(productname, manufacturer,
                inventoryNumber, imei, location, admin, acqValue, invNos, imeis);

        //set Acquisiton Value to default to help User with input format
        acqValue.setText("0.0");

        //set up verifiers
        inventoryNumber.setInputVerifier(iv);
        manufacturer.setInputVerifier(iv);
        productname.setInputVerifier(iv);
        location.setInputVerifier(iv);
        acqValue.setInputVerifier(iv);
        imei.setInputVerifier(iv);
    }

    //concatenate String to pass to Insert-Into-Devices Method; call this method
    /**
     * Parse through textfields, concatenate String to query DB, call INSERT
     * INTO method passing String
     */
    public void concatInsertStringCallInsertMethod() {

        //initialize String Array with Values from User Input
        String[] values = new String[9];
        values[0] = inventoryNumber.getText();
        values[1] = manufacturer.getText();
        values[2] = productname.getText();
        values[3] = notes.getText();
        values[4] = location.getText();
        values[5] = admin.getSelectedItem().toString();

        //get Acquisition Value in SQL-Syntax
        values[6] = acqValue.getText();
        values[6] = values[6].replaceAll("[,]", "");

        //get the entered Date as String
        SelectedDate sd = acqDate.getSelectedDate();
        LocalDate acqDate = LocalDate.of(sd.getYear(), sd.getMonth(), sd.getDay());
        values[7] = acqDate.toString();

        //imei as last value in the Array to allow for easy access
        values[8] = imei.getText();

        /*
    fields left blank by user are passed as blank String
    since IMEI is UNIQUE we need to check if it was left blank
    if YES we ignore it in our query, SQL will automatically null it
    if NO we pass the value as usually
         */
        boolean noImei = values[8].isBlank();

        //initialize String to query DB
        String valuesToPass = "";

        //check if Inventory Number was left blank
        //if YES: display error message, set Focus on TF inventory Number
        boolean noInvNo = values[0].isBlank();

        if (noInvNo) {
            JOptionPane.showMessageDialog(null, "Bitte eine eindeutige Inventarnummer eingeben",
                    "Error", 0);
            inventoryNumber.requestFocus();
        } else {
            //no imei, ignore last value of String-Array
            if (noImei) {
                for (int i = 0; i < values.length - 2; i++) {
                    valuesToPass = valuesToPass.concat("'" + values[i] + "', ");
                }
                valuesToPass = valuesToPass.concat("'" + values[values.length - 2] + "'");

                //imei entered, pass last value of String-Array
            } else {
                for (int i = 0; i < values.length - 1; i++) {
                    valuesToPass = valuesToPass.concat("'" + values[i] + "', ");
                }
                valuesToPass = valuesToPass.concat("'" + values[values.length - 1] + "'");
            }

            //pass the concatenated String and the boolean checking if an IMEI was entered
            try {
                dbh.insertNewDevice(valuesToPass, noImei);
                JOptionPane.showMessageDialog(null, "Neuer Datensatz eingefügt", "Success", 1);
            } catch (SQLException ex) {
                Logger.getLogger(InventoryNew_Helper.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Ein Fehler ist beim Datenbankzugriff aufgetreten",
                        "SQL-Error", 0);
                System.out.println(ex);
            }
        }
    }

    //reset all fields in the New Device Panel
    public void resetNewDeviceTextfields() {
        productname.setText("");
        manufacturer.setText("");
        inventoryNumber.setText("");
        imei.setText("");
        location.setText("");
        notes.setText("");
        acqValue.setText("0.0");

        admin.setSelectedIndex(0);

        Date today = new Date();
        acqDate.setSelectedDate(today);
    }
}

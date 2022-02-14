/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.SelectedDate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
 * Helper Class handling logic for Update-Inventory-Panel
 *
 * @author bstra
 */
public class InventoryUpdate_Helper {

    private String invNoToCheck;
    private JTextField inventoryNumber;
    private JTextField manufacturer;
    private JTextField productname;
    private JTextArea notes;
    private JTextField location;
    private JTextField imei;
    private JTextField acquisitionValue;
    private DateChooser acquisitionDate;
    private JComboBox administrator;
    private JButton save;
    private JButton cancel;
    private String[] blub = new String[8];

    //new DatabaseHelper instance to access methods
    private final DatabaseHelper dbh = new DatabaseHelper();

    //Constructor
    public InventoryUpdate_Helper(String invNoToCheck, JTextField inventoryNumber,
            JTextField manufacturer, JTextField productname, JTextArea notes,
            JTextField location, JTextField imei, JTextField acquisitionValue,
            DateChooser acquisitionDate, JComboBox administrator,
            JButton save, JButton cancel) {
        this.invNoToCheck = invNoToCheck;
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

    /**
     * initializes String Array with values from DB query; queries DB with
     * inventory Number of the Row the user had selected
     */
    public void initializeArray() {
        blub = dbh.getDeviceByID2(invNoToCheck);
    }

    /**
     * Populates TextFields with selected Values
     */
    public void fillTFs() {
        
        initializeArray();
        //set Textfields
        inventoryNumber.setText(blub[0]);
        manufacturer.setText(blub[1]);
        productname.setText(blub[2]);
        notes.setText(blub[3]);
        location.setText(blub[4]);
        imei.setText(blub[5]);
        acquisitionValue.setText(blub[6]);

        //set values for administrator Combobox
        administrator.setModel(new DefaultComboBoxModel<>(dbh.getAdminIDs().toArray((new String[0]))));
        administrator.setEditable(false);
        administrator.setSelectedItem(blub[8]);
        AutoCompleteDecorator.decorate(administrator);

        //set Acquisition Date in Datechooser
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(blub[7]);
            acquisitionDate.setSelectedDate(date);

        } catch (ParseException ex) {
            Logger.getLogger(InventoryUpdate_Helper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * set up Key Listners for IMEI and Inventory Number Textfields
     * if user enters a new Imei/InvNo which is invalid, an error
     * message is displayed and focus is not allowed to be lost from the Textfield
     * this method allows the user to press ESC and restore the original value
     * in order to avoid being stuck in the textfield with an invalid value
     */
    public void setKeyListener() {

        // set up Key Listener to allow user to restore initial Value for
        // Inventory Number and IMEI by pressing ESC
        KeyListener kliInvNo = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    inventoryNumber.setText(blub[0]);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    inventoryNumber.setText(blub[0]);
                }
            }

        };

        KeyListener kliImei = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    imei.setText(blub[0]);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    imei.setText(blub[0]);
                }
            }

        };

        //check if KeyListener is already active before initializing
        inventoryNumber.addKeyListener(kliInvNo);
        imei.addKeyListener(kliImei);

    }

    /**
     * Initializes Input Verifiers for Textfields verifies if user input matches
     * required Parameters if YES: yield focus as usual if NO: do not yield
     * focus, display error message
     */
    public void setInputVerifiers() {

        // query DB for all existing Inventory Numbers and Imeis
        // to ascertain that newly entered value is UNIQUE
        // remove existing value from the List, to avoid SQLException
        ArrayList<String> invNos = dbh.allInventoryNumbers();
        invNos.remove(invNoToCheck);
        ArrayList<String> imeis = dbh.allImeiNumbers();
        if (blub[5] != null) {
            if (!blub[5].isBlank()) {
                imeis.remove(blub[5]);
            }
        }

        //instantiate Inventory Input Verifier
        Inventory_Verifier iv = new Inventory_Verifier(productname, manufacturer,
                inventoryNumber, imei, location, administrator, acquisitionValue,
                invNos, imeis);

        //set Input Verifiers
        if (acquisitionValue.getText().isBlank()) {
            acquisitionValue.setText("0.0");
        }

        inventoryNumber.setInputVerifier(iv);
        manufacturer.setInputVerifier(iv);
        productname.setInputVerifier(iv);
        location.setInputVerifier(iv);
        acquisitionValue.setInputVerifier(iv);
        imei.setInputVerifier(iv);
    }

    /**
     * Parses through TextFields and compares new values to existing values
     * builds String and queries DB with updated Value(s)
     */
    public void checkForUpdate() {

        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //helper booleans to determine which query to run
                boolean updateBefore = false;

                //save original InventoryNumber
                //String invNoToUpdate = blub[0];
                //helper Strings
                String queryToPass = "";
                final String separator = ", ";

                /* following if-checks:
                check if value was changed by comparing current value with passed value
                updates boolean to trigger corresponding SQL-query
                updates query to pass to SQL-query
                 */
                if (!inventoryNumber.getText().equals(invNoToCheck)) {
                    updateBefore = true;
                    queryToPass = "inventoryNumber = '" + inventoryNumber.getText() + "'";
                }
                /*
                starting here, additionally check if an entry before was already updated by
                checking boolean updateBefore; if so, concatenate correct query-String
                to pass to SQL-Update-Method
                 */
                if (!manufacturer.getText().equals(blub[1])) {
                    if (updateBefore) {
                        queryToPass = queryToPass + separator + "manufacturer = '"
                                + manufacturer.getText() + "'";
                    } else {
                        queryToPass = "manufacturer = '" + manufacturer.getText() + "'";
                        updateBefore = true;
                    }
                }
                if (!productname.getText().equals(blub[2])) {
                    if (updateBefore) {
                        queryToPass = queryToPass + separator + "productname = '"
                                + productname.getText() + "'";
                    } else {
                        queryToPass = "productname = '" + productname.getText() + "'";
                        updateBefore = true;
                    }
                }
                if (!notes.getText().equals(blub[3])) {
                    if (updateBefore) {
                        queryToPass = queryToPass + separator + "notes = '" + notes.getText() + "'";
                    } else {
                        queryToPass = "notes = '" + notes.getText() + "'";
                        updateBefore = true;
                    }
                }
                if (!location.getText().equals(blub[4])) {
                    if (updateBefore) {
                        queryToPass = queryToPass + separator + "location = '" + location.getText() + "'";
                    } else {
                        queryToPass = "location = '" + location.getText() + "'";
                        updateBefore = true;
                    }
                }

                String checkImei = imei.getText();
                System.out.println(checkImei);
                System.out.println(blub[5]);
                if (!checkImei.isBlank()) {
                    if (!checkImei.equals(blub[5])) {
                        if (updateBefore) {
                            queryToPass = queryToPass + separator + "imei = '" + checkImei + "'";
                        } else {
                            queryToPass = "imei = '" + imei.getText() + "'";
                            updateBefore = true;
                        }
                    }
                }
                String acqValToCheck = acquisitionValue.getText().replaceAll("[,]", "");
                if (!acqValToCheck.equals(blub[6])) {
                    if (updateBefore) {
                        queryToPass = queryToPass + separator + "acquisitionValue = '"
                                + acqValToCheck + "'";
                    } else {
                        queryToPass = "acquisitionValue = '" + acqValToCheck + "'";
                        updateBefore = true;
                    }
                }

                /*Date-Check
                proves trickier; needs to get newly selected Date from Update-Device-Panel
                and transform it into SQL-formatted String to run check if data changed and
                proceed as documented above
                 */
                SelectedDate sd = acquisitionDate.getSelectedDate();
                LocalDate dateToString = LocalDate.of(sd.getYear(), sd.getMonth(), sd.getDay());
                String dts = dateToString.toString();
                if (!dts.equals(blub[7])) {
                    if (updateBefore) {
                        queryToPass = queryToPass + separator + "acquisitionDate = '" + dateToString + "'";
                    } else {
                        queryToPass = "acquisitionDate = '" + dateToString + "'";
                        updateBefore = true;
                    }
                }

                /* Admin-Check
                get selected Admin-ID from ComboBox
                 */
                String adminToCheck = administrator.getSelectedItem().toString();
                if (!adminToCheck.equals(blub[8])) {
                    if (updateBefore) {
                        queryToPass = queryToPass + separator + "administrators_adminID = '" + adminToCheck + "'";
                    } else {
                        queryToPass = "acquisitionDate = '" + dateToString + "'";
                        updateBefore = true;
                    }
                }

                try {

                    if (updateBefore) {
                        dbh.updateDevice(queryToPass, invNoToCheck);
                        JOptionPane.showMessageDialog(null, "Device Feld(er) geändert", "Success", 1);
                    } else {
                        JOptionPane.showMessageDialog(null, //no owner frame
                                "Keine Änderungen am Datensatz festgestellt", //text to display
                                "Error", //title
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, "Ein Fehler ist beim Datenbankzugriff aufgetreten",
                            "SQL-Error", 0);
                }

            }

        });
    }

    /**
     * allows user to reset Textfields to their original values
     */
    public void resetTfs() {
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillTFs();
            }
        });

    }
}

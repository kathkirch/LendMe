/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * InputVerifier used to verify User Input in New Device and Update Device
 * Panels
 *
 * @author bstra
 */
public class Inventory_Verifier extends InputVerifier {

    //components to verify
    private JTextField productname;
    private JTextField manufacturer;
    private JTextField inventoryNumber;
    private JTextField imei;
    private JTextField location;
    private JComboBox admin;
    private JTextField acqValue;

    //ArrayLists of existing DB-entries
    private ArrayList<String> invNos;
    private ArrayList<String> imeis;

    //NumberFormat to Format acquisitonValues
    private NumberFormat nf;

    //constructor
    public Inventory_Verifier(JTextField productname, JTextField manufacturer,
            JTextField inventoryNumber, JTextField imei, JTextField location,
            JComboBox admin, JTextField acqValue, ArrayList<String> invNos,
            ArrayList<String> imeis) {
        this.productname = productname;
        this.manufacturer = manufacturer;
        this.inventoryNumber = inventoryNumber;
        this.imei = imei;
        this.location = location;
        this.admin = admin;
        this.acqValue = acqValue;
        this.invNos = invNos;
        this.imeis = imeis;
    }

    /**
     * initialize Number Format for Field Acquisition Value
     */
    private void setUpNumberFormat() {
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
    }

    @Override
    public boolean verify(JComponent input) {

        //check if user input in JTextField productname is shorter than 45
        //if NO: display error message and do not yield focus
        //if YES: accept input for SQL-query
        if (input == productname && productname.getText().length() > 45) {

            JOptionPane.showMessageDialog(null, //no owner frame
                    "Eingabe zu lang. Bitte max. 45 Zeichen eingeben", //text to display
                    "Error", //title
                    JOptionPane.WARNING_MESSAGE);

            return false;

            //check if user input in JTextField manufacturer is shorter than30
            //if NO: display error message and do not yield focus
            //if YES: accept input for SQL-query
        } else if (input == manufacturer && manufacturer.getText().length() > 30) {

            JOptionPane.showMessageDialog(null, //no owner frame
                    "Eingabe zu lang. Bitte max. 30 Zeichen eingeben", //text to display
                    "Error", //title
                    JOptionPane.WARNING_MESSAGE);

            return false;

            //check if user input in JTextField location is shorter than 45
            //if NO: display error message and do not yield focus
            //if YES: accept input for SQL-query    
        } else if (input == location && location.getText().length() > 45) {

            JOptionPane.showMessageDialog(null, //no owner frame
                    "Eingabe zu lang. Bitte max. 45 Zeichen eingeben", //text to display
                    "Error", //title
                    JOptionPane.WARNING_MESSAGE);

            return false;

            // check if user input in JTF acquisiton Value matches the format
            // 1-7 pre-decimal places, 0-2 decimal places and . as decimal delemiter
        } else if (input == acqValue) {
            String getAcqValue = acqValue.getText();

            if (!(getAcqValue.matches("[0-9]{1,7}[.]{1}[0-9]{0,2}")
                    || getAcqValue.matches("[0-9]{1,7}"))) {

                JOptionPane.showMessageDialog(null, //no owner frame
                        "Bitte max. 7 Vor- und 2 Nachkommastellen sowie . als Dezimaltrennzeichen eingeben", //text to display
                        "Error", //title
                        JOptionPane.WARNING_MESSAGE);
                return false;
            } else {
                //formats Input
                Double asDbl = Double.parseDouble(getAcqValue);
                setUpNumberFormat();
                acqValue.setText(nf.format(asDbl));
            }

            //Validator for JTF InventoryNumber
        } else if (input == inventoryNumber) {

            String invToCheck = inventoryNumber.getText();

            //check if user input in JTF inventoryNumber is between 1 and 11 digits
            if (!invToCheck.matches("[0-9]{0,11}")) {
                JOptionPane.showMessageDialog(null, //no owner frame
                        "Bitte die max. 11-stellige Inventarnummer eingeben oder\n"
                        + "das Feld freilassen, um automatisch eine zu generieren", //text to display
                        "Error", //title
                        JOptionPane.WARNING_MESSAGE);

                return false;
            }
            //check if user input in JTF inventoryNumber already exists in the database
            //if YES: display Error Message, do not yield Focus
            if (invNos.contains(invToCheck)) {
                JOptionPane.showMessageDialog(null, //no owner frame
                        "Die eingegebene Inventarnummer ist bereits vergeben. \n"
                        + "Bitte wählen Sie eine andere.", //text to display
                        "Error", //title
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }

            //Validator for JTF imei
        } else if (input == imei) {

            String imeiToCheck = imei.getText();

            //check if JTF imei is either left empty or if exactly 15 numbers are entered
            //if YES: continue
            //if NO: display Error Message, do not yield Focus
            if (!(imeiToCheck.isEmpty() || imeiToCheck.matches("[0-9]{15}"))) {

                JOptionPane.showMessageDialog(null, //no owner frame
                        "Für eine gültige IMEI bitte genau 15 Ziffern eingeben", //text to display
                        "Error", //title
                        JOptionPane.WARNING_MESSAGE);
                return false;

            }

            //check if user input in JTF imei already exists in the database
            //if YES: display Error Message, do not yield Focus
            if (imeis.contains(imeiToCheck)) {
                JOptionPane.showMessageDialog(null, //no owner frame
                        "Die eingegebene IMEI ist bereits vergeben. \n"
                        + "Bitte wählen Sie eine andere.", //text to display
                        "Error", //title
                        JOptionPane.WARNING_MESSAGE);

                return false;
            }

        }

        return true;
    }

}

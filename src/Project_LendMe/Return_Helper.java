/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.SelectedDate;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Helper Class to initialize the newreturn_panel and all the methods
 * and listener for JButtons needed in this panel
 * 
 * @author Katharina
 */
public class Return_Helper {
    
    JPanel panel;
    JTextField productname;
    JTextField manufacturer;
    JTextField inventoryNumber;
    JTextField userID;
    JTextField userName;
    JTextField userPhone;
    JTextField userEmail;
    DateChooser rDate; 
    JTextArea rNotes;
    JButton rSave;
    JButton rCancel;
    JRadioButton yesBT;
    JLayeredPane lp;
    JPanel home;
 
    private final static DatabaseHelper dbH = new DatabaseHelper();
    private final static Validator val = new Validator();
    
    List <Devices> devices = dbH.getAllDevices2();
   
    public Return_Helper(JPanel panel, JLayeredPane lp, JPanel home) {
        
        this.panel = panel;
        this.productname = (JTextField) panel.getComponent(12);
        this.manufacturer = (JTextField) panel.getComponent(13);
        this.inventoryNumber = (JTextField) panel.getComponent(14);
        this.userID = (JTextField) panel.getComponent(15);
        this.userName = (JTextField) panel.getComponent(16);
        this.userPhone = (JTextField) panel.getComponent(17);
        this.userEmail = (JTextField) panel.getComponent(18);
        this.rNotes = (JTextArea) panel.getComponent(19);
        this.rDate = (DateChooser) panel.getComponent(20);
        this.yesBT = (JRadioButton) panel.getComponent(11);
        this.rSave = (JButton) panel.getComponent(0);
        this.rCancel = (JButton) panel.getComponent(21);
        
        this.lp = lp;
        this.home = home;
    }
    
    
    // method to make all the JTextFields in this panel not Editable
    public void notEditable () {
        
        Component [] comps = panel.getComponents();
        for (Component field : comps){
            if (field instanceof JTextField) {
                ((JTextField) field).setEditable(false);
            }
        }
    }
    
    // method to fill the JTextFields with the values of device that is returned
    public void showData (){
        
        String notes = null;
        
        productname.setText(RentalList_Helper.RETURN_PRODUCTNAME);
        manufacturer.setText(RentalList_Helper.RETURN_MANUFACTURER);
        inventoryNumber.setText(String.valueOf(RentalList_Helper.RETURN_INVNUMBER));
        userID.setText(String.valueOf(RentalList_Helper.RETURN_USERID));
        
        Users user = dbH.getUserByID(String.valueOf(RentalList_Helper.RETURN_USERID));
        
        String fullname = user.getUserFirstName() + " " + user.getUserLastName();
        userName.setText(fullname);
        userPhone.setText(user.getUserPhone());
        userEmail.setText(user.getUserEmail());
        
        for (Devices dev : devices) {
            if (dev.getInventoryNumber() == RentalList_Helper.RETURN_INVNUMBER){
                notes = dev.getNotes();
                
                if (val.isAlphaNumeric(notes)) {
                    notes = dev.getNotes();
                } else {
                    JOptionPane.showMessageDialog(null, 
                   "Feld \"Notizen\" darf keine Sonderzeichen enthalten");
                }
            }
        }
        
        rNotes.setText(notes);
        
    }
    
    /**
     *initiates a listener for the "Speichern" button 
     *calls the createNewReturn method
     */
    public void saveReturn () {
        rSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                createNewReturn();
                
            }
        });
    }
   

    /**
     *initialize a listener for the "Abbrechen" Button 
     * to go back to the start/home-screen
     */
    public void cancel () {
        rCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                backToStart();
            }
        });
    }
    
    /**
     * method to load and pop up the home_panel 
     */
    public void backToStart () {
        
        lp.removeAll();
        lp.add(home);
        lp.repaint();
        lp.revalidate();
        
    }
    
    /**
     *method to save a new return
     *calls the 'updateRentals'-method and the 'setDevice_NotLent'-method
     */
    public void createNewReturn (){
        
        int returnID = RentalList_Helper.RETURN_ID;
        
        SelectedDate selectedDate = rDate.getSelectedDate();
        
        LocalDate returnDate = LocalDate.of(selectedDate.getYear(), 
                                selectedDate.getMonth(),
                                selectedDate.getDay()); 
        
        Rentals rental = dbH.getRentalByID(returnID);
        
        String notes = rNotes.getText();
        
        boolean validDate = val.validReturnDate(returnDate, rental.getRentalDate());
        boolean validNotes = val.validNotes(notes);
        
        long invNumb = Long.valueOf(inventoryNumber.getText());
        
        if (yesBT.isSelected()) {
            
            // check if returnDate and notes are valid fields
            if (validDate == true && validNotes == true){
                
                try {
                    
                dbH.updateRentals(returnID, returnDate);
                dbH.setDevice_NotLent(invNumb, notes);
                JOptionPane.showMessageDialog(null, "Gerätestatus aktualisert"
                        + "\n Rückgabe gespeichert");
                yesBT.setSelected(false);
                backToStart();
                
                } catch (UserException ex){
                    JOptionPane.showMessageDialog(null, "Fehler bei Rückgabe \n"
                            + "Eingabe prüfen", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println(ex + "\n createNewReturn in ReturnHelper");
                }
            }
            
        } else {
          JOptionPane.showMessageDialog(null, 
                  "Bitte Gerät auf Werkseinstellungen zurücksetzen!");
        }
    } 
}

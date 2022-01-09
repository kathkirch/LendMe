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
import java.time.Month;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;





/**
 *
 * @author linda, Katharina
 */
public class ReturnHelper {
    
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
    JScrollPane scrollPane;
    
    private final static DatabaseHelper dbH = new DatabaseHelper();
    
    List <Devices> devices = dbH.getDevices();

    public ReturnHelper(JPanel panel) {
        
        this.panel = panel;
        
        this.productname = (JTextField) panel.getComponent(12);
        this.manufacturer = (JTextField) panel.getComponent(11);
        this.inventoryNumber = (JTextField) panel.getComponent(13);
        this.userID = (JTextField) panel.getComponent(14);
        this.userName = (JTextField) panel.getComponent(19);
        this.userPhone = (JTextField) panel.getComponent(10);
        this.userEmail = (JTextField) panel.getComponent(20);
        this.rNotes = (JTextArea) panel.getComponent(21);
        this.rDate = (DateChooser) panel.getComponent(15);
        this.yesBT = (JRadioButton) panel.getComponent(16);
        this.rSave = (JButton) panel.getComponent(17);
        this.rCancel = (JButton) panel.getComponent(18);
        
    }
    
    public void notEditable () {
        
        Component [] comps = panel.getComponents();
        for (Component field : comps){
            if (field instanceof JTextField) {
                ((JTextField) field).setEditable(false);
            }
        }
    }
    
    
    public void showData (){
        
        String notes = null;
        
        productname.setText(Rentallist_Helper.RETURN_PRODUCTNAME);
        manufacturer.setText(Rentallist_Helper.RETURN_MANUFACTURER);
        inventoryNumber.setText(String.valueOf(Rentallist_Helper.RETURN_INVNUMBER));
        userID.setText(String.valueOf(Rentallist_Helper.RETURN_USERID));
        
        Users user = dbH.checkUserID(String.valueOf(Rentallist_Helper.RETURN_USERID));
        
        String fullname = user.getUserFirstName() + " " + user.getUserLastName();
        userName.setText(fullname);
        userPhone.setText(user.getUserPhone());
        userEmail.setText(user.getUserEmail());
        
        for (Devices dev : devices) {
            if (dev.getInventoryNumber() == Rentallist_Helper.RETURN_INVNUMBER){
                System.out.println("ttt?");
                notes = dev.getNotes();
            }
        }
        
        System.out.println(notes);
        rNotes.setText(notes);
        
    }
    
    public void saveReturn () {
        rSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                createNewReturn();
            }
        });
    }
    
    
    
    
    public void createNewReturn (){
        
        int returnID = Rentallist_Helper.RETURN_ID;
        
        SelectedDate selectedDate = rDate.getSelectedDate();
        
        LocalDate returnDate = LocalDate.of(selectedDate.getYear(), 
                                selectedDate.getMonth(),
                                selectedDate.getDay()); 
        
        String notes = rNotes.getText().toString();
        
        long invNumb = Long.valueOf(inventoryNumber.getText().toString());
        
        if (yesBT.isSelected()) {
            dbH.updateRentals(returnID, returnDate);
            dbH.setDevice_NotLent(invNumb, notes);
        } else {
          JOptionPane.showMessageDialog(null, 
                  "Bitte Gerät auf Werkseinstellungen zurücksetzen!");
        }
    }
    
}

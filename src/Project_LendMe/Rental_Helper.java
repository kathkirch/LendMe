/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import com.raven.datechooser.DateChooser;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import com.raven.datechooser.SelectedDate;

/**
 *
 * @author Katharina
 */
public class Rental_Helper {
    
    final DatabaseHelper hp = new DatabaseHelper();
    
    public void fillComboBox_Category (JComboBox box, String category){
        List <Devices> list = hp.getDevices();
        
        List <Object> oList = hp.makeListForCategory(list, category);
        box.setModel(new DefaultComboBoxModel<>(oList.toArray((new String[0]))));
        box.setEditable(true);
        box.setSelectedItem("");
        AutoCompleteDecorator.decorate(box);  
    }
    
    public void fillUser_Year_Admin (JComboBox userBox, JComboBox yearBox,
                                        JComboBox adminBox){
    
        userBox.setModel(new DefaultComboBoxModel<>(hp.getUsersID().toArray((new String[0]))));
        //damit man in UserID Matrikelnummer eingeben kann
        userBox.setEditable(true);
        userBox.setSelectedItem("");
        AutoCompleteDecorator.decorate(userBox);
        
        yearBox.setModel(new DefaultComboBoxModel<>(hp.getUserYears().toArray((new String[0]))));
        // damit man neues Jahr eingeben kann wenn noch nicht in DB vorhanden
        yearBox.setEditable(true);
        yearBox.setSelectedItem("");
        AutoCompleteDecorator.decorate(yearBox);
        
        adminBox.setModel(new DefaultComboBoxModel<>(hp.getAdminIDs().toArray((new String[0]))));
        adminBox.setEditable(true);
        adminBox.setSelectedItem("");
        AutoCompleteDecorator.decorate(adminBox);
        
    }
    
    public Users createUser(JComboBox idBox, JTextField tf_firs, JTextField tf_last,
                                JTextField tf_phone,JTextField tf_mail, JComboBox yearBox){
        
        Users user = null;
        
        String id = idBox.getEditor().getItem().toString();
        String firstname = tf_firs.getText();
        String lastname = tf_last.getText(); 
        String phone = tf_phone.getText();
        String email = tf_mail.getText();
        String year = yearBox.getSelectedItem().toString();
        
        if (firstname.isBlank() || lastname.isBlank() || phone.isBlank() ||
                email.isBlank()){
            JOptionPane.showMessageDialog(null, 
                    "Achtung! Ein oder mehrere Textfelder sind leer!"
                            + " Bitte alles ausfüllen!");
        
        }else {
            user = new Users(Integer.parseInt(id), firstname, lastname, phone, 
                                email, year);
        }
        return user;
    }
    
    public void createNewRental (DateChooser chDate, JComboBox boxInvNumber,
                                    JComboBox boxUserID, JComboBox boxAdminID){
        
        try {
            Thread.sleep(1000); // wird benoetigt wenn neuer User hinzugefuegt wird, sonst SQL Errer wegen Foreign KEY
            
            SelectedDate selectedDate = chDate.getSelectedDate();
            LocalDate date = 
                LocalDate.of(selectedDate.getYear(), 
                selectedDate.getMonth(), selectedDate.getDay());
        
            String inventoryNumb = (String) boxInvNumber.getSelectedItem();
        
            String userID = (String) boxUserID.getSelectedItem();
        
            String adminID = (String) boxAdminID.getSelectedItem();
        
            Rentals rental = new Rentals(date, Integer.parseInt(inventoryNumb),
                            Integer.parseInt(adminID),
                            Integer.parseInt(userID));
        
            hp.insertNewRental_DB(rental);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } 
        JOptionPane.showMessageDialog(null, "Device verliehen! Status aktualisiert");
    }
     
}

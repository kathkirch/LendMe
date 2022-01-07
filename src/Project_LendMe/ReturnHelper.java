/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.SelectedDate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author linda
 */
public class ReturnHelper {
    
    DateChooser rDate; 
    JTextField rNotes;
    JButton rSave;
    JButton rCancel;
    
    final DatabaseHelper rdb = new DatabaseHelper();

    public ReturnHelper(DateChooser rDate, JTextField rNotes, JButton rSave, JButton rCancel) {
        this.rDate = rDate;
        this.rNotes = rNotes;
        this.rSave = rSave;
        this.rCancel = rCancel;
    }
    
    
    public void createNewReturn (DateChooser rDate, JTextField rNotes){
        
        try {
            Thread.sleep(1000); // wird benoetigt wenn neuer User hinzugefuegt wird, sonst SQL Errer wegen Foreign KEY

            SelectedDate selectedDate = rDate.getSelectedDate();
            LocalDate date = 
                LocalDate.of(selectedDate.getYear(), 
                selectedDate.getMonth(), selectedDate.getDay());
        
            String notes = rNotes.getText();
            
           /*  Return return = new Return(date, notes);
            rdb.insertNewReturn_DB(return); */
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } 
        JOptionPane.showMessageDialog(null, "Device verliehen! Status aktualisiert");
    }
    
    /**
     * adds an listener for the save-JButton 
     * controls if the user is new in database and if yes creates a new user object
     * if the new created user- object has valid data and is not null 
     * the user -object is inserted in the database and boolean validUser is set true
     * if user is not new in database a user object is created to proof if 
     * userfields are valid and boolean validUser is set true
     * if userValid is true the createNewRental() Method is called
     */
   
    
    
    
    /*public void saveNewRental () {
        jBsave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                boolean validUser = true;

                if (hp.isUserNew(jCBuserID.getEditor().getItem()
                        .toString())){
                    
                    Users user = createUser(jCBuserID, jTFfirstname, 
                                jTFlastname, jTFphone, jTFmail, jCByear);

                    if (user == null){
                        validUser = false;
                    }
                    if (validUser){
                        hp.insertNewUser(user);
                        JOptionPane
                            .showMessageDialog(null, "Neuen User hinzugefügt");
                    }
                
                } else {
                    Users user = createUser(jCBuserID, jTFfirstname, 
                                jTFlastname, jTFphone, jTFmail, jCByear);

                    if (user == null){
                        validUser = false;
                    }
                }
                if (validUser){
                    createNewRental(dcDate, jCBinvnumber, jCBuserID, jCBadminID);
                    deleteAll();
                }
            }
        });
    } */
    
    
    
}

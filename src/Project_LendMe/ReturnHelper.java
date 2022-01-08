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
        
    }
    
}

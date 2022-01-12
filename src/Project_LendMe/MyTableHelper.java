/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Katharina
 */
abstract public class MyTableHelper {
  
    JTable table;
    JScrollPane js;
    JComboBox box;
    JRadioButton ascRadio;
    JRadioButton descRadio;
    JTextField filterTF;
    JButton filterBT;
    JButton clearBT;
    
    static List <Rentallist> rentalList;
    static List <Rentals> allRentals;
    String [] columns;
    
    Object [][] data;
    DefaultTableModel model;

    public MyTableHelper(JTable table, JScrollPane js, JComboBox box, 
                        JRadioButton ascRadio, JRadioButton descRadio, 
                        JTextField filterTF, JButton filterBT, JButton clearBT) {
        
        this.table = table;
        this.js = js;
        this.box = box;
        this.ascRadio = ascRadio;
        this.descRadio = descRadio;
        this.filterTF = filterTF;
        this.filterBT = filterBT;
        this.clearBT = clearBT;
        
        ascRadio.setSelected(false);
        descRadio.setSelected(false);
        filterTF.setText("");

        
    }
    
    /**
     * fills the JComboBox with the Strings 
     * initialized in the ' String [] columns ' 
     */
    public void fillBox () {
        box.setModel(new DefaultComboBoxModel<> (columns));
    }
    
    /**
     * initializes the table with its data and populates the table
     */
    public void  populateTable (){
        
        if (allRentals != null){
            data = initRentals(allRentals);
        } else if (rentalList != null){
            data = initRentalList(rentalList);
        }
        
        model = new DefaultTableModel(data, columns);
        
        table.setModel(model);
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        
        if (table.getPreferredSize().getHeight() < js.getPreferredSize().getHeight()){
            table.setPreferredSize(js.getPreferredSize());
        }
        
        table.setEnabled(false);
        js.setVisible(true); 
    }
    
    
     /**
     *
     * @param rentals as a List of Rentals-Objects 
     * needed to put items within the list 
     * into a double object array to display for each Object it's attributes
     * @return Object [][]
     */
    public Object [][] initRentals (List <Rentals> rentals){
       Object [][] data = new Object [rentals.size()] [];
       int i = 0;
       for(Rentals r : rentals){
           data[i] = new Object []{r.getRentalID(),
                                   r.getRentalDate(),
                                   r.getReturnDate(), 
                                   r.getDevice_inventoryNumber(),
                                   r.getAdministrators_AdminID(), 
                                   r.getUsers_UserID()};
           i = i + 1;
       }
       return data;
    } 
    
    
     /**
     *
     * @param rentallist as a List of Rentallist-Objects 
     * needed to put items within the list 
     * into a double object array to display for each Object it's attributes
     * @return Object [][]
     */
    public Object [][] initRentalList (List <Rentallist> rentallist){
        Object [][] datalist = new Object [rentallist.size()] [];
        int i = 0;
        for(Rentallist r : rentallist){
            datalist[i] = new Object []{r.getRentalID(),
                                        r.getDevice_inventoryNumber(),
                                        r.getProductName(),
                                        r.getManufacturer(),
                                        r.getUsers_UserID(),
                                        r.getLentDays()};                
            i = i + 1;
        }
        return datalist;
    }
}

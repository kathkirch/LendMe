/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import java.awt.Dimension;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author linda
 */
public class Rentallist_Helper {
    
    private final DatabaseHelper rlH = new DatabaseHelper();

    void initializeRentallist(JTable table, JScrollPane sp) {
        
         String [] columns = new String [] {"ID", "Inventarnummer","Produktname", 
                                "Herstellername","Verliehen an" ,"Verleihdatum"};
                                        
                                        
        
         List <Rentallist> rentallist =rlH.displayRentallist();
                
        
         Object [][] datalist = dataRental(rentallist);
         
        DefaultTableModel model = new DefaultTableModel(datalist, columns);
        
        table.setModel(model);
        
        table.setPreferredScrollableViewportSize(new Dimension (650, 400));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        /*table.setRowHeight(25);
            if (table.getPreferredSize().getHeight() < sp.getPreferredSize().getHeight()){
             table.setPreferredSize(sp.getPreferredSize()); */
        
       
        table.setEnabled(false);
        sp.setVisible(true);
        
    }
    
    public Object [][] dataRental (List <Rentallist> rentallist){
        Object [][] datalist = new Object [rentallist.size()] [];
        int i = 0;
        for(Rentallist r : rentallist){
            datalist[i] = new Object []{r.getRentalID(),
                                        r.getDevices_inventoryNumber(),
                                        r.getProductName(),
                                        r.getManufacturer(),
                                        r.getUsers_UserID(),
                                        r.getRentalDate()};
                                        
                                        
                                        
            i = i + 1;
        }
        return datalist;
    }  
}
    


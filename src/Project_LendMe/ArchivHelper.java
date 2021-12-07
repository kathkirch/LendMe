/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


/**
 *
 * @author Katharina
 */
public class ArchivHelper {
    
    private final DatabaseHelper dbH = new DatabaseHelper();
    
    public void  populateTable (JTable table, JScrollPane js){
        
        String [] columns = new String [] {"ID", "Verliehen am", 
                                        "Zurück am",  "Inventarnummer", 
                                        "AdminID", "Matrikelnummer"};
        
        List <Rentals> allRentals = dbH.readRentals();
        
        Object [][] data = initRentals1(allRentals);
        
        DefaultTableModel model = new DefaultTableModel(data, columns);
        
        table.setModel(model);
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(40);
        colModel.getColumn(1).setPreferredWidth(93);
        colModel.getColumn(2).setPreferredWidth(91);
        colModel.getColumn(3).setPreferredWidth(102);
        colModel.getColumn(4).setPreferredWidth(70);
        colModel.getColumn(5).setPreferredWidth(102);
        
        table.setRowHeight(25);
        
        //table.setPreferredScrollableViewportSize(new Dimension (3000, 3000));
        table.setFillsViewportHeight(true);
        
        if (table.getPreferredSize().getHeight() < js.getPreferredSize().getHeight()){
            table.setPreferredSize(js.getPreferredSize());
        }
        
        
        table.setEnabled(true);
        js.setVisible(true); 
    }
    
   
    
    public Object [][] initRentals1 (List <Rentals> rentals){
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
}

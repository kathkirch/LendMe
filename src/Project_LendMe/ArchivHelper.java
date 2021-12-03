/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.awt.Dimension;
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
        
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        table.setModel(model);
        
        table.setSize(32768, 32768);
        
        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(1).setPreferredWidth(3);
        colModel.getColumn(2).setPreferredWidth(5);
        colModel.getColumn(3).setPreferredWidth(5);
        colModel.getColumn(4).setPreferredWidth(7);
        table.setRowHeight(25);
        
        table.setPreferredScrollableViewportSize(new Dimension (650, 400));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(25);
        if (table.getPreferredSize().getHeight() < js.getPreferredSize().getHeight()){
            table.setPreferredSize(js.getPreferredSize());
        }
        
        table.setEnabled(false);
        js.setVisible(true);
        
    }
    
    
    public Object [][] initRentals1 (List <Rentals> rentals){
        Object [][] data = new Object [rentals.size()] [];
        int i = 0;
        for(Rentals r : rentals){
            System.out.println(r.getRentalID());
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

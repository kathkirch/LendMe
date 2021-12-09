/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import Comparators.rentalIDComparatorASC;
import Comparators.rentalDateComparatorASC;
import Comparators.returnDateComparatorASC;
import Comparators.rentalInvComparatorASC;
import Comparators.rentalAdminIDComparatorASC;
import Comparators.rentalUserIDComparatorASC;
import Comparators.rentalIDComparatorDESC;
import Comparators.rentalDateComparatorDESC;
import Comparators.returnDateComparatorDESC;
import Comparators.rentalInvComparatorDESC;
import Comparators.rentalAdminIDComparatorDESC;
import Comparators.rentalUserIDComparatorDESC;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


/**
 *
 * @author Katharina
 */
public class ArchivHelper {
    
    
    private final DatabaseHelper dbH = new DatabaseHelper();
    List <Rentals> allRentals = dbH.readRentals();
    private final String [] columns = new String [] {"ID", "Verliehen am", 
                                        "Zurück am",  "Inventarnummer", 
                                        "AdminID", "Matrikelnummer"};
    
    Object [][] data;
    JTable table;
    JScrollPane js;
    DefaultTableModel model;
    JComboBox box;
    JRadioButton ascRadio;
    JRadioButton descRadio;
    JTextField filterTF;
    JButton filterBT;
    
    
    public ArchivHelper(JTable table, JScrollPane js, JComboBox box,
                        JRadioButton ascRadio, JRadioButton descRadio,
                        JTextField filterTF, JButton filterBT){
        this.table = table;
        this.js = js;
        this.box = box;
        this.ascRadio = ascRadio;
        this.descRadio = descRadio;
        this.filterTF = filterTF;
        this.filterBT = filterBT;
        
        ascRadio.setSelected(false);
        descRadio.setSelected(false);
    }
    
    public void fillBox () {
        box.setModel(new DefaultComboBoxModel<>(columns));
    }
    
    public void  populateTable (){
         
        data = initRentals(allRentals);
        
        model = new DefaultTableModel(data, columns);
        
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
        
        table.setFillsViewportHeight(true);
        
        if (table.getPreferredSize().getHeight() < js.getPreferredSize().getHeight()){
            table.setPreferredSize(js.getPreferredSize());
        }
        
        table.setEnabled(false);
        js.setVisible(true); 
    }
    
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
    
    public void filterArchiveTable() {
        filterBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent aev) {
                int whereClause = box.getSelectedIndex();
                String filterString = filterTF.getText();
                List <Rentals> filteredList = dbH.filterRentals(whereClause, filterString);
                if (filteredList != null){
                    refreshTable(filteredList);
                } else {
                    JOptionPane.showMessageDialog(null, "Filteroption liefert keine Ergebnisse"); 
                }
            }
        });
    }
    
    public void sortArchiveTable (){
        
        ascRadio.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent aev) {
                if(ascRadio.isSelected()){
                    descRadio.setSelected(false);
                    int selected = box.getSelectedIndex(); 
                    switch (selected) {
                        case 0 :
                            Collections.sort(allRentals, new rentalIDComparatorASC());
                            refreshTable(allRentals);
                            break;
                        case 1 :
                            Collections.sort(allRentals, new rentalDateComparatorASC());
                            refreshTable(allRentals);
                            break;
                        case 2 :
                            Collections.sort(allRentals, new returnDateComparatorASC());
                            refreshTable(allRentals);
                            break;
                        case 3 :
                            Collections.sort(allRentals, new rentalInvComparatorASC());
                            refreshTable(allRentals);
                            break;
                        case 4 :
                            Collections.sort(allRentals, new rentalAdminIDComparatorASC());
                            refreshTable(allRentals);
                            break;
                        case 5 :
                            Collections.sort(allRentals, new rentalUserIDComparatorASC());
                            refreshTable(allRentals);
                            break;
                    }
                }  
            }
        }); 
        
        descRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent aev) {
                if (descRadio.isSelected()){
                    ascRadio.setSelected(false);
                    int selected = box.getSelectedIndex();
                    switch (selected) {
                        case 0 :
                            Collections.sort(allRentals, new rentalIDComparatorDESC());
                            refreshTable(allRentals);
                            break;
                        case 1 :
                            Collections.sort(allRentals, new rentalDateComparatorDESC());
                            refreshTable(allRentals);
                            break;
                        case 2 :
                            Collections.sort(allRentals, new returnDateComparatorDESC());
                            refreshTable(allRentals);
                            break;
                        case 3 :
                            Collections.sort(allRentals, new rentalInvComparatorDESC());
                            refreshTable(allRentals);
                            break;
                        case 4 :
                            Collections.sort(allRentals, new rentalAdminIDComparatorDESC());
                            refreshTable(allRentals);
                            break;
                        case 5 :
                            Collections.sort(allRentals, new rentalUserIDComparatorDESC());
                            refreshTable(allRentals);
                            break;
                    }
                }
            }
        }); 
    }
    
    public void refreshTable (List<Rentals> list){
        data = initRentals(list);
        model = new DefaultTableModel(data, columns);
        table.setModel(model);
        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(40);
        colModel.getColumn(1).setPreferredWidth(93);
        colModel.getColumn(2).setPreferredWidth(91);
        colModel.getColumn(3).setPreferredWidth(102);
        colModel.getColumn(4).setPreferredWidth(70);
        colModel.getColumn(5).setPreferredWidth(102);
        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        if (table.getPreferredSize().getHeight() < js.getPreferredSize().getHeight()){
            table.setPreferredSize(js.getPreferredSize());
        }
        table.setEnabled(false);
        js.setVisible(true); 
    }    
}

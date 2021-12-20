/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Katharina
 */
public class ArchivHelper extends MyTableHelper {
    
    
    private final DatabaseHelper dbH = new DatabaseHelper();
    
    public ArchivHelper(JTable table, JScrollPane js, 
                    JComboBox box, JRadioButton ascRadio, JRadioButton descRadio, 
                    JTextField filterTF, JButton filterBT) {
        super( table, js, box, ascRadio, descRadio, filterTF, filterBT);
        
        this.allRentals = dbH.readRentals();
        this.columns =  new String [] {"ID", "Verliehen am", 
                                        "Zurück am",  "Inventarnummer", 
                                        "AdminID", "Matrikelnummer"};
        
       
//        ascRadio.setSelected(false);
//        descRadio.setSelected(false);
//        filterTF.setText("");
    }

    @Override
    public Object[][] initRentals(List<Rentals> rentals) {
        Object [] [] data = super.initRentals(rentals); 
        return data;
    }

    @Override
    public void populateTable() {
        super.populateTable(); 
        
        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(40);
        colModel.getColumn(1).setPreferredWidth(93);
        colModel.getColumn(2).setPreferredWidth(91);
        colModel.getColumn(3).setPreferredWidth(102);
        colModel.getColumn(4).setPreferredWidth(70);
        colModel.getColumn(5).setPreferredWidth(102);
        
        table.setRowHeight(25);
    }

    @Override
    public void fillBox() {
        super.fillBox(); 
    }
    
    
    public void filterArchiveTable() {
        filterBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent aev) {
                int whereClause = box.getSelectedIndex();
                String filterString = filterTF.getText();
                List <Rentals> filteredList = dbH.filterRentals(whereClause, filterString);
                if (filteredList != null){
                    refreshArchiveTable(filteredList);
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
                            break;
                        case 1 :
                            Collections.sort(allRentals, new rentalDateComparatorASC());
                            break;
                        case 2 :
                            Collections.sort(allRentals, new returnDateComparatorASC());
                            break;
                        case 3 :
                            Collections.sort(allRentals, new rentalInvComparatorASC());
                            break;
                        case 4 :
                            Collections.sort(allRentals, new rentalAdminIDComparatorASC());
                            break;
                        case 5 :
                            Collections.sort(allRentals, new rentalUserIDComparatorASC());
                            break;
                    }
                    refreshArchiveTable(allRentals);
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
                            Collections.sort(allRentals, new rentalIDComparatorDESC());//                            refreshTable(allRentals);
                            break;
                        case 1 :
                            Collections.sort(allRentals, new rentalDateComparatorDESC());//                            refreshTable(allRentals);
                            break;
                        case 2 :
                            Collections.sort(allRentals, new returnDateComparatorDESC());//                            refreshTable(allRentals);
                            break;
                        case 3 :
                            Collections.sort(allRentals, new rentalInvComparatorDESC());//                            refreshTable(allRentals);
                            break;
                        case 4 :
                            Collections.sort(allRentals, new rentalAdminIDComparatorDESC());//                            refreshTable(allRentals);
                            break;
                        case 5 :
                            Collections.sort(allRentals, new rentalUserIDComparatorDESC());//                            refreshTable(allRentals);
                            break;
                    }
                    refreshArchiveTable(allRentals);
                }
            }
        }); 
    }
    
    
    public void refreshArchiveTable (List<Rentals> list){ 
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

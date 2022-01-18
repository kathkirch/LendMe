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
import Comparators.RentalIDComparator;
import Comparators.RentalDateComparator;
import Comparators.ReturnDateComparator;
import Comparators.RentalInvComparator;
import Comparators.RentalAdminIDComparator;
import Comparators.RentalUserIDComparator;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Katharina
 */
public class ArchivHelper extends MyTableHelper implements FilterSortModel {
    
    
    private final DatabaseHelper dbH = new DatabaseHelper();
    
   
    public ArchivHelper(JTable table, JScrollPane js, 
                    JComboBox box, JRadioButton ascRadio, JRadioButton descRadio, 
                    JTextField filterTF, JButton filterBT, JButton clearBT) {
        super( table, js, box, ascRadio, descRadio, filterTF, filterBT, clearBT);
        
        this.allRentals = dbH.readRentals();
        this.columns =  new String [] {"ID", "Verliehen am", 
                                        "Zurück am",  "Inventarnummer", 
                                        "AdminID", "Matrikelnummer"};
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
        
    }

    @Override
    public void fillBox() {
        super.fillBox(); 
    }
    
    
     /**
     *
     * @param list refresh the table with objects from the given list 
     * of Rentals-Objects
     */ 
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
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>();
        table.setRowSorter(sorter);
        sorter.setModel(model);
   }    
    

    
    /**
     * filters the data from database with the 
     * whereClause(JComboBox) and filterString(JTextField) 
     * and displays them in the table
     */
    @Override
    public void filterTable() {
        filterBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent aev) {
                int whereClause = box.getSelectedIndex();
                String filterString = filterTF.getText();
                List <Rentals> filteredList = dbH.filterRentals(whereClause, filterString);
                if (filteredList.size() > 0){
                    refreshArchiveTable(filteredList);
                } else {
                    JOptionPane.showMessageDialog(null, "Filteroption liefert keine Ergebnisse"); 
                }
            }
        });
    }

    
    /**
     * adds an listener for radioButton asc and desc, depending on selection
     * it sorts the data in table
     */
    @Override
    public void sortTable() {
         ascRadio.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent aev) {
                if(ascRadio.isSelected()){
                    descRadio.setSelected(false);
                    int selected = box.getSelectedIndex(); 
                    switch (selected) {
                        case 0 :
                            Collections.sort(allRentals, new RentalIDComparator());
                            break;
                        case 1 :
                            Collections.sort(allRentals, new RentalDateComparator());
                            break;
                        case 2 :
                            Collections.sort(allRentals, new ReturnDateComparator());
                            break;
                        case 3 :
                            Collections.sort(allRentals, new RentalInvComparator());
                            break;
                        case 4 :
                            Collections.sort(allRentals, new RentalAdminIDComparator());
                            break;
                        case 5 :
                            Collections.sort(allRentals, new RentalUserIDComparator());
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
                            Collections.sort(allRentals, new RentalIDComparator().reversed());
                            break;
                        case 1 :
                            Collections.sort(allRentals, new RentalDateComparator().reversed());
                            break;
                        case 2 :
                            Collections.sort(allRentals, new ReturnDateComparator().reversed());
                            break;
                        case 3 :
                            Collections.sort(allRentals, new RentalInvComparator().reversed());
                            break;
                        case 4 :
                            Collections.sort(allRentals, new RentalAdminIDComparator().reversed());
                            break;
                        case 5 :
                            Collections.sort(allRentals, new RentalUserIDComparator().reversed());
                            break;
                    }
                    refreshArchiveTable(allRentals);
                }
            }
        });
    }

    
     /**
     * adds an Listener for the clearBT, deletes all filter and search options 
     * in the frame
     */
    @Override
    public void clearSelection() {
        clearBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent aEv){
                box.setSelectedIndex(0);
                ascRadio.setSelected(false);
                descRadio.setSelected(false);
                filterTF.setText("");
                List <Rentals> wholeRentals = dbH.readRentals();
                System.out.println(wholeRentals.toString());
                refreshArchiveTable(wholeRentals);
            }
        }); 
    }
}

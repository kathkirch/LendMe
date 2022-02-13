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
import Comparators.RentalUserIDComparator;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



/**
 *  Helper Class for the archive table with it's methods, 
 *  inherits from MyTableHelper, implements FilterSortModel
 *  
 * 
 * @author Katharina
 */
public class Archiv_Helper extends MyTableHelper implements FilterSortModel {
    
    
    private final DatabaseHelper dbH = new DatabaseHelper();
    private List <Rentals> filteredList = null;
    
    public static int RENTALID_ARCHIVE;
    public static long USERID_ARCHIVE;
    public static long INVNUMB_ARCHIVE;
    
    JLayeredPane lp;
    JPanel archive_panel;
   
    public Archiv_Helper(JTable table, JScrollPane js, 
                    JComboBox box, JRadioButton ascRadio, JRadioButton descRadio, 
                    JTextField filterTF, JButton filterBT, JButton clearBT, 
                    JLayeredPane lp, JPanel archive_panel) {
        super( table, js, box, ascRadio, descRadio, filterTF, filterBT, clearBT);
        
        MyTableHelper.allRentals = dbH.getCompletedRentals();
        
        this.columns =  new String [] {"ID", "Verliehen am", 
                                        "Zurück am", "Inventarnummer", 
                                         "Matrikelnummer"};
        
        this.lp = lp;
        this.archive_panel = archive_panel;
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
        colModel.getColumn(4).setPreferredWidth(102);

    }

    @Override
    public void fillBox() {
        super.fillBox(); 
    }
    
    
     /**
     * method to refresh the table with data from database
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
        colModel.getColumn(4).setPreferredWidth(102);
        
        table.setRowHeight(25);
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(570, 4000));
        
        table.setPreferredSize(new Dimension(524, 4000));
        
        if (table.getPreferredSize().getHeight() < js.getPreferredSize().getHeight()){
            table.setPreferredSize(js.getPreferredSize());
        }
        
        js.setVisible(true);
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
                filteredList = dbH.filterRentals(whereClause, filterString);
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
                
                if (filteredList != null && filteredList.size() > 0){
                    allRentals = filteredList;
                }
                
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
                
                if (filteredList != null && filteredList.size() > 0){
                    allRentals = filteredList;
                }
                
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
                            Collections.sort(allRentals, new RentalUserIDComparator().reversed());
                            break;
                    }
                    refreshArchiveTable(allRentals);
                }
            }
        });
    }
    
    /**
     * method to initialize a doubleClick-MouseListener 
     * gets the selected row, opens a new panel the info_archive_panel
     * to show more information about the selected rental/row
     * 
     * @param info_archive_panel to have access to this panel 
     */
    public void rowDoubleMousClick(JPanel info_archive_panel) {
        
        int i = table.getMouseListeners().length;
        
        if (i <= 2 && i < 4 ){
            
            table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                DefaultTableModel actual_model = (DefaultTableModel) table.getModel(); 
                table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int index = row;
                   
                    int rentalID = 0;
                    long invNumber = 0;
                    long userID = 0;
                    
                    try {
                        rentalID = (int) actual_model.getValueAt(index, 0);
                    } catch (java.lang.ClassCastException exe ){
                        String rentalIdAsString = (String) actual_model.getValueAt(index, 0);
                        rentalID = Integer.parseInt(rentalIdAsString);
                    }
                    try{
                        invNumber = (long) actual_model.getValueAt(index, 3);
                    } catch (java.lang.ClassCastException exe){
                        String invNumberAsString = (String) actual_model.getValueAt(index, 3);
                        invNumber = Long.parseLong(invNumberAsString);
                    }
                    try{
                        userID = (long) actual_model.getValueAt(index, 4);
                    } catch (java.lang.ClassCastException exe){
                        String userIdAsString = (String) actual_model.getValueAt(index, 4);
                        userID = Long.parseLong(userIdAsString);
                    }
                    
                    // set this static attributes with data from the selected row
                    // to get data via id values
                    if (rentalID != 0 && userID != 0 && invNumber != 0){
                        RENTALID_ARCHIVE = rentalID;
                        USERID_ARCHIVE = userID;
                        INVNUMB_ARCHIVE = invNumber;
                    }
                    
                    
                    lp.removeAll();
                    lp.add(info_archive_panel);
                    lp.repaint();
                    lp.revalidate();
                    
                    //remove all listener in this panel to avoid muliple listener
                    GUI.removeListener(info_archive_panel);
                    
                    //initialize a ArchiveInfo_Object
                    ArchiveInfo_Helper aih = new ArchiveInfo_Helper
                                        (info_archive_panel, lp, archive_panel);
                    
                    //method to make textfields not editable
                    aih.notEditable();
                    //method to initialize Textfields with Data from Database
                    //depending on selected rental/row
                    aih.showData();
                    //method to initialize a Listener for 'Abbrechen" button 
                    //in info_archive_panel to go back to the archive_panel
                    aih.cancel();
                    }
                }
            });
        }  
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
                filteredList = null;
                List <Rentals> wholeRentals = dbH.getCompletedRentals();
                refreshArchiveTable(wholeRentals);
            }
        }); 
    }
}

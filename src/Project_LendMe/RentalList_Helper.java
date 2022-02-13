/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import Comparators.RentalIDComparator;
import Comparators.RentalInvComparator;
import Comparators.RentalUserIDComparator;
import Comparators.RentallistLentDaysComparator;
import Comparators.RentallistManuNameComparator;
import Comparators.RentallistProNameComparator;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *  Helper Class for the rentallist table with it's methods, 
 *  inherits from MyTableHelper, implements FilterSortModel
 * 
 * @author Katharina
 */
public class RentalList_Helper extends MyTableHelper implements FilterSortModel{
    
    private final DatabaseHelper rlH = new DatabaseHelper();
    
    private List <RentalList> filteredList_rentals = null;
    
    public JButton returnBT;
    public JLayeredPane lp;
    public JPanel home;
    public static int RETURN_ID;
    public static String RETURN_PRODUCTNAME;
    public static String RETURN_MANUFACTURER;
    public static long RETURN_INVNUMBER;
    public static long RETURN_USERID;
    
    public RentalList_Helper(JTable table, JScrollPane js, JComboBox box, 
                JRadioButton ascRadio, JRadioButton descRadio, 
                JTextField filterTF, JButton filterBT, JButton clearBT, 
                JButton returnBT, JLayeredPane lp, JPanel home) {
        super(table, js, box, ascRadio, descRadio, filterTF, filterBT, clearBT);
        
        this.returnBT = returnBT;
        
        MyTableHelper.rentalList = rlH.getRentallist();
        
        this.columns = new String [] {"ID", "Inventarnummer","Produktname", 
                                "Hersteller","Verliehen an" ,"Verliehene Tage"};
        
        this.lp = lp;
        this.home = home;
        
    }
    

    @Override
    public Object[][] initRentalList(List<RentalList> rentallist) {
        Object [] [] data = super.initRentalList(rentallist);
        return data; 
    }

    
    @Override
    public void populateTable() {
        super.populateTable();
        
        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(35);
        colModel.getColumn(1).setPreferredWidth(102);
        colModel.getColumn(2).setPreferredWidth(85);
        colModel.getColumn(3).setPreferredWidth(70);
        colModel.getColumn(4).setPreferredWidth(90);
        colModel.getColumn(5).setPreferredWidth(105);
        
        table.setDefaultEditor(Object.class, null);
    }

    @Override
    public void fillBox() {
        super.fillBox(); 
    }
    
     
    /**
     * method to refresh the table with the elements from given list
     * 
     * @param list refresh the table with objects from the given list 
     *  of RentalList-Objects
     */
    public void refreshRentalTable (List<RentalList> list){ 
        data = initRentalList(list);
        model = new DefaultTableModel(data, columns);
        table.setModel(model);
        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(35);
        colModel.getColumn(1).setPreferredWidth(102);
        colModel.getColumn(2).setPreferredWidth(85);
        colModel.getColumn(3).setPreferredWidth(70);
        colModel.getColumn(4).setPreferredWidth(100);
        colModel.getColumn(5).setPreferredWidth(105);
        
        table.setRowHeight(25);
        
        table.setFillsViewportHeight(true);
        if (table.getPreferredSize().getHeight() < js.getPreferredSize().getHeight()){
            table.setPreferredSize(js.getPreferredSize());
        }
        
        js.setVisible(true);
        table.setDefaultEditor(Object.class, null);
    }  
    
    /**
     * adds an Listener for the clearBT, deletes all filter and search options 
     * in the frame
     */
    @Override
    public void clearSelection(){
        clearBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent aEv){
                box.setSelectedIndex(0);
                ascRadio.setSelected(false);
                descRadio.setSelected(false);
                filterTF.setText("");
                filteredList_rentals = null;
                List <RentalList> wholeList = rlH.getRentallist();
                refreshRentalTable(wholeList);  
            }
        });
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
            public void actionPerformed (ActionEvent aEv) {
                
                if (box.getSelectedIndex() == box.getItemCount()-1) {
                   JOptionPane.showMessageDialog(null, "In dieser Spalte kein Filtern möglich");  
                } else {
                    int whereClause = box.getSelectedIndex();
                    String filterString = filterTF.getText();
                    filteredList_rentals = rlH.filterRentals2(whereClause, filterString);
                    
                    if (filteredList_rentals != null && filteredList_rentals.size() >  0){
                    refreshRentalTable(filteredList_rentals);
                    } else {
                        JOptionPane.showMessageDialog(null, "Filteroption liefert keine Ergebnisse"); 
                    }
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
            public void actionPerformed(ActionEvent e){
                
                if (filteredList_rentals != null && filteredList_rentals.size() > 0 ) {
                    rentalList = filteredList_rentals;
                }
                
                if (ascRadio.isSelected()){
                    descRadio.setSelected(false);
                    int selected = box.getSelectedIndex();
                     switch (selected){
                        case 0:
                            Collections.sort(rentalList, new RentalIDComparator());
                            break;
                        case 1:
                            Collections.sort(rentalList, new RentalInvComparator());
                            break;
                        case 2:
                            Collections.sort(rentalList, new RentallistProNameComparator());
                            break;
                        case 3:
                            Collections.sort(rentalList, new RentallistManuNameComparator());
                            break;
                        case 4:
                            Collections.sort(rentalList, new RentalUserIDComparator());
                            break;
                        case 5:
                            Collections.sort(rentalList, new RentallistLentDaysComparator());
                            break;
                    }
                    refreshRentalTable(rentalList);     
                }
            }
        });
        descRadio.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (filteredList_rentals != null && filteredList_rentals.size() > 0 ) {
                    rentalList = filteredList_rentals;
                }
                
                if (descRadio.isSelected()){
                    ascRadio.setSelected(false);
                    int selected = box.getSelectedIndex();
                    switch (selected){
                        case 0:
                            Collections.sort(rentalList, new RentalIDComparator().reversed());
                            break;
                        case 1:
                            Collections.sort(rentalList, new RentalInvComparator().reversed());
                            break;
                        case 2:
                            Collections.sort(rentalList, new RentallistProNameComparator().reversed());
                            break;
                        case 3:
                            Collections.sort(rentalList, new RentallistManuNameComparator().reversed());
                            break;
                        case 4:
                            Collections.sort(rentalList, new RentalUserIDComparator().reversed());
                            break;
                        case 5:
                            Collections.sort(rentalList, new RentallistLentDaysComparator().reversed());
                            break;
                    }
                    refreshRentalTable(rentalList);
                }
            } 
        });
    }
    
    /**
     * method to initialize the return_panel per click on the 
     * "Geraetereuckgabe"-Button set the textfields in given return_panel with values from 
     * selected row in table, initialize the return_panel 
     * 
     * @param return_panel needed to initialize the return_panel 
     */
    public void newReturn ( JPanel return_panel) {
        
        if (returnBT.getActionListeners().length == 0){
            
            returnBT.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent arg0) {

                    if (!table.getSelectionModel().isSelectionEmpty()) {

                        int index = table.getSelectedRow();

                        int id = 0;
                        long invNumber = 0;
                        long userID = 0;

                        String productname = (String) model.getValueAt(index, 2);
                        String manufacturer = (String) model.getValueAt(index, 3);

                        try {
                            id = (int) model.getValueAt(index, 0);
                        } catch (java.lang.ClassCastException exe ){
                            String rentalIdAsString = (String) model.getValueAt(index, 0);
                            id = Integer.parseInt(rentalIdAsString);
                        }
                        try{
                            invNumber = (long) model.getValueAt(index, 1);
                        } catch (java.lang.ClassCastException exe){
                            String invNumberAsString = (String) model.getValueAt(index, 1);
                            invNumber = Long.parseLong(invNumberAsString);
                        }
                        try{
                            userID = (long) model.getValueAt(index, 4);
                        } catch (java.lang.ClassCastException exe){
                            String userIdAsString = (String) model.getValueAt(index, 4);
                            userID = Long.parseLong(userIdAsString);
                        }

                        RETURN_ID = id;
                        RETURN_PRODUCTNAME = productname;
                        RETURN_MANUFACTURER = manufacturer;
                        RETURN_INVNUMBER = invNumber;
                        RETURN_USERID = userID;

                        lp.removeAll();
                        lp.add(return_panel);
                        lp.repaint();
                        lp.revalidate();
                        
                        //to remove ActionListener in this panel, 
                        //to avoid multiple ActionListener
                        GUI.removeListener(return_panel);

                        Return_Helper returnHelper = new Return_Helper(return_panel, lp, home);
                        returnHelper.notEditable();
                        returnHelper.showData();

                        returnHelper.saveReturn();
                        returnHelper.cancel();
                    }
                }
            });
        } 
    }
}
    


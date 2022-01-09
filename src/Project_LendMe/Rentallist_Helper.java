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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 *
 * @author Katharina
 */
public class Rentallist_Helper extends MyTableHelper implements FilterSortModel{
    
    private final DatabaseHelper rlH = new DatabaseHelper();
    
    public JButton returnBT;
    
    public static int RETURN_ID;
    public static String RETURN_PRODUCTNAME;
    public static String RETURN_MANUFACTURER;
    public static long RETURN_INVNUMBER;
    public static long RETURN_USERID;
    
    public Rentallist_Helper(JTable table, JScrollPane js, JComboBox box, 
                JRadioButton ascRadio, JRadioButton descRadio, 
                JTextField filterTF, JButton filterBT, JButton clearBT, 
                JButton returnBT) {
        super(table, js, box, ascRadio, descRadio, filterTF, filterBT, clearBT);
        
        this.returnBT = returnBT;
        
        this.rentalList = rlH.displayRentallist();
        
        this.columns = new String [] {"ID", "Inventarnummer","Produktname", 
                                "Hersteller","Verliehen an" ,"Verliehene Tage"};
        
        
    }
    

    @Override
    public Object[][] initRentalList(List<Rentallist> rentallist) {
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
        
        table.setEnabled(true);
    }

    @Override
    public void fillBox() {
        super.fillBox(); 
    }
    
     
    /**
     *
     * @param list refresh the table with objects from the given list 
     * of Rentallist-Objects
     */
    public void refreshRentalTable (List<Rentallist> list){ 
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
        table.setEnabled(true);
        js.setVisible(true); 
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
                List <Rentallist> wholeList = rlH.displayRentallist();
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
                int whereClause = box.getSelectedIndex();
                String filterString = filterTF.getText();
                List <Rentallist> filteredList = rlH.filterRentals2(whereClause, filterString);
                if (filteredList.size() >  0){
                    refreshRentalTable(filteredList);
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
            public void actionPerformed(ActionEvent e){
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
    
    public void newReturn (JLayeredPane layeredpane, JPanel return_panel) {
        returnBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
               
                if (!table.getSelectionModel().isSelectionEmpty()) {
                    
                    int index = table.getSelectedRow();
                    int id = (int) model.getValueAt(index, 0);
                    long inventorynumber = (long) model.getValueAt(index, 1);
                    String productname = (String) model.getValueAt(index, 2);
                    String manufacturer = (String) model.getValueAt(index, 3);
                    long user = (long) model.getValueAt(index, 4);
                    
                    RETURN_ID = id;
                    RETURN_PRODUCTNAME = productname;
                    RETURN_MANUFACTURER = manufacturer;
                    RETURN_INVNUMBER = inventorynumber;
                    RETURN_USERID = user;
                    
                    layeredpane.removeAll();
                    layeredpane.add(return_panel);
                    layeredpane.repaint();
                    layeredpane.revalidate();
                    
                    ReturnHelper returnHelper = new ReturnHelper(return_panel);
                    returnHelper.notEditable();
                    returnHelper.showData();
                    returnHelper.saveReturn();
                    
                }
            }
        });
    } 
}
    


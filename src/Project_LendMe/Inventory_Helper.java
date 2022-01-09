/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import Comparators.InventoryAcqDateComparator;
import Comparators.InventoryAcqValueComparator;
import Comparators.InventoryLocationComparator;
import Comparators.InventoryManufacturerComparator;
import Comparators.InventoryNumberComparator;
import Comparators.InventoryProductnameComparator;
import Comparators.InventoryStatusComparator;
import Comparators.InventoryUserIDComparator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Katharina, bstra
 */



public class Inventory_Helper extends MyTableHelper implements FilterSortModel{
    
   private final DatabaseHelper dbH = new DatabaseHelper();
   private final JButton updateRow;
    
    public Inventory_Helper(JTable table, JScrollPane js, JComboBox box, 
            JRadioButton ascRadio, JRadioButton descRadio, JTextField filterTF, 
            JButton filterBT, JButton clearBT, JButton updateRow) {
        super(table, js, box, ascRadio, descRadio, filterTF, filterBT, clearBT);
        
        this.updateRow = updateRow;
        this.allDevices = dbH.getAllDevices();
        this.columns =  new String [] {"Inventarnr.", "Hersteller", 
                                        "Produktname",  "Notizen", 
                                        "Ort", "Status", "IMEI", "Verliehen an", 
                                        "Anschaffungswert", "Anschaffungsdatum"};
    }

    @Override
    public Object[][] initDeviceList(List<Devices> devicelist) {
        Object [][] data = super.initDeviceList(devicelist);
        return data;
    }

    @Override
    public void populateTable() {
    super.populateTable();
    
    TableColumnModel colModel = table.getColumnModel();
    colModel.getColumn(0).setPreferredWidth(80);
    colModel.getColumn(1).setPreferredWidth(100);
    colModel.getColumn(2).setPreferredWidth(100);
    colModel.getColumn(3).setPreferredWidth(50);
    colModel.getColumn(4).setPreferredWidth(50);
    colModel.getColumn(5).setPreferredWidth(50);
    colModel.getColumn(6).setPreferredWidth(70);
    colModel.getColumn(7).setPreferredWidth(80);
    colModel.getColumn(8).setPreferredWidth(125);
    colModel.getColumn(9).setPreferredWidth(125);
    
    table.setEnabled(true);
    }

    @Override
    public void fillBox() {
        String [] sortableBy = new String [] {"Inventarnummer", "Hersteller", 
                                        "Produktname", "Ort", "Status", "Verliehen an", 
                                        "Anschaffungswert", "Anschaffungsdat."};
        box.setModel(new DefaultComboBoxModel<> (sortableBy));
    }

    public void refreshDevicesTable (List<Devices> list) {
        data = initDeviceList(list);

        model = new DefaultTableModel(data, columns);
        
        table.setModel(model);
        
        TableColumnModel colModel = table.getColumnModel();
        
        colModel.getColumn(0).setPreferredWidth(80);
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(2).setPreferredWidth(100);
        colModel.getColumn(3).setPreferredWidth(50);
        colModel.getColumn(4).setPreferredWidth(50);
        colModel.getColumn(5).setPreferredWidth(50);
        colModel.getColumn(6).setPreferredWidth(70);
        colModel.getColumn(7).setPreferredWidth(80);
        colModel.getColumn(8).setPreferredWidth(125);
        colModel.getColumn(9).setPreferredWidth(125);
        
        table.setRowHeight(25);
        
        table.setFillsViewportHeight(true);
        if (table.getPreferredSize().getHeight() < js.getPreferredSize().getHeight()){
        table.setPreferredSize(js.getPreferredSize());
        }
        table.setEnabled(true);
        js.setVisible(true);
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>();
        table.setRowSorter(sorter);
        sorter.setModel(model);
    }
    
    @Override
    public void filterTable() {
        filterBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filterByColumn = box.getSelectedIndex();
                String filterByUserInput = filterTF.getText();
                List <Devices> filteredList = dbH.filterInventory(filterByColumn, filterByUserInput);
                if (filteredList.size() > 0){
                    refreshDevicesTable(filteredList);
                } else {
                    JOptionPane.showMessageDialog(null, "Filteroption liefert keine Ergebnisse");
                }
            }
        });
    }

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
                            Collections.sort(allDevices, new InventoryNumberComparator());
                            break;
                        case 1 :
                            Collections.sort(allDevices, new InventoryManufacturerComparator());
                            break;
                        case 2 :
                            Collections.sort(allDevices, new InventoryProductnameComparator());
                            break;
                        case 3 :
                            Collections.sort(allDevices, new InventoryLocationComparator());
                            break;
                        case 4 :
                            Collections.sort(allDevices, new InventoryStatusComparator());
                            break;
                        case 5 :
                            Collections.sort(allDevices, new InventoryUserIDComparator());
                            break;
                        case 6 :
                            Collections.sort(allDevices, new InventoryAcqValueComparator());
                            break;
                        case 7 :
                            Collections.sort(allDevices, new InventoryAcqDateComparator());
                            break;
                    }
                    refreshDevicesTable(allDevices);
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
                            Collections.sort(allDevices, new InventoryNumberComparator().reversed());
                            break;
                        case 1 :
                            Collections.sort(allDevices, new InventoryManufacturerComparator().reversed());
                            break;
                        case 2 :
                            Collections.sort(allDevices, new InventoryProductnameComparator().reversed());
                            break;
                        case 3 :
                            Collections.sort(allDevices, new InventoryLocationComparator().reversed());
                            break;
                        case 4 :
                            Collections.sort(allDevices, new InventoryStatusComparator().reversed());
                            break;
                        case 5 :
                            Collections.sort(allDevices, new InventoryUserIDComparator().reversed());
                            break;
                        case 6 :
                            Collections.sort(allDevices, new InventoryAcqValueComparator().reversed());
                            break;
                        case 7 :
                            Collections.sort(allDevices, new InventoryAcqDateComparator().reversed());
                            break;
                    }
                    refreshDevicesTable(allDevices);
                }
            }
        });
    }

    @Override
    public void clearSelection() {
        clearBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                box.setSelectedIndex(0);
                ascRadio.setSelected(false);
                descRadio.setSelected(false);
                filterTF.setText("");
                List <Devices> devs = dbH.getAllDevices();
                refreshDevicesTable(devs);
            }        
        });
    }
    
    public void update () {
        updateRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
    }
    
    
    
}



    


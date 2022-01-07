/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Katharina, bstra
 */



public class Inventory_Helper extends MyTableHelper implements FilterSortModel{
    
   private final DatabaseHelper dbH = new DatabaseHelper();
    
    public Inventory_Helper(JTable table, JScrollPane js, JComboBox box, 
            JRadioButton ascRadio, JRadioButton descRadio, JTextField filterTF, 
            JButton filterBT, JButton clearBT) {
        super(table, js, box, ascRadio, descRadio, filterTF, filterBT, clearBT);
        
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
    }
    
    @Override
    public void filterTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sortTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    
}

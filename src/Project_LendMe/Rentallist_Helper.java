/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Katharina
 */
public class Rentallist_Helper extends MyTableHelper {
    
    private final DatabaseHelper rlH = new DatabaseHelper();

    public Rentallist_Helper(JTable table, JScrollPane js, JComboBox box, 
                JRadioButton ascRadio, JRadioButton descRadio, 
                JTextField filterTF, JButton filterBT) {
        super(table, js, box, ascRadio, descRadio, filterTF, filterBT);
        
        this.rentalList = rlH.displayRentallist();
        this.columns = new String [] {"ID", "Inventarnummer","Produktname", 
                                "Herstellername","Verliehen an" ,"Verleihdatum"};
        
    }
    
    
    @Override
    public Object[][] initRentalList(List<Rentallist> rentallist) {
        Object [] [] data = super.initRentalList(rentallist);
        return data; 
    }

    @Override
    public void populateTable() {
        super.populateTable(); 
        
    }

    @Override
    public void fillBox() {
        super.fillBox(); 
    }
    
    public void filterRentalTable() {
        filterBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent aev) {
                int whereClause = box.getSelectedIndex();
                String filterString = filterTF.getText();
                List <Rentallist> filteredList = rlH.filterRentals2(whereClause, filterString);
                if (filteredList != null){
                    refreshRentalTable(filteredList);
                } else {
                    JOptionPane.showMessageDialog(null, "Filteroption liefert keine Ergebnisse"); 
                }
            }
        });
    }
    
     public void refreshRentalTable (List<Rentallist> list){ 
        data = initRentalList(list);
        model = new DefaultTableModel(data, columns);
        table.setModel(model);
        table.setEnabled(true);
        js.setVisible(true); 
   }    
    
}
    


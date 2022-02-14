package Project_LendMe;

import Comparators.InventoryAdminIdComparator;
import Comparators.InventoryManufacturerComparator;
import Comparators.InventoryNumberComparator;
import Comparators.InventoryProductnameComparator;
import com.raven.datechooser.DateChooser;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 * Helper class handling logic connected to Inventory Table
 *
 * @author Katharina, bstra
 */
public class Inventory_Helper extends MyTableHelper implements FilterSortModel {

    private final DatabaseHelper dbH = new DatabaseHelper();
    private final JButton updateRow;
    private final JButton deleteRow;
    private final JButton insertDevice;
    private final JRadioButton equals;
    private final JRadioButton lower;
    private final JRadioButton larger;

    private JLayeredPane lp;
    private JPanel inventory_panel;
    private ButtonGroup pg = new ButtonGroup();
    private List<Devices> filteredList = null;

    public Inventory_Helper(JTable table, JScrollPane js, JComboBox box,
            JRadioButton ascRadio, JRadioButton descRadio, JTextField filterTF,
            JButton filterBT, JButton clearBT, JButton updateRow, JButton deleteRow,
            JButton insertDevice, JLayeredPane lp, JPanel inventory_panel,
            JRadioButton equals, JRadioButton lower, JRadioButton larger) {
        super(table, js, box, ascRadio, descRadio, filterTF, filterBT, clearBT);

        this.updateRow = updateRow;
        this.deleteRow = deleteRow;
        this.insertDevice = insertDevice;
        this.lp = lp;
        this.inventory_panel = inventory_panel;
        // set up Table Data
        this.allDevices = dbH.getAllDevices2();
        // set up Column Names
        this.columns = new String[]{"InvNr.", "Hersteller",
            "Produktname", "Admin"};
        //set up additional Filter options
        this.larger = larger;
        this.lower = lower;
        this.equals = equals;

    }

    //Override initDeviceList setting up correct data
    @Override
    public Object[][] initDeviceList(List<Devices> devicelist) {
        Object[][] data = super.initDeviceList(devicelist);
        return data;
    }

    /**
     * set up TableColumnModel for new Table
     */
    @Override
    public void populateTable() {
        super.populateTable();

        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(80);
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(2).setPreferredWidth(125);
        colModel.getColumn(3).setPreferredWidth(50);

    }

    /**
     * Pass Values for filter/sort/search Combobox
     */
    @Override
    public void fillBox() {
        String[] sortableBy = new String[]{"Inventarnummer", "Hersteller",
            "Produktname", "Admin"};
        box.setModel(new DefaultComboBoxModel<>(sortableBy));
        // "Ort", "Status", "Verliehen an", "Anschaffungswert", "Anschaffungsdat.",
    }

    /**
     * initialize Filter Buttons add them to ButtonGroup
     */
    public void setFilterButtons() {

        pg.add(equals);
        pg.add(lower);
        pg.add(larger);

        //default: = option selected
        equals.setSelected(true);

    }

    /**
     * get selected Button, assign a number depending on which Button is
     * selected since there is a default selection and all Buttons are in a
     * Group, there is no way that no button can be selected
     *
     * @return the int for the selected button
     */
    public int getSelectedFilterButton() {
        if (equals.isSelected()) {
            return 1;
        } else if (lower.isSelected()) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * handles logic when filter/sort/search Buttons are pressed refreshes table
     * with corresponding data
     *
     * @param list the filtered/sorted/searched results
     */
    public void refreshDevicesTable(List<Devices> list) {
        data = initDeviceList(list);

        model = new DefaultTableModel(data, columns);

        table.setModel(model);

        TableColumnModel colModel = table.getColumnModel();

        colModel.getColumn(0).setPreferredWidth(80);
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(2).setPreferredWidth(100);
        colModel.getColumn(3).setPreferredWidth(50);

        table.setRowHeight(25);

        table.setFillsViewportHeight(true);
        if (table.getPreferredSize().getHeight() < js.getPreferredSize().getHeight()) {
            table.setPreferredSize(js.getPreferredSize());
        }
        table.setEnabled(true);
        js.setVisible(true);

    }

    /**
     * queries DB with user Input, returns corresponding results
     */
    @Override
    public void filterTable() {
        filterBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filterByColumn = box.getSelectedIndex();
                String filterByUserInput = filterTF.getText();
                //queries DB with Filter Options desired from the user
                //passes Column to filter By, Value to Filter by, Option to Filter By (<, >, =)
                filteredList = dbH.filterInventory(filterByColumn, filterByUserInput,
                        getSelectedFilterButton()); // filtered list befuellen
                if (filteredList.size() > 0) {
                    refreshDevicesTable(filteredList);
                } else {
                    JOptionPane.showMessageDialog(null, "Filteroption liefert keine Ergebnisse");
                }
            }
        });
    }

    /**
     * Queries DB with selected Sort-Options, returns corresponding results
     */
    @Override
    public void sortTable() {
        ascRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent aev) {

                // wenn etwas in filteredList drinnen ist, also zuvor gefiltered wurde
                // initalisere allDevices list mit der filteredList
                // so wird dann nur die filteredList sortiert
                if (filteredList != null && filteredList.size() > 0) {
                    allDevices = filteredList;
                }

                if (ascRadio.isSelected()) {
                    descRadio.setSelected(false);
                    int selected = box.getSelectedIndex();
                    switch (selected) {
                        case 0:
                            Collections.sort(allDevices, new InventoryNumberComparator());
                            break;
                        case 1:
                            Collections.sort(allDevices, new InventoryManufacturerComparator());
                            break;
                        case 2:
                            Collections.sort(allDevices, new InventoryProductnameComparator());
                            break;
                        case 3:
                            Collections.sort(allDevices, new InventoryAdminIdComparator());
                            break;
                    }
                    refreshDevicesTable(allDevices);
                }
            }
        });

        descRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent aev) {

                // wenn etwas in filteredList drinnen ist, also zuvor gefiltered wurde
                // initalisere allDevices list mit der filteredList
                // so wird dann nur die filteredList sortiert
                if (filteredList != null && filteredList.size() > 0) {
                    allDevices = filteredList;
                }

                if (descRadio.isSelected()) {
                    ascRadio.setSelected(false);
                    int selected = box.getSelectedIndex();
                    switch (selected) {
                        case 0:
                            Collections.sort(allDevices, new InventoryNumberComparator().reversed());
                            break;
                        case 1:
                            Collections.sort(allDevices, new InventoryManufacturerComparator().reversed());
                            break;
                        case 2:
                            Collections.sort(allDevices, new InventoryProductnameComparator().reversed());
                            break;
                        case 3:
                            Collections.sort(allDevices, new InventoryAdminIdComparator().reversed());
                            break;
                    }
                    refreshDevicesTable(allDevices);
                }
            }
        });
    }

    /**
     * Clears all sort/filter/search options and resets table
     */
    @Override
    public void clearSelection() {
        clearBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                box.setSelectedIndex(0);
                ascRadio.setSelected(false);
                descRadio.setSelected(false);
                filterTF.setText("");
                List<Devices> devs = dbH.getAllDevices2();
                refreshDevicesTable(devs);
            }
        });
    }

    /**
     * Listener for Button 'Bearbeiten' in Inventory-Table; fills String[] with
     * values of selected Row
     *
     * @param invUpdate_panel pass element to constructor
     * @param invNo pass element to constructor
     * @param manu pass element to constructor
     * @param pn pass element to constructor
     * @param notes pass element to constructor
     * @param loc pass element to constructor
     * @param adminID pass element to constructor
     * @param acqV pass element to constructor
     * @param acqD pass element to constructor
     * @param imei pass element to constructor
     * @param save pass element to constructor
     * @param cancel pass element to constructor
     */
    public void update(JPanel invUpdate_panel, JTextField invNo, JTextField manu,
            JTextField pn, JTextArea notes, JTextField loc, JTextField imei,
            JTextField acqV, DateChooser acqD, JComboBox adminID,
            JButton save, JButton cancel) {

        updateRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int indexRow = table.getSelectedRow();
                try {
                    String invNoToCheck = table.getValueAt(indexRow, 0).toString();

                    InventoryUpdate_Helper invUpd_Helper = new InventoryUpdate_Helper(
                            invNoToCheck, invNo, manu, pn, notes, loc, imei, acqV, acqD, adminID,
                            save, cancel);

                    lp.removeAll();
                    lp.add(invUpdate_panel);
                    lp.repaint();
                    lp.revalidate();

                    //remove Listeners to avoid multiple Listeners at once
                    GUI.removeListener(invUpdate_panel);

                    invUpd_Helper.fillTFs();
                    invUpd_Helper.setKeyListener();
                    invUpd_Helper.setInputVerifiers();
                    invUpd_Helper.checkForUpdate();
                    invUpd_Helper.resetTfs();

                    /*
                    If Update Button is clicked when no Device is selected, the method
                    throws an ArrayIndexOutofBoundsException, which is handled here.
                   */
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println(ex);
                }

            }
        });

    }

    /**
     * Handles click on 'Neues Device anlegen'
     * @param invInsert_panel pass element to constructor
     * @param insertPN pass element to constructor
     * @param insertManu pass element to constructor
     * @param insertInvNo pass element to constructor
     * @param insertImei pass element to constructor
     * @param insertLoc pass element to constructor
     * @param insertNotes pass element to constructor
     * @param insertAdmin pass element to constructor
     * @param insertAV pass element to constructor
     * @param insertAD pass element to constructor
     * @param insertSave pass element to constructor
     * @param insertClear pass element to constructor
     * 
     */
    public void insert(JPanel invInsert_panel, JTextField insertPN, JTextField insertManu,
            JTextField insertInvNo, JTextField insertImei, JTextField insertLoc,
            JTextArea insertNotes, JComboBox insertAdmin, JTextField insertAV,
            DateChooser insertAD, JButton insertSave, JButton insertClear) {

        //set ClickListener
        insertDevice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                InventoryNew_Helper invNew = new InventoryNew_Helper(insertPN,
                        insertManu, insertInvNo, insertImei, insertLoc, insertNotes,
                        insertAdmin, insertAV, insertAD, insertSave, insertClear);

                //repaint Panel
                lp.removeAll();
                lp.add(invInsert_panel);
                lp.repaint();
                lp.revalidate();

                //remove Listeners
                GUI.removeListener(invInsert_panel);

                // set up input Verifiers for Textfields          
                invNew.setInputVerifiers();
                //fill Admin-Combobox with values from DB
                invNew.fillCbAdmin();
                //set up Click Listeners
                invNew.saveClickListener();
                invNew.clearClickListener();

            }

        });
    }

    /**
     * deletes selected Row and refreshes Table
     */
    public void delete() {
        deleteRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexRow = table.getSelectedRow();

                String invNo = table.getValueAt(indexRow, 0).toString();

                try {
                    int i = dbH.deleteDevice(invNo);
                    if (i == 1) {
                        JOptionPane.showMessageDialog(null, "Datensatz gelöscht", "Success", 1);
                    }

                } catch (SQLException ex) {
                    System.out.println(ex);
                }
                try {

                    //no row selected
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, //no owner frame
                            "Bitte eine Zeile auswählen", //text to display
                            "Error", //title
                            JOptionPane.WARNING_MESSAGE);
                }
                //query for new Device-List, refresh table
                List<Devices> devs = dbH.getAllDevices2();
                refreshDevicesTable(devs);

            }

        });
    }

    /**
     * Displays additional Information for the row the user Double-Clicks on
     *
     * @param invInfo_panel pass element to constructor
     * @param invNo pass element to constructor
     * @param productname pass element to constructor
     * @param manufacturer pass element to constructor
     * @param imei pass element to constructor
     * @param location pass element to constructor
     * @param acqValue pass element to constructor
     * @param acqDate pass element to constructor
     * @param notes pass element to constructor
     * @param adminId pass element to constructor
     * @param adminName pass element to constructor
     * @param back pass element to constructor
     *
     */
    public void rowDoubleClick(JPanel invInfo_panel, JTextField invNo, JTextField productname,
            JTextField manufacturer, JTextField imei, JTextField location,
            JTextField acqValue, JTextField acqDate, JTextArea notes,
            JTextField adminId, JTextField adminName, JButton back) {

        int i = table.getMouseListeners().length;
        
        if (i <= 2 && i < 4 ){
        
            //add the double-click Listener
            this.table.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                    DefaultTableModel actual_model = (DefaultTableModel) table.getModel();
                    // on which table in which row did the user click?
                    table = (JTable) me.getSource();
                    Point point = me.getPoint();
                    int row = table.rowAtPoint(point);

                    if (me.getClickCount() == 2 && table.getSelectedRow() != -1) {
                        int index = row;

                        String invNoToCheck = String.valueOf(actual_model.getValueAt(index, 0));
                        int adminID = (int) actual_model.getValueAt(index, 3);

                        //set up Information-Panel
                        lp.removeAll();
                        lp.add(invInfo_panel);
                        lp.repaint();
                        lp.revalidate();

                        //remove Listeners to avoid multiple Listeners
                        GUI.removeListener(invInfo_panel);

                        InventoryInfo_Helper invInfo = new InventoryInfo_Helper(invInfo_panel,
                                lp, inventory_panel, invNo, productname, manufacturer, imei, location,
                                acqValue, acqDate, notes, adminId, adminName, back, adminID, invNoToCheck);

                        invInfo.notEditable();
                        invInfo.showData();
                        invInfo.back();

                    }

                }

            });
        }
    }

}

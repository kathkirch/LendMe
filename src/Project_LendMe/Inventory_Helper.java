package Project_LendMe;

import Comparators.InventoryAcqDateComparator;
import Comparators.InventoryAcqValueComparator;
import Comparators.InventoryAdminIdComparator;
import Comparators.InventoryLocationComparator;
import Comparators.InventoryManufacturerComparator;
import Comparators.InventoryNumberComparator;
import Comparators.InventoryProductnameComparator;
import Comparators.InventoryStatusComparator;
import Comparators.InventoryUserIDComparator;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
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
import javax.swing.table.TableRowSorter;

/**
 * Helper class handling logic connected to Inventory Table
 *
 * @author Katharina, bstra
 */
public class Inventory_Helper extends MyTableHelper implements FilterSortModel {

    private final DatabaseHelper dbH = new DatabaseHelper();
    private final JButton updateRow;
    private final JButton deleteRow;
    private static String[] selectedRow;
    private static Boolean openUpdatePanel;

    private JLayeredPane lp;
    private JPanel inventory_panel;

    private static List<Devices> filteredList = null; //static List damit man von dieser Klasse aus immer zugreifen kann

    public Inventory_Helper(JTable table, JScrollPane js, JComboBox box,
            JRadioButton ascRadio, JRadioButton descRadio, JTextField filterTF,
            JButton filterBT, JButton clearBT, JButton updateRow, JButton deleteRow,
            JLayeredPane lp, JPanel inventory_panel) {
        super(table, js, box, ascRadio, descRadio, filterTF, filterBT, clearBT);

        this.updateRow = updateRow;
        this.deleteRow = deleteRow;
        this.lp = lp;
        this.inventory_panel = inventory_panel;
        // set up Table Data
        this.allDevices = dbH.getAllDevices2(); //changed to getAllDevices2 (Methode ohne administrators has devices table)
        // set up Column Names
        this.columns = new String[]{"InvNr.", "Hersteller",
            "Produktname", "Notizen",
            "Ort", "Status", "IMEI", "Verliehen an",
            "Ansch.-Wert", "Ansch.-Datum",
            "Admin"};
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
        colModel.getColumn(4).setPreferredWidth(75);
        colModel.getColumn(5).setPreferredWidth(50);
        colModel.getColumn(6).setPreferredWidth(70);
        colModel.getColumn(7).setPreferredWidth(80);
        colModel.getColumn(8).setPreferredWidth(125);
        colModel.getColumn(9).setPreferredWidth(125);
        colModel.getColumn(10).setPreferredWidth(80);

        table.setEnabled(true);

    }

    /**
     * Pass Values for filter/sort/search Combobox
     */
    @Override
    public void fillBox() {
        String[] sortableBy = new String[]{"Inventarnummer", "Hersteller",
            "Produktname", "Ort", "Status", "Verliehen an",
            "Anschaffungswert", "Anschaffungsdat.", "Admin"};
        box.setModel(new DefaultComboBoxModel<>(sortableBy));
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
        colModel.getColumn(4).setPreferredWidth(50);
        colModel.getColumn(5).setPreferredWidth(50);
        colModel.getColumn(6).setPreferredWidth(70);
        colModel.getColumn(7).setPreferredWidth(80);
        colModel.getColumn(8).setPreferredWidth(125);
        colModel.getColumn(9).setPreferredWidth(125);
        colModel.getColumn(10).setPreferredWidth(125);

        table.setRowHeight(25);

        table.setFillsViewportHeight(true);
        if (table.getPreferredSize().getHeight() < js.getPreferredSize().getHeight()) {
            table.setPreferredSize(js.getPreferredSize());
        }
        table.setEnabled(true);
        js.setVisible(true);

        //allow sorting by clicking on column names
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>();
        table.setRowSorter(sorter);
        sorter.setModel(model);
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
                filteredList = dbH.filterInventory(filterByColumn, filterByUserInput); // filtered list befuellen
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
                            Collections.sort(allDevices, new InventoryLocationComparator());
                            break;
                        case 4:
                            Collections.sort(allDevices, new InventoryStatusComparator());
                            break;
                        case 5:
                            Collections.sort(allDevices, new InventoryUserIDComparator());
                            break;
                        case 6:
                            Collections.sort(allDevices, new InventoryAcqValueComparator());
                            break;
                        case 7:
                            Collections.sort(allDevices, new InventoryAcqDateComparator());
                            break;
                        case 8:
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
                            Collections.sort(allDevices, new InventoryLocationComparator().reversed());
                            break;
                        case 4:
                            Collections.sort(allDevices, new InventoryStatusComparator().reversed());
                            break;
                        case 5:
                            Collections.sort(allDevices, new InventoryUserIDComparator().reversed());
                            break;
                        case 6:
                            Collections.sort(allDevices, new InventoryAcqValueComparator().reversed());
                            break;
                        case 7:
                            Collections.sort(allDevices, new InventoryAcqDateComparator().reversed());
                            break;
                        case 8:
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
                List<Devices> devs = dbH.getAllDevices2(); //changed to getAllDevices2 (Methode ohne administrators has devices table)
                refreshDevicesTable(devs);
            }
        });
    }

    /**
     * Listener for Button 'Bearbeiten' in Inventory-Table fills String[] with
     * values of selected Row
     */
    public void update() {
        updateRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                selectedRow = new String[11];

                try {
                    int indexRow = table.getSelectedRow();

                    for (int i = 0; i <= 10; i++) {

                        if (table.getValueAt(indexRow, i) == null) {
                            selectedRow[i] = "";
                        } else {
                            selectedRow[i] = table.getValueAt(indexRow, i).toString();
                        }

                        //Helper Boolean: tells Click-Listener in GUI to initialize Inventory-Update-Panel
                        openUpdatePanel = true;
                    }
                    //No Row Selected
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, //no owner frame
                            "Bitte eine Zeile auswählen", //text to display
                            "Error", //title
                            JOptionPane.WARNING_MESSAGE);
                    openUpdatePanel = false;
                }

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

                selectedRow = new String[11];
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

    public void rowDoubleClick(JPanel invInfo_panel, JTextField invNo, JTextField productname,
            JTextField manufacturer, JTextField imei, JTextField location,
            JTextField acqValue, JTextField acqDate, JTextArea notes,
            JTextField adminId, JTextField adminName, JButton back) {

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point point = me.getPoint();
                int row = table.rowAtPoint(point);

                if (me.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int index = row;

                    long invNoToCheck = (long) model.getValueAt(index, 0);
                    int adminID = (int) model.getValueAt(index, 10);

                    lp.removeAll();
                    lp.add(invInfo_panel);
                    lp.repaint();
                    lp.revalidate();

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

    /**
     *
     * @return String [] filled with Data of selected Row in Inventory-Table
     */
    public static String[] getSelectedRow() {
        return selectedRow;
    }

    /**
     *
     * @return helper boolean to check if a row was selected
     */
    public static Boolean getOpenUpdatePanel() {
        return openUpdatePanel;
    }

}

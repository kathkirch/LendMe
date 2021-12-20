/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Project_LendMe;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Anja, Katharina
 */
public class GUI extends javax.swing.JFrame implements Runnable {
    
    private final DatabaseHelper hp = new DatabaseHelper();
    private final Rental_Helper rentalHelper = new Rental_Helper();
    private final ArchivHelper archHelper = new ArchivHelper();
    
    
    private final Rentallist_Helper rentallistHelper = new Rentallist_Helper();
    private final Return returndata= new Return();
    
    
     /**
     * Creates new form GUI
     */
    public GUI() {
        
        initComponents();
        
        fillDropdowns();
        
        listenForSelectionPN();
        listenForSelectionM();
        listenForSelectionIN();
        listenForSelectionUID();
        listenForSelectionAID();
        
    }
    
    /**
    *
    * @author Katharina
    */
    public final void fillDropdowns() {
        
        rentalHelper.fillComboBox_Category(productname_newrental, "productName");
        rentalHelper.fillComboBox_Category(inventorynumber_newrental, "inventoryNumber");
        rentalHelper.fillComboBox_Category(manufacturer_newrental, "manufacturer");
        rentalHelper.fillUser_Year_Admin(userID_newrental,
                                            year_newrental, 
                                            administrator_newrental);
    }
    
    /**
    *
    * @author Katharina
    */
    public final void listenForSelectionPN () {
            productname_newrental.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    
                    String selected = e.getItem().toString();
               
                    if (productname_newrental.getItemCount() == 0 ){
                        selected = ""; 
                        rentalHelper.fillComboBox_Category(productname_newrental, "productName");
                    }
                
                    if (!selected.isBlank()){
                        List <Devices> list = hp.getItemByProductName(selected);
                        String [] categories = new String [] {"manufacturer", "inventoryNumber"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = hp.makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("manufacturer")){
                                manufacturer_newrental.setModel
                            (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                            if (categories[i].equalsIgnoreCase("inventoryNumber")){
                                inventorynumber_newrental.setModel
                            (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                        }
                    }
                }
            }
        });
    }
    
    /**
    *
    * @author Katharina
    */
    public final void listenForSelectionM () {
        manufacturer_newrental.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                     String selected = e.getItem().toString();
                
                    if (manufacturer_newrental.getItemCount() == 0 ){
                        selected = ""; 
                        rentalHelper.fillComboBox_Category(manufacturer_newrental, "manufacturer");
                    }
                    if (!selected.isBlank()){
                        List <Devices> list = hp.getItemByManufacturer(selected);
                        String [] categories = new String [] {"productName", "inventoryNumber"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = hp.makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("productName")){
                                productname_newrental.setModel
                            (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                        }
                    }
                }
            }
        }); 
    }
    
    /**
    *
    * @author Katharina
    */
    public final void listenForSelectionIN () {
        inventorynumber_newrental.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    String selected = e.getItem().toString();
                
                    if (inventorynumber_newrental.getItemCount() == 0 ){
                        selected = ""; 
                        rentalHelper.fillComboBox_Category(inventorynumber_newrental, "inventoryNumber");
                        
                    }
                
                    if (!selected.isBlank() && hp.isNumeric(selected)){
                        List <Devices> list = hp.getItemByInvNumber(selected);
                        String [] categories = new String [] {"productName", "manufacturer"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = hp.makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("productName")){
                                productname_newrental.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                            if (categories[i].equalsIgnoreCase("manufacturer")){
                                manufacturer_newrental.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                        }
                    }
                }  
            }
        }); 
    }
    
    /**
    *
    * @author Katharina
    */
    public final void listenForSelectionUID () {
        userID_newrental.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                String selected = e.getItem().toString();
                if ((!selected.isBlank() && hp.isNumeric(selected))){
                    Users userToCheck = hp.checkUserID(selected);
                    if (userToCheck != null && (!hp.isUserNew(selected))) {
                        userFirstName.setText(userToCheck.getUserFirstName());
                        userLastName.setText(userToCheck.getUserLastName());
                        userPhone.setText(userToCheck.getUserPhone());
                        userEmail.setText(userToCheck.getUserEmail());
                        year_newrental.setSelectedItem(userToCheck.getYear());
                        year_newrental.setEnabled(false);
                    } else if (hp.isUserNew(selected)) {
                        userFirstName.setText("");
                        userLastName.setText("");
                        userPhone.setText("");
                        userEmail.setText("");
                        year_newrental.setSelectedItem("");
                        year_newrental.setEnabled(true);
                    } 
                }
            }
        });
    }
    
    /**
    *
    * @author Katharina
    */
    public final void listenForSelectionAID() {
        administrator_newrental.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                String selected = e.getItem().toString();
                if ((!selected.isBlank() && hp.isNumeric(selected))) {
                    adminFullName.setText(hp.getAdminNameByID(selected));
                }
            }
        });
    }
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toppenal = new javax.swing.JPanel();
        parentpanel = new javax.swing.JPanel();
        sidepanel = new javax.swing.JPanel();
        newrental = new javax.swing.JButton();
        rentallist = new javax.swing.JButton();
        archive = new javax.swing.JButton();
        inventory = new javax.swing.JButton();
        startpage = new javax.swing.JPanel();
        scrollpane_startpage = new javax.swing.JScrollPane();
        layerpane = new javax.swing.JLayeredPane();
        home_panel = new javax.swing.JPanel();
        newrental_panel = new javax.swing.JPanel();
        newrentaltitle = new javax.swing.JLabel();
        devicename = new javax.swing.JLabel();
        manufacturername = new javax.swing.JLabel();
        inventory_number = new javax.swing.JLabel();
        rentedby = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        productname_newrental = new javax.swing.JComboBox<>();
        manufacturer_newrental = new javax.swing.JComboBox<>();
        inventorynumber_newrental = new javax.swing.JComboBox<>();
        userID_newrental = new javax.swing.JComboBox<>();
        userFirstName = new javax.swing.JTextField();
        userLastName = new javax.swing.JTextField();
        userEmail = new javax.swing.JTextField();
        userPhone = new javax.swing.JTextField();
        year_newrental = new javax.swing.JComboBox<>();
        administrator_newrental = new javax.swing.JComboBox<>();
        date = new javax.swing.JLabel();
        rentalDate_newrental = new com.raven.datechooser.DateChooser();
        save_newrental = new javax.swing.JToggleButton();
        cancel_newrental = new javax.swing.JToggleButton();
        adminFullName = new javax.swing.JTextField();
        rentallist_panel = new javax.swing.JPanel();
        rentallist_title = new javax.swing.JLabel();
        searchfilter_rentallist = new javax.swing.JComboBox<>();
        filter_options_rentallist = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        rentallist_table = new javax.swing.JTable();
        return_button = new javax.swing.JButton();
        archive_panel = new javax.swing.JPanel();
        archive_title = new javax.swing.JLabel();
        searchfilter_archive = new javax.swing.JComboBox<>();
        filter_options_archive = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        archive_table = new javax.swing.JTable();
        inventory_panel = new javax.swing.JPanel();
        newdevice_button = new javax.swing.JButton();
        inventory_title = new javax.swing.JLabel();
        searchfilter_inventory = new javax.swing.JComboBox<>();
        filter_options_inventory = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        inventory_table = new javax.swing.JTable();
        return_panel = new javax.swing.JPanel();
        productname_return = new javax.swing.JLabel();
        re_productname = new javax.swing.JTextField();
        manufacturer_return = new javax.swing.JLabel();
        return_title = new javax.swing.JLabel();
        rentedby_return = new javax.swing.JLabel();
        date_return = new javax.swing.JLabel();
        administrator_return = new javax.swing.JLabel();
        returndate = new com.raven.datechooser.DateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        re_withdrawnby = new javax.swing.JComboBox<>();
        notestitle_return = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        re_notes = new javax.swing.JTextArea();
        defaultsettings = new javax.swing.JLabel();
        yes = new javax.swing.JRadioButton();
        no = new javax.swing.JRadioButton();
        save_return = new javax.swing.JToggleButton();
        cancel_return = new javax.swing.JToggleButton();
        re_manufacturer = new javax.swing.JTextField();
        re_user = new javax.swing.JTextField();
        re_rentaldate = new javax.swing.JTextField();
        re_administrator = new javax.swing.JTextField();
        inventorynumber = new javax.swing.JLabel();
        re_inventorynumber = new javax.swing.JTextField();
        newdevice_panel = new javax.swing.JPanel();
        newdevice_title = new javax.swing.JLabel();
        devicename_newdevice = new javax.swing.JLabel();
        manufacturer_newdevice = new javax.swing.JLabel();
        inventorynumber_newdevice = new javax.swing.JLabel();
        IMEInumber_newdevice = new javax.swing.JLabel();
        acquisitiondate_newdevice = new javax.swing.JLabel();
        acquisitionDate = new com.raven.datechooser.DateChooser();
        save_newdevice = new javax.swing.JToggleButton();
        cancel_newdevice = new javax.swing.JToggleButton();
        manufacturer = new javax.swing.JTextField();
        productname = new javax.swing.JTextField();
        inventoryNumber = new javax.swing.JTextField();
        imei = new javax.swing.JTextField();
        room_newdevice = new javax.swing.JLabel();
        location = new javax.swing.JTextField();
        notes_newdevice = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notes = new javax.swing.JTextArea();
        administrator_newdevice = new javax.swing.JLabel();
        administrator = new javax.swing.JComboBox<>();
        acquisitionvalue_newdevice = new javax.swing.JLabel();
        acquisitionValue = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));

        toppenal.setBackground(new java.awt.Color(87, 121, 50));
        toppenal.setPreferredSize(new java.awt.Dimension(802, 110));

        javax.swing.GroupLayout toppenalLayout = new javax.swing.GroupLayout(toppenal);
        toppenal.setLayout(toppenalLayout);
        toppenalLayout.setHorizontalGroup(
            toppenalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 802, Short.MAX_VALUE)
        );
        toppenalLayout.setVerticalGroup(
            toppenalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        getContentPane().add(toppenal, java.awt.BorderLayout.PAGE_START);

        parentpanel.setBackground(new java.awt.Color(255, 255, 255));
        parentpanel.setPreferredSize(new java.awt.Dimension(802, 800));
        parentpanel.setLayout(new java.awt.BorderLayout());

        sidepanel.setBackground(new java.awt.Color(87, 121, 50));
        sidepanel.setMinimumSize(new java.awt.Dimension(100, 500));
        sidepanel.setPreferredSize(new java.awt.Dimension(250, 300));
        sidepanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        newrental.setBackground(new java.awt.Color(55, 57, 58));
        newrental.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        newrental.setForeground(new java.awt.Color(255, 255, 255));
        newrental.setText("Neuer Verleih");
        newrental.setBorderPainted(false);
        newrental.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        newrental.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        newrental.setIconTextGap(15);
        newrental.setPreferredSize(new java.awt.Dimension(250, 70));
        newrental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newrentalActionPerformed(evt);
            }
        });
        sidepanel.add(newrental);
        newrental.getAccessibleContext().setAccessibleDescription("");

        rentallist.setBackground(new java.awt.Color(55, 57, 58));
        rentallist.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rentallist.setForeground(new java.awt.Color(255, 255, 255));
        rentallist.setText("Verleihliste");
        rentallist.setBorderPainted(false);
        rentallist.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        rentallist.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rentallist.setIconTextGap(15);
        rentallist.setPreferredSize(new java.awt.Dimension(250, 70));
        rentallist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentallistActionPerformed(evt);
            }
        });
        sidepanel.add(rentallist);

        archive.setBackground(new java.awt.Color(55, 57, 58));
        archive.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        archive.setForeground(new java.awt.Color(255, 255, 255));
        archive.setText("Archiv");
        archive.setToolTipText("");
        archive.setActionCommand("Verleihliste");
        archive.setBorderPainted(false);
        archive.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        archive.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        archive.setIconTextGap(15);
        archive.setPreferredSize(new java.awt.Dimension(250, 70));
        archive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveActionPerformed(evt);
            }
        });
        sidepanel.add(archive);

        inventory.setBackground(new java.awt.Color(55, 57, 58));
        inventory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        inventory.setForeground(new java.awt.Color(255, 255, 255));
        inventory.setText("Inventar");
        inventory.setToolTipText("");
        inventory.setActionCommand("Verleihliste");
        inventory.setBorderPainted(false);
        inventory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        inventory.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        inventory.setIconTextGap(15);
        inventory.setPreferredSize(new java.awt.Dimension(250, 70));
        inventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryActionPerformed(evt);
            }
        });
        sidepanel.add(inventory);

        parentpanel.add(sidepanel, java.awt.BorderLayout.LINE_START);
        sidepanel.getAccessibleContext().setAccessibleName("");

        startpage.setBackground(new java.awt.Color(220, 229, 211));

        scrollpane_startpage.setBorder(null);

        layerpane.setLayout(new java.awt.CardLayout());

        home_panel.setBackground(new java.awt.Color(220, 229, 211));

        javax.swing.GroupLayout home_panelLayout = new javax.swing.GroupLayout(home_panel);
        home_panel.setLayout(home_panelLayout);
        home_panelLayout.setHorizontalGroup(
            home_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 665, Short.MAX_VALUE)
        );
        home_panelLayout.setVerticalGroup(
            home_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 973, Short.MAX_VALUE)
        );

        layerpane.add(home_panel, "card9");

        newrental_panel.setBackground(new java.awt.Color(220, 229, 211));

        newrentaltitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        newrentaltitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newrentaltitle.setText("Neuer Verleih");

        devicename.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        devicename.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        devicename.setText("Gerätename");

        manufacturername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manufacturername.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        manufacturername.setText("Herstellerbezeichnung");

        inventory_number.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inventory_number.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        inventory_number.setText("Inventarnummer");

        rentedby.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rentedby.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rentedby.setText("Verliehen von");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel38.setText("Verleihen an");

        productname_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        productname_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4", " " }));

        manufacturer_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manufacturer_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        inventorynumber_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inventorynumber_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        userID_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userID_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        userID_newrental.setToolTipText("Matrikelnummer");

        userFirstName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userFirstName.setText("Vorname");
        userFirstName.setToolTipText("Vorname");

        userLastName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userLastName.setText("Nachname");
        userLastName.setToolTipText("Nachname");

        userEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userEmail.setText("E-Mail");
        userEmail.setToolTipText("E-Mail");
        userEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userEmailActionPerformed(evt);
            }
        });

        userPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userPhone.setText("Telefonnummer");
        userPhone.setToolTipText("Telefonnummer");
        userPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userPhoneActionPerformed(evt);
            }
        });

        year_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        year_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jahrgang" }));

        administrator_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        administrator_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        administrator_newrental.setToolTipText("Admin-ID");

        date.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        date.setText("Datum");

        rentalDate_newrental.setForeground(new java.awt.Color(87, 121, 50));
        rentalDate_newrental.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        save_newrental.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        save_newrental.setText("Speichern");
        save_newrental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_newrentalActionPerformed(evt);
            }
        });

        cancel_newrental.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cancel_newrental.setText("Abbrechen");
        cancel_newrental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_newrentalActionPerformed(evt);
            }
        });

        adminFullName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        adminFullName.setText("Vor- und Nachname");
        adminFullName.setToolTipText("");

        javax.swing.GroupLayout newrental_panelLayout = new javax.swing.GroupLayout(newrental_panel);
        newrental_panel.setLayout(newrental_panelLayout);
        newrental_panelLayout.setHorizontalGroup(
            newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newrental_panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newrental_panelLayout.createSequentialGroup()
                        .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rentedby, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(newrental_panelLayout.createSequentialGroup()
                                .addComponent(save_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(cancel_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(administrator_newrental, 0, 307, Short.MAX_VALUE)
                            .addComponent(rentalDate_newrental, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(adminFullName)))
                    .addGroup(newrental_panelLayout.createSequentialGroup()
                        .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(manufacturername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(devicename, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inventory_number, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(newrental_panelLayout.createSequentialGroup()
                                .addComponent(userPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(year_newrental, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(userEmail)
                            .addComponent(userID_newrental, 0, 307, Short.MAX_VALUE)
                            .addGroup(newrental_panelLayout.createSequentialGroup()
                                .addComponent(userFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(userLastName))
                            .addComponent(productname_newrental, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(manufacturer_newrental, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inventorynumber_newrental, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(newrentaltitle, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        newrental_panelLayout.setVerticalGroup(
            newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newrental_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(newrentaltitle, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(devicename)
                    .addComponent(productname_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manufacturername)
                    .addComponent(manufacturer_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inventory_number)
                    .addComponent(inventorynumber_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(userID_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(userEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(year_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rentedby)
                    .addComponent(administrator_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date)
                    .addComponent(rentalDate_newrental, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save_newrental)
                    .addComponent(cancel_newrental))
                .addContainerGap(290, Short.MAX_VALUE))
        );

        layerpane.add(newrental_panel, "card2");

        rentallist_panel.setBackground(new java.awt.Color(220, 229, 211));

        rentallist_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rentallist_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rentallist_title.setText("Aktuell verliehen");

        searchfilter_rentallist.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchfilter_rentallist.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        searchfilter_rentallist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfilter_rentallistActionPerformed(evt);
            }
        });

        filter_options_rentallist.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filter_options_rentallist.setText("Filterungs- und Sortiermöglichkeiten:");

        jScrollPane6.setBackground(new java.awt.Color(220, 229, 211));

        rentallist_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", "", ""
            }
        ));
        rentallist_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rentallist_tableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(rentallist_table);

        return_button.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        return_button.setText("Geräterückgabe");
        return_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rentallist_panelLayout = new javax.swing.GroupLayout(rentallist_panel);
        rentallist_panel.setLayout(rentallist_panelLayout);
        rentallist_panelLayout.setHorizontalGroup(
            rentallist_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rentallist_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(rentallist_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rentallist_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                        .addComponent(rentallist_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rentallist_panelLayout.createSequentialGroup()
                            .addComponent(filter_options_rentallist)
                            .addGap(20, 20, 20)
                            .addComponent(searchfilter_rentallist, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(return_button, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(141, 141, 141))
        );
        rentallist_panelLayout.setVerticalGroup(
            rentallist_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rentallist_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(rentallist_title, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(rentallist_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchfilter_rentallist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filter_options_rentallist))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addComponent(return_button)
                .addGap(174, 174, 174))
        );

        layerpane.add(rentallist_panel, "card3");

        archive_panel.setBackground(new java.awt.Color(220, 229, 211));

        archive_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        archive_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        archive_title.setText("Archiv");

        searchfilter_archive.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchfilter_archive.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        searchfilter_archive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfilter_archiveActionPerformed(evt);
            }
        });

        filter_options_archive.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filter_options_archive.setText("Filterungs- und Sortiermöglichkeiten:");

        jScrollPane3.setBackground(new java.awt.Color(220, 229, 211));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(800, 500));
        jScrollPane3.setRequestFocusEnabled(false);

        archive_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        archive_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(archive_table);

        javax.swing.GroupLayout archive_panelLayout = new javax.swing.GroupLayout(archive_panel);
        archive_panel.setLayout(archive_panelLayout);
        archive_panelLayout.setHorizontalGroup(
            archive_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(archive_panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(archive_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(archive_title, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                    .addGroup(archive_panelLayout.createSequentialGroup()
                        .addComponent(filter_options_archive)
                        .addGap(20, 20, 20)
                        .addComponent(searchfilter_archive, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(143, 143, 143))
        );
        archive_panelLayout.setVerticalGroup(
            archive_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(archive_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(archive_title, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(archive_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchfilter_archive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filter_options_archive))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
                .addGap(223, 223, 223))
        );

        layerpane.add(archive_panel, "card4");

        inventory_panel.setBackground(new java.awt.Color(220, 229, 211));

        newdevice_button.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        newdevice_button.setText("Neues Device anlegen");
        newdevice_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newdevice_buttonActionPerformed(evt);
            }
        });

        inventory_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        inventory_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inventory_title.setText("Inventar");

        searchfilter_inventory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchfilter_inventory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        searchfilter_inventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfilter_inventoryActionPerformed(evt);
            }
        });

        filter_options_inventory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filter_options_inventory.setText("Filterungs- und Sortiermöglichkeiten:");

        jScrollPane4.setBackground(new java.awt.Color(220, 229, 211));

        inventory_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Inventar"
            }
        ));
        jScrollPane4.setViewportView(inventory_table);

        javax.swing.GroupLayout inventory_panelLayout = new javax.swing.GroupLayout(inventory_panel);
        inventory_panel.setLayout(inventory_panelLayout);
        inventory_panelLayout.setHorizontalGroup(
            inventory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventory_panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(inventory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inventory_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(inventory_panelLayout.createSequentialGroup()
                        .addComponent(jScrollPane4)
                        .addGap(129, 129, 129))
                    .addGroup(inventory_panelLayout.createSequentialGroup()
                        .addGroup(inventory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(inventory_panelLayout.createSequentialGroup()
                                .addComponent(filter_options_inventory)
                                .addGap(10, 10, 10)
                                .addComponent(searchfilter_inventory, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(newdevice_button, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 239, Short.MAX_VALUE))))
        );
        inventory_panelLayout.setVerticalGroup(
            inventory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventory_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(inventory_title, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(newdevice_button)
                .addGap(10, 10, 10)
                .addGroup(inventory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filter_options_inventory)
                    .addComponent(searchfilter_inventory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
                .addGap(186, 186, 186))
        );

        layerpane.add(inventory_panel, "card5");

        return_panel.setBackground(new java.awt.Color(220, 229, 211));

        productname_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        productname_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        productname_return.setText("Produktname");

        re_productname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        re_productname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                re_productnameActionPerformed(evt);
            }
        });

        manufacturer_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manufacturer_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        manufacturer_return.setText("Herstellerbezeichnung");

        return_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        return_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        return_title.setText("Rückgabe");

        rentedby_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rentedby_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rentedby_return.setText("Verliehen an");

        date_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        date_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        date_return.setText("Datum");

        administrator_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        administrator_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        administrator_return.setText("Verliehen von");

        returndate.setForeground(new java.awt.Color(87, 121, 50));
        returndate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Rücknahme durch");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Rückgabedatum");

        re_withdrawnby.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        re_withdrawnby.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        re_withdrawnby.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                re_withdrawnbyActionPerformed(evt);
            }
        });

        notestitle_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        notestitle_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        notestitle_return.setText("Notizen");

        re_notes.setColumns(20);
        re_notes.setRows(5);
        jScrollPane5.setViewportView(re_notes);

        defaultsettings.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        defaultsettings.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        defaultsettings.setText("Werkseinstellungen");

        yes.setBackground(new java.awt.Color(220, 229, 211));
        yes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        yes.setText("Ja");
        yes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yesActionPerformed(evt);
            }
        });

        no.setBackground(new java.awt.Color(220, 229, 211));
        no.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        no.setText("Nein");

        save_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        save_return.setText("Speichern");
        save_return.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_returnActionPerformed(evt);
            }
        });

        cancel_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cancel_return.setText("Abbrechen");

        re_manufacturer.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        re_manufacturer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                re_manufacturerActionPerformed(evt);
            }
        });

        re_user.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        re_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                re_userActionPerformed(evt);
            }
        });

        re_rentaldate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        re_rentaldate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                re_rentaldateActionPerformed(evt);
            }
        });

        re_administrator.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        re_administrator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                re_administratorActionPerformed(evt);
            }
        });

        inventorynumber.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inventorynumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        inventorynumber.setText("Inventarnummer");

        re_inventorynumber.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        re_inventorynumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                re_inventorynumberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout return_panelLayout = new javax.swing.GroupLayout(return_panel);
        return_panel.setLayout(return_panelLayout);
        return_panelLayout.setHorizontalGroup(
            return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, return_panelLayout.createSequentialGroup()
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(return_panelLayout.createSequentialGroup()
                        .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(return_panelLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(productname_return, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(manufacturer_return, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rentedby_return, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inventorynumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, return_panelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(notestitle_return, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(defaultsettings, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(48, 48, 48)
                        .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(return_panelLayout.createSequentialGroup()
                                .addComponent(save_return, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(cancel_return, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(return_panelLayout.createSequentialGroup()
                                .addComponent(yes, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(no, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(re_productname)
                            .addComponent(re_manufacturer)
                            .addComponent(re_user)
                            .addComponent(re_inventorynumber)
                            .addComponent(re_withdrawnby, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(returndate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane5)))
                    .addGroup(return_panelLayout.createSequentialGroup()
                        .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(administrator_return, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(date_return, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(48, 48, 48)
                        .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(re_rentaldate, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(re_administrator, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(234, 234, 234))
            .addGroup(return_panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(return_title, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        return_panelLayout.setVerticalGroup(
            return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(return_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(return_panelLayout.createSequentialGroup()
                        .addComponent(return_title, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(re_productname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(productname_return))
                        .addGap(9, 9, 9)
                        .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(re_manufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(manufacturer_return))
                        .addGap(38, 38, 38))
                    .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(re_user, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rentedby_return)))
                .addGap(10, 10, 10)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(re_inventorynumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inventorynumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(re_rentaldate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date_return))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(re_administrator, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(administrator_return))
                .addGap(31, 31, 31)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(returndate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(re_withdrawnby, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(notestitle_return)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(defaultsettings)
                    .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(yes)
                        .addComponent(no)))
                .addGap(20, 20, 20)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save_return)
                    .addComponent(cancel_return))
                .addGap(62, 62, 62))
        );

        layerpane.add(return_panel, "card6");

        newdevice_panel.setBackground(new java.awt.Color(220, 229, 211));

        newdevice_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        newdevice_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newdevice_title.setText("Neues Gerät");

        devicename_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        devicename_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        devicename_newdevice.setText("Gerätename");

        manufacturer_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manufacturer_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        manufacturer_newdevice.setText("Herstellerbezeichnung");

        inventorynumber_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inventorynumber_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        inventorynumber_newdevice.setText("Inventarnummer");

        IMEInumber_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IMEInumber_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        IMEInumber_newdevice.setText("IMEI Nummer");

        acquisitiondate_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        acquisitiondate_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        acquisitiondate_newdevice.setText("Anschaffungsdatum");

        acquisitionDate.setForeground(new java.awt.Color(87, 121, 50));
        acquisitionDate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        save_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        save_newdevice.setText("Speichern");
        save_newdevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_newdeviceActionPerformed(evt);
            }
        });

        cancel_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cancel_newdevice.setText("Abbrechen");

        manufacturer.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        productname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        inventoryNumber.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        imei.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        room_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        room_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        room_newdevice.setText("Raum");

        location.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        notes_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        notes_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        notes_newdevice.setText("Notizen");

        notes.setColumns(20);
        notes.setRows(5);
        jScrollPane2.setViewportView(notes);

        administrator_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        administrator_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        administrator_newdevice.setText("Administrator");

        administrator.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        administrator.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        administrator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                administratorActionPerformed(evt);
            }
        });

        acquisitionvalue_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        acquisitionvalue_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        acquisitionvalue_newdevice.setText("Anschaffungswert");

        acquisitionValue.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout newdevice_panelLayout = new javax.swing.GroupLayout(newdevice_panel);
        newdevice_panel.setLayout(newdevice_panelLayout);
        newdevice_panelLayout.setHorizontalGroup(
            newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newdevice_panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newdevice_panelLayout.createSequentialGroup()
                        .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(manufacturer_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(devicename_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inventorynumber_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(IMEInumber_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(room_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(notes_newdevice, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(administrator_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(acquisitionvalue_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                        .addGap(40, 40, 40)
                        .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(manufacturer)
                            .addComponent(productname)
                            .addComponent(inventoryNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                            .addComponent(imei, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addComponent(administrator, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(location)
                            .addComponent(acquisitionValue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE))
                        .addGap(0, 144, Short.MAX_VALUE))
                    .addGroup(newdevice_panelLayout.createSequentialGroup()
                        .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newdevice_title, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(newdevice_panelLayout.createSequentialGroup()
                                .addComponent(acquisitiondate_newdevice, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(newdevice_panelLayout.createSequentialGroup()
                                        .addComponent(save_newdevice, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(cancel_newdevice, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(acquisitionDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(19, 19, 19))))
        );
        newdevice_panelLayout.setVerticalGroup(
            newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newdevice_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(newdevice_title, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(devicename_newdevice)
                    .addComponent(productname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manufacturer_newdevice))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inventorynumber_newdevice)
                    .addComponent(inventoryNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IMEInumber_newdevice)
                    .addComponent(imei, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(room_newdevice)
                    .addComponent(location, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(notes_newdevice)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(administrator, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(administrator_newdevice))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acquisitionvalue_newdevice)
                    .addComponent(acquisitionValue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(acquisitiondate_newdevice)
                    .addComponent(acquisitionDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save_newdevice)
                    .addComponent(cancel_newdevice))
                .addGap(172, 172, 172))
        );

        layerpane.add(newdevice_panel, "card7");

        scrollpane_startpage.setViewportView(layerpane);

        javax.swing.GroupLayout startpageLayout = new javax.swing.GroupLayout(startpage);
        startpage.setLayout(startpageLayout);
        startpageLayout.setHorizontalGroup(
            startpageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane_startpage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
        );
        startpageLayout.setVerticalGroup(
            startpageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane_startpage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );

        parentpanel.add(startpage, java.awt.BorderLayout.CENTER);

        getContentPane().add(parentpanel, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleName("jFrame");

        pack();
    }// </editor-fold>//GEN-END:initComponents
        
    private void newrentalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newrentalActionPerformed
               
        layerpane.removeAll();
        layerpane.add(newrental_panel);
        layerpane.repaint();
        layerpane.revalidate();
        
    }//GEN-LAST:event_newrentalActionPerformed

    private void rentallistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentallistActionPerformed
                
        layerpane.removeAll();
        layerpane.add(rentallist_panel);
        layerpane.repaint();
        layerpane.revalidate();
        
        rentallistHelper.initializeRentallist(rentallist_table, jScrollPane6);
    }//GEN-LAST:event_rentallistActionPerformed

    /**
    *
    * @author Katharina
    */
    private void archiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveActionPerformed
        
        layerpane.removeAll();
        layerpane.add(archive_panel);
        layerpane.repaint();
        layerpane.revalidate();
        
        archHelper.populateTable(archive_table, jScrollPane3);
        
    }//GEN-LAST:event_archiveActionPerformed

    private void inventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventoryActionPerformed
        
        layerpane.removeAll();
        layerpane.add(inventory_panel);
        layerpane.repaint();
        layerpane.revalidate();
    }//GEN-LAST:event_inventoryActionPerformed

    private void searchfilter_archiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfilter_archiveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchfilter_archiveActionPerformed

    private void searchfilter_rentallistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfilter_rentallistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchfilter_rentallistActionPerformed

    private void userEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userEmailActionPerformed

    private void userPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userPhoneActionPerformed

    /**
    *
    * @author Katharina
    */
    private void cancel_newrentalActionPerformed(java.awt.event.ActionEvent evt) {
        deleteAll();
    }   
    
    /**
    *
    * @author Katharina
    */
    public void deleteAll(){
        
        productname_newrental.removeAllItems();
        manufacturer_newrental.removeAllItems();
        inventorynumber_newrental.removeAllItems();
           
        userID_newrental.setSelectedItem("");
        year_newrental.setSelectedItem("");
        year_newrental.setEnabled(true);
        administrator_newrental.setSelectedItem("");
        adminFullName.setText("Vor- und Nachname");
        userFirstName.setText("Vorname");
        userLastName.setText("Nachname");
        userPhone.setText("Telefon");
        userEmail.setText("E-Mail");
    }
    
    
    
    private void save_newrentalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_newrentalActionPerformed
        
        if (!userID_newrental.getEditor().getItem().toString().isBlank()){
            boolean createNewUser = true;
            if (hp.isUserNew(userID_newrental.getEditor().getItem().toString())){
                Users user = rentalHelper.createUser(userID_newrental, userFirstName, userLastName,
                                                        userPhone, userEmail, year_newrental);
                
                if (user == null){
                    createNewUser = false;
                }
                if (createNewUser){
                    hp.insertNewUser(user);
                    JOptionPane.showMessageDialog(null, "Neuen User hinzugefügt");
                }   
                
            } else {
                Users user = rentalHelper.createUser(userID_newrental, userFirstName, userLastName,
                                                        userPhone, userEmail, year_newrental);
                
                if (user == null){
                    createNewUser = false;
                }
            }
            if (createNewUser){
                rentalHelper.createNewRental(rentalDate_newrental, inventorynumber_newrental,
                                            userID_newrental, administrator_newrental);
            }
        } else {
            JOptionPane.showMessageDialog(null, "UserID/Matrikelnummer angeben!");
        }
        deleteAll();
    }//GEN-LAST:event_save_newrentalActionPerformed

    private void save_newdeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_newdeviceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_save_newdeviceActionPerformed

    private void administratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_administratorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_administratorActionPerformed

    private void re_withdrawnbyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_re_withdrawnbyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_re_withdrawnbyActionPerformed

    private void save_returnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_returnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_save_returnActionPerformed

    private void yesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yesActionPerformed

    private void return_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_buttonActionPerformed
        TableModel model = rentallist_table.getModel();
        //int indexs[] = rentallist_table.getSelectedRows();
        
        //Object[] row = new Object[5];
        
        int index = rentallist_table.getSelectedRow();
        
        String ret_productname = model.getValueAt(index, 2).toString();
        String ret_manufacturer = model.getValueAt(index, 3).toString();
        String ret_user = model.getValueAt(index, 4).toString();
        String ret_inventorynumber = model.getValueAt(index, 1).toString();
        String ret_rentaldate = model.getValueAt(index, 5).toString();
        
        layerpane.removeAll();
        layerpane.add(return_panel);
        layerpane.repaint();
        layerpane.revalidate();

        
        //DefaultTableModel model1 = (DefaultTableModel)return_panel.rentallist_table.getModel();
        
   
        
        
    }//GEN-LAST:event_return_buttonActionPerformed

    private void searchfilter_inventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfilter_inventoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchfilter_inventoryActionPerformed

    private void newdevice_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newdevice_buttonActionPerformed
        // TODO add your handling code here:

        layerpane.removeAll();
        layerpane.add(newdevice_panel);
        layerpane.repaint();
        layerpane.revalidate();
    }//GEN-LAST:event_newdevice_buttonActionPerformed

    private void re_productnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_re_productnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_re_productnameActionPerformed

    private void re_manufacturerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_re_manufacturerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_re_manufacturerActionPerformed

    private void re_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_re_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_re_userActionPerformed

    private void re_rentaldateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_re_rentaldateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_re_rentaldateActionPerformed

    private void re_administratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_re_administratorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_re_administratorActionPerformed

    private void re_inventorynumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_re_inventorynumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_re_inventorynumberActionPerformed

    private void rentallist_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rentallist_tableMouseClicked
        /* int index = rentallist_table.getSelectedRow();
        TableModel model = rentallist_table.getModel();
        String ret_productname = model.getValueAt(index, 2).toString();
        String ret_manufacturer = model.getValueAt(index, 3).toString();
        String ret_user = model.getValueAt(index, 4).toString();
        String ret_inventorynumber = model.getValueAt(index, 1).toString();
        String ret_rentaldate = model.getValueAt(index, 5).toString(); */
    
        /*returndata.setVisible(true);
        returndata.pack();
        returndata.setDefaultCloseOperation(index); */
            
        
    }//GEN-LAST:event_rentallist_tableMouseClicked
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IMEInumber_newdevice;
    private com.raven.datechooser.DateChooser acquisitionDate;
    private javax.swing.JTextField acquisitionValue;
    private javax.swing.JLabel acquisitiondate_newdevice;
    private javax.swing.JLabel acquisitionvalue_newdevice;
    private javax.swing.JTextField adminFullName;
    private javax.swing.JComboBox<String> administrator;
    private javax.swing.JLabel administrator_newdevice;
    private javax.swing.JComboBox<String> administrator_newrental;
    private javax.swing.JLabel administrator_return;
    private javax.swing.JButton archive;
    private javax.swing.JPanel archive_panel;
    private javax.swing.JTable archive_table;
    private javax.swing.JLabel archive_title;
    private javax.swing.JToggleButton cancel_newdevice;
    private javax.swing.JToggleButton cancel_newrental;
    private javax.swing.JToggleButton cancel_return;
    private javax.swing.JLabel date;
    private javax.swing.JLabel date_return;
    private javax.swing.JLabel defaultsettings;
    private javax.swing.JLabel devicename;
    private javax.swing.JLabel devicename_newdevice;
    private javax.swing.JLabel filter_options_archive;
    private javax.swing.JLabel filter_options_inventory;
    private javax.swing.JLabel filter_options_rentallist;
    private javax.swing.JPanel home_panel;
    private javax.swing.JTextField imei;
    private javax.swing.JButton inventory;
    private javax.swing.JTextField inventoryNumber;
    private javax.swing.JLabel inventory_number;
    private javax.swing.JPanel inventory_panel;
    private javax.swing.JTable inventory_table;
    private javax.swing.JLabel inventory_title;
    private javax.swing.JLabel inventorynumber;
    private javax.swing.JLabel inventorynumber_newdevice;
    private javax.swing.JComboBox<String> inventorynumber_newrental;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLayeredPane layerpane;
    private javax.swing.JTextField location;
    private javax.swing.JTextField manufacturer;
    private javax.swing.JLabel manufacturer_newdevice;
    private javax.swing.JComboBox<String> manufacturer_newrental;
    private javax.swing.JLabel manufacturer_return;
    private javax.swing.JLabel manufacturername;
    private javax.swing.JButton newdevice_button;
    private javax.swing.JPanel newdevice_panel;
    private javax.swing.JLabel newdevice_title;
    private javax.swing.JButton newrental;
    private javax.swing.JPanel newrental_panel;
    private javax.swing.JLabel newrentaltitle;
    private javax.swing.JRadioButton no;
    private javax.swing.JTextArea notes;
    private javax.swing.JLabel notes_newdevice;
    private javax.swing.JLabel notestitle_return;
    private javax.swing.JPanel parentpanel;
    private javax.swing.JTextField productname;
    private javax.swing.JComboBox<String> productname_newrental;
    private javax.swing.JLabel productname_return;
    private javax.swing.JTextField re_administrator;
    private javax.swing.JTextField re_inventorynumber;
    private javax.swing.JTextField re_manufacturer;
    private javax.swing.JTextArea re_notes;
    private javax.swing.JTextField re_productname;
    private javax.swing.JTextField re_rentaldate;
    private javax.swing.JTextField re_user;
    private javax.swing.JComboBox<String> re_withdrawnby;
    private com.raven.datechooser.DateChooser rentalDate_newrental;
    private javax.swing.JButton rentallist;
    private javax.swing.JPanel rentallist_panel;
    private javax.swing.JTable rentallist_table;
    private javax.swing.JLabel rentallist_title;
    private javax.swing.JLabel rentedby;
    private javax.swing.JLabel rentedby_return;
    private javax.swing.JButton return_button;
    private javax.swing.JPanel return_panel;
    private javax.swing.JLabel return_title;
    private com.raven.datechooser.DateChooser returndate;
    private javax.swing.JLabel room_newdevice;
    private javax.swing.JToggleButton save_newdevice;
    private javax.swing.JToggleButton save_newrental;
    private javax.swing.JToggleButton save_return;
    private javax.swing.JScrollPane scrollpane_startpage;
    private javax.swing.JComboBox<String> searchfilter_archive;
    private javax.swing.JComboBox<String> searchfilter_inventory;
    private javax.swing.JComboBox<String> searchfilter_rentallist;
    private javax.swing.JPanel sidepanel;
    private javax.swing.JPanel startpage;
    private javax.swing.JPanel toppenal;
    private javax.swing.JTextField userEmail;
    private javax.swing.JTextField userFirstName;
    private javax.swing.JComboBox<String> userID_newrental;
    private javax.swing.JTextField userLastName;
    private javax.swing.JTextField userPhone;
    private javax.swing.JComboBox<String> year_newrental;
    private javax.swing.JRadioButton yes;
    // End of variables declaration//GEN-END:variables
       
    @Override
    public void run() {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        new GUI().setVisible(true);
    }
}

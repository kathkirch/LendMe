package Project_LendMe;

import com.raven.datechooser.DateChooser;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import com.raven.datechooser.SelectedDate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Helper-class to initialize the newrental_panel with all it's 
 * methods and listeners for JButtons or JComboBox in this component
 * 
 * @author Katharina
 */
public final class Rental_Helper {
    
    //components
    private JPanel panel;
    private JComboBox jCBname;
    private JComboBox jCBmanufacturer;
    private JComboBox jCBinvnumber;
    private JComboBox jCBuserID;
    private JTextField jTFfirstname;
    private JTextField jTFlastname;
    private JTextField jTFmail;
    private JTextField jTFphone;
    private JComboBox jCByear;
    private JTextField jTFadminID;
    private JTextField jTFadminName;
    private DateChooser dcDate; 
    private JButton jBsave;
    private JButton jBcancel;
   
    //DatabaseHelper Object and Validator Object
    private DatabaseHelper hp = new DatabaseHelper();
    private final Validator val = new Validator();
    
    private final String lastItem_e = "";
    private final String lastItem = "";
    
    /**
     *  static final string attributes needed for actionevent
     *  to differentiate between user and program changes 
     *  for the JComboBoxes 
     */
    private static final String USER_CHANGE = "user_change";
    private static final String PROGRAM_CHANGE = "program_change";
    
   
    public Rental_Helper(JPanel panel) {
        
        this.panel = panel;
        this.jCBname = (JComboBox) panel.getComponent(17);
        this.jCBmanufacturer = (JComboBox) panel.getComponent(18);
        this.jCBinvnumber = (JComboBox) panel.getComponent(19);
        this.jCBuserID = (JComboBox) panel.getComponent(14);
        this.jTFfirstname = (JTextField) panel.getComponent(15);
        this.jTFlastname = (JTextField) panel.getComponent(16);
        this.jTFmail = (JTextField) panel.getComponent(13);
        this.jTFphone = (JTextField) panel.getComponent(11);
        this.jCByear = (JComboBox) panel.getComponent(12);
        this.jTFadminID = (JTextField) panel.getComponent(6);
        this.jTFadminName = (JTextField) panel.getComponent(5);
        this.dcDate = (DateChooser) panel.getComponent(2);
        this.jBsave = (JButton) panel.getComponent(3);
        this.jBcancel = (JButton) panel.getComponent(4);
        
        jTFadminID.setEditable(false);
        jTFadminName.setEditable(false);
        
    }
    
    /**
     *
     * @param box the jComboBox which should be filled with Strings or Items
     * @param category category as a String needed to call the makeListForCategory 
     * method to get Items from a Devices-Object list to fill the JComboBox with
     */
    public void fillComboBox_Category (JComboBox box, String category){
        
        List <Devices> list = hp.getAvailableDevices();
        List <Object> oList = makeListForCategory(list, category);
        box.setModel(new DefaultComboBoxModel<>(oList.toArray((new String[0]))));
        box.setEditable(true);
        box.setSelectedItem(lastItem);
        AutoCompleteDecorator.decorate(box);  
        
        //set the ActionCommand to PROGRAM_CHANGE to differnciate between
        //program-changes and user-changes in the listener for JComboBox
        jCBname.setActionCommand(PROGRAM_CHANGE);
        jCBinvnumber.setActionCommand(PROGRAM_CHANGE);
        jCBmanufacturer.setActionCommand(PROGRAM_CHANGE);
    }
    
    /**
     * fills the userID-, userYear-,  
     * productName-, manufacturer- and inventoryNumber-JComboBox with the 
     * suitable items
     */
    public void fillBoxes (){
        
        //methods to fill the boxes with data from database for the given category
        fillComboBox_Category(jCBname, "productName");
        fillComboBox_Category(jCBinvnumber, "inventoryNumber");
        fillComboBox_Category(jCBmanufacturer, "manufacturer");
       
        List <String> u = hp.getUsersID();
        jCBuserID.setModel(new DefaultComboBoxModel<>(u.toArray((new String[0]))));
        // allows to make a new entry for 'Matrikelnummer/userID' to store a new
        // user in the db
        jCBuserID.setEditable(true);
        jCBuserID.addItem(lastItem_e);
        jCBuserID.setSelectedIndex(jCBuserID.getItemCount()-1);
        AutoCompleteDecorator.decorate(jCBuserID);
        
        jCByear.setModel(new DefaultComboBoxModel<>(hp.getUserYears().toArray((new String[0]))));
        // allows to make a new entry for 'year' when a new user should be 
        // stored in db
        jCByear.setEditable(true);
        jCByear.setSelectedItem("");
        AutoCompleteDecorator.decorate(jCByear);
  
        //set the ActionCommand to PROGRAM_CHANGE to differnciate between
        //program-changes and user-changes in the listener for JComboBox
        jCBname.setActionCommand(PROGRAM_CHANGE);
        jCBinvnumber.setActionCommand(PROGRAM_CHANGE);
        jCBmanufacturer.setActionCommand(PROGRAM_CHANGE);
        
    }
    
    /**
     *method to set Text for Admin-Data with suitable data from Database
     *every device has only one administrator,
     *fields are not editable - just for user information
     */
    public void setAdminData() throws NullPointerException {
        
        String invID = jCBinvnumber.getSelectedItem().toString();  
       
        if (!invID.isBlank() && val.isNumeric(invID)) {
            
            // set admin values
            String a_ID = hp.getDeviceAdminID(invID);
            jTFadminID.setText(a_ID);
            String name = hp.getAdminNameByID(a_ID);
            jTFadminName.setText(name);
        }
    }
   
    
    /**
     * adds an listener for userID-JComboBox and sets 
     * userFirstName, userLastName, userPhone, userMail and userYear 
     * automatically based on selection in userID_JComboBox
     * if userID is not known in the db all the textfields are set empty
     */
    public void listenForSelectionUID () {
        jCBuserID.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                
                if (e.getItem().equals(lastItem_e)){
                    jTFfirstname.setText("Vorname");
                    jTFlastname.setText("Nachname");
                    jTFphone.setText("Telefonnummer");
                    jTFmail.setText("E-Mail");
                    jCByear.setSelectedItem("");
                    jCByear.setEnabled(true);
                    jTFfirstname.setEditable(true);
                    jTFlastname.setEditable(true);
                    jTFphone.setEditable(true);
                    jTFmail.setEditable(true);
                    jCByear.setEnabled(true);
                    jCByear.setEnabled(true);
                }
                
                if (e.getStateChange() == ItemEvent.SELECTED 
                        && !e.getItem().toString().equals(lastItem)){
                    String selected = e.getItem().toString();
                    
                    if ((!selected.equals(lastItem_e) && val.isNumeric(selected))){
                       
                        Users userToCheck = hp.getUserByID(selected);
                        
                        if (userToCheck != null && (!hp.isUserNew(Long.parseLong(selected)))) {
                            jTFfirstname.setText(userToCheck.getUserFirstName());
                            jTFlastname.setText(userToCheck.getUserLastName());
                            jTFphone.setText(userToCheck.getUserPhone());
                            jTFmail.setText(userToCheck.getUserEmail());
                            jCByear.setSelectedItem(userToCheck.getYear());
                            jTFfirstname.setEditable(false);
                            jTFlastname.setEditable(false);
                            jTFphone.setEditable(false);
                            jTFmail.setEditable(false);
                            jCByear.setEnabled(false);
                        } else if (hp.isUserNew(Long.parseLong(selected))) {
                            jTFfirstname.setText("");
                            jTFlastname.setText("");
                            jTFphone.setText("");
                            jTFmail.setText("");
                            jCByear.setSelectedItem("");
                            jTFfirstname.setEditable(true);
                            jTFlastname.setEditable(true);
                            jTFphone.setEditable(true);
                            jTFmail.setEditable(true);
                            jCByear.setEnabled(true);
                            jCByear.setEnabled(true);
                        } 
                    }
                }
            }
        });
    }
    
    /**
     * adds an listener for productname-JComboBox and sets 
     * manufacturer- and inventoryNumber-JComboBox 
     * automatically based on selection for productname
     */
    public void listenForSelectionPN () {
        
        jCBname.addItemListener(new ItemListener () {
            
            public void itemStateChanged(ItemEvent e) {
                
                String selected = lastItem;
             
                if (e.getStateChange() == ItemEvent.SELECTED 
                        && !jCBname.getSelectedItem().equals(lastItem)) {
                    
                    selected = e.getItem().toString();
                    jCBname.setActionCommand(USER_CHANGE);
                }
                
                if ( jCBname.getActionCommand().equals(USER_CHANGE)){

                    if (!selected.isBlank() && val.isDeviceString(selected)){

                        List <Devices> list = hp.getItemByProductName(selected);
                        String [] categories = new String [] {"manufacturer", "inventoryNumber"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("manufacturer")){
                                jCBmanufacturer.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                                jCBmanufacturer.setActionCommand(PROGRAM_CHANGE);
                            }
                            if (categories[i].equalsIgnoreCase("inventoryNumber")){
                                jCBinvnumber.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                                jCBinvnumber.setActionCommand(PROGRAM_CHANGE);

                                try{
                                    setAdminData();
                                }catch (java.lang.NullPointerException npEx){
                                    System.out.println(npEx + "setAdminData");
                                }
                            }
                        }
                    }
                }
            }
        });
    }
    
   /**
     * adds an listener for manufacturer-JComboBox and sets 
     * productname- and inventoryNumber-JComboBox automatically based on 
     * selection for manufacturer
     */
    public void listenForSelectionM () {
        
        jCBmanufacturer.addItemListener(new ItemListener () {
            
            public void itemStateChanged(ItemEvent e) {
                
                String selected = lastItem;
                
                if (e.getStateChange() == ItemEvent.SELECTED &&
                       (! jCBmanufacturer.getSelectedItem().equals(lastItem))){
                    
                    selected = e.getItem().toString();
                   
                    if ( !e.getItem().equals(lastItem) &&
                        (!jCBname.getActionCommand().equals(USER_CHANGE)) &&
                        (!jCBinvnumber.getActionCommand().equals(USER_CHANGE))){
                         
                        jCBmanufacturer.setActionCommand(USER_CHANGE);
                    }
                    
                    // for the case a second device has the same productname
                    // the listener for productname puts only devices in the combobox
                    // with selected device-productname, but the combobox for productname 
                    // is filled with all devices 
                    else if ( !e.getItem().equals(lastItem) &&
                            jCBname.getItemCount() > jCBmanufacturer.getItemCount() &&
                            jCBmanufacturer.getItemCount() == jCBinvnumber.getItemCount()){
                        
                        jCBmanufacturer.setActionCommand(USER_CHANGE);
                    }
                }
                
                if ( jCBmanufacturer.getActionCommand().equals(USER_CHANGE)){

                    if (!selected.isBlank() && val.isDeviceString(selected)){

                        List <Devices> list = hp.getItemByManufacturer(selected);
                        String [] categories = new String [] {"productName", "inventoryNumber"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("productName")){
                                jCBname.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                                jCBname.setActionCommand(PROGRAM_CHANGE);
                            }
                            if (categories[i].equalsIgnoreCase("inventoryNumber")){
                                jCBinvnumber.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                                jCBinvnumber.setActionCommand(PROGRAM_CHANGE);

                                try{
                                    setAdminData();
                                }catch (java.lang.NullPointerException npEx){
                                    System.out.println(npEx + "setAdminData");
                                }
                            }
                        }
                    }
                }
            }
        }); 
    }
    
    /**
     * adds an listener for inventoryNumber-JComboBox and sets 
     * productname- and manufacturer-JComboBox automatically based on 
     * selection for inventoryNumber
    */
    public void listenForSelectionIN () {
        
        jCBinvnumber.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                
                String selected = "";
                
                if (e.getStateChange() == ItemEvent.SELECTED 
                        && !jCBinvnumber.getSelectedItem().equals(lastItem)){
                    
                    selected = e.getItem().toString();

                    if ( !e.getItem().equals(lastItem) && 
                        (!jCBname.getActionCommand().equals(USER_CHANGE)) &&
                        (!jCBmanufacturer.getActionCommand().equals(USER_CHANGE))){
                    
                        jCBinvnumber.setActionCommand(USER_CHANGE);
                    }
                }
                 
                if ( ! jCBinvnumber.getActionCommand().equals(PROGRAM_CHANGE)){
                                        
                    if (!selected.isBlank() && val.isNumeric(selected)){

                        List <Devices> list = hp.getItemByInvNumber(selected);
                        String [] categories = new String [] {"productName", "manufacturer"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("productName")){
                                jCBname.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                                jCBname.setActionCommand(PROGRAM_CHANGE);
                            }
                            if (categories[i].equalsIgnoreCase("manufacturer")){
                                jCBmanufacturer.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                                jCBmanufacturer.setActionCommand(PROGRAM_CHANGE);
                            }
                        }
                        try{
                            setAdminData();
                        }catch (java.lang.NullPointerException npEx){
                            System.out.println(npEx + "setAdminData");
                        }
                    }
                }  
            }
        });
    }
    
    
    
    /**
     * adds an listener for the cancel-JButton and calls the deleteAll() Method
     * if button is clicked
     */
    public void cancelButton () {
        
        jBcancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent aev) {
                deleteAll();
            }
        });
    }
    
    
    
    /**
     * method to delete all 
     * JTextFields and clears all the selections in the JComboBoxes 
     */
    public void deleteAll(){
        
        jCBname.removeAllItems();
        jCBmanufacturer.removeAllItems();
        jCBinvnumber.removeAllItems();
        
        jCBname.setActionCommand(PROGRAM_CHANGE);
        jCBinvnumber.setActionCommand(PROGRAM_CHANGE);
        jCBmanufacturer.setActionCommand(PROGRAM_CHANGE);
        
        jCByear.setEnabled(true);
        jCByear.setSelectedIndex(jCByear.getItemCount()-1);
        
        jTFfirstname.setText("Vorname");
        jTFlastname.setText("Nachname");
        jTFphone.setText("Telefon");
        jTFmail.setText("E-Mail");

        fillBoxes();
        
        jTFadminID.setText("Admin ID");
        jTFadminName.setText("Vor- und Nachname"); 
        
    }
    
    /**
     * method to create a Users Object with all the parameters 
     * also validates the textfields 
     * 
     * @param idBox to get the users ID from the JComboBox
     * @param tf_firs to get the users firstname from the JTextField
     * @param tf_last to get the users lastname from the JTextField
     * @param tf_phone to get the users phone form the JTextField
     * @param tf_mail to get the users mail from the JTextField
     * @param yearBox to get the users year from the JComboBox
     * 
     * @return Users Object if fields are valid and a null Object if not
     */
    public Users createUser(JComboBox idBox, JTextField tf_firs, JTextField tf_last,
                                JTextField tf_mail, JTextField tf_phone, 
                                JComboBox yearBox){
        
        Validator val = new Validator();
        boolean nameV = false;
        boolean emailV = false;
        boolean phoneV = false;
        boolean yearV = false;
        boolean idV = false;
        
        Users user = null;
        
        String id = idBox.getEditor().getItem().toString();
        String firstname = tf_firs.getText();
        String lastname = tf_last.getText(); 
        String phone = tf_phone.getText();
        String email = tf_mail.getText();
        String year = yearBox.getSelectedItem().toString();
        
        if (firstname.isBlank() || lastname.isBlank() || phone.isBlank() ||
                email.isBlank() || id.isBlank()){
            JOptionPane.showMessageDialog(null, 
                    "Achtung! Ein oder mehrere Felder sind leer!"
                            + " Bitte alles ausf??llen!", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
        
        }else {
            
            if ( val.isName(firstname) &&  val.isName(lastname)){
                nameV = true;
            }
            if ( val.isEmail(email)) {
                emailV = true;
            }
            if ( val.isPhone(phone)) {
                phoneV = true;
            }
            if ( val.isYear(year)) {
                yearV = true;
            }
            if ( val.isUserID(id)) {
                idV = true;
            }
            
            if (nameV && emailV && phoneV && yearV && idV) {
                user = new Users(Long.parseLong(id), firstname, lastname, email, 
                                phone, year);
            }
        }
        return user;
    }
    
    /**
     * method to create a new rental object, uses a Thread.sleep for the case
     * a new user is created 
     * inserts the rental-object in the database
     * 
     * @param chDate to get the rentalDate from the DateChosser
     * @param boxInvNumber to get the InventoryNumber from the JComboBox
     * @param boxUserID to get the userID from the JComboBox
     * @param textAdminID to get the adminID from the JTextField
     */
    public void createNewRental (DateChooser chDate, JComboBox boxInvNumber,
                                    JComboBox boxUserID, JTextField textAdminID){
        
        SelectedDate selectedDate = chDate.getSelectedDate();
        LocalDate date = 
                LocalDate.of(selectedDate.getYear(), 
                selectedDate.getMonth(), selectedDate.getDay());
        
        boolean valid = val.validRentalDate(date);
        
                
        if (valid) {
            
            try {
                Thread.sleep(1000); 
                
                String inventoryNumb = (String) boxInvNumber.getSelectedItem();
                String userID = (String) boxUserID.getSelectedItem();
                String adminID = (String) textAdminID.getText();

                if (inventoryNumb.isBlank() || userID.isBlank() || adminID.isBlank()){
                    JOptionPane.showMessageDialog(null, 
                            "Ein oder mehrere Felder sind leer!"
                            + " Bitte ausf??llen!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                
                } else if (!val.isNumeric(inventoryNumb)|| !val.isNumeric(userID) 
                           || !val.isNumeric(adminID)) {
                
                     JOptionPane.showMessageDialog(null, 
                            "Eingabe pr??fen!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Rentals rental = new Rentals(date, Long.parseLong(inventoryNumb),
                                Integer.parseInt(adminID),
                               Long.parseLong(userID));
                    try {
                        hp.insertNewRental_DB(rental);
                        JOptionPane.showMessageDialog(null, "Device verliehen! Status aktualisiert");
                        deleteAll();

                        
                    } catch (UserException ex){
                        System.out.println(ex);
                        System.out.println("createNewRental in Rental_Helper");
                        JOptionPane.showMessageDialog(null, 
                                "??berpr??fen der Eingabe notwendig!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    /**
     * adds an listener for the save-JButton 
     * controls if the user is new in database and if yes creates a new user object
     * if the new created user- object has valid data and is not null 
     * the user -object is inserted in the database and boolean validUser is set true
     * if user is not new in database a user object is created to proof if 
     * userfields are valid and boolean validUser is set true
     * if userValid is true the createNewRental() Method is called
     */
    public void saveNewRental () {
        
        jBsave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean validUser = true;
                
                if (jCBuserID.getSelectedItem().toString().isBlank()){
                    JOptionPane
                            .showMessageDialog(null,
                                    "Bitte UserID angeben!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                } if (val.isUserID(jCBuserID.getSelectedItem().toString())){
                    
                    String userID = jCBuserID.getEditor().getItem().toString();
                    
                    long id = Long.parseLong(userID);
                    
                    if (hp.isUserNew(id)){
                        
                        Users user = createUser(jCBuserID, jTFfirstname,
                                jTFlastname, jTFmail, jTFphone, jCByear);
                        
                        if (user == null){
                            validUser = false;
                        }
                        if (validUser){
                            try {
                                hp.insertNewUser(user);
                                JOptionPane
                                        .showMessageDialog(null, "Neuen User hinzugef??gt");
                                
                            }catch(UserException ex) {
                                System.out.println(ex);
                                System.out.println("ERROR! saveNewRental in Rental_Helper");
                                JOptionPane
                                        .showMessageDialog(null,
                                                "Fehler beim hinzuf??gen "
                                                        + "eines neuen Users",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        
                    } else {
                        Users user = createUser(jCBuserID, jTFfirstname,
                                jTFlastname, jTFmail, jTFphone, jCByear);
                        
                        if (user == null){
                            validUser = false;
                        }
                    }
                    if (validUser){
                        createNewRental(dcDate, jCBinvnumber, jCBuserID, jTFadminID);
                    }
                }
            }
        });
    } 
    
    
    /**
     * method to get a Object List from the given List depending on given category
     * 
     * @param list List of Devices Objects needed to create a object List 
     * @param itemCategory needed as a String to create the Objects List with the given itemCategory
     * @return a Object List with the given parameters
     */
    public List <Object> makeListForCategory (List <Devices> list, 
                                            String itemCategory) {
        
        List <Object> itemArray = new ArrayList<>();
        String string;
        long i;
        
        switch (itemCategory) {
            case "productName" :
                for (Devices dev : list){
                    string = dev.getProductName();
                    itemArray.add(string);
                }
                return itemArray;
             case "manufacturer" :
                for (Devices dev : list){
                    string = dev.getManufacturer();
                    itemArray.add(string);
                }
                return itemArray;
            case "inventoryNumber" :
                for (Devices dev : list){
                    i = dev.getInventoryNumber();
                    itemArray.add(String.valueOf(i));
                }
                return itemArray;
            default :
                return itemArray;  
        } 
    }
}

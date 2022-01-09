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
 *
 * @author Katharina
 */
public class Rental_Helper {
    
    JPanel panel;
    JComboBox jCBname;
    JComboBox jCBmanufacturer;
    JComboBox jCBinvnumber;
    JComboBox jCBuserID;
    JTextField jTFfirstname;
    JTextField jTFlastname;
    JTextField jTFmail;
    JTextField jTFphone;
    JComboBox jCByear;
    JComboBox jCBadminID;
    JTextField jTFadminName;
    DateChooser dcDate; 
    JButton jBsave;
    JButton jBcancel;
   
    
    final DatabaseHelper hp = new DatabaseHelper();
    private final Validator val = new Validator();

    public Rental_Helper(JPanel panel) {
        
        this.panel = panel;
        this.jCBname = (JComboBox) panel.getComponent(10);
        this.jCBmanufacturer = (JComboBox) panel.getComponent(11);
        this.jCBinvnumber = (JComboBox) panel.getComponent(12);
        this.jCBuserID = (JComboBox) panel.getComponent(7);
        this.jTFfirstname = (JTextField) panel.getComponent(8);
        this.jTFlastname = (JTextField) panel.getComponent(9);
        this.jTFmail = (JTextField) panel.getComponent(6);
        this.jTFphone = (JTextField) panel.getComponent(4);
        this.jCByear = (JComboBox) panel.getComponent(5);
        this.jCBadminID = (JComboBox) panel.getComponent(16);
        this.jTFadminName = (JTextField) panel.getComponent(18);
        this.dcDate = (DateChooser) panel.getComponent(17);
        this.jBsave = (JButton) panel.getComponent(19);
        this.jBcancel = (JButton) panel.getComponent(20);
        
        
    }
    
    /**
     *
     * @param box the jComboBox which should be filled with Strings or Items
     * @param category category as a String needed to call the makeListForCategory 
     * method to get Items from a Devices-Object list to fill the JComboBox with
     */
    public void fillComboBox_Category (JComboBox box, String category){
        List <Devices> list = hp.getDevices();
        
        List <Object> oList = makeListForCategory(list, category);
        box.setModel(new DefaultComboBoxModel<>(oList.toArray((new String[0]))));
        box.setEditable(true);
        box.setSelectedItem("");
        AutoCompleteDecorator.decorate(box);  
    }
    
    /**
     * fills the userID-, userYear-, adminID-, 
     * productName-, manufacturer- and inventoryNumber-JComboBox with the 
     * suitable items
     */
    public void fillBoxes (){
    
        jCBuserID.setModel(new DefaultComboBoxModel<>(hp.getUsersID().toArray((new String[0]))));
        //damit man in UserID Matrikelnummer eingeben kann
        jCBuserID.setEditable(true);
        jCBuserID.setSelectedItem("");
        AutoCompleteDecorator.decorate(jCBuserID);
        
        jCByear.setModel(new DefaultComboBoxModel<>(hp.getUserYears().toArray((new String[0]))));
        // damit man neues Jahr eingeben kann wenn noch nicht in DB vorhanden
        jCByear.setEditable(true);
        jCByear.setSelectedItem("");
        AutoCompleteDecorator.decorate(jCByear);
        
        jCBadminID.setModel(new DefaultComboBoxModel<>(hp.getAdminIDs().toArray((new String[0]))));
        jCBadminID.setEditable(true);
        jCBadminID.setSelectedItem("");
        AutoCompleteDecorator.decorate(jCBadminID);
        
        fillComboBox_Category(jCBname, "productName");
        fillComboBox_Category(jCBinvnumber, "inventoryNumber");
        fillComboBox_Category(jCBmanufacturer, "manufacturer");
        
        fillComboBox_Category(jCBname, "productName");
        fillComboBox_Category(jCBinvnumber, "inventoryNumber");
        fillComboBox_Category(jCBmanufacturer, "manufacturer");
        
    }
   
    
    /**
     * adds an listener for adminID-JComboBox and sets the adminFullName
     * automatically based on selection in adminID-JComboBox
     */
    public final void listenForSelectionAID() {
        jCBadminID.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                String selected = e.getItem().toString();
                if ((!selected.isBlank() && val.isNumeric(selected))) {
                    jTFadminName.setEditable(false);
                    jTFadminName.setText(hp.getAdminNameByID(selected));
                }
            }
        });
    }
    
    /**
     * adds an listener for userID-JComboBox and sets 
     * userFirstName, userLastName, userPhone, userMail and userYear 
     * automatically based on selection in userID_JComboBox
     * if userID is not known in the db all the textfields are set empty
     */
    public final void listenForSelectionUID () {
        jCBuserID.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                String selected = e.getItem().toString();
                if (e.getStateChange() == ItemEvent.SELECTED){
                    if ((!selected.isBlank() && val.isNumeric(selected))){
                    Users userToCheck = hp.checkUserID(selected);
                        if (userToCheck != null && (!hp.isUserNew(Long.parseLong(selected)))) {
                            jTFfirstname.setText(userToCheck.getUserFirstName());
                            jTFlastname.setText(userToCheck.getUserLastName());
                            jTFphone.setText(userToCheck.getUserPhone());
                            jTFmail.setText(userToCheck.getUserEmail());
                            jCByear.setSelectedItem(userToCheck.getYear());
                            jCByear.setEnabled(false);
                        } else if (hp.isUserNew(Long.parseLong(selected))) {
                            jTFfirstname.setText("");
                            jTFlastname.setText("");
                            jTFphone.setText("");
                            jTFmail.setText("");
                            jCByear.setSelectedItem("");
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
    public final void listenForSelectionPN () {
        jCBname.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){

                    String selected = e.getItem().toString();

                    if (jCBname.getItemCount() == 0 ){
                        selected = ""; 
                        fillComboBox_Category(jCBname, "productName");
                    }

                    if (!selected.isBlank()){
                        List <Devices> list = hp.getItemByProductName(selected);
                        String [] categories = new String [] {"manufacturer", "inventoryNumber"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("manufacturer")){
                                jCBmanufacturer.setModel
                            (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                            if (categories[i].equalsIgnoreCase("inventoryNumber")){
                                jCBinvnumber.setModel
                            (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
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
    public final void listenForSelectionM () {
        jCBmanufacturer.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                     String selected = e.getItem().toString();
                
                    if (jCBmanufacturer.getItemCount() == 0 ){
                        selected = ""; 
                        fillComboBox_Category(jCBmanufacturer, "manufacturer");
                    }
                    if (!selected.isBlank()){
                        List <Devices> list = hp.getItemByManufacturer(selected);
                        String [] categories = new String [] {"productName", "inventoryNumber"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("productName")){
                                jCBname.setModel
                            (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                            if (categories[i].equalsIgnoreCase("inventoryNumber")){
                                jCBinvnumber.setModel
                            (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
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
    public final void listenForSelectionIN () {
        jCBinvnumber.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    String selected = e.getItem().toString();
                
                    if (jCBinvnumber.getItemCount() == 0 ){
                        selected = ""; 
                        fillComboBox_Category(jCBinvnumber, "inventoryNumber");
                        
                    }
                
                    if (!selected.isBlank() && val.isNumeric(selected)){
                        List <Devices> list = hp.getItemByInvNumber(selected);
                        String [] categories = new String [] {"productName", "manufacturer"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("productName")){
                                jCBname.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                            if (categories[i].equalsIgnoreCase("manufacturer")){
                                jCBmanufacturer.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                        }
                    }
                }  
            }
        }); 
    }
    
    /**
     * adds an listener for the cancel-JButton and calls the deleteAll() Mehtod
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
        
           
        jCBuserID.setSelectedItem("");
        jCByear.setSelectedItem("");
        jCByear.setEnabled(true);
        jCBadminID.setSelectedItem("");
        jTFadminName.setText("Vor- und Nachname");
        jTFfirstname.setText("Vorname");
        jTFlastname.setText("Nachname");
        jTFphone.setText("Telefon");
        jTFmail.setText("E-Mail");
    }
    
    /**
     *
     * @param idBox to get the users ID from the JComboBox
     * @param tf_firs to get the users firstname from the JTextField
     * @param tf_last to get the users lastname from the JTextField
     * @param tf_phone to get the users phone form the JTextField
     * @param tf_mail to get the users mail from the JTextField
     * @param yearBox to get the users year from the JComboBox
     * 
     * method to create a Users Object with all the parameters 
     * validates the textfields 
     * @return Users Object if fields are valid and a null Object if not
     */
    public Users createUser(JComboBox idBox, JTextField tf_firs, JTextField tf_last,
                                JTextField tf_phone,JTextField tf_mail, JComboBox yearBox){
        
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
                            + " Bitte alles ausfüllen!");
        
        }else {
            if ( val.isAlpha(firstname) &&  val.isAlpha(lastname)){
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
            if ( val.isNumeric(id)) {
                idV = true;
            }
            
            if (nameV && emailV && phoneV && yearV && idV) {
                user = new Users(Integer.parseInt(id), firstname, lastname, phone, 
                                email, year);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Eingabefehler, Formatvorlage beachten!");
            }
        }
        return user;
    }
    
    /**
     *
     * @param chDate to get the rentalDate from the DateChosser
     * @param boxInvNumber to get the InventoryNumber from the JComboBox
     * @param boxUserID to get the userID from the JComboBox
     * @param boxAdminID to get the adminID from the JComboBox
     * method to create a new rental object, uses a Thread.sleep for the case
     * a new user must be created 
     * inserts the rental-object in the database
     */
    public void createNewRental (DateChooser chDate, JComboBox boxInvNumber,
                                    JComboBox boxUserID, JComboBox boxAdminID){
        
        try {
            Thread.sleep(1000); // wird benoetigt wenn neuer User hinzugefuegt wird, sonst SQL Errer wegen Foreign KEY

            SelectedDate selectedDate = chDate.getSelectedDate();
            LocalDate date = 
                LocalDate.of(selectedDate.getYear(), 
                selectedDate.getMonth(), selectedDate.getDay());
        
            String inventoryNumb = (String) boxInvNumber.getSelectedItem();
        
            String userID = (String) boxUserID.getSelectedItem();
        
            String adminID = (String) boxAdminID.getSelectedItem();
            
            if (inventoryNumb.isBlank() || userID.isBlank() || adminID.isBlank()){
                JOptionPane.showMessageDialog(null, "Ein oder mehrere Felder sind leer!"
                        + " Bitte ausfuellen!");
            } else {
                Rentals rental = new Rentals(date, Integer.parseInt(inventoryNumb),
                            Integer.parseInt(adminID),
                            Integer.parseInt(userID));
                
                hp.insertNewRental_DB(rental);
            }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } 
        JOptionPane.showMessageDialog(null, "Device verliehen! Status aktualisiert");
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
                
                String userID = jCBuserID.getEditor().getItem().toString();
                long id = Long.parseLong(userID);

                if (hp.isUserNew(id)){
                    
                    Users user = createUser(jCBuserID, jTFfirstname, 
                                jTFlastname, jTFphone, jTFmail, jCByear);

                    if (user == null){
                        validUser = false;
                    }
                    if (validUser){
                        hp.insertNewUser(user);
                        JOptionPane
                            .showMessageDialog(null, "Neuen User hinzugefügt");
                    }
                
                } else {
                    Users user = createUser(jCBuserID, jTFfirstname, 
                                jTFlastname, jTFphone, jTFmail, jCByear);

                    if (user == null){
                        validUser = false;
                    }
                }
                if (validUser){
                    createNewRental(dcDate, jCBinvnumber, jCBuserID, jCBadminID);
                    deleteAll();
                }
            }
        });
    } 
    
    
    /**
     *
     * @param list List of Devices Objects needed to create a object List 
     * @param itemCategory needed as a String to create the Objects List with the given itemCategory
     * @return a Object List for created with the given parameters
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
            case "users_UserID" :
                for (Devices dev : list){
                    i = dev.getUsers_userID();
                    itemArray.add(String.valueOf(i));
                }
                return itemArray;
            default :
                return itemArray;  
        } 
    }
    
}

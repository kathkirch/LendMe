/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Helper Class for all the CRUD methods to the database
 *
 * @author Katharina, bstra
 */
public class DatabaseHelper {

    private static Connection con;
    private static ResultSet rs;
    private static Statement stmt;

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////CONNECTION METHODS//////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**
     * method to connect to the database in load the JDBC-Driver
     */
    public static void connectDB() {
        con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded");
            con = DriverManager.getConnection("jdbc:mysql://10.25.2.145:3306/hermanna19?user=hermanna19&password=geb19&serverTimezone=CET");
            System.out.println("Connection to the DB successfully established");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
            System.out.println("Connection to DB failed");
            System.exit(1);
        }
    }

    /**
     * method to close the connection to the database
     */
    public void closeDB() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////READ IN DATABASE////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * returns all devices from device table with the status 0 (not lent)
     *
     * @return Devices Objects as a List
     */
    public List<Devices> getAvailableDevices() {

        List<Devices> devicesList = new ArrayList<>();

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM devices WHERE status=0;";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String productName = rs.getString("productName");
                String manufacturer = rs.getString("manufacturer");
                long inventoryNumber = rs.getLong("inventoryNumber");
                String notes = rs.getString("notes");

                Devices dev = new Devices();
                dev.setProductName(productName);
                dev.setManufacturer(manufacturer);
                dev.setInventoryNumber(inventoryNumber);
                dev.setNotes(notes);

                devicesList.add(dev);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getDevices");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return devicesList;
    }

    /**
     * method to return all active rentals joint with devices table to get
     * manufacturer and productname
     * @return List with RentalList Objects
     */
    public List<RentalList> getRentallist() {

        ArrayList <RentalList> Rentallist = new ArrayList<>();

        String query = "SELECT rentalID, rentalDate, rentals.administrators_adminID "
                + ",rentals.users_UserID, rentals.devices_inventoryNumber, "
                + "manufacturer, productname "
                + "from rentals "
                + "join devices "
                + "on devices_inventoryNumber = devices.inventoryNumber "
                + "where returnDate IS NULL;";
        
        try {
            
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {

                int rentalID = rs.getInt("rentalID");
                LocalDate rentalDate = rs.getDate("rentalDate").toLocalDate();
                long users_UserID = rs.getLong("users_UserID");
                long devices_inventoryNumber = rs.getLong("devices_inventoryNumber");
                int administrators_AdminID = rs.getInt("administrators_adminID");
                String manufacturer = rs.getString("manufacturer");
                String productname = rs.getString("productname");

                RentalList ren = new RentalList(productname, manufacturer,
                        rentalDate, devices_inventoryNumber,
                        administrators_AdminID, users_UserID);
                ren.setRentalID(rentalID);

                Rentallist.add(ren);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return Rentallist;
    }

    /**
     * method to return all completed rentals = rentals where returnDate not is
     * null
     *
     * @return Rentals Objects as an ArrayList
     */
    public ArrayList<Rentals> getCompletedRentals() {

        ArrayList<Rentals> allRentals = new ArrayList<>();

        String query = "SELECT * FROM rentals WHERE returnDate IS NOT NULL";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {

                int rentalID = rs.getInt("rentalID");
                LocalDate rentalDate = rs.getDate("rentalDate").toLocalDate();
                LocalDate returnDate = rs.getDate("returnDate").toLocalDate();
                long inventoryNumb = rs.getLong("devices_inventoryNumber");
                int adminID = rs.getInt("administrators_adminID");
                long userID = rs.getLong("users_UserID");

                Rentals rental = new Rentals(rentalDate, inventoryNumb, adminID,
                        userID);

                rental.setRentalID(rentalID);
                rental.setReturnDate(returnDate);

                allRentals.add(rental);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("readRentals");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }

        return allRentals;
    }

    /**
     * method to return devices with a specific productName
     *
     * @param productName as a String, needed to search in the Database
     * @return Devices Objects as a List with the given productName
     */
    public List<Devices> getItemByProductName(String productName) {

        List<Devices> items = new ArrayList<>();
        String query = "SELECT manufacturer, inventoryNumber"
                + " FROM devices WHERE productName=" + "'" + productName
                + "' AND status=0;";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String manufacturer = rs.getString("manufacturer");
                long inventoryNumber = rs.getLong("inventoryNumber");

                Devices d = new Devices();
                d.setManufacturer(manufacturer);
                d.setInventoryNumber(inventoryNumber);
                items.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getItemByProductName");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return items;
    }

    /**
     * method to return devices with a specific manufacturer
     *
     * @param manufacturer as a String needed to search in Database
     * @return Devices Objects as a List with the given manufacturer
     */
    public List<Devices> getItemByManufacturer(String manufacturer) {

        List<Devices> items = new ArrayList<>();
        String query = "SELECT productName, inventoryNumber"
                + " FROM devices WHERE manufacturer=" + "'" + manufacturer
                + "' AND status=0;";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String productName = rs.getString("productName");
                long inventoryNumber = rs.getLong("inventoryNumber");

                Devices d = new Devices();
                d.setProductName(productName);
                d.setInventoryNumber(inventoryNumber);
                items.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getItemByManufacturer");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return items;
    }

    /**
     * method to return devices with a specific inventorynumber
     *
     * @param invNumber as a String needed to search for in the Database
     * @return Devices Objects as a List with the given invNumber
     */
    public List<Devices> getItemByInvNumber(String invNumber) {

        List<Devices> items = new ArrayList<>();
        String query = "SELECT productName, manufacturer"
                + " FROM devices WHERE inventoryNumber=" + "'"
                + Long.parseLong(invNumber)
                + "' AND status=0;";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String productName = rs.getString("productName");
                String manufacturer = rs.getString("manufacturer");

                Devices d = new Devices();
                d.setProductName(productName);
                d.setManufacturer(manufacturer);
                items.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getItemByInvNumber");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return items;
    }

    /**
     * method to return all userIDs stored in db
     *
     * @return List of Strings with all the UserID's in the Database
     */
    public List<String> getUsersID() {

        List<String> userIDs = new ArrayList<>();
        String query = "SELECT userID FROM users";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                long userID = rs.getLong("userID");
                userIDs.add(String.valueOf(userID));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getUsersID");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return userIDs;
    }

    /**
     * method to return all userYears stored in db
     *
     * @return List of Strings with all UserYears from the database
     */
    public List<String> getUserYears() {
        List<String> userYears = new ArrayList<>();
        String query = "SELECT DISTINCT userYear FROM users ORDER BY userYear ASC";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String userYear = rs.getString("userYear");
                userYears.add(userYear);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getUserYear");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return userYears;
    }

    /**
     * method to return the adminID of a specific device by its inventorynumber
     * 
     * @param invNumber as a String to search for in the database
     * @return the adminID as an String matching to the given inventorynumber
     */
    public String getDeviceAdminID(String invNumber) {

        long number = Long.parseLong(invNumber);

        String adminID = null;

        String query = "SELECT administrators_adminID FROM devices "
                + "WHERE inventoryNumber = " + number + ";";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                adminID = rs.getString("administrators_adminID");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getDeviceAdminIDs");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return adminID;
    }

    /**
     * method to return all AdminIDs stored in the database
     *
     * @return List of Strings with all AdminID's from the database
     */
    public List<String> getAdminIDs() {
        String adminID;
        List<String> adminIDs = new ArrayList<>();

        String query = "SELECT adminID FROM administrators ORDER BY adminID ASC";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                adminID = rs.getString("adminID");
                adminIDs.add(adminID);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getAdminIDs");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return adminIDs;
    }

    /**
     * method to return the Admin-Fullname by specific adminID
     *
     * @param adminID as a String needed to search for in the Database
     * @return adminName from the database with the given adminID
     */
    public String getAdminNameByID(String adminID) {
        String adminName = null;

        String query = "SELECT concat(adminFirstName," + "' '" + ", adminLastName)"
                + "as " + "'Fullname'" + "FROM administrators "
                + "WHERE adminID=" + Integer.parseInt(adminID) + ";";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                adminName = rs.getString("Fullname");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getAdminByID");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return adminName;
    }

    /**
     * method to return user with the given userID
     *
     * @param userID needed as a String to search in the Database
     * @return Users Object with the given UserID
     */
    public Users getUserByID(String userID) {

        Users user = null;

        String query = "SELECT * FROM users WHERE userID = ? ";
       
        long id = Long.parseLong(userID);
                
        try {

            PreparedStatement prep = con.prepareStatement(query);
            prep.setLong(1, id);
            rs = prep.executeQuery();

            while (rs.next()) {
                String userFirstName = rs.getString("userFirstName");
                String userLastName = rs.getString("userLastName");
                String userEmail = rs.getString("userEmail");
                String userPhone = rs.getString("userPhone");
                String userYear = rs.getString("userYear");

                user = new Users(id, userFirstName,
                        userLastName, userEmail, userPhone, userYear);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getUserByID");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return user;
    }
    
    /**
     * method to search for a rentals entry in database with 
     * a specific rentalID
     * 
     * @param rentalID as int needed to search in the Database
     * @return the Rentals-Object with the given rentalID
     */
    public Rentals getRentalByID (int rentalID){
        
        String query = "SELECT * FROM rentals"
                        + " WHERE rentalID=" + rentalID + ";";
        
        Rentals rental = null;
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                LocalDate rentalDate = rs.getDate("rentalDate").toLocalDate();
                Date returnDate = rs.getDate("returnDate");
                long inventoryNumb = rs.getLong("devices_inventoryNumber");
                int adminID = rs.getInt("administrators_adminID");
                long userID = rs.getLong("users_UserID");
                
                
               rental = new Rentals (rentalDate, 
                                            inventoryNumb, adminID, userID);
               
               if (returnDate != null){
                   
                   LocalDate retDate = returnDate.toLocalDate();
                   
                   rental.setReturnDate(retDate);
               }
            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getRentalByID");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return rental;
    }
    
    /**
     * method to search for a devices entry in database with 
     * a specific inventroyNumber
     * 
     * @param invNumb as String needed to search in the Database
     * @return a Devices Object with the given invNumber
     */
    public Devices getDeviceByID(String invNumb) {
        
        long l = Long.parseLong(invNumb);
        
        String query = "SELECT * FROM devices"
                        + " WHERE inventoryNumber=" + l + ";";
        
        Devices dev = null;
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                long invNo = rs.getLong(1);
                String manuf = rs.getString(2);
                String prodN = rs.getString(3);
                String notes = rs.getString(4);
                String location = rs.getString(5);
                int stat = rs.getInt(6);
                String im = rs.getString(7);
                long usID = rs.getLong(8);
                double acV = rs.getDouble(9);
                LocalDate acD = rs.getDate(10).toLocalDate();
                int admin = rs.getInt(11);
                
                dev = new Devices ();
                dev.setInventoryNumber(invNo);
                dev.setManufacturer(manuf);
                dev.setProductName(prodN);
                dev.setNotes(notes);
                dev.setLocation(location);
                dev.setStatus(stat);
                dev.setImei(im);
                dev.setUsers_userID(usID);
                dev.setAquisitionValue(acV);
                dev.setAquistionDate(acD);
                dev.setAdminID(admin); 

            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("getDeviceByID");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return dev;
    }
    
    /**
     *  similar to getDeviceByID() but returning a String Array with results
     *  instead of a Device-Object; the String [] lets us handle results
     *  more elegantly in the calling method
     * @param invNo inventoryNumber to query DB with
     * @return return Query-Results as String Array
     */
    public String[] getDeviceByID2 (String invNo) {
        
        String[] deviceData = new String[9];
        
        String query = "SELECT * FROM devices WHERE "
                + "inventoryNumber = " + invNo +";";
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                deviceData[0] = rs.getString(1); // Inventory Number
                deviceData[1] = rs.getString(2); // Manufacturer
                deviceData[2] = rs.getString(3); // Productname
                deviceData[3] = rs.getString(4); // Notes
                deviceData[4] = rs.getString(5); // Location
                deviceData[5] = rs.getString(7); // Imei
                deviceData[6] = rs.getString(9); // Acquisition Value
                deviceData[7] = rs.getString(10); // Acquisition Date
                deviceData[8] = rs.getString(11); // Admin ID
            }
         } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return deviceData;
    }

    /**
     * **************** QUERYS HANDLING INVENTORY/DEVICES *******************
     */

  
    /**
     * method to get all Devices stored in the Database
     * 
     * @return returns all Devices as a Devices-ArrayList
     */
    public ArrayList<Devices> getAllDevices2() {

        ArrayList<Devices> allDevices = new ArrayList<>();

        String query = "SELECT * FROM devices;";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {

                long invNo = rs.getLong(1);
                String manuf = rs.getString(2);
                String prodN = rs.getString(3);
                String notes = rs.getString(4);
                String location = rs.getString(5);
                int stat = rs.getInt(6);
                String im = rs.getString(7);
                long usID = rs.getLong(8);
                double acV = rs.getDouble(9);
                LocalDate acD = rs.getDate(10).toLocalDate();
                int admin = rs.getInt(11);

                Devices device = new Devices();

                device.setInventoryNumber(invNo);
                device.setManufacturer(manuf);
                device.setProductName(prodN);
                device.setNotes(notes);
                device.setLocation(location);
                device.setStatus(stat);
                device.setImei(im);
                device.setUsers_userID(usID);
                device.setAquisitionValue(acV);
                device.setAquistionDate(acD);
                device.setAdminID(admin); // chanched setAdminID as int

                allDevices.add(device);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return allDevices;
    }

    /**
     * prepares and executes Update Query
     * @param toUpdate which and how many columns to update
     * @param invNo which device to update
     * @throws SQLException Errors are handled in caller method
     */
    public void updateDevice(String toUpdate, String invNo) throws SQLException {

        
        String query = "UPDATE devices SET " + toUpdate
                + " WHERE inventoryNumber = " + invNo + ";";

        System.out.println(query);
        
        stmt = con.createStatement();
        stmt.executeUpdate(query);
        

        if (stmt != null) {
            stmt.close();
        }
    }

  
    public void insertNewDevice(String toInsert, boolean noImei) throws SQLException {

        String query;

        if (noImei) {
            query = "INSERT INTO devices (inventoryNumber, manufacturer, "
                    + "productname, notes, location, administrators_adminID, "
                    + "acquisitionValue, acquisitionDate) "
                    + "VALUES (" + toInsert + ");";

        } else {
            query = "INSERT INTO devices (inventoryNumber, manufacturer, "
                    + "productname, notes, location, administrators_adminID, "
                    + "acquisitionValue, acquisitionDate, imei) "
                    + "VALUES (" + toInsert + ");";
        }

        
        stmt = con.createStatement();
        stmt.executeUpdate(query);

        if (stmt != null) {
            stmt.close();
        }

    }

    /**
     * prepares and executes Delete From Query
     * @param deviceToDelete InventoryNumber of Device to delete
     * @return returns number of successfully deleted datasets; will be 1 or 0
     *      is used to display confirmation or error message to user
     * @throws SQLException handled in caller method
     */
    public int deleteDevice(String deviceToDelete) throws SQLException {

        stmt = con.createStatement();

        String queryDevice = "DELETE FROM devices WHERE "
                + "(inventoryNumber = " + deviceToDelete + ");";
        stmt = con.createStatement();
        int i = stmt.executeUpdate(queryDevice);
        

        if (stmt != null) {
            stmt.close();
        }
        return i;
    }

    
    /**
     * get all existing Inventory Numbers in DB
     * @return return them as String Array
     * called in Update-Device Class to ascertain newly entered Inventory-ID
     * is UNIQUE
     */
    public ArrayList<String> allInventoryNumbers() {

        ArrayList<String> allInvNos = new ArrayList<>();

        try {
            String query = "SELECT inventoryNumber FROM devices;";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String in = rs.getString(1);
                allInvNos.add(in);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allInvNos;
    }

    /**
     * get all existing IMEI-Numbers in DB
     * @return return them as String Array
     * called in Update-Device Class to ascertain newly entered IMEI is UNIQUE
     */
    public ArrayList<String> allImeiNumbers() {

        ArrayList<String> allImeis = new ArrayList<>();

        try {
            String query = "SELECT imei FROM devices;";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String in = rs.getString(1);
                allImeis.add(in);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allImeis;
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////INSERT IN DATABASE/////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * method to insert a new user in the database
     *
     * @param user as a Users Object is needed to insert the User-Object with
     * it's Attributes as Data in the Database
     * @throws Project_LendMe.UserException caught in Rental_Helper in method
     * 'saveNewRental'
     */
    public void insertNewUser(Users user) throws UserException {

        String insert = "INSERT INTO users (userID, userFirstName, "
                        + "userLastName, userEmail, userPhone, userYear) values"
                        + " (?, ?, ?, ?, ?, ?);";
        
        if (user != null) {
            try {
                PreparedStatement prep = con.prepareStatement(insert);
 
                prep.setLong(1, user.getUserID());
                prep.setString(2, user.getUserFirstName());
                prep.setString(3, user.getUserLastName());
                prep.setString(4, user.getUserEmail());
                prep.setString(5, user.getUserPhone());
                prep.setString(6, user.getYear());
                
                prep.executeUpdate();

            } catch (SQLException ex) {
                System.out.println(ex);
                System.out.println("insertNewUser");
                throw new UserException();
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
    }

    /**
     * method to insert a new rental in database
     *
     * @param rental as a Rentals Object is needed to insert the Rental-Object
     * with it's Attributes as Data in the Database
     * @throws Project_LendMe.UserException caught in Rental_Helper method
     * 'createNewRental'
     *
     */
    public void insertNewRental_DB(Rentals rental) throws UserException {
        
       String dateSt = rental.getRentalDate().toString();
        
        String insert = "INSERT INTO rentals "
                    + "(rentalDate, devices_inventoryNumber, "
                    + "administrators_adminID, users_userID) "
                    + "values (?, ?, ?, ?);";
        
        try {
            PreparedStatement prep = con.prepareStatement(insert);
            
            prep.setString(1, dateSt);
            prep.setLong(2, rental.getDevice_inventoryNumber());
            prep.setInt(3, rental.getAdministrators_AdminID());
            prep.setLong(4, rental.getUsers_UserID());
            
            prep.executeUpdate();

            setDevice_Lent(rental.getDevice_inventoryNumber(),
                    rental.getUsers_UserID());

            System.out.println("Datensatz erfolgreich hinzugefuegt");

        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("insertNewRental_DB");
            throw new UserException();

        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////UPDATE IN DATABASE///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * method to update the device status to 1 and userID when a device is lent
     *
     * @param device_inventoryNumber needed to search for in the database
     * @param userID needed to search for in the database updates the values for
     * userID and status automatically if the method is called and a suitable
     * entry for the given parameters in the database was found
     */
    public void setDevice_Lent(long device_inventoryNumber, long userID) {

        stmt = null;
        rs = null;

        try {
            stmt = con.createStatement();
            String table = "devices";
            rs = stmt.executeQuery("SELECT * FROM " + table
                    + " WHERE inventoryNumber = '" + device_inventoryNumber + "';");

            while (rs.next()) {

                String s = "UPDATE " + table + " SET status = ?, users_UserID = ?"
                        + " WHERE inventoryNumber = ?;";
                PreparedStatement prepStat = con.prepareStatement(s);
                prepStat.setInt(1, Devices.LENT);
                prepStat.setLong(2, userID);
                prepStat.setLong(3, device_inventoryNumber);
                System.out.println(s);
                prepStat.executeUpdate();
                System.out.println("Device status was successfully changed");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("setDevice_Lent");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    /**
     * method to update the device status to 0 and notes when a device returned
     *
     * @param device_inventoryNumber needed to search for the device in the
     * database
     * @param notes as a String, to store the notes in the notes field in
     * database
     * @throws Project_LendMe.UserException caught in Return_Helper in method
     * 'createNewReturn'
     */
    public void setDevice_NotLent(long device_inventoryNumber, String notes)
            throws UserException {

        stmt = null;
        rs = null;
        Long nullLong = null;

        try {
            stmt = con.createStatement();
            String table = "devices";
            rs = stmt.executeQuery("SELECT * FROM " + table
                    + " WHERE inventoryNumber= '" + device_inventoryNumber + "'");

            while (rs.next()) {

                String s = "UPDATE " + table
                        + " SET "
                        + "status = ?, "
                        + "users_UserID = ?,"
                        + "notes = ?"
                        + "WHERE inventoryNumber = ?";

                PreparedStatement prepStat = con.prepareStatement(s);
                prepStat.setInt(1, Devices.NOT_LENT);
                prepStat.setObject(2, nullLong);
                prepStat.setString(3, notes);
                prepStat.setLong(4, device_inventoryNumber);
                System.out.println(s);
                prepStat.executeUpdate();
                System.out.println("Device status was successfully changed");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("setDevice_NotLent");
            throw new UserException();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    /**
     * method to update the rentals table with the returnDate when a device is
     * returned
     *
     * @param rentalID to get the specific rental entry
     * @param returnDate as LocalDate to update the rental with the returnDate
     * @throws UserException caught in Return_Helper in method 'createNewReturn'
     */
    public void updateRentals(int rentalID, LocalDate returnDate)
            throws UserException {

        String table = "rentals";
        stmt = null;
        rs = null;

        String query = "SELECT * FROM " + table
                + " WHERE rentalID = '" + rentalID + "';";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String s = "UPDATE " + table + " SET returnDate= ? "
                        + "WHERE rentalID= ?;";
                PreparedStatement prepStat = con.prepareStatement(s);
                prepStat.setString(1, returnDate.toString());
                prepStat.setInt(2, rentalID);
                prepStat.executeUpdate();
                System.out.println("ReturnDate was set successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            throw new UserException();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////READ IN DATABASE --> FILTER//////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * method for filter- and search functions in the archive table
     *
     * @param whereClause as an int for the whereClause means in which column
     * should be filtered
     * @param filterString as an String means on which data should be filtered
     * @return the filtered list of Rentals-Objects if filtering was successful
     * or an empty list if filtering had no result
     */
    public List<Rentals> filterRentals(int whereClause, String filterString) {

        stmt = null;
        rs = null;
        String table = "rentals";
        String where = "";
        ArrayList<Rentals> filteredRentals = new ArrayList<>();

        switch (whereClause) {
            case 0:
                where = "rentalID";
                break;
            case 1:
                where = "rentalDate";
                break;
            case 2:
                where = "returnDate";
                break;
            case 3:
                where = "devices_inventoryNumber";
                break;
            case 4:
                where = "users_userID";
                break;
        }
        
        String query = "SELECT * FROM " + table
                    + " WHERE " + where + " LIKE ? "
                    + "AND returnDate IS NOT NULL;";

        try {
            PreparedStatement prep = con.prepareStatement(query);
            prep.setString(1, "%"+filterString+"%");
            rs = prep.executeQuery();

            while (rs.next()) {

                int rentalID = rs.getInt("rentalID");
                LocalDate rentalDate = rs.getDate("rentalDate").toLocalDate();
                LocalDate returnDate = rs.getDate("returnDate").toLocalDate();
                long inventoryNumb = rs.getLong("devices_inventoryNumber");
                int adminID = rs.getInt("administrators_adminID");
                long userID = rs.getLong("users_UserID");

                Rentals rental = new Rentals(rentalDate, inventoryNumb, adminID,
                        userID);

                rental.setRentalID(rentalID);
                rental.setReturnDate(returnDate);

                filteredRentals.add(rental);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return filteredRentals;
    }

    /**
     * the method for filter- and search functions in the rentallist table
     *
     * @param whereClause as an int for the whereClause means in which column
     * should be filtered
     * @param filterString as an String means on which data should be filtered
     * @return the filtered list of Rentals-Objects if filtering was successful
     * or an empty list if filtering had no result
     */
    public List<RentalList> filterRentals2(int whereClause, String filterString) {

        stmt = null;
        rs = null;
        String table = "rentals";
        String joinTable = "devices";
        String where = "";
        ArrayList <RentalList> filteredRentallist = new ArrayList<>();

        switch (whereClause) {
            case 0:
                where = "rentalID";
                break;
            case 1:
                where = "devices_inventoryNumber";
                break;
            case 2:
                where = "productname";
                break;
            case 3:
                where = "manufacturer";
                break;
            case 4:
                where = "users_userID";
                break;
            case 5:
                where = "rentalDate";
                break;
        }
     
        String query = "SELECT rentalID, rentalDate, rentals.administrators_adminID, "
                + "rentals.users_UserID, devices_inventoryNumber, "
                + "manufacturer, productname "
                + "FROM " + table
                + " JOIN " + joinTable
                + " ON devices_inventoryNumber = devices.inventoryNumber "
                + "WHERE " + where + " LIKE ? AND returnDate IS NULL;";

        try {
            PreparedStatement prep = con.prepareStatement(query);
            prep.setString(1, "%"+filterString+"%");
            rs = prep.executeQuery();

            while (rs.next()) {
                int rentalID = rs.getInt("rentalID");
                LocalDate rentalDate = rs.getDate("rentalDate").toLocalDate();
                long users_UserID = rs.getLong("users_UserID");
                long devices_inventoryNumber = rs.getLong("devices_inventoryNumber");
                int administrators_AdminID = rs.getInt("administrators_adminID");
                String manufacturer = rs.getString("manufacturer");
                String productname = rs.getString("productname");

                RentalList ren = new RentalList(productname, manufacturer,
                        rentalDate, devices_inventoryNumber,
                        administrators_AdminID, users_UserID);
                ren.setRentalID(rentalID);
                filteredRentallist.add(ren);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE,
                    null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return filteredRentallist;
    }

    /**
     * builds and executes a Query to DB with passed Params
     * @param column Which Column to filter by
     * @param filterBy Value to filter by
     * @param filterOption Option (&lt; &gt; =) to filter by
     * @return a list only with filtered elements
     */
    public List<Devices> filterInventory(int column, String filterBy, int filterOption) {

        stmt = null;
        rs = null;
        String where = "";
        String option = "";
        ArrayList<Devices> filteredInventory = new ArrayList<>();
        switch (column) {
            case 0:
                where = "inventoryNumber";
                break;
            case 1:
                where = "manufacturer";
                break;
            case 2:
                where = "productname";
                break;
            case 3:
                where = "administrators_adminID";
                break;

        }
        
        switch (filterOption) {
            case 1:
                option = " LIKE '%" + filterBy +"%'";
                break;
            case 2:
                option = " < " + filterBy;
                break;
            case 3:
                option = " > " +filterBy;
                break;
        }

        String query = "SELECT * FROM devices WHERE " + where + option + ";";

        System.out.println(query);
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {

                long invNo = rs.getLong(1);
                String manuf = rs.getString(2);
                String prodN = rs.getString(3);
                int admin = rs.getInt(11);

                Devices device = new Devices();

                device.setInventoryNumber(invNo);
                device.setManufacturer(manuf);
                device.setProductName(prodN);
                device.setAdminID(admin);

                filteredInventory.add(device);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }

        return filteredInventory;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * method to check wether user with given userID already exists in database
     * or not
     *
     * @param userID needed to search for in the Database
     * @return true if the user not exists in the Database, false if the user
     * exists
     */
    public boolean isUserNew(long userID) {

        boolean userNEW = true;
        String query = "SELECT * FROM users WHERE userID = ?;";

        try {
            PreparedStatement prep = con.prepareStatement(query);
            prep.setLong(1, userID);
            rs = prep.executeQuery();

            if (rs.next()) {
                userNEW = false;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("isUserNew");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return userNEW;
    }
}

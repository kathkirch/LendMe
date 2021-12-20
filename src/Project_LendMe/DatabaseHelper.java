/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.sql.Connection;
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
 *
 * @author Katharina
 */
public class DatabaseHelper {
    
    private static Connection con;
    private static ResultSet rs;
    private static Statement stmt;
    
    public static void connectDB(){
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
    
    public static void closeDB() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     *returns all devices from device table with the status 0 (not lent)
     * @return Devices Objects as a List
     */
    public List <Devices> getDevices(){
    
        List <Devices> devicesList = new ArrayList <>();
        
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM devices WHERE status=0";
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                String productName = rs.getString("productName");
                String manufacturer = rs.getString("manufacturer");
                int inventoryNumber = rs.getInt("inventoryNumber");
                
                Devices dev = new Devices();
                dev.setProductName(productName);
                dev.setManufacturer(manufacturer);
                dev.setInventoryNumber(inventoryNumber);
                
                devicesList.add(dev);
            }
        
        }catch (SQLException ex){
            System.out.println(ex);
        } finally {
            if (stmt != null) {
                try{
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } 
        }
        return devicesList;
    }
    
    /**
     * returns all completed rentals, just rentals with a returnDate
     * @return Rentals Objects as an ArrayList
     */
    public ArrayList <Rentals> readRentals () {
        
        ArrayList <Rentals> allRentals = new ArrayList <>();
        
        String query = "SELECT * FROM rentals WHERE returnDate IS NOT NULL";
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                
                int rentalID = rs.getInt("rentalID");
                LocalDate rentalDate = rs.getDate("rentalDate").toLocalDate();
                LocalDate returnDate = rs.getDate("returnDate").toLocalDate();
                int inventoryNumb = rs.getInt("devices_inventoryNumber");
                int adminID = rs.getInt("administrators_adminID");
                int userID = rs.getInt("users_UserID");
                
                Rentals rental = new Rentals(rentalDate, inventoryNumb, adminID,
                                                userID);
                
                rental.setRentalID(rentalID);
                rental.setReturnDate(returnDate);
                
                allRentals.add(rental);
            }
        }catch (SQLException ex) {
            System.out.println(ex);
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        
        return allRentals;
    }

    /**
     *
     * @param productName as a String, needed to search in the Database
     * @return Devices Objects as a List with the given productName 
     */
    public List <Devices> getItemByProductName(String productName){
        
        List <Devices> items = new ArrayList <>();
        String query = "SELECT manufacturer, inventoryNumber"
                        + " FROM devices WHERE productName=" + "'" + productName
                        + "';";
                
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                String manufacturer = rs.getString("manufacturer");
                int inventoryNumber = rs.getInt("inventoryNumber");
                
                Devices d = new Devices();
                d.setManufacturer(manufacturer);
                d.setInventoryNumber(inventoryNumber);
                items.add(d);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        } finally {
            if (stmt != null) {
                try{
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } 
        } 
        return items;
    }
    
    /**
     *
     * @param manufacturer as a String needed to search in Database
     * @return Devices Objects as a List with the given manufacturer
     */
    public List <Devices> getItemByManufacturer(String manufacturer){
        
        List <Devices> items = new ArrayList <>();
        String query = "SELECT productName, inventoryNumber"
                        + " FROM devices WHERE manufacturer=" + "'" + manufacturer
                        + "';";
                
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                String productName = rs.getString("productName");
                int inventoryNumber = rs.getInt("inventoryNumber");
                
                Devices d = new Devices();
                d.setProductName(productName);
                d.setInventoryNumber(inventoryNumber);
                items.add(d);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        } finally {
            if (stmt != null) {
                try{
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } 
        } 
        return items;
    }
    
    /**
     *
     * @param invNumber as a String needed to search for in the Database
     * @return Devices Objects as a List with the given invNumber
     */
    public List <Devices> getItemByInvNumber (String invNumber){
        
        List <Devices> items = new ArrayList <>();
        String query = "SELECT productName, manufacturer"
                        + " FROM devices WHERE inventoryNumber=" + "'" 
                        + Integer.parseInt(invNumber)
                        + "';";
                
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                String productName = rs.getString("productName");
                String manufacturer = rs.getString("manufacturer");
                
                Devices d = new Devices();
                d.setProductName(productName);
                d.setManufacturer(manufacturer);
                items.add(d);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        } finally {
            if (stmt != null) {
                try{
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } 
        } 
        return items;
    }
    
    /**
     *
     * @return List of Strings with all the UserID's in the Database
     */
    public List <String> getUsersID(){
        
        List <String> userIDs = new ArrayList <>();
        String query = "SELECT userID FROM users";
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                int userID = rs.getInt("userID");
                userIDs.add(String.valueOf(userID));
            }
        }catch (SQLException ex){
            System.out.println(ex);
        } finally {
            if (stmt != null) {
                try{
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } 
        } 
        return userIDs;
    }
    
    /**
     *
     * @return List of Strings with all UserYears from the database
     */
    public List <String> getUserYears(){
        List <String> userYears = new ArrayList <>();
        String query = "SELECT userYear FROM users";
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                String userYear = rs.getString("userYear");
                userYears.add(userYear);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        } finally {
            if (stmt != null) {
                try{
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } 
        } 
        return userYears;
    }
    
    /**
     *
     * @return List of Strings with all AdminID's from the database
     */
    public List <String> getAdminIDs() {
        String adminID;
        List <String> adminIDs = new ArrayList<>();
        
        String query = "SELECT adminID FROM administrators";
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                adminID = rs.getString("adminID");
                adminIDs.add(adminID);
            }
        
        }catch (SQLException ex){
            System.out.println(ex);
        } finally {
            if (stmt != null) {
                try{
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } 
        }
        return adminIDs;
    }
         
    /**
     *
     * @param adminID as a String needed to search for in the Database
     * @return adminName from the database with the given adminID
     */
    public String getAdminNameByID (String adminID){
        String adminName = null;
        
        String query = "SELECT concat(adminFirstName," + "' '" +", adminLastName)"
                    + "as " +"'Fullname'" + "FROM administrators "
            + "WHERE adminID=" + Integer.parseInt(adminID) + ";";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){
                adminName = rs.getString("Fullname");
            }

        }catch (SQLException ex){
            System.out.println(ex);
        } finally {
            if (stmt != null) {
                try{
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } 
        }  
        return adminName;
    }
    
    /**
     *
     * @param userID needed as a String to search in the Database
     * @return Users Object with the given UserID
     */
    public Users checkUserID(String userID){
        
        Users user = null;
        
        String query = "SELECT * FROM users WHERE userID=" + "'" 
                    + Integer.parseInt(userID)
                    + "';";      
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){
                String userFirstName = rs.getString("userFirstName");
                String userLastName = rs.getString("userLastName");
                String userEmail = rs.getString("userEmail");
                String userPhone = rs.getString("userPhone");
                String userYear = rs.getString("userYear");

                user = new Users (Integer.parseInt(userID), userFirstName, 
                                userLastName, userEmail, userPhone, userYear);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        } finally {
            if (stmt != null) {
                try{
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } 
        }  
        return user;
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
        int i;
        
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
        
    /**
     *
     * @param user as a Users Object is needed to insert 
     * the User-Object with it's Attributes as Data in the Database
     */
    public void insertNewUser(Users user){
        
        if (user != null){
            try{
            stmt = con.createStatement();
            String query = "INSERT INTO users (userID, userFirstName, "
                    + "userLastName, userEmail, userPhone, userYear) values ('"
                    + user.getUserID() + "', '" + user.getUserFirstName() + "', '"
                    + user.getUserLastName() + "', '" + user.getUserEmail() 
                    + "', '" + user.getUserPhone() + "', '" + user.getYear() 
                    + "');";
            
            System.out.println("insertString" + query);
            
            stmt.executeUpdate(query);
            
            System.out.println("User hinzugefuegt");
            
            }catch(SQLException ex) {
                System.out.println(ex);
                System.out.println("Error with Database");
            } finally {
                if (stmt != null) {
                    try{
                        stmt.close();
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                } 
            }
        } 
    }
    
    /**
     *
     * @param userID needed to search for in the Database
     * @return true if the user not exists in the Database, false if the user exists
     */
    public boolean isUserNew(String userID){
        
        boolean userNEW = true;
        String query = "SELECT * FROM users WHERE userID=" + userID + ";";
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            if (rs.next()){
                userNEW = false;
            }
        }catch(SQLException ex) {
            System.out.println(ex);
            System.out.println("Error with Database");
        } finally {
            if (stmt != null) {
                try{
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } 
        }  
        return userNEW;
    }
    
    /**
     *
     * @param rental as a Rentals Object is needed to insert 
     * the Rental-Object with it's Attributes as Data in the Database
     */
    public void insertNewRental_DB(Rentals rental) {
        try{ 
            stmt = con.createStatement();
            String string = "insert into rentals (rentalDate, "
                            +"devices_inventoryNumber, administrators_adminID, "
                            +"users_userID) values ('" + rental.getRentalDate() 
                            +"', '" + rental.getDevice_inventoryNumber() 
                            + "', '" + rental.getAdministrators_AdminID()
                            + "', '" + rental.getUsers_UserID()+ "');";
            stmt.executeUpdate(string);
            updateDeviceStatus(rental.getDevice_inventoryNumber(), rental.getUsers_UserID());
            
            System.out.println("Datensatz erfolgreich hinzugefuegt");
            
        } catch(SQLException ex) {
            System.out.println(ex);
            System.out.println("Error with Database");
        } finally {
            if (stmt != null) {
                try{
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } 
        }
    }
        
    /**
     *
     * @param device_inventoryNumber needed as a int to search for in the database
     * @param userID needed to search for in the database
     * updates the values for userID and status automatically if the method is called
     * and a suitable entry for the given parameters in the database was found
     */
    public void updateDeviceStatus(int device_inventoryNumber, int userID) {
        stmt = null;
        rs = null;
        
        try {
            stmt = con.createStatement();
            String table = "devices";
            rs = stmt.executeQuery("SELECT * FROM " + table + 
                    " WHERE inventoryNumber= '" + device_inventoryNumber + "'");
            
            while(rs.next()){
                String s = "UPDATE " + table + " SET status = ?, users_UserID "
                        + "WHERE inventoryNumber= ?";
                PreparedStatement prepStat = con.prepareStatement(s);
                prepStat.setInt(1, 1);
                prepStat.setInt(2, userID);
                prepStat.setInt(3, device_inventoryNumber);
                System.out.println(s);
                prepStat.executeUpdate();
                System.out.println("Device status was successfully changed");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                if (stmt != null) {
                    try { 
                        stmt.close();
                    } catch (SQLException ex){
                        System.out.println(ex);
                    }
                }  
            } 
    }
    
    public List <Rentals> filterRentals (int whereClause, String filterString){
        
        stmt = null;
        rs = null;
        String table = "rentals";
        String where = "";
        ArrayList <Rentals> filteredRentals = new ArrayList <>();
        
        switch (whereClause) {
            case 0 :
                where = "rentalID";        
                break;
            case 1 :
                where = "rentalDate";
                break;
            case 2 :
                where = "returnDate";
                break;
            case 3 :
                where = "devices_inventoryNumber";
                break;
            case 4 :
                where = "administrators_adminID";
                break;
            case 5 :
                where = "users_userID";
                break;
        }
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + table + 
                    " WHERE " + where + "='" + filterString 
                    + "' AND returnDate IS NOT NULL");
            
            while(rs.next()){
                
                int rentalID = rs.getInt("rentalID");
                LocalDate rentalDate = rs.getDate("rentalDate").toLocalDate();
                LocalDate returnDate = rs.getDate("returnDate").toLocalDate();
                int inventoryNumb = rs.getInt("devices_inventoryNumber");
                int adminID = rs.getInt("administrators_adminID");
                int userID = rs.getInt("users_UserID");
                
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
                } catch (SQLException ex){
                    System.out.println(ex);
                }
            }  
        } 
        return filteredRentals;
    }
    
    public List <Rentallist> filterRentals2 (int whereClause, String filterString){
        
        stmt = null;
        rs = null;
        String table = "rentals";
        String joinTable = "devices";
        String where = "";
        ArrayList <Rentallist> filteredRentallist = new ArrayList <>();
        
        switch (whereClause) {
            case 0 :
                where = "rentalID";        
                break;
            case 1 :
                where = "devices_inventoryNumber";
                break;
            case 2 :
                where = "productname";
                break;
            case 3 :
                where = "manufacturer";
                break;
            case 4 :
                where = "users_userID";
                break;
            case 5 :
                where = "rentalDate";
                break;
        }
        String query = "SELECT rentalID, rentalDate, administrators_adminID, "
                    + "rentals.users_UserID, devices_inventoryNumber, "
                    + "manufacturer, productname "
                    + "FROM " + table
                    + " JOIN " + joinTable
                    + " ON devices_inventoryNumber = devices.inventoryNumber "
                    + "WHERE " + where + "='" + filterString 
                    + "' AND returnDate IS NULL;";
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while(rs.next()){
                int rentalID = rs.getInt("rentalID");
                LocalDate rentalDate = rs.getDate("rentalDate").toLocalDate();
                int users_UserID = rs.getInt("users_UserID");
                int devices_inventoryNumber = rs.getInt("devices_inventoryNumber");
                int administrators_AdminID = rs.getInt("administrators_adminID");
                String manufacturer = rs.getString("manufacturer");
                String productname =rs.getString("productname");
                
                Rentallist ren = new Rentallist(productname, manufacturer,
                                                rentalDate, devices_inventoryNumber,
                                                administrators_AdminID, users_UserID);
                ren.setRentalID(rentalID);
                filteredRentallist.add(ren);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try { 
                    stmt.close();
                } catch (SQLException ex){
                    System.out.println(ex);
                }
            }  
        } 
        return filteredRentallist;
    }
    
    
    
    /**
     *
     * @param str String to proof 
     * @return true if String is numeric only
     */
    public static boolean isNumeric(String str) { 
        try {  
            Integer.parseInt(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
    }
    
/**
 *
 * @author linda
 */
    public List<Rentallist> displayRentallist() {
       
     ArrayList <Rentallist> Rentallist = new ArrayList <>();
        
        String query = "SELECT rentalID, rentalDate, administrators_adminID "
                + ",rentals.users_UserID, rentals.devices_inventoryNumber, "
                + "manufacturer, productname "
                + "from rentals "
                + "join devices "
                + "on devices_inventoryNumber = devices.inventoryNumber "
                + "where returnDate IS NULL;";
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                
                int rentalID = rs.getInt("rentalID");
                LocalDate rentalDate = rs.getDate("rentalDate").toLocalDate();
                int users_UserID = rs.getInt("users_UserID");
                int devices_inventoryNumber = rs.getInt("devices_inventoryNumber");
                int administrators_AdminID = rs.getInt("administrators_adminID");
                String manufacturer = rs.getString("manufacturer");
                String productname =rs.getString("productname");
                
                Rentallist ren = new Rentallist(productname, manufacturer,
                                                rentalDate, devices_inventoryNumber,
                                                administrators_AdminID, users_UserID);
                ren.setRentalID(rentalID);
                
                Rentallist.add(ren);
            }
        }catch (SQLException ex) {
            System.out.println(ex);
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        return Rentallist;
    }
}

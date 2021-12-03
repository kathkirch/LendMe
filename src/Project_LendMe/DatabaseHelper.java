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
    
    public List <Devices> getDevices(){
    
        List <Devices> devicesList = new ArrayList <>();
        
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM devices WHERE status =0";
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
    
    /*
    public List <Rentals> readRentals () {
        List <Rentals> allRentals = new ArrayList <>();
        String query = "SELECT * FROM rentals WHERE returnDate IS NOT NULL";
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                for (Rentals rental : allRentals){
                    rental.setRentalID(rs.getInt())
                }
            }
            
        
        }catch (SQLException ex) {
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
    
        
    }
*/
    
    public static List <Devices> getItemByProductName(String productName){
        
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
    
    public static List <Devices> getItemByManufacturer(String manufacturer){
        
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
    
    public static List <Devices> getItemByInvNumber (String invNumber){
        
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
    
    public static List <String> getUsersID(){
        
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
    
    public static List <String> getUserYears(){
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
    
    public static List <String> getAdminIDs() {
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
         
    public static String getAdminNameByID (String adminID){
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
    
    public static Users checkUserID(String userID){
        
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
    
    public static List <Object> makeListForCategory (List <Devices> list, 
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
        
    public static void insertNewUser(Users user){
        
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
    
    public static boolean isUserNew(String userID){
        
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
    
    public static void insertNewRental_DB(Rentals rental) {
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
        
    public static void updateDeviceStatus(int device_inventoryNumber, int userID) {
        Statement stmt = null;
        ResultSet rs = null;
        
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
    
    public static boolean isNumeric(String str) { 
        try {  
            Integer.parseInt(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
    }
    
}

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
import java.time.LocalDate;
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
    
    public static List <Devices> getItems(){
    
        List <Devices> devicesList = new ArrayList <>();
        
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM devices";
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                String productName = rs.getString("productName");
                String manufacturer = rs.getString("manufacturer");
                int inventoryNumber = rs.getInt("inventoryNumber");
                int userID = rs.getInt("users_userID");
                
                Devices dev = new Devices();
                dev.setProductName(productName);
                dev.setManufacturer(manufacturer);
                dev.setInventoryNumber(inventoryNumber);
                dev.setUsers_userID(userID);
                
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
    
    public static List <Devices> getItemByProductName(String productName){
        
        List <Devices> items = new ArrayList <>();
        String query = "SELECT manufacturer, inventoryNumber, users_userID"
                        + " FROM devices WHERE productName=" + "'" + productName
                        + "';";
                
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                String manufacturer = rs.getString("manufacturer");
                int inventoryNumber = rs.getInt("inventoryNumber");
                int userID = rs.getInt("users_userID");
                
                Devices d = new Devices();
                d.setManufacturer(manufacturer);
                d.setInventoryNumber(inventoryNumber);
                d.setUsers_userID(userID);
                
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
        String query = "SELECT productName, inventoryNumber, users_userID"
                        + " FROM devices WHERE manufacturer=" + "'" + manufacturer
                        + "';";
                
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                String productName = rs.getString("productName");
                int inventoryNumber = rs.getInt("inventoryNumber");
                int userID = rs.getInt("users_userID");
                
                Devices d = new Devices();
                d.setProductName(productName);
                d.setInventoryNumber(inventoryNumber);
                d.setUsers_userID(userID);
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
        String query = "SELECT productName, manufacturer, users_userID"
                        + " FROM devices WHERE inventoryNumber=" + "'" 
                        + Integer.parseInt(invNumber)
                        + "';";
                
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                String productName = rs.getString("productName");
                String manufacturer = rs.getString("manufacturer");
                int userID = rs.getInt("users_userID");
                
                Devices d = new Devices();
                d.setProductName(productName);
                d.setManufacturer(manufacturer);
                d.setUsers_userID(userID);
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
    
     public static List <Devices> getItemByUserID(String UserID){
        
        List <Devices> items = new ArrayList <>();
        String query = "SELECT productName, manufacturer, inventoryNumber"
                        + " FROM devices WHERE users_userID=" + "'" 
                        + Integer.parseInt(UserID)
                        + "';";
                
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            while (rs.next()){
                String productName = rs.getString("productName");
                String manufacturer = rs.getString("manufacturer");
                int inventoryNumber = rs.getInt("inventoryNumber");
                
                
                Devices d = new Devices();
                d.setProductName(productName);
                d.setInventoryNumber(inventoryNumber);
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
    
    public static List <Object> getItemsFromList (List <Devices> list, 
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
        
    
    // idee, spaeter date, userid usw der methode uebergeben damit rental erzeugen
    // so weiterarbeiten
    public static void initNewRental_DB() {
        
        // spaeter durch Eingabe aus GUI ersetzen!
        Rentals rentals = new Rentals(LocalDate.now(), 11111, 123, 121212);
        
        Statement stmt = null;
        try{ 
            stmt = con.createStatement();
            String string = "insert into rentals (rentalDate, "
                            +"devices_inventoryNumber, administrators_adminID, "
                            +"users_userID) values ('" + LocalDate.now() 
                            +"', '" + rentals.getDevice_inventoryNumber() 
                            + "', '" + rentals.getAdministrators_AdminID()
                            + "', '" + rentals.getUsers_UserID()+ "');";
            stmt.executeUpdate(string);
            updateDeviceStatus(rentals.getDevice_inventoryNumber());
            
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
    
    
        
    public static void updateDeviceStatus(int device_inventoryNumber) {
        Statement stmt = null;
        ResultSet rs = null;
        
        System.out.println("hello hello");
        
        try {
            stmt = con.createStatement();
            String table = "devices";
            rs = stmt.executeQuery("SELECT * FROM " + table + 
                    " WHERE inventoryNumber= '" + device_inventoryNumber + "'");
            
            while(rs.next()){
                String s = "UPDATE " + table + " SET status = ? "
                        + "WHERE inventoryNumber= ?";
                PreparedStatement prepStat = con.prepareStatement(s);
                prepStat.setInt(1, 1);
                prepStat.setInt(2, device_inventoryNumber);
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
    
}

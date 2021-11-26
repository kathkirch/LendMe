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

/**
 *
 * @author Katharina
 */
public class DatabaseHelper {
    
    private static Connection con;
    
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

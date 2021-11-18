/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import Inventory.InsertNewInventory;

/**
 *
 * @author Katharina
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
              
        //Create InsertNewInventory Object using default constructor
        InsertNewInventory insertNew = new InsertNewInventory();
        
        //establish DB connection
        DatabaseHelper.connectDB();
        
        //create Statement to insert Data
        DatabaseHelper.insertInventory(insertNew.newInventory());
        
        //close DB connection
       DatabaseHelper.closeConnection();
        
    }

}

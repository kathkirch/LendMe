/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Katharina
 */
public class MainClass {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {  
        
        // TODO code application logic here
        
        DatabaseHelper.connectDB();
        //DatabaseHelper.initNewRental_DB();
        
        Runnable runnable = new GUI();
        Thread thread = new Thread(runnable);
        thread.start();
        
    }
}

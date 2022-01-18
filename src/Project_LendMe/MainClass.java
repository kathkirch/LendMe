/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

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
        
        DatabaseHelper.connectDB();
        
        SwingUtilities.invokeLater(new Runnable() {
        @Override
            
            public void run() {
                GUI g = new GUI();
                g.setVisible(true); 
            }
        });
    }
}

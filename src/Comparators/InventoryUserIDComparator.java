/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Comparators;

import Project_LendMe.Devices;
import java.util.Comparator;

/**
 *
 * @author bstra
 */
public class InventoryUserIDComparator implements Comparator<Devices> {

    @Override
    public int compare(Devices o1, Devices o2) {
        
        long val = o1.getUsers_userID() - o2.getUsers_userID();
        
        int c = (int) val;
        
        return c;
    }
    
}

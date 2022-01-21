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
public class InventoryNumberComparator implements Comparator<Devices>{

    @Override
    public int compare(Devices o1, Devices o2) {
        
        //liefert sonst falsche sortierung falls wirklich ein long verglichen wird
        long i = o1.getInventoryNumber();
        long j = o2.getInventoryNumber();
        int c = Long.compare(i, j);
        
        return c;
        
        
//        long val = o1.getInventoryNumber() - o2.getInventoryNumber();
//        
//        int c = (int) val;
//        
//        return c;
    }
    
    
    
}

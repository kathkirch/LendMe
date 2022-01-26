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
public class InventoryProductnameComparator implements Comparator<Devices> {

    @Override
    public int compare(Devices o1, Devices o2) {
        return o1.getProductName().compareToIgnoreCase(o2.getProductName());
    }
    
}

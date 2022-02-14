/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparators;

import Project_LendMe.RentalList;
import java.util.Comparator;

/**
 * Comparator-Class used for sorting 
 * methods called in rental-list-table 
 * 
 * @author Katharina
 */
public class RentallistProNameComparator implements Comparator <RentalList> {

    @Override
    public int compare(RentalList r1, RentalList r2) {
        int c = r1.getProductName().compareTo(r2.getProductName());
        return c;
    }

    @Override
    public Comparator<RentalList> reversed() {
        return Comparator.super.reversed(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparators;

import Project_LendMe.RentalList;
import java.util.Comparator;

/**
 *
 * @author Katharina
 */
public class RentallistManuNameComparator implements Comparator <RentalList> {

    @Override
    public int compare(RentalList r1, RentalList r2) {
       int c = r1.getManufacturer().compareTo(r2.getManufacturer());
       return c;
    }

    @Override
    public Comparator<RentalList> reversed() {
        return Comparator.super.reversed(); 
    }
    
    
    
    
}

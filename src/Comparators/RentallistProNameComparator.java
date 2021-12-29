/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparators;

import Project_LendMe.Rentallist;
import java.util.Comparator;

/**
 *
 * @author Katharina
 */
public class RentallistProNameComparator implements Comparator <Rentallist> {

    @Override
    public int compare(Rentallist r1, Rentallist r2) {
        int c = r1.getProductName().compareTo(r2.getProductName());
        return c;
    }

    @Override
    public Comparator<Rentallist> reversed() {
        return Comparator.super.reversed(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

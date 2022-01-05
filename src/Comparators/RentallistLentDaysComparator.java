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
public class RentallistLentDaysComparator implements Comparator <Rentallist> {

    @Override
    public int compare(Rentallist r1, Rentallist r2) {
        int c = r1.getLentDays() - r2.getLentDays();
        return c;
    }

    @Override
    public Comparator<Rentallist> reversed() {
        return Comparator.super.reversed(); 
    }
    
}

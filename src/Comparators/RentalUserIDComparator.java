/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparators;

import Project_LendMe.Rentals;
import java.util.Comparator;

/**
 *
 * @author Katharina
 */
public class RentalUserIDComparator implements Comparator<Rentals>{
    @Override
    public int compare(Rentals r1, Rentals r2) {
        long i = r1.getUsers_UserID();
        long j = r2.getUsers_UserID();
        
        int c = Long.compare(i, j);
        
        return c;
    }

    @Override
    public Comparator<Rentals> reversed() {
        return Comparator.super.reversed(); 
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.time.LocalDate;

/**
 * Class to build a Rentals object, has the same properties as rentals
 * table in the database; variables are accessible via getter and setter methods
 * 
 * @author Katharina 
 */

public class Rentals {
    
    private int rentalID;
    private LocalDate rentalDate;
    private LocalDate returnDate; 
    private long device_inventoryNumber;
    private int administrators_AdminID;
    private long users_UserID;

    
    public Rentals(LocalDate rentalDate, long device_inventoryNumber, 
                        int administrators_AdminID, long users_UserID) {
        this.rentalDate = rentalDate;
        this.device_inventoryNumber = device_inventoryNumber;
        this.administrators_AdminID = administrators_AdminID;
        this.users_UserID = users_UserID;
    }

   
    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

   
    public LocalDate getRentalDate() {
        return rentalDate;
    }

   
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    
    public LocalDate getReturnDate() {
        return returnDate;
    }

    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

   
    public long getDevice_inventoryNumber() {
        return device_inventoryNumber;
    }

    
    public void setDevice_inventoryNumber(long device_inventoryNumber) {
        this.device_inventoryNumber = device_inventoryNumber;
    }

    public int getAdministrators_AdminID() {
        return administrators_AdminID;
    }

   
    public void setAdministrators_AdminID(int administrators_AdminID) {
        this.administrators_AdminID = administrators_AdminID;
    }

   
    public long getUsers_UserID() {
        return users_UserID;
    }

   
    public void setUsers_UserID(long users_UserID) {
        this.users_UserID = users_UserID;
    }
}

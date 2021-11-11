/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.time.LocalDate;

/**
 *
 * @author Katharina 
 * 
 */

public class Rentals {
    
    private int rentalID;
    private LocalDate rentalDate;
    private LocalDate returnDate; 
    private int device_inventoryNumber;
    private int administrators_AdminID;
    private int users_UserID;

    
    public Rentals(LocalDate rentalDate, int device_inventoryNumber, 
                        int administrators_AdminID, int users_UserID) {
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.device_inventoryNumber = device_inventoryNumber;
        this.administrators_AdminID = administrators_AdminID;
        this.users_UserID = users_UserID;
    }

    /**
     *
     * @return
     */
    public int getRentalID() {
        return rentalID;
    }

    /**
     *
     * @param rentalID 
     */
    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    /**
     *
     * @return
     */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /**
     *
     * @param rentalDate
     */
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     *
     * @return
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     *
     * @param returnDate
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     *
     * @return
     */
    public int getDevice_inventoryNumber() {
        return device_inventoryNumber;
    }

    /**
     *
     * @param device_inventoryNumber
     */
    public void setDevice_inventoryNumber(int device_inventoryNumber) {
        this.device_inventoryNumber = device_inventoryNumber;
    }

    /**
     *
     * @return
     */
    public int getAdministrators_AdminID() {
        return administrators_AdminID;
    }

    /**
     *
     * @param administrators_AdminID
     */
    public void setAdministrators_AdminID(int administrators_AdminID) {
        this.administrators_AdminID = administrators_AdminID;
    }

    /**
     *
     * @return
     */
    public int getUsers_UserID() {
        return users_UserID;
    }

    /**
     *
     * @param users_UserID
     */
    public void setUsers_UserID(int users_UserID) {
        this.users_UserID = users_UserID;
    }
    
   
}

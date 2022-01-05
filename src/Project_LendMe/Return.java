/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import java.time.LocalDate;

/**
 *
 * @author linda
 */
class Return {
    
    private LocalDate returnDate;
    private int administrators_adminID;
    private String notes;
    private String productName;
    private String manufacturer;
    private int inventoryNumber;
    private int users_userID;

    public Return(LocalDate returnDate, int administrators_adminID, String notes, String productName, String manufacturer, int inventoryNumber, int users_userID) {
        this.returnDate = returnDate;
        this.administrators_adminID = administrators_adminID;
        this.notes = notes;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.inventoryNumber = inventoryNumber;
        this.users_userID = users_userID;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getAdministrators_adminID() {
        return administrators_adminID;
    }

    public void setAdministrators_adminID(int administrators_adminID) {
        this.administrators_adminID = administrators_adminID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public int getUsers_userID() {
        return users_userID;
    }

    public void setUsers_userID(int users_userID) {
        this.users_userID = users_userID;
    }
    
    @Override
    public String toString() {
        
        String string = returnDate + " | " + administrators_adminID + " | " + inventoryNumber 
                + " | " +  users_userID;
        
        return string; 
    }
    
    
    
    

}

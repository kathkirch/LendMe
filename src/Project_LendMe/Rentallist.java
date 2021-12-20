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
public class Rentallist {
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    private LocalDate rentalDate;
    private String productName;
    private int devices_inventoryNumber;
    private int users_UserID;
    private String manufacturer;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }
        private int rentalID;

    public String getProductName() {
        return productName;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }


    public int getDevices_inventoryNumber() {
        return devices_inventoryNumber;
    }

    public void setDevices_inventoryNumber(int devices_inventoryNumber) {
        this.devices_inventoryNumber = devices_inventoryNumber;
    }

    public int getUsers_UserID() {
        return users_UserID;
    }

    public void setUsers_UserID(int users_UserID) {
        this.users_UserID = users_UserID;
    }
      
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    
    
}

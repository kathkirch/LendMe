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
 */
public class Devices implements Rentable {
    
    private long inventoryNumber;
    private String manufacturer;
    private String productName;
    private String notes;
    private String location;
    private long imei;
    private long users_userID;
    private double aquisitionValue;
    private LocalDate aquistionDate;
    private int status;
    

    public Devices() {
    
    }

    public int getStatus() {
        return status;
    }
    
    public long getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(long inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getImei() {
        return imei;
    }

    public void setImei(long imei) {
        this.imei = imei;
    }

    public long getUsers_userID() {
        return users_userID;
    }

    public void setUsers_userID(long users_userID) {
        this.users_userID = users_userID;
    }

    public double getAquisitionValue() {
        return aquisitionValue;
    }

    public void setAquisitionValue(double aquisitionValue) {
        this.aquisitionValue = aquisitionValue;
    }

    public LocalDate getAquistionDate() {
        return aquistionDate;
    }

    public void setAquistionDate(LocalDate aquistionDate) {
        this.aquistionDate = aquistionDate;
    }
    
    
}   

interface Rentable {

    static final int lent = 1;

    static final int not_lent = 0;

  
}
    



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
public class Devices {
    
    private long inventoryNumber;
    private String manufacturer;
    private String productName;
    private String notes;
    private String location;
    private int imei;
    private int users_userID;
    private double aquisitionValue;
    private LocalDate aquistionDate;
    

    public Devices() {
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

    public int getImei() {
        return imei;
    }

    public void setImei(int imei) {
        this.imei = imei;
    }

    public int getUsers_userID() {
        return users_userID;
    }

    public void setUsers_userID(int users_userID) {
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

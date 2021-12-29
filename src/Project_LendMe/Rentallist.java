/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project_LendMe;

import java.time.LocalDate;

/**
 *
 * @author linda, katharina
 */
public class Rentallist extends Rentals{
    
    private String productName;
    private String manufacturer;
    private int lentDays;
    
     Rentallist(String productName, String manufacturer, 
                LocalDate rentalDate, int device_inventoryNumber, 
                int administrators_AdminID, int users_UserID) {
       super(rentalDate, device_inventoryNumber, administrators_AdminID, users_UserID);
       this.productName = productName;
       this.manufacturer = manufacturer;
       this.lentDays = countDays(rentalDate);
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
    
    /**
     *
     * @param rentalDate as a LocalDate object needed to get difference
     * between rentalDate and currentDate
     * @return difference between rentalDate and currentDate as int
     */
    public int countDays (LocalDate rentalDate) {
        
        LocalDate currentDate = LocalDate.now();
        int curYearDay = currentDate.getDayOfYear(); 
        int rentYearDay = rentalDate.getDayOfYear();
        int lentDays = 0;
        
        lentDays = curYearDay - rentYearDay;
        
        if (lentDays < 0) {
            lentDays = lentDays * (-1);
        }
        
        return lentDays;
    }

    public int getLentDays() {
        return lentDays;
    }

    public void setLentDays(int lentDays) {
        this.lentDays = lentDays;
    }
    
    
}

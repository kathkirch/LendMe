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
    
    public Rentallist(String productName, String manufacturer, 
                LocalDate rentalDate, long device_inventoryNumber, 
                int administrators_AdminID, long users_UserID) {
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
     * calls the isLeapYear method to proof if currentYear is Leap year 
     * to get the difference
     * @return difference between rentalDate and currentDate as int
     */
    public int countDays (LocalDate rentalDate) {
        Validator val = new Validator();
        
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int curYearDay = currentDate.getDayOfYear(); 
        int rentYearDay = rentalDate.getDayOfYear();
        int lentDays = 0;
        
        if(curYearDay > rentYearDay){
            lentDays = curYearDay - rentYearDay;
        } else if (curYearDay < rentYearDay) {
            if (val.isLeapYear(year)){
                curYearDay = curYearDay + 366;
                lentDays = curYearDay - rentYearDay;
            } else if (!val.isLeapYear(year)){
                curYearDay = curYearDay + 365;
                lentDays = curYearDay - rentYearDay;
            }
        } else if (curYearDay == rentYearDay) {
            lentDays = 0;
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

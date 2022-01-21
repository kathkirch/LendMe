/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.time.LocalDate;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * Class with validation methods
 * 
 * @author Katharina
 */
public class Validator {
    
    /**
     * method to check if given String is only alpha
     * 
     * @param name String to proof
     * @return true if String only contains letters
     */
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z-]+");
    }
    
    
    /**
     * method to check if given String is only numeric or not
     * 
     * @param str String to proof 
     * @return true if String is numeric only
     */
    public boolean isNumeric(String str) { 
        try {  
            Long.parseLong(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
    }
    
    /**
     * method to check if given string is alphanumeric
     * @param str String to check
     * @return true if String is alphanumeric numeric or alpha only
     * return false if String has special characters
     */
    public boolean isAlphaNumeric (String str){
        return str != null && str.matches("^[a-zA-Z0-9 ]*$");
    }
    
    /**
     * method to check if String email is valid for a email
     * 
     * @param email String to proof
     * @return true if String uses a valid email pattern
     */
    public boolean isEmail (String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    
    /**
     *method to check if String phone is a valid value for a phone number
     * 
     * @param phone String to proof
     * @return true if String uses given phone number pattern 
     */
    public boolean isPhone(String phone) {
        return phone.matches("[0-9/]+");
    }
    
    /**
     * method to check if String year is a valid 'year-value' 
     * letter g, e, and b for GEB and letters e, h and t
     * @param year String to proof 
     * @return true if given String matches year pattern
     */
    public boolean isYear (String year) {
        return year.matches("[GEBgebEHTeht0-9 ]+");
    }
    
    /**
     * method to check if given year is leap year 
     * 
     * @param year as int to proof if given year is a leap year
     * @return true if given year is a leap year, false if given year is not a 
     * leap year
     */
    public boolean isLeapYear (int year){
        boolean isLeap = false;
        
        if (((year % 4 == 0) && (year % 100!= 0)) || (year%400 == 0)){
            isLeap = true;
        }
        else{ 
            isLeap = false;
        }
        return isLeap;
    }
    
    /**
     *  method to check if returnDate is valid
     *  first check if returnDate is after currentDate
     *  then check if returnDate is before rentalDate
     * 
     *  @param returnDate as LocalDate 
     *  @param rentalDate as LocalDate
     *  @return true if returnDate is not after currentDate and if returnDate 
     *      is not before rentalDate
     */
    public boolean validReturnDate (LocalDate returnDate, LocalDate rentalDate){
        
        boolean valid = true;
        
        LocalDate currentDate = LocalDate.now();
        
        boolean futureDate = returnDate.isAfter(currentDate);
        boolean notMatchingDate = returnDate.isBefore(rentalDate);
        
        if ( futureDate ) {
            valid = false;
            JOptionPane.showMessageDialog(null, 
                    "Rückgabedatum darf nicht in der Zukunft liegen!");
        } else if ( notMatchingDate) {
            valid = false;
            JOptionPane.showMessageDialog(null, 
                    "Rückgabedatum darf nicht vor dem Verleihdatum sein!");
        }
        
        return valid;
    }
}

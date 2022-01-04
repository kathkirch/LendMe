/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

import java.util.regex.Pattern;

/**
 *
 * @author Katharina
 */
public class Validator {
    
    /**
     *
     * @param name String to proof
     * @return true if String only contains letters
     */
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z-]+");
    }
    
    
    /**
     * @param str String to proof 
     * @return true if String is numeric only
     */
    public boolean isNumeric(String str) { 
        try {  
            Integer.parseInt(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
    }
    
    /**
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
     *
     * @param phone String to proof
     * @return true if String uses given phone number pattern 
     */
    public boolean isPhone(String phone) {
        return phone.matches("[0-9/]+");
    }
    
    /**
     *
     * @param year String to proof 
     * @return true if given String matches year pattern
     */
    public boolean isYear (String year) {
        return year.matches("[GEB0-9 ]+");
    }
    
    /**
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
}

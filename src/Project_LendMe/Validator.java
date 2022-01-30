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
     * method to check if given String for name is valid value for namefield
     * only allows letters, umlaut and - and empty space
     * minimum length is 2, maximum length is 20
     * @param name as String to check
     * @return true if given String matches the pattern
     */
    public boolean isName (String name) {
         boolean validName = false;
        
        String nameRegex = "[A-Za-z"
                            + "\\u00c4\\u00e4"      
                            + "\\u00d6\\u00f6"
                            + "\\u00dc\\u00fc"
                            + "\\u00df"
                            + "\\- ]{2,20}";
        Pattern pat = Pattern.compile(nameRegex);
        
        if (name != null && pat.matcher(name).matches()){
            validName = true;
        } else if (name == null){
             JOptionPane.showMessageDialog(null, "Falsche Eingabe bei "
                    + "\"Name\"! \nDarf nicht leer sein");
            validName = false;
        } else if (pat.matcher(name).matches() == false){
            JOptionPane.showMessageDialog(null, "Falsche Eingabe bei "
                    + "\"Name\"! \n Bitte validen Namen eingeben \n"
                    + "Leerzeichen und - erlaubt; maximal 20 Zeichen");
            validName = false;
        }
        return validName;
    }
    
    /**
     * method to check if given String userID is valid value for a userID
     * allows numeric only, minimum length is 1, maximum length is 11 
     * @param userID as String to check 
     * @return true if given String matches the pattern
     */
    public boolean isUserID (String userID) {
        boolean validUserID = false;
        
        String userIDRegex = "[0-9]{1,11}";
        Pattern pat = Pattern.compile(userIDRegex);
        
        
        if (userID != null  && pat.matcher(userID).matches()) {
            validUserID = true;
        } else if (userID == null || userID.isBlank()){
            JOptionPane.showMessageDialog(null, "Falsche Eingabe bei "
                    + "\"UserID\"! \nDarf nicht leer sein");
            validUserID = false;
        } else if (pat.matcher(userID).matches() == false){
            JOptionPane.showMessageDialog(null, "Falsche Eingabe bei "
                    + "\"UserID\"! \nID ist numerisch und maximal 11 Zeichen "
                    + "lang");
            validUserID = false;
        }
        return validUserID;
    }
   
    
    /**
     * method to check if String email is valid for a email
     * email must contain a @ and has to end with letters
     * @param email String to proof
     * @return true if String matches the pattern 
     */
    public boolean isEmail (String email){
        
        boolean validEmail = false;
                
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                   "[a-zA-Z0-9_+&*-]+)*@" +
                   "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                   "A-Z]{2,7}$";
        
        Pattern pat = Pattern.compile(emailRegex);
        
        if (email != null && pat.matcher(email).matches()){
            validEmail = true;
            
        } else if (email == null) {
            validEmail = false;
             JOptionPane.showMessageDialog(null, "Falsche Eingabe bei "
                    + "\"Email\"! \nDarf nicht leer sein");
             validEmail = false;
             
        } else if (pat.matcher(email).matches() == false){
            JOptionPane.showMessageDialog(null, "Falsche Eingabe bei "
                    + "\"Email\"! \n Bitte eine valide Email-Adresse angeben!");
            validEmail = false;
            
        } 
        else if (email.length() > 45){
            JOptionPane.showMessageDialog(null, "Falsche Eingabe bei "
                    + "\"Email\"! \n E-Mail ist zu lang");
            validEmail = false;
        }
        return validEmail;
    }
    
    /**
     * method to check if String phone is a valid value for a phone number
     * phone must start with + or 0, allowed length is 20 
     * allowed special signs are - / and empty space
     * @param phone String to proof
     * @return true if String phone matches the pattern  
     */
    public boolean isPhone(String phone) {
        
        boolean validPhone = false;
        String phoneRegex = ("^(\\+|0)[1-9 \\-\\/][0-9 \\-\\/]{7,20}$");
        
        Pattern pat = Pattern.compile(phoneRegex);
        
        if (phone != null && pat.matcher(phone).matches() && phone.length() < 21){
            validPhone = true;
        } else if (phone == null){
            JOptionPane.showMessageDialog(null, "Falsche Eingabe bei "
                    + "\"Telefon\"! \nDarf nicht leer sein");
            validPhone = false;
        } else if (pat.matcher(phone).matches() == false) {
            JOptionPane.showMessageDialog(null, "Falsche Eingabe bei "
                    + "\"Telefon\"! \n"
                    + "Eingabe muss mit + oder 0 beginnen"
                    + " Erlaubte Trennzeichen - , / oder Leerzeichen");
            validPhone = false;
        } else if (phone.length() > 20){
            JOptionPane.showMessageDialog(null, "Falsche Eingabe bei "
                    + "\"Telefon\"! \nNummer zu lange (Max. 20 Zeichen)");
            validPhone = false;
        }
        return validPhone;
    }
    
    /**
     * method to check if String year is a valid 'year-value' 
     * only matches if first 3 characters are alpha, followed by 2 digits
     * which represents the year letters can be separated by empty space (optional)
     * @param year String to proof 
     * @return true if given String matches year pattern
     */
    public boolean isYear (String year) {
        boolean validyear = false;
        
        String yearRegex = "[A-Za-z]{3}\\s?[0-9]{2}"; 
        Pattern pat = Pattern.compile(yearRegex);
        
        if (year != null && pat.matcher(year).matches() && year.length()<11){
             validyear = true;
        } else if (year == null) {
            JOptionPane.showMessageDialog(null, 
                    "Falsche Eingabe bei \"Jahr\"! " +
                            "Darf nicht leer sein");
            validyear = false;
        } else if (pat.matcher(year).matches() == false){    
            JOptionPane.showMessageDialog(null, 
                    "Falsche Eingabe bei \"Jahr\" "
                            + "Jahr besteht aus 3 Buchstaben gefolgt von "
                            + "2 Zahlen für die Jahreszahl");
            validyear = false;
        }                
        return validyear;    
        
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
    
    /**
     * method to check if selected rentalDate is a valid date
     * checks if rentalDate is after currentDate and if yes returns false
     * @param rentalDate as LocalDate - param to check
     * @return true if rentalDate is not after currentDate
     */
    public boolean validRentalDate (LocalDate rentalDate){

       boolean valid = true;

       LocalDate currentDate = LocalDate.now();

       boolean futureDate = rentalDate.isAfter(currentDate);


       if ( futureDate ) {
           valid = false;
           JOptionPane.showMessageDialog(null, 
                   "Verleihdatum darf nicht in der Zukunft liegen!");
       } 
       return valid;
    }
    
    /**
     * method to check if String notes is valid value for notes
     * allows only letters and umlaute does not allow special signs
     * @param notes as String to check this string
     * @return true if given string matches the pattern for notes
     */
    public boolean validNotes (String notes){
        boolean valide = false;
        
        String notesRegex = "^[a-zA-Z0-9 "
                                + "\\u00c4\\u00e4"
                                + "\\u00d6\\u00f6"
                                + "\\u00dc\\u00fc"
                                + "\\u00df"
                                + "\\|\\-\\:\\(\\)"
                                + "\\,\\;\\.\\?\n ]*$";
        
        Pattern pat = Pattern.compile(notesRegex);

        if (notes != null && pat.matcher(notes).matches()){
            valide = true;
        } else if ( !pat.matcher(notes).matches() ){
          JOptionPane.showMessageDialog(null, 
                   "Falsche Eingabe bei \"Notizen\" darf keine "
                           + "Sonderzeichen enthalten außer (),.;:|/?");
        }
        return valide;
    }
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

/**
 *  Class for building Users Objects has same properties as users
 *  table in the database; variables are accessible via getter and setter methods 
 * 
 * @author Katharina
 */
public class Users {
    
    private long userID;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPhone;
    private String year;

    public Users(long userID, String userFirstName, String userLastName, String userEmail, String userPhone, String year) {
        this.userID = userID;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.year = year;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Users{" + "userID=" + userID + ", userFirstName=" + userFirstName + ", userLastName=" + userLastName + ", userEmail=" + userEmail + ", userPhone=" + userPhone + ", year=" + year + '}';
    }

   
}

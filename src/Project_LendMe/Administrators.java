/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_LendMe;

/**
 * Class for building Administrators Objects has same properties as administrators
 * table in the database; variables are accessible via getter and setter methods
 * 
 *
 * @author Katharina
 */
public class Administrators {
    
    private int adminID;
    private String adminFirstName;
    private String adminLastName;

    public Administrators() {
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getAdminFirstName() {
        return adminFirstName;
    }

    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }

    public String getAdminLastName() {
        return adminLastName;
    }

    public void setAdminLastName(String adminLastName) {
        this.adminLastName = adminLastName;
    } 
    
}

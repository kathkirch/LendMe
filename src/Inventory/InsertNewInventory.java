package Inventory;

import java.util.Scanner;

/**
 *
 * @author bstra
 */
public class InsertNewInventory {
    
    public String newInventory() {
        
        Scanner userEntry = new Scanner(System.in);
        
        System.out.println("Bitte Inventarnummer eingeben:");
        String inventoryNumber = userEntry.nextLine();
        
        System.out.println("Bitte Hersteller eingeben:");
        String manufacturer = userEntry.nextLine();
        
        System.out.println("Bitte Produktname eingeben:");
        String productname = userEntry.nextLine();
        
        System.out.println("Bitte Notizen eingeben:");
        String notes = userEntry.nextLine();
        
        System.out.println("Bitte Raum eingeben:");
        String location = userEntry.nextLine();
        
        System.out.println("Bitte Verleihstatus eingeben:");
        String status = userEntry.nextLine();
        
        System.out.println("Bitte Imei eingeben:");
        String imei = userEntry.nextLine();
        
        System.out.println("Bitte Administrator eingeben:");
        String admin = userEntry.nextLine();
        
        System.out.println("Bitte Anschaffungswert eingeben:");
        String acquisitionValue = userEntry.nextLine();
        
        System.out.println("Bitte Anschaffungsdatum eingeben:");
        String acquisitionDate = userEntry.nextLine();
        
        String toInsert = ("INSERT INTO devices (inventoryNumber, manufacturer, "
                + "productname, notes, location, status, imei, users_userID, "
                + "acquisitionValue, acquisitionDate) "
                + "VALUES ('" + inventoryNumber + "','"+ manufacturer + "','"
                + productname + "','" + notes + "','" + location + "','" 
                + status + "','" + imei + "','" + admin + "','"
                + acquisitionValue + "','" + acquisitionDate + "')");
        
        System.out.println(toInsert);
        
        return toInsert;
    }
    
}

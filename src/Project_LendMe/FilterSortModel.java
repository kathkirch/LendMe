package Project_LendMe;



/**
 * Interface FilterSortModel 
 * 
 * implemented in Archive-Helper, Inventory-Helper and RentalList-Helper
 * to declare all this table/classes needs the methods 
 * 
 * 
 * @author Katharina
 */
interface FilterSortModel {
    
  
    void filterTable();
    
    void sortTable();
    
    void clearSelection();
    
}

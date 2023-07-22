package com.certifikace.projekt1;

import static com.certifikace.projekt1.RestaurantSettings.fileTables;
import static com.certifikace.projekt1.RestaurantSettings.fileWaiters;

public class RestaurantSaversVoidsForMain {
    public void saveTablesData(TableManager tableManager) {
        try {tableManager.saveDataTablesToFile(fileTables()); System.out.println(); System.out.println();
            System.out.println("Data stolů byla uložena.");
        }
        catch (RestaurantException e) {System.err.println("Chyba při ukládání dat stolů: " + e.getMessage());}
    }

    public void saveWaitersData(WaiterManager waiterManager) {
        try {waiterManager.saveDataWaiterToFile(fileWaiters()); System.out.println(); System.out.println();
            System.out.println("Data číšníků byla uložena.");
        }
        catch (RestaurantException e) {System.err.println("Chyba při ukládání dat číšníků: " + e.getMessage());}
    }

}

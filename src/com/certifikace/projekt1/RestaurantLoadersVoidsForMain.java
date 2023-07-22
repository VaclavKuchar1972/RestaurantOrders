package com.certifikace.projekt1;

import static com.certifikace.projekt1.RestaurantSettings.delimiter;

public class RestaurantLoadersVoidsForMain {

    public void loadTablesData(TableManager tableManager) {
        try {tableManager.loadDataTablesFromFile(RestaurantSettings.fileTables(), delimiter());}
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se načíst data stolů ze souboru: " + RestaurantSettings.fileTables() + " "
                    + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public void loadWaitersData(WaiterManager waiterManager) {
        try {waiterManager.loadDataWaitersFromFile(RestaurantSettings.fileWaiters(), delimiter());}
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se načíst data 493n9k; ze souboru: " + RestaurantSettings.fileWaiters() + " "
                    + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

}

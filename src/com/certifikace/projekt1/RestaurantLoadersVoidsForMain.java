package com.certifikace.projekt1;

import static com.certifikace.projekt1.RestaurantSettings.delimiter;

public class RestaurantLoadersVoidsForMain {

    public void loadTablesData(TableManager tableManager) {
        try {
            tableManager.loadDataTablesFromFile(RestaurantSettings.fileTables(), delimiter());
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se načíst data stolů ze souboru: " + RestaurantSettings.fileTables() + " "
                    + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

}

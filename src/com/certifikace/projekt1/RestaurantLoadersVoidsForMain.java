package com.certifikace.projekt1;

import java.io.FileNotFoundException;

import static com.certifikace.projekt1.RestaurantSettings.delimiter;

public class RestaurantLoadersVoidsForMain {
    String errorMessage = "Nepodařilo se načíst data ze souboru: ";

    public void loadFoodCategoriesData() {FoodCategory.getInstance();/* Načtení kategorií pomocí singletonu*/}
    public void loadTablesData(TableManager tableManager) {
        try {tableManager.loadDataTablesFromFile(RestaurantSettings.fileTables(), delimiter());}
        catch (RestaurantException e) {
            System.err.println(errorMessage + RestaurantSettings.fileTables() + " " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
    public void loadWaitersData(WaiterManager waiterManager) {
        try {waiterManager.loadDataWaitersFromFile(RestaurantSettings.fileWaiters(), delimiter());}
        catch (RestaurantException e) {
            System.err.println(errorMessage + RestaurantSettings.fileWaiters() + " " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
    public void loadDishsData(DishManager dishManager) {
        try {dishManager.loadDataDishsFromFile(RestaurantSettings.fileDishs(), delimiter());}
        catch (RestaurantException e) {
            System.err.println(errorMessage + RestaurantSettings.fileDishs() + " " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
    public void loadActualMenuData(ActualMenuManager actualMenuManager) {
        try {actualMenuManager.loadDataMenuFromFile(RestaurantSettings.fileActualMenu(), delimiter());}
        catch (RestaurantException e) {
            System.err.println(errorMessage + RestaurantSettings.fileActualMenu() + " " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public void loadUnconfirmedItemList(OrderManager orderManager) {
        try {
            orderManager.loadItemOrOrderFromFile("DB-UnconfirmedItems");
        } catch (RestaurantException | FileNotFoundException e) {
            System.err.println(errorMessage + "DB-ItemActualNumber.txt" + " " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

}

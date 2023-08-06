package com.certifikace.projekt1;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestVoidsForMain {

    static RestaurantSaversVoidsForMain saversVoids = new RestaurantSaversVoidsForMain();

    public static void createAndAddNewTable (TableManager tableManager) throws RestaurantException {
        try {tableManager.addTable(new Table(tableManager.getTableList().size() + 1, "SALONEK",
                "A2", 2));}
        catch (RestaurantException e) {System.err.println("Nepodařilo se přidat nový stůl: "
                + e.getLocalizedMessage());}
        saversVoids.saveTablesData(tableManager);
    }

    public static void createAndAddNewWaiter (WaiterManager waiterManager) throws RestaurantException {
        try {
            waiterManager.addWaiter(new Waiter(waiterManager.getWaiterList().size() + 1,
                    "", "Václav", "Kuchař", "",
                    "151237620", "HPP"));
        }
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat nového číšníka: " + e.getLocalizedMessage());
        }
        saversVoids.saveWaitersData(waiterManager);
    }

    public static void createAndAddNewDish(DishManager dishManager) throws RestaurantException {
        FoodCategory mainCategory = FoodCategory.getInstance().valueOf("SPECIALITY");
        int dishNumberOfNextRecomendedCategory = 3;
        List<FoodCategory> nextRecommendedCategories = new ArrayList<>();
        nextRecommendedCategories.add(FoodCategory.getInstance().valueOf("MINUTEMEAL"));
        nextRecommendedCategories.add(FoodCategory.getInstance().valueOf("PORK"));
        nextRecommendedCategories.add(FoodCategory.getInstance().valueOf("GLUTENFREEFOOD"));
        int dishNumberOfNextPhotos = 1;
        List<String> dishNextPhoto = new ArrayList<>();
        dishNextPhoto.add("SP-MM-KatuvSleh-01");
        Dish newDish = new Dish(mainCategory, dishNumberOfNextRecomendedCategory, nextRecommendedCategories,
                "Katův šleh \"Chez Quis à Prague\"", 250,
                "g", new BigDecimal("240"), 15,
                "SP-MM-KatuvSleh-Main", dishNumberOfNextPhotos, dishNextPhoto);
        dishManager.addDish(newDish);
    }

}





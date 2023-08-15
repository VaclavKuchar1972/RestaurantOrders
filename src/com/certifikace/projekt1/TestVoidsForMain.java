package com.certifikace.projekt1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestVoidsForMain {

    static RestaurantSaversVoidsForMain saversVoids = new RestaurantSaversVoidsForMain();


    public static void createAndAddNewTable (TableManager tableManager) throws RestaurantException {
        try {tableManager.addTable(new Table(20, "SALONEK",
                "A2", 2));
            saversVoids.saveTablesData(tableManager);
        }
        catch (RestaurantException e) {System.err.println("Nepodařilo se přidat nový stůl: "
                + e.getLocalizedMessage());}
    }

    public static void removeTableByNumber (TableManager tableManager) throws RestaurantException {
        try {
            tableManager.removeTableByNumber(20);
            saversVoids.saveTablesData(tableManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se odebrat stůl: " + e.getLocalizedMessage());
        }
    }

    public static void createAndAddNewWaiter (WaiterManager waiterManager) throws RestaurantException {
        try {
            waiterManager.addWaiter(new Waiter(25,  "", "Václav",
                    "Kuchař", "",  "151237620",
                    "HPP"));
        }
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat nového číšníka: " + e.getLocalizedMessage());
        }
        saversVoids.saveWaitersData(waiterManager);
    }
    public static void removeWaiterByNumber (WaiterManager waiterManager) throws RestaurantException {
        try {
            waiterManager.removeWaiterByNumber(25);
            saversVoids.saveWaitersData(waiterManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se odebrat číšníka: " + e.getLocalizedMessage());
        }
    }

    public static void createAndAddNewDish(DishManager dishManager) throws RestaurantException {
        try {
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
                    "g", new BigDecimal("240"), 20,
                    "SP-MM-KatuvSleh-Main", dishNumberOfNextPhotos, dishNextPhoto);
            dishManager.addDish(newDish);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat nové jídlo do zásobníku: " + e.getLocalizedMessage());
        }
        saversVoids.saveDishsData(dishManager);
    }

    public static void removeDishByTitleAndQuantity (DishManager dishManager) throws RestaurantException {
        try {
            dishManager.removeDishByTitleAndQuantity("Katův šleh \"Chez Quis à Prague\"", 250);
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se odebrat jídlo do zásobníku: " + e.getLocalizedMessage());
        }
    }

    public static void replaceDishRecomendedMainCategoryByTitleAndQuantity(DishManager dishManager) {
        try {
            FoodCategory newRecomendedMainCategory = FoodCategory.getInstance().getCategoryByName("SPECIALITY");
            dishManager.replaceDishRecomendedMainCategoryByTitleAndQuantity(
                    "Grilovaný hovězí biftek z mladého býčka s pepřovou omáčkou", 300,
                    newRecomendedMainCategory);
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se nahradit dopručenou hlavní kategorii jídla: "
                    + e.getLocalizedMessage());
        }
    }



}





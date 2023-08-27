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
        //saversVoids.saveDishsData(dishManager);
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
    
    public static void addDishNextRecomendedCategoryByTitleAndQuantity(DishManager dishManager) {
        try {
            String dishTitle = "Grilovaný hovězí biftek z mladého býčka s pepřovou omáčkou";
            int dishQuantity = 300;
            String newCategoryName = "SALAD";
            dishManager.addDishNextRecomendedCategoryByTitleAndQuantity(dishTitle, dishQuantity, newCategoryName);
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat doporučenou další kategorii jídla: " + e.getLocalizedMessage());
        }
    }

    public static void removeAddressedDishNextRecomendedCategoryByTitleAndQuantity(DishManager dishManager) {
        try {
            String dishTitle = "Grilovaný hovězí biftek z mladého býčka s pepřovou omáčkou";
            int dishQuantity = 300;
            String categoryNameToRemove = "SALAD";
            dishManager.removeAddressedDishNextRecomendedCategoryByTitleAndQuantity(
                    dishTitle, dishQuantity, categoryNameToRemove
            );
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se odebrat doporučenou další kategorii jídla: " + e.getLocalizedMessage());
        }
    }

    public static void renameDishTitleByTitleAndQuantity(DishManager dishManager) {
        try {
            String dishTitle = "Jahodový zmrzlinový pohár";
            int dishRecomendedQuantity = 200;
            String newDishTitle = "Čerstvé jahody se zmrzlinou a mátovočokoládovou zálivkou";
            dishManager.renameDishTitleByTitleAndQuantity(dishTitle, dishRecomendedQuantity, newDishTitle);
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se změnit název jídla: " + e.getLocalizedMessage());
        }
    }

    public static void addDishSameTitleWithDifferentQuantityAndPrice(DishManager dishManager) {
        String dishTitle = "Houskový knedlík";
        int newDishRecomendedQuantity = 6;
        BigDecimal newRecommendedPrice = new BigDecimal("30");
        try {
            dishManager.addDishSameTitleWithDifferentQuantityAndPrice
                    (dishTitle, newDishRecomendedQuantity, newRecommendedPrice);
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat jídlo se stejným názvem a jiným doporučeným množstvím a cenou: "
                    + e.getLocalizedMessage());
        }
    }

    public static void replaceDishRecommendedQuantityByTitleAndQuantity(DishManager dishManager) {
        String dishTitle = "Katův šleh v moderním kulinářském hávu z vepřové panenky a čerstvé zeleniny";
        int dishRecommendedQuantity = 300;
        int newDishRecommendedQuantity = 250;
        try {
            dishManager.replaceDishRecommendedQuantityByTitleAndQuantity
                    (dishTitle, dishRecommendedQuantity, newDishRecommendedQuantity);
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se změnit doporučené množství jídla: "
                    + e.getLocalizedMessage());
        }
    }

    public static void replaceDishRecommendedPriceByTitleAndQuantity(DishManager dishManager) {
        String dishTitle = "Grilovaný hovězí biftek z mladého býčka s pepřovou omáčkou";
        int dishRecommendedQuantity = 300;
        BigDecimal newRecommendedPrice = new BigDecimal("450");
        try {
            dishManager.replaceDishRecommendedPriceByTitleAndQuantity
                    (dishTitle, dishRecommendedQuantity, newRecommendedPrice);
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se změnit doporučenou cenu jídla: "
                    + e.getLocalizedMessage());
        }
    }

    public static void replaceDishEstimatedPreparationTimeByTitleAndQuantity(DishManager dishManager) {
        String dishTitle = "Grilovaný hovězí biftek z mladého býčka s pepřovou omáčkou";
        int dishRecommendedQuantity = 300;
        int newEstimatedPreparationTime = 20;
        try {
            dishManager.replaceDishEstimatedPreparationTimeByTitleAndQuantity
                    (dishTitle, dishRecommendedQuantity, newEstimatedPreparationTime);
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se změnit předpokládaný čas přípravy jídla: "
                    + e.getLocalizedMessage());
        }
    }

    public static void replaceDishMainPhotoByTitleAndQuantity(DishManager dishManager) {
        String dishTitle = "Čerstvá Zázvorová";
        int dishRecommendedQuantity = 330;
        String newDishMainPhoto = "";
        try {
            dishManager.replaceDishMainPhotoByTitleAndQuantity(dishTitle, dishRecommendedQuantity, newDishMainPhoto);
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se změnit název souboru s hlavní fotografe: "
                    + e.getLocalizedMessage());
        }
    }

    public static void addDishNextPhotoByTitleAndQuantity(DishManager dishManager) {
        String dishTitle = "Čerstvý pstruh na grilu s ledovým salátem";
        int dishRecommendedQuantity = 250;
        String newDishMainPhoto = "MM-GL-MM-GL-PstruhGril-NEW02";
        try {
            dishManager.addDishNextPhotoByTitleAndQuantity(dishTitle, dishRecommendedQuantity, newDishMainPhoto);
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat další název souboru fotografie v dalších fotografiích: "
                    + e.getLocalizedMessage());
        }
    }

    public static void removeAddressedDishNextPhotoByTitleAndQuantity(DishManager dishManager) {
        String dishTitle = "Čerstvý pstruh na grilu s ledovým salátem";
        int dishRecommendedQuantity = 250;
        String photoToRemove = "MM-GL-MM-GL-PstruhGril-NEW02";
        try {
            dishManager.removeAddressedDishNextPhotoByTitleAndQuantity
                    (dishTitle, dishRecommendedQuantity, photoToRemove);
            saversVoids.saveDishsData(dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se odebrat další název souboru fotografie v dalších fotografiích: "
                    + e.getLocalizedMessage());
        }
    }

    public static void testAddFoodToMenu(ActualMenuManager amManager, DishManager dishManager) {
        String dishTitle = "Plzeň 12 světlá čepovaná";
        int dishRecommendedQuantity = 500;
        try {
            amManager.addFoodToMenu(dishTitle, dishRecommendedQuantity, dishManager);
            //saversVoids.saveActualMenuData(amManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat jídlo do amList: " + e.getLocalizedMessage());
        }
    }





}





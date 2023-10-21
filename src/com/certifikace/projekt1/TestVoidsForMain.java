package com.certifikace.projekt1;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestVoidsForMain {

    static RestaurantSaversVoidsForMain saversVoids = new RestaurantSaversVoidsForMain();


    public static void createAndAddNewTable (TableManager tableManager) {
        tableManager.addTable(new Table(20, "SALONEK", "A2", 2));
        saversVoids.saveTablesData(tableManager);
    }
    public static void removeTableByNumber (TableManager tableManager) throws RestaurantException {
        try {
            tableManager.removeTableByNumber(20);
            saversVoids.saveTablesData(tableManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se odebrat stůl: " + e.getLocalizedMessage());
        }
    }

    public static void createAndAddNewWaiter (WaiterManager waiterManager) {
            waiterManager.addWaiter(new Waiter(25,  "", "Václav",
                    "Kuchař", "",  "151237620",
                    "HPP"));
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
        String dishTitle = "Katův šleh v moderním kulinářském hávu z vepřové panenky a čerstvé zeleniny";
        int dishRecommendedQuantity = 300;
        try {
            amManager.addFoodToMenuByTitleAndQuantity(dishTitle, dishRecommendedQuantity, dishManager);
            saversVoids.saveMenuData(amManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat jídlo do amList: " + e.getLocalizedMessage());
        }
    }

    public static void testRemoveFoodFromMenu(ActualMenuManager amManager) {
        String dishTitle = "Katův šleh v moderním kulinářském hávu z vepřové panenky a čerstvé zeleniny";
        int dishRecommendedQuantity = 300;
        try {
            amManager.removeFoodFromMenu(dishTitle, dishRecommendedQuantity);
            saversVoids.saveMenuData(amManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se odebrat jídlo z amList: " + e.getLocalizedMessage());
        }
    }

    public static void testClearAndSave(ActualMenuManager amManager, RestaurantSaversVoidsForMain saversVoids) throws IOException {
        amManager.clearAmList();
        TestVoidsForMain.saversVoids.saveMenuData(amManager);
    }


    public static void testAddItemToUnconfirmedOrdersListByTitleAndQuantity(
            OrderManager orderManager, ActualMenuManager amManager, WaiterManager waiterManager,
            TableManager tableManager) {
        String dishTitle = "Nakládaný hermelín";
        int dishRecommendedQuantity = 150;


        int waiterNumber = 1;
        int tableNumber = 5;
        int unitsNumber = 3;
        String noteForKitchen = "Bez cibule";
        String noteForManagement = "VIP host";
        //OrderCategory orderCategory = OrderCategory.RECEIVED;


        try {
            orderManager.addItemToUnconfirmedOrdersByTitleAndQuantity(dishTitle, dishRecommendedQuantity, amManager,
                    waiterNumber, tableNumber, unitsNumber, noteForKitchen, noteForManagement, waiterManager,
                    tableManager);
            //saversVoids.saveOrdersData(orderManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat položku do nepotvrzených objednávek: " + e.getLocalizedMessage());
        }
    }

    public static void testRemoveItemOfUnconfirmedOrdersByItemNumber(OrderManager orderManager) {
        int itemNumber = 9;
        try {orderManager.removeItemOfUnconfirmedOrdersByItemNumber(itemNumber);} catch (RestaurantException e) {
            System.err.println("Nepodařilo se odebrat položku s číslem " + itemNumber + " z nepotvrzených objednávek: "
                    + e.getLocalizedMessage());
        }
    }

    public static void testRemoveAllItemsOfUnconfirmedOrdersByTable(OrderManager orderManager) {
        int tableNumber = 3;
        try {orderManager.removeAllItemsOfUnconfirmedOrdersByTable(tableNumber);} catch (RestaurantException e) {
            System.err.println("Nepodařilo se odebrat všechny položky ze stolu " + tableNumber + " z nepotvrzených "
                    + "objednávek: " + e.getLocalizedMessage());
        }
    }

    public static void testAddAllItemByTable15ToConfirmedOrders(OrderManager orderManager) {
        int tableNumber = 15;
        try {orderManager.addAllItemByTableToConfirmedOrders(tableNumber);}
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat položky ze stolu " + tableNumber + " do potvrzených objednávek: "
                    + e.getLocalizedMessage());
        }
    }
    public static void testAddAllItemByTable2ToConfirmedOrders(OrderManager orderManager) {
        int tableNumber = 2;
        try {orderManager.addAllItemByTableToConfirmedOrders(tableNumber);} catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat položky ze stolu " + tableNumber + " do potvrzených objednávek: "
                    + e.getLocalizedMessage());
        }
    }

    public static void testPrinterOutputToBar(OrderManager orderManager) {
        List<Order> filteredOrders = orderManager.printerOutputOnBar();
        if (filteredOrders.isEmpty()) {
            System.out.println("Žádné objednávky nebyly nalezeny. Není co tisknout na BAR.");
        } else {
            System.out.println();
            System.out.println("Filtrované objednávky pro výstup na tiskárnu v BARu:");
            filteredOrders.forEach(order -> System.out.println(order));
        }
    }

    public static void testPrinterOutputToKitchen(OrderManager orderManager) {
        List<Order> filteredOrders = orderManager.printerOutputOnKitchen();
        if (filteredOrders.isEmpty()) {
            System.out.println("Žádné objednávky nebyly nalezeny. Není co tisknout do KUCHYNĚ.");
        } else {
            System.out.println();
            System.out.println("Filtrované objednávky pro výstup na tiskárnu v KUCHYNI:");
            filteredOrders.forEach(order -> System.out.println(order));
        }
    }

    public static void testChangeItemStatusHasBeenBroughtToTableByItemNumber2to3(OrderManager orderManager) {
        for (int itemNumber = 2; itemNumber <= 3; itemNumber++) {
            try {orderManager.changeItemStatusHasBeenBroughtToTableByItemNumber(itemNumber);}
            catch (RestaurantException e) {
                System.err.println("Nepodařilo se změnit stav položky č." + itemNumber + " v potvrzených objednávkách: "
                    + e.getLocalizedMessage());
            }
        }
    }

    public static void testChangeItemStatusHasBeenBroughtToTableByItemNumber7to8(OrderManager orderManager) {
        for (int itemNumber = 7; itemNumber <= 8; itemNumber++) {
            try {orderManager.changeItemStatusHasBeenBroughtToTableByItemNumber(itemNumber);}
            catch (RestaurantException e) {
                System.err.println("Nepodařilo se změnit stav položky č." + itemNumber + " v potvrzených objednávkách: "
                        + e.getLocalizedMessage());
            }
        }
    }

    public static void testChangeItemStatusHasBeenBroughtToTableByItemNumber4to6(OrderManager orderManager) {
        for (int itemNumber = 4; itemNumber <= 6; itemNumber++) {
            try {orderManager.changeItemStatusHasBeenBroughtToTableByItemNumber(itemNumber);}
            catch (RestaurantException e) {
                System.err.println("Nepodařilo se změnit stav položky č." + itemNumber + " v potvrzených objednávkách: "
                        + e.getLocalizedMessage());
            }
        }
    }

    public static void testChangeItemStatusHasBeenPaidByItemNumber2to6(OrderManager orderManager) {
        List<Integer> itemNumbersToChange = Arrays.asList(2, 3, 4, 5, 6);
        try {
            orderManager.changeItemStatusHasBeenPaidByItemNumberList(itemNumbersToChange);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se změnit stav některých položek v potvrzených objednávkách: "
                    + e.getLocalizedMessage());
        }
    }

    public static void testChangeItemStatusHasBeenPaidByItemNumber7(OrderManager orderManager) {
        List<Integer> itemNumbersToChange = Arrays.asList(7);
        try {
            orderManager.changeItemStatusHasBeenPaidByItemNumberList(itemNumbersToChange);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se změnit stav některých položek v potvrzených objednávkách: "
                    + e.getLocalizedMessage());
        }
    }

    public static void testChangeTableNumberBySelectedTableAndItemsToNewTable
            (OrderManager orderManager, TableManager tableManager) {
        int oldTableNumber = 15; List<Integer> itemNumbers = Arrays.asList(3, 4); int newTableNumber = 9;
        try {
            orderManager.changeTableNumberBySelectedTablesAndItemsToNewTable
                    (oldTableNumber, itemNumbers, newTableNumber, tableManager);
        } catch (RestaurantException e) {System.err.println("Nepodařilo se změnit čísla stolů: " + e.getMessage());}
    }

    public static void testChangeTableNumberBySelectedTablesAllItemsToNewTable
            (OrderManager orderManager, TableManager tableManager) {
        int oldTableNumber = 15; int newTableNumber = 10;
        try {
            orderManager.changeTableNumberBySelectedTablesAllItemsToNewTable
                    (oldTableNumber, newTableNumber, tableManager);
        } catch (RestaurantException e) {System.err.println("Nepodařilo se změnit čísla stolů: " + e.getMessage());}
    }

    public static void TestGetAverageProcessingTimeInTheSpecifiedTimePeriod(RestaurantManager restaurantManager) {
        LocalDateTime startOfPeriod = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endOfPeriod = LocalDateTime.of(2024, 1, 1, 0, 0);
        Integer averageProcessingTime = restaurantManager.getAverageProcessingTimeInTheSpecifiedTimePeriod
                (startOfPeriod, endOfPeriod);
        System.out.println("Průměrná doba zpracování objednávek od objednání do přinesení na stůl v zadaném časovém "
                + "období je: " + averageProcessingTime + " minut");
    }

}












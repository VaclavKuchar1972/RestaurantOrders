package com.certifikace.projekt1;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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


    public static void testCreateAndAddNewDish1(DishManager dishManager) throws RestaurantException {
        try {
            FoodCategory mainCategory = FoodCategory.getInstance().valueOf("MINUTEMEAL");
            int dishNumberOfNextRecomendedCategory = 1;
            List<FoodCategory> nextRecommendedCategories = new ArrayList<>();
            nextRecommendedCategories.add(FoodCategory.getInstance().valueOf("CHICKEN"));
            int dishNumberOfNextPhotos = 0;
            List<String> dishNextPhoto = new ArrayList<>();
            Dish newDish = new Dish(mainCategory, dishNumberOfNextRecomendedCategory, nextRecommendedCategories,
                    "Kuřecí řízek obalovaný", 150,
                    "g", new BigDecimal("175"), 15,
                    "MM-KureciRizekObalovany-Main", dishNumberOfNextPhotos, dishNextPhoto);
            dishManager.addDish(newDish);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat nové jídlo do zásobníku: " + e.getLocalizedMessage());
        }
    }
    public static void testCreateAndAddNewDish2(DishManager dishManager) throws RestaurantException {
        try {
            FoodCategory mainCategory = FoodCategory.getInstance().valueOf("GARNISH");
            int dishNumberOfNextRecomendedCategory = 1;
            List<FoodCategory> nextRecommendedCategories = new ArrayList<>();
            nextRecommendedCategories.add(FoodCategory.getInstance().valueOf("GLUTENFREEFOOD"));
            int dishNumberOfNextPhotos = 0;
            List<String> dishNextPhoto = new ArrayList<>();
            Dish newDish = new Dish(mainCategory, dishNumberOfNextRecomendedCategory, nextRecommendedCategories,
                    "Hranolky", 150,
                    "g", new BigDecimal("40"), 15,
                    "GN-Hranolky-Main", dishNumberOfNextPhotos, dishNextPhoto);
            dishManager.addDish(newDish);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat nové jídlo do zásobníku: " + e.getLocalizedMessage());
        }
    }
    public static void testCreateAndAddNewDish3(DishManager dishManager) throws RestaurantException {
        try {
            FoodCategory mainCategory = FoodCategory.getInstance().valueOf("SPECIALITY");
            int dishNumberOfNextRecomendedCategory = 3;
            List<FoodCategory> nextRecommendedCategories = new ArrayList<>();
            nextRecommendedCategories.add(FoodCategory.getInstance().valueOf("MINUTEMEAL"));
            nextRecommendedCategories.add(FoodCategory.getInstance().valueOf("FISH"));
            nextRecommendedCategories.add(FoodCategory.getInstance().valueOf("GLUTENFREEFOOD"));
            int dishNumberOfNextPhotos = 1;
            List<String> dishNextPhoto = new ArrayList<>();
            dishNextPhoto.add("SP-MM-PstruhNaVine-01");
            Dish newDish = new Dish(mainCategory, dishNumberOfNextRecomendedCategory, nextRecommendedCategories,
                    "Pstruh na víně", 200,
                    "g", new BigDecimal("350"), 9,
                    "SP-MM-PstruhNaVine-Main", dishNumberOfNextPhotos, dishNextPhoto);
            dishManager.addDish(newDish);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat nové jídlo do zásobníku: " + e.getLocalizedMessage());
        }
    }
    public static void testCreateAndAddNewDish4(DishManager dishManager) throws RestaurantException {
        try {
            FoodCategory mainCategory = FoodCategory.getInstance().valueOf("NONALCOHOLIC");
            int dishNumberOfNextRecomendedCategory = 1;
            List<FoodCategory> nextRecommendedCategories = new ArrayList<>();
            nextRecommendedCategories.add(FoodCategory.getInstance().valueOf("SPARKLING"));
            int dishNumberOfNextPhotos = 0;
            List<String> dishNextPhoto = new ArrayList<>();
            Dish newDish = new Dish(mainCategory, dishNumberOfNextRecomendedCategory, nextRecommendedCategories,
                    "Kofola", 500,
                    "ml", new BigDecimal("35"), 3,
                    "NA-SP-Kofola-Main", dishNumberOfNextPhotos, dishNextPhoto);
            dishManager.addDish(newDish);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat nové jídlo do zásobníku: " + e.getLocalizedMessage());
        }
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


    public static void testAddFoodToMenu1(ActualMenuManager amManager, DishManager dishManager) {
        String dishTitle = "Kuřecí řízek obalovaný";
        int dishRecommendedQuantity = 150;
        try {
            amManager.addFoodToMenuByTitleAndQuantity(dishTitle, dishRecommendedQuantity, dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat jídlo do amList: " + e.getLocalizedMessage());
        }
    }
    public static void testAddFoodToMenu2(ActualMenuManager amManager, DishManager dishManager) {
        String dishTitle = "Hranolky";
        int dishRecommendedQuantity = 150;
        try {
            amManager.addFoodToMenuByTitleAndQuantity(dishTitle, dishRecommendedQuantity, dishManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat jídlo do amList: " + e.getLocalizedMessage());
        }
    }
    public static void testAddFoodToMenu4(ActualMenuManager amManager, DishManager dishManager) {
        String dishTitle = "Kofola";
        int dishRecommendedQuantity = 500;
        try {
            amManager.addFoodToMenuByTitleAndQuantity(dishTitle, dishRecommendedQuantity, dishManager);
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
        try {
            orderManager.addItemToUnconfirmedOrdersByTitleAndQuantity(dishTitle, dishRecommendedQuantity, amManager,
                    waiterNumber, tableNumber, unitsNumber, noteForKitchen, noteForManagement, waiterManager,
                    tableManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat položku do nepotvrzených objednávek: " + e.getLocalizedMessage());
        }
    }


    public static void testAddItemToUnconfirmedOrdersListByTitleAndQuantity1(
            OrderManager orderManager, ActualMenuManager amManager, WaiterManager waiterManager,
            TableManager tableManager) {
        String dishTitle = "Kuřecí řízek obalovaný";
        int dishRecommendedQuantity = 150;
        int waiterNumber = 1;
        int tableNumber = 15;
        int unitsNumber = 2;
        String noteForKitchen = "nepřipálit a nevysušit";
        String noteForManagement = "klientovi se moc líbí výzdoba restaurace";
        try {
            orderManager.addItemToUnconfirmedOrdersByTitleAndQuantity(dishTitle, dishRecommendedQuantity, amManager,
                    waiterNumber, tableNumber, unitsNumber, noteForKitchen, noteForManagement, waiterManager,
                    tableManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat položku do nepotvrzených objednávek: " + e.getLocalizedMessage());
        }
    }
    public static void testAddItemToUnconfirmedOrdersListByTitleAndQuantity2(
            OrderManager orderManager, ActualMenuManager amManager, WaiterManager waiterManager,
            TableManager tableManager) {
        String dishTitle = "Hranolky";
        int dishRecommendedQuantity = 150;
        int waiterNumber = 1;
        int tableNumber = 15;
        int unitsNumber = 2;
        String noteForKitchen = "křupavý a do zlatova";
        String noteForManagement = "klientovi se moc líbí výzdoba restaurace";
        try {
            orderManager.addItemToUnconfirmedOrdersByTitleAndQuantity(dishTitle, dishRecommendedQuantity, amManager,
                    waiterNumber, tableNumber, unitsNumber, noteForKitchen, noteForManagement, waiterManager,
                    tableManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat položku do nepotvrzených objednávek: " + e.getLocalizedMessage());
        }
    }
    public static void testAddItemToUnconfirmedOrdersListByTitleAndQuantity3(
            OrderManager orderManager, ActualMenuManager amManager, WaiterManager waiterManager,
            TableManager tableManager) {
        String dishTitle = "Kofola";
        int dishRecommendedQuantity = 500;
        int waiterNumber = 1;
        int tableNumber = 15;
        int unitsNumber = 2;
        String noteForKitchen = "";
        String noteForManagement = "klientovi se moc líbí výzdoba restaurace";
        try {
            orderManager.addItemToUnconfirmedOrdersByTitleAndQuantity(dishTitle, dishRecommendedQuantity, amManager,
                    waiterNumber, tableNumber, unitsNumber, noteForKitchen, noteForManagement, waiterManager,
                    tableManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat položku do nepotvrzených objednávek: " + e.getLocalizedMessage());
        }
    }
    public static void testAddItemToUnconfirmedOrdersListByTitleAndQuantity4(
            OrderManager orderManager, ActualMenuManager amManager, WaiterManager waiterManager,
            TableManager tableManager) {
        String dishTitle = "Kofola";
        int dishRecommendedQuantity = 500;
        int waiterNumber = 3;
        int tableNumber = 2;
        int unitsNumber = 3;
        String noteForKitchen = "";
        String noteForManagement = "";
        try {
            orderManager.addItemToUnconfirmedOrdersByTitleAndQuantity(dishTitle, dishRecommendedQuantity, amManager,
                    waiterNumber, tableNumber, unitsNumber, noteForKitchen, noteForManagement, waiterManager,
                    tableManager);
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat položku do nepotvrzených objednávek: " + e.getLocalizedMessage());
        }
    }
    public static void testAddItemToUnconfirmedOrdersListByTitleAndQuantityFoodNotExistInActualMenu(
            OrderManager orderManager, ActualMenuManager amManager, WaiterManager waiterManager,
            TableManager tableManager) {
        String dishTitle = "Pečení nadívaní holubi, kteří sami létají do úst :D";
        int dishRecommendedQuantity = 150;
        int waiterNumber = 1;
        int tableNumber = 15;
        int unitsNumber = 2;
        String noteForKitchen = "křupavý a do zlatova";
        String noteForManagement = "klientovi se moc líbí výzdoba restaurace";
        try {
            orderManager.addItemToUnconfirmedOrdersByTitleAndQuantity(dishTitle, dishRecommendedQuantity, amManager,
                    waiterNumber, tableNumber, unitsNumber, noteForKitchen, noteForManagement, waiterManager,
                    tableManager);
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


    public static void testAddAllItemsByTable15ToConfirmedOrders(OrderManager orderManager) {
        int tableNumber = 15;
        try {orderManager.addAllItemByTableToConfirmedOrders(tableNumber);}
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat položky ze stolu " + tableNumber + " do potvrzených objednávek: "
                    + e.getLocalizedMessage());
        }
    }
    public static void testAddAllItemsByTable2ToConfirmedOrders(OrderManager orderManager) {
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

    public static void testChangeItemStatusHasBeenBroughtToTableByItemNumber3(OrderManager orderManager) {
        int itemNumber = 4;
        try {
            orderManager.changeItemStatusHasBeenBroughtToTableByItemNumber(itemNumber);
            System.out.println("Stav položky č." + itemNumber + " byl úspěšně změněn.");
        } catch (RestaurantException e) {
            System.err.println("Nepodařilo se změnit stav položky č." + itemNumber + " v potvrzených objednávkách: "
                    + e.getLocalizedMessage());
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

    public static void testGetAverageProcessingTimeInTheSpecifiedTimePeriod(RestaurantManager restaurantManager) {
        try {
            LocalDateTime startOfPeriod = LocalDateTime.of(2023, 1, 1, 0, 0);
            LocalDateTime endOfPeriod = LocalDateTime.of(2024, 1, 1, 0, 0);
            Integer averageProcessingTime = restaurantManager.getAverageProcessingTimeInTheSpecifiedTimePeriod
                    (startOfPeriod, endOfPeriod);
            System.out.println("Průměrná doba zpracování objednávek od objednání do přinesení na stůl v zadaném "
                    + "časovém období je: " + averageProcessingTime + " minut");
        } catch (RestaurantException e) {
            System.out.println("Chyba: " + e.getMessage());
        }
    }

    public static void testGetTotalValueForTable15ByConfirmedUnclosedOrders(RestaurantManager restaurantManager) {
        int tableNumber = 15;
        BigDecimal totalValue = restaurantManager.getTotalValueForTableByConfirmedUncloasedOrders(tableNumber);
        System.out.println("Celková hodnota potvrzených a neuzavřených objednávek pro stůl č. " + tableNumber
                + " je: " + totalValue + " Kč");
    }

    public static void testFillingTheFileWithTables(TableManager tableManager) {
        List<String> tableData = Arrays.asList(
                "1; HLAVNÍ SÁL; A1; 4",
                "2; HLAVNÍ SÁL; A2; 4",
                "3; HLAVNÍ SÁL; A3; 4",
                "4; HLAVNÍ SÁL; A4; 4",
                "5; HLAVNÍ SÁL; A5; 4",
                "6; HLAVNÍ SÁL; B1; 6",
                "7; HLAVNÍ SÁL; B2; 6",
                "8; HLAVNÍ SÁL; B3; 6",
                "9; HLAVNÍ SÁL; C1; 2",
                "10; HLAVNÍ SÁL; C2; 2",
                "11; HLAVNÍ SÁL; C3; 2",
                "12; HLAVNÍ SÁL; C4; 2",
                "13; HLAVNÍ SÁL; C5; 2",
                "14; HLAVNÍ SÁL; C6; 2",
                "15; TERASA; A1; 4",
                "16; TERASA; A2; 4",
                "17; TERASA; B1; 4",
                "18; TERASA; B2; 4",
                "19; SALONEK; A1; 20"
        );
        for (String tableInfo : tableData) {
            String[] item = tableInfo.split("; ");
            int tableNumber = Integer.parseInt(item[0]);
            String tableLocation = item[1];
            String tableSector = item[2];
            int tableCapacity = Integer.parseInt(item[3]);
            Table table = new Table(tableNumber, tableLocation, tableSector, tableCapacity);
            tableManager.addTable(table); // tady se to přidává do TbleListu, úplně stejně jako by se to dělalo,
            // kdyby uživatel přidával jeden stůl po druhém, jen jsem to urychlil testovacím kódem výše,
            // abych se neupsal, výsledek je stejný, jako kdyby se použil rovnou ten TXT soubor, který tam původně
            // byl přiložen...
        }
        saversVoids.saveTablesData(tableManager);
    }

    public static void testFillingTheFileWithWaiters(WaiterManager waiterManager) {
        List<String> waiterData = Arrays.asList(
                "1; Ing.; Josef; Králík; ; 251954480; HPP",
                "2; ; Dalibor; Vrána; ; 901950481; HPP",
                "3; ; Zdeněk; Svěrák; ; 106990851; HPP",
                "4; MUDr.; Karel; Nešpor; ,CSc.; 999999999; HPP",
                "5; MUDr.; Miroslav; Plzák; ,CSc.; 999999998; HPP",
                "6; MUDr.; Radim; Uzel; ,CSc.; 999999998; HPP",
                "7; JUDr.; Ivo; Jahelka; ; 999999997; HPP",
                "8; ; Volodymyr; Zelenskyj; ; AH5856ST; BRIGADA",
                "9; ; Joe; Biden; ; AH5856ST1; BRIGADA",
                "10; ; Vladimir; Putin; ; AH5856ST1O; BRIGADA",
                "11; ; Emmanuel; Macron; ; AH5856ST1; BRIGADA",
                "12; prof.PhDr.; Petr; Fiala; ,Ph.D.,LL.M.; 996669998; HPP",
                "13; ; Jiřina; Jirásková; ; 105690851; VPP",
                "14; ; Hana; Zagorová; ; 985690851; VPP",
                "15; ; Lenka; Filipová; ; 943560851; VPP",
                "16; ; Helena; Vyvondráčkovaná; ; 111111111; VPP",
                "17; ; Helena; Růžičková; ; 111111112; DPP",
                "18; ; Marie; Rotrová; ; 111596112; DPP",
                "19; ; Nina; Ricci; ; AH5123ST1; DPP",
                "20; ; Coco; Channel; ; A12323ST1; HPP",
                "21; ; Iva; Janžurová; ; 111771112; HPP",
                "22; ; Dana; Medřická; ; 111771252; HPP",
                "23; ; Lída; Baarová; ; 111774562; HPP",
                "24; ; Ivana; Chýlková; ; 156274562; HPP"
        );
        for (String waiterInfo : waiterData) {
            String[] item = waiterInfo.split("; ");
            int waiterNumber = Integer.parseInt(item[0].trim());
            String titleBefore = item[1].trim();
            String firstName = item[2].trim();
            String secondName = item[3].trim();
            String titleAfter = item[4].trim();
            String idDocumentNumber = item[5].trim();
            String employmentRelationship = item[6].trim();
            Waiter waiter = new Waiter(
                    waiterNumber, titleBefore, firstName, secondName, titleAfter, idDocumentNumber,
                    employmentRelationship
            );
            waiterManager.addWaiter(waiter); // to samé jako u stolů výše
        }
        saversVoids.saveWaitersData(waiterManager);
    }

    public static void testFillingTheFileWithCategories(FoodCategory foodCategory) throws RestaurantException {
        List<String> categoryData = Arrays.asList(
                "NONALCOHOLIC; nealkoholický nápoj", "ALCOHOLIC; alkoholický nápoj", "BEER; pivo", "WINE; víno",
                "CHAMPAIGN; šampaňské", "DESTILATE; destilát", "OTHER; další nabídka", "SOMETHINGTOBEER; něco k pivu",
                "PRECAUTION; předkrm", "SOUP; polévka", "PREPAREDFOOD; hotové jídlo", "CHILDFOOD; dětské jídlo",
                "MINUTEMEAL; minutka", "GRILLED; na grilu", "SPECIALITY; specialita", "SALAD; salát",
                "GARNISH; příloha", "DIP; omáčka", "ICECREAM; zmrzlinový pohár", "BUBBLEFREE; neperlivý",
                "LIGHTLYSPARKLING; jemně perlivý", "SPARKLING; perlivý/šumivé", "LEMONADE; limonáda", "JUICE; džus",
                "BRIGHT; světlé", "DARK; tmavé", "MIXED; řezané", "WHITE; bílé", "RED; červené",
                "ROSE; růžové", "APERITIVE; aperitiv", "LIQUOR; likér", "WHISKY; whisky/burbon", "BEEF; hovězí",
                "PORK; vepřové", "HATTED; skopové", "CHICKEN; kuřecí", "KITCHEN; kachní", "FISH; ryba",
                "SEAFOOD; mořské plody", "GREEN; zeleninový", "LITTLE; malý", "GLUTENFREEFOOD; bezlepkový"
        );

        for (String categoryInfo : categoryData) {
            String[] item = categoryInfo.split("; ");
            String name = item[0].trim();
            String description = item[1].trim();
            foodCategory.addCategory(name, description); // to samé jako u stolů a číšníků výše
        }
    }


}












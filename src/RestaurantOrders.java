import com.certifikace.projekt1.*;

import java.io.IOException;

public class RestaurantOrders {
    public static void main(String[] args) throws RestaurantException, IOException {

        RestaurantPrintLnOutputsForMain printLnOutputs = new RestaurantPrintLnOutputsForMain();
        RestaurantLoadersVoidsForMain loadersVoids = new RestaurantLoadersVoidsForMain();
        RestaurantSaversVoidsForMain saversVoids = new RestaurantSaversVoidsForMain();
        FoodCategory foodCategory; foodCategory = FoodCategory.getInstance();
        TableManager tableManager = new TableManager();
        WaiterManager waiterManager = new WaiterManager();
        DishManager dishManager = new DishManager();
        ActualMenuManager amManager = new ActualMenuManager();
        OrderManager orderManager = new OrderManager();
        RestaurantManager restaurantManager = new RestaurantManager(orderManager);

        loadersVoids.loadTablesData(tableManager);
        loadersVoids.loadWaitersData(waiterManager);
        loadersVoids.loadDishsData(dishManager);
        loadersVoids.loadActualMenuData(amManager);
        loadersVoids.loadUnconfirmedItemsList(orderManager);
        loadersVoids.loadConfirmedItemsList(orderManager);
        loadersVoids.loadClosedOrdersList(orderManager);

        System.out.println(); System.out.println();
        System.out.println("Restaurant Chez Quis à Prague");

        // Zde plním soubory a data nezbytná pro správný běh testovacího kódu, v podstatě jsem jen stejnými metodami,
        // které by se volali z FrontEndu a uživatel by si ty věci zadával sám od píky, naplnil ty tři TXT,
        // které nebyli součástí zadání natvdro, a doúkoloval jsem se s nimi sám, těmi samými daty, která byli v těch
        // třech nezbytných souborech TXT, které jsem přiložil ke kódu rovnou, jak je vidět, program při loadování výše
        // nespadl, zahlásil nějaké chybky, pokračoival dál a zde již se to plní... :-) ...a vše jede jak bylo
        // zamýšleno, když jsem si to před čtvrt rokem koncipoval. :-)
        TestVoidsForMain.testFillingTheFileWithCategories(foodCategory);
        TestVoidsForMain.testFillingTheFileWithTables(tableManager);
        TestVoidsForMain.testFillingTheFileWithWaiters(waiterManager);

        // Testovací scénář - Bod 1.
        printLnOutputs.printDishList(dishManager);
        printLnOutputs.printMenuList(amManager);
        printLnOutputs.printUnconfirmedItemsList(orderManager);
        printLnOutputs.printConfirmedItemsList(orderManager);

        // Testovací scénář - Bod 2a. (přidat 4 specifická jídla do dishList)
        TestVoidsForMain.testCreateAndAddNewDish1(dishManager);
        TestVoidsForMain.testCreateAndAddNewDish2(dishManager);
        TestVoidsForMain.testCreateAndAddNewDish3(dishManager);
        TestVoidsForMain.testCreateAndAddNewDish4(dishManager);
        saversVoids.saveDishsData(dishManager);

        // Testovací scénář - Bod 2b. (přidat 3 specifická jídla do amList)
        TestVoidsForMain.testAddFoodToMenu1(amManager, dishManager);
        TestVoidsForMain.testAddFoodToMenu2(amManager, dishManager);
        TestVoidsForMain.testAddFoodToMenu4(amManager, dishManager);
        saversVoids.saveMenuData(amManager);

        // Testovací scénář - Bod 2c. (na 15tku 2x řízek, hrany a kofolu + výzdoba do poznámky + objednávka na dvojku
        // a 2 kofoly z 15tky do dodensenjch objednávek)
        TestVoidsForMain.testAddItemToUnconfirmedOrdersListByTitleAndQuantity1
                (orderManager, amManager, waiterManager, tableManager);
        TestVoidsForMain.testAddItemToUnconfirmedOrdersListByTitleAndQuantity2
                (orderManager, amManager, waiterManager, tableManager);
        TestVoidsForMain.testAddItemToUnconfirmedOrdersListByTitleAndQuantity3
                (orderManager, amManager, waiterManager, tableManager);
        TestVoidsForMain.testAddItemToUnconfirmedOrdersListByTitleAndQuantity4
                (orderManager, amManager, waiterManager, tableManager);
        TestVoidsForMain.testAddAllItemsByTable15ToConfirmedOrders(orderManager);
        TestVoidsForMain.testAddAllItemsByTable2ToConfirmedOrders(orderManager);
        TestVoidsForMain.testChangeItemStatusHasBeenBroughtToTableByItemNumber3(orderManager);

        // Testovací scénář - Bod 3.
        TestVoidsForMain.testAddItemToUnconfirmedOrdersListByTitleAndQuantityFoodNotExistInActualMenu
                (orderManager, amManager, waiterManager, tableManager);

        // Testovací scénář - Bod 4. NOVÝ! - starý zněl PROVEĎ UZAVŘENÍ OBJENÁVKY
        // A? Jako za celou dobu existance stolu? Nebo jak? Pro jaké hosty nebo objednávky? :DD zadání mimo realitu...
        // Budu brát aktuální potvrzené, rozpracované a ještě nezaplacené objednávky, nevím jak jinak... :-)
        // Konzumace je co? Kolik toho vypili a snědli nebo k čemu je tato rutina? Management vyhodnocuje věci zpětně,
        // ne za chodu, to nemá význam...
        TestVoidsForMain.testGetTotalValueForTable15ByConfirmedUnclosedOrders(restaurantManager);

        // Testovací scénář - Bod 5.
        System.out.println(); System.out.println("Počet potvrzených a nedokončených objednávek, to znamená, "
                + "že ještě nebyly doneseny hostovi a nebyly hostem zaplaceny je: "
                + restaurantManager.getNumberOfReceivedOrders());

        // PLNĚNÍ - RestaurantManager - BOD2 - část A - číslo číšníka (stačí odkomentovat)
        System.out.println(); System.out.println("Seřazené potvrzené objednávky podle číšníka, které ještě nebyly "
                + "uzavřeny, tedy doneseny na stůl a zaplaceny:");
        restaurantManager.getSortedOrdersByWaiterNumberOfConfirmedOrders().forEach(System.out::println);

        // PLNĚNÍ - RestaurantManager - BOD2 - část B - čas zadání (stačí odkomentovat)
        System.out.println(); System.out.println("Seřazené potvrzené objednávky podle času potvrzení objednání položky,"
                + " které ještě nebyly uzavřeny, tedy doneseny na stůl a zaplaceny:");
        restaurantManager.getSortedOrdersByTimeReceiptOfConfirmedOrders().forEach(System.out::println);


        // PLNĚNÍ - RestaurantManager - BOD3 - část A - celková cena a počet aktuální objednávek jednotlivých číšníků
        System.out.println(); System.out.println("Seznam číšníků, kteří mají aktuálně rozpracované a neuzavřené "
                + "objednávky, počet těchto objednávek každého číšníka a jejich celkový součet:");
        restaurantManager.getSortedAllActualOrdersByWaiterAndTurnover().forEach(System.out::println);

        // PLNĚNÍ - RestaurantManager - BOD3 - část B - celkový obrat a počet uzavřených objednávek jedotlivých číšníků
        // za celou jejich kariéru v této restauraci. Do obraru se započítávají pouze uzavřené objednávky, tj. donesné
        // na stůl a zaplacené
        System.out.println(); System.out.println("Seznam číšníků a jejich celkové obraty uzavřených objednávek za " +
                "celou jejich kariéru v naší restauraci:");
        restaurantManager.getSortedAllClosedOrdersByWaiterAndTurnover().forEach(System.out::println);

        // PLNĚNÍ - RestaurantManager - BOD4 - průměrná doba zpracovaní objednávek v zadaném časovém období,
        // tedy od potvrzení objednávky hostem po donesení hostovi na stůl, bylo by ale dobré mít v tuto chvíli
        // v receivedOrdersList nějaké objednávky s orderTimeIssue != null nebo jich mít vic v closedOrdersList
        System.out.println(); TestVoidsForMain.testGetAverageProcessingTimeInTheSpecifiedTimePeriod(restaurantManager);

        // Výzvu č.3 plnit NEbudu, je to absolutní nesmysl v zadání, když u stolu bude sedět více hostů a každý bude
        // chtít platit zvlášť, tak by to byl velkej problém, navíc, každý z nich by měl dostat daňový doklad na to co
        // si vypil nebo snědl a ne i za kámoše, kterej sedí na pivu vedle něj. Když bude chtít, může, můj kód by tuto
        // situaci měl mít ošetřenu, aby mohl, zbytek si myslím, je k pracem na FrondEndu, ale nevím.

        // PLNĚNÍ - RestaurantManager - BOD5 - Seznam jídel, které byli dnes objednány bez ohledu na to kolikrát
        System.out.println(); System.out.println("Seznam jídel, které byli dnes objednány bez ohledu na to kolikrát: ");
        restaurantManager.getListOfMealsOrderedTodayWithUniqeTitles().forEach(System.out::println);

        // PLNĚNÍ - Formát výstupu dle zadání
        printLnOutputs.printConfirmedItemsListExportFormattedAsSpecified(orderManager);

    }

}


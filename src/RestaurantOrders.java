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


        // Testovací scénář - Bod 1.
        printLnOutputs.printDishList(dishManager);
        printLnOutputs.printMenuList(amManager);

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



        // Zkušební kód pro odebrání jídla z aktuálního menu (stačí ho odkomentovat), ale lze to udělat jedině
        // v případě, že jste před tím aktivovali předchozí zkušební metodu pro přidání, jinak to zahlásí chybovou
        // hlášku, nic to neudělá, ale program bude bez pádu pokračovat dál
        //TestVoidsForMain.testRemoveFoodFromMenu(amManager); printLnOutputs.printMenuList(amManager);

        // Zkušební kód pro splnění zadání - totálního vymazání Menu (stačí ho odkomentovat), program ho vymaže,
        // pak je ale třeba tam vrátit ten první testovací TXT, jinak viz. výše, jako doposud.
        //TestVoidsForMain.testClearAndSave(amManager, saversVoids); printLnOutputs.printMenuList(amManager);

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-ActualMenu.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Pak je ale třeba tam vrátit ten první testovací, jinak nebudou správně fungovat další testovací kódy.


        //printLnOutputs.printUnconfirmedItemsList(orderManager);

        // Zkušební kód pro přidání položky do rozpracovaných objednávek všech číšníků, které ještě nebyli úplně
        // dohodnuty s hosty u jednotlivých stolů a finálně objednány. (stačí ho odkomentovat)
        /*
        TestVoidsForMain.testAddItemToUnconfirmedOrdersListByTitleAndQuantity(
                orderManager, amManager, waiterManager, tableManager);
        printLnOutputs.printUnconfirmedItemsList(orderManager);
        */

        // Zkušební kód pro odebrání položky z rozpracovaných objednávek všech číšníků, které ještě nebyli úplně
        // dohodnuty s hosty u jednotlivých stolů a finálně objednány dle čísla položky. (stačí ho odkomentovat)
        /*
        TestVoidsForMain.testRemoveItemOfUnconfirmedOrdersByItemNumber(orderManager);
        printLnOutputs.printUnconfirmedItemsList(orderManager);
        */

        // Zkušební kód pro odebrání všech položek z rozpracovaných objednávek všech číšníků, které ještě nebyli úplně
        // dohodnuty s hosty u jednotlivých stolů a finálně objednány dle čísla stolu. (stačí ho odkomentovat)
        /*
        TestVoidsForMain.testRemoveAllItemsOfUnconfirmedOrdersByTable(orderManager);
        printLnOutputs.printUnconfirmedItemsList(orderManager);
        */


        //printLnOutputs.printConfirmedItemsList(orderManager);

        // Zkušební kód pro přidání všech položek z rozpracovaných objednávek všech číšníků, které již byly úplně
        // dohodnuty s hosty u jednotlivých stolů a finálně objednány dle čísla stolu do Listu s potvrzenými
        // objednávkami a jejich odebrání z Listu s nepotvrzenýmí objednávkami. (stačí ho odkomentovat)
        /*
        TestVoidsForMain.testAddAllItemByTable15ToConfirmedOrders(orderManager);
        printLnOutputs.printConfirmedItemsList(orderManager);
        TestVoidsForMain.testPrinterOutputToBar(orderManager);
        TestVoidsForMain.testPrinterOutputToKitchen(orderManager);
        TestVoidsForMain.testAddAllItemByTable2ToConfirmedOrders(orderManager);
        printLnOutputs.printConfirmedItemsList(orderManager);
        TestVoidsForMain.testPrinterOutputToBar(orderManager);
        TestVoidsForMain.testPrinterOutputToKitchen(orderManager);
        */

        //TestVoidsForMain.testAddAllItemByTable15ToConfirmedOrders(orderManager);

        // Plnění výzvy č.4 - Všechny nebo jen některé objednávky se převedou k jinému stolu
        // Tady vidím trochu problém v tom, že objednávkový lístek již bude pro kuchyń nebo bar vytištěn, takže
        // číšník bude muset informovat číšníka, který se o nový stůl stará o této změně ústně, ale při výstupech
        // na obrazovku/y se tato změna projeví ihned, když to FrondEnd správně ošetří. Dalo by se to ošetřit další
        // třídou a kódem, kde se pro každou směnu budou přidělovat stoly jednotlivým číšníkům a rovnou přehodit
        // i číšníka, ale to bych to s mým časovým rozvrhem nikdy nedodělal... :D ...ta kuchyň a bar se dá ošetřit,
        // že jim na tiskárně vyjede ta změna stolu s příslunými položkami a novým stolem, to by se dalo již
        // v OrderManager, ale opět, jsem ve skluzu. Samozřejmě, když nový stůl bude mít na starosti stejný číšník
        // jako byl u předchozího, tak si to musí jen pamatovat a ani to asi ne. Uzavřené, tedy donesené a i již
        // zaplacené objednávky již samozřejmě nejde nikam převádět, ty již slouží jen jako podklady pro Účtárnu
        // a Management.
        // Část A - převádějí se jen některé položky k novému stolu
        /*
        TestVoidsForMain.testChangeTableNumberBySelectedTableAndItemsToNewTable(orderManager,tableManager);
        printLnOutputs.printConfirmedItemsList(orderManager);
        */
        // Část B - převádějí se všechny položky k novému stolu
        /*
        TestVoidsForMain.testChangeTableNumberBySelectedTablesAllItemsToNewTable(orderManager, tableManager);
        printLnOutputs.printConfirmedItemsList(orderManager);
        */


        // PLNĚNÍ - RestaurantManager - BOD1 (stačí odkomentovat)
        /*
        System.out.println(); System.out.println("Počet potvrzených a nedokončených objednávek, to znamená, "
                + "že ještě nebyly doneseny hostovi a nebyly hostem zaplaceny je: "
                + restaurantManager.getNumberOfReceivedOrders());
        */

        // PLNĚNÍ - RestaurantManager - BOD2 - část A - číslo číšníka (stačí odkomentovat)
        /*
        System.out.println(); System.out.println("Seřazené potvrzené objednávky podle číšníka, které ještě nebyly "
                + "uzavřeny, tedy doneseny na stůl a zaplaceny:");
        restaurantManager.getSortedOrdersByWaiterNumberOfConfirmedOrders().forEach(System.out::println);
        */

        // PLNĚNÍ - RestaurantManager - BOD2 - část B - čas zadání (stačí odkomentovat)
        /*
        System.out.println(); System.out.println("Seřazené potvrzené objednávky podle času potvrzení objednání položky,"
                + " které ještě nebyly uzavřeny, tedy doneseny na stůl a zaplaceny:");
        restaurantManager.getSortedOrdersByTimeReceiptOfConfirmedOrders().forEach(System.out::println);
        */

        // PLNĚNÍ - RestaurantManager - BOD3 - část A - celková cena a počet aktuální objednávek jednotlivých číšníků
        // (stačí odkomentovat)
        /*
        System.out.println(); System.out.println("Seznam číšníků, kteří mají aktuálně rozpracované a neuzavřené "
                + "objednávky, počet těchto objednávek každého číšníka a jejich celkový součet:");
        restaurantManager.getSortedAllActualOrdersByWaiterAndTurnover().forEach(System.out::println);
        */

        // PLNĚNÍ - RestaurantManager - BOD3 - část B - celkový obrat a počet uzavřených objednávek jedotlivých číšníků
        // za celou jejich kariéru v této restauraci. Do obraru se započítávají pouze uzavřené objednávky, tj. donesné
        // na stůl a zaplacené (stačí odkomentovat)
        /*
        System.out.println(); System.out.println("Seznam číšníků, kteří mají aktuálně rozpracované a neuzavřené "
                + "objednávky, počet těchto objednávek každého číšníka a jejich celkový součet:");
        restaurantManager.getSortedAllClosedOrdersByWaiterAndTurnover().forEach(System.out::println);
        */

        // Zkušební kód pro změnu stavu objednávky, které již byli potvrzeny a pracuje se na nich, v tomoto případě byli
        // již doneseny na stůl, takhle se zaznamená čas, kdy byli hostovi předloženy, ale ještě nebyli zaplaceny, ale
        // mohli být zaplaceny již např. při objednání před donesením jídla nebo pití na stůl.(stačí ho odkomentovat)
        /*
        TestVoidsForMain.testChangeItemStatusHasBeenBroughtToTableByItemNumber2to3(orderManager);
        printLnOutputs.printConfirmedItemsList(orderManager);
        TestVoidsForMain.testChangeItemStatusHasBeenBroughtToTableByItemNumber7to8(orderManager);
        printLnOutputs.printConfirmedItemsList(orderManager);
        TestVoidsForMain.testChangeItemStatusHasBeenBroughtToTableByItemNumber4to6(orderManager);
        printLnOutputs.printConfirmedItemsList(orderManager);
        */

        // Zkušební kód pro změnu stavu objednávky, které již byli potvrzeny a pracuje se na nich, v tomoto případě byli
        // již zaplaceny, takže se změní kategorie objednávky na PAID, akce může být provedena, ještě před donesení
        // jídla nebo pití (stačí ho odkomentovat)
        /*
        TestVoidsForMain.testChangeItemStatusHasBeenPaidByItemNumber2to6(orderManager);
        printLnOutputs.printConfirmedItemsList(orderManager);
        TestVoidsForMain.testChangeItemStatusHasBeenPaidByItemNumber7(orderManager);
        printLnOutputs.printConfirmedItemsList(orderManager);
        printLnOutputs.printCosedOrdersList(orderManager);
        */

        // PLNĚNÍ - RestaurantManager - BOD4 - průměrná doba zpracovaní objednávek v zadaném časovém období,
        // tedy od potvrzení objednávky hostem po donesení hostovi na stůl, bylo by ale dobré mít v tuto chvíli
        // v receivedOrdersList nějaké objednávky s orderTimeIssue != null nebo jich mít vic v closedOrdersList
        // (stačí odkomentovat)
        //System.out.println(); TestVoidsForMain.TestGetAverageProcessingTimeInTheSpecifiedTimePeriod(restaurantManager);

        // Výzvu č.3 plnit NEbudu, je to absolutní nesmysl v zadání, když u stolu bude sedět více hostů a každý bude
        // chtít platit zvlášť, tak by to byl velkej problém, navíc, každý z nich by měl dostat daňový doklad na to co
        // si vypil nebo snědl a ne i za kámoše, kterej sedí na pivu vedle něj. Když bude chtít, může, můj kód by tuto
        // situaci měl mít ošetřenu, aby mohl, zbytek si myslím, je k pracem na FrondEndu, ale nevím.

        // PLNĚNÍ - RestaurantManager - BOD5 - Seznam jídel, které byli dnes objednány bez ohledu na to kolikrát
        // (stačí odkomentovat)
        /*
        System.out.println(); System.out.println("Seznam jídel, které byli dnes objednány bez ohledu na to kolikrát: ");
        restaurantManager.getListOfMealsOrderedTodayWithUniqeTitles().forEach(System.out::println);
        */

    }

}


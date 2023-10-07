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

        loadersVoids.loadTablesData(tableManager);
        loadersVoids.loadWaitersData(waiterManager);
        loadersVoids.loadDishsData(dishManager);
        loadersVoids.loadActualMenuData(amManager);
        loadersVoids.loadUnconfirmedItemList(orderManager);
        loadersVoids.loadConfirmedItemList(orderManager);


        // Toto tu je jen kvůli testování, později (při pozdějším reálném použití programu) se to může smazat
        // i s testovacími TXT. Nicméně pro testování jsou pro mě nutné. Nakonec ani jeden řádek kódu pod touto
        // poznámkou není nutný a vše je kvůli testování. Jestli se nemýlím, kolegům z FrontEndu by mělo stačit vše výše
        // a možná ani to nepotřebují, ale to nevím určitě.

        System.out.println(); System.out.println();
        System.out.println("Restaurant Chez Quis à Prague");


        //printLnOutputs.printFoodCategoryList(foodCategory);

        // Zkušební kód pro přidání nové kategorie do foodCategory a uložení do souboru (stačí ho odkomentovat)
        //foodCategory.addCategory("NEWCATEGORY", "nová kategorie"); printLnOutputs.printFoodCategoryList(foodCategory);

        // Zkušební kód pro odebrání kategorie z foodCategory a uložení do souboru (stačí ho odkomentovat)
        // Jako příklad jsem odebral kategorii, kterou jsem přidal v předchozím testovacím kódu NEWCATEGORY,
        // takže se musí nejdříve přidat, jinak to vyhodí chybovou hlášku nebo se musí odebrat nějaká kategorie z již
        // existujících, které mám uloženy v testovacím TXT souboru, ale to bych nedělal! Simuluji třídu ENUM,
        // která má být statická, ale zároveň plním výzvu: "Možnost přidávat a odebírat kategorie jídel" a podmínku,
        // že po pádu systému se tento obnoví "bez ztráty kytičky". :-) Jinak by to mělo splňovat všechny podmínky
        // zadání, ale když se odebere nějaká kategorie z testovacího souboru TXT, bude to vyhazovat chybový hlášky,
        // protože je nutný pro další má testovací data, jinak by to podle mě mělo být OK i v případě, že žádné
        // testovací TXT soubory nebudou existovat, když se ten projekt spustí úplně "holej".
        //foodCategory.removeCategory("NEWCATEGORY"); printLnOutputs.printFoodCategoryList(foodCategory);

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-FoodCategories.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Pak je ale třeba tam vrátit ten první testovací, jinak nebudou správně fungovat další testovací kódy.


        //printLnOutputs.printTableList(tableManager);

        // Zkušební kód pro přidání stolu do tableList a jeho uložení do souboru (stačí ho odkomentovat)
        //TestVoidsForMain.createAndAddNewTable(tableManager); printLnOutputs.printTableList(tableManager);

        // Zkušební kód pro odebrání stolu z tableList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím byl stůl číslo 20 přidán aktivováním předchozí zkušební metody, jinak to bude hlásit chybu
        // a stůl se neodebere, ale program nespadne
        //TestVoidsForMain.removeTableByNumber(tableManager); printLnOutputs.printTableList(tableManager);

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Tables.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Pak je ale třeba tam vrátit ten první testovací, jinak nebudou správně fungovat další testovací kódy.


        //printLnOutputs.printWaiterList(waiterManager);

        // Zkušební kód pro přidání číšníka do waiterList a uložení do souboru (stačí ho odkomentovat)
        //TestVoidsForMain.createAndAddNewWaiter(waiterManager); printLnOutputs.printWaiterList(waiterManager);

        // Zkušební kód pro odebrání číšníka z waiterList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím byl číšník s číslem 25 přidán aktivováním předchozí zkušební metody, jinak to bude hlásit chybu
        // a číšník se neodebere, ale program nespadne
        //TestVoidsForMain.removeWaiterByNumber(waiterManager); printLnOutputs.printWaiterList(waiterManager);

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Waiters.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Pak je ale třeba tam vrátit ten první testovací, jinak nebudou správně fungovat další testovací kódy.


        //printLnOutputs.printDishListDataFromFile(dishManager);

        // Zkušební kód pro přidání jídla do zásobníku a jeho uložení do souboru (stačí ho odkomentovat)
        //TestVoidsForMain.createAndAddNewDish(dishManager); printLnOutputs.printDishListDataFromFile(dishManager);

        // Zkušební kód pro odebrání jídla z dishList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím bylo jídlo "Katův šleh "Chez Quis à Prague" s doporučeným nožstvím 250 g přidáno aktivováním
        // předchozí zkušební metody, jinak to bude hlásit chybu a jídlo se neodebere, ale program nespadne
        /*
        TestVoidsForMain.removeDishByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro nahrazení hlavní doporučené kategorie jidla v dishList (stačí ho odkomentovat)
        /*
        TestVoidsForMain.replaceDishRecomendedMainCategoryByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro přidání další doporučené kategorie jidla v dishList (stačí ho odkomentovat)
        /*
        TestVoidsForMain.addDishNextRecomendedCategoryByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro odebrání další doporučené kategorie z dishList (stačí ho odkomentovat), ale lze to udělat
        // jen v případě, že před tím byla další doporučená kategorie "SALAD" přidána aktivováním předchozí zkušební
        // metody, jinak to bude hlásit chybu a kategorie se neodebere, ale program nespadne
        /*
        TestVoidsForMain.removeAddressedDishNextRecomendedCategoryByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro změnu názvu jídla v dishList (stačí ho odkomentovat), ale lze to udělat jen v případě, že
        // máte jako aktuální můj původní soubor DB-dish.txt, jinak to bude hlásit chybu a nic to neudělá, ale program
        // poběží dál
        /*
        TestVoidsForMain.renameRecomendedDishTitleByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro přidání nového jídla z dishList se stejným názvem, ale s jinou hodnotou velikosti porce
        // a cenou (stačí ho odkomentovat), ale lze to udělat jen v případě, že že máte jako aktuální můj původní soubor
        // DB-dish.txt, jinak to bude hlásit chybu a nic to neudělá, ale program poběží dál
        /*
        TestVoidsForMain.addDishSameTitleWithDifferentQuantityAndPrice(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro změnu doporučeného množství jídla v dishList (stačí ho odkomentovat), ale lze to udělat jen
        // v případě, že máte jako aktuální můj původní soubor DB-dish.txt, jinak to bude hlásit chybu a nic to neudělá,
        // ale program poběží dál
        /*
        TestVoidsForMain.replaceDishRecommendedQuantityByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro změnu doporučené ceny jídla v dishList (stačí ho odkomentovat), ale lze to udělat jen
        // v případě, že máte jako aktuální můj původní soubor DB-dish.txt, jinak to bude hlásit chybu a nic to neudělá,
        // ale program poběží dál
        /*
        TestVoidsForMain.replaceDishRecommendedPriceByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro změnu předpokládané doby přípravy jídla v dishList (stačí ho odkomentovat), ale lze
        // to udělat jen v případě, že máte jako aktuální můj původní soubor DB-dish.txt, jinak to bude hlásit chybu
        // a nic to neudělá, ale program poběží dál
        /*
        TestVoidsForMain.replaceDishEstimatedPreparationTimeByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro změnu názvu souboru s hlavní fotografií v dishList (stačí ho odkomentovat), ale lze
        // to udělat jen v případě, že máte jako aktuální můj původní soubor DB-dish.txt, jinak to bude hlásit chybu
        // a nic to neudělá, ale program poběží dál
        /*
        TestVoidsForMain.replaceDishMainPhotoByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro přidání dalšího názvu souboru fotografie do dalších fotografií v dishList (stačí ho
        // odkomentovat), ale lze to udělat jen v případě, že máte jako aktuální můj původní soubor DB-dish.txt, jinak
        // to bude hlásit chybu a nic to neudělá, ale program poběží dál
        /*
        TestVoidsForMain.addDishNextPhotoByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro odebrání dalšího názvu souboru fotografie z dalších fotografií v dishList (stačí ho
        // odkomentovat), ale lze to udělat jen v případě, že jste před tím přidali další fotku v předchozím zkušebním
        // kód pro test přidání fotografie, jinak to bude hlásit chybu a nic to neudělá, ale program poběží dál
        /*
        TestVoidsForMain.removeAddressedDishNextPhotoByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Dishs.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Pak je ale třeba tam vrátit ten první testovací, jinak nebudou správně fungovat další testovací kódy.


        //printLnOutputs.printMenuListDataFromFile(amManager);

        // Zkušební kód pro přidání jídla do aktuálního menu a jeho uložení do souboru (stačí ho odkomentovat)
        //TestVoidsForMain.testAddFoodToMenu(amManager, dishManager); printLnOutputs.printMenuListDataFromFile(amManager);

        // Zkušební kód pro odebrání jídla z aktuálního menu (stačí ho odkomentovat), ale lze to udělat jedině
        // v případě, že jste před tím aktivovali předchozí zkušební metodu pro přidání, jinak to zahlásí chybovou
        // hlášku, nic to neudělá, ale program bude bez pádu pokračovat dál
        //TestVoidsForMain.testRemoveFoodFromMenu(amManager); printLnOutputs.printMenuListDataFromFile(amManager);

        // Zkušební kód pro splnění zadání - totálního vymazání Menu (stačí ho odkomentovat), program ho vymaže,
        // pak je ale třeba tam vrátit ten první testovací TXT, jinak viz. výše, jako doposud.
        //TestVoidsForMain.testClearAndSave(amManager, saversVoids); printLnOutputs.printMenuListDataFromFile(amManager);

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-ActualMenu.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Pak je ale třeba tam vrátit ten první testovací, jinak nebudou správně fungovat další testovací kódy.


        //printLnOutputs.printUnconfirmedItemOrederListDataFromFile(orderManager);

        // Zkušební kód pro přidání položky do rozpracovaných objednávek všech číšníků, které ještě nebyli úplně
        // dohodnuty s hosty u jednotlivých stolů a finálně objednány. (stačí ho odkomentovat)
        /*
        TestVoidsForMain.testAddItemToUnconfirmedOrdersListByTitleAndQuantity(
                orderManager, amManager, waiterManager, tableManager);
        printLnOutputs.printUnconfirmedItemOrederListDataFromFile(orderManager);
        */

        // Zkušební kód pro odebrání položky z rozpracovaných objednávek všech číšníků, které ještě nebyli úplně
        // dohodnuty s hosty u jednotlivých stolů a finálně objednány dle čísla položky. (stačí ho odkomentovat)
        /*
        TestVoidsForMain.testRemoveItemOfUnconfirmedOrdersByItemNumber(orderManager);
        printLnOutputs.printUnconfirmedItemOrederListDataFromFile(orderManager);
        */

        // Zkušební kód pro odebrání všech položek z rozpracovaných objednávek všech číšníků, které ještě nebyli úplně
        // dohodnuty s hosty u jednotlivých stolů a finálně objednány dle čísla stolu. (stačí ho odkomentovat)
        /*
        TestVoidsForMain.testRemoveAllItemsOfUnconfirmedOrdersByTable(orderManager);
        printLnOutputs.printUnconfirmedItemOrederListDataFromFile(orderManager);
        */


        printLnOutputs.printConfirmedItemOrederListDataFromFile(orderManager);

        // Zkušební kód pro přidání všech položek z rozpracovaných objednávek všech číšníků, které již byly úplně
        // dohodnuty s hosty u jednotlivých stolů a finálně objednány dle čísla stolu do Listu s potvrzenými
        // objednávkami a jejich odebrání z Listu s nepotvrzenýmí objednávkami. (stačí ho odkomentovat)
        /*
        TestVoidsForMain.testAddAllItemByTable15ToConfirmedOrders(orderManager);
        printLnOutputs.printConfirmedItemOrederListDataFromFile(orderManager);
        TestVoidsForMain.testPrinterOutputToBar(orderManager);
        TestVoidsForMain.testPrinterOutputToKitchen(orderManager);
        TestVoidsForMain.testAddAllItemByTable2ToConfirmedOrders(orderManager);
        printLnOutputs.printConfirmedItemOrederListDataFromFile(orderManager);
        TestVoidsForMain.testPrinterOutputToBar(orderManager);
        TestVoidsForMain.testPrinterOutputToKitchen(orderManager);
        */




    }

}


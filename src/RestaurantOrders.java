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
        loadersVoids.loadUnconfirmedItemsList(orderManager);
        loadersVoids.loadConfirmedItemsList(orderManager);
        loadersVoids.loadClosedOrdersList(orderManager);



        // Toto tu je jen kvůli testování, později (při pozdějším reálném použití programu) se to může smazat
        // i s testovacími TXT. Nicméně pro testování jsou pro mě nutné. Nakonec ani jeden řádek kódu pod touto
        // poznámkou není nutný a vše je kvůli testování. Jestli se nemýlím, kolegům z FrontEndu by mělo stačit vše výše
        // a možná ani to nepotřebují, ale to nevím určitě.

        System.out.println(); System.out.println();
        System.out.println("Restaurant Chez Quis à Prague");


        //printLnOutputs.printFoodCategoryList(foodCategory);

        // Zkušební kód pro přidání nové kategorie do foodCategory a uložení do souboru (stačí ho odkomentovat)
        /*
        foodCategory.addCategory("NEWCATEGORY", "nová kategorie");
        printLnOutputs.printFoodCategoryList(foodCategory);
        */


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


        //printLnOutputs.printDishList(dishManager);

        // Zkušební kód pro přidání jídla do zásobníku a jeho uložení do souboru (stačí ho odkomentovat)
        //TestVoidsForMain.createAndAddNewDish(dishManager); printLnOutputs.printDishList(dishManager);

        // Zkušební kód pro odebrání jídla z dishList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím bylo jídlo "Katův šleh "Chez Quis à Prague" s doporučeným nožstvím 250 g přidáno aktivováním
        // předchozí zkušební metody, jinak to bude hlásit chybu a jídlo se neodebere, ale program nespadne
        /*
        TestVoidsForMain.removeDishByTitleAndQuantity(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro nahrazení hlavní doporučené kategorie jidla v dishList (stačí ho odkomentovat)
        /*
        TestVoidsForMain.replaceDishRecomendedMainCategoryByTitleAndQuantity(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro přidání další doporučené kategorie jidla v dishList (stačí ho odkomentovat)
        /*
        TestVoidsForMain.addDishNextRecomendedCategoryByTitleAndQuantity(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro odebrání další doporučené kategorie z dishList (stačí ho odkomentovat), ale lze to udělat
        // jen v případě, že před tím byla další doporučená kategorie "SALAD" přidána aktivováním předchozí zkušební
        // metody, jinak to bude hlásit chybu a kategorie se neodebere, ale program nespadne
        /*
        TestVoidsForMain.removeAddressedDishNextRecomendedCategoryByTitleAndQuantity(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro změnu názvu jídla v dishList (stačí ho odkomentovat), ale lze to udělat jen v případě, že
        // máte jako aktuální můj původní soubor DB-dish.txt, jinak to bude hlásit chybu a nic to neudělá, ale program
        // poběží dál
        /*
        TestVoidsForMain.renameDishTitleByTitleAndQuantity(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro přidání nového jídla z dishList se stejným názvem, ale s jinou hodnotou velikosti porce
        // a cenou (stačí ho odkomentovat), ale lze to udělat jen v případě, že že máte jako aktuální můj původní soubor
        // DB-dish.txt, jinak to bude hlásit chybu a nic to neudělá, ale program poběží dál
        /*
        TestVoidsForMain.addDishSameTitleWithDifferentQuantityAndPrice(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro změnu doporučeného množství jídla v dishList (stačí ho odkomentovat), ale lze to udělat jen
        // v případě, že máte jako aktuální můj původní soubor DB-dish.txt, jinak to bude hlásit chybu a nic to neudělá,
        // ale program poběží dál
        /*
        TestVoidsForMain.replaceDishRecommendedQuantityByTitleAndQuantity(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro změnu doporučené ceny jídla v dishList (stačí ho odkomentovat), ale lze to udělat jen
        // v případě, že máte jako aktuální můj původní soubor DB-dish.txt, jinak to bude hlásit chybu a nic to neudělá,
        // ale program poběží dál
        /*
        TestVoidsForMain.replaceDishRecommendedPriceByTitleAndQuantity(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro změnu předpokládané doby přípravy jídla v dishList (stačí ho odkomentovat), ale lze
        // to udělat jen v případě, že máte jako aktuální můj původní soubor DB-dish.txt, jinak to bude hlásit chybu
        // a nic to neudělá, ale program poběží dál
        /*
        TestVoidsForMain.replaceDishEstimatedPreparationTimeByTitleAndQuantity(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro změnu názvu souboru s hlavní fotografií v dishList (stačí ho odkomentovat), ale lze
        // to udělat jen v případě, že máte jako aktuální můj původní soubor DB-dish.txt, jinak to bude hlásit chybu
        // a nic to neudělá, ale program poběží dál
        /*
        TestVoidsForMain.replaceDishMainPhotoByTitleAndQuantity(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro přidání dalšího názvu souboru fotografie do dalších fotografií v dishList (stačí ho
        // odkomentovat), ale lze to udělat jen v případě, že máte jako aktuální můj původní soubor DB-dish.txt, jinak
        // to bude hlásit chybu a nic to neudělá, ale program poběží dál
        /*
        TestVoidsForMain.addDishNextPhotoByTitleAndQuantity(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro odebrání dalšího názvu souboru fotografie z dalších fotografií v dishList (stačí ho
        // odkomentovat), ale lze to udělat jen v případě, že jste před tím přidali další fotku v předchozím zkušebním
        // kód pro test přidání fotografie, jinak to bude hlásit chybu a nic to neudělá, ale program poběží dál
        /*
        TestVoidsForMain.removeAddressedDishNextPhotoByTitleAndQuantity(dishManager);
        printLnOutputs.printDishList(dishManager);
        */

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Dishs.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Pak je ale třeba tam vrátit ten první testovací, jinak nebudou správně fungovat další testovací kódy.


        //printLnOutputs.printMenuList(amManager);

        // Zkušební kód pro přidání jídla do aktuálního menu a jeho uložení do souboru (stačí ho odkomentovat)
        //TestVoidsForMain.testAddFoodToMenu(amManager, dishManager); printLnOutputs.printMenuList(amManager);

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

        // Výzvu č.3 plnit NEbudu, je to absolutní nesmysl v zadání, když u stolu bude sedět více hostů a každý bude
        // chtít platit zvlášť, tak by to byl velkej problém, navíc, každý z nich by měl dostat daňový doklad na to co
        // si vypil nebo snědl a ne i za kámoše, kterej sedí na pivu vedle něj. Když bude chtít, může, můj kód by tuto
        // situaci měl mít ošetřenu, aby mohl, zbytek si myslím, je k pracem na FrondEndu, ale nevím.









    }

}


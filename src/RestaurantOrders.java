import com.certifikace.projekt1.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.certifikace.projekt1.RestaurantSettings.*;

public class RestaurantOrders {
    public static void main(String[] args) throws RestaurantException {

        RestaurantPrintLnOutputsForMain printLnOutputs = new RestaurantPrintLnOutputsForMain();
        RestaurantLoadersVoidsForMain loadersVoids = new RestaurantLoadersVoidsForMain();
        RestaurantSaversVoidsForMain saversVoids = new RestaurantSaversVoidsForMain();
        FoodCategory foodCategory; foodCategory = FoodCategory.getInstance();
        TableManager tableManager = new TableManager();
        WaiterManager waiterManager = new WaiterManager();
        DishManager dishManager = new DishManager();

        loadersVoids.loadTablesData(tableManager);
        loadersVoids.loadWaitersData(waiterManager);
        try {
            // ... (další kód)
            dishManager.loadDataDishsFromFile(fileDishs(), delimiter());
            // ... (další kód)
        } catch (RestaurantException e) {
            System.err.println("Chyba: " + e.getMessage());
        }
        //loadersVoids.loadDishsData(dishManager);

        System.out.println(); System.out.println();
        System.out.println("Restaurant Chez Quis à Prague");


        printLnOutputs.printFoodCategoryList(foodCategory);

        // Zkušební kód pro přidání nové kategorie do foodCategory a uložení do souboru (stačí ho odkomentovat)
        //foodCategory.addCategory("NEWCATEGORY", "nová kategorie"); printLnOutputs.printFoodCategoryList(foodCategory);

        // Zkušební kód pro odebrání kategorie z foodCategory a uložení do souboru (stačí ho odkomentovat)
        // Jako příklad jsem odebral kategorii, kterou jsem přidal v předchozím testovacím kódu NEWCATEGORY,
        // takže se musí nejdříve přidat, jinak to vyhodí chybovou hlášku nebo se musí odebrat nějaká kategorie z již
        // existujících, které mám uloženy v testovacím TXT souboru, ale to bych nedělal! Simuluji třídu ENUM,
        // která má být statická, ale zároveň plním výzvu: "Možnost přidávat a odebírat kategorie jídel" a podmínku,
        // že po pádu systému se tento obnoví "bez ztráty kytičky". :-) Jinak by to mělo splňovat všechny podmínky
        // zadání, ale když se odebere nějaká kategorie z testovacího souboru TXT, bude to vyhazovat chybový hlášky,
        // protože je nutný pro další mé testovací data, jinak by to podle mě mělo být OK i v případě, že žádné
        // testovací TXT soubory nebudou existovat.
        //foodCategory.removeCategory("NEWCATEGORY"); printLnOutputs.printFoodCategoryList(foodCategory);

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-FoodCategories.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Program tento soubor vygeneruje, přidá do něj kategorii EMPTYCATEGORY a česky "prázdná kategorie"
        // Pak je ale třeba tam vrátit ten první testovací, jinak viz. výše.

        printLnOutputs.printTableList(tableManager);

        // Zkušební kód pro přidání stolu do tableList a jeho uložení do souboru (stačí ho odkomentovat)
        //TestVoidsForMain.createAndAddNewTable(tableManager); printLnOutputs.printTableList(tableManager);

        // Zkušební kód pro odebrání stolu z tableList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím byl stůl číslo 20 přidán aktivováním předchozí zkušební metody, jinak to bude hlásit chybu
        // a stůl se neodebere, ale program nespadne
        //TestVoidsForMain.removeTableByNumber(tableManager); printLnOutputs.printTableList(tableManager);

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Tables.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Program tento soubor vygeneruje, přidá do něj stůl číslo 99, kapacitu stolu na 999999
        // a všechny ostatní data stolu nastaví na null.


        printLnOutputs.printWaiterList(waiterManager);

        // Zkušební kód pro přidání číšníka do waiterList a uložení do souboru (stačí ho odkomentovat)
        //TestVoidsForMain.createAndAddNewWaiter(waiterManager); printLnOutputs.printWaiterList(waiterManager);

        // Zkušební kód pro odebrání číšníka z waiterList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím byl číšník s číslem 25 přidán aktivováním předchozí zkušební metody, jinak to bude hlásit chybu
        // a číšník se neodebere, ale program nespadne
        //TestVoidsForMain.removeWaiterByNumber(waiterManager); printLnOutputs.printWaiterList(waiterManager);

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Waiters.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Program tento soubor vygeneruje, přidá do něj číšníka číslo 999, typ PPV na EMPTY
        // a všechny ostatní data číšníka nastaví na null.
        // Pak je ale třeba tam vrátit ten první testovací, jinak viz. výše.

        printLnOutputs.printDishListDataFromFile(dishManager);

        // Zkušební kód pro přidání jídla do zásobníku a jeho uložení do souboru (stačí ho odkomentovat)
        //TestVoidsForMain.createAndAddNewDish(dishManager); printLnOutputs.printDishListDataFromFile(dishManager);

        // Zkušební kód pro odebrání jídla z dishList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím bylo jídlo "Katův šleh "Chez Quis à Prague" s doporučeným nožstvím 250 g přidáno aktivováním
        // předchozí zkušební metody, jinak to bude hlásit chybu a jídlo se neodebere, ale program nespadne
        /*
        TestVoidsForMain.removeDishByTitleAndQuantity(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);
        */



    }

}

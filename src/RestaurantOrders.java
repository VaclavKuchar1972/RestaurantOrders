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
        // zadání, ale když se odebere nějaká kategorie z testovacího souboru, bude to vyhazovat chybový hlášky,
        // protože je nutný pro další mé testovací data, jinak by to podle mě mělo být OK i v případě, že žádné
        // testovací TXT soubory nebudou existovat.
        //foodCategory.removeCategory("NEWCATEGORY"); printLnOutputs.printFoodCategoryList(foodCategory);

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-FoodCategories.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Program tento soubor vygeneruje, přidá do něj kategorii EMPTYCATEGORY a česky "prázdná kategorie"


        loadersVoids.loadTablesData(tableManager);
        printLnOutputs.printTableList(tableManager);

        // Zkušební kód pro přidání stolu do tableList a jeho uložení do souboru (stačí ho odkomentovat)
        //TestVoidsForMain.createAndAddNewTable(tableManager); printLnOutputs.printTableList(tableManager);

        // Zkušební kód pro odebrání stolu z tableList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím byl stůl číslo 20 přidán aktivováním předchozí zkušební metody, jinak to bude hlásit chybu
        //TestVoidsForMain.removeTableByNumber(tableManager); printLnOutputs.printTableList(tableManager);

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Tables.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Program tento soubor vygeneruje, přidá do něj stůl číslo 99, kapacitu stolu na 999999
        // a všechny ostatní data stolu nastaví na null.


        loadersVoids.loadWaitersData(waiterManager);
        printLnOutputs.printWaiterList(waiterManager);

        // Zkušební kód pro přidání číšníka do waiterList a uložení do souboru (stačí ho odkomentovat)
        //TestVoidsForMain.createAndAddNewWaiter(waiterManager); printLnOutputs.printWaiterList(waiterManager);

        // Zkušební kód pro odebrání číšníka z waiterList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím byl číšník s číslem 25 přidán aktivováním předchozí zkušební metody, jinak to zkolabuje a nemám
        // ošetřeny tyto eventuality - jde jen o to, aby bylo jasné, že číšníka lze odebrat.
        // Nebo odebrat třeba číšníka č.1.
        //waiterManager.removeWaiterByNumber(25); printLnOutputs.printWaiterList(waiterManager);
        //saversVoids.saveWaitersData(waiterManager);

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Waiters.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Program tento soubor vygeneruje, přidá do něj číšníka číslo 25 a všechny ostatní data číšníka nastaví
        // na null.


        loadersVoids.loadDishsData(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);

        // Zkušební kód pro přidání jídla do zásobníku a jeho uložení do souboru (stačí ho odkomentovat)
        // POZOR! Při zkoušce nahrazení prvního prázdného stolu ihned po vytvoření souboru DB-Dishs.txt samotným
        // programem, který je v podstatě prázdný a bude mít jen jednu položku je nutné výše zakomentovat kód
        // loadersVoids.loadDishsData(dishManager);, jinak se přirozeně vytvoří dva a nic se nenahradí, to je jen
        // důsledek toho, že si to zkoušim sám pro sebe, pro správný běh programu tento kód výše být tady vůbec nemusí
        //TestVoidsForMain.createAndAddNewDish(dishManager); printLnOutputs.printDishListDataFromFile(dishManager);



    }

}

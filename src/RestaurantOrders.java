import com.certifikace.projekt1.*;

import static com.certifikace.projekt1.RestaurantSettings.*;

public class RestaurantOrders {
    public static void main(String[] args) {

        System.out.println(); System.out.println();
        System.out.println("Restaurant Chez Quis à Prague");


        TableManager tableManager = new TableManager();
        WaiterManager waiterManager = new WaiterManager();
        DishManager dishManager = new DishManager();
        RestaurantPrintLnOutputsForMain printLnOutputs = new RestaurantPrintLnOutputsForMain();
        RestaurantLoadersVoidsForMain loadersVoids = new RestaurantLoadersVoidsForMain();
        RestaurantSaversVoidsForMain saversVoids = new RestaurantSaversVoidsForMain();


        loadersVoids.loadTablesData(tableManager);
        printLnOutputs.printTableList(tableManager);

        // Zkušební kód pro přidání stolu do tableList a jeho uložení do souboru (stačí ho odkomentovat)
        // POZOR! Při zkoušce nahrazení prvního prázdného stolu ihned po vytvoření souboru DB-Tables.txt samotným
        // programem, který je v podstatě prázdný a bude mít jen jednu položku je nutné výše zakomentovat kód
        // loadersVoids.loadTablesData(tableManager);, jinak se přirozeně vytvoří dva a nic se nenahradí, to je jen
        // důsledek toho, že si to zkoušim sám pro sebe, pro správný běh programu tento kód výše být tady vůbec nemusí
        /*
        try {
            tableManager.addTable(new Table(tableManager.getTableList().size() + 1, "SALONEK",
                    "A2", 2));
        }
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat nový stůl: " + e.getLocalizedMessage());
        }
        printLnOutputs.printTableList(tableManager);
        saversVoids.saveTablesData(tableManager);
        */
        // Zkušební kód pro odebrání stolu z tableList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím byl stůl číslo 20 přidán aktivováním předchozí zkušební metody, jinak to zkolabuje a nemám
        // ošetřeny tyto eventuality - jde jen o to, aby bylo jasné, že stůl lze odebrat. Nebo odebrat třeba stůl č.1.
        /*tableManager.removeTableByNumber(20);
        printLnOutputs.printTableList(tableManager);
        saversVoids.saveTablesData(tableManager);
        */

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Tables.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Program tento soubor vygeneruje, přidá do něj stůl číslo 1 a všechny ostatní data stolu nastaví na null.


        loadersVoids.loadWaitersData(waiterManager);
        printLnOutputs.printWaiterList(waiterManager);

        // Zkušební kód pro přidání číšníka do waiterList a uložení do souboru (stačí ho odkomentovat)
        // POZOR! Při zkoušce nahrazení prvního prázdného číšníka ihned po vytvoření souboru DB-Waiters.txt samotným
        // programem, který je v podstatě prázdný a bude mít jen jednu položku je nutné výše zakomentovat kód
        // loadersVoids.loadWaitersData(waiterManager);, jinak se přirozeně vytvoří dva a nic se nenahradí, to je jen
        // důsledek toho, že si to zkoušim sám pro sebe, pro správný běh programu tento kód výše být tady vůbec nemusí
        /*
        try {
            waiterManager.addWaiter(new Waiter(waiterManager.getWaiterList().size() + 1,
                    "", "Václav", "Kuchař", "",
                    "151237620", "HPP"));
        }
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat nového číšníka: " + e.getLocalizedMessage());
        }
        printLnOutputs.printWaiterListNoAbbreviationRelationship(waiterManager);
        saversVoids.saveWaitersData(waiterManager);
        */

        // Zkušební kód pro odebrání stolu z tableList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím byl číšník s číslem 25 přidán aktivováním předchozí zkušební metody, jinak to zkolabuje a nemám
        // ošetřeny tyto eventuality - jde jen o to, aby bylo jasné, že číšníka lze odebrat.
        // Nebo odebrat třeba číšníka č.1.
        /*
        waiterManager.removeWaiterByNumber(25);
        printLnOutputs.printWaiterList(waiterManager);
        saversVoids.saveWaitersData(waiterManager);
        */

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Waiters.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Program tento soubor vygeneruje, přidá do něj číšníka číslo 1 a všechny ostatní data číšníka nastaví na null.


        FoodCategory foodCategory = FoodCategory.getInstance();
        printLnOutputs.printFoodCategoryList(foodCategory);


        loadersVoids.loadDishsData(dishManager);
        printLnOutputs.printDishListDataFromFile(dishManager);


        //System.out.println(); System.out.println("Aktuální kategorie:");
        //for (FoodCategory category : FoodCategory.values()) {
        //    System.out.println(category.name() + " (" + category + ")");
        //}

        //FoodCategory.addCategory("NEWCATEGORY", "nová kategorie");
        //System.out.println(); System.out.println("Aktuální kategorie po přidání kategorie:");
        //for (FoodCategory category : FoodCategory.values()) {
        //    System.out.println(category.name() + " (" + category + ")");
        //}



    }

}

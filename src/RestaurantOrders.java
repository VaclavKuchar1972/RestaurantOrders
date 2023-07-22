import com.certifikace.projekt1.*;

import java.util.List;

import static com.certifikace.projekt1.RestaurantSettings.*;

public class RestaurantOrders {
    public static void main(String[] args) {

        System.out.println(); System.out.println();
        System.out.println("Restaurant Chez Quis à Prague");


        TableManager tableManager = new TableManager();
        WaiterManager waiterManager = new WaiterManager();
        RestaurantPrintLnOutputsForMain printLnOutputs = new RestaurantPrintLnOutputsForMain();
        RestaurantLoadersVoidsForMain loadersVoids = new RestaurantLoadersVoidsForMain();
        RestaurantSaversVoidsForMain saversVoids = new RestaurantSaversVoidsForMain();


        loadersVoids.loadTablesData(tableManager);
        printLnOutputs.printTableList(tableManager);

        // Zkušební kód pro přidání stolu do tableList a jeho uložení do souboru (stačí ho odkomentovat)
        /*try {
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
        // ošetřeny tyto eventuality - jde jen o to, aby bylo jasné, že stůl lze odebrat
        /*tableManager.removeTableByNumber(20);
        printLnOutputs.printTableList(tableManager);
        saversVoids.saveTablesData(tableManager);
        */

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Tables.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Program tento soubor vygeneruje, přidá do něj stůl číslo 1 a všechny ostatní data stolu nastaví na null.


        loadersVoids.loadWaitersData(waiterManager);
        printLnOutputs.printWaiterListNoAbbreviationRelationship(waiterManager);

        // Zkušební kód pro přidání číšníka do waiterList a uložení do souboru (stačí ho odkomentovat)
        /*try {
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
        // ošetřeny tyto eventuality - jde jen o to, aby bylo jasné, že stůl lze odebrat
        /*waiterManager.removeWaiterByNumber(25);
        printLnOutputs.printWaiterList(waiterManager);
        saversVoids.saveWaitersData(waiterManager);
        */

        // Zkušební kód pro ověření, že program nezkolabuje při prvním spuštění, když ještě nebude existovat
        // soubor DB-Waiters.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Program tento soubor vygeneruje, přidá do něj číšníka číslo 1 a všechny ostatní data číšníka nastaví na null.


    }

}

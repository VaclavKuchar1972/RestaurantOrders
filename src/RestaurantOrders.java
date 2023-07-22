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

        // Zkušební kód pro přidání stolu do tableList (stačí ho odkomentovat)
        /*try {
            tableManager.addTable(new Table(tableManager.getTableList().size() + 1, "SALONEK",
                    "A2", 2));
        }
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat nový stůl: " + e.getLocalizedMessage());
        }
        printLnOutputs.printTableList(tableManager);
        */


        // Zkušební kód pro odebrání stolu z tableList (stačí ho odkomentovat), ale lze to udělat jen v případě,
        // že před tím byl stůl číslo 20 přidán aktivováním předchozí zkušební metody, jinak to zkolabuje a nemám
        // ošetřeny tyto eventuality - jde jen o to, aby bylo jasné, že stůl lze odebrat
        /*tableManager.removeTableByNumber(20);
        saversVoids.saveTablesData(tableManager);
        printLnOutputs.printTableList(tableManager);
         */

        // Zkušební kód pro ověření, že program nezkolabuje při prvním zpuštění když ještě nebude existovat
        // soubor DB-Tables.txt není potřeba. Stačí z adresáře programu tento soubor smazat a spustit program.
        // Program tento soubor vygeneruje, přidá do něj stůl číslo 1 a všechny ostatní data stolu nastaví na null.


        loadersVoids.loadWaitersData(waiterManager);
        printLnOutputs.printWaiterListNoAbbreviationRelationship(waiterManager);




    }

}

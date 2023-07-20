import com.certifikace.projekt1.*;

import java.util.List;

import static com.certifikace.projekt1.RestaurantSettings.*;

public class RestaurantOrders {
    public static void main(String[] args) {

        System.out.println(); System.out.println();
        System.out.println("Restaurant Chez Quis à Prague");

        TableManager tableManager = new TableManager();
        RestaurantPrintLnOutputsForMain printLnOutputs = new RestaurantPrintLnOutputsForMain();
        RestaurantLoadersVoidsForMain loadersVoids = new RestaurantLoadersVoidsForMain();
        RestaurantSaversVoidsForMain saversVoids = new RestaurantSaversVoidsForMain();

        loadersVoids.loadTablesData(tableManager);
        printLnOutputs.printTableList(tableManager);


        // Zkušební kód pro přidání stolu do tableList (stačí ho odkomentovat)

        try {
            tableManager.addTable(new Table(tableManager.getTableList().size() + 1, "SALONEK",
                    "A2", 2));
        }
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se přidat nový stůl: " + e.getLocalizedMessage());
        }
        printLnOutputs.printTableList(tableManager);


        saversVoids.saveTablesData(tableManager);
        printLnOutputs.printTableList(tableManager);






    }

}

/*


        WaiterManager waiterManager = new WaiterManager();
        try {waiterManager.loadDataWaitersFromFile(RestaurantSettings.fileWaiters(), delimiter());}
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se načíst data ze souboru: " + RestaurantSettings.fileWaiters() + " "
                    + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        List<Waiter> waiterList = waiterManager.getWaiterList();
        System.out.println("Seznam zaměstnaných servírek a číšníků restaurace:");
        for (Waiter waiter : waiterList) {System.out.println(waiter.getWaiterInfo());}

         */
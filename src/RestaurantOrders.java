import com.certifikace.projekt1.*;

import java.util.List;

import static com.certifikace.projekt1.RestaurantSettings.delimiter;

public class RestaurantOrders {
    public static void main(String[] args) {



        System.out.println(); System.out.println();
        System.out.println("Restaurant Chez Quis à Prague");

        TableManager tableManager = new TableManager();
        try {tableManager.loadDataTablesFromFile(RestaurantSettings.fileTables(), delimiter());}
        catch (RestaurantException e) {
            System.err.println("Nepodařilo se načíst data ze souboru: " + RestaurantSettings.fileTables() + " "
                    + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        List<Table> tableList = tableManager.getTableList();
        System.out.println();
        System.out.println("Seznam stolů restaurace:");
        for (Table table : tableList) {System.out.println(table.getTableInfo());}

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

    }

}
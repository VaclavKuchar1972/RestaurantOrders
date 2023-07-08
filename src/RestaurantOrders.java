import com.certifikace.projekt1.RestaurantException;
import com.certifikace.projekt1.RestaurantSettings;
import com.certifikace.projekt1.Table;
import com.certifikace.projekt1.TableManager;

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

    }

}
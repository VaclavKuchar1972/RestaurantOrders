package com.certifikace.projekt1;

import java.util.List;

public class RestaurantPrintLnOutputsForMain {

    public void printTableList(TableManager tableManager) {
        List<Table> tableList = tableManager.getTableList();
        System.out.println();
        System.out.println("Seznam stolů restaurace:");
        for (Table table : tableList) {
            System.out.println(table.getTableInfoForTestPrint());
        }
    }

    public void printWaiterList(WaiterManager waiterManager) {
        List<Waiter> waiterList = waiterManager.getWaiterList();
        System.out.println();
        System.out.println("Seznam číšníků restaurace:");
        for (Waiter waiter : waiterList) {
            System.out.println(waiter.getWaiterInfoForTestPrint());
        }
    }

    public void printWaiterListNoAbbreviationRelationship(WaiterManager waiterManager) {
        List<Waiter> waiterList = waiterManager.getWaiterList();
        System.out.println();
        System.out.println("Seznam číšníků restaurace s pracovním vztahem v nezkrácené verzi:");
        for (Waiter waiter : waiterList) {
            System.out.println(waiter.getWaiterInfoForPrintMeNoAbbreviationRelationship()
                    + "   Typ pracovně právního vztahu: "
                    + waiter.getWaiterTypeOfEmploymentRelationshipNoAbbreviation());
        }
    }



}

package com.certifikace.projekt1;

import java.util.List;

public class RestaurantPrintLnOutputsForMain {

    public void printTableList(TableManager tableManager) {
        List<Table> tableList = tableManager.getTableList();
        System.out.println();
        System.out.println("Seznam stolů restaurace:");
        for (Table table : tableList) {
            System.out.println(table.getTableInfoForPrintMe());
        }
    }

}

package com.certifikace.projekt1;

import java.util.List;
import java.util.Map;

public class RestaurantPrintLnOutputsForMain {

    public void printTableList(TableManager tableManager) {
        List<Table> tableList = tableManager.getTableList();
        System.out.println();
        System.out.println("Seznam stolů restaurace:");
        for (Table table : tableList) {System.out.println(table.getTableInfoForTestPrint());}
    }
    public void printWaiterList(WaiterManager waiterManager) {
        List<Waiter> waiterList = waiterManager.getWaiterList();
        System.out.println();
        System.out.println("Seznam číšníků restaurace s pracovním vztahem v nezkrácené verzi:");
        for (Waiter waiter : waiterList) {
            System.out.println(waiter.getWaiterInfoForPrintMeNoAbbreviationRelationship()
                    + "   Typ pracovně právního vztahu: "
                    + waiter.getWaiterTypeOfEmploymentRelationshipNoAbbreviation());
        }
    }
    public void printFoodCategoryList(FoodCategory foodCategory) {
        System.out.println();
        System.out.println("Seznam kategorií jídel:");
        for (FoodCategory category : foodCategory.getAllCategories()) {
            System.out.println(category.getName() + ", " + category.getDescription());
        }
    }
    public void printDishListDataFromFile(DishManager dishManager) {
        List<Dish> dishList = dishManager.getDishList();
        System.out.println();
        System.out.println("Výpis dat nečtených ze souboru DB-Dishs.txt:");
        for (Dish dish : dishList) {System.out.println(dish.getDishInfoForTestPrint());}
    }

}

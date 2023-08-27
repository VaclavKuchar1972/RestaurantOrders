package com.certifikace.projekt1;

import java.util.List;
import java.util.Map;

public class RestaurantPrintLnOutputsForMain {

    // Celá tato třída také není nutná pro správný chod programu, je tu jen kvůli testování.
    // Úplně také neplatí, že se jedná přímo o výpisy z těch TXT souborů, ale vzhledem k tomu, že to mám koncipované
    // tak, že po každé změně tyto soubory aktualizují (přeuloží) v testovacích metodách v testovací třídě, tak to
    // vlastně tak je, maslím ale, že to by si měli zajištovat také na FrontEdndu, aby to tak bylo, proto jsem to volání
    // ukládání nedával přímo do příslušných metod příslušných "managerů", myslím, že to by nebylo v pořádku, ale nevím.

    public void printTableList(TableManager tableManager) {
        List<Table> tableList = tableManager.getTableList();
        System.out.println();
        System.out.println("Seznam stolů restaurace:");
        for (Table table : tableList) {System.out.println(table.getTableInfoForTestPrint());}
    }

    public void printWaiterList(WaiterManager waiterManager) {
        List<Waiter> waiterList = waiterManager.getWaiterList();
        System.out.println(); System.out.println("Seznam číšníků restaurace:");
        for (Waiter waiter : waiterList) {System.out.println(waiter.getWaiterInfoForTestPrint());}
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

    public void printMenuListDataFromFile(ActualMenuManager amManager) {
        List<ActualMenu> amList = amManager.getAmList();
        System.out.println();
        System.out.println("Výpis dat nečtených ze souboru DB-ActualMenu.txt:");
        for (ActualMenu actualMenu : amList) {System.out.println(actualMenu.getActualMenuInfoForTestPrint());}
    }




}

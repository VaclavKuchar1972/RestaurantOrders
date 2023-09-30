package com.certifikace.projekt1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    // "unconfirmedOrderList" je list, kde jsou rozpracované objednávky všech číšníků, které ještě nebyli úplně
    // dohodnuty s hosty u jednotlivých stolů. Je to něco jako nákupní košík v internetovém obchodě, kdy můžete ještě
    // jednotlivé položky mazat, přidávat nové nebo měnit počet objednaného zboží, případně smazat vše a nic neobjednat
    private List<Order> unconfirmedOrdersList;
    private List<Order> receivedOrdersList;

    // Do tohoto lisu se dostanou až skutečně zaplacené objednávky, když někdo uteče bez placení, to by tam být nemělo.
    // Umím si představit i situaci, kdy host jednoduše nemá na zaplacení všeho co si naobjednal, tak zaplatí jen část
    // a zbytek zůstane dlužit a zaplatí jindy nebo se to bude řešit jinak, ale do účtárny by to jít nemělo dokud
    // to není obrat a toto není, je to ztráta až do uhrazení
    private List<Order> closedOrdersList;

    public OrderManager() {
        this.unconfirmedOrdersList = new ArrayList<>();
        this.receivedOrdersList = new ArrayList<>();
        this.closedOrdersList = new ArrayList<>();
    }

    public void addFoodToUnconfirmedOrdersByTitleAndQuantity(
            String titleSelect, int quantitySelect, ActualMenuManager amManager,
            int waiterNumber, int tableNumber, int unitsNumber, String noteForKitchen, String noteForManagement,
            WaiterManager waiterManager, TableManager tableManager)

            throws RestaurantException {

        boolean waiterExists = false;
        for(Waiter waiter : waiterManager.getWaiterList()) {
            if(waiter.getWaiterNumber() == waiterNumber) {waiterExists = true; break;}
        }
        if(!waiterExists) {
            System.err.println("Chyba: Číšník s číslem " + waiterNumber + " neexistuje ve WaiterList. Položka NEBYLA"
                    + " přidána do unconfirmedOrdersList.");
            return;
        }

        boolean tableExists = false;
        for(Table table : tableManager.getTableList()) {
            if(table.getTableNumber() == tableNumber) {tableExists = true; break;}
        }
        if(!tableExists) {
            System.err.println("Chyba: Stůl s číslem " + tableNumber + " neexistuje v TableList. Položka NEBYLA"
                    + " přidána do unconfirmedOrdersList.");
            return;
        }

        if (unitsNumber <= 0) {
            System.err.println("Chyba: Pokoušíte se objednat položku, která má zápornou nebo nulovou hodnotu počtu "
                    + "objednávaných jednotek:" + unitsNumber + " Položka NEBYLA přidána do unconfirmedOrdersList.");
            return;
        }

        for (ActualMenu actualMenu : amManager.getAmList()) {
            if (actualMenu.getAmTitle().equals(titleSelect) && actualMenu.getAmQuantity() >= quantitySelect) {
                Order newOrder = new Order(
                        0, // toto musím nastavit hned, ale až otestuju, že to takto vůbec funguje!!!
                        null, //LocalDate.now(),
                        null, //LocalDateTime.now(),
                        null,  // orderTimeIssue nastavím později, až se objednané jídlo přinese do receivedOrdersList
                        waiterNumber,
                        tableNumber,
                        actualMenu.getTitleForOrder(),
                        unitsNumber,
                        actualMenu.getAmPrice(),
                        unitsNumber,
                        noteForKitchen,
                        noteForManagement,
                        OrderCategory.UNCONFIRMED,
                        actualMenu.getAmMainCategory()
                );
                unconfirmedOrdersList.add(newOrder);
                return;
            }
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + titleSelect + " a množstvím " + quantitySelect
                + " nebylo nalezeno v amList. Objednané jídlo NEBYLO přidáno do orderList.");
    }

    public List<Order> getOrderList() {return new ArrayList<>(unconfirmedOrdersList);}



}

package com.certifikace.projekt1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private List<Order> orderList;
    private List<Order> orderListForAccountingOffice;
    public OrderManager() {this.orderList = new ArrayList<>(); this.orderListForAccountingOffice = new ArrayList<>();}



    // MUSÍM JEŠTĚ PŘIDAT CHYBOVÉ HLÁŠKY, neexistující stůl, číšník,
    public void addFoodToUnconfirmedOrderByTitleAndQuantity(
            String titleSelect, int quantitySelect, ActualMenuManager amManager,
            int waiterNumber, int tableNumber, int unitsNumber, String noteForKitchen, String noteForManagement,
            OrderCategory orderCategory
    )
            throws RestaurantException {
        for (ActualMenu actualMenu : amManager.getAmList()) {
            if (actualMenu.getAmTitle().equals(titleSelect) && actualMenu.getAmQuantity() >= quantitySelect) {
                Order newOrder = new Order(
                        0, // toto musím nastavit hned, ale až otestuju, že to takto vůbec funguje!!!
                        LocalDate.now(),
                        LocalDateTime.now(),
                        null,  // orderTimeIssue nastavím později, až se objednané jídlo přinese na stůl
                        waiterNumber,
                        tableNumber,
                        actualMenu.getTitleForOrder(),
                        unitsNumber,
                        actualMenu.getAmPrice(),
                        unitsNumber,
                        noteForKitchen,
                        noteForManagement,
                        orderCategory,
                        actualMenu.getAmMainCategory()
                );
                orderList.add(newOrder);
                return;
            }
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + titleSelect + " a množstvím " + quantitySelect
                + " nebylo nalezeno v amList. Objednané jídlo NEBYLO přidáno do orderList.");
    }




    public List<Order> getOrderList() {return new ArrayList<>(orderList);}



}

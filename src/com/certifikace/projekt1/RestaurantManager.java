package com.certifikace.projekt1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RestaurantManager {
    private OrderManager orderManager;

    public RestaurantManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public Integer getNumberOfReceivedOrders() {
        List<Order> confirmedOrders = orderManager.getConfirmedItemsList();
        if (confirmedOrders != null) {return confirmedOrders.size();}
        return 0;
    }

    public List<Order> getSortedOrdersByWaiterNumber() {
        List<Order> confirmedOrders = orderManager.getConfirmedItemsList();
        if (confirmedOrders == null) {return new ArrayList<>();}
        List<Order> sortedOrders = new ArrayList<>(confirmedOrders);
        Collections.sort(sortedOrders, new Comparator<Order>() {
            @Override
            public int compare(Order orderStart, Order orderTarget) {
                return Integer.compare(orderStart.getOrderWaiterNumber(), orderTarget.getOrderWaiterNumber());
            }
        });
        return sortedOrders;
    }






}

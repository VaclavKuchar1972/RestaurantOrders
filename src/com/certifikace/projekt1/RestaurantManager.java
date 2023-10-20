package com.certifikace.projekt1;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> getSortedOrdersByWaiterNumberOfConfirmedOrders() {
        return orderManager.getConfirmedItemsList().stream()
                .sorted(Comparator.comparing(Order::getOrderWaiterNumber))
                .map(Order::getAccordingToTheProjectSpecificationPrints)  // Tady převedeme každou objednávku na její krátký popis.
                .collect(Collectors.toList());
    }







}

package com.certifikace.projekt1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class RestaurantManager {
    private OrderManager orderManager;

    public RestaurantManager(OrderManager orderManager) {this.orderManager = orderManager;}

    public Integer getNumberOfReceivedOrders() {
        List<Order> confirmedOrders = orderManager.getConfirmedItemsList();
        if (confirmedOrders != null) {return confirmedOrders.size();}
        return 0;
    }

    public List<String> getSortedOrdersByWaiterNumberOfConfirmedOrders() {
        return orderManager.getConfirmedItemsList().stream()
                .sorted(Comparator.comparing(Order::getOrderWaiterNumber))
                .map(Order::getAccordingToTheProjectSpecificationPrints)  // Poznámka pro mě: Tady převádím každou
                // objednávku na její výstup na obrazovku tak jak byl v zadání
                .collect(Collectors.toList());
    }

    public List<String> getSortedOrdersByTimeReceiptOfConfirmedOrders() {
        return orderManager.getConfirmedItemsList().stream()
                .sorted(Comparator.comparing(Order::getOrderTimeReceipt))
                .map(Order::getAccordingToTheProjectSpecificationPrints)
                .collect(Collectors.toList());
    }

    public List<String> getSortedAllActualOrdersByWaiterAndTurnover() {
        List<Order> confirmedOrders = orderManager.getConfirmedItemsList();
        return calculateWaiterOrdersAndTurnover(confirmedOrders);
    }
    public List<String> getSortedAllClosedOrdersByWaiterAndTurnover() {
        List<Order> closedOrders = orderManager.getClosedOrdersList();
        return calculateWaiterOrdersAndTurnover(closedOrders);
    }
    private List<String> calculateWaiterOrdersAndTurnover(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {return new ArrayList<>();}
        Map<Integer, BigDecimal> waiterTurnoverMap = new HashMap<>();
        for (Order order : orders) {
            int waiterNumber = order.getOrderWaiterNumber();
            BigDecimal orderValue = order.getOrderValue();
            if (waiterTurnoverMap.containsKey(waiterNumber)) {
                BigDecimal currentTurnover = waiterTurnoverMap.get(waiterNumber);
                BigDecimal newTurnover = currentTurnover.add(orderValue);
                waiterTurnoverMap.put(waiterNumber, newTurnover);
            }
            else {waiterTurnoverMap.put(waiterNumber, orderValue);}
        }
        return waiterTurnoverMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> "Číšník č. " + entry.getKey() + "  Počet objednávek: "
                        + getWaiterOrderCount(entry.getKey(), orders) + "  Celková cena objednávek: "
                        + entry.getValue() + " Kč")
                .collect(Collectors.toList());
    }
    private int getWaiterOrderCount(int waiterNumber, List<Order> orders) {
        return (int) orders.stream()
                .filter(order -> order.getOrderWaiterNumber() == waiterNumber)
                .count();
    }


    public Integer getAverageProcessingTimeInTheSpecifiedTimePeriod
                (LocalDateTime startOfPeriod, LocalDateTime endOfPeriod) {
        List<Order> receivedOrders = orderManager.getConfirmedItemsList();
        List<Order> closedOrders = orderManager.getClosedOrdersList();
        List<Order> allItemsAndOrders = new ArrayList<>();
        allItemsAndOrders.addAll(receivedOrders.stream()
                .filter(order -> order.getOrderTimeIssue() != null)
                .collect(Collectors.toList()));
        for (Order closedOrder : closedOrders) {
            int orderItemNumber = closedOrder.getOrderItemNumber();
            boolean found = false;
            for (Order item : allItemsAndOrders) {
                if (item.getOrderItemNumber() == orderItemNumber
                        && item.getOrderTimeReceipt().equals(closedOrder.getOrderTimeReceipt())) {
                    item.setOrderTimeIssue(closedOrder.getOrderTimeIssue());
                    found = true;
                    break;
                }
            }
            if (!found) {allItemsAndOrders.add(closedOrder);}
        }
        allItemsAndOrders = allItemsAndOrders.stream()
                .filter(order -> order.getOrderTimeReceipt().isAfter(startOfPeriod)
                        && order.getOrderTimeReceipt().isBefore(endOfPeriod))
                .collect(Collectors.toList());
        double totalProcessingTime = 0;
        int itemCount = allItemsAndOrders.size();
        for (Order item : allItemsAndOrders) {
            int processingTime = item.getOrderProcessingTimeInMinutesByItem();
            if (processingTime >= 0) {totalProcessingTime += processingTime;}
        }
        double averageProcessingTimeDouble = (itemCount > 0) ? totalProcessingTime / itemCount : 0;
        int averageProcessingTime = (int) Math.round(averageProcessingTimeDouble);
        return averageProcessingTime;
    }


}

package com.certifikace.projekt1;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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




    ///// HLAVA JIŽ NEdává, nechám to na neděli....
    public List<Map.Entry<Integer, BigDecimal>> getTotalWaiterTurnover() {
        List<Order> allOrders = allConfirmedItemsAndClosedOrdersList();
        Map<Integer, BigDecimal> waiterTurnover = allOrders.stream()
                .collect(Collectors.groupingBy(
                        Order::getOrderWaiterNumber,
                        Collectors.mapping(Order::getOrderValue, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));
        // Seřadím seznam podle hodnoty obratu od největšího k nejmenšímu a vrátím ho na výstup
        return waiterTurnover.entrySet().stream()
                .sorted(Map.Entry.<Integer, BigDecimal>comparingByValue().reversed())
                .collect(Collectors.toList());
    }







    // VŠECHNY poznámky v následují metodě jsou pro jen pro mě, netýkají se plnění úkolu, jen abych věděl já...
    // ...nestíhám :-(
    private List<Order> allConfirmedItemsAndClosedOrdersList() {
        List<Order> confirmedItemsList = orderManager.getConfirmedItemsList();
        List<Order> closedOrdersList = orderManager.getClosedOrdersList();
        return Stream.concat(confirmedItemsList.stream(), closedOrdersList.stream())
                .collect(Collectors.toMap(
                        Order::getOrderItemNumber,   // klíč je orderItemNumber (dvě :: = operátory metody. Používají
                        // se k odkazování na metody (nebo konstruktory) bez jejich vyvolání. Toto je užitečné zejména
                        // v kontextu funkčního programování v Javě, zejména když pracujete se streamy a lambdami!!!!
                        order -> order,             // hodnota je samotný "objekt" objednávky (Order) - TOTO si musím
                        // ještě prostudovat, to je moc i na mě... !!!!!!!!!!!
                        (existingOrder, newOrder) -> newOrder))  // pokud se orderItemNumber shoduje, chci použít
                // objednávku z closedOrdersList - nevím jak to funguje v objektovým programování, pro mě je to
                // pojistka, aby program nekolidoval, když se odehrají dvě události v jednu chvíli najednou, ale i
                // multitasking by měl podle mě fungovat v nějákým časovým pořadníku, takže možná zbytečný, ALE!!!
                // ...nevím, prostě kdyby ke shodě orderItemNumber došlo, použije to řádek LISTu z uzavřených a jdu
                // od toho...
                .values()
                .stream()
                .collect(Collectors.toList());

        // Toto mi odpověděl ČaťákBoťákGPT4 na mých hodně nejsností ve fungování sysému JAVA v kontextu na OS:
        // Ano, nyní jsou vaše komentáře k allConfirmedItemsAndClosedOrdersList přesnější a vystihují, co kód dělá.
        // Vaše poznámky o objektovém programování a multitaskingu jsou zajímavé!!! Pokud jde o tento kód, věc, kterou
        // zde děláte, je slučování dvou seznamů (to vím :D) objednávek a v případě kolize (když dvě objednávky mají
        // stejné orderItemNumber), dáváte přednost objednávce z closedOrdersList před objednávkou z confirmedItemsList.
        // !!! Pokud jde o multitasking a časové pořadníky, to jsou koncepty na úrovni operačního systému a jazyka Java,
        // které umožňují paralelní a současný běh kódu. Ale v kontextu této metody, jednoduše řešíte potenciální kolizi
        // mezi dvěma seznamy objednávek. Pokud máte další otázky nebo nejasnosti o tom, jak kód funguje, rád vám s nimi
        // pomohu!
    }




}

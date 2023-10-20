package com.certifikace.projekt1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {

    private int orderNumber;
    // V zadání není datum objednávky, ale s objednávkou bez datumu by nás účtárna TUTOVĚ poslala MINIMÁLNĚ k šípku :D
    private LocalDateTime orderDateTimeClosing;
    private int orderItemNumber;
    private LocalDateTime orderTimeReceipt;
    private LocalDateTime orderTimeIssue;
    private int orderWaiterNumber;
    private int orderTableNumber;
    private String orderTitle;
    private int orderNumberOfUnits;
    private BigDecimal orderPriceOfUnits;
    private String orderNoteForKitchen;
    private String orderNoteForManagement;
    private OrderCategory orderCategory;
    private FoodCategory orderFoodMainCategory;

    public Order(int orderNumber, LocalDateTime orderDateTimeClosing, int orderItemNumber,
                 LocalDateTime orderTimeReceipt, LocalDateTime orderTimeIssue, int orderWaiterNumber,
                 int orderTableNumber, String orderTitle, int orderNumberOfUnits, BigDecimal orderPriceOfUnits,
                 String orderNoteForKitchen, String orderNoteForManagement, OrderCategory orderCategory,
                 FoodCategory orderFoodMainCategory) {
        this.orderNumber = orderNumber;
        this.orderDateTimeClosing = orderDateTimeClosing;
        this.orderItemNumber = orderItemNumber;
        this.orderTimeReceipt = orderTimeReceipt;
        this.orderTimeIssue = orderTimeIssue;
        this.orderWaiterNumber = orderWaiterNumber;
        this.orderTableNumber = orderTableNumber;
        this.orderTitle = orderTitle;
        this.orderNumberOfUnits = orderNumberOfUnits;
        this.orderPriceOfUnits = orderPriceOfUnits;
        this.orderNoteForKitchen = orderNoteForKitchen;
        this.orderNoteForManagement = orderNoteForManagement;
        this.orderCategory = orderCategory;
        this.orderFoodMainCategory = orderFoodMainCategory;
    }

    public String getOrderInfoForTestPrint() {
        String helpWaiterString = "";
        if (orderWaiterNumber < 100) {helpWaiterString = " ";}
        if (orderWaiterNumber < 10) {helpWaiterString = "  ";}
        String helpTableString = "";
        if (orderTableNumber < 10) {helpTableString = " ";}
        return orderNumber + ", " + orderDateTimeClosing + ", " + orderItemNumber + ", " + orderTimeReceipt + ", "
                + orderTimeIssue + ", " + helpWaiterString + orderWaiterNumber + ", " + helpTableString
                + orderTableNumber + ", " + orderTitle + ", " + orderNumberOfUnits + ", " + orderPriceOfUnits
                + ",- Kč, " + orderNoteForKitchen + ", " + orderNoteForManagement + ", " + orderCategory + ", "
                + orderFoodMainCategory;
    }

    // PLNĚNÍ - RestaurantManager - BOD6 - Export seznamu objednávek ve správném formátu
    public String getAccordingToTheProjectSpecificationPrints() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedOrderItemNumber = String.format("%09d", orderItemNumber);
        String formattedOrderWaiterNumber = String.format("%3s", orderWaiterNumber);
        String formattedOrderTableNumber = String.format("%2s", orderTableNumber);
        BigDecimal totalOrderPrice = orderPriceOfUnits.multiply(BigDecimal.valueOf(orderNumberOfUnits));
        String formattedOrderTimeReceipt = orderTimeReceipt.format(timeFormatter);
        String formattedOrderTimeIssue = (orderTimeIssue != null) ? orderTimeIssue.format(timeFormatter)
                : "objednávka ještě nebyla klientovi dodána na stůl"; // poznámka pro mě:  ? = hodnota, když výraz je
        // true : hodnota -  když výraz je false
        return formattedOrderItemNumber + ". " + orderTitle + " " + orderNumberOfUnits + " ("
                + totalOrderPrice + " Kč):\t" + formattedOrderTimeReceipt + "-" + formattedOrderTimeIssue
                + "\t\"číšník č. " + formattedOrderWaiterNumber;
    }

    public BigDecimal getOrderValue() {
        return orderPriceOfUnits.multiply(BigDecimal.valueOf(orderNumberOfUnits));
    }

    public int getOrderNumber() {return orderNumber;}
    public void setOrderNumber(int orderNumber) {this.orderNumber = orderNumber;}
    public LocalDateTime getOrderDateTimeClosing() {return orderDateTimeClosing;}
    public void setOrderDateTimeClosing(LocalDateTime orderDateTimeClosing) {
        this.orderDateTimeClosing = orderDateTimeClosing;
    }
    public int getOrderItemNumber() {return orderItemNumber;}
    public void setOrderItemNumber(int orderItemNumber) {this.orderItemNumber = orderItemNumber;}
    public LocalDateTime getOrderTimeReceipt() {return orderTimeReceipt;}
    public void setOrderTimeReceipt(LocalDateTime orderTimeReceipt) {this.orderTimeReceipt = orderTimeReceipt;}
    public LocalDateTime getOrderTimeIssue() {return orderTimeIssue;}
    public void setOrderTimeIssue(LocalDateTime orderTimeIssue) {this.orderTimeIssue = orderTimeIssue;}
    public int getOrderWaiterNumber() {return orderWaiterNumber;}
    public void setOrderWaiterNumber(int orderWaiterNumber) {this.orderWaiterNumber = orderWaiterNumber;}
    public int getOrderTableNumber() {return orderTableNumber;}
    public void setOrderTableNumber(int orderTableNumber) {this.orderTableNumber = orderTableNumber;}
    public String getOrderTitle() {return orderTitle;}
    public void setOrderTitle(String orderTitle) {this.orderTitle = orderTitle;}
    public int getOrderNumberOfUnits() {return orderNumberOfUnits;}
    public void setOrderNumberOfUnits(int orderNumberOfUnits) {this.orderNumberOfUnits = orderNumberOfUnits;}
    public BigDecimal getOrderPriceOfUnits() {return orderPriceOfUnits;}
    public void setOrderPriceOfUnits(BigDecimal orderPriceOfUnits) {this.orderPriceOfUnits = orderPriceOfUnits;}
    public String getOrderNoteForKitchen() {return orderNoteForKitchen;}
    public void setOrderNoteForKitchen(String orderNoteForKitchen) {this.orderNoteForKitchen = orderNoteForKitchen;}
    public String getOrderNoteForManagement() {return orderNoteForManagement;}
    public void setOrderNoteForManagement(String orderNoteForManagement) {
        this.orderNoteForManagement = orderNoteForManagement;
    }
    public OrderCategory getOrderCategory() {return orderCategory;}
    public void setOrderCategory(OrderCategory orderCategory) {this.orderCategory = orderCategory;}
    public FoodCategory getOrderFoodMainCategory() {return orderFoodMainCategory;}
    public void setOrderFoodMainCategory(FoodCategory orderFoodMainCategory) {
        this.orderFoodMainCategory = orderFoodMainCategory;
    }

    @Override
    public String toString() {
        return getOrderNumber() + ", " + getOrderDateTimeClosing() + ", " + getOrderItemNumber() + ", "
                + getOrderTimeReceipt() + ", " + getOrderTimeIssue() + ", " + getOrderWaiterNumber() + ", "
                + getOrderTableNumber() + ", " + getOrderTitle() + ", " + getOrderNumberOfUnits() + ", "
                + getOrderPriceOfUnits() + ", " + getOrderNoteForKitchen() + ", " + getOrderNoteForManagement() + ", "
                + getOrderCategory() + ", " + getOrderFoodMainCategory();
    }

}


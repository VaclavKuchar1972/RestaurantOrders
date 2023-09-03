package com.certifikace.projekt1;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {

    // V zadání není datum objednávky, ale s objednávkou bez datumu by nás účtárna TUTOVĚ poslala MINIMÁLNĚ k šípku :D
    private LocalDate orderDate;
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






    public Order(LocalDate orderDate, LocalDateTime orderTimeReceipt, LocalDateTime orderTimeIssue,
                 int orderWaiterNumber, int orderTableNumber, String orderTitle, int orderNumberOfUnits,
                 BigDecimal orderPriceOfUnits, String orderNoteForKitchen, String orderNoteForManagement,
                 OrderCategory orderCategory) {
        this.orderDate = orderDate;
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
    }

    public LocalDate getOrderDate() {return orderDate;}
    public void setOrderDate(LocalDate orderDate) {this.orderDate = orderDate;}
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




}

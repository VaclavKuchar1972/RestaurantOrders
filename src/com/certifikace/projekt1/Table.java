package com.certifikace.projekt1;

import java.time.format.DateTimeFormatter;

public class Table {

    private int tableNumber;
    private String tableLocation;
    private String tableSector;
    private int tableCapacity;

    public Table(int tableNumber, String tableLocation, String tableSector, int tableCapacity) {
        this.tableNumber = tableNumber;
        this.tableLocation = tableLocation;
        this.tableSector = tableSector;
        this.tableCapacity = tableCapacity;
    }

    public String getTableInfo () {
        // OŠETŘENÍ - Vložení mezery před číslo stolu, je-li jednociferné
        String helpString = ""; if (tableNumber < 10) {helpString = " ";}
        return "Číslo stolu: " + helpString + tableNumber + "   Umístění stolu: " + tableLocation
                + "   Sektor umístění stolu pro grafický výstup na FrontEndu: " + tableSector +
                "   Počet míst u stolu: " + tableCapacity;
    }

    public int getTableNumber() {return tableNumber;}
    public void setTableNumber(int tableNumber) throws RestaurantException {
        // OŠETŘENÍ - Číslo stolu musí být maximálně dvouciferné
        if (tableNumber < 0) {
            throw new RestaurantException("Chyba - počet stolů je menší než 1");
        }
        if (tableNumber > 99) {
            throw new RestaurantException("Chyba - počet stolů je větší než 99");
        }
        this.tableNumber = tableNumber;
    }
    public String getTableLocation() {return tableLocation;}
    public void setTableLocation(String tableLocation) {this.tableLocation = tableLocation;}
    public String getTableSector() {return tableSector;}
    public void setTableSector(String tableSector) {this.tableSector = tableSector;}
    public int getTableCapacity() {return tableCapacity;}
    public void setTableCapacity(int tableCapacity) {this.tableCapacity = tableCapacity;}

}
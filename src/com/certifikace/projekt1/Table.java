package com.certifikace.projekt1;

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

    public String getTableInfoForTestPrint() {
        // OŠETŘENÍ - Vložení mezery před číslo stolu, je-li jednociferné
        String helpString = ""; if (tableNumber < 10) {helpString = " ";}
        return "Číslo stolu: " + helpString + tableNumber + "   Umístění stolu: " + tableLocation.toString()
                + "   Sektor umístění stolu pro grafický výstup na FrontEndu: " + tableSector +
                "   Počet míst u stolu: " + tableCapacity;
    }

    public int getTableNumber() {return tableNumber;}
    public void setTableNumber(int tableNumber) throws RestaurantException {
        // OŠETŘENÍ - Číslo stolu musí být maximálně dvouciferné a nesmí být nulové nebo záporné
        if (tableNumber < 1) {
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
    public void setTableSector(String tableSector) throws RestaurantException {
        // OŠETŘENÍ - pro výstup pro grafický FrontEnd je nutné, aby String byl dvoumístný,
        // na prvním místě měl písmeno a na druhém číslo
        // - vykřičník invertuje (neguje) hodnotu podmínky
        if (tableSector.length() > 2 || (!Character.isLetter(tableSector.charAt(0))
                || !Character.isDigit(tableSector.charAt(1)))) {
            throw new RestaurantException("Chyba - neplatný formát sektoru stolu: " + tableSector);
        }
        this.tableSector = tableSector;
    }
    public int getTableCapacity() throws RestaurantException {return tableCapacity;}
    public void setTableCapacity(int tableCapacity) throws RestaurantException {
        if (tableCapacity < 1) {
            throw new RestaurantException("Chyba - počet míst u stolu je menší než 1");
        }
        this.tableCapacity = tableCapacity;}

}
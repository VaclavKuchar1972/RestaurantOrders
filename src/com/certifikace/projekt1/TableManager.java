package com.certifikace.projekt1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TableManager {

    private List<Table> tableList;
    public TableManager() {this.tableList = new ArrayList<>();}

    public void addTable(Table table) throws RestaurantException {
        // OŠETŔENÍ - Počet stolů musí být dvoumístný - nelze přidat více stolů nad počet 99
        if (tableList.size() > 98) {
            throw new RestaurantException("Chyba - Nelze přidat stůl. Byl dosažen maximální počet stolů.");
        }
        // OŠETŘENÍ - Nový stůl nesmí být umístěn ve stejné místnosti na stejném místě, kde již jeden stůl stojí
        for (Table existingTable : tableList) {
            if (existingTable.getTableLocation().equals(table.getTableLocation()) &&
                    existingTable.getTableSector().equals(table.getTableSector())) {
                throw new RestaurantException("Chyba - Nelze přidat stůl ve stejné místnosti na stejné místo,"
                        +"kde již jedn stůl stojí.");
            }
        }
        tableList.add(table);
    }
    public void removeTable(Table table) {tableList.remove(table);}


    public void loadDataTablesFromFile(String fileTables, String delimiter) throws RestaurantException {
        String line = "";
        String[] item = new String[0];
        int tableNumber; String tableLocation; String tableSector; int tableCapacity;
        int helpBadFormatIdentifokator = 0;
        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileTables)))) {
            while (scannerLoadData.hasNextLine()) {
                line = scannerLoadData.nextLine();
                // Oddělení jednotlivých dat stažených ze souboru - nastavil jsem si "; "
                item = line.split(delimiter);
                if (item.length != 4) {
                    throw new RestaurantException("Chyba - špatný počet položek na řádku: " + line);
                }
                tableNumber = Integer.parseInt(item[0]);
                helpBadFormatIdentifokator = 1;
                tableLocation = item[1];
                tableSector = item [2];
                tableCapacity = Integer.parseInt(item [3]);
                Table newTable = new Table(tableNumber, tableLocation, tableSector, tableCapacity);
                tableList.add(newTable);
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileTables + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Chyba - v databázi není číslo: " + item[helpBadFormatIdentifokator]
                    + " na řádku: " + line);
        }
    }

    public List<Table> getTableList() {return new ArrayList<>(tableList);}

}

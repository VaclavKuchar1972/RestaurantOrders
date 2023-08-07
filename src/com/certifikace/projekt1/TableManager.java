package com.certifikace.projekt1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static com.certifikace.projekt1.RestaurantSettings.delimiter;
import static com.certifikace.projekt1.RestaurantSettings.fileTables;
import static com.certifikace.projekt1.RestaurantSettings.fileTablesBackUp;

public class TableManager {

    private List<Table> tableList;
    public TableManager() {this.tableList = new ArrayList<>();}


    private boolean firstWriteDetector(Table table) {
        return table.getTableNumber() == 99 && table.getTableCapacity() == 999999;
    }
    private void removefirstWrite() {
        /*
        Iterator může být použit pro procházení kolekcí bez ohledu na jejich typ. Nemusím tedy specifikovat, jaká
        kolekce se používá (seznam, množina, mapa atd.), ale můžu použít stejný způsob pro iteraci jakékoliv kolekce.
        Díky tomu by kód měl být flexibilnější a jednodušší na údržbu. Iterator jsem použil k tomu, abych prošel
        seznamem dishList a vyhledal, jestli prvek odpovídá specifickému záznamu, který chci odstranit.
         */
        Iterator<Table> iterator = tableList.iterator();
        while (iterator.hasNext()) {Table table = iterator.next(); if (firstWriteDetector(table)) {iterator.remove();}
        }

    }
    private boolean isTableNumberDuplicity(int tableNumber) {
        for (Table existingTable : tableList) {if (existingTable.getTableNumber() == tableNumber) {return true;}}
        return false;
    }
    private boolean isTableLocationDuplicity(Table newTable) {
        for (Table existingTable : tableList) {
            if (existingTable.getTableLocation().equals(newTable.getTableLocation()) &&
                    existingTable.getTableSector().equals(newTable.getTableSector())) {
                return true;
            }
        }
        return false;
    }
    public void addTable(Table table) throws RestaurantException {
        // Když tam bude první programem vytvořený zápis po prvním spuštěnmí, odstraním ho z Listu
        removefirstWrite();
        if (table.getTableNumber() < 1) {
            throw new RestaurantException("Chyba - Nelze přidat stůl se záporným nebo nulovým číslem: "
                    + table.getTableNumber());
        }
        if (table.getTableCapacity() < 1) {
            throw new RestaurantException("Chyba - Nelze mít stůl se zápornou nebo nulovou kapacitou: "
                    + table.getTableNumber());
        }
        if (tableList.size() > 98) {
            throw new RestaurantException("Chyba - Nelze přidat stůl. Byl dosažen maximální počet stolů.");
        }
        if (isTableNumberDuplicity(table.getTableNumber())) {
            throw new RestaurantException("Chyba - Nelze přidat stůl s již existujícím číslem stolu: "
                    + table.getTableNumber());
        }
        if (isTableLocationDuplicity(table)) {
            throw new RestaurantException("Chyba - Nelze přidat stůl ve stejné místnosti na stejné místo, "
                    + "kde již jeden stůl stojí.");
        }
        tableList.add(table);
    }

    public void removeTable(Table table) {tableList.remove(table);}
    public void removeTableByNumber(int tableNumber) throws RestaurantException {
        if (isTableNumberDuplicity(tableNumber)) {
            tableList.removeIf(table -> table.getTableNumber() == tableNumber);
        } else {
            throw new RestaurantException("Chyba - Stůl s číslem " + tableNumber + " neexistuje, takže ho nelze "
                    + "odebrat, nebyl tedy odebrán.");
        }
    }

    private void createEmptyTablesFile(String fileTables) throws RestaurantException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileTables))) {
            writer.write(99 + delimiter() + delimiter() + delimiter() + 999999);
            writer.newLine();
        } catch (IOException e) {
            throw new RestaurantException("Chyba při vytváření souboru při neexistenci souboru se Stoly: "
                    + e.getMessage());
        }
    }

    public void loadDataTablesFromFile(String fileTables, String delimiter) throws RestaurantException {
        // OŠETŘENÍ prvního spuštění programu, když ještě nebude existovat soubor DB-Tables.txt
        if (!Files.exists(Paths.get(fileTables))) {createEmptyTablesFile(fileTables); return;}

        int helpBadFormatIdentificator = 0;
        String line = "";
        String[] item = new String[0];
        int tableNumber; String tableLocation; String tableSector; int tableCapacity;
        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileTables)))) {
            while (scannerLoadData.hasNextLine()) {
                line = scannerLoadData.nextLine();
                // Oddělení jednotlivých dat stažených ze souboru - nastavil jsem si "; "
                item = line.split(delimiter);
                if (item.length != 4) {
                    throw new RestaurantException("Chyba - špatný počet položek na řádku: " + line);
                }
                tableNumber = Integer.parseInt(item[0]);
                if (tableNumber < 1) {
                    throw new RestaurantException("Chyba - číslo stolu je menší než 1 na řádku: " + line);
                }
                helpBadFormatIdentificator = 3;
                tableLocation = item[1];
                tableSector = item[2];
                tableCapacity = Integer.parseInt(item [3]);
                if (tableCapacity < 1) {
                    throw new RestaurantException("Chyba - kapacita stolu je menší než 1 na řádku: " + line);
                }
                Table table = new Table(tableNumber, tableLocation, tableSector, tableCapacity);
                tableList.add(table);
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileTables + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Chyba - v databázi není číslo: " + item[helpBadFormatIdentificator]
                    + " na řádku: " + line + " položka č. " + helpBadFormatIdentificator);
        }
    }

    public void saveDataTablesToFile(String fileName) throws RestaurantException {
        try {
            // Zálohování souboru před uložením nových hodnot do primárního souboru
            File originalFile = new File(fileTables());
            File backupFile = new File(fileTablesBackUp());
            Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // Uložení nových dat do primárního souboru
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (Table table : tableList) {
                    writer.write(table.getTableNumber() + delimiter() + table.getTableLocation() + delimiter()
                            + table.getTableSector() + delimiter() + table.getTableCapacity());
                    writer.newLine();
                }
            } catch (IOException e) {
                throw new RestaurantException("Chyba při ukládání dat do souboru: " + e.getMessage());
            }
        } catch (IOException e) {throw new RestaurantException("Chyba při zálohování souboru: " + e.getMessage());}
    }

    public List<Table> getTableList() {return new ArrayList<>(tableList);}

}

package com.certifikace.projekt1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.certifikace.projekt1.RestaurantSettings.delimiter;
import static com.certifikace.projekt1.RestaurantSettings.fileTables;
import static com.certifikace.projekt1.RestaurantSettings.fileTablesBackUp;

public class TableManager {

    private List<Table> tableList;
    public TableManager() {this.tableList = new ArrayList<>();}

    public void addTable(Table table) throws RestaurantException {
        // OŠETŔENÍ - Počet stolů musí být dvoumístný - nelze přidat více stolů nad počet 99
        if (tableList.size() > 98) {
            throw new RestaurantException("Chyba - Nelze přidat stůl. Byl dosažen maximální počet stolů.");
        }
        // OŠETŘENÍ - Nový stůl nesmí mít stejné číslo jako již existující stůl
        if (isTableNumberDuplicate(table.getTableNumber())) {
            throw new RestaurantException("Chyba - Nelze přidat stůl s již existujícím číslem stolu: "
                    + table.getTableNumber());
        }
        // OŠETŘENÍ - Nový stůl nesmí být umístěn ve stejné místnosti na stejném místě, kde již jeden stůl stojí
        for (Table existingTable : tableList) {
            if (existingTable.getTableLocation().equals(table.getTableLocation()) &&
                    existingTable.getTableSector().equals(table.getTableSector())) {
                throw new RestaurantException("Chyba - Nelze přidat stůl ve stejné místnosti na stejné místo,"
                        +"kde již jedn stůl stojí.");
            }
        }
        // OŠETŘENÍ - Když vznikne po prvním spuštění programu soubor DB-Tables.txt, který bude mít číslo stolu 1
        // a zbytek dat null a uživatel na FrontEndu zadá do systému první stůl, tento bude nahrazem skutečnými daty
        // od uživatele a přeuloží v DB-Tables.txt
        if (tableList.size() == 1 && tableList.get(0).getTableNumber() == 1 &&
                (tableList.get(0).getTableLocation() == null || tableList.get(0).getTableLocation().isEmpty()) &&
                (tableList.get(0).getTableSector() == null || tableList.get(0).getTableSector().isEmpty()) &&
                tableList.get(0).getTableCapacity() == 0) {
            // Pokud ano, nahraď pouze hodnoty a ponech číslo stolu 1
            Table tableWithNumberOne = tableList.get(0);
            tableWithNumberOne.setTableLocation(table.getTableLocation());
            tableWithNumberOne.setTableSector(table.getTableSector());
            tableWithNumberOne.setTableCapacity(table.getTableCapacity());
            saveDataTablesToFile(fileTables());
        } else {
            // Jinak se standardně přidá pouze do tableList
            tableList.add(table);
        }


    }
    private boolean isTableNumberDuplicate(int tableNumber) {
        for (Table existingTable : tableList) {if (existingTable.getTableNumber() == tableNumber) {return true;}}
        return false;
    }
    public void removeTable(Table table) {tableList.remove(table);}
    public void removeTableByNumber(int tableNumber) {
        tableList.removeIf(table -> table.getTableNumber() == tableNumber);
    }

    private void createEmptyTablesFile(String fileTables) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileTables))) {
            writer.write("1; ; ; 0"); writer.newLine();
        } catch (IOException e) {
            System.err.println("Chyba při vytváření prázdného souboru se Stoly: " + e.getMessage());}
    }
    public void loadDataTablesFromFile(String fileTables, String delimiter) throws RestaurantException {
        // OŠETŘENÍ prvního spuštění programu, když ještě nebude existovat soubor DB-Tables.txt
        if (!Files.exists(Paths.get(fileTables))) {createEmptyTablesFile(fileTables); return;}

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
                tableSector = item[2];
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

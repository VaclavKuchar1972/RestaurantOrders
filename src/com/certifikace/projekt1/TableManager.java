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
    public void addTable(Table table) {
        String helpSameErrMessage =  " Stůl NEBYL přidán do tableList!";

        if (table.getTableNumber() < 1) {
            System.err.println("Chyba: Nelze přidat stůl se záporným nebo nulovým číslem." + helpSameErrMessage);
            return;
        }
        if (table.getTableNumber() > 99) {
            System.err.println("Chyba: Nelze přidat stůl s číslem nad 99, překoročilo by to maximální počet stolů pro "
                    + "tuto restauraci." + helpSameErrMessage); return;
        }
        if (tableList.size() > 98) {
            System.err.println("Chyba: Nelze přidat stůl. Byl dosažen maximální počet stolů, tj. 99."
                    + helpSameErrMessage); return;
        }
        if (isTableNumberDuplicity(table.getTableNumber())) {
            System.err.println("Chyba: Nelze přidat stůl s již existujícím číslem stolu: " + table.getTableNumber()
                    + "."+ helpSameErrMessage); return;
        }
        if (table.getTableCapacity() < 1) {
            System.err.println("Chyba: Nelze přidat stůl se zápornou nebo nulovou kapacitou: "
                    + table.getTableCapacity() + "." + helpSameErrMessage); return;
        }
        if (isTableLocationDuplicity(table)) {
            System.err.println("Chyba: Nelze přidat stůl ve stejné místnosti na stejné místo, kde již jeden stůl stojí."
                    + helpSameErrMessage); return;
        }
        String tableSector = table.getTableSector();
        if (tableSector.length() != 2 || !Character.isUpperCase(tableSector.charAt(0))
                || !Character.isDigit(tableSector.charAt(1)) || tableSector.charAt(1) < '1'
                || tableSector.charAt(1) > '9') {
            System.err.println("Chyba: Nelze přidat stůl s neplatným formátem sektoru stolu: " + table.getTableSector()
                    + "." + helpSameErrMessage); return;
        }

        tableList.add(table);
    }

    public void removeTableByNumber(int tableNumber) throws RestaurantException {
        if (isTableNumberDuplicity(tableNumber)) {
            tableList.removeIf(table -> table.getTableNumber() == tableNumber);
        } else {
            throw new RestaurantException("Chyba: Stůl s číslem " + tableNumber + " neexistuje, takže ho nelze "
                    + "odebrat, nebyl tedy odebrán.");
        }
    }

    public void loadDataTablesFromFile(String fileTables, String delimiter) throws RestaurantException {
        if (!Files.exists(Paths.get(fileTables))) {return;}

        String line = ""; int lineNumber = 0; int itemLocalizator = 0;
        String[] item = new String[0];
        int tableNumber; String tableLocation; String tableSector; int tableCapacity;
        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileTables)))) {
            while (scannerLoadData.hasNextLine()) {
                lineNumber++;
                line = scannerLoadData.nextLine(); item = line.split(delimiter);
                if (item.length != 4) {
                    System.err.println("Chyba: Špatný počet položek stolů na řádku č: " + lineNumber); return;
                }
                itemLocalizator = 1; tableNumber = Integer.parseInt(item[0]);
                if (tableNumber < 1 || tableNumber > 99) {
                    System.err.println("Chyba: Číslo stolu na řádku č. " + lineNumber + " je menší než 1 nebo větší "
                            + "než 99."); return;
                }
                tableLocation = item[1];
                tableSector = item[2];
                itemLocalizator = 4; tableCapacity = Integer.parseInt(item [3]);
                if (tableCapacity < 1) {
                    System.err.println("Chyba: Kapacita stolu na řádku: " + lineNumber + " je menší než 1."); return;
                }
                Table table = new Table(tableNumber, tableLocation, tableSector, tableCapacity);
                tableList.add(table);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Soubor " + fileTables + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            System.err.println("Chyba: v souboru " + fileTables + " není číslo na řádku číslo: " + lineNumber
                    + "  Vadná položka je na řádku v pořadí " + itemLocalizator + ".." + " Řádek má tento obsah: "
                    + line);
        }
    }

    public void saveDataTablesToFile(String fileName) throws RestaurantException {
        try {
            File originalFile = new File(fileTables()); File backupFile = new File(fileTablesBackUp());
            if (Files.exists(Paths.get(originalFile.toURI()))) {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
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

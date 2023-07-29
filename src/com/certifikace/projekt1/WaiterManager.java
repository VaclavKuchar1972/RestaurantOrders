package com.certifikace.projekt1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.certifikace.projekt1.RestaurantSettings.*;
import static com.certifikace.projekt1.RestaurantSettings.delimiter;

public class WaiterManager {

    private List<Waiter> waiterList;
    public WaiterManager() {this.waiterList = new ArrayList<>();}

    public void addWaiter(Waiter waiter) throws RestaurantException {
        // OŠETŔENÍ - Počet číšníků musí být třímístný - nelze přidat více číšníku nebo servírek nad počet 999
        if (waiterList.size() > 998) {
            throw new RestaurantException("Chyba - Nelze přidat číšníka. Byl dosažen maximální počet číšníků " +
                    "v pracovním poměru");
        }
        // OŠETŘENÍ - Když vznikne po prvním spuštění programu soubor DB-Waiters.txt, který bude mít jen jednoho číšníka
        // s číslem 1 a zbytek dat bude mít hodnotu null nebo 0 a uživatel na FrontEndu zadá do systému první stůl,
        // tento bude nahrazem skutečnými daty od uživatele a přeuloží se v DB-Tables.txt
        if (waiterList.size() == 1 && waiterList.get(0).getWaiterNumber() == 1
                && (waiterList.get(0).getWaiterTitleBeforeName() == null
                || waiterList.get(0).getWaiterTitleBeforeName().isEmpty())
                && (waiterList.get(0).getWaiterSecondName() == null
                || waiterList.get(0).getWaiterSecondName().isEmpty())
                && (waiterList.get(0).getWaiterTitleAfterName() == null
                || waiterList.get(0).getWaiterTitleAfterName().isEmpty())
                && (waiterList.get(0).getWaiterIdentificationDocumentNumber() == null
                || waiterList.get(0).getWaiterIdentificationDocumentNumber().isEmpty())
                && (waiterList.get(0).getWaiterTypeOfEmploymentRelationship() == null
                || waiterList.get(0).getWaiterTypeOfEmploymentRelationship().isEmpty())) {
            // Pokud ano, nahradí pouze ostatní hodnoty a ponechá číšníka 1
            Waiter waiterNumberOne = waiterList.get(0);
            waiterNumberOne.setWaiterTitleBeforeName(waiter.getWaiterTitleBeforeName());
            waiterNumberOne.setWaiterFirstName(waiter.getWaiterFirstName());
            waiterNumberOne.setWaiterSecondName(waiter.getWaiterFirstName());
            waiterNumberOne.setWaiterTitleAfterName(waiter.getWaiterTitleAfterName());
            waiterNumberOne.setWaiterIdentificationDocumentNumber(waiter.getWaiterIdentificationDocumentNumber());
            waiterNumberOne.setWaiterTypeOfEmploymentRelationship(waiter.getWaiterTypeOfEmploymentRelationship());
        }
        else {
            // Jinak se standardně přidá pouze do waiterList
            waiterList.add(waiter);
        }
    }

    public void removeWaiter(Waiter waiter) {waiterList.remove(waiter);}
    public void removeWaiterByNumber(int waiterNumber) {
        waiterList.removeIf(table -> table.getWaiterNumber() == waiterNumber);
    }

    private void createEmptyWaitersFile(String fileWaiters) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileWaiters))) {
            writer.write("1; ; ; ; ; ; ;"); writer.newLine();
        } catch (IOException e) {
            System.err.println("Chyba při vytváření souboru při neexistenci souboru se Stoly: " + e.getMessage());}
    }
    public void loadDataWaitersFromFile(String fileWaiters, String delimiter) throws RestaurantException {
        // OŠETŘENÍ prvního spuštění programu, když ještě nebude existovat soubor DB-Waiters.txt
        if (!Files.exists(Paths.get(fileWaiters))) {createEmptyWaitersFile(fileWaiters); return;}

        String line = "";
        String[] item = new String[0];
        int waiterNumber; String waiterTitleBeforeName; String waiterFirstName; String waiterSecondName;
        String waiterTitleAfterName; String waiterIdentificationDocumentNumber;
        String waiterTypeOfEmploymentRelationship;
        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileWaiters)))) {
            while (scannerLoadData.hasNextLine()) {
                line = scannerLoadData.nextLine();
                item = line.split(delimiter);
                if (item.length != 7) {throw new RestaurantException("Chyba - špatný počet položek na řádku: " + line);}
                waiterNumber = Integer.parseInt(item[0]);
                waiterTitleBeforeName = item[1];
                waiterFirstName = item[2];
                waiterSecondName = item[3];
                waiterTitleAfterName = item[4];
                waiterIdentificationDocumentNumber = item[5];
                waiterTypeOfEmploymentRelationship = item[6];
                Waiter waiter = new Waiter(waiterNumber, waiterTitleBeforeName, waiterFirstName, waiterSecondName,
                        waiterTitleAfterName, waiterIdentificationDocumentNumber, waiterTypeOfEmploymentRelationship);
                waiterList.add(waiter);
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileWaiters + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Chyba - v databázi není číslo: " + item[0] + " na řádku: " + line);
        }
    }

    public void saveDataWaiterToFile(String fileName) throws RestaurantException {
        try {
            // Zálohování souboru před uložením nových hodnot do primárního souboru
            File originalFile = new File(fileWaiters());
            File backupFile = new File(fileWaitersBackUp());
            Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // Uložení nových dat do primárního souboru
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (Waiter waiter : waiterList) {
                    writer.write(waiter.getWaiterNumber() + delimiter() + waiter.getWaiterTitleBeforeName()
                                    + delimiter() + waiter.getWaiterFirstName() + delimiter()
                            + waiter.getWaiterSecondName() + delimiter() + waiter.getWaiterTitleAfterName()
                            + delimiter() + waiter.getWaiterIdentificationDocumentNumber() + delimiter()
                            + waiter.getWaiterTypeOfEmploymentRelationship());
                    writer.newLine();
                }
            } catch (IOException e) {
                throw new RestaurantException("Chyba při ukládání dat do souboru: " + e.getMessage());
            }
        } catch (IOException e) {throw new RestaurantException("Chyba při zálohování souboru: " + e.getMessage());}
    }

    public List<Waiter> getWaiterList() {return new ArrayList<>(waiterList);}

}

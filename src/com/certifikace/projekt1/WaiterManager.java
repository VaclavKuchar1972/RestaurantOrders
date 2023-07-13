package com.certifikace.projekt1;

import java.io.*;
import java.nio.file.Files;
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
        waiterList.add(waiter);
    }

    public void removeWaiter(Waiter waiter) {waiterList.remove(waiter);}

    public void loadDataWaitersFromFile(String fileWaiters, String delimiter) throws RestaurantException {

        String line = "";
        String[] item = new String[0];
        int waiterNumber; String waiterTitleBeforeName; String waiterFirstName; String waiterSecondName;
        String waiterTitleAfterName; String waiterIdentificationDocumentNumber;
        String waiterTypeOfEmploymentRelationship;

        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileWaiters)))) {
            while (scannerLoadData.hasNextLine()) {
                line = scannerLoadData.nextLine();
                item = line.split(delimiter);
                if (item.length != 7) {
                    throw new RestaurantException("Chyba - špatný počet položek na řádku: " + line);
                }
                waiterNumber = Integer.parseInt(item[0]);
                waiterTitleBeforeName = item[1];
                waiterFirstName = item[2];
                waiterSecondName = item[3];
                waiterTitleAfterName = item[4];
                waiterIdentificationDocumentNumber = item[5];
                waiterTypeOfEmploymentRelationship = item[6];

                WaiterCategory category = null;

                category = WaiterCategory.valueOf(waiterTypeOfEmploymentRelationship.toUpperCase());
 //               try {category = WaiterCategory.valueOf(waiterTypeOfEmploymentRelationship.toUpperCase());}
 //               catch (
 //                       IllegalArgumentException e) {
 //                   throw new RestaurantException("Soubor " + fileWaiters + " nebyl nalezen! " + e.getLocalizedMessage());
 //               }

                Waiter newWaiter = new Waiter(waiterNumber, waiterTitleBeforeName, waiterFirstName, waiterSecondName,
                        waiterTitleAfterName, waiterIdentificationDocumentNumber, category);
                waiterList.add(newWaiter);
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileWaiters + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Chyba - v databázi není číslo: " + item[0] + " na řádku: " + line);
        }
    }

    public void saveDataWaiterFromFile(String fileName, List<Waiter> waiters) throws RestaurantException {
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

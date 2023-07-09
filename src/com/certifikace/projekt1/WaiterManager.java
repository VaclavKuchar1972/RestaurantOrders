package com.certifikace.projekt1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                // Oddělení jednotlivých dat stažených ze souboru - nastavil jsem si "; "
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
                Waiter newWaiter = new Waiter(waiterNumber, waiterTitleBeforeName, waiterFirstName, waiterSecondName,
                        waiterTitleAfterName, waiterIdentificationDocumentNumber, waiterTypeOfEmploymentRelationship);
                waiterList.add(newWaiter);
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileWaiters + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Chyba - v databázi není číslo: " + item[0]
                    + " na řádku: " + line);
        }
    }

    public List<Waiter> getWaiterList() {return new ArrayList<>(waiterList);}

}

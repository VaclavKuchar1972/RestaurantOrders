package com.certifikace.projekt1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static com.certifikace.projekt1.RestaurantSettings.*;
import static com.certifikace.projekt1.RestaurantSettings.delimiter;

public class WaiterManager {

    private List<Waiter> waiterList;
    public WaiterManager() {this.waiterList = new ArrayList<>();}

    private boolean isWaiterNumberDuplicity(int waiterNumber) {
        for (Waiter existingWaiter : waiterList) {if (existingWaiter.getWaiterNumber() == waiterNumber) {return true;}}
        return false;
    }
    public void addWaiter(Waiter waiter) {

        String helpErrMessage =  " Číšník NEBYL přidán do waiterList!";

        if (waiter.getWaiterNumber() < 1  || waiter.getWaiterNumber() > 999) {
            System.err.println("Chyba: Číslo číšníka nemůže být záporné nebo nulové a ani větší než 999 tedy: "
                    + waiter.getWaiterNumber() + helpErrMessage); return;
        }
        if (isWaiterNumberDuplicity(waiter.getWaiterNumber())) {
            System.err.println("Chyba: Nelze přidat číšníka s číslem: " + waiter.getWaiterNumber()
                    + " Číšník s tímto číslem již existuje." + helpErrMessage); return;
        }
        // Přijímání duplicitních osob nemám ošetřeno proto, že to není tak jednoduché. To by bylo na samostaný scénář
        // a bez přístupu do státních oneline regitsrů to jednoduše nejde. Např. číslo OP může být stejné jako číslo
        // pasu, rodné číslo může být také u dvou osoab stejné (vlastní zkušenost - a nutili mě abych si ho nechal
        // předělat) a v neposeldní řadě může jít o cizince, který má i české státní občanství, tudíž při přijímání
        // do práce může předložit buď OP nebo PAS a v tu chvíli tu máme hned jednoho člověka, který je zaměstnán
        // dvakrát na stejné pracovní pozici.
        // NAVÍC!!! - GDPR!? Toto by se prostě celkově muselo vyřešit jinak...
        waiterList.add(waiter);
    }

    public void removeWaiterByNumber(int waiterNumber) throws RestaurantException {
        if (isWaiterNumberDuplicity(waiterNumber)) {
            waiterList.removeIf(waiter -> waiter.getWaiterNumber() == waiterNumber);
        } else {
            throw new RestaurantException("Chyba - Číšník s číslem " + waiterNumber + " neexistuje, takže ho nelze "
                    + "odebrat, nebyl tedy odebrán.");
        }
    }

    public void loadDataWaitersFromFile(String fileWaiters, String delimiter) throws RestaurantException {
        if (!Files.exists(Paths.get(fileWaiters))) {return;}
        String line = ""; int lineNumber = 0;
        String[] item = new String[0];
        int waiterNumber; String waiterTitleBeforeName; String waiterFirstName; String waiterSecondName;
        String waiterTitleAfterName; String waiterIdentificationDocumentNumber;
        String waiterTypeOfEmploymentRelationship;
        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileWaiters)))) {
            while (scannerLoadData.hasNextLine()) {
                lineNumber++;
                line = scannerLoadData.nextLine(); item = line.split(delimiter);
                if (item.length != 7) {
                    System.err.println("Chyba: Špatný počet položek číšníků na řádku č: " + lineNumber); return;
                }
                waiterNumber = Integer.parseInt(item[0]);
                if (waiterNumber < 1 || waiterNumber > 999) {
                    System.err.println("Chyba: Číslo číšníka na řádku č. " + lineNumber + " je menší než 1 nebo větší "
                            + "než 999."); return;
                }
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
            System.err.println("Soubor " + fileWaiters + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            System.err.println("Chyba: v souboru " + fileWaiters + " není číslo na řádku číslo: " + lineNumber
                    + "  Vadná položka je na řádku v pořadí 1.." + " Řádek má tento obsah: "
                    + line);
        }
    }

    public void saveDataWaitersToFile(String fileName) throws RestaurantException {
        try {
            File originalFile = new File(fileWaiters()); File backupFile = new File(fileWaitersBackUp());
            if (Files.exists(Paths.get(originalFile.toURI()))) {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
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

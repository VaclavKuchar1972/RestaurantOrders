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

    private boolean firstWriteDetector(Waiter waiter) {
        // POZOR na záseky s blbostma - Stringy nemohu porovnávat stejně jako Inty, to nefunguje Stringy EQUALS!!!
        return waiter.getWaiterNumber() == 999 && waiter.getWaiterTypeOfEmploymentRelationship().equals("EMPTY");
    }
    private void removefirstWrite() {
        Iterator<Waiter> iterator = waiterList.iterator();
        while (iterator.hasNext()) {Waiter waiter = iterator.next();
            if (firstWriteDetector(waiter)) {iterator.remove();}
        }
    }
    private boolean isWaiterNumberDuplicity(int waiterNumber) {
        for (Waiter existingWaiter : waiterList) {if (existingWaiter.getWaiterNumber() == waiterNumber) {return true;}}
        return false;
    }
    public void addWaiter(Waiter waiter) {
        // Když tam bude první programem vytvořený zápis po prvním spuštěnmí, odstraním ho z Listu
        removefirstWrite();

        String helpDuplicityErrMessage =  " Číšník NEBYL přidán do waiterList!";

        if (waiter.getWaiterNumber() < 1) {
            System.err.println("Chyba: Číslo číšníka nemůže být záporné nebo nulové tedy: " + waiter.getWaiterNumber()
                    + helpDuplicityErrMessage); return;
        }
        if (waiterList.size() > 998) {
            System.err.println("Chyba: Nelze přidat číšníka. Byl dosažen maximální počet číšníků což je 999."
                    + helpDuplicityErrMessage); return;
        }
        if (isWaiterNumberDuplicity(waiter.getWaiterNumber())) {
            System.err.println("Chyba: Nelze přidat číšníka se stejným číslem: " + waiter.getWaiterNumber()
                    + " Číšník s tímto číslem již existuje." + helpDuplicityErrMessage); return;
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

    private void createEmptyWaitersFile(String fileWaiters) throws RestaurantException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileWaiters))) {
            writer.write(999 + delimiter() + delimiter() + delimiter() + delimiter() + delimiter() + delimiter() + "EMPTY");
            writer.newLine();
        } catch (IOException e) {
            throw new RestaurantException("Chyba při vytváření souboru při neexistenci souboru s číšníky: "
                    + e.getMessage());}
    }
    public void loadDataWaitersFromFile(String fileWaiters, String delimiter) throws RestaurantException {
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
                if (item.length != 7) {throw new RestaurantException("Chyba: Špatný počet položek na řádku: " + line);}
                waiterNumber = Integer.parseInt(item[0]);
                if (waiterNumber < 1) {
                    throw new RestaurantException("Chyba: Číslo číšníka je menší než 1 na řádku: " + line);
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
            throw new RestaurantException("Soubor " + fileWaiters + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Chyba: V souboru " + fileWaiters+ "není číslo: " + item[0] + " na řádku: "
                    + line + " položka č. 0");
        }
    }

    public void saveDataWaitersToFile(String fileName) throws RestaurantException {
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

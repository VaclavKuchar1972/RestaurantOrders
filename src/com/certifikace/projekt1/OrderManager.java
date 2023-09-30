package com.certifikace.projekt1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderManager {

    // "unconfirmedOrderList" je list, kde jsou rozpracované objednávky všech číšníků, které ještě nebyli úplně
    // dohodnuty s hosty u jednotlivých stolů. Je to něco jako nákupní košík v internetovém obchodě, kdy můžete ještě
    // jednotlivé položky mazat, přidávat nové nebo měnit počet objednaného zboží, případně smazat vše a nic neobjednat
    private List<Order> unconfirmedOrdersList;
    private List<Order> receivedOrdersList;

    // Do tohoto lisu se dostanou až skutečně zaplacené objednávky, když někdo uteče bez placení, to by tam být nemělo.
    // Umím si představit i situaci, kdy host jednoduše nemá na zaplacení všeho co si naobjednal, tak zaplatí jen část
    // a zbytek zůstane dlužit a zaplatí jindy nebo se to bude řešit jinak, ale do účtárny by to jít nemělo dokud
    // to není obrat a toto není, je to ztráta až do uhrazení
    private List<Order> closedOrdersList;

    public OrderManager() {
        this.unconfirmedOrdersList = new ArrayList<>();
        this.receivedOrdersList = new ArrayList<>();
        this.closedOrdersList = new ArrayList<>();
    }

    public void addItemToUnconfirmedOrdersByTitleAndQuantity(
            String titleSelect, int quantitySelect, ActualMenuManager amManager,
            int waiterNumber, int tableNumber, int unitsNumber, String noteForKitchen, String noteForManagement,
            WaiterManager waiterManager, TableManager tableManager)

            throws RestaurantException {

        String helpSameErrMessage =  " Položka NEBYLA přidána do unconfirmedOrdersList.";

        boolean waiterExists = false;
        for(Waiter waiter : waiterManager.getWaiterList()) {
            if(waiter.getWaiterNumber() == waiterNumber) {waiterExists = true; break;}
        }
        if(!waiterExists) {
            System.err.println("Chyba: Číšník s číslem " + waiterNumber + " neexistuje ve WaiterList."
                    + helpSameErrMessage);
            return;
        }

        boolean tableExists = false;
        for(Table table : tableManager.getTableList()) {
            if(table.getTableNumber() == tableNumber) {tableExists = true; break;}
        }
        if(!tableExists) {
            System.err.println("Chyba: Stůl s číslem " + tableNumber + " neexistuje v TableList." + helpSameErrMessage);
            return;
        }

        if (unitsNumber <= 0) {
            System.err.println("Chyba: Pokoušíte se objednat položku, která má zápornou nebo nulovou hodnotu počtu "
                    + "objednávaných jednotek:" + unitsNumber + helpSameErrMessage);
            return;
        }

        for (ActualMenu actualMenu : amManager.getAmList()) {
            if (actualMenu.getAmTitle().equals(titleSelect) && actualMenu.getAmQuantity() >= quantitySelect) {

                String fileItemOrOrderActualNumber = "DB-ItemActualNumber"; Integer itemNumber = 0;
                try {itemNumber = loadItemOrOrderActualNumber(fileItemOrOrderActualNumber);}
                catch (RestaurantException e) {System.err.println(e.getMessage() + helpSameErrMessage); return;}
                itemNumber++;
                // Předpokládám, že jedna miliarda je dostatečný počet položek pro každou restauraci na to,
                // aby se stačili ze "zásobníku" nepotvrzených objednávek tyto odstarnit a nedošlo ke kolizi položek
                // se stejnými čísly a zároveň nedošlo k přečerpání možností proměnné Integer, vlastně tím jenom chráním
                // ten Integer, aby program za třeba 10 let nezkolaboval na takový blbosti.
                if (itemNumber > 999999999) {itemNumber = 1;}
                saveItemOrOrderActualNumber(fileItemOrOrderActualNumber, itemNumber);

                Order newItem = new Order(
                        itemNumber,
                        null, //LocalDate.now(), - zatím nebylo objednáno
                        null, //LocalDateTime.now(), - zatím nebylo objednáno, takže čas objednání ještě neexistuje
                        null,  // orderTimeIssue se nastaví později, až se objednané jídlo přenese do receivedOrdersList
                               // a bude doneseno na stůl hostovi
                        waiterNumber,
                        tableNumber,
                        actualMenu.getTitleForOrder(),
                        unitsNumber,
                        actualMenu.getAmPrice(),
                        unitsNumber,
                        noteForKitchen,
                        noteForManagement,
                        OrderCategory.UNCONFIRMED,
                        actualMenu.getAmMainCategory()
                );



                unconfirmedOrdersList.add(newItem);


                String filePath = "DB-UnconfirmedItems.txt";
                try {saveItemOrOrderToFile(filePath);}
                catch (RestaurantException e) {
                    System.err.println("Chyba při ukládání do souboru " + filePath + ": " + e.getMessage());
                }


                return;


            }
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + titleSelect + " a množstvím " + quantitySelect
                + " nebylo nalezeno v amList. Objednané jídlo NEBYLO přidáno do orderList.");
    }


    public void saveItemOrOrderToFile(String filePath) throws RestaurantException {
        File originalFile = new File(filePath);
        File backupFile = new File(filePath + "BackUp.txt");
        if (originalFile.exists()) {
            try {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RestaurantException("Chyba při vytváření zálohy souboru " + filePath + ": " + e.getLocalizedMessage());
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile))) {
            for (Order order : unconfirmedOrdersList) {
                writer.write(order.getOrderNumber() + RestaurantSettings.delimiter() +
                        order.getOrderWaiterNumber() + RestaurantSettings.delimiter() +
                        order.getOrderTableNumber() + RestaurantSettings.delimiter() +
                        order.getOrderTitle() + RestaurantSettings.delimiter() +
                        order.getOrderNumberOfUnits() + RestaurantSettings.delimiter() +
                        order.getOrderPriceOfUnits() + RestaurantSettings.delimiter() +
                        order.getOrderNoteForKitchen() + RestaurantSettings.delimiter() +
                        order.getOrderFoodMainCategory());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru " + filePath + ": " + e.getLocalizedMessage());
        }
    }




    private int loadItemOrOrderActualNumber(String filePath) throws RestaurantException {
        if (!Files.exists(Paths.get(filePath + ".txt"))) {return 1;}
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath + ".txt")))) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                throw new RestaurantException("Soubor " + filePath + ".txt" + " neobsahuje platné celé číslo!");
            }
        }
        catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + filePath + ".txt" + " nebyl nalezen! " + e.getLocalizedMessage());
        }
    }

    public void saveItemOrOrderActualNumber(String filePath, int itemNumber) throws RestaurantException {
        File originalFile = new File(filePath + ".txt");
        if(originalFile.exists()) {
            try {
                Files.copy(Paths.get(filePath + ".txt"), Paths.get(
                        filePath + "BackUp.txt"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RestaurantException("Chyba při vytváření zálohy souboru " + filePath + ".txt: "
                        + e.getLocalizedMessage());
            }
        }
        try (FileWriter writer = new FileWriter(filePath + ".txt")) {
            writer.write(Integer.toString(itemNumber));
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru " + filePath + ".txt" + ": "
                    + e.getLocalizedMessage());
        }
    }


    public List<Order> getOrderList() {return new ArrayList<>(unconfirmedOrdersList);}



}

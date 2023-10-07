package com.certifikace.projekt1;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.certifikace.projekt1.RestaurantSettings.delimiter;

public class OrderManager {

    // "unconfirmedOrderList" je list, kde jsou rozpracované objednávky všech číšníků, které ještě nebyli úplně
    // dohodnuty s hosty u jednotlivých stolů. Je to něco jako nákupní košík v internetovém obchodě, kdy můžete ještě
    // jednotlivé položky mazat, přidávat nové nebo měnit počet objednaného zboží, případně smazat vše a nic neobjednat
    private List<Order> unconfirmedOrdersList;
    private List<Order> receivedOrdersList;
    private List<Order> receivedOrdersListToPrint;

    // Do tohoto lisu se dostanou až skutečně zaplacené objednávky, když někdo uteče bez placení, to by tam být nemělo.
    // Umím si představit i situaci, kdy host jednoduše nemá na zaplacení všeho co si naobjednal, tak zaplatí jen část
    // a zbytek zůstane dlužit a zaplatí jindy nebo se to bude řešit jinak, ale do účtárny by to jít nemělo dokud
    // to není obrat a toto není, je to ztráta až do uhrazení
    private List<Order> closedOrdersList;

    public OrderManager() {
        this.unconfirmedOrdersList = new ArrayList<>();
        this.receivedOrdersList = new ArrayList<>();
        this.receivedOrdersListToPrint = new ArrayList<>();
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
                        noteForKitchen,
                        noteForManagement,
                        OrderCategory.UNCONFIRMED,
                        actualMenu.getAmMainCategory()
                );
                unconfirmedOrdersList.add(newItem);
                String filePath = "DB-UnconfirmedItems";
                try {saveItemOrOrderToFile(filePath, unconfirmedOrdersList);}
                catch (RestaurantException e) {
                    System.err.println("Chyba při ukládání do souboru " + filePath + ": " + e.getMessage());
                }
                return;
            }
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + titleSelect + " a množstvím " + quantitySelect
                + " nebylo nalezeno v amList. Objednané jídlo NEBYLO přidáno do orderList.");
    }


    public void removeItemOfUnconfirmedOrdersByItemNumber(int itemNumber) throws RestaurantException {
        // poznámka pro mě: Pro ověření, zda objednávka existuje v unconfirmedOrdersList před jejím odstraněním,
        // můžu použít metodu "stream()" spolu s "anyMatch()", která vrátí, zda nějaká objednávka odpovídá danému
        // kritériu. Pokud žádná taková objednávka neexistuje, vyhodí výjimku RestaurantException.
        boolean doesExist = unconfirmedOrdersList.stream().anyMatch(order -> order.getOrderNumber() == itemNumber);
        if (!doesExist) {
            throw new RestaurantException("Chyba: Položka s číslem " + itemNumber + " neexistuje"
                    + "v unconfirmedOrdersList, nebyla tedy odebrána.");
        }
        unconfirmedOrdersList.removeIf(order -> order.getOrderNumber() == itemNumber);
        String filePath = "DB-UnconfirmedItems";
        try {saveItemOrOrderToFile(filePath, unconfirmedOrdersList);}
        catch (RestaurantException e) {
            System.err.println("Chyba při ukládání do souboru " + filePath + ": " + e.getMessage());
        }
    }

    public void removeAllItemsOfUnconfirmedOrdersByTable(int tableNumber) throws RestaurantException {
        boolean hasRemoved = unconfirmedOrdersList.removeIf(order -> order.getOrderTableNumber() == tableNumber);
        if(!hasRemoved) {
            try {
                throw new RestaurantException("Žádné položky s číslem stolu " + tableNumber + " nebyly nalezeny "
                        + "v unconfirmedOrdersList. Nebyly tedy odstraněny.");
            } catch (RestaurantException e) {System.err.println(e.getMessage());}
        } else {
            String filePath = "DB-UnconfirmedItems";
            try {saveItemOrOrderToFile(filePath, unconfirmedOrdersList);}
            catch (RestaurantException e) {
                System.err.println("Chyba při ukládání do souboru " + filePath + ": " + e.getMessage());
            }
        }
    }

    public void addAllItemByTableToConfirmedOrders(int tableNumber) throws RestaurantException {
        List<Order> toBeConfirmedOrders = new ArrayList<>();
        for (Order order : unconfirmedOrdersList) {
            if (order.getOrderTableNumber() == tableNumber) {toBeConfirmedOrders.add(order);}
        }
        if (toBeConfirmedOrders.isEmpty()) {
            System.err.println("Chyba: Nebyly nalezeny žádné položky s číslem stolu " + tableNumber +
                    " v unconfirmedOrdersList. Položky tedy nebyli přidány do receivedOrdersList.");
            return;
        }
        receivedOrdersListToPrint.clear();
        for (Order order : toBeConfirmedOrders) {
            order.setOrderCategory(OrderCategory.RECEIVED);
            order.setOrderTimeReceipt(LocalDateTime.now());
            receivedOrdersList.add(order);
            receivedOrdersListToPrint.add(order);
            unconfirmedOrdersList.remove(order);
        }
        try {
            saveItemOrOrderToFile("DB-ConfirmedItems", receivedOrdersList);
        } catch (RestaurantException e) {
            System.err.println("Chyba při ukládání do souboru DB-ConfirmedItems.txt: " + e.getMessage());
            return;
        }
        try {
            saveItemOrOrderToFile("DB-UnconfirmedItems", unconfirmedOrdersList);
        } catch (RestaurantException e) {
            System.err.println("Chyba při ukládání do souboru DB-UnconfirmedItems.txt: " + e.getMessage());
        }
    }

    public List<Order> printerOutputOnBar() {
        return receivedOrdersListToPrint.stream()
                .filter(order -> {
                    FoodCategory category = order.getOrderFoodMainCategory();
                    String categoryName = category != null ? category.getName() : "";
                    return categoryName.equals("NONALCOHOLIC") || categoryName.equals("ALCOHOLIC");
                })
                .collect(Collectors.toList());
    }

    public List<Order> printerOutputOnKitchen() {
        List<Order> barOrders = printerOutputOnBar();
        return receivedOrdersListToPrint.stream()
                .filter(order -> !barOrders.contains(order))
                .collect(Collectors.toList());
    }

    public void changeItemStatuHasBeenBroughtToTableByItemNumber(int itemNumber) throws RestaurantException {
        Order foundOrder = null;
        String helpSameErrMessage =  " Stav položky NEBYL v receivedOrdersList změněn.";
        for (Order order : receivedOrdersList) {
            if (order.getOrderNumber() == itemNumber) {foundOrder = order; break;}
        }
        if (foundOrder == null) {
            throw new RestaurantException("Chyba: Položka s číslem " + itemNumber + " nebyla nalezena."
                    + helpSameErrMessage);
        } else if (foundOrder.getOrderCategory() == OrderCategory.ISSUED) {
            throw new RestaurantException("Chyba: Položka s číslem " + itemNumber + " již byla hostovi donesena."
                    + helpSameErrMessage);
        } else {
            foundOrder.setOrderCategory(OrderCategory.ISSUED);
            foundOrder.setOrderTimeIssue(LocalDateTime.now());
            try {
                saveItemOrOrderToFile("DB-ConfirmedItems", receivedOrdersList);
            } catch (RestaurantException e) {
                System.err.println("Chyba při ukládání do souboru DB-ConfirmedItems.txt: " + e.getMessage());
                return;
            }
        }
    }






    public void loadItemOrOrderFromFile(String filePath) throws RestaurantException, FileNotFoundException {
        File file = new File(filePath + ".txt");
        if (!file.exists()) {System.err.println("Soubor " + filePath + ".txt" + " neexistuje!"); return;}
        String line; int lineNumber = 0;
        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(file)))) {
            while (scannerLoadData.hasNextLine()) {
                lineNumber++;
                line = scannerLoadData.nextLine();
                String[] item = line.split(delimiter());
                if (item.length != 13) {
                    System.err.println("Chyba: Špatný počet položek na řádku č: " + lineNumber);
                    return;
                }
                try {
                    int orderNumber = Integer.parseInt(item[0]);
                    // poznámka pro mě: Znak "?" je součástí ternárního operátoru v JAVA,
                    // který je zkrácenou formou if-else příkazu. Struktura tohoto operátoru je následující:
                    // výraz ? výraz_pokud_true : výraz_pokud_false;
                    LocalDate orderDate = item[1].equals("null") ? null : LocalDate.parse(item[1]);
                    LocalDateTime orderTimeReceipt = item[2].equals("null") ? null : LocalDateTime.parse(item[2]);
                    LocalDateTime orderTimeIssue = item[3].equals("null") ? null : LocalDateTime.parse(item[3]);
                    int orderWaiterNumber = Integer.parseInt(item[4]);
                    int orderTableNumber = Integer.parseInt(item[5]);
                    String orderTitle = item[6];
                    int orderNumberOfUnits = Integer.parseInt(item[7]);
                    BigDecimal orderPriceOfUnits = new BigDecimal(item[8]);
                    String orderNoteForKitchen = item[9];
                    String orderNoteForManagement = item[10];
                    OrderCategory orderCategory = OrderCategory.valueOf(item[11]);
                    FoodCategory orderFoodMainCategory = FoodCategory.valueOf(item[12]);
                    Order order = new Order(orderNumber, orderDate, orderTimeReceipt, orderTimeIssue, orderWaiterNumber,
                            orderTableNumber, orderTitle, orderNumberOfUnits, orderPriceOfUnits, orderNoteForKitchen,
                            orderNoteForManagement,orderCategory, orderFoodMainCategory);

                    if (filePath == "DB-UnconfirmedItems") {unconfirmedOrdersList.add(order);}
                    if (filePath == "DB-ConfirmedItems") {receivedOrdersList.add(order);}

                    // tady musím přerozdělit do listů dle názvu souboru


                } catch (NumberFormatException | DateTimeParseException e) {
                    System.err.println("Chyba: Špatný formát čísla nebo data na řádku: " + lineNumber + " Soubor: "
                            + filePath + ".txt");
                } catch (IllegalArgumentException e) {
                    System.err.println("Chyba: Neplatná hodnota v enumu na řádku: " + lineNumber + " Soubor: "
                            + filePath + ".txt");
                }
            }
        }
    }


    public void saveItemOrOrderToFile(String filePath, List<Order> orders) throws RestaurantException {
        File originalFile = new File(filePath + ".txt");
        File backupFile = new File(filePath + "BackUp.txt");
        if (originalFile.exists()) {
            try {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RestaurantException("Chyba při vytváření zálohy souboru " + filePath + ": " + e.getLocalizedMessage());
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile))) {
            for (Order order : orders) {
                writer.write(order.getOrderNumber() + delimiter() +
                        order.getOrderDate() + delimiter() +
                        order.getOrderTimeReceipt() + delimiter() +
                        order.getOrderTimeIssue() + delimiter() +
                        order.getOrderWaiterNumber() + delimiter() +
                        order.getOrderTableNumber() + delimiter() +
                        order.getOrderTitle() + delimiter() +
                        order.getOrderNumberOfUnits() + delimiter() +
                        order.getOrderPriceOfUnits() + delimiter() +
                        order.getOrderNoteForKitchen() + delimiter() +
                        order.getOrderNoteForManagement() + delimiter() +
                        order.getOrderCategory().name() + delimiter() +
                        order.getOrderFoodMainCategory().getName());
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

    private void saveItemOrOrderActualNumber(String filePath, int itemNumber) throws RestaurantException {
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


    public List<Order> getUncofirmedItemsList() {return new ArrayList<>(unconfirmedOrdersList);}
    public List<Order> getConfirmedItemsList() {return new ArrayList<>(receivedOrdersList);}





}




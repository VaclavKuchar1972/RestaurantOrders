package com.certifikace.projekt1;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
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
                // ten Integer a t ještě s rezervou, aby program za třeba 10 let nezkolaboval na takový blbosti.
                if (itemNumber > 999999999) {itemNumber = 1;}
                saveItemOrOrderActualNumber(fileItemOrOrderActualNumber, itemNumber);
                Order newItem = new Order(
                        0,
                        null, //zatím účetně nevznikla objednávka, takže čas a datum objednávky neexistuje
                        itemNumber,
                        null, //zatím nebylo s hostem úplně dohodnuto, že objedná, takže čas objednání
                        // položky ještě neexistuje
                        null,  // orderTimeIssue se nastaví později, až se objednané jídlo přenese do receivedOrdersList
                               // a HLAVNĚ bude doneseno na stůl hostovi
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
        boolean doesExist = unconfirmedOrdersList.stream().anyMatch(order -> order.getOrderItemNumber() == itemNumber);
        if (!doesExist) {
            throw new RestaurantException("Chyba: Položka s číslem " + itemNumber + " neexistuje"
                    + "v unconfirmedOrdersList, nebyla tedy odebrána.");
        }
        unconfirmedOrdersList.removeIf(order -> order.getOrderItemNumber() == itemNumber);
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


    public void changeTableNumberBySelectedTablesAndItemsToNewTable(
            int oldTableNumber, List<Integer> itemNumbers, int newTableNumber, TableManager tableManager)
            throws RestaurantException {
        boolean oldTableExists = false; boolean newTableExists = false;
        for (Table table : tableManager.getTableList()) {
            if (table.getTableNumber() == oldTableNumber) {oldTableExists = true;}
            if (table.getTableNumber() == newTableNumber) {newTableExists = true;}
        }
        if (!oldTableExists) {throw new RestaurantException("Chyba: Stůl s číslem " + oldTableNumber + " neexistuje.");}
        if (!newTableExists) {throw new RestaurantException("Chyba: Stůl s číslem " + newTableNumber + " neexistuje.");}
        List<Order> itemsToMove = new ArrayList<>();
        for (int itemNumber : itemNumbers) {
            boolean itemFound = false;
            for (Order order : receivedOrdersList) {
                if (order.getOrderItemNumber() == itemNumber) {
                    if (order.getOrderTableNumber() == oldTableNumber) {
                        itemsToMove.add(order);
                        itemFound = true;
                    } else {
                        throw new RestaurantException("Chyba: Položka s číslem " + itemNumber + " nepatří ke stolu "
                                + oldTableNumber);
                    }
                }
            }
            if (!itemFound) {
                throw new RestaurantException("Chyba: Položka s číslem " + itemNumber + " nebyla nalezena nebo nepatří "
                        + "ke stolu " + oldTableNumber);
            }
        }
        for (Order itemToMove : itemsToMove) {itemToMove.setOrderTableNumber(newTableNumber);}
        String filePath = "DB-ConfirmedItems";
        try {saveItemOrOrderToFile(filePath, receivedOrdersList);}
        catch (RestaurantException e) {
            System.err.println("Chyba při ukládání do souboru " + filePath + ": " + e.getMessage());
        }
    }

    public void changeTableNumberBySelectedTablesAllItemsToNewTable
            (int oldTableNumber, int newTableNumber, TableManager tableManager)
            throws RestaurantException {
        boolean oldTableExists = false; boolean newTableExists = false;
        for (Table table : tableManager.getTableList()) {
            if (table.getTableNumber() == oldTableNumber) {oldTableExists = true;}
            if (table.getTableNumber() == newTableNumber) {newTableExists = true;}
        }
        if (!oldTableExists) {throw new RestaurantException("Chyba: Stůl s číslem " + oldTableNumber + " neexistuje.");}
        if (!newTableExists) {throw new RestaurantException("Chyba: Stůl s číslem " + newTableNumber + " neexistuje.");}
        List<Order> itemsToMove = new ArrayList<>();
        for (Order order : receivedOrdersList) {
            if (order.getOrderTableNumber() == oldTableNumber) {itemsToMove.add(order);}
        }
        for (Order itemToMove : itemsToMove) {itemToMove.setOrderTableNumber(newTableNumber);}
        String filePath = "DB-ConfirmedItems";
        try {
            saveItemOrOrderToFile(filePath, receivedOrdersList);
        } catch (RestaurantException e) {
            System.err.println("Chyba při ukládání do souboru " + filePath + ": " + e.getMessage());
        }
    }


    public void changeItemStatusHasBeenBroughtToTableByItemNumber(int itemNumber) throws RestaurantException {
        Order foundItem = null;
        String helpSameErrMessage =  " Stav položky NEBYL v receivedOrdersList změněn.";
        for (Order order : receivedOrdersList) {
            if (order.getOrderItemNumber() == itemNumber) {foundItem = order; break;}
        }
        if (foundItem == null) {
            throw new RestaurantException("Chyba: Položka s číslem " + itemNumber + " nebyla nalezena."
                    + helpSameErrMessage);
        } else if (foundItem.getOrderTimeIssue() != null) {
            throw new RestaurantException("Chyba: Položka s číslem " + itemNumber + " již byla hostovi donesena."
                    + helpSameErrMessage);
        } else {
            foundItem.setOrderTimeIssue(LocalDateTime.now());
            try {
                saveItemOrOrderToFile("DB-ConfirmedItems", receivedOrdersList);
            } catch (RestaurantException e) {
                System.err.println("Chyba při ukládání do souboru DB-ConfirmedItems.txt: " + e.getMessage());
                return;
            }
            checkBroughtToTheTableAndPaidWithFollowUpAction();
        }
    }

    public void changeItemStatusHasBeenPaidByItemNumberList(List<Integer> itemNumbers) throws RestaurantException {
        String filePathOrderActualNumber = "DB-OrderActualNumber"; Integer orderNumber = 0;
        try {orderNumber = loadItemOrOrderActualNumber(filePathOrderActualNumber);}

        catch (RestaurantException e) {
            System.err.println(e.getMessage() + "Metoda changeItemStatusHasBeenPaidByItemNumberList byla přerušena, "
                    + "nebyla provedena žádná operace. ");
            return;
        }
        // Když host bude platit více položek najednou, všechny položky budou účetně v jedné objednávce, takže všechny
        // dostanou stejné číslo objednávky, aby se pak dal udělat daňový doklad, kde bude jedno číslo objednávky
        // a všechny položky, které do ní patří. Host může zaplatit i položku za kamaráda vedle u stolu a podobně,
        // objednávka i daňový doklad se vztahují ke konkrétnímu plátci, nikoli ke stolu
        int currentYear = LocalDate.now().getYear();
        int combinedNumber = Integer.parseInt(String.valueOf(currentYear) + String.valueOf(orderNumber));
        // Předpokládám, že na FrontEndu nebo po přidání dalšího kódu na BackEnd nebude problém z Integeru
        // combinedNumber oddělit první 4 čísla reprezentující rok objednávky od zbytku čísla, které obsahuje
        // její pořadí do vyčerpání miliardového limitu a na daňový doklad pro objednávku např. z roku 2023
        // s číslem 11 vytisknout číslo objednávky 2023000000011 (ve String podobě).
        boolean found = false;
        for (int itemNumber : itemNumbers) {
            boolean itemFound = false;
            for (Order order : receivedOrdersList) {
                if (order.getOrderItemNumber() == itemNumber) {
                    order.setOrderCategory(OrderCategory.PAID);
                    order.setOrderNumber(combinedNumber);
                    itemFound = true;
                    found = true;
                }
            }
            if (!itemFound) {
                throw new RestaurantException("První položka z itemNumbers s číslem " + itemNumber + " nebyla nalezena "
                        + "v receivedOrdersList, její stav tedy nebyl změněn a ani stav ostatních položek z listu "
                        + "itemNumbers. ");
            }
        }
        if (!found) {
            throw new RestaurantException("Žádná z položek zadaných na FrontEndu nebyla nalezena v receivedOrdersList,"
                    + " nic se tedy nezměnilo v receivedOrdersList.");
        }
        else orderNumber++;
        // Předpokládám, že miliarda je dostatečný počet uzavřených objednávek pro každou restauraci na to,
        // aby stačili na objednávky za jeden rok. Cca 2,7Mio uzavřených objednávek denně je hezké číslo.
        // Takže až se číslo přehoupne přes 999999999, čísla objednávek nebudou duplicitní, protože níže jsou
        // ošetřeny ještě vložením kalendářního roku před dané číslo. Z hlediska účetnictví, je jedno,
        // že objednávky každý rok nejsou od nuly. To je finančáku úplně jedno. :-) Jen by vzhledem ke správnému
        // chodu programu neměli být duplicitní.
        if (orderNumber > 999999999) {orderNumber = 1;}
        saveItemOrOrderActualNumber(filePathOrderActualNumber, orderNumber);
        try {
            saveItemOrOrderToFile("DB-ConfirmedItems", receivedOrdersList);
        } catch (RestaurantException e) {
            System.err.println("Chyba při ukládání do souboru DB-ConfirmedItems.txt: " + e.getMessage());
            return;
        }
        checkBroughtToTheTableAndPaidWithFollowUpAction();
    }

    private void checkBroughtToTheTableAndPaidWithFollowUpAction() throws RestaurantException {
        List<Order> ordersToRemove = new ArrayList<>();
        for (Order order : receivedOrdersList) {
            if (order.getOrderCategory() == OrderCategory.PAID && order.getOrderTimeIssue() != null) {
                order.setOrderDateTimeClosing(LocalDateTime.now());
                order.setOrderCategory(OrderCategory.CLOSED);
                closedOrdersList.add(order);
                ordersToRemove.add(order);
            }
        }
        receivedOrdersList.removeAll(ordersToRemove);
        String filePath = "DB-ClosedOrders";
        try {saveItemOrOrderToFile(filePath, closedOrdersList);}
        catch (RestaurantException e) {
            System.err.println("Chyba při ukládání do souboru " + filePath + ": " + e.getMessage());
        }
        filePath = "DB-ConfirmedItems";
        try {saveItemOrOrderToFile(filePath, receivedOrdersList);}
        catch (RestaurantException e) {
            System.err.println("Chyba při ukládání do souboru " + filePath + ": " + e.getMessage());
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
                if (item.length != 14) {
                    System.err.println("Chyba: Špatný počet položek na řádku č: " + lineNumber);
                    return;
                }
                try {
                    int orderNumber = Integer.parseInt(item[0]);
                    // poznámka pro mě: Znak "?" je součástí ternárního operátoru v JAVA,
                    // který je zkrácenou formou if-else příkazu. Struktura tohoto operátoru je následující:
                    // výraz ? výraz_pokud_true : výraz_pokud_false;
                    LocalDateTime orderDate = item[1].equals("null") ? null : LocalDateTime.parse(item[1]);
                    int itemNumber = Integer.parseInt(item[2]);
                    LocalDateTime orderTimeReceipt = item[3].equals("null") ? null : LocalDateTime.parse(item[3]);
                    LocalDateTime orderTimeIssue = item[4].equals("null") ? null : LocalDateTime.parse(item[4]);
                    int orderWaiterNumber = Integer.parseInt(item[5]);
                    int orderTableNumber = Integer.parseInt(item[6]);
                    String orderTitle = item[7];
                    int orderNumberOfUnits = Integer.parseInt(item[8]);
                    BigDecimal orderPriceOfUnits = new BigDecimal(item[9]);
                    String orderNoteForKitchen = item[10];
                    String orderNoteForManagement = item[11];
                    OrderCategory orderCategory = OrderCategory.valueOf(item[12]);
                    FoodCategory orderFoodMainCategory = FoodCategory.valueOf(item[13]);
                    Order order = new Order(orderNumber, orderDate, itemNumber, orderTimeReceipt, orderTimeIssue,
                            orderWaiterNumber, orderTableNumber, orderTitle, orderNumberOfUnits, orderPriceOfUnits,
                            orderNoteForKitchen, orderNoteForManagement,orderCategory, orderFoodMainCategory);
                    if (filePath == "DB-UnconfirmedItems") {unconfirmedOrdersList.add(order);}
                    if (filePath == "DB-ConfirmedItems") {receivedOrdersList.add(order);}
                    if (filePath == "DB-ClosedOrders") {closedOrdersList.add(order);}
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
                throw new RestaurantException("Chyba při vytváření zálohy souboru " + filePath + ": "
                        + e.getLocalizedMessage());
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile))) {
            for (Order order : orders) {
                writer.write(order.getOrderNumber() + delimiter() +
                        order.getOrderDateTimeClosing() + delimiter() +
                        order.getOrderItemNumber() + delimiter() +
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
    public List<Order> getClosedOrdersList() {return new ArrayList<>(closedOrdersList);}


}




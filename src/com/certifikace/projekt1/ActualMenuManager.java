package com.certifikace.projekt1;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.certifikace.projekt1.RestaurantSettings.*;

public class ActualMenuManager {

    private List<ActualMenu> amList;

    public ActualMenuManager() {this.amList = new ArrayList<>();}

    public void addFoodToMenuByTitleAndQuantity(String title, int quantity, DishManager dishManager) throws RestaurantException {
        String helpSameErrMessage = " Jídlo NEBYLO přidáno do amList!";

        for (Dish dish : dishManager.getDishList()) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {
                for (ActualMenu existingFood : amList) {
                    if (existingFood.getAmTitle().equals(dish.getDishTitle()) &&
                            existingFood.getAmQuantity() == dish.getDishRecommendedQuantity()) {
                        throw new RestaurantException("Chyba: Jídlo s názvem " + title + " a množstvím " + quantity
                                + " již existuje v amList." + helpSameErrMessage);
                    }
                }
                ActualMenu newFoodToMenu = new ActualMenu(
                        dish.getDishRecomendedMainCategory(),
                        dish.getDishNumberOfNextRecomendedCategories(),
                        dish.getDishNextRecomendedCategory(),
                        dish.getDishTitle(),
                        dish.getDishRecommendedQuantity(),
                        dish.getDishRecommendedUnitOfQuantity(),
                        dish.getDishRecommendPrice(),
                        dish.getDishEstimatedPreparationTime(),
                        dish.getDishMainPhoto(),
                        dish.getDishNumberOfNextPhotos(),
                        dish.getDishNextPhoto()
                );
                amList.add(newFoodToMenu);
                return;
            }
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + title + " a množstvím " + quantity + " nebylo nalezeno"
                + " v dishList." + helpSameErrMessage);
    }

    public void removeFoodFromMenu(String title, int quantity) throws RestaurantException {
        if (amList.isEmpty()) {throw new RestaurantException("Chyba: amList je prázdný. Nelze odstranit jídlo.");}
        ActualMenu foodToRemove = null;
        for (ActualMenu food : amList) {
            if (food.getAmTitle().equals(title) && food.getAmQuantity() == quantity) {
                foodToRemove = food;
                break;
            }
        }
        if (foodToRemove != null) {amList.remove(foodToRemove);}
        else {
            throw new RestaurantException("Chyba: Jídlo s názvem " + title + " a množstvím " + quantity + " nebylo "
                    + "nalezeno v amList. Nelze ho tedy odstranit.");
        }
    }

    public void clearAmList() {amList.clear();}

    public void loadDataMenuFromFile(String fileActualMenu, String delimiter) throws RestaurantException {
        if (!Files.exists(Paths.get(fileActualMenu))) {return;}
        String helpSameErrMessage1 = "Chyba: Špatný počet položek na řádku č. ";
        String helpSameErrMessage2 = " Vadná položka je na řádku v pořadí ";
        String helpSameErrMessage3 = " NEBO! Na tomto řádku je špatný počet položek díky proměnlivému počtu dalších "
                + "kategoríí nebo dalších fotek!";
        int i; int lineNumber = 0; int itemLocalizator = 0;
        FoodCategory helpCategory = null;
        String line = ""; String[] item = new String[0];
        FoodCategory amMainCategory; int amNumberOfNextCategories; String amTitle; int amQuantity;
        String amUnitOfQuantity; BigDecimal amPrice; int amPreparationTime; String amMainPhoto;
        int amNumberOfNextPhotos;
        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileActualMenu)))) {
            while (scannerLoadData.hasNextLine()) {
                lineNumber++;
                line = scannerLoadData.nextLine(); item = line.split(delimiter);
                if (item.length < 9) { System.err.println(helpSameErrMessage1 + lineNumber); continue; }
                amMainCategory = FoodCategory.valueOf(item[0]);
                if (amMainCategory == null) {
                    System.err.println("Chyba: V souboru DB-ActualMenu.txt je základní kategorie, která má hodnotu"
                            + " null nebo má neplatný formát nebo neexistuje v seznamu kategorií ve FoodCategory na "
                            + "řádku číslo " + lineNumber + " s obsahem: " + line + helpSameErrMessage3 ); return;
                }
                itemLocalizator = 2; amNumberOfNextCategories = Integer.parseInt(item[1]);
                List<FoodCategory> amNextCategory = new ArrayList<>();
                for (i = 0; i < amNumberOfNextCategories; i++) {
                    helpCategory = FoodCategory.valueOf(item[i + 2]);
                    amNextCategory.add(helpCategory);
                    if (helpCategory == null) {
                        System.err.println("Chyba: V souboru DB-ActualMenu.txt je další kategorie, která má "
                                + "hodnotu null nebo má neplatný formát nebo neexistuje v seznamu kategorií ve "
                                + "FoodCategory na řádku s obsahem: " + line); return;
                    }
                }
                amTitle = item[2 + amNumberOfNextCategories];
                itemLocalizator = 4 + amNumberOfNextCategories;
                amQuantity = Integer.parseInt(item[3 + amNumberOfNextCategories]);
                amUnitOfQuantity = item[4 + amNumberOfNextCategories];
                itemLocalizator = 6 + amNumberOfNextCategories;
                amPrice = new BigDecimal(item[5 + amNumberOfNextCategories]);
                itemLocalizator = 7 + amNumberOfNextCategories;
                amPreparationTime = Integer.parseInt(item[6 + amNumberOfNextCategories]);
                amMainPhoto = item[7 + amNumberOfNextCategories];
                itemLocalizator = 9 + amNumberOfNextCategories;
                amNumberOfNextPhotos = Integer.parseInt(item[8 + amNumberOfNextCategories]);
                if (item.length != 9 + amNumberOfNextCategories + amNumberOfNextPhotos) {
                    System.err.println(helpSameErrMessage1 + lineNumber + helpSameErrMessage2 + itemLocalizator + ".");
                    return;
                }
                List<String> amNextPhoto = new ArrayList<>();
                for (i = 0; i < amNumberOfNextPhotos; i++) {
                    amNextPhoto.add(item[i + 9 + amNumberOfNextCategories]);
                }
                ActualMenu newActualMenu = new ActualMenu(amMainCategory, amNumberOfNextCategories, amNextCategory,
                        amTitle, amQuantity, amUnitOfQuantity, amPrice, amPreparationTime, amMainPhoto,
                        amNumberOfNextPhotos, amNextPhoto);
                amList.add(newActualMenu);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Soubor " + fileActualMenu + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            System.err.println("Chyba: v souboru " + fileActualMenu + " není číslo na řádku číslo: " + lineNumber
                    + "  Vadná položka je na řádku v pořadí " + itemLocalizator + ".." + " Řádek má tento obsah: "
                    + line + helpSameErrMessage3);
        }
    }

    public void saveDataMenuToFile(String fileName) throws RestaurantException {
        try {
            File originalFile = new File(fileActualMenu()); File backupFile = new File(fileBackUpMenu());
            if (Files.exists(Paths.get(originalFile.toURI()))) {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (ActualMenu actualMenu : amList) {
                    FoodCategory amMainCategory = actualMenu.getAmMainCategory(); // tak na tuto věc bacha !!!
                    writer.write(amMainCategory.getName() + delimiter());
                    writer.write(actualMenu.getAmNumberOfNextCategories() + delimiter());
                    List<FoodCategory> nextCategories = actualMenu.getAmNextCategory();
                    for (FoodCategory category : nextCategories) {
                        writer.write(category.getName() + delimiter());
                    }
                    writer.write(actualMenu.getAmTitle() + delimiter());
                    writer.write(actualMenu.getAmQuantity() + delimiter());
                    writer.write(actualMenu.getAmUnitOfQuantity() + delimiter());
                    writer.write(actualMenu.getAmPrice() + delimiter());
                    writer.write(actualMenu.getAmPreparationTime() + delimiter());
                    writer.write(actualMenu.getAmMainPhoto() + delimiter());
                    writer.write(actualMenu.getAmNumberOfNextPhotos() + delimiter());
                    List<String> nextPhotos = actualMenu.getAmNextPhoto();
                    for (String photo : nextPhotos) {
                        writer.write(photo + delimiter());
                    }
                    writer.newLine();
                }
            } catch (IOException e) {
                throw new RestaurantException("Chyba při ukládání dat do souboru: " + e.getMessage());
            }
        } catch (RestaurantException exception) {
            throw new RestaurantException("Chyba při zálohování souboru: " + exception.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ActualMenu> getAmList() {return new ArrayList<>(amList);}

}

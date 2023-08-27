package com.certifikace.projekt1;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static com.certifikace.projekt1.RestaurantSettings.*;

public class ActualMenuManager {

    private List<ActualMenu> amList;

    public ActualMenuManager() {this.amList = new ArrayList<>();}


    private boolean firstWriteDetector(ActualMenu actualMenu) {
        return actualMenu.getAmNumberOfNextCategories() == 0 && actualMenu.getAmTitle().equals("Empty Title")
                && actualMenu.getAmQuantity() == 0  && actualMenu.getAmPrice().compareTo(BigDecimal.ZERO) == 0
                && actualMenu.getAmPreparationTime() == 999999 && actualMenu.getAmNumberOfNextPhotos() == 0;
    }
    private void removefirstWrite() {
        Iterator<ActualMenu> iterator = amList.iterator();
        while (iterator.hasNext()) {
            ActualMenu actualMenu = iterator.next();
            if (firstWriteDetector(actualMenu)) {iterator.remove();}
        }
    }
    public void addFoodToMenu(String title, int quantity, DishManager dishManager) throws RestaurantException {
        // Když tam bude první programem vytvořený zápis po prvním spuštěnmí, odstraní se z Listu
        removefirstWrite();

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




    private void createEmptyDishsFile(String fileActualMenu) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileActualMenu))) {
            writer.write(delimiter() + 0 + delimiter() + "Empty Title" + delimiter() + 0 + delimiter() + delimiter()
                    + "0" + delimiter() + 999999 + delimiter() + delimiter() + 0); writer.newLine();
        } catch (IOException e) {
            System.err.println("Chyba při vytváření souboru při neexistenci souboru s aktuálním menu: "
                    + e.getMessage());
        }
    }
    public void loadDataMenuFromFile(String fileActualMenu, String delimiter) throws RestaurantException {
        // OŠETŘENÍ prvního spuštění programu, když ještě nebude existovat soubor DB-Dishs.txt
        if (!Files.exists(Paths.get(fileActualMenu))) {createEmptyDishsFile(fileActualMenu); return;}

        int i; int helpBadFormatIdentificator = 0; FoodCategory helpCategory = null;
        String line = ""; String[] item = new String[0];
        FoodCategory amMainCategory; int amNumberOfNextCategories; String amTitle; int amQuantity;
        String amUnitOfQuantity; BigDecimal amPrice; int amPreparationTime; String amMainPhoto;
        int amNumberOfNextPhotos;
        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileActualMenu)))) {
            while (scannerLoadData.hasNextLine()) {
                line = scannerLoadData.nextLine();
                item = line.split(delimiter);
                amMainCategory = FoodCategory.valueOf(item[0]);
                if (amMainCategory == null) {System.err.println("Chyba: V souboru DB-ActualMenu.txt je "
                        + "základní kategorie, která má hodnotu null nebo má neplatný formát nebo neexistuje"
                        + " v seznamu kategorií ve FoodCategory na řádku s obsahem: " + line);}
                helpBadFormatIdentificator = 1;
                amNumberOfNextCategories = Integer.parseInt(item[1]);
                List<FoodCategory> amNextCategory = new ArrayList<>();
                for (i = 0; i < amNumberOfNextCategories; i++) {
                    helpCategory = FoodCategory.valueOf(item[i + 2]);
                    amNextCategory.add(helpCategory);
                    if (helpCategory == null) {System.err.println("Chyba: V souboru DB-ActualMenu.txt je další "
                            + "kategorie, která má hodnotu null nebo má neplatný formát nebo neexistuje v seznamu "
                            + "kategorií ve FoodCategory na řádku s obsahem: " + line);}
                }
                amTitle = item[2 + amNumberOfNextCategories];
                helpBadFormatIdentificator = 2 + amNumberOfNextCategories;
                amQuantity = Integer.parseInt(item[3 + amNumberOfNextCategories]);
                amUnitOfQuantity = item[4 + amNumberOfNextCategories];
                helpBadFormatIdentificator = 4 + amNumberOfNextCategories;
                amPrice = new BigDecimal(item[5 + amNumberOfNextCategories]);
                helpBadFormatIdentificator = 5 + amNumberOfNextCategories;
                amPreparationTime = Integer.parseInt(item[6 + amNumberOfNextCategories]);
                amMainPhoto = item[7 + amNumberOfNextCategories];
                helpBadFormatIdentificator = 7 + amNumberOfNextCategories;
                amNumberOfNextPhotos = Integer.parseInt(item[8 + amNumberOfNextCategories]);
                if (item.length != 9 + amNumberOfNextCategories + amNumberOfNextPhotos) {
                    throw new RestaurantException("Chyba: Špatný počet položek na řádku: " + line);
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
            throw new RestaurantException("Chyba: Soubor " + fileActualMenu + " nebyl nalezen! "
                    + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Chyba: V souboru DB-ActualMenu.txt není číslo nebo má nedovolenou zápornou "
                    + "hodnotu na řádku: " + line + " položka č." + helpBadFormatIdentificator);
        }
    }


    /*
    public void saveDataMenuToFile(String fileName) throws RestaurantException {
        try {
            // Zálohování souboru před uložením nových hodnot do primárního souboru
            File originalFile = new File(fileDishs());
            File backupFile = new File(fileDishsBackUp());
            Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // Uložení nových dat do primárního souboru
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (Dish dish : dishList) {
                    // Musím získat název kategorie v angličtině, jinak se mi tam zapíšou český názvy a bude zle!!!
                    // Zase problém s ENUM, který není ENUM, ale jinak to nejde :D
                    writer.write(dish.getDishRecomendedMainCategory().getName() + delimiter());
                    writer.write(dish.getDishNumberOfNextRecomendedCategories() + delimiter());
                    List<FoodCategory> nextRecomendedCategories = dish.getDishNextRecomendedCategory();
                    for (FoodCategory category : nextRecomendedCategories) {
                        writer.write(category.getName() + delimiter());
                    }
                    writer.write(dish.getDishTitle() + delimiter());
                    writer.write(dish.getDishRecommendedQuantity() + delimiter());
                    writer.write(dish.getDishRecommendedUnitOfQuantity() + delimiter());
                    writer.write(dish.getDishRecommendPrice() + delimiter());
                    writer.write(dish.getDishEstimatedPreparationTime() + delimiter());
                    writer.write(dish.getDishMainPhoto() + delimiter());
                    writer.write(dish.getDishNumberOfNextPhotos() + delimiter());
                    List<String> nextPhotos = dish.getDishNextPhoto();
                    for (String photo : nextPhotos) {
                        writer.write(photo + delimiter());
                    }
                    writer.newLine();
                }
            } catch (IOException e) {
                throw new RestaurantException("Chyba při ukládání dat do souboru: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zálohování souboru: " + e.getMessage());
        }
    }

     */


    public List<ActualMenu> getAmList() {return new ArrayList<>(amList);}

}

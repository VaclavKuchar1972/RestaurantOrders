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

public class DishManager {

    private List<Dish> dishList;
    public DishManager() {this.dishList = new ArrayList<>();}



    private boolean firstWriteDetector(Dish dish) {
        return dish.getDishNumberOfNextRecomendedCategory() == 0 && dish.getDishTitle().equals("Empty Title")
                && dish.getDishRecommendedQuantity() == 0  && dish.getDishRecommendPrice().compareTo(BigDecimal.ZERO) == 0
                && dish.getDishEstimatedPreparationTime() == 999999 && dish.getDishNumberOfNextPhotos() == 0;
    }
    private void removefirstWrite() {
        Iterator<Dish> iterator = dishList.iterator();
        while (iterator.hasNext()) {Dish dish = iterator.next(); if (firstWriteDetector(dish)) {iterator.remove();}}
    }

    public void addDish(Dish dish) throws RestaurantException {
        // Když tam bude první programem vytvořený zápis po prvním spuštěnmí, odstraním ho z Listu
        removefirstWrite();

        List<String> validCategoryNames = FoodCategory.getAllCategoryNames();

        /*
        if (!dishRecomendedMainCategory.getName().matches("^[A-Z0-9_]+$")) {
            throw new RestaurantException("Chyba: dishRecomendedMainCategory může obsahovat pouze velká písmena, "
                    + "číslice a znak \"_\".");
        }

         */

        if (dish.getDishRecomendedMainCategory() == null) {
            throw new RestaurantException("Chyba: dishRecomXXXXendedMainCategory nesmí být null, jídlo tedy nebylo "
                    + "přidáno.");
        }

        if (!validCategoryNames.contains(dish.getDishRecomendedMainCategory().getName())) {
            throw new RestaurantException("Chyba: Kategorie " + dish.getDishRecomendedMainCategory().getName()
                    + " neexistuje ve FoodCategory, jídlo tedy nebylo přidáno. Je nutné kategorii nejdříve přidat do "
                    + "FoodCategory a pak to půjde.");
        }


        /*

        for (FoodCategory category : dish.getDishNextRecomendedCategory()) {
            if (!validCategoryNames.contains(category.getName())) {
                throw new RestaurantException("Chyba - neplatná doporučená kategorie jídla: " + category.getName());
            }
        }

         */




        dishList.add(dish);


    }




    public void removeDish(Dish dish) {dishList.remove(dish);}
    public void removeDishByTitleAndQuantity(String dishTitle, int recommendedQuantity) throws RestaurantException {
        boolean removed = false;
        for (Dish dish : dishList) {
            if (dish.getDishTitle().equals(dishTitle) && dish.getDishRecommendedQuantity() == recommendedQuantity) {
                dishList.remove(dish); removed = true;
                break;
            }
        }
        if (!removed) {
            throw new RestaurantException("Chyba - Jídlo s názvem " + dishTitle + " a doporučeným množstvím "
                    + recommendedQuantity + " jednotek neexistuje, nelze ho tedy odebrat.");
        }
    }

    private void createEmptyDishsFile(String fileDishs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileDishs))) {
            writer.write(delimiter() + 0 + delimiter() + "Empty Title" + delimiter() + 0 + delimiter() + delimiter()
                    + "0" + delimiter() + 999999 + delimiter() + delimiter() + 0); writer.newLine();
        } catch (IOException e) {
            System.err.println("Chyba při vytváření souboru při neexistenci souboru se zásobníkem jídel: "
                    + e.getMessage());
        }
    }
    public void loadDataDishsFromFile(String fileDishs, String delimiter) throws RestaurantException {
        // OŠETŘENÍ prvního spuštění programu, když ještě nebude existovat soubor DB-Dishs.txt
        if (!Files.exists(Paths.get(fileDishs))) {
            createEmptyDishsFile(fileDishs);
            return;
        }
        int i;
        int helpBadFormatIdentificator = 0;
        String helpCategoryName = "";
        String line = "";
        String[] item = new String[0];
        FoodCategory dishRecomendedMainCategory;
        int dishNumberOfNextRecomendedCategory;
        String dishTitle;
        int dishRecommendedQuantity;
        String dishRecommendedUnitOfQuantity;
        BigDecimal dishRecommendPrice;
        int dishEstimatedPreparationTime;
        String dishMainPhoto;
        int dishNumberOfNextPhotos;

        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileDishs)))) {
            while (scannerLoadData.hasNextLine()) {
                line = scannerLoadData.nextLine();
                item = line.split(delimiter);

                /*
                try {
                    dishRecomendedMainCategory = FoodCategory.valueOf(item[0]);
                } catch (IllegalArgumentException e) {
                    // Neplatná kategorie
                    System.err.println("Chyba - neplatná kategorie na řádku: " + line);
                    // Pokračujte v načítání dalších dat, nebo se rozhodněte, jak chcete postupovat v případě chyby
                    // ...
                    dishRecomendedMainCategory = null;
                }

                 */

                dishRecomendedMainCategory = FoodCategory.valueOf(item[0]);
                if (dishRecomendedMainCategory == null) {System.err.println("Chyba - neplatná kategorie na řádku: " + line);}



                helpBadFormatIdentificator = 1;
                dishNumberOfNextRecomendedCategory = Integer.parseInt(item[1]);
                List<FoodCategory> dishNextRecomendedCategory = new ArrayList<>();
                for (i = 0; i < dishNumberOfNextRecomendedCategory; i++) {
                    dishNextRecomendedCategory.add(FoodCategory.valueOf(item[i + 2]));
                }
                dishTitle = item[2 + dishNumberOfNextRecomendedCategory];
                helpBadFormatIdentificator = 2 + dishNumberOfNextRecomendedCategory;
                dishRecommendedQuantity = Integer.parseInt(item[3 + dishNumberOfNextRecomendedCategory]);
                dishRecommendedUnitOfQuantity = item[4 + dishNumberOfNextRecomendedCategory];
                helpBadFormatIdentificator = 4 + dishNumberOfNextRecomendedCategory;
                dishRecommendPrice = new BigDecimal(item[5 + dishNumberOfNextRecomendedCategory]);
                helpBadFormatIdentificator = 5 + dishNumberOfNextRecomendedCategory;
                dishEstimatedPreparationTime = Integer.parseInt(item[6 + dishNumberOfNextRecomendedCategory]);
                dishMainPhoto = item[7 + dishNumberOfNextRecomendedCategory];
                helpBadFormatIdentificator = 7 + dishNumberOfNextRecomendedCategory;
                dishNumberOfNextPhotos = Integer.parseInt(item[8 + dishNumberOfNextRecomendedCategory]);
                if (item.length != 9 + dishNumberOfNextRecomendedCategory + dishNumberOfNextPhotos) {
                    throw new RestaurantException("Chyba - špatný počet položek na řádku: " + line);
                }
                List<String> dishNextPhoto = new ArrayList<>();
                for (i = 0; i < dishNumberOfNextPhotos; i++) {
                    dishNextPhoto.add(item[i + 9 + dishNumberOfNextRecomendedCategory]);
                }
                Dish newDish = new Dish(dishRecomendedMainCategory, dishNumberOfNextRecomendedCategory,
                        dishNextRecomendedCategory, dishTitle, dishRecommendedQuantity, dishRecommendedUnitOfQuantity,
                        dishRecommendPrice, dishEstimatedPreparationTime, dishMainPhoto, dishNumberOfNextPhotos,
                        dishNextPhoto);
                dishList.add(newDish);
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileDishs + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Chyba - v databázi není číslo: " + " na řádku: " + line + " položka č."
                    + helpBadFormatIdentificator);
        }
    }

    public void saveDataDishsToFile(String fileName) throws RestaurantException {
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
                    writer.write(dish.getDishNumberOfNextRecomendedCategory() + delimiter());
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

    public List<Dish> getDishList() {return new ArrayList<>(dishList);}

}

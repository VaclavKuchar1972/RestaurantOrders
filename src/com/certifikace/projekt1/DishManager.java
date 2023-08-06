package com.certifikace.projekt1;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static com.certifikace.projekt1.RestaurantSettings.delimiter;

public class DishManager {

    private List<Dish> dishList;
    public DishManager() {this.dishList = new ArrayList<>();}



    private boolean firstWriteDetector(Dish dish) {
        return dish.getDishNumberOfNextRecomendedCategory() == 0 && dish.getDishTitle().equals("Empty Title")
                && dish.getDishNumberOfNextPhotos() == 0;
    }
    private void removefirstWrite() {
        /*
        Iterator může být použit pro procházení kolekcí bez ohledu na jejich typ. Nemusím tedy specifikovat, jaká
        kolekce se používá (seznam, množina, mapa atd.), ale můžu použít stejný způsob pro iteraci jakékoliv kolekce.
        Díky tomu by kód měl být flexibilnější a jednodušší na údržbu. Iterator jsem použil k tomu, abych prošel
        seznamem dishList a vyhledal, jestli prvek odpovídá specifickému záznamu, který chci odstranit.
         */
        Iterator<Dish> iterator = dishList.iterator();
        while (iterator.hasNext()) {Dish dish = iterator.next(); if (firstWriteDetector(dish)) {iterator.remove();}}
    }
    public void addDish(Dish dish) throws RestaurantException {

        // Když tam bude první programem vytvořený zápis po prvním spuštěnmí, odstraním ho z Listu
        if (firstWriteDetector(dish)) {removefirstWrite();}




        dishList.add(dish);
    }

    private void createEmptyDishsFile(String fileDishs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileDishs))) {
            writer.write(delimiter() + "0" + delimiter() + "Empty Title" + delimiter() + delimiter() + delimiter()
                    + delimiter() + delimiter() + delimiter() + "0"); writer.newLine();
        } catch (IOException e) {
            System.err.println("Chyba při vytváření souboru při neexistenci souboru se zásobníkem jídel: "
                    + e.getMessage());
        }
    }
    public void loadDataDishsFromFile(String fileDishs, String delimiter) throws RestaurantException {
        // OŠETŘENÍ prvního spuštění programu, když ještě nebude existovat soubor DB-Dishs.txt
        if (!Files.exists(Paths.get(fileDishs))) {createEmptyDishsFile(fileDishs); return;}
        int i; int helpBadFormatIdentificator = 0;
        String line = ""; String[] item = new String[0];
        FoodCategory dishRecomendedMainCategory; int dishNumberOfNextRecomendedCategory; String dishTitle;
        int dishRecommendedQuantity; String dishRecommendedUnitOfQuantity; BigDecimal dishRecommendPrice;
        int dishEstimatedPreparationTime; String dishMainPhoto; int dishNumberOfNextPhotos;
        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileDishs)))) {
            while (scannerLoadData.hasNextLine()) {
                line = scannerLoadData.nextLine();
                item = line.split(delimiter);
                dishRecomendedMainCategory = FoodCategory.getInstance().valueOf(item[0]);
                helpBadFormatIdentificator = 1;
                dishNumberOfNextRecomendedCategory = Integer.parseInt(item[1]);
                List<FoodCategory> dishNextRecomendedCategory = new ArrayList<>();
                for (i = 0; i < dishNumberOfNextRecomendedCategory; i++) {
                    dishNextRecomendedCategory.add(FoodCategory.getInstance().valueOf(item[i + 2]));
                }
                dishTitle = item[2 + dishNumberOfNextRecomendedCategory];
                helpBadFormatIdentificator = 2 + dishNumberOfNextRecomendedCategory;
                dishRecommendedQuantity = Integer.parseInt(item[3 + dishNumberOfNextRecomendedCategory]);
                dishRecommendedUnitOfQuantity = item[4 + dishNumberOfNextRecomendedCategory];
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

    public List<Dish> getDishList() {return new ArrayList<>(dishList);}

}

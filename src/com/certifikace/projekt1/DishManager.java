package com.certifikace.projekt1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DishManager {

    private List<Dish> dishList;
    public DishManager() {this.dishList = new ArrayList<>();}



    public void loadDataDishsFromFile(String fileDishs, String delimiter) throws RestaurantException {

        // CHYBÍ!!! OŠETŘENÍ prvního spuštění programu, když ještě nebude existovat soubor DB-Dishs.txt


        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileDishs)))) {
            while (scannerLoadData.hasNextLine()) {
                String line = scannerLoadData.nextLine();
                String[] item = line.split(delimiter);

                // Ošetřit špatný počet položek na řádku!!!
                //if (item.length != 12) {
                //    throw new RestaurantException("Chyba - špatný počet položek na řádku: " + line);
                //}

                // Ošetřit, že když to nalouduje kategorii, která není ve FoodCategory, přidá jí tam

                FoodCategory dishRecomendedMainCategory = FoodCategory.valueOf(item[0]);

                int dishNumberOfNextRecomendedCategory = Integer.parseInt(item[1]);
                List<FoodCategory> dishNextRecomendedCategory = new ArrayList<>();
                for (int i = 0; i < dishNumberOfNextRecomendedCategory; i++) {
                    dishNextRecomendedCategory.add(FoodCategory.valueOf(item[i]));
                }

                String dishTitle = item[2 + dishNumberOfNextRecomendedCategory];
                // int dishRecommendedQuantity = Integer.parseInt(item[4 + dishNumberOfNextRecomendedCategory].trim());
                int dishRecommendedQuantity = Integer.parseInt(item[3 + dishNumberOfNextRecomendedCategory]);
                String dishRecommendedUnitOfQuantity = item[4 + dishNumberOfNextRecomendedCategory];
                BigDecimal dishRecommendPrice = new BigDecimal(item[5 + dishNumberOfNextRecomendedCategory]);
                int dishEstimatedPreparationTime = Integer.parseInt(item[6 + dishNumberOfNextRecomendedCategory]);
                String dishMainPhoto = item[7 + dishNumberOfNextRecomendedCategory];



                int dishNumberOfNextPhotos = Integer.parseInt(item[8 + dishNumberOfNextRecomendedCategory]);
                List<String> dishNextPhoto = new ArrayList<>();
                for (int i = 9 + dishNumberOfNextRecomendedCategory; i < 9 + dishNumberOfNextRecomendedCategory
                        + dishNumberOfNextPhotos; i++) {dishNextPhoto.add(item[i]);}

                Dish newDish = new Dish(dishRecomendedMainCategory, dishNumberOfNextRecomendedCategory,
                        dishNextRecomendedCategory, dishTitle, dishRecommendedQuantity, dishRecommendedUnitOfQuantity,
                        dishRecommendPrice, dishEstimatedPreparationTime, dishMainPhoto, dishNumberOfNextPhotos,
                        dishNextPhoto);
                dishList.add(newDish);
            }

            // DOLADIT!!! ty ošetření
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileDishs + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Chyba - špatný formát čísla v databázi na řádku: " + e.getMessage());
        }
    }


    public List<Dish> getDishList() {return new ArrayList<>(dishList);}

}

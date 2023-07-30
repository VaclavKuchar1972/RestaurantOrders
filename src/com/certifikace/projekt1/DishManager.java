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

        int i;
        String line = "";
        String[] item = new String[0];
        FoodCategory dishRecomendedMainCategory; int dishNumberOfNextRecomendedCategory; String dishTitle;
        int dishRecommendedQuantity; String dishRecommendedUnitOfQuantity; BigDecimal dishRecommendPrice;
        int dishEstimatedPreparationTime; String dishMainPhoto; int dishNumberOfNextPhotos;
        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileDishs)))) {
            while (scannerLoadData.hasNextLine()) {
                line = scannerLoadData.nextLine();
                item = line.split(delimiter);

                // Ošetřit, že když to nalouduje kategorii, která není ve FoodCategory, přidá jí tam

                dishRecomendedMainCategory = FoodCategory.getInstance().valueOf(item[0]);
                dishNumberOfNextRecomendedCategory = Integer.parseInt(item[1]);
                List<FoodCategory> dishNextRecomendedCategory = new ArrayList<>();
                for (i = 0; i < dishNumberOfNextRecomendedCategory; i++) {
                    dishNextRecomendedCategory.add(FoodCategory.getInstance().valueOf(item[i + 2]));
                }

                dishTitle = item[2 + dishNumberOfNextRecomendedCategory];
                dishRecommendedQuantity = Integer.parseInt(item[3 + dishNumberOfNextRecomendedCategory]);
                dishRecommendedUnitOfQuantity = item[4 + dishNumberOfNextRecomendedCategory];
                dishRecommendPrice = new BigDecimal(item[5 + dishNumberOfNextRecomendedCategory]);
                dishEstimatedPreparationTime = Integer.parseInt(item[6 + dishNumberOfNextRecomendedCategory]);

                dishMainPhoto = item[7 + dishNumberOfNextRecomendedCategory];
                dishNumberOfNextPhotos = Integer.parseInt(item[8 + dishNumberOfNextRecomendedCategory]);
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
            throw new RestaurantException("Chyba - špatný formát čísla v databázi na řádku: " + " na řádku: " + line);
        }
    }

    public List<Dish> getDishList() {return new ArrayList<>(dishList);}

}

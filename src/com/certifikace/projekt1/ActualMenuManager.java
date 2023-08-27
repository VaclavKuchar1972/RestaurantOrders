package com.certifikace.projekt1;

import java.util.ArrayList;
import java.util.List;

public class ActualMenuManager {

    private List<ActualMenu> amList;

    public ActualMenuManager() {this.amList = new ArrayList<>();}


    public void addFoodToMenu(String title, int quantity, DishManager dishManager) throws RestaurantException {
        for (Dish dish : dishManager.getDishList()) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {
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
                + " dishList. Jídlo NEBYLO přidáno do amList!");
    }

    public void removeFoodFromMenu(String title, int quantity) {
        if (amList.isEmpty()) {
            System.err.println("Chyba: amList je prázdný! Jídlo s názvem " + title + " a množstvím " + quantity
                    + " nelze odtrantit z amList.");
            return;}
        ActualMenu foodToRemove = null;
        for (ActualMenu food : amList) {
            if (food.getAmTitle().equals(title) && food.getAmQuantity() == quantity) {foodToRemove = food; break;}
        }
        if (foodToRemove != null) {amList.remove(foodToRemove);}
        else {
            System.err.println("Chyba: Jídlo s názvem " + title + " a množstvím " + quantity + " nebylo nalezeno v "
                    + "amList. Nelze ho tedy odstranit.");
        }
    }






    public List<ActualMenu> getAmList() {return new ArrayList<>(amList);}

}

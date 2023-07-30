package com.certifikace.projekt1;

import java.util.ArrayList;
import java.util.List;

public class FoodCategoryManager {
    private static FoodCategoryManager instance;
    public FoodCategoryManager() {
        // Private konstruktor pro singleton
        // Nějaká inicializace nebo načtení dat pro FoodCategoryManager
        // Například inicializace spojení s databází, načtení konfiguračních parametrů, apod.
    }
    public static FoodCategoryManager getInstance() {
        if (instance == null) {instance = new FoodCategoryManager();}
        return instance;
    }
    public void addCategory(String name, String description) {
        FoodCategory.getInstance().addCategory(name, description);
    }
    public void removeCategory(String name) {
        FoodCategory.getInstance().removeCategory(name);
    }
    public List<FoodCategory> getFoodCategories() {return FoodCategory.getInstance().getAllCategories();}
    public FoodCategory getCategoryByName(String name) {return FoodCategory.getInstance().getCategoryByName(name);}


}
package com.certifikace.projekt1;

import java.util.ArrayList;
import java.util.List;

public class FoodCategoryManager {
    private static FoodCategoryManager instance;

    public static FoodCategoryManager getInstance() {
        if (instance == null) {
            instance = new FoodCategoryManager();
        }
        return instance;
    }

    private FoodCategoryManager() {
        // Private konstruktor pro singleton
        // Nějaká inicializace nebo načtení dat pro FoodCategoryManager
        // Například inicializace spojení s databází, načtení konfiguračních parametrů, apod.
    }

    public List<FoodCategory> getFoodCategories() {
        return FoodCategory.getInstance().getAllCategories();
    }

    public FoodCategory getCategoryByName(String name) {
        return FoodCategory.getInstance().getCategoryByName(name);
    }
}
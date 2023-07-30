package com.certifikace.projekt1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//  Kategorie nemám jako ENUM, jak jsme se to učili, protože ENUM je v Javě je statická a nemůže se měnit za běhu
//  programu. Takže nemůžu jednoduše přidávat nové položky do enum třídy za běhu programu. Také musím splnit to,
//  že když program selže nebo někdo vypne počítač, tak se mi nově přidané, resp. aktuální kategorie načtou ze souboru.
public class FoodCategory {

    private String name; private String description;
    public FoodCategory(String name, String description) {this.name = name; this.description = description;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    @Override
    public String toString() {return name + ", " + description;}

    private static FoodCategory instance;
    public static FoodCategory getInstance() {
        if (instance == null) {instance = new FoodCategory(); instance.loadDataCategoriesFromFile();}
        return instance;
    }

    private FoodCategory() {
        // Private konstruktor pro singleton
        // Nějaká inicializace nebo načtení dat pro FoodCategoryManager
        // Například inicializace spojení s databází, načtení konfiguračních parametrů, apod.
    }

    private Map<String, FoodCategory> categoriesMap = new HashMap<>();

    private void loadDataCategoriesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RestaurantSettings.fileFoodCategories()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] item = line.split(RestaurantSettings.delimiter());
                if (item.length == 2) {
                    String name = item[0].trim();
                    String description = item[1].trim();
                    FoodCategory category = new FoodCategory(name, description);
                    categoriesMap.put(name, category);
                }
            }
        } catch (IOException e) {
            System.err.println("Chyba při načítání kategorií ze souboru DB-FoodCategories.txt: " + e.getMessage());
        }
    }

    public List<FoodCategory> getAllCategories() {return new ArrayList<>(categoriesMap.values());}
    public FoodCategory getCategoryByName(String name) {return categoriesMap.get(name);}
    public static FoodCategory valueOf(String name) {return getInstance().getCategoryByName(name);}


}

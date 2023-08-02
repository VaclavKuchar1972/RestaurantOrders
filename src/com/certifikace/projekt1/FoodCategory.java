package com.certifikace.projekt1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.certifikace.projekt1.RestaurantSettings.*;
import static com.certifikace.projekt1.RestaurantSettings.fileFoodCategories;

//  Kategorie nemám jako ENUM, jak jsme se to učili, protože ENUM je v Javě statická a nemůže se měnit za běhu
//  programu. Takže nemůžu jednoduše přidávat nové položky do enum třídy za běhu programu. Také musím splnit to,
//  že když program selže nebo někdo vypne systém, tak se mi nově přidané, resp. aktuální kategorie načtou ze souboru.
public class FoodCategory {

    private String name; private String description;
    public FoodCategory(String name, String description) {this.name = name; this.description = description;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    @Override
    public String toString() {return description;}

    private static FoodCategory instance;
    public static FoodCategory getInstance() {
        if (instance == null) {
            instance = new FoodCategory();
            try {instance.loadDataCategoriesFromFile();}
            catch (RestaurantException e) {System.err.println("Chyba: " + e.getMessage());}
            return instance;
    }
        return null;
    }
    public FoodCategory() {
        try {loadDataCategoriesFromFile();}
        catch (RestaurantException e) {System.err.println("Chyba: " + e.getMessage());}
        // Private konstruktor pro singleton
        // Nějaká inicializace nebo načtení dat pro FoodCategoryManager
        // Například inicializace spojení s databází, načtení konfiguračních parametrů, apod.
    }

    // Pomocný Boolean pro kontrolu, zda je název psán velkými písmeny a neobsahuje mezery
    private boolean ifIsValidCategoryName(String name) {return name.matches("^[A-Z]+$");}
    public void addCategory(String name, String description) throws RestaurantException {
        if (!ifIsValidCategoryName(name)) {
            System.err.println("Chyba: Název kategorie musí být psán velkými písmeny a nesmí obsahovat mezery, "
                    + "kategorii tedy nelze přidat.");
            return;
        }

        // Ošetření prvního spuštění programu, kdy je soubor DB-FoodCategories.txt prázdný resp. obsahuje nesmysly
        // po prvním spuštění
        if (categoriesMap.isEmpty()) {
            FoodCategory emptyCategory = new FoodCategory(name, description);
            categoriesMap.put(name, emptyCategory);
        } else {
            // Nahrazení prázdné kategorie skutečnými daty z FrontEndu, pokud byla zadána nová kategorie.
            // To se stane pouze v případě, že soubor s kategoriemi nubude obsahovat jen jednu položku, pak ať si tam
            // na FrontEndu, dělají co chtějí, resp. uživatel. :-) Spíš asi oba, FrontEnd musí zajistit, aby tady "name"
            // kategorie přistálo bez mezer a s velkými písmeny, jinak bude zase zle... :-) ...ale jinak, můžou tam
            // přidat to samé co jsem si vymyslel, ale nebudou tam prostě dvě stejný věci duplicitně, to nikdo nechce,
            // nebo jedna zbytačná po prvním spuštění, to nechce taky nikdo. Takhle složitě je to proto, aby někdo
            // později mohl přidat i tuto "blbost", kdyby chtěl, aby uživatel nebyl omezen programátorem.
            if (categoriesMap.containsKey("EMPTYCATEGORY") && categoriesMap.size() == 1) {
                FoodCategory emptyCategory = categoriesMap.get("EMPTYCATEGORY");
                emptyCategory.name = name;
                emptyCategory.description = description;
            } else {
                if (!categoriesMap.containsKey(name)) {
                    FoodCategory category = new FoodCategory(name, description);
                    categoriesMap.put(name, category);
                } else {
                    System.err.println("Chyba: Kategorie s názvem " + name + " již existuje, nelze ji tedy přidat.");
                }
            }
        }

        try {saveDataCategoriesToFile();}
        catch (RestaurantException e) {
            System.err.println("Chyba: Při přidávání kategorie při pokusu o uložení: " + e.getMessage());
        }
    }

    public void removeCategory(String name) {
        if (categoriesMap.containsKey(name)) {
            categoriesMap.remove(name);
        } else {System.err.println("Kategorie s názvem " + name + " neexistuje, nelze jí tedy odebrat");}
        try {saveDataCategoriesToFile();} catch (RestaurantException e) {
            System.err.println("Chyba při odebírání kategorie při pokusu o uložení" + e.getMessage());
        }
    }

    private Map<String, FoodCategory> categoriesMap = new HashMap<>();

    private void createFoodCategoriesFile(String fileFoodCategories) throws RestaurantException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileFoodCategories))) {
            writer.write("EMPTYCATEGORY" + delimiter() + "prázdná kategorie");
            writer.newLine();
        } catch (IOException e) {
            throw new RestaurantException("Chyba při vytváření souboru při neexistenci souboru s Kategoriemi: "
                    + e.getMessage());
        }
    }
    private void loadDataCategoriesFromFile() throws RestaurantException {
        // OŠETŘENÍ prvního spuštění programu, když ještě nebude existovat soubor DB-FoodCategories.txt
        if (!Files.exists(Paths.get(fileFoodCategories()))) {
            createFoodCategoriesFile(fileFoodCategories());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileFoodCategories()))) {
            String line;
            String name;
            String description;
            while ((line = reader.readLine()) != null) {
                String[] item = line.split(RestaurantSettings.delimiter());
                if (item.length == 2) {
                    name = item[0].trim();
                    description = item[1].trim();
                    if (ifIsValidCategoryName(name)) {
                        FoodCategory category = new FoodCategory(name, description);
                        categoriesMap.put(name, category);
                    } else {
                        // Instead of printing the error, throw the RestaurantException
                        throw new RestaurantException("Chyba: Formát kategorie v souboru DB-FoodCategories.txt je nesprávný. Název"
                                + " kategorie musí být psán velkými písmeny a nesmí obsahovat mezery. Kategorie "
                                + "s názvem " + name + " bude ignorována.");
                    }
                }
            }
        } catch (IOException e) {
            // Instead of printing the error, throw the RestaurantException
            throw new RestaurantException("Chyba při načítání kategorií ze souboru DB-FoodCategories.txt: " + e.getMessage());
        }
    }

    public void saveDataCategoriesToFile() throws RestaurantException {
        try {
            // Zálohování souboru před uložením nových hodnot do primárního souboru
            File originalFile = new File(fileFoodCategories());
            File backupFile = new File(fileFoodCategoriesBackUp());
            Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // Uložení nových dat do primárního souboru
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile))) {
                List<FoodCategory> categories = getAllCategories();
                for (FoodCategory category : categories) {
                    writer.write(category.getName() + RestaurantSettings.delimiter() + category.getDescription());
                    writer.newLine();
                }
            } catch (IOException e) {
                throw new RestaurantException("Chyba při ukládání kategorií do souboru DB-FoodCategories.txt: "
                        + e.getMessage());
            }
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zálohování souboru DB-FoodCategories.txt: " + e.getMessage());
        }
    }

    public List<FoodCategory> getAllCategories() {return new ArrayList<>(categoriesMap.values());}

    public FoodCategory getCategoryByName(String name) {return categoriesMap.get(name);}
    public static FoodCategory valueOf(String name) {return getInstance().getCategoryByName(name);}


}

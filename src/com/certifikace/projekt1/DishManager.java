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
        return dish.getDishNumberOfNextRecomendedCategories() == 0 && dish.getDishTitle().equals("Empty Title")
                && dish.getDishRecommendedQuantity() == 0  && dish.getDishRecommendPrice().compareTo(BigDecimal.ZERO) == 0
                && dish.getDishEstimatedPreparationTime() == 999999 && dish.getDishNumberOfNextPhotos() == 0;
    }
    private void removefirstWrite() {
        Iterator<Dish> iterator = dishList.iterator();
        while (iterator.hasNext()) {Dish dish = iterator.next(); if (firstWriteDetector(dish)) {iterator.remove();}}
    }
    public void addDish(Dish dish) throws RestaurantException {
        // Když tam bude první programem vytvořený zápis po prvním spuštěnmí, odstraním se z Listu
        removefirstWrite();

        String helpSameErrMessage =  " Jídlo NEBYLO přidáno do dishList!";
        if (dish.getDishRecomendedMainCategory() == null) {
            System.err.println("Chyba: Pokoušíte se přidat kategorii, která má hodnotu null nebo má neplatný formát "
                    + "nebo neexistuje v seznamu kategorií ve FoodCategory a je třeba jí před přidáním jídla přidat do "
                    + "seznamu kategorií." + helpSameErrMessage);
            return;
        }
        if (dish.getDishNumberOfNextRecomendedCategories() < 0) {
            System.err.println("Chyba: Pokoušíte se přidat jídlo se záporným počtem dalších doporučených "
                    + "kategorií do zásobníku, to nejde." + helpSameErrMessage);
            return;
        }
        if (dish.getDishTitle() == null || dish.getDishTitle().isEmpty()) {
            System.err.println("Chyba: Pokoušíte se přidat jídlo s prázdným názvem jídla, to nejde."
                    + helpSameErrMessage);
            return;
        }
        if (dish.getDishRecommendedQuantity() < 1) {
            throw new RestaurantException("Chyba: Pokoušíte se přidat jídlo s nulovým nebo záporným doporučeným " +
                    "množstvím k prodeji: " + dish.getDishRecommendedQuantity() + ", to nejde." + helpSameErrMessage);
        }
        for (Dish existingDish : dishList) {
            if (existingDish.dishDetectSameTitleAndQuantity(dish.getDishTitle(), dish.getDishRecommendedQuantity())) {
                System.err.println("Chyba: Jídlo s názvem " + dish.getDishTitle() + " a doporučeným množstvím "
                        + dish.getDishRecommendedQuantity() + " již existuje v seznamu kategorií ve FoodCategory a "
                        + "nelze ho tedy přidat do seznamu kategorií." + helpSameErrMessage);
                return;
            }
        }
        if (dish.getDishRecommendPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new RestaurantException("Chyba: Pokoušíte se přidat jídlo se zápornou doporučenou cenou "
                    + dish.getDishRecommendPrice() + ",- Kč, to by opravdu nešlo. ...možná \"akcička\" za nula, ale "
                    + "jinak?!... :D" + helpSameErrMessage);
        }
        if (dish.getDishEstimatedPreparationTime() < 1) {
            throw new RestaurantException("Chyba: Pokoušíte se přidat jídlo s nulovou nebo zápornou předpokládanou "
                    + "dobou přípravy: " + dish.getDishEstimatedPreparationTime() + " minut, to fakt nejde."
                    + helpSameErrMessage);
        }

        for (char forbiddenCharacter : forbiddenFileCharacters.toCharArray()) {
            if (dish.getDishMainPhoto().contains(String.valueOf(forbiddenCharacter))) {
                throw new RestaurantException ("Chyba: Pokoušíte se přidat jídlo s názvem hlavní fotogarfie, který "
                        + "obsahuje nepovolený znak: " + forbiddenCharacter + "." + helpSameErrMessage);
            }
        }
        if (dish.getDishMainPhoto() == null || dish.getDishMainPhoto().equals("")) {dish.setDishMainPhoto("blank");}


        if (dish.getDishNumberOfNextPhotos() < 0) {
            throw new RestaurantException ("Chyba: Pokoušíte se přidat jídlo se záporným počtem dalších fotografií, to "
                    + "nejde." + helpSameErrMessage);
        }
        if (dish.getDishNumberOfNextPhotos() > 0) {
            for (String nextPhoto : dish.getDishNextPhoto()) {
                for (char forbiddenCharacter : forbiddenFileCharacters.toCharArray()) {
                    if (nextPhoto.contains(String.valueOf(forbiddenCharacter))) {
                        throw new RestaurantException ("Chyba: Pokoušíte se přidat jídlo, které obsahuje jednu z "
                                + "dalších další fotografií, jejíž název obsahuje zakázaný znak: " + forbiddenCharacter
                                + "." + helpSameErrMessage);
                    }
                }
            }
        }
        dishList.add(dish);
    }

    // Nevidím důvod, proč by nemohli existovat dvě jídla se shodným názvem, ale jen s odlišným množstvím jednotek,
    // které se hostovi prodají. Když tam budou, nic se neděje, restauratér si jen vybere, které z nich chce dát na Menu
    // a když bude chtít tam dát obě tak je tam dá obě a každé bude prodávat s jinou cenou. Např. někdo chce guláš se
    // šesti tak tam bude jako příloha Knedlík s jednotkou 6 a ne 4.
    public void removeDishByTitleAndQuantity(String dishTitle, int recommendedQuantity) throws RestaurantException {
        for (Dish dish : dishList) {
            if (dish.dishDetectSameTitleAndQuantity(dishTitle, recommendedQuantity)) {dishList.remove(dish); return;}
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + dishTitle + " a doporučeným množstvím " +
                recommendedQuantity + " jednotek neexistuje v seznamu kategorií, nelze ho tedy odebrat z dishList.");
    }

    // V mém projektu nelze mít jídlo v zásobníku bez hlavní kategorie, lze ji pouze nahradit, je to obdobné jako s tou
    // hlavní fotkou dle zadání Projektu 1 a myslím, že je to tak správné. Lépe se pak budou třídit i jídla na jídelním
    // lístku pro případný výstup na tiskárnu nebo i na web, kdyby se měl jídelní lístek předkádat hostům i
    // elektornicky.
    public void replaceDishRecomendedMainCategoryByTitleAndQuantity
    (String title, int quantity, FoodCategory newRecomendedMainCategory) throws RestaurantException {
        for (Dish dish : dishList) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {
                if (newRecomendedMainCategory != null) {dish.setDishRecomendedMainCategory(newRecomendedMainCategory);}
                else {System.err.println("Chyba: Pokoušíte se změnit dopručenou hlavní kategorii jídla v zásobníku, "
                        + "tato kategorie ale nemá platný formát nebo neexistuje ve FoodCategory a je třeba ji tam "
                        + "nejdříve přidat. Hlavní doporučená kategorie u jídla " + title + " s dopručeným množstvím "
                        + quantity + " jednotek NEBYLA nahrazena Vámi požadovanou novou kategorií v dishList!" );}
                return;
            }
        }
        System.err.println ("Chyba: Jídlo s názvem " + title + " a množstvím " + quantity + " nebylo nalezeno v "
                + "dishList. Hlavní kategorie u jídla tedy NEMOHLA být nahrazena.");
    }

    public void addDishNextRecomendedCategoryByTitleAndQuantity
            (String title, int quantity, String newRecomendedNextCategory) throws RestaurantException{
        String helpSameErrMessage =  " Kategorie NEBYLA přídána k příslušnému jídlu do dishList!";
        for (Dish dish : dishList) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {
                FoodCategory newCategory = FoodCategory.getInstance().getCategoryByName(newRecomendedNextCategory);
                if (newCategory == null) {
                    System.err.println("Chyba: Další doporučená kategorie, kterou se pokoušíte přidat do  \""
                            + newRecomendedNextCategory + "\" nemá platný formát nebo neexistuje ve FoodCategory a je "
                            + "třeba jí tam nejdříve přidat." + helpSameErrMessage);
                    return;
                }
                List<FoodCategory> existingCategories = new ArrayList<>(dish.getDishNextRecomendedCategory());
                existingCategories.add(dish.getDishRecomendedMainCategory());
                for (FoodCategory existingCategory : existingCategories) {
                    if (existingCategory.equals(newCategory)) {
                        System.err.println("Chyba: Další doporučená kategorie, kterou se pokoušíte přidat \""
                                + newRecomendedNextCategory + "\" již existuje u jídla " + title
                                + " s doporučeným množstvím " + quantity + " jednotek." + helpSameErrMessage);
                        return;
                    }
                }
                dish.getDishNextRecomendedCategory().add(newCategory);
                dish.setDishNumberOfNextRecomendedCategories(dish.getDishNextRecomendedCategory().size());
                return;
            }
        }
        System.err.println("Chyba: Jídlo s názvem " + title + " a množstvím " + quantity
                + " nebylo nalezeno v dishList" + helpSameErrMessage);
    }

    public void removeAddressedDishNextRecomendedCategoryByTitleAndQuantity(
            String title, int quantity, String categoryNameToRemove) throws RestaurantException {
        String helpSameErrMessage = " Další dopručená kategorie NEBYLA odebrána z příslušného jídla z dishList!";
        boolean categoryFound = false;
        for (Dish dish : dishList) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {
                List<FoodCategory> nextRecommendedCategories = dish.getDishNextRecomendedCategory();
                if (nextRecommendedCategories.isEmpty()) {
                    System.err.println("Chyba: Žádná další doporučená kategorie u jídla " + title + " s doporučeným "
                            + "množstvím " + quantity + "neexistuje, ArrayList nextRecommendedCategories je prázdný."
                            + helpSameErrMessage);
                    return;
                }
                FoodCategory categoryToRemove = FoodCategory.getInstance().getCategoryByName(categoryNameToRemove);
                if (categoryToRemove == null) {
                    System.err.println("Chyba: Kategorie s názvem " + categoryNameToRemove + " neexistuje ve "
                            + "FoodCategory." + helpSameErrMessage);
                    return;
                }
                List<FoodCategory> existingCategories = new ArrayList<>(nextRecommendedCategories);
                existingCategories.add(dish.getDishRecomendedMainCategory());
                for (FoodCategory existingCategory : existingCategories) {
                    if (existingCategory.equals(categoryToRemove)) {categoryFound = true; break;}
                }
                if (categoryFound) {
                    nextRecommendedCategories.remove(categoryToRemove);
                    dish.setDishNumberOfNextRecomendedCategories(nextRecommendedCategories.size());
                } else {
                    System.err.println("Chyba: Další doporučená kategorie " + categoryNameToRemove + " neexistuje u "
                            + "jídla " + title + " s doporučeným množstvím " + quantity + " jednotek."
                            + helpSameErrMessage);
                }
                return;
            }
        }
        System.err.println("Chyba: Jídlo s názvem " + title + " a množstvím " + quantity + " nebylo nalezeno "
                + "v dishList." + helpSameErrMessage);
    }

    public void renameDishTitleByTitleAndQuantity(String title, int quantity, String newDishTitle)
            throws RestaurantException {
        for (Dish dish : dishList) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {dish.setDishTitle(newDishTitle); return;}
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + title + " a doporučeným množstvím " +
                quantity + " jednotek nebylo nalezeno v dishList, nelze ho tedy přejmenovat.");
    }

    public void addDishSameTitleWithDifferentQuantityAndPrice
            (String title, int newRecommendedQuantity, BigDecimal newRecommendedPrice)
            throws RestaurantException {
        String helpSameErrMessage = " Nové jídlo NEBYLO přidáno do dishList!";
        if (newRecommendedQuantity < 1) {
            throw new RestaurantException("Chyba: Pokoušíte se přidat jídlo s nulovým nebo záporným doporučeným " +
                    "množstvím k prodeji: " + newRecommendedQuantity + ", to nejde." + helpSameErrMessage);
        }
        if (newRecommendedPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new RestaurantException("Chyba: Pokoušíte se přidat jídlo se zápornou doporučenou cenou "
                    + newRecommendedPrice + ",- Kč, to by opravdu nešlo. ...možná \"akcička\" za nula, ale jinak?!... "
                    + ":D" + helpSameErrMessage);
        }
        boolean existingDishFound = false; Dish existingDish = null;
        for (Dish dish : dishList) {
            if (dish.getDishTitle().equals(title)
                    && dish.getDishRecommendedQuantity() == newRecommendedQuantity) {
                existingDishFound = true;
                break;
            }
        }
        if (existingDishFound) {
            throw new RestaurantException("Chyba: Jídlo se zadaným názvem " + title + "a stejným množstvím"
                    + newRecommendedQuantity + "již existuje v dishList. " + helpSameErrMessage);
        }
        for (Dish dish : dishList) {
            if (dish.getDishTitle().equals(title)) {existingDish = dish; break;}}
        if (existingDish != null) {
            Dish newDish = new Dish(
                    existingDish.getDishRecomendedMainCategory(), existingDish.getDishNumberOfNextRecomendedCategories(),
                    existingDish.getDishNextRecomendedCategory(), existingDish.getDishTitle(), newRecommendedQuantity,
                    existingDish.getDishRecommendedUnitOfQuantity(), newRecommendedPrice,
                    existingDish.getDishEstimatedPreparationTime(), existingDish.getDishMainPhoto(),
                    existingDish.getDishNumberOfNextPhotos(), existingDish.getDishNextPhoto()
            );
            dishList.add(newDish);
        } else {
            throw new RestaurantException("Chyba: Jídlo se zadaným názvem " + title + " a doporučeným množstvím "
                    + newRecommendedQuantity + " nebylo nalezeno v dishList." + helpSameErrMessage);
        }
    }

    public void replaceDishRecommendedQuantityByTitleAndQuantity
            (String title, int quantity, int newDishRecomendedQuantity)
            throws RestaurantException {
        String helpSameErrMessage = " Nové doporučené množství NEBYLO změněno!";
        if (newDishRecomendedQuantity < 1) {
            throw new RestaurantException("Chyba: Pokoušíte se změnit doporučené množství u jídla " + title
                    + " na nové doporučené množstvím k prodeji: " + newDishRecomendedQuantity + ", tedy s nulovou nebo "
                    + "zápornou hodnotou, to nejde." + helpSameErrMessage);
        }
        for (Dish dish : dishList) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {
                dish.setDishRecommendedQuantity(newDishRecomendedQuantity); return;}
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + title + " a doporučeným množstvím " + quantity
                + " jednotek nebylo nalezeno v dishList." + helpSameErrMessage);
    }

    public void replaceDishRecommendedPriceByTitleAndQuantity
            (String title, int quantity, BigDecimal newRecommendedPrice)
            throws RestaurantException {
        String helpSameErrMessage = " Nová doporučená cena NEBYLA změněna!";
        if (newRecommendedPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new RestaurantException("Chyba: Pokoušíte se změnit doporučenou cenu u jídla " + title + " na novou "
                    + "doporučenou cenu k prodeji: " + newRecommendedPrice.toString() + ",- Kč, to by opravdu nešlo. ...možná "
                    + " \"akcička\" za nula, ale jinak?!... :D" + helpSameErrMessage);
        }
        for (Dish dish : dishList) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {
                dish.setDishRecommendPrice(newRecommendedPrice); return;}
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + title + " a doporučeným množstvím " + quantity
                + " jednotek nebylo nalezeno v dishList." + helpSameErrMessage);
    }

    public void replaceDishEstimatedPreparationTimeByTitleAndQuantity
            (String title, int quantity, int newDishEstimatedPreparationTime)
            throws RestaurantException {
        String helpSameErrMessage = " Nový předpokládaný čas přípravy jídla NEBYL změněn!";
        if (newDishEstimatedPreparationTime < 1) {
            throw new RestaurantException("Chyba: Pokoušíte se změnit předpokládaný čas přípravy u jídla " + title
                    + " na nový nulový nebo záporný: " + newDishEstimatedPreparationTime + " minut. To nejde."
                    + helpSameErrMessage);
        }
        for (Dish dish : dishList) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {
                dish.setDishEstimatedPreparationTime(newDishEstimatedPreparationTime); return;}
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + title + " a doporučeným množstvím " + quantity
                + " jednotek nebylo nalezeno v dishList." + helpSameErrMessage);
    }

    public void replaceDishMainPhotoByTitleAndQuantity(String title, int quantity, String newMainPhoto)
            throws RestaurantException {
        String helpSameErrMessage =  " Název souboru s hlavní fotografií NEBYL změněn!";
        if (newMainPhoto == null || newMainPhoto.isEmpty()) {
            throw new RestaurantException("Chyba: Pokoušíte se změnit u jídla " + title + " s doporučeným " + quantity
                    + " název souboru hlavní fotografie. Nový je ale prázdný znak. " + helpSameErrMessage);
        }
        for (Dish dish : dishList) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {
                for (char forbiddenCharacter : forbiddenFileCharacters.toCharArray()) {
                    if (newMainPhoto.contains(String.valueOf(forbiddenCharacter))) {
                        throw new RestaurantException("Chyba: Pokoušíte se změnit hlavní fotogarfii u jídla " + title
                                + " s doporučeným nožstvím" + quantity + ", ale nový název souboru obsahuje nepovolený "
                                + "znak: " + forbiddenCharacter + "." + helpSameErrMessage);
                    }
                }
                dish.setDishMainPhoto(newMainPhoto);
                return;
            }
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + title + " a doporučeným množstvím " + quantity
                + " jednotek nebylo nalezeno v dishList, nelze tedy změnit jeho hlavní fotografii.");
    }

    public void addDishNextPhotoByTitleAndQuantity(String title, int quantity, String newNextPhoto)
            throws RestaurantException {
        String helpSameErrMessage =  " Další název souboru fotografie NEBYL přidán do dishNextPhoto listu!";
        for (Dish dish : dishList) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {
                if (newNextPhoto == null || newNextPhoto.isEmpty()) {
                    throw new RestaurantException("Chyba: Pokoušíte se u jídla " + title + " s doporučeným množstvím "
                            + quantity + " přidat další fotografii, ale její název je prázdný řetězec."
                            + helpSameErrMessage);
                }
                for (char forbiddenCharacter : forbiddenFileCharacters.toCharArray()) {
                    if (newNextPhoto.contains(String.valueOf(forbiddenCharacter))) {
                        throw new RestaurantException("Chyba: Pokoušíte se u jídla " + title + " s doporučeným "
                                + "množsvím " + quantity + " přidat název souboru fotografie, který obsahuje zakázaný "
                                + "znak: " + forbiddenCharacter + "." + helpSameErrMessage);
                    }
                }
                if (dish.getDishNextPhoto().contains(newNextPhoto) || newNextPhoto.equals(dish.getDishMainPhoto())) {
                    throw new RestaurantException("Chyba: Pokoušíte se u jídla " + title + " s doporučeným množsvím "
                            + quantity + " přidat název souboru fotografie: " + newNextPhoto + ". Stejný název je již "
                            + "ale obsažen v dishMainPhoto nebo v doshNextPhoto listu." + helpSameErrMessage);
                }
                dish.getDishNextPhoto().add(newNextPhoto);
                dish.setDishNumberOfNextPhotos(dish.getDishNextPhoto().size());
                return;
            }
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + title + " a množstvím " + quantity + " nebylo nalezeno"
                + " v dishList." + helpSameErrMessage);
    }

    public void removeAddressedDishNextPhotoByTitleAndQuantity(String title, int quantity, String photoToRemove)
            throws RestaurantException {
        String helpSameErrMessage =  " Další název souboru fotografie NEBYL odstraněn z dishNextPhoto listu!";
        for (Dish dish : dishList) {
            if (dish.dishDetectSameTitleAndQuantity(title, quantity)) {
                List<String> nextPhotos = dish.getDishNextPhoto();
                if (!nextPhotos.contains(photoToRemove)) {
                    throw new RestaurantException("Chyba: Název footografie: " + photoToRemove + ", kterou se pokoušíte"
                            + " odebrat z dishNextPhoto u jídla " + title + " s doporučeným množstvím " + quantity
                            + " v něm není obsažen nebo ještě prázdný." + helpSameErrMessage);
                }
                nextPhotos.remove(photoToRemove);
                dish.setDishNumberOfNextPhotos(nextPhotos.size());
                return;
            }
        }
        throw new RestaurantException("Chyba: Jídlo s názvem " + title + " a množstvím " + quantity + " nebylo nalezeno"
                + " v dishList." + helpSameErrMessage);
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
        if (!Files.exists(Paths.get(fileDishs))) {createEmptyDishsFile(fileDishs); return;}

        int i; int helpBadFormatIdentificator = 0; FoodCategory helpNextRecomendedCategory = null;
        String line = ""; String[] item = new String[0];
        FoodCategory dishRecomendedMainCategory; int dishNumberOfNextRecomendedCategory; String dishTitle;
        int dishRecommendedQuantity; String dishRecommendedUnitOfQuantity; BigDecimal dishRecommendPrice;
        int dishEstimatedPreparationTime; String dishMainPhoto; int dishNumberOfNextPhotos;
        try (Scanner scannerLoadData = new Scanner(new BufferedReader(new FileReader(fileDishs)))) {
            while (scannerLoadData.hasNextLine()) {
                line = scannerLoadData.nextLine();
                item = line.split(delimiter);
                dishRecomendedMainCategory = FoodCategory.valueOf(item[0]);
                if (dishRecomendedMainCategory == null) {System.err.println("Chyba: V souboru DB-Dish.txt je "
                        + "základní doporučená kategorie, která má hodnotu null nebo má neplatný formát nebo neexistuje"
                        + " v seznamu kategorií ve FoodCategory na řádku s obsahem: " + line);}
                helpBadFormatIdentificator = 1;
                dishNumberOfNextRecomendedCategory = Integer.parseInt(item[1]);
                List<FoodCategory> dishNextRecomendedCategory = new ArrayList<>();
                for (i = 0; i < dishNumberOfNextRecomendedCategory; i++) {
                    helpNextRecomendedCategory = FoodCategory.valueOf(item[i + 2]);
                    dishNextRecomendedCategory.add(helpNextRecomendedCategory);
                    if (helpNextRecomendedCategory == null) {System.err.println("Chyba: V souboru DB-Dish.txt je další "
                            + "doporučená kategorie, která má hodnotu null nebo má neplatný formát nebo neexistuje "
                            + "v seznamu kategorií ve FoodCategory na řádku s obsahem: " + line);}
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
                    throw new RestaurantException("Chyba: Špatný počet položek na řádku: " + line);
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
            throw new RestaurantException("Chyba: Soubor " + fileDishs + " nebyl nalezen! " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Chyba: V souboru DB-Dish.txt není číslo nebo má nedovolenou zápornou hodnotu"
                    + " na řádku: " + line + " položka č." + helpBadFormatIdentificator);
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

    public List<Dish> getDishList() {return new ArrayList<>(dishList);}

}





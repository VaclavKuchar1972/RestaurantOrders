package com.certifikace.projekt1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RestaurantSettings {

    public static String delimiter() {return "; ";}
    public static String forbiddenFileCharacters = "<>:\"/\\|?*"; // Zakázané znaky pro název souboru ve Windows 10

    public static String fileTables() {return "DB-Tables.txt";}
    public static String fileTablesBackUp() {return "DB-TablesBackUp.txt";}

    public static String fileWaiters() {return "DB-Waiters.txt";}
    public static String fileWaitersBackUp() {return "DB-WaitersBackUp.txt";}

    public static String fileFoodCategories() {return "DB-FoodCategories.txt";}
    public static String fileFoodCategoriesBackUp() {return "DB-FoodCategoriesBackUp.txt";}

    public static String fileDishs() {return "DB-Dishs.txt";}
    public static String fileDishsBackUp() {return "DB-DishsBackUp.txt";}


    public static String fileActualMenu() {return "DB-ActualMenu.txt";}

    // Tady cítím trochu problém, že bude vznikat spoustu záloh Aktuálního Menu, ale vzhledem k tomu, že je důležité,
    // aby manager nebo majitel restaurace měl přehled, jak mu po různých změnách "dupou králíci", tak by to podle mě
    // tak mělo být. Určitě a asi bych to i zvládl, by mělo být možné napsat třídu nebo metodu, která by tyto "odpadní"
    // TXT soubory automaticky mazala po předem určeném časovém úseku, ale již potřebuji dodělat Projekt1, takže se tím
    // zabývat nebudu. Nicméně bych takovou metodu přidal do třídy ActualMenuManager a volal jí
    // z metody loadDataMenuFromFile, takže by se to kontrolovalo a promazávalo při každém spuštění programu. Navíc
    // si myslím, že to je problém s TXT pseudodatabází a že při použití skutečné by se to mělo dát ošetřit
    // "elegantněji", ale to jen hádám, protože jsem se ještě nedostal za lekci 7. :D Stále makám na Projek1...
    public static String fileBackUpMenu() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return "DB-MenuBackUp-" + now.format(formatter) + ".txt";
    }





}
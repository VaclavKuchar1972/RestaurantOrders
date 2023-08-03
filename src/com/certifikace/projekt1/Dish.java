package com.certifikace.projekt1;

import java.util.List;
import java.math.BigDecimal;

public class Dish {

    private FoodCategory dishRecomendedMainCategory;
    private int dishNumberOfNextRecomendedCategory;
    private List<FoodCategory> dishNextRecomendedCategory;
    private String dishTitle;
    private int dishRecommendedQuantity;
    private String dishRecommendedUnitOfQuantity;
    private BigDecimal dishRecommendPrice;
    private int dishEstimatedPreparationTime;
    private String dishMainPhoto;
    private int dishNumberOfNextPhotos;
    private List<String> dishNextPhoto;

    public Dish(FoodCategory dishRecomendedMainCategory, int dishNumberOfNextRecomendedCategory,
                List<FoodCategory> dishNextRecomendedCategory, String dishTitle, int dishRecommendedQuantity,
                String dishRecommendedUnitOfQuantity, BigDecimal dishRecommendPrice, int dishEstimatedPreparationTime,
                String dishMainPhoto, int dishNumberOfNextPhotos, List<String> dishNextPhoto) {
        this.dishRecomendedMainCategory = dishRecomendedMainCategory;
        this.dishNumberOfNextRecomendedCategory = dishNumberOfNextRecomendedCategory;
        this.dishNextRecomendedCategory = dishNextRecomendedCategory;
        this.dishTitle = dishTitle;
        this.dishRecommendedQuantity = dishRecommendedQuantity;
        this.dishRecommendedUnitOfQuantity = dishRecommendedUnitOfQuantity;
        this.dishRecommendPrice = dishRecommendPrice;
        this.dishEstimatedPreparationTime = dishEstimatedPreparationTime;
        this.dishMainPhoto = dishMainPhoto;
        this.dishNumberOfNextPhotos = dishNumberOfNextPhotos;
        this.dishNextPhoto = dishNextPhoto;
    }

    public String getDishInfoForTestPrint() {
        return dishRecomendedMainCategory + ", " + dishNumberOfNextRecomendedCategory + ", "
                + dishNextRecomendedCategory + ", " + dishTitle + ", " + dishRecommendedQuantity
                + ", " + dishRecommendedUnitOfQuantity + ", " + dishRecommendPrice + ", " + dishEstimatedPreparationTime
                + ", " + dishMainPhoto + ", " + dishNumberOfNextPhotos + ", " + dishNextPhoto;
    }

    public FoodCategory getDishRecomendedMainCategory() {return dishRecomendedMainCategory;}
    public void setDishRecomendedMainCategory(FoodCategory dishRecomendedMainCategory) throws RestaurantException {
        // OŠETŘENÍ - kategorie může mít pouze velká písmena, číslice a znak "_" (vykřičník opět neguje podmínku),
        // toto "^[A-Z0-9_]+$" mi poradil ChatGPT3.5, nevím jak bych se k tomu jinak rychle dopracoval, ale je jasné,
        // že to bude dělat to co chci, takže je zbytečné to testovat
        if (!dishRecomendedMainCategory.getName().matches("^[A-Z0-9_]+$")) {
            throw new RestaurantException("Chyba: dishRecomendedMainCategory může obsahovat pouze velká písmena, "
                    // takto do textového řetězce můžu vkládat uvozovky
                    + "číslice a znak \"_\".");
        }
        this.dishRecomendedMainCategory = dishRecomendedMainCategory;
    }
    public int getDishNumberOfNextRecomendedCategory() {return dishNumberOfNextRecomendedCategory;}
    public void setDishNumberOfNextRecomendedCategory(int dishNumberOfNextRecomendedCategory)
            throws RestaurantException {
        if (dishNumberOfNextRecomendedCategory < 0) {
            throw new RestaurantException("Chyba: dishNumberOfNextRecomendedCategory nesmí být menší než nula.");
        }
        this.dishNumberOfNextRecomendedCategory = dishNumberOfNextRecomendedCategory;
    }
    public List<FoodCategory> getDishNextRecomendedCategory() {return dishNextRecomendedCategory;}
    public void setDishNextRecomendedCategory(List<FoodCategory> dishNextRecomendedCategory)
            throws RestaurantException {
        // OŠETŘENÍ - stejné jako pro dishRecomendedMainCategory
        for (FoodCategory category : dishNextRecomendedCategory) {
            if (!category.getName().matches("^[A-Z0-9_]+$")) {
                throw new RestaurantException("Chyba: dishRecomendedMainCategory může obsahovat pouze velká písmena, "
                        + "číslice a znak \"_\".");
            }
        }
        this.dishNextRecomendedCategory = dishNextRecomendedCategory;
    }
    public String getDishTitle() {return dishTitle;}
    public void setDishTitle(String dishTitle) {this.dishTitle = dishTitle;}
    public int getDishRecommendedQuantity() {return dishRecommendedQuantity;}
    public void setDishRecommendedQuantity(int dishRecommendedQuantity) throws RestaurantException {
        // OŠETŘENÍ - Restaurace nemůže za peníze prodávat nulové množství čehokoli
        if (dishRecommendedQuantity < 1) {
            throw new RestaurantException("Chyba: dishRecommendedQuantity je menší než 1.");
        }
        this.dishRecommendedQuantity = dishRecommendedQuantity;
    }
    public String getDishRecommendedUnitOfQuantity() {return dishRecommendedUnitOfQuantity;}
    public void setDishRecommendedUnitOfQuantity(String dishRecommendedUnitOfQuantity) {
        this.dishRecommendedUnitOfQuantity = dishRecommendedUnitOfQuantity;
    }
    public BigDecimal getDishRecommendPrice() {return dishRecommendPrice;}
    public void setDishRecommendPrice(BigDecimal dishRecommendPrice) throws RestaurantException {
        // OŠETŘENÍ - Restaurace nemůže vracet peníze za objednávky klientů, může jim to ale dát zadarmo, např., když
        // na takovou položku udělá restaurace akci nebo si prostě majitel usmyslí, že chleby k polévce budou zadarmo
        // - a hotovo (rozhodnutí managementu)
        if (dishRecommendPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new RestaurantException("Chyba: dishRecommendPrice nesmí mít zápornou hodnotu.");
        }
        this.dishRecommendPrice = dishRecommendPrice;}
    public int getDishEstimatedPreparationTime() throws RestaurantException {return dishEstimatedPreparationTime;}
    public void setDishEstimatedPreparationTime(int dishEstimatedPreparationTime) throws RestaurantException {
        // OŠETŘENÍ - opravdu nejde nic udělat za nulový čas, tedy lze - další nic :-)
        if (dishEstimatedPreparationTime < 1 ) {
            throw new RestaurantException("Chyba: dishEstimatedPreparationTime nesmí být menší než 1.");
        }
        this.dishEstimatedPreparationTime = dishEstimatedPreparationTime;
    }
    public String getDishMainPhoto() {return dishMainPhoto;}
    public void setDishMainPhoto(String dishMainPhoto) {
        if (dishMainPhoto == null || dishMainPhoto.isEmpty()) {this.dishMainPhoto = "blank";}
        else {this.dishMainPhoto = dishMainPhoto;}
    }
    public int getDishNumberOfNextPhotos() {return dishNumberOfNextPhotos;}
    public void setDishNumberOfNextPhotos(int dishNumberOfNextPhotos) throws RestaurantException {
        if (dishNumberOfNextPhotos < 0) {
            throw new RestaurantException("Chyba: dishNumberOfNextPhotos nesmí být menší než 0.");
        }
        this.dishNumberOfNextPhotos = dishNumberOfNextPhotos;
    }
    public List<String> getDishNextPhoto() {return dishNextPhoto;}
    public void setDishNextPhoto(List<String> dishNextPhoto) {this.dishNextPhoto = dishNextPhoto;}
    public void addDishNextPhoto(String photo) {dishNextPhoto.add(photo);}


}

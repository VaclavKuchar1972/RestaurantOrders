package com.certifikace.projekt1;

import java.util.List;
import java.math.BigDecimal;

import static com.certifikace.projekt1.RestaurantSettings.delimiter;

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
    public void setDishRecomendedMainCategory(FoodCategory dishRecomendedMainCategory) {
        this.dishRecomendedMainCategory = dishRecomendedMainCategory;
    }
    public int getDishNumberOfNextRecomendedCategory() {return dishNumberOfNextRecomendedCategory;}
    public void setDishNumberOfNextRecomendedCategory(int dishNumberOfNextRecomendedCategory) {
        this.dishNumberOfNextRecomendedCategory = dishNumberOfNextRecomendedCategory;
    }
    public List<FoodCategory> getDishNextRecomendedCategory() {return dishNextRecomendedCategory;}
    public void setDishNextRecomendedCategory(List<FoodCategory> dishNextRecomendedCategory) {
        this.dishNextRecomendedCategory = dishNextRecomendedCategory;
    }
    public String getDishTitle() {return dishTitle;}
    public void setDishTitle(String dishTitle) {this.dishTitle = dishTitle;}
    public int getDishRecommendedQuantity() {return dishRecommendedQuantity;}
    public void setDishRecommendedQuantity(int dishRecommendedQuantity) {
        this.dishRecommendedQuantity = dishRecommendedQuantity;
    }
    public String getDishRecommendedUnitOfQuantity() {return dishRecommendedUnitOfQuantity;}
    public void setDishRecommendedUnitOfQuantity(String dishRecommendedUnitOfQuantity) {
        this.dishRecommendedUnitOfQuantity = dishRecommendedUnitOfQuantity;
    }
    public BigDecimal getDishRecommendPrice() {return dishRecommendPrice;}
    public void setDishRecommendPrice(BigDecimal dishRecommendPrice) {this.dishRecommendPrice = dishRecommendPrice;}
    public int getDishEstimatedPreparationTime() {return dishEstimatedPreparationTime;}
    public void setDishEstimatedPreparationTime(int dishEstimatedPreparationTime) {
        this.dishEstimatedPreparationTime = dishEstimatedPreparationTime;
    }
    public String getDishMainPhoto() {return dishMainPhoto;}
    public void setDishMainPhoto(String dishMainPhoto) {this.dishMainPhoto = dishMainPhoto;}
    public int getDishNumberOfNextPhotos() {return dishNumberOfNextPhotos;}
    public void setDishNumberOfNextPhotos(int dishNumberOfNextPhotos) {
        this.dishNumberOfNextPhotos = dishNumberOfNextPhotos;
    }
    public List<String> getDishNextPhoto() {return dishNextPhoto;}
    public void setDishNextPhoto(List<String> dishNextPhoto) {this.dishNextPhoto = dishNextPhoto;}
    public void addDishNextPhoto(String photo) {dishNextPhoto.add(photo);}


}

package com.certifikace.projekt1;

import java.util.List;
import java.math.BigDecimal;

public class Dish {

    private FoodCategory dishRecomendedMainCategory;
    private int dishNumberOfNextRecomendedCategories;
    private List<FoodCategory> dishNextRecomendedCategory;
    private String dishTitle;
    private int dishRecommendedQuantity;
    private String dishRecommendedUnitOfQuantity;
    private BigDecimal dishRecommendPrice;
    private int dishEstimatedPreparationTime;
    private String dishMainPhoto;
    private int dishNumberOfNextPhotos;
    private List<String> dishNextPhoto;

    public boolean dishDetectSameTitleAndQuantity(String title, int quantity) {
        return dishTitle.equals(title) && dishRecommendedQuantity == quantity;
    }

    public Dish(FoodCategory dishRecomendedMainCategory, int dishNumberOfNextRecomendedCategories,
                List<FoodCategory> dishNextRecomendedCategory, String dishTitle, int dishRecommendedQuantity,
                String dishRecommendedUnitOfQuantity, BigDecimal dishRecommendPrice, int dishEstimatedPreparationTime,
                String dishMainPhoto, int dishNumberOfNextPhotos, List<String> dishNextPhoto) {
        this.dishRecomendedMainCategory = dishRecomendedMainCategory;
        this.dishNumberOfNextRecomendedCategories = dishNumberOfNextRecomendedCategories;
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
        return dishRecomendedMainCategory + ", " + dishNumberOfNextRecomendedCategories + ", "
                + dishNextRecomendedCategory + ", " + dishTitle + ", " + dishRecommendedQuantity
                + ", " + dishRecommendedUnitOfQuantity + ", " + dishRecommendPrice + ", " + dishEstimatedPreparationTime
                + ", " + dishMainPhoto + ", " + dishNumberOfNextPhotos + ", " + dishNextPhoto;
    }

    public FoodCategory getDishRecomendedMainCategory() {return dishRecomendedMainCategory;}
    public void setDishRecomendedMainCategory(FoodCategory dishRecomendedMainCategory) {
        this.dishRecomendedMainCategory = dishRecomendedMainCategory;
    }
    public int getDishNumberOfNextRecomendedCategories() {return dishNumberOfNextRecomendedCategories;}
    public void setDishNumberOfNextRecomendedCategories(int dishNumberOfNextRecomendedCategories) {
        this.dishNumberOfNextRecomendedCategories = dishNumberOfNextRecomendedCategories;
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
    public void setDishRecommendPrice(BigDecimal dishRecommendPrice) {
        this.dishRecommendPrice = dishRecommendPrice;}
    public int getDishEstimatedPreparationTime() {return dishEstimatedPreparationTime;}
    public void setDishEstimatedPreparationTime(int dishEstimatedPreparationTime) throws RestaurantException {
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
    //public void addDishNextPhoto(String photo) {dishNextPhoto.add(photo);}


}

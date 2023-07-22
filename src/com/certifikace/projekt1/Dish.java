package com.certifikace.projekt1;

import java.util.List;
import java.math.BigDecimal;

public class Dish {

    private FoodCategory dishRecomendedMainCategory;
    private int dishNumberOfNextCategory;
    private List<FoodCategory> dishNextCategory;
    private String dishTitle;
    private int dishRecommendedQuantity;
    private String dishRecommendedUnitOfQuantity;
    private BigDecimal dishRecommendPrice;
    private int dishEstimatedPreparationTime;
    private String dishMainPhoto;
    private int dishNumberOfNextPhotos;
    private List<String> dishNextPhoto;


    public FoodCategory getDishRecomendedMainCategory() {
        return dishRecomendedMainCategory;
    }

    public void setDishRecomendedMainCategory(FoodCategory dishRecomendedMainCategory) {
        this.dishRecomendedMainCategory = dishRecomendedMainCategory;
    }

    public int getDishNumberOfNextCategory() {
        return dishNumberOfNextCategory;
    }

    public void setDishNumberOfNextCategory(int dishNumberOfNextCategory) {
        this.dishNumberOfNextCategory = dishNumberOfNextCategory;
    }

    public List<FoodCategory> getDishNextCategory() {
        return dishNextCategory;
    }

    public void setDishNextCategory(List<FoodCategory> dishNextCategory) {
        this.dishNextCategory = dishNextCategory;
    }

    public String getDishTitle() {
        return dishTitle;
    }

    public void setDishTitle(String dishTitle) {
        this.dishTitle = dishTitle;
    }

    public int getDishRecommendedQuantity() {
        return dishRecommendedQuantity;
    }

    public void setDishRecommendedQuantity(int dishRecommendedQuantity) {
        this.dishRecommendedQuantity = dishRecommendedQuantity;
    }

    public String getDishRecommendedUnitOfQuantity() {
        return dishRecommendedUnitOfQuantity;
    }

    public void setDishRecommendedUnitOfQuantity(String dishRecommendedUnitOfQuantity) {
        this.dishRecommendedUnitOfQuantity = dishRecommendedUnitOfQuantity;
    }

    public BigDecimal getDishRecommendPrice() {
        return dishRecommendPrice;
    }

    public void setDishRecommendPrice(BigDecimal dishRecommendPrice) {
        this.dishRecommendPrice = dishRecommendPrice;
    }

    public int getDishEstimatedPreparationTime() {
        return dishEstimatedPreparationTime;
    }

    public void setDishEstimatedPreparationTime(int dishEstimatedPreparationTime) {
        this.dishEstimatedPreparationTime = dishEstimatedPreparationTime;
    }

    public String getDishMainPhoto() {
        return dishMainPhoto;
    }

    public void setDishMainPhoto(String dishMainPhoto) {
        this.dishMainPhoto = dishMainPhoto;
    }

    public int getDishNumberOfNextPhotos() {
        return dishNumberOfNextPhotos;
    }

    public void setDishNumberOfNextPhotos(int dishNumberOfNextPhotos) {
        this.dishNumberOfNextPhotos = dishNumberOfNextPhotos;
    }

    public List<String> getDishNextPhoto() {
        return dishNextPhoto;
    }

    public void setDishNextPhoto(List<String> dishNextPhoto) {
        this.dishNextPhoto = dishNextPhoto;
    }
}

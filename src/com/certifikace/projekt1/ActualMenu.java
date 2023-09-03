package com.certifikace.projekt1;

import java.math.BigDecimal;
import java.util.List;

public class ActualMenu {


    private FoodCategory amMainCategory;
    private int amNumberOfNextCategories;
    private List<FoodCategory> amNextCategory;
    private String amTitle;
    private int amQuantity;
    private String amUnitOfQuantity;
    private BigDecimal amPrice;
    private int amPreparationTime;
    private String amMainPhoto;
    private int amNumberOfNextPhotos;
    private List<String> amNextPhoto;


    public boolean amDetectSameTitleAndQuantity(String title, int quantity) {
        return amTitle.equals(title) && amQuantity == quantity;
    }

    public ActualMenu(FoodCategory amMainCategory, int amNumberOfNextCategories,
                      List<FoodCategory> amNextCategory, String amTitle, int amQuantity,
                      String amUnitOfQuantity, BigDecimal amPrice, int amPreparationTime, String amMainPhoto,
                      int amNumberOfNextPhotos, List<String> amNextPhoto) {
        this.amMainCategory = amMainCategory;
        this.amNumberOfNextCategories = amNumberOfNextCategories;
        this.amNextCategory = amNextCategory;
        this.amTitle = amTitle;
        this.amQuantity = amQuantity;
        this.amUnitOfQuantity = amUnitOfQuantity;
        this.amPrice = amPrice;
        this.amPreparationTime = amPreparationTime;
        this.amMainPhoto = amMainPhoto;
        this.amNumberOfNextPhotos = amNumberOfNextPhotos;
        this.amNextPhoto = amNextPhoto;
    }

    public String getActualMenuInfoForTestPrint() {
        return amMainCategory + ", " + amNumberOfNextCategories + ", " + amNextCategory + ", " + amTitle + ", "
                + amQuantity + ", " + amUnitOfQuantity + ", " + amPrice + ", " + amPreparationTime + ", " + amMainPhoto
                + ", " + amNumberOfNextPhotos + ", " + amNextPhoto;
    }

    public String getTitleForOrder () {return amTitle + " - " + amQuantity + " " + amUnitOfQuantity;}

    public FoodCategory getAmMainCategory() {return amMainCategory;}
    public void setAmMainCategory(FoodCategory amMainCategory) {this.amMainCategory = amMainCategory;}
    public int getAmNumberOfNextCategories() {return amNumberOfNextCategories;}
    public void setAmNumberOfNextCategories(int amNumberOfNextCategories) {
        this.amNumberOfNextCategories = amNumberOfNextCategories;
    }
    public List<FoodCategory> getAmNextCategory() {return amNextCategory;}
    public void setAmNextCategory(List<FoodCategory> amNextCategory) {this.amNextCategory = amNextCategory;}
    public String getAmTitle() {return amTitle;}
    public void setAmTitle(String amTitle) {this.amTitle = amTitle;}
    public int getAmQuantity() {return amQuantity;}
    public void setAmQuantity(int amQuantity) {this.amQuantity = amQuantity;}
    public String getAmUnitOfQuantity() {return amUnitOfQuantity;}
    public void setAmUnitOfQuantity(String amUnitOfQuantity) {this.amUnitOfQuantity = amUnitOfQuantity;}
    public BigDecimal getAmPrice() {return amPrice;}
    public void setAmPrice(BigDecimal amPrice) {this.amPrice = amPrice;}
    public int getAmPreparationTime() {return amPreparationTime;}
    public void setAmPreparationTime(int amPreparationTime) {this.amPreparationTime = amPreparationTime;}
    public String getAmMainPhoto() {return amMainPhoto;}
    public void setAmMainPhoto(String amMainPhoto) {this.amMainPhoto = amMainPhoto;}
    public int getAmNumberOfNextPhotos() {return amNumberOfNextPhotos;}
    public void setAmNumberOfNextPhotos(int amNumberOfNextPhotos) {this.amNumberOfNextPhotos = amNumberOfNextPhotos;}
    public List<String> getAmNextPhoto() {return amNextPhoto;}
    public void setAmNextPhoto(List<String> amNextPhoto) {this.amNextPhoto = amNextPhoto;}

}

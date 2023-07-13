package com.certifikace.projekt1;

public enum WaiterCategory {
    MER("hlavní"), SE("vedlejší"), BRIGADE("brigáda"), ATPW("dohoda o provedení práce"),
    AOWA("dohoda o pracovní činnosti");


    private final String categoryName;

    WaiterCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

//    public static List<String> getCategories() {
//        List<String> categories = new ArrayList<>();
//        for (WaiterCategory category : WaiterCategory.values()) {
//            categories.add(category.getCategoryName());
//        }
//        return categories;

}







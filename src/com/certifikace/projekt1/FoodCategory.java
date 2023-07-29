package com.certifikace.projekt1;

public enum FoodCategory {
    NONALCOHOLIC("nealkoholický nápoj"), ALCOHOLIC("alkoholický nápoj"),
    BEER("pivo"), WINE("víno"), CHAMPAIGN("šampaňské"),
    DESTILATE("destilát"), OTHER("ostatní"), SOMETHINGTOBEER("něco k pivu"),
    PRECAUTION("předkrm"), SOUP("polévka"), PREPAREDFOOD("hotové jídlo"),
    CHILDFOOD("dětské jídlo"), MINUTEMEAL("minutka"), GRILLED("na grilu"),
    SPECIALITY("specialita"), SALAD("salát"), GARNISH("příloha"),
    DIP("omáčka"), ICECREAM("zmrzlinový pohár"), BUBBLEFREE("neperlivý"),
    LIGHTLYSPARKLING("jemně perlivý"), SPARKLING("perlivý/šumivé"),
    LEMONADE("limonáda"), JUICE("džus"), BRIGHT("světlé"),
    DARK("tmavé"), MIXED("řezané"), WHITE("bílé"), RED("červené"),
    ROSE("růžové"), APERITIVE("aperitiv"), LIQUOR("likér"),
    WHISKY("whisky/burbon"), BEEF("hovězí"), PORK("vepřové"),
    HATTED("skopové"), CHICKEN("kuřecí"), KITCHEN("kachní"),
    FISH("ryba"), SEAFOOD("mořské plody"), GREEN("zeleninový"),
    LITTLE("malý"), GLUTENFREEFOOD("bezlepkový");

    private String foodDescription;

    FoodCategory(String foodDescription) {this.foodDescription = foodDescription;}

    @Override
    public String toString() {return foodDescription;}

}
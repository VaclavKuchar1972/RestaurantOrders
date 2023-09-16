package com.certifikace.projekt1;

public enum OrderCategory {
    RECEIVED("přijatá objednávka"),
    ISSUED("objednávka předaná na stůl"),
    PAID("zaplacená objednávka");

    private final String czechName;
    OrderCategory(String czechName) {this.czechName = czechName;}
    public String getCzechName() {return czechName;}
    @Override
    public String toString() {return czechName;}

}

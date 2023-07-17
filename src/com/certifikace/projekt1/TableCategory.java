package com.certifikace.projekt1;

public enum TableCategory {
    HALL("HLAVNÍ SÁL"), TERRACE("TERASA"), SALON("SALONEK");

    private String tableDescription;

    TableCategory(String tableDescription) {this.tableDescription = tableDescription;}

    @Override
    public String toString() {return tableDescription;}

}

package com.certifikace.projekt1;

public class Waiter {

    private int waiterNumber;
    private String waiterTitleBeforeName;
    private String waiterFirstName;
    private String waiterSecondName;
    private String waiterTitleAfterName;
    private String waiterIdentificationDocumentNumber;
    private String waiterTypeOfEmploymentRelationship;

    public Waiter(int waiterNumber, String waiterTitleBeforeName, String waiterFirstName, String waiterSecondName,
                  String waiterTitleAfterName, String waiterIdentificationDocumentNumber,
                  String waiterTypeOfEmploymentRelationship) {
        this.waiterNumber = waiterNumber;
        this.waiterTitleBeforeName = waiterTitleBeforeName;
        this.waiterFirstName = waiterFirstName;
        this.waiterSecondName = waiterSecondName;
        this.waiterTitleAfterName = waiterTitleAfterName;
        this.waiterIdentificationDocumentNumber = waiterIdentificationDocumentNumber;
        this.waiterTypeOfEmploymentRelationship = waiterTypeOfEmploymentRelationship;
    }

    public String getWaiterInfoForPrintMe() {
        // OŠETŘENÍ - Vložení mezery před číslo číšníka, je-li jedno nebo dvouciferné
        String helpString = ""; if (waiterNumber < 100) {helpString = " ";}; if (waiterNumber < 10) {helpString = "  ";}
        return "Číslo číšníka: " + helpString + waiterNumber + "   Jméno a příjmení/tituly: " + waiterTitleBeforeName
                + waiterFirstName + " " + waiterSecondName + waiterTitleAfterName +
                "   Čislo OP/pasu/náhradního dokladu: " + waiterIdentificationDocumentNumber
                + "   Typ pracovně právního vztahu: " + waiterTypeOfEmploymentRelationship;
    }

    public String getWaiterInfoForPrintMeNoAbbreviationRelationship() {
        // OŠETŘENÍ - Vložení mezery před číslo číšníka, je-li jedno nebo dvouciferné
        String helpString = ""; if (waiterNumber < 100) {helpString = " ";}; if (waiterNumber < 10) {helpString = "  ";}
        return "Číslo číšníka: " + helpString + waiterNumber + "   Jméno a příjmení/tituly: " + waiterTitleBeforeName
                + waiterFirstName + " " + waiterSecondName + waiterTitleAfterName +
                "   Čislo OP/pasu/náhradního dokladu: " + waiterIdentificationDocumentNumber;
    }

    public String getWaiterTypeOfEmploymentRelationshipNoAbbreviation() {
        String abbreviation = getWaiterTypeOfEmploymentRelationship();
        switch (abbreviation) {
            case "HPP": return "hlavní pracovní poměr";
            case "VPP": return "vedlejší pracovní poměr";
            case "BRIGADA": return "brigáda";
            case "DPP": return "dohoda o provedení práce";
            default: return "neznámý typ pracovního poměru";
        }
    }

    public int getWaiterNumber() {return waiterNumber;}
    public void setWaiterNumber(int waiterNumber) throws RestaurantException {
        // OŠETŘENÍ - Číslo čííšníka musí být maximálně tříciferné
        if (waiterNumber < 1) {
            throw new RestaurantException("Chyba - počet číšníků je menší než 1");
        }
        if (waiterNumber > 999) {
            throw new RestaurantException("Chyba - počet číšníků je větší než 999");
        }
        this.waiterNumber = waiterNumber;
    }
    public String getWaiterTitleBeforeName() {return waiterTitleBeforeName;}
    public void setWaiterTitleBeforeName(String waiterTitleBeforeName) {
        this.waiterTitleBeforeName = waiterTitleBeforeName;
    }
    public String getWaiterFirstName() {return waiterFirstName;}
    public void setWaiterFirstName(String waiterFirstName) {this.waiterFirstName = waiterFirstName;}
    public String getWaiterSecondName() {return waiterSecondName;}
    public void setWaiterSecondName(String waiterSecondName) {this.waiterSecondName = waiterSecondName;}
    public String getWaiterTitleAfterName() {return waiterTitleAfterName;}
    public void setWaiterTitleAfterName(String waiterTitleAfterName) {this.waiterTitleAfterName = waiterTitleAfterName;}
    public String getWaiterIdentificationDocumentNumber() {return waiterIdentificationDocumentNumber;}
    public void setWaiterIdentificationDocumentNumber(String waiterIdentificationDocumentNumber) {
        // Přijímání duplicitních osob nemám ošetřeno proto, že to není tak jednoduché. To by bylo na samostaný scénář
        // a bez přístupu do státních oneline regitsrů to jednoduše nejde. Např. číslo OP může být stejné jako číslo
        // pasu, rodné číslo může být také u dvou osoab stejné (vlastní zkušenost - a nutili mě abych si ho nechal
        // předělat) a v neposeldní řadě může jít o cizince, který má i české státní občanství, tudíž při přijímání
        // do práce může předložit buď OP nebo PAS a v tu chvíli tu máme hned jednoho člověka, který je zaměstnán
        // dvakrát na stejné pracovní pozici.
        this.waiterIdentificationDocumentNumber = waiterIdentificationDocumentNumber;
    }
    public String getWaiterTypeOfEmploymentRelationship() {return waiterTypeOfEmploymentRelationship;}
    public void setWaiterTypeOfEmploymentRelationship(String waiterTypeOfEmploymentRelationship) {
        this.waiterTypeOfEmploymentRelationship = waiterTypeOfEmploymentRelationship;
    }



}



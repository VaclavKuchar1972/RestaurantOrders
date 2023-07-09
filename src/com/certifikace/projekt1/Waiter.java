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

    public String getWaiterInfo () {
        // OŠETŘENÍ - Vložení mezery před číslo číšníka, je-li jedno nebo dvouciferné
        String helpString = ""; if (waiterNumber < 100) {helpString = " ";}; if (waiterNumber < 10) {helpString = "  ";}
        return "Číslo číšníka: " + helpString + waiterNumber + "   Jméno a příjmení/tituly: " + waiterTitleBeforeName
                + waiterFirstName + " " + waiterSecondName + waiterTitleAfterName +
                "   Čislo OP/pasu/náhradního dokladu: " + waiterIdentificationDocumentNumber
                + "   Typ pracovně právního vztahu: " + waiterTypeOfEmploymentRelationship;
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
        // Vysvětlit proč nemám ošetřeno přijímaní duplicitní fyzich osob


        this.waiterIdentificationDocumentNumber = waiterIdentificationDocumentNumber;
    }
    public String getWaiterTypeOfEmploymentRelationship() {return waiterTypeOfEmploymentRelationship;}
    public void setWaiterTypeOfEmploymentRelationship(String waiterTypeOfEmploymentRelationship) {
        this.waiterTypeOfEmploymentRelationship = waiterTypeOfEmploymentRelationship;
    }

}

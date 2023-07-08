package com.certifikace.projekt1;

public class Waiter {

    private int waiterNumber;
    private String waiterFirstName;
    private String waiterSecondName;
    private String waiterIdentificationDocumentNumber;
    private String waiterTypeOfEmploymentRelationship;

    public Waiter(int waiterNumber, String waiterFirstName, String waiterSecondName,
                  String waiterIdentificationDocumentNumber,
                  String waiterTypeOfEmploymentRelationship) {
        this.waiterNumber = waiterNumber;
        this.waiterFirstName = waiterFirstName;
        this.waiterSecondName = waiterSecondName;
        this.waiterIdentificationDocumentNumber = waiterIdentificationDocumentNumber;
        this.waiterTypeOfEmploymentRelationship = waiterTypeOfEmploymentRelationship;
    }

    public String getWaiterInfo () {
        // OŠETŘENÍ - Vložení mezery před číslo číšníka, je-li jedno nebo dvouciferné
        String helpString = ""; if (waiterNumber < 100) {helpString = " ";}; if (waiterNumber < 10) {helpString = "  ";}
        return "Číslo číšníka: " + helpString + waiterNumber + "   Jméno a příjmení: " + waiterFirstName + " "
                + waiterSecondName + "   Čislo OP/pasu/náhradního dokladu: " + waiterIdentificationDocumentNumber
                + "   Typ pracovně právního vztahu: " + waiterTypeOfEmploymentRelationship;
    }

    public int getWaiterNumber() {return waiterNumber;}

    public void setWaiterNumber(int waiterNumber) throws RestaurantException {
        // OŠETŘENÍ - Číslo stolu musí být maximálně dvouciferné
        if (waiterNumber < 1) {
            throw new RestaurantException("Chyba - počet číšníků je menší než 1");
        }
        if (waiterNumber > 999) {
            throw new RestaurantException("Chyba - počet číšníků je větší než 999");
        }
        this.waiterNumber = waiterNumber;
    }

    public String getWaiterFirstName() {
        return waiterFirstName;
    }

    public void setWaiterFirstName(String waiterFirstName) {
        this.waiterFirstName = waiterFirstName;
    }

    public String getWaiterSecondName() {
        return waiterSecondName;
    }

    public void setWaiterSecondName(String waiterSecondName) {
        this.waiterSecondName = waiterSecondName;
    }

    public String getWaiterIdentificationDocumentNumber() {
        return waiterIdentificationDocumentNumber;
    }

    public void setWaiterIdentificationDocumentNumber(String waiterIdentificationDocumentNumber) {
        this.waiterIdentificationDocumentNumber = waiterIdentificationDocumentNumber;
    }

    public String getWaiterTypeOfEmploymentRelationship() {
        return waiterTypeOfEmploymentRelationship;
    }

    public void setWaiterTypeOfEmploymentRelationship(String waiterTypeOfEmploymentRelationship) {
        this.waiterTypeOfEmploymentRelationship = waiterTypeOfEmploymentRelationship;
    }
}

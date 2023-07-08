package com.certifikace.projekt1;

public class Waiter {

    private String waiterFirstName;
    private String waiterSecondName;
    private String waiterIdentificationDocumentNumber;
    private String waiterTypeOfEmploymentRelationship;

    public Waiter(String waiterFirstName, String waiterSecondName, String waiterIdentificationDocumentNumber,
                  String waiterTypeOfEmploymentRelationship) {
        this.waiterFirstName = waiterFirstName;
        this.waiterSecondName = waiterSecondName;
        this.waiterIdentificationDocumentNumber = waiterIdentificationDocumentNumber;
        this.waiterTypeOfEmploymentRelationship = waiterTypeOfEmploymentRelationship;
    }

    public String getWaiterInfo () {
        return "Jméno a příjmení: " + waiterFirstName + " " + waiterSecondName + "   Čislo OP/pasu/náhradního dokladu: "
                + waiterIdentificationDocumentNumber + "   Typ pracovně právního vztahu: "
                + waiterTypeOfEmploymentRelationship;
    }

    public String getWaiterFirstName() {return waiterFirstName;}
    public void setWaiterFirstName(String waiterFirstName) {this.waiterFirstName = waiterFirstName;}
    public String getWaiterSecondName() {return waiterSecondName;}
    public void setWaiterSecondName(String waiterSecondName) {this.waiterSecondName = waiterSecondName;}
    public String getWaiterIdentificationDocumentNumber() {return waiterIdentificationDocumentNumber;}
    public void setWaiterIdentificationDocumentNumber(String waiterIdentificationDocumentNumber) {
        this.waiterIdentificationDocumentNumber = waiterIdentificationDocumentNumber;
    }
    public String getWaiterTypeOfEmploymentRelationship() {return waiterTypeOfEmploymentRelationship;}
    public void setWaiterTypeOfEmploymentRelationship(String waiterTypeOfEmploymentRelationship) {
        this.waiterTypeOfEmploymentRelationship = waiterTypeOfEmploymentRelationship;
    }

}

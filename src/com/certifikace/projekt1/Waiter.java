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

    // tak jsem byl donucen použít mnou nenáviděný Switch :D ...je v tomto případě nejefektivnější
    public String getWaiterTypeOfEmploymentRelationshipNoAbbreviation() {
        switch (waiterTypeOfEmploymentRelationship) {
            case "HPP": return "hlavní pracovní poměr";
            case "VPP": return "vedlejší pracovní poměr";
            case "BRIGADA": return "brigáda";
            case "DPP": return "dohoda o provedení práce";
            default: return "neznámý typ pracovního poměru";
        }
    }

    public String getWaiterInfoForTestPrint() {
        String formatedNuberWaiterString = String.format("%10d", waiterNumber);
        String employmentRelationship = getWaiterTypeOfEmploymentRelationshipNoAbbreviation();
        return "Číslo číšníka: " + formatedNuberWaiterString + "   Jméno a příjmení/tituly: " + waiterTitleBeforeName
                + waiterFirstName + " " + waiterSecondName + waiterTitleAfterName
                + "   Čislo OP/pasu/náhradního dokladu: " + waiterIdentificationDocumentNumber
                + "   Typ pracovně právního vztahu: " + waiterTypeOfEmploymentRelationship + " ("
                + employmentRelationship + ")";
    }

    public int getWaiterNumber() {return waiterNumber;}
    public void setWaiterNumber(int waiterNumber) {this.waiterNumber = waiterNumber;}
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
        this.waiterIdentificationDocumentNumber = waiterIdentificationDocumentNumber;
    }
    public String getWaiterTypeOfEmploymentRelationship() {return waiterTypeOfEmploymentRelationship;}
    public void setWaiterTypeOfEmploymentRelationship(String waiterTypeOfEmploymentRelationship) {
        this.waiterTypeOfEmploymentRelationship = waiterTypeOfEmploymentRelationship;
    }

}



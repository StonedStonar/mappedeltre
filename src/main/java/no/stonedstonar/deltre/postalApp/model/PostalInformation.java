package no.stonedstonar.deltre.postalApp.model;

/**
 * Represets a place by holding the postal number and the name of the place.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class PostalInformation {

    private String nameOfPlace;

    private Long postalCodeOfPlace;

    private Long countyAndMunicipalityNumber;

    /**
     * Makes an instance of the postal information object.
     * @param nameOfPlace the name of the place that has this postal code.
     * @param postalCode the postal code of this place.
     * @param newCountyAndMunicipalityNumber the county and municipality number this postal information belongs to.
     */
    public PostalInformation(String nameOfPlace, Long postalCode, Long newCountyAndMunicipalityNumber){
        checkIfPostalCodeIsValid(postalCode);
        PostalFacade.checkString(nameOfPlace, "name of the place");
        PostalFacade.checkIfCountyAndMunicipalityNumberOrPostalCodeIsValid(newCountyAndMunicipalityNumber);
        postalCodeOfPlace = postalCode;
        this.nameOfPlace = nameOfPlace;
        countyAndMunicipalityNumber = newCountyAndMunicipalityNumber;
    }

    /**
     * Gets the county and municipality number from the object.
     * @return the county and municipality number.
     */
    public Long getCountyAndMunicipalityNumber(){
        return countyAndMunicipalityNumber;
    }

    /**
     * Sets the county and municipality number to a new value.
     * @param newCountyAndMunicipalityNumber the new county and municipality number.
     */
    public void setCountyAndMunicipalityNumber(Long newCountyAndMunicipalityNumber){
        PostalFacade.checkIfCountyAndMunicipalityNumberOrPostalCodeIsValid(newCountyAndMunicipalityNumber);
        countyAndMunicipalityNumber = newCountyAndMunicipalityNumber;
    }

    /**
     * Gets the name that this postalcode belongs to.
     * @return the name as a string of the place.
     */
    public String getNameOfPlace() {
        return nameOfPlace;
    }

    /**
     * Sets the name of the postal code place to a new value.
     * @param newName the new name of the postal place.
     */
    public void setNameOfPlace(String newName){
        PostalFacade.checkString(newName, "name of the postal code place");
        nameOfPlace = newName;
    }

    /**
     * Gets the postalcode of the place.
     * @return the postalcode as a int of this place.
     */
    public Long getPostalCodeOfPlace() {
        return postalCodeOfPlace;
    }

    /**
     * Sets the postal code of this object to a new value.
     * @param newPostalCode the new postal code to this place.
     */
    public void setPostalCodeOfPlace(Long newPostalCode){
        checkIfPostalCodeIsValid(newPostalCode);
        postalCodeOfPlace = newPostalCode;
    }

    /**
     * Checks if the postal code is of a valid format.
     * @param postCode the postal code you want to check.
     */
    public static void checkIfPostalCodeIsValid(Long postCode){
        if ((postCode == null) || (postCode <= PostalFacade.getMinPostalCodeValue()) || (postCode > PostalFacade.getMaxPostalCodeValue())){
            String error = "is too short";
            if (postCode == null){
                error = "null";
            }else if (postCode > PostalFacade.getMaxPostalCodeValue()){
                error = "is too long";
            }
            throw new IllegalArgumentException("The postal code " + error + " must be a number between 0000 and 9999");
        }
    }
}

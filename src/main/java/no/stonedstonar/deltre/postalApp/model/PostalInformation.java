package no.stonedstonar.deltre.postalApp.model;

/**
 * Represets a place by holding the postal number and the name of the place.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class PostalInformation {

    private String nameOfPlace;

    private int postalCodeOfPlace;

    private int municipality;

    /**
     * Makes an instance of the postal information object.
     * @param nameOfPlace the name of the place that has this postal code.
     * @param postalCode the postal code of this place.
     */
    public PostalInformation(String nameOfPlace, int postalCode){
        checkIfPostalCodeIsValid(postalCode);
        checkString(nameOfPlace, "name of the place");
        postalCodeOfPlace = postalCode;
        this.nameOfPlace = nameOfPlace;
    }

    /**
     * Gets the name that this postalcode belongs to.
     * @return the name as a string of the place.
     */
    public String getNameOfPlace() {
        return nameOfPlace;
    }

    /**
     * Gets the postalcode of the place.
     * @return the postalcode as a int of this place.
     */
    public int getPostalCodeOfPlace() {
        return postalCodeOfPlace;
    }

    /**
     * Checks if the postal code is of a valid format.
     * @param postCode the postal code you want to check.
     */
    private void checkIfPostalCodeIsValid(int postCode){
        if ((postCode < 0000) || (postCode > 9999)){
            String error = "is too short";
            if (postCode > 9999){
                error = "is too long";
            }
            throw new IllegalArgumentException("The postal code " + error + " must be a number between 0000 and 9999");
        }
    }

    /**
     * Checks if the string is empty or null.
     * @param wordToCheck the word you want to check if its empty or null.
     * @param prefix the prefix you want in the exception.
     */
    private void checkString(String wordToCheck, String prefix){
        if ((wordToCheck == null) || (wordToCheck.isEmpty())){
            String error = "empty";
            if (wordToCheck == null){
                error = "null";
            }
            throw new IllegalArgumentException("The " + prefix + " cannot be " + error + ".");
        }
    }
}

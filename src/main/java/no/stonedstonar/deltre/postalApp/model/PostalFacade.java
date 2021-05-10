package no.stonedstonar.deltre.postalApp.model;

import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotAddCountyException;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotAddPostalInformationException;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotGetCountyException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the backbone of the application. Handles all the GUI interaction and logs the error that could occur in
 * the program.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class PostalFacade {

    private static Long minLengthCountyAndMunicipalityNumber = 0000L;

    private static Long maxLengthCountyAndMunicipalityNumber = 9999L;

    private static Long minPostalCodeValue = 0000L;

    private static Long maxPostalCodeValue = 9999L;

    private PostalRegister postalRegister;

    private CountyRegister countyRegister;

    private Logger logger;

    /**
     * Makes an instance of the postal facade object.
     */
    public PostalFacade(){
        postalRegister = new PostalRegister();
        countyRegister = new CountyRegister();
        logger = Logger.getLogger(getClass().toString());
    }

    /**
     * Adds a postal information object to the postal register.
     * @param nameOfPlace the name of the place.
     * @param postalCode the postal code of the place.
     * @param countyAndMunicipalityNumber the county and municipality number.
     */
    private void addPostalInformation(String nameOfPlace, Long postalCode, long countyAndMunicipalityNumber) {
        PostalInformation postalInformation = new PostalInformation(nameOfPlace, postalCode, countyAndMunicipalityNumber);
        try {
            postalRegister.addPostalInformation(postalInformation);
        }catch (CouldNotAddPostalInformationException exception){
            logger.log(Level.INFO, exception.getMessage());
        }
    }


    /**
     * Adds the wanted county to the county register.
     * @param nameOfCounty the name of the county you want to add.
     * @param countyAndMunicipalityNumber the county and municipality number that this county belongs to.
     */
    private void addCounty(String nameOfCounty, Long countyAndMunicipalityNumber) {
        try {
            checkString(nameOfCounty, "name of county");
            checkIfCountyAndMunicipalityNumberIsValid(countyAndMunicipalityNumber);
            countyRegister.addCountyWithName(nameOfCounty, countyAndMunicipalityNumber);
        }catch (CouldNotAddCountyException | IllegalArgumentException exception){
            logger.log(Level.WARNING, exception.getMessage());
        }
    }

    /**
     * Adds a municipality to the county it belongs to.
     * @param municipalityName the name of the new municipality.
     * @param countyAndMunicipalityNumber the county and municipality number.
     * @throws CouldNotGetCountyException gets thrown if the county could not be found.
     */
    private void addMunicipality(String municipalityName, Long countyAndMunicipalityNumber) throws CouldNotGetCountyException {
        try {
            checkString(municipalityName, "municipality name");
            checkIfCountyAndMunicipalityNumberIsValid(countyAndMunicipalityNumber);
            countyRegister.addMunicipalityToCounty(municipalityName, countyAndMunicipalityNumber);
        }catch (CouldNotGetCountyException | IllegalArgumentException exception){
            logger.log(Level.WARNING, exception.getMessage());
            throw exception;
        }
    }

    //Todo: Alle under er statiske metoder.
    /**
     * Checks if the string is empty or null.
     * @param wordToCheck the word you want to check if its empty or null.
     * @param prefix the prefix you want in the exception.
     */
    public static void checkString(String wordToCheck, String prefix){
        if ((wordToCheck == null) || (wordToCheck.isEmpty())){
            String error = "empty";
            if (wordToCheck == null){
                error = "null";
            }
            throw new IllegalArgumentException("The " + prefix + " cannot be " + error + ".");
        }
    }

    /**
     * Checks if the CountyAndMunicipalityNumber is a valid format.
     * @param countyAndMunicipalityNumber the number of the county and the municipality number that you want to check .
     */
    public static void checkIfCountyAndMunicipalityNumberIsValid(Long countyAndMunicipalityNumber){
        if ((countyAndMunicipalityNumber == null) || (countyAndMunicipalityNumber <= minLengthCountyAndMunicipalityNumber) || (countyAndMunicipalityNumber > maxLengthCountyAndMunicipalityNumber)){
            String error = "must be between 0000 and 9999.";
            if (countyAndMunicipalityNumber == null){
                error = "cannot be null.";
            }
            throw new IllegalArgumentException("The county and municipality number " + error);
        }
    }

    /**
     * Gets the min value of the county and municipality number.
     * @return the min value of the county and municipality number.
     */
    public static Long getMinLengthCountyAndMunicipalityNumber(){
        return minLengthCountyAndMunicipalityNumber;
    }

    /**
     * Gets the max value of the county and municipality number.
     * @return the max value of the county and municipality number.
     */
    public static Long getMaxLengthCountyAndMunicipalityNumber(){
        return maxLengthCountyAndMunicipalityNumber;
    }

    /**
     * Gets the min value that the postal code should be.
     * @return the min value that a postal code should be.
     */
    public static Long getMinPostalCodeValue(){
        return minPostalCodeValue;
    }

    /**
     * Gets the max value that the postal code should be.
     * @return the max value that the postal code should be.
     */
    public static Long getMaxPostalCodeValue(){
        return maxPostalCodeValue;
    }
}

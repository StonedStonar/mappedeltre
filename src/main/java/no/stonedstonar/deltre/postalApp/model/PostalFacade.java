package no.stonedstonar.deltre.postalApp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the backbone of the application. Handles all the GUI interaction and logs the error that could occur in
 * the program. This facade holds all the postal information for one country. If you want to expand to other countries we need to make
 * multiple postal facades and a class that holds them all.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class PostalFacade {

    private List<PostalSystem> postalSystems;

    private Logger logger;

    /**
     * Makes an instance of the postal facade object.
     */
    public PostalFacade(){
        postalSystems = new ArrayList<>();
        logger = Logger.getLogger(getClass().toString());
    }

    /**
     * Gets a country form the postal facade.
     * @param countryName the name of the country.
     * @return a postal system that matches this name.
     */
    public PostalSystem getPostalSystemByName(String countryName){
        PostalSystem.checkString(countryName, "country name");
        PostalSystem postalSystem;
        try {
            postalSystem = postalSystems.stream().filter(postSystem -> postSystem.getCountry().equals(countryName)).findFirst().get();
        }catch (NoSuchElementException exception){
            String error = "Could not find any country by that name in this postal facade. " + countryName;
            logger.log(Level.FINE, error);
            throw new IllegalArgumentException(error);
        }
        return postalSystem;
    }

    

    /**
     * Adds a countries postal system to the Postal facade.
     * @param countryName the name of the country you want to make a postal system for.
     * @param minLengthCountyAndMunicipalityNumber the min value that the county and municipality number should be.
     * @param maxLengthCountyAndMunicipalityNumber the max value that the county and municipality number should be.
     * @param minPostalCodeLength the min value that the postal code should be.
     * @param maxPostalCodeLength the max value that the postal code should be.
     */
    public void addPostalSystem(String countryName,Long minLengthCountyAndMunicipalityNumber, Long maxLengthCountyAndMunicipalityNumber, Long minPostalCodeLength, Long maxPostalCodeLength){
        PostalSystem.checkString(countryName, "country name");
        checkLong(minLengthCountyAndMunicipalityNumber, "minimum value of county and municiplaity number");
        checkLong(maxLengthCountyAndMunicipalityNumber, "maximum value of county and municiplaity number");
        checkLong(minPostalCodeLength, "minimum value of the postal code");
        checkLong(maxPostalCodeLength, "minimum value of the postal code");
        PostalSystem newPostalSystem = new PostalSystem(countryName, minLengthCountyAndMunicipalityNumber, maxLengthCountyAndMunicipalityNumber, minPostalCodeLength, maxPostalCodeLength);
        postalSystems.add(newPostalSystem);
    }

    /**
     * Checks if the long is null or not.
     * @param longToCheck the long you want to check.
     */
    private void checkLong(Long longToCheck, String prefix){
        if (longToCheck == null){
            throw new IllegalArgumentException("The " + prefix + " cannot be null.");
        }
    }
}

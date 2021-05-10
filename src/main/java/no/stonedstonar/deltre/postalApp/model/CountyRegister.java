package no.stonedstonar.deltre.postalApp.model;

import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotAddCountyException;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotGetCountyException;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a county register where all the county's of this country are stored in case the government changes
 * the postal system later.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CountyRegister {


    private Map<Long, County> countyMap;

    /**
     * Makes an instance of the county register for this country.
     */
    public CountyRegister(){
        countyMap = new HashMap<>();
    }

    /**
     * Adds a county to the list.
     * @param countyName the name of the county.
     * @param countyAndMunicipalityNumber the number of the county and the municipality.
     * @throws CouldNotAddCountyException gets thrown if the county by this number is already in the register.
     */
    public void addCountyWithName(String countyName, Long countyAndMunicipalityNumber) throws CouldNotAddCountyException {
        County county = new County(countyName);
        PostalFacade.checkIfCountyAndMunicipalityNumberIsValid(countyAndMunicipalityNumber);
        Long countyNumber = getSubNumber(countyAndMunicipalityNumber, 0, 2);
        if (!countyMap.containsKey(countyAndMunicipalityNumber)){
            countyMap.put(countyNumber, county);
        }else{
            throw new CouldNotAddCountyException("Could not add the county by name " + countyName + " and number " + countyAndMunicipalityNumber + " since they are allready in the register.");
        }
    }

    /**
     * Adds a municipality to the wanted county.
     * @param municipalityName the name of the new municipality.
     * @param countyAndMunicipalityNumber the county and municipality number.
     * @throws CouldNotGetCountyException gets thrown if the county does not exist.
     */
    public void addMunicipalityToCounty(String municipalityName, Long countyAndMunicipalityNumber) throws CouldNotGetCountyException {
        PostalFacade.checkString(municipalityName, "municipality name");
        PostalFacade.checkIfCountyAndMunicipalityNumberIsValid(countyAndMunicipalityNumber);
        Long countyNumber = getSubNumber(countyAndMunicipalityNumber, 0, 2);
        Long municipalityNumber = getSubNumber(countyAndMunicipalityNumber, 2, 4);
        if (countyMap.containsKey(countyNumber)){
            countyMap.get(countyNumber).addMunicipality(municipalityName, municipalityNumber);
        }else {
            throw new CouldNotGetCountyException("The county with this number is not in the register:" + countyAndMunicipalityNumber);
        }
    }

    /**
     * Checks if the county by this number is in the register.
     * @param countyAndMunicipalityNumber the county number you want to check.
     * @return <code>true</code> if the county by this number is in the register.
     *         <code>false</code> if the county by this name isn't in the register.
     */
    public boolean checkIfCountyIsInRegister(Long countyAndMunicipalityNumber){
        boolean valid;
        try {
            PostalFacade.checkIfCountyAndMunicipalityNumberIsValid(countyAndMunicipalityNumber);
            Long countyNumber = getSubNumber(countyAndMunicipalityNumber, 0, 2);
            valid = countyMap.containsKey(countyNumber);
        }catch (IllegalArgumentException exception){
            valid = false;
        }
        return valid;
    }

    /**
     * Gets a municipality name based on the countyWithMunicipality number.
     * @param countyWithMunicipality the number that contains the county and municipality.
     * @return the municipality that matches this number.
     */
    public String getMunicipality(Long countyWithMunicipality){
        Long countyNumber = getSubNumber(countyWithMunicipality, 0, 2);
        String municipalityName;
        if (countyMap.containsKey(countyNumber)){
            County county = countyMap.get(countyNumber);
            Long municipalityNumber = getSubNumber(countyWithMunicipality, 2, 4);
            municipalityName = county.getMunicipality(municipalityNumber);
        }else {
            throw new IllegalArgumentException("The county with the number" + countyNumber + " is not in this county register.");
        }
        return municipalityName;
    }

    /**
     * Gets a certain portion of a long number.
     * @param numberToBeSplit the number you want to get a part of.
     * @param firstPlace the first number you want to get. Goes from 0 to number length.
     * @param lastPlace the last number you want included in the cut.
     * @return a new number based on the first place and the last place. If you want 2 numbers out you can set
     *         firstplace to be 0 and last place to be 2.
     */
    private Long getSubNumber(Long numberToBeSplit, int firstPlace, int lastPlace){
        if (numberToBeSplit == null){
            throw new IllegalArgumentException("The municipality number cannot be null.");
        }
        String numberAsString = Long.toString(numberToBeSplit);
        Long countyNumber = Long.parseLong(numberAsString.substring(firstPlace,lastPlace));

        return countyNumber;
    }
}
